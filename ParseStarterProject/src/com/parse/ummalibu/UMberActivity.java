package com.parse.ummalibu;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.base.ToolbarActivity;
import com.parse.ummalibu.objects.LatLngInterpolator;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.volley.VolleyRequestListener;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by rjaylward on 9/22/15.
 */
public class UMberActivity extends ToolbarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Marker mMarker;
    private Marker mPickUpMarker;
    private ScheduledExecutorService mExecutor;
    private ProgressDialog mDialog;

    private LatLng mPickUpLocation;
    private EditText mPickUpLocationName;

    private LatLng mDestination;
    private String mDestinationName;

    private Handler mHandler;
    private boolean mKeepUpdating = true;
    private boolean mIsUpdating = false;

    private boolean mHasHappened = false;

    private UmberRequest mUmberRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("UMBER");

        mPickUpLocationName = (EditText) findViewById(R.id.search_pickup_location);
        mPickUpLocationName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || (actionId == EditorInfo.IME_NULL && event != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    mPickUpLocationName.setCursorVisible(false);
                    return onLocationEntered();
                } else
                    return false;
            }
        });
        mPickUpLocationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickUpLocationName.setCursorVisible(true);
            }
        });

        ImageView clearButton = (ImageView) findViewById(R.id.search_clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickUpLocationName.setText("");
                mPickUpLocationName.setCursorVisible(false);
                hideKeyboard();
            }
        });

        Button requestRideButton = (Button) findViewById(R.id.umber_request_button);
        requestRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeUmberRequest();
            }
        });

        mHandler = new Handler();

        setUpMapIfNeeded();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_umber;
    }

    private void makeApiRequest() {
        ApiHelper helper = new ApiHelper(this);
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
    protected void onResume() {
        super.onResume();
        mKeepUpdating = true;
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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            View mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getView();

            //noinspection ResourceType
            View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);

            // and next place it, for example, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 30, 30);
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
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        try {
            mLocationManager.requestLocationUpdates(bestProvider, minTime, minDistance, mLocationListener);
        } catch (SecurityException e) {
            // do something
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
    protected void onDestroy() {
        super.onDestroy();
        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (SecurityException e) {
            // something
        }

        mKeepUpdating = false;
//        mExecutor.shutdownNow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mKeepUpdating = false;
//        mExecutor.shutdownNow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mKeepUpdating = false;
//        mExecutor.shutdownNow();
    }



    private void showProgressDialog(String message) {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(message);
        mDialog.show();
    }

    private void hideProgressDialog() {
        mDialog.hide();
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

    private boolean onLocationEntered() {
        String entry = mPickUpLocationName.getText().toString();
        if(entry.length() > 0) {
            hideKeyboard();
            new GeocodeLookup().execute(entry);
            return true;
        }
        else {
            return false;
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void makeUmberRequest() {

        if(mMap != null && mPickUpLocation != null) {
            mUmberRequest = new UmberRequest();

            mUmberRequest.setName("RJ Test");

            mUmberRequest.setClaimed(false);
            mUmberRequest.setIsPickedUp(false);
            mUmberRequest.setPhoneNumber(Constants.TEST_PHONE_NUMBER);
            mUmberRequest.setRiderImageUrl(Constants.TEST_IMAGE_URL);

            Location myLocation = mMap.getMyLocation();

            mUmberRequest.setLongitude(myLocation.getLongitude());
            mUmberRequest.setLatitude(myLocation.getLatitude());

            mUmberRequest.setPickUpLocation(mPickUpLocationName.getText().toString());
            mUmberRequest.setPickupLat(mPickUpLocation.latitude);
            mUmberRequest.setPickupLong(mPickUpLocation.longitude);

            mUmberRequest.setDestination("UM");
            mUmberRequest.setDestinationLat(34.040599);
            mUmberRequest.setDestinationLong(-118.696172);

            final Context context = this;

            ApiHelper helper = new ApiHelper(this);
            helper.makeUmberRequest(mUmberRequest, new VolleyRequestListener() {
                @Override
                public void onResponse(Object response) {
                    Toast.makeText(context, "it worked!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            if(mMap != null)
                Toast.makeText(this, "Please set a pickup location", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error, please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private class GeocodeLookup extends AsyncTask<String, Void, Address> {

        @Override
        protected void onPreExecute() {
            showProgressDialog("Looking up location");
        }

        @Override
        protected Address doInBackground(String... params) {
            try {
                Geocoder geocoder = new Geocoder(getApplicationContext());
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
                else
                    formattedAddressLine = String.format("%s, %s", addressLine0, address.getAddressLine(1));

                mPickUpLocationName.setText(formattedAddressLine);
                mPickUpLocationName.clearFocus();

                //Set location on the map
                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                if(mPickUpMarker != null)
                    mPickUpMarker.remove();

                mPickUpLocation = location;
                drawPinAndMoveCamera(location, mPickUpMarker, true);

            } else
                Toast.makeText(getApplicationContext(), "error not found", Toast.LENGTH_SHORT).show();
        }

    }
}

