package com.parse.ummalibu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.parse.ummalibu.R;
import com.parse.ummalibu.SearchLayout;
import com.parse.ummalibu.adapters.LocationsListAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.base.BaseFragment;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.objects.UmLocation;
import com.parse.ummalibu.responses.UmLocationsResponse;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.views.EmptyListView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 10/3/15
 */
public class LocationsFragment extends BaseFragment {

    @Bind(R.id.recycler_view) LoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.basic_empty_list_view) EmptyListView mEmptyListView;
    @Bind(R.id.progress) ProgressBar mProgressBar;
    @Bind(R.id.search_layout) SearchLayout mSearchLayout;

    private AppCompatActivity mActivity;
    private LocationsListAdapter mAdapter;
    private ProgressDialog mDialog;

    public static LocationsFragment create() {
        return new LocationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_locations_list, container, false);
        ButterKnife.bind(this, root);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.getContentResolver().registerContentObserver(Table.UmLocations.CONTENT_URI, true, mLocationsObserver);

        initLayout();
        loadList();
        return root;
    }

    private ContentObserver mLocationsObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            queryDatabase();
        }
    };

    private void initLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryApi();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        mAdapter = new LocationsListAdapter(mActivity, new LocationsListAdapter.OnLocationSelectedListener() {
            @Override
            public void onLocationSelected(UmLocation location) {
                selectLocation(location);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mSearchLayout.setOnTextChangedListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (actionId == EditorInfo.IME_NULL && event != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    mSearchLayout.getEditText().setCursorVisible(false);
                    return onLocationEntered();
                } else
                    return false;
            }
        });

        mSearchLayout.setOnSearchListener(new SearchLayout.OnSearchListener() {
            @Override
            public void onTextClicked() {
                mSearchLayout.getEditText().setCursorVisible(true);
            }

            @Override
            public void onLocationButtonClicked() {
            }

            @Override
            public void onClearButtonClicked() {
                mSearchLayout.setLocationName("");
                mSearchLayout.getEditText().setCursorVisible(false);
                hideKeyboard();
            }
        });
    }

    private void selectLocation(UmLocation location) {
        finishForResult(location, Activity.RESULT_OK);
    }

    private void loadList() {
        queryDatabase();
        if(Preferences.getInstance().getListData().getLocationsExpiration() < System.currentTimeMillis())
            queryApi();
    }

    public void queryApi() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getLocations(new VolleyRequestListener<UmLocationsResponse>() {
            @Override
            public void onResponse(UmLocationsResponse response) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
                Preferences.getInstance().getListData().setLocationsExpiration(System.currentTimeMillis() + Constants.ONE_MIN_MILLIS);
                response.saveResponse(mActivity);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Locations Request Error", error.getMessage());
            }
        });
    }

    public void queryDatabase() {
        DatabaseHelper helper = DatabaseHelper.getInstance(mActivity);
        mAdapter.setItems(helper.getAllLocations());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mActivity.getContentResolver().unregisterContentObserver(mLocationsObserver);
    }

    private boolean onLocationEntered() {

        String entry = mSearchLayout.getEditText().getText().toString();
        if(entry.length() > 0) {
            hideKeyboard();
            new GeocodeLookup().execute(entry);
            return true;
        }
        else
            return false;
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        mSearchLayout.getEditText().clearFocus();
    }

    private class GeocodeLookup extends AsyncTask<String, Void, Address> {

        @Override
        protected void onPreExecute() {
            showProgressDialog("Looking up location");
        }

        @Override
        protected Address doInBackground(String... params) {
            try {
                Geocoder geocoder = new Geocoder(mActivity.getApplicationContext());
                Address address = geocoder.getFromLocationName(params[0], 1).get(0);
                Log.d("address match", address.toString());

                return address;
            } catch(Exception e) {
                Log.e("address search error", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Address address) {
            hideProgressDialog();

            if(address != null) {
                //The address may have separate lines for city and state, or combined into 1. This will figure out which
                String addressLine0 = address.getAddressLine(0);
                final String formattedAddressLine;
                if(addressLine0.contains(","))
                    formattedAddressLine = addressLine0;
                else if(!addressLine0.contains("null"))
                    formattedAddressLine = String.format("%s, %s", addressLine0, address.getAddressLine(1));
                else
                    formattedAddressLine = address.getAddressLine(1);

                mSearchLayout.clearTextViewFocus();
                mSearchLayout.setLocationName(formattedAddressLine);

                UmLocation umLocation = new UmLocation();
                umLocation.setAddress(formattedAddressLine);
                umLocation.setLat(address.getLatitude());
                umLocation.setLon(address.getLongitude());
                Log.d("UM Location", umLocation.getLatLng().toString() + umLocation.getAddress());

                finishForResult(umLocation, Activity.RESULT_OK);
            } else
                Toast.makeText(mActivity, "error not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishForResult(UmLocation location, int activityResultCode) {
        hideKeyboard();

        Intent intent = new Intent();
        if(location != null)
            intent.putExtra(FieldNames.ADDRESS, location);

        mActivity.setResult(activityResultCode, intent);
        mActivity.finish();
    }

    private void showProgressDialog(String message) {
        mDialog = new ProgressDialog(mActivity);
        mDialog.setMessage(message);
        mDialog.show();
    }

    private void hideProgressDialog() {
        mDialog.hide();
    }
}
