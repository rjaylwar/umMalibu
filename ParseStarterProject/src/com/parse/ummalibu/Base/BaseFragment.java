package com.parse.ummalibu.base;

import android.support.v4.app.Fragment;

import com.parse.ummalibu.ParseApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by rjaylward on 10/7/15
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = ParseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}