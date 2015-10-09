package com.parse.ummalibu.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.parse.ummalibu.LocationsActivity;
import com.parse.ummalibu.LoginActivity;
import com.parse.ummalibu.R;
import com.parse.ummalibu.SearchLayout;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.api.NotificationsHelper;
import com.parse.ummalibu.base.BaseFragment;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.helper.DatePickerHelper;
import com.parse.ummalibu.objects.Driver;
import com.parse.ummalibu.objects.UmLocation;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.responses.DriverResponse;
import com.parse.ummalibu.responses.MyUmberRequestResponse;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.views.RiderControlsView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rjaylward on 9/28/15
 */
public class UmberMapFragment extends BaseFragment {

    @Bind(R.id.search_pickup_layout) SearchLayout mSearchPickUpLayout;
    @Bind(R.id.search_destination_layout) SearchLayout mSearchDestLayout;
    @Bind(R.id.search_search_layout) LinearLayout mSearchLayout;
    @Bind(R.id.umber_request_button) Button mRequestButton;
    @BindDimen(R.dimen.search_widget_height) int mSearchLayoutHeight;

    @Bind(R.id.umber_driver_layout) RelativeLayout mDriverLayout;
    @Bind(R.id.umber_driver_image) ImageView mDriverImage;
    @Bind(R.id.umber_driver_name) TextView mDriverName;
    @Bind(R.id.umber_contact_button) Button mDriverContactButton;
    @Bind(R.id.umber_cancel_button) Button mDriverCancelButton;
    @Bind(R.id.umber_car_description) TextView mDriverCarDescription;

    @Bind(R.id.umber_riders_layout) LinearLayout mRidersLayout;
    @Bind(R.id.umber_first_rider_control) RiderControlsView mFirstRiderView;
    @Bind(R.id.umber_second_rider_control) RiderControlsView mSecondRiderView;
    @Bind(R.id.umber_third_rider_control) RiderControlsView mThirdRiderView;

    private View mRoot;
    private AppCompatActivity mActivity;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Marker mMarker;

    private Handler mHandler;

    private boolean mKeepUpdating = false;
    private boolean mIsUpdating = false;
    private boolean mHasHappened = false;

    private static final int PICKUP_LOCATIONS = 33;
    private static final int DESTINATION_LOCATIONS = 34;

    private HashSet<UmberRequest> mOpenRequests = new HashSet<>();

    private UmLocation mSelectedPickUpLocation;
    private UmLocation mSelectedDestLocation;

    private ArrayList<UmberRequest> mRiderRequests;
    private ArrayList<UmberRequest> mDriverRequests;
    private Driver mDriver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.activity_umber, container, false);
        mActivity = (AppCompatActivity) getActivity();
        ButterKnife.bind(this, mRoot);

        mSearchPickUpLayout.getEditText().setHint("Click to choose a spot...");
        mSearchPickUpLayout.setTitleText("Pickup Spot");
        mSearchPickUpLayout.setTitleColor(mActivity.getResources().getColor(R.color.um_green));
        mSearchPickUpLayout.setOnSearchListener(new SearchLayout.OnSearchListener() {
            @Override
            public void onTextClicked() {
                mSearchPickUpLayout.getEditText().setFocusable(true);
                mSearchPickUpLayout.clear();
                launchPickupSpotsList();
                mSearchPickUpLayout.getEditText().setCursorVisible(true);
                hideKeyboard();
            }

            @Override
            public void onLocationButtonClicked() {
            }

            @Override
            public void onClearButtonClicked() {
                if (mSearchDestLayout.getEditText().getText().toString().length() == 0)
                    hideDestinationSearch();
                mSelectedPickUpLocation = null;
                mRequestButton.setVisibility(View.GONE);
            }
        });

        mSearchDestLayout.setTitleText("Destination");
        mSearchDestLayout.getEditText().setHint("UM - 3324 Malibu Canyon Rd, Malibu, CA 90265");
        mSearchDestLayout.setTitleColor(mActivity.getResources().getColor(R.color.um_highlight_blue));
        mSearchDestLayout.setOnSearchListener(new SearchLayout.OnSearchListener() {
            @Override
            public void onTextClicked() {
                mSearchDestLayout.getEditText().setFocusable(true);
                mSearchDestLayout.clear();
                launchDestinationSpotsList();
                mSearchDestLayout.getEditText().setCursorVisible(true);
                hideKeyboard();
            }

            @Override
            public void onLocationButtonClicked() {
            }

            @Override
            public void onClearButtonClicked() {
                if (mSearchPickUpLayout.getEditText().getText().toString().length() == 0)
                    hideDestinationSearch();
                mSelectedDestLocation = null;
                mRequestButton.setVisibility(View.GONE);
            }
        });

        Button requestRideButton = (Button) mRoot.findViewById(R.id.umber_request_button);
        requestRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeUmberRequest();
            }
        });

        mHandler = new Handler();
        setUpMapIfNeeded();

        return mRoot;
    }

    private void launchPickupSpotsList() {
        startActivityForResult(LocationsActivity.createIntent(mActivity, LocationsActivity.PICKUP), PICKUP_LOCATIONS);
    }

    private void launchDestinationSpotsList() {
        startActivityForResult(LocationsActivity.createIntent(mActivity, LocationsActivity.DESTINATION), DESTINATION_LOCATIONS);
    }

    private void hideDestinationSearch() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSearchLayout.getLayoutParams();
        params.height = mSearchLayoutHeight;
    }

    private void showDestinationSearch() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSearchLayout.getLayoutParams();
        params.height = 2 * mSearchLayoutHeight;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            UmLocation location = data.getParcelableExtra(FieldNames.ADDRESS);

            if(requestCode == PICKUP_LOCATIONS) {
                setSelectedPickUp(location);
                showDestinationSearch();
            } else
                setSelectedDestination(location);
            checkIfRequestCanBeMade();
        } else {
            if(requestCode == PICKUP_LOCATIONS) {
                mSearchPickUpLayout.clear();
                mSelectedPickUpLocation = null;
            } else {
                mSearchDestLayout.clear();
                mSelectedDestLocation = null;
            }
        }
    }

    private void checkIfRequestCanBeMade() {
        if(mSearchDestLayout.getEditText().getText().toString().length() > 0 &&
                mSearchPickUpLayout.getEditText().getText().toString().length() > 0)
            mRequestButton.setVisibility(View.VISIBLE);
        else
            mRequestButton.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        hideKeyboard();
    }

    private void setSelectedPickUp(UmLocation location) {
        mSelectedPickUpLocation = location;
        mSearchPickUpLayout.setLocationName(location.getFormattedTitle());
        mSearchPickUpLayout.clearFocus();
    }

    private void setSelectedDestination(UmLocation location) {
        mSelectedDestLocation = location;
        mSearchDestLayout.setLocationName(location.getFormattedTitle());
        mSearchDestLayout.clearFocus();
    }

//    private void makeMovingDriverApiRequest() {
//        ApiHelper helper = new ApiHelper(mActivity);
//        helper.getUmberLocation(new VolleyRequestListener<JsonObject>() {
//            @Override
//            public void onResponse(JsonObject response) {
//                double lat;
//                lat = response.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonPrimitive("latitude").getAsDouble();
//                double lon;
//                lon = response.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonPrimitive("longitude").getAsDouble();
//
//                animateMarker(lat, lon);
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//    }
//
//    private void startUpdating() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (mKeepUpdating) {
//                    try {
//                        mIsUpdating = true;
//                        Thread.sleep(3000);
//                        mHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                makeMovingDriverApiRequest();
//                            }
//                        });
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//
//                if (!mKeepUpdating)
//                    mIsUpdating = false;
//            }
//        }).start();
//    }

    @Override
    public void onResume() {
        super.onResume();
        mKeepUpdating = false;
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            View mapView = getChildFragmentManager().findFragmentById(R.id.map).getView();

            if(mapView != null) {
                //noinspection ResourceType
                View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);

                // and next place it, for example, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                // position on right bottom
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                rlp.setMargins(0, 0, 30, 30);
            }
        }

        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
    }

    private void setUpMap() {

        mMap.setMyLocationEnabled(true);

        // The minimum time (in milliseconds) the system will wait until checking if the location changed
        int minTime = 1000;
        // The minimum distance (in meters) traveled until you will be notified
        float minDistance = 5;
        // Create a new instance of the location listener
        // Get the location manager from the system
        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

        // Get the criteria you would like to use
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(false);
        // Get the best provider from the criteria specified, and false to say it can turn the
        // provider on if it isn't already

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateMapCameraOnce(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        String bestProvider = mLocationManager.getBestProvider(criteria, false);
        // Request location updates
        try {
            mLocationManager.requestLocationUpdates(bestProvider, minTime, minDistance, mLocationListener);
        } catch (SecurityException e) {
            // something
        }



//        if (!mIsUpdating)
//            startUpdating();

    }

    private void updateMapCameraOnce(Location location) {
        if (!mHasHappened) {
            updateMapCamera(location);
            mHasHappened = true;
        }
    }

    private void updateMapCamera(Location location) {
        updateMapCamera(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void updateMapCamera(LatLng latLng) {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 1500, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
    }

//    private void animateMarker(double lat, double lon) {
//
//        animateMarkerToICS(mMarker, new LatLng(lat, lon), new LatLngInterpolator() {
//            @Override
//            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
//                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
//                double lng = (b.longitude - a.longitude) * fraction + a.longitude;
//                return new LatLng(lat, lng);
//            }
//        });
//    }


    @Override
    public void onPause() {
        super.onPause();
//        mKeepUpdating = false;
    }

//    static void animateMarkerToICS(Marker marker, LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
//        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
//            @Override
//            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
//                return latLngInterpolator.interpolate(fraction, startValue, endValue);
//            }
//        };
//        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
//        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
//        animator.setDuration(3000);
//        animator.start();
//    }

    private void makeUmberRequest() {

        if(mMap != null && mSelectedPickUpLocation != null && mSelectedDestLocation != null) {
            final UmberRequest umberRequest = new UmberRequest();

            umberRequest.setName(Preferences.getInstance().getName());
            umberRequest.setEmail(Preferences.getInstance().getEmail());

            umberRequest.setPhoneNumber(Preferences.getInstance().getPhoneNumber());
            umberRequest.setRiderImageUrl(Preferences.getInstance().getImageUrl());

            if(mMap.getMyLocation() != null) {
                Location myLocation = mMap.getMyLocation();

                umberRequest.setLongitude(myLocation.getLongitude());
                umberRequest.setLatitude(myLocation.getLatitude());
            }

            umberRequest.setPickUpLocation(mSelectedPickUpLocation.getFormattedTitle());
            umberRequest.setPickupLat(mSelectedPickUpLocation.getLat());
            umberRequest.setPickupLong(mSelectedPickUpLocation.getLon());

            umberRequest.setDestination(mSelectedDestLocation.getFormattedTitle());
            umberRequest.setDestinationLat(mSelectedDestLocation.getLat());
            umberRequest.setDestinationLong(mSelectedDestLocation.getLon());

            DatePickerHelper datePicker = new DatePickerHelper(mActivity);
            datePicker.showPicker(umberRequest, true, new DatePickerHelper.OnDatePickedListener() {

                @Override
                public void onDatePicked(UmberRequest request, Calendar cal) {
                    request.setEta(cal.getTimeInMillis());
                    ApiHelper helper = new ApiHelper(mActivity);

                    helper.makeUmberRequest(umberRequest, new VolleyRequestListener<JsonObject>() {

                        @Override
                        public void onResponse(JsonObject response) {
                            Log.d("New Request ->", response.toString());
                            Toast.makeText(mActivity, "Your request has been made!", Toast.LENGTH_SHORT).show();

                            mSearchPickUpLayout.clear();
                            mSearchDestLayout.clear();

                            String objectId = response.getAsJsonPrimitive(FieldNames.OBJECT_ID).getAsString();
                            NotificationsHelper.subscribeAsRider(objectId);
                            if (Constants.SEND_NEW_REQUEST_NOTIFICATIONS)
                                NotificationsHelper.sendNewRequestNotification(umberRequest);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else {
            if(mMap != null)
                Toast.makeText(mActivity, "Please set a pickup location", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Error, please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        mSearchPickUpLayout.getEditText().clearFocus();
        mSearchDestLayout.getEditText().clearFocus();

        mSearchPickUpLayout.getEditText().setFocusable(false);
        mSearchDestLayout.getEditText().setFocusable(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mKeepUpdating = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (SecurityException e) {
            // something
        }

        mKeepUpdating = false;
    }

    private void getMyRequests() {
        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getMyActiveUmberRequests(new VolleyRequestListener<MyUmberRequestResponse>() {
            @Override
            public void onResponse(MyUmberRequestResponse response) {
                response.saveResponse(mActivity);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void loadCurrentRequest() {
        String email = Preferences.getInstance().getEmail();
        if(!email.equals("")) {
            ArrayList<UmberRequest> myRequests = DatabaseHelper.getInstance(mActivity).getMyActiveRequests(email);

            mRiderRequests = new ArrayList<>();
            mDriverRequests = new ArrayList<>();

            for(UmberRequest request : myRequests) {
                if(request.getEmail().equals(email))
                    mRiderRequests.add(request);
                else if(request.getDriverEmail().equals(email))
                    mDriverRequests.add(request);
            }

            loadAsRider();
            loadAsDriver();

        } else {
            Toast.makeText(mActivity, "You must login to view or make requests", Toast.LENGTH_SHORT).show();
            startActivity(LoginActivity.createIntent(mActivity));
            mActivity.finish();
        }
    }

    private void loadAsDriver() {
        if(!mDriverRequests.isEmpty())
            mRidersLayout.setVisibility(View.VISIBLE);
        else
            mRidersLayout.setVisibility(View.GONE);
    }

    private void loadAsRider() {
        if(!mDriverRequests.isEmpty()) {
            Marker myRequestPickUpMarker = mMap.addMarker(new MarkerOptions()
                            .position(mRiderRequests.get(0).getPickUpLatLng())
                            .title("Pick Up Spot")
                            .snippet(mRiderRequests.get(0).getPickUpLocation())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            );

            Marker myRequestDestMarker = mMap.addMarker(new MarkerOptions()
                            .position(mRiderRequests.get(0).getDestinationLatLng())
                            .title("Destination")
                            .snippet(mRiderRequests.get(0).getDestination())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            );

            updateMapCamera(mRiderRequests.get(0).getPickUpLatLng());

            if (mRiderRequests.get(0).isClaimed())
                getDriver(mRiderRequests.get(0));
            else
                mDriverLayout.setVisibility(View.GONE);
        } else {
            mDriverLayout.setVisibility(View.GONE);
        }

    }

    private void getDriver(final UmberRequest request) {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getDriver(request.getDriverEmail(), new VolleyRequestListener<DriverResponse>() {
            @Override
            public void onResponse(DriverResponse response) {
                response.saveResponse(mActivity);
                if (!response.getDrivers().isEmpty()) {
                    mDriver = response.getDrivers().get(0);
                    loadDriverLayout();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "Error, unable to find your driver. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDriverLayout() {
        mDriverLayout.setVisibility(View.VISIBLE);
        Glide.with(mActivity).load(mDriver.getImageUrl()).into(mDriverImage);
        mDriverName.setText(mDriver.getName());
        mDriverCarDescription.setText(mDriver.getCarDescription());
    }

    @OnClick(R.id.umber_contact_button)
    void contactDriver() {
        if(mDriver != null) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("sms:" + mDriver.getPhoneNumber()));
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("exit_on_sent", true);
            startActivityForResult(sendIntent, 0);
        }
    }

    @OnClick(R.id.umber_cancel_button)
    void cancelRequest() {
        if(!mRiderRequests.get(0).isStarted()) {
            ApiHelper helper = new ApiHelper(mActivity);
            helper.updateUmberRequestStatus(mRiderRequests.get(0), new VolleyRequestListener() {
                @Override
                public void onResponse(Object response) {
                    NotificationsHelper.sendCancelNotification(mRiderRequests.get(0));
                    mRiderRequests.remove(0);
                    if(mRiderRequests.get(0) != null)
                        loadAsRider();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, "Error, unable to cancel your request at this time. Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
