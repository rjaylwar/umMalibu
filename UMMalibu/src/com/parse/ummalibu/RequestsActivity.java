package com.parse.ummalibu;

import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.RequestListFragment;

/**
 * Created by rjaylward on 9/25/15.
 */
public class RequestsActivity extends BaseFragActivity {

    @Override
    protected Fragment getFragment() {
        return new RequestListFragment();
    }

}