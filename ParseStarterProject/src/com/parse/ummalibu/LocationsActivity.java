package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.LocationsFragment;
import com.parse.ummalibu.values.Constants;

import java.security.InvalidParameterException;

/**
 * Created by rjaylward on 9/25/15
 */
public class LocationsActivity extends BaseFragActivity {

    public static final int PICKUP = 21;
    public static final int DESTINATION = 22;

    public static Intent createIntent(Context context, int listType) {
        if(listType != PICKUP && listType != DESTINATION)
            throw new InvalidParameterException();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.LOCATION, listType);
        return new Intent(context, LocationsActivity.class);
    }

    @Override
    protected Fragment getFragment() {
        return LocationsFragment.create();
    }
}