package com.parse.ummalibu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.instabug.wrapper.support.activity.InstabugFragmentActivity;

public class AboutUMActivity extends InstabugFragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_um);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbar.setTitle("About UM");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        Boolean mapViewEnabled = true;

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        if (mapViewEnabled) {
            ImageView mapImage = (ImageView) findViewById(R.id.mapimg);
            mapImage.setVisibility(ImageView.GONE);
            mMap.addMarker(new MarkerOptions().position(new LatLng(34.040599, -118.696172)).title("UM - Malibu Pres"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.040599, -118.696172), 15));

        } else {
            LinearLayout mapFragmentLayout = (LinearLayout) findViewById(R.id.mapFragmentLayout);
            mapFragmentLayout.setVisibility(LinearLayout.GONE);
        }

        //setUpMap();
        //setUpMapIfNeeded();

        getSupportFragmentManager().findFragmentById(R.id.map).setRetainInstance(true);

        addListenerOnButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(34.040599, -118.696172)).title("UM - Malibu Pres"));

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(34.040599, -118.696172));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        Button navButton = (Button) findViewById(R.id.about_directions_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=34.040599,-118.696172"));
                startActivity(intent);
            }
        });

        Button shareButton = (Button) findViewById(R.id.about_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Come check out UM on tuesday nights at 8pm located at Malibu Presbyterian Church (http://maps.google.com/maps?daddr=34.040599,-118.696172)</p>"));
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap = null;
    }
}
