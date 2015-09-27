package com.parse.ummalibu.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.parse.ummalibu.Adapters.RequestListAdapter;
import com.parse.ummalibu.Api.ApiHelper;
import com.parse.ummalibu.Database.DatabaseHelper;
import com.parse.ummalibu.Database.Table;
import com.parse.ummalibu.Objects.UmberRequest;
import com.parse.ummalibu.R;
import com.parse.ummalibu.Responses.UmberRequestResponse;
import com.parse.ummalibu.Values.Constants;
import com.parse.ummalibu.Values.Preferences;
import com.parse.ummalibu.Views.EmptyListView;
import com.parse.ummalibu.Views.LoadMoreRecyclerView;
import com.parse.ummalibu.Volley.VolleyRequestListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/25/15.
 */
public class RequestListFragment extends Fragment {

    @Bind(R.id.recycler_view) LoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.basic_empty_list_view) EmptyListView mEmptyListView;
    @Bind(R.id.progress) ProgressBar mProgressBar;

    private RequestListAdapter mAdapter;
    protected AppCompatActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.basic_swipe_refresh_recyclerview, container, false);
        ButterKnife.bind(this, root);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.getContentResolver().registerContentObserver(Table.Requests.CONTENT_URI, true, mRequestsObserver);

        initLayout();
        loadList();
        return root;
    }

    private void initLayout() {
        mEmptyListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new RequestListAdapter(getActivity(), new RequestListAdapter.OnRequestClickedListener() {
            @Override
            public void onRequestClicked(UmberRequest request) {
                checkRequestStatus(request);
            }
        });
        mAdapter.colorRequests(useColors());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.um_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiRequest();
            }
        });
    }

    protected boolean useColors() {
        return false;
    }

    protected int getMode() {
        return RequestListAdapter.OPEN_REQUESTS;
    }

    private ContentObserver mRequestsObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            loadList();
        }
    };

    private void checkRequestStatus(final UmberRequest request) {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getUmberRequest(request.getObjectId(), new VolleyRequestListener<UmberRequest>() {
            @Override
            public void onResponse(UmberRequest response) {
                if (!request.isClaimed()) {
                    showAlert(request);
                } else {
                    Toast.makeText(mActivity, "Request has already been claimed", Toast.LENGTH_SHORT).show();
                    request.saveResponse(mActivity);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Checking Request Error", error.getMessage());
                Toast.makeText(mActivity, R.string.request_cant_be_claimed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlert(final UmberRequest request) {
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle(mActivity.getString(R.string.claim_request));
        alertDialog.setMessage(String.format(mActivity.getString(R.string.want_to_give_person_ride_to_address), request.getName(), request.getDestination()));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showDatePicker(request);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showDatePicker(final UmberRequest request) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(request.getEta());

        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                showTimePicker(cal, request);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimePicker(final Calendar cal, final UmberRequest request) {
        Calendar today = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                claimRequest(request, cal);
            }
        }, today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }

    private void claimRequest(final UmberRequest request, Calendar eta) {
        request.setDriverEmail(Preferences.getInstance().getEmail());
        request.setEta(eta.getTimeInMillis());
        request.setClaimed(true);

        ApiHelper helper = new ApiHelper(mActivity);
        helper.claimUmberRequest(request, new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                DatabaseHelper dbHelper = new DatabaseHelper(mActivity);
                dbHelper.addRequest(request);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Claim Request Error", error.getMessage() == null ? "Error message was null" : error.getMessage());
                Toast.makeText(mActivity, R.string.request_cant_be_claimed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadList() {
        if(Preferences.getInstance().getListData().getRequestsExpiration() < System.currentTimeMillis())
            apiRequest();

        databaseRequest();
    }

    private void databaseRequest() {
        ArrayList<UmberRequest> requests = new DatabaseHelper(mActivity).getRequests();
        mAdapter.setData(requests, getMode());
    }

    private void apiRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getUmberRequests(new VolleyRequestListener<UmberRequestResponse>() {
            @Override
            public void onResponse(UmberRequestResponse response) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
                Preferences.getInstance().getListData().setRequestsExpiration(System.currentTimeMillis() + Constants.ONE_MIN_MILLIS);
                response.saveResponse(mActivity);
                Log.d("Response ------>", String.valueOf(response.getRequests().size()));
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Get Umber Request Error", error.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().getContentResolver().unregisterContentObserver(mRequestsObserver);
    }
}
