package com.parse.ummalibu.fragments;

import android.util.Log;

import com.android.volley.VolleyError;
import com.parse.ummalibu.TripSummaryActivity;
import com.parse.ummalibu.adapters.RequestListAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.responses.UmberRequestResponse;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.volley.VolleyRequestListener;

/**
 * Created by rjaylward on 9/26/15
 */
public class MyRequestsFragment extends AbsRequestListFragment {

    @Override
    protected void onRequestSelected(UmberRequest request) {
        startActivity(TripSummaryActivity.createIntent(mActivity, request));
    }

    @Override
    protected void databaseRequest() {
        DatabaseHelper helper = new DatabaseHelper(mActivity);
        mAdapter.setData(helper.getRequests(), RequestListAdapter.MY_REQUESTS);
    }

    @Override
    protected void apiRequest() {
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
    protected long getExpiration() {
        return Preferences.getInstance().getListData().getRequestsExpiration();
    }

    @Override
    protected boolean useColors() {
        return true;
    }
}
