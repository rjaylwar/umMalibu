package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.TumblrTalksFragment;

/**
 * Created by rjaylward on 10/15/15
 **/
public class TumblrTalksActivity extends BaseFragActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, TumblrTalksActivity.class);
    }

    @Override
    protected Fragment getFragment() {
        return TumblrTalksFragment.createFragment();
    }

}