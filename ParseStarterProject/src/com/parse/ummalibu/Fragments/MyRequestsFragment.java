package com.parse.ummalibu.Fragments;

import android.util.Log;

import com.android.volley.VolleyError;
import com.parse.ummalibu.Adapters.RequestListAdapter;
import com.parse.ummalibu.Api.ApiHelper;
import com.parse.ummalibu.Database.DatabaseHelper;
import com.parse.ummalibu.Objects.UmberRequest;
import com.parse.ummalibu.Responses.UmberRequestResponse;
import com.parse.ummalibu.Values.Constants;
import com.parse.ummalibu.Values.Preferences;
import com.parse.ummalibu.Volley.VolleyRequestListener;

/**
 * Created by rjaylward on 9/26/15.
 */
public class MyRequestsFragment extends AbsRequestListFragment {

    @Override
    protected void onRequestSelected(UmberRequest request) {

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
