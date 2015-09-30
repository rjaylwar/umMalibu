package com.parse.ummalibu.fragments;

import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.values.Preferences;

/**
 * Created by rjaylward on 9/29/15
 */
public class HistoryRequestsListFragment extends AbsRequestListFragment {
    @Override
    protected void onRequestSelected(UmberRequest request) {

    }

    @Override
    protected void databaseRequest() {

    }

    @Override
    protected void apiRequest() {

    }

    @Override
    protected long getExpiration() {
        return Preferences.getInstance().getListData().getRequestsExpiration();
    }
}
