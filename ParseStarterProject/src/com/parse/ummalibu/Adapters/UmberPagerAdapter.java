package com.parse.ummalibu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.parse.ummalibu.fragments.MyRequestsFragment;
import com.parse.ummalibu.fragments.RequestListFragment;
import com.parse.ummalibu.fragments.UmberMapFragment;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/26/15.
 */
public class UmberPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int mTabs;

    public UmberPagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new UmberMapFragment();
                break;
            case 1:
                fragment = new RequestListFragment();
                break;
            case 2:
                fragment = new MyRequestsFragment();
                break;
            default:
                throw new IllegalArgumentException("tab ordinal not found");
        }

        mFragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabs;
    }

    public ArrayList<Fragment> getFragments() {
        return mFragments;
    }


}