package com.parse.ummalibu;

import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.SummaryFragment;

/**
 * Created by rjaylward on 9/27/15.
 */
public class TripSummaryActivity extends BaseFragActivity {

    @Override
    protected Fragment getFragment() {
        return new SummaryFragment();
    }

}