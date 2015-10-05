package com.parse.ummalibu.fragments;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.parse.ummalibu.LocationsActivity;
import com.parse.ummalibu.R;
import com.parse.ummalibu.SearchLayout;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.objects.LatLngInterpolator;
import com.parse.ummalibu.objects.UmLocation;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.volley.VolleyRequestListener;

import java.util.HashSet;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/28/15
 */
public class UmberMapFragment extends Fragment {

    @Bind(R.id.search_pickup_layout) SearchLayout mSearchPickUpLayout;
    @Bind(R.id.search_destination_layout) SearchLayout mSearchDestLayout;

    private View mRoot;
    private AppCompatActivity mActivity;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Marker mMarker;
    private Marker mPickUpMarker;
    private ScheduledExecutorService mExecutor;
    private ProgressDialog mDialog;

    private LatLng mPickUpLocation;
    @Bind(R.id.search_pickup_location) EditText mPickUpLocationName;

    private LatLng mDestination;
    private String mDestinationName;

    private Handler mHandler;
    private boolean mKeepUpdating = false;
    private boolean mIsUpdating = false;

    private boolean mHasHappened = false;

    private UmberRequest mUmberRequest;

    private static final int PICKUP_LOCATIONS = 33;
    private static final int DESTINATION_LOCATIONS = 34;

    private HashSet<UmberRequest> mOpenRequests = new HashSet<>();
    private UmLocation mSelectedPickUpLocation;
    private UmLocation mSelectedDestLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.activity_umber, container, false);
        mActivity = (AppCompatActivity) getActivity();
        ButterKnife.bind(this, mRoot);

        mPickUpLocationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickUpLocationName.setCursorVisible(true);
                launchPickupSpotsList();
            }
        });

        ImageView clearButton = (ImageView) mRoot.findViewById(R.id.search_clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickUpLocationName.setText("");
                mPickUpLocationName.setCursorVisible(false);
            }
        });

        Button requestRideButton = (Button) mRoot.findViewById(R.id.umber_button);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            UmLocation location = data.getParcelableExtra(FieldNames.ADDRESS);

            if(requestCode == PICKUP_LOCATIONS)
                setSelectedPickUp(location);
            else
                setSelectedDestination(location);

        } else {
            mPickUpLocationName.setText("");
            mPickUpLocationName.setCursorVisible(false);
        }
    }

    private void setSelectedPickUp(UmLocation location) {
        mSelectedPickUpLocation = location;
        mSearchPickUpLayout.setLocationName(location.getFormattedTitle());
    }

    private void setSelectedDestination(UmLocation location) {
        mSelectedDestLocation = location;
        mSearchDestLayout.setLocationName(location.getFormattedTitle());
    }

    private void makeApiRequest() {
        ApiHelper helper = new ApiHelper(mActivity);
        helper.getUmberLocation(new VolleyRequestListener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                double lat;
                lat = response.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonPrimitive("latitude").getAsDouble();
                double lon;
                lon = response.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonPrimitive("longitude").getAsDouble();

                animateMarker(lat, lon);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void startUpdating() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (mKeepUpdating) {
                    try {
                        mIsUpdating = true;
                        Thread.sleep(3000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                makeApiRequest();
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

                if (!mKeepUpdating)
                    mIsUpdating = false;
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mKeepUpdating = false;
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     **/

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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

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

        if (mMarker == null) {
            mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(34.04063163, -118.69598329)));
            mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        }

        if (!mIsUpdating)
            startUpdating();

    }

    private void updateMapCameraOnce(Location location) {
        if (!mHasHappened) {
            updateMapCamera(location);
            mHasHappened = true;
        }
    }

    private void updateMapCamera(Location location) {
        updateMapCamera(location.getLatitude(), location.getLongitude());
    }

    private void updateMapCamera(double lat, double lon) {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15), 2800, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void animateMarker(Location location) {
        animateMarker(location.getLatitude(), location.getLongitude());
    }

    private void animateMarker(double lat, double lon) {

        animateMarkerToICS(mMarker, new LatLng(lat, lon), new LatLngInterpolator() {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lng = (b.longitude - a.longitude) * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        });
    }

    private void drawPinAndMoveCamera(final LatLng location, final Marker marker, boolean moveCamera, final Integer iconResource) {
        if (moveCamera) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15), 2800, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    drawPin(location, marker, iconResource);
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    private void drawPinAndMoveCamera(LatLng location, Marker marker, boolean moveCamera) {
        drawPinAndMoveCamera(location, marker, moveCamera, 0);
    }

    private void drawPin(LatLng location, Marker marker, Integer iconResource) {
        marker = mMap.addMarker(new MarkerOptions().position(location));

        if (iconResource != null && iconResource > 0)
            marker.setIcon(BitmapDescriptorFactory.fromResource(iconResource));
    }

    @Override
    public void onPause() {
        super.onPause();
        mKeepUpdating = false;
//        mExecutor.shutdownNow();
    }

    static void animateMarkerToICS(Marker marker, LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                return latLngInterpolator.interpolate(fraction, startValue, endValue);
            }
        };
        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(3000);
        animator.start();
    }

    private void makeUmberRequest() {

        if(mMap != null && mPickUpLocation != null) {
            mUmberRequest = new UmberRequest();

            mUmberRequest.setName(Preferences.getInstance().getName());
            mUmberRequest.setEmail(Preferences.getInstance().getEmail());

            mUmberRequest.setClaimed(false);
            mUmberRequest.setIsPickedUp(false);
            mUmberRequest.setPhoneNumber(Preferences.getInstance().getPhoneNumber());
            mUmberRequest.setRiderImageUrl(Preferences.getInstance().getImageUrl());

            Location myLocation = mMap.getMyLocation();

            mUmberRequest.setLongitude(myLocation.getLongitude());
            mUmberRequest.setLatitude(myLocation.getLatitude());

            mUmberRequest.setPickUpLocation(mPickUpLocationName.getText().toString());
            mUmberRequest.setPickupLat(mPickUpLocation.latitude);
            mUmberRequest.setPickupLong(mPickUpLocation.longitude);

            mUmberRequest.setDestination(mDestinationName);
            mUmberRequest.setDestinationLat(mDestination.latitude);
            mUmberRequest.setDestinationLong(mDestination.longitude);

            ApiHelper helper = new ApiHelper(mActivity);
            helper.makeUmberRequest(mUmberRequest, new VolleyRequestListener() {
                @Override
                public void onResponse(Object response) {
                    Toast.makeText(mActivity, "it worked!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStop() {
        super.onStop();
        mKeepUpdating = false;
//        mExecutor.shutdownNow();
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
//        mExecutor.shutdownNow();
    }
}
