package com.parse.ummalibu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ummalibu.ParseApplication;
import com.parse.ummalibu.R;
import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 10/22/15
 */
public abstract class YoutubeToolbarFragment extends Fragment implements BaseFragActivity.OnActivityBackPressedListener{

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = ParseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onActivityBackPressed() { }

    @Nullable
    @Bind(R.id.toolbar)
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
