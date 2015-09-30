package com.parse.ummalibu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ummalibu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/29/15
 */
public abstract class ToolbarFragment extends Fragment {

    @Nullable @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected AppCompatActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
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

        }

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
