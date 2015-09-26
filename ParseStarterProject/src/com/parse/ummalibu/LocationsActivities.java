package com.parse.ummalibu;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.parse.ummalibu.Adapters.LocationsListAdapter;
import com.parse.ummalibu.Api.ApiHelper;
import com.parse.ummalibu.Base.ToolbarActivity;
import com.parse.ummalibu.Responses.UmLocationsResponse;
import com.parse.ummalibu.Volley.VolleyRequestListener;

/**
 * Created by rjaylward on 9/25/15.
 */
public class LocationsActivities extends ToolbarActivity {

    private RecyclerView mRecyclerView;
    private LocationsListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locations_list);
    }

    public void getLocations() {
        ApiHelper helper = new ApiHelper(this);
        helper.getUmberLocation(new VolleyRequestListener<UmLocationsResponse>() {
            @Override
            public void onResponse(UmLocationsResponse response) {
                response.saveResponse(LocationsActivities.this);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get Locations Request Error", error.getMessage());
            }
        });
    }
}
