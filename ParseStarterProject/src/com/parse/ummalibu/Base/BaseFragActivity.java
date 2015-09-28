package com.parse.ummalibu.base;

import android.support.v4.app.Fragment;

import com.parse.ummalibu.R;

/**
 * Created by rjaylward on 9/22/15.
 */
public abstract class BaseFragActivity extends BaseActivity {

    private Fragment mFragment;
    private static final String FRAGMENT_TAG = "frag_tag";

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initLayout() {
        mFragment = getFragment();
        mFragment.setArguments(getIntent().getExtras());

        if(mFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root, mFragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    protected abstract Fragment getFragment();
}
