package com.parse.ummalibu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ummalibu.R;
import com.parse.ummalibu.base.BaseActivity;
import com.parse.ummalibu.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/29/15
 */
public abstract class ToolbarFragment extends BaseFragment {

    @Nullable @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected BaseActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, root);
        if(mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
            //noinspection ConstantConditions
            mActivity.getSupportActionBar().setHomeButtonEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.onBackPressed();
                }
            });
        } else
            Log.d("Toolbar", "toolbar is null");

        fragOnCreateView(savedInstanceState);

        return root;
    }

    protected abstract int getLayoutId();

    protected abstract void fragOnCreateView(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
