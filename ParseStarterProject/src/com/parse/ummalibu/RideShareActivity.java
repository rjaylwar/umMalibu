package com.parse.ummalibu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.parse.ummalibu.Adapters.UmberPagerAdapter;
import com.parse.ummalibu.Base.ToolbarActivity;

/**
 * Created by rjaylward on 9/26/15.
 */
public class RideShareActivity extends ToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        UmberPagerAdapter adapter = new UmberPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
