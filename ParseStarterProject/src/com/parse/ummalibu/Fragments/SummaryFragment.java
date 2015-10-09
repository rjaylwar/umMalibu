package com.parse.ummalibu.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.parse.ummalibu.VenmoLibrary;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.api.NotificationsHelper;
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

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by rjaylward on 9/27/15
 */
public class SummaryFragment extends ToolbarFragment {

    private UmberRequest mRequest;
    private Driver mDriver;

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

    public static SummaryFragment makeFragment(UmberRequest request) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("umber_request", request);
        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_summary_view;
    }

    @Override
    protected void fragOnCreateView(Bundle savedInstanceState) {
        mRequest = getArguments().getParcelable(FieldNames.UMBER_REQUEST);

        if(mRequest == null) {
            Toast.makeText(mActivity, "An error occured, try again later", Toast.LENGTH_SHORT).show();
            mActivity.onBackPressed();
        }

        mActivity.getContentResolver().registerContentObserver(Table.Drivers.CONTENT_URI, true, mDriversObserver);
        initViews();
    }

    private ContentObserver mDriversObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);

//            DatabaseHelper dbHelper = new DatabaseHelper(mActivity);
//            mDriver = dbHelper.getDriver(mRequest.getDriverEmail());
//            if(mDriver != null)
//                loadDriverView();
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a M/d/yy", Locale.US);
        checkStatus();
        mPickUpTime.setText(dateFormat.format(mRequest.getEta()));
        mDestAddress.setText(mRequest.getDestination());
        mPickUpAddress.setText(mRequest.getPickUpLocation());
        makeGoogleDirectionsRequest();
        loadDriverView();
    }

    private void checkStatus() {
        if(!mRequest.isCanceled()) {
            if (!mRequest.isClaimed()) {
                mStatus.setText(R.string.unclaimed);
                mName.setText(R.string.no_driver_yet);
                mStatusButton.setText(R.string.cancel);
            } else if (!mRequest.isStarted()) {
                mStatus.setText(R.string.claimed);

                if (isRider())
                    mStatusButton.setText(R.string.cancel);
                else
                    mStatusButton.setText(R.string.unclaim);

            } else if (!mRequest.isPickedUp()) {
                mStatusButton.setText(R.string.in_progress);
                mStatus.setText(R.string.on_the_way);
            } else if (!mRequest.isComplete()) {
                mStatusButton.setText(R.string.in_progress);
                mStatus.setText(R.string.picked_up);
            } else {
                mStatusButton.setText(R.string.delete);
                mStatus.setText(R.string.complete);
            }
        }
        else {
            mStatus.setText(R.string.canceled);
            mStatusButton.setText(R.string.delete);
        }
    }

    @OnClick(R.id.summary_status_button)
    void onStatusButtonClick() {
        if(!mStatusButton.getText().toString().equals(getString(R.string.in_progress))) {
            AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
            alertDialog.setTitle(mActivity.getString(R.string.claim_request));
            alertDialog.setMessage("Are you sure you want to " + mStatusButton.getText().toString() + " this request?");
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (mStatusButton.getText().toString()) {
                                case "Cancel":
                                    Log.d("Cancel Request", mStatusButton.getText().toString());
                                    cancelRequest();
                                    break;
                                case "Unclaim":
                                    Log.d("Unclaim Request", mStatusButton.getText().toString());
                                    unclaimRequest();
                                    break;
                                case "Delete":
                                    Log.d("Delete Request", mStatusButton.getText().toString());
                                    deleteRequest();
                                    break;
                                default:
                                    Log.d("Button text", mStatusButton.getText().toString());
                            }
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void unclaimRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        mRequest.setClaimed(false);
        mRequest.setDriverEmail("");

        helper.updateUmberRequestStatus(mRequest, new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                NotificationsHelper.sendUnclaimNotification(mRequest);
                mActivity.onBackPressed();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "The request could not be unclaimed at this time. Try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.deleteRequest(mRequest.getObjectId(), new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                mActivity.onBackPressed();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "The request could not be deleted at this time. Try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cancelRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        mRequest.setCanceled(true);

        helper.updateUmberRequestStatus(mRequest, new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                NotificationsHelper.sendCancelNotification(mRequest);
                mActivity.onBackPressed();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "The request could not be canceled at this time. Try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void loadDriverView() {
        if(mRequest.getDriverEmail().equals(Preferences.getInstance().getEmail())) {
            Glide.with(mActivity).load(mRequest.getRiderImageUrl()).into(mCircleImageView);
            mName.setText(mRequest.getName());
        } else {
            if(mDriver != null) {
                Glide.with(mActivity).load(mDriver.getImageUrl()).into(mCircleImageView);
                mName.setText(mDriver.getName());
                calculateCost();
            } else
                getDriver();
        }
    }

    private void makeGoogleDirectionsRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getGoogleMapsData(mRequest, new VolleyRequestListener<MapsResponse>() {
            @Override
            public void onResponse(MapsResponse mapsResponse) {
                if (mapsResponse != null) {
                    mMapsResponse = mapsResponse;
                    mDistance.setText(mapsResponse.getDistance());
                    mTripTime.setText(mapsResponse.getTripTime());
                    calculateCost();
                }
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
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mActivity);
        mDriver = dbHelper.getDriver(mRequest.getDriverEmail());
        if(mDriver == null)
            driverApiRequest();
    }

    private void driverApiRequest() {
        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getDriver(mRequest.getEmail(), new VolleyRequestListener<DriverResponse>() {
            @Override
            public void onResponse(DriverResponse response) {
//                response.saveResponse(mActivity);
                mDriver = response.getDrivers().get(0);
                loadDriverView();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "Error getting driver", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.summary_contact_button)
    void sendText() {
        if(isDriver()) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("sms:" + mRequest.getPhoneNumber()));
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("exit_on_sent", true);
            startActivityForResult(sendIntent, 0);
        } else if(mDriver != null && mRequest.getEmail().equals(Preferences.getInstance().getEmail())) {
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
        mSplitGas.setText(String.format(mActivity.getResources().getQuantityString(R.plurals.split_gas_number_ways, mNumWaysSplit), mNumWaysSplit));
    }

    @OnClick(R.id.summary_split_gas_button)
    void launchVenmo() {
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle(mActivity.getString(R.string.claim_request));
        alertDialog.setMessage("Would you like to try and use Venmo to split the cost of gas?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(VenmoLibrary.isVenmoInstalled(mActivity))
                            launchVenmoPayment();
                        else
                            Toast.makeText(mActivity, "Error, You must install Venmo first.", Toast.LENGTH_SHORT).show();
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

    public void launchVenmoPayment() {
        if(isDriver()) {
            String note = "UM Waves Rideshare request to split the cost of gas for a ride to "
                    + mRequest.getDestination();

            Intent venmoIntent = VenmoLibrary.openVenmoPayment(Constants.VENMO_APP_ID, Constants.VENMO_APP_NAME,
                    mRequest.getPhoneNumber(), String.format("%.2f", mDisplayedCost), note, VenmoLibrary.CHARGE);

            startActivityForResult(venmoIntent, VenmoLibrary.REQUEST_CODE_VENMO_APP_SWITCH);
        }
        else {
            if(mRequest.isClaimed() && mDriver != null && !mDriver.getPhoneNumber().equals("")) {
                String note = "UM Waves Rideshare - gas cost reimbursement for ride to "
                        + mRequest.getDestination();

                Intent venmoIntent = VenmoLibrary.openVenmoPayment(Constants.VENMO_APP_ID, Constants.VENMO_APP_NAME,
                        mDriver.getPhoneNumber(), String.format("%.2f", mDisplayedCost), note, VenmoLibrary.PAY);

                startActivityForResult(venmoIntent, VenmoLibrary.REQUEST_CODE_VENMO_APP_SWITCH);
            }
        }
    }

    private boolean isDriver() {
        return mRequest.isClaimed() && mRequest.getDriverEmail().toLowerCase().equals(Preferences.getInstance().getEmail());
    }

    private boolean isRider() {
        return Preferences.getInstance().getEmail().equals(mRequest.getEmail());
    }
}
