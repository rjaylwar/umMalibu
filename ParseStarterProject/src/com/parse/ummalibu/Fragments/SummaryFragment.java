package com.parse.ummalibu.fragments;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.objects.Driver;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.responses.DriverResponse;
import com.parse.ummalibu.responses.MapsResponse;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rjaylward on 9/27/15.
 */
public class SummaryFragment extends Fragment {

    private View mRoot;
    private UmberRequest mRequest;
    private Driver mDriver;
    private AppCompatActivity mActivity;

    @Bind(R.id.summary_background_map)
    ImageView mMapImage;
    @Bind(R.id.summary_locations_top_layout)
    LinearLayout mTopLocationsLayout;
    @Bind(R.id.summary_pick_up_address)
    TextView mPickUpAddress;
    @Bind(R.id.summary_destination_address)
    TextView mDestAddress;

    @Bind(R.id.summary_sliding_layout)
    RelativeLayout mSlidingLayout;

    @Bind(R.id.summary_person_image)
    de.hdodenhof.circleimageview.CircleImageView mCircleImageView;
    @Bind(R.id.summary_contact_button)
    Button mContactButton;
    @Bind(R.id.summary_status_button)
    Button mStatusButton;
    @Bind(R.id.summary_name_view)
    TextView mName;

    @Bind(R.id.summary_pick_up_time)
    TextView mPickUpTime;
    @Bind(R.id.summary_distance)
    TextView mDistance;
    @Bind(R.id.summary_time)
    TextView mTripTime;
    @Bind(R.id.summary_status)
    TextView mStatus;

    @Bind(R.id.summary_cost_view)
    TextView mCost;

    @Bind(R.id.summary_minus_button)
    Button mMinusButton;
    @Bind(R.id.summary_plus_button)
    Button mPlusButton;
    @Bind(R.id.summary_split_gas_button)
    Button mSplitGas;

    private MapsResponse mMapsResponse;

    private double mDisplayedCost;
    private int mNumWaysSplit = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (AppCompatActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_summary_view, container, false);
        ButterKnife.bind(this, mRoot);
        mRequest = getArguments().getParcelable(FieldNames.UMBER_REQUEST);

        if(mRequest == null) {
            Toast.makeText(mActivity, "An error occured, try again later", Toast.LENGTH_SHORT).show();
            mActivity.onBackPressed();
        }

        mActivity.getContentResolver().registerContentObserver(Table.Drivers.CONTENT_URI, true, mDriversObserver);
        initViews();

        return mRoot;
    }

    private ContentObserver mDriversObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);

            DatabaseHelper dbHelper = new DatabaseHelper(mActivity);
            mDriver = dbHelper.getDriver(mRequest.getDriverEmail());
            if(mDriver != null)
                loadDriverView();
        }
    };

    private void initViews() {
        mMapImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mRequest != null)
                    Glide.with(SummaryFragment.this).load(mRequest
                            .getMapUrl(mMapImage.getWidth(), mMapImage.getHeight())).into(mMapImage);
                mMapImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        makeGoogleDirectionsRequest();
        loadDriverView();
    }

    private void loadDriverView() {
        if(mRequest.getDriverEmail().equals(Preferences.getInstance().getEmail())) {
            Glide.with(mActivity).load(mRequest.getRiderImageUrl()).into(mCircleImageView);
            mName.setText(mRequest.getName());
        } else {
            if(mDriver != null) {
                Glide.with(mActivity).load(mDriver.getImageUrl()).into(mCircleImageView);
                mName.setText(mDriver.getName());
            } else
                getDriver();
        }
    }

    private void makeGoogleDirectionsRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getGoogleMapsData(mRequest, new VolleyRequestListener<MapsResponse>() {
            @Override
            public void onResponse(MapsResponse mapsResponse) {
                mMapsResponse = mapsResponse;
                mDistance.setText(mapsResponse.getDistance());
                mTripTime.setText(mapsResponse.getTripTime());
                calculateCost();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("maps req error", error.toString());
            }
        });
    }

    private void calculateCost() {
        if(mRequest.getDriverEmail().equals(Preferences.getInstance().getEmail()))
            mDisplayedCost = mMapsResponse.getDistanceMiles() / Preferences.getInstance().getMpg() * Constants.GAS_PRICE / 2;
        else if(mDriver != null)
            mDisplayedCost = mMapsResponse.getDistanceMiles() / mDriver.getMpg() * Constants.GAS_PRICE / 2;

        mNumWaysSplit = 2;
        showCost();
    }

    private void getDriver() {
        DatabaseHelper dbHelper = new DatabaseHelper(mActivity);
        mDriver = dbHelper.getDriver(mRequest.getDriverEmail());
        if(mDriver == null)
            driverApiRequest();
    }

    private void driverApiRequest() {
        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getDriver(mRequest.getEmail(), new VolleyRequestListener<DriverResponse>() {
            @Override
            public void onResponse(DriverResponse response) {
                response.saveResponse(mActivity);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "Error getting driver", Toast.LENGTH_SHORT);
            }
        });
    }

    @OnClick(R.id.summary_contact_button)
    void sendText() {
        if(mRequest.getDriverEmail().toLowerCase().equals(Preferences.getInstance().getEmail())) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("sms:" + mRequest.getPhoneNumber()));
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("exit_on_sent", true);
            startActivityForResult(sendIntent, 0);
        } else if(mDriver != null && mDriver.getEmail().equals(Preferences.getInstance().getEmail())) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("sms:" + mDriver.getPhoneNumber()));
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("exit_on_sent", true);
            startActivityForResult(sendIntent, 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().getContentResolver().unregisterContentObserver(mDriversObserver);
    }

    @OnClick(R.id.summary_minus_button)
    void divideCost() {
        if(mNumWaysSplit > 1) {
            mDisplayedCost = mDisplayedCost * 2;
            mNumWaysSplit -= 1;
            showCost();
        }
    }

    @OnClick(R.id.summary_plus_button)
    void splitCost() {
        mDisplayedCost = mDisplayedCost / 2;
        mNumWaysSplit += 1;
        showCost();
    }

    private void showCost() {
        mCost.setText(String.format("$%.2f", mDisplayedCost));
        mSplitGas.setText(mActivity.getResources().getQuantityString(R.plurals.split_gas_number_ways, mNumWaysSplit));
    }
}
