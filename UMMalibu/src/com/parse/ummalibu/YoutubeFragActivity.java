package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.parse.ummalibu.base.BaseFragActivity;
import com.parse.ummalibu.fragments.WorshipYoutubeFragment;

/**
 * Created by rjaylward on 10/23/15
 */
public class YoutubeFragActivity extends BaseFragActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, YoutubeFragActivity.class);
    }

    @Override
    protected Fragment getFragment() {
        return WorshipYoutubeFragment.createFragment();
    }

}
