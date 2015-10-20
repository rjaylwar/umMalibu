package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import com.parse.ummalibu.adapters.UmberPagerAdapter;
import com.parse.ummalibu.base.ToolbarActivity;
import com.parse.ummalibu.helper.SlidingPaneHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/26/15
 */
public class RideShareActivity extends ToolbarActivity {

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.view_pager) ViewPager mViewPager;

    private SlidingPaneHelper mSlidingPaneHelper;

    public static Intent createIntent(Context context) {
        return new Intent(context, RideShareActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mSlidingPaneHelper = new SlidingPaneHelper(this, mToolbar, mNavigationView, mDrawerLayout);
        mSlidingPaneHelper.loadView();

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Requests"));
        mTabLayout.addTab(mTabLayout.newTab().setText("History"));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        UmberPagerAdapter adapter = new UmberPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ride_share;
    }

}