package com.parse.ummalibu.helper;

import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.base.ToolbarActivity;
import com.parse.ummalibu.values.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rjaylward on 10/7/15
 */
public class SlidingPaneHelper {

    private ToolbarActivity mActivity;
    private NavigationView mNavigationView;
    private TextView mName;
    private TextView mEmail;
    private CircleImageView mProfileImage;

    public SlidingPaneHelper(ToolbarActivity activity, NavigationView navigationView) {
        mActivity = activity;
        mNavigationView = navigationView;
        createView();
    }

    private void createView() {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.header_layout, mNavigationView, false);

        mProfileImage = (CircleImageView) v.findViewById(R.id.header_profile_image);
        mEmail = (TextView) v.findViewById(R.id.header_email);
        mName = (TextView) v.findViewById(R.id.header_username);

        mNavigationView.addHeaderView(v);
    }

    public void loadView() {
        Glide.with(mActivity).load(Preferences.getInstance().getImageUrl()).into(mProfileImage);
        mEmail.setText(Preferences.getInstance().getEmail());
        mName.setText(Preferences.getInstance().getName());
    }

//    NavigationView mNavigationView;
//    ToolbarActivity mToolbarActivity;
//    DrawerLayout mDrawerLayout;
//    Toolbar mToolbar;
//    OnDrawerStateChangedListener mListener;
//
//    public SlidingPaneHelper(ToolbarActivity activity, Toolbar toolbar, DrawerLayout drawerLayout) {
//        mToolbarActivity = activity;
//        mToolbar = toolbar;
//        mDrawerLayout = drawerLayout;
//
//        setUp();
//    }
//
//    public void setUp() {
//        mNavigationView.setBackgroundColor(mToolbarActivity.getResources().getColor(R.color.um_dark_blue));
//        mNavigationView.setItemIconTintList(ColorStateList.valueOf(mToolbarActivity.getResources().getColor(R.color.white)));
//        mNavigationView.setItemTextColor(ColorStateList.valueOf(mToolbarActivity.getResources().getColor(R.color.white)));
//        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                if (!menuItem.isChecked())
//                    menuItem.setChecked(true);
//
//                switch (menuItem.getItemId()) {
//                    case R.id.menu_item_rideshare:
//                        if(!(mToolbarActivity instanceof RideShareActivity))
//                            mToolbarActivity.startActivity(RideShareActivity.createIntent(mToolbarActivity));
//                        return true;
//                    case R.id.menu_item_um:
//                        if(!(mToolbarActivity instanceof MainActivity))
//                            mToolbarActivity.startActivity(MainActivity.createIntent(mToolbarActivity));
//                        return true;
//                    case R.id.menu_item_settings:
//                        if(!(mToolbarActivity instanceof LoginActivity))
//                            mToolbarActivity.startActivity(LoginActivity.createIntent(mToolbarActivity));
//                        return true;
//                    default:
//                        return true;
//                }
//            }
//        });
//        initDrawer(mToolbar);
//    }
//
//    public void initDrawer(@NonNull Toolbar toolbar) {
//        if(mDrawerLayout != null) {
//            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mToolbarActivity, mDrawerLayout, toolbar, 0, 0) {
//                @Override
//                public void onDrawerClosed(View view) {
//                    if(mListener != null)
//                        mListener.onDrawerStateChanged(false);
//                }
//
//                @Override
//                public void onDrawerOpened(View drawerView) {
//                    if(mListener != null)
//                        mListener.onDrawerStateChanged(true);
//                }
//            };
//
//            mDrawerLayout.setDrawerListener(drawerToggle);
//            drawerToggle.syncState();
//        }
//    }
//
//    public void setOnDrawerStateChangedListener(OnDrawerStateChangedListener listener) {
//        mListener = listener;
//    }
//
//    public interface OnDrawerStateChangedListener {
//
//        void onDrawerStateChanged(boolean isOpen);
//
//    }

}
