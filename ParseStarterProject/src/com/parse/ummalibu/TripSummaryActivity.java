package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.SummaryFragment;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 9/27/15
 */
public class TripSummaryActivity extends BaseFragActivity {

    public static Intent createIntent(Context context, UmberRequest request) {
        Intent intent = new Intent(context, TripSummaryActivity.class);
        intent.putExtra(FieldNames.UMBER_REQUEST, request);

        return intent;
    }

    @Override
    protected Fragment getFragment() {
        return SummaryFragment.makeFragment((UmberRequest) getIntent().getParcelableExtra(FieldNames.UMBER_REQUEST));
    }

}