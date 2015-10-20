package com.parse.ummalibu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.base.ToolbarActivity;
import com.parse.ummalibu.helper.SlidingPaneHelper;
import com.parse.ummalibu.objects.Driver;
import com.parse.ummalibu.responses.DriverResponse;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rjaylward on 10/6/15
 */
public class LoginActivity extends ToolbarActivity {

    @Bind(R.id.login_circle_image)
    CircleImageView mCircleImageView;

    @Bind(R.id.login_name_input_layout)
    TextInputLayout mNameLayout;
    @Bind(R.id.login_email_input_layout)
    TextInputLayout mEmailLayout;
    @Bind(R.id.login_phone_input_layout)
    TextInputLayout mPhoneLayout;
    @Bind(R.id.login_image_url_input_layout)
    TextInputLayout mImageUrlLayout;

    @Bind(R.id.login_car_input_layout)
    TextInputLayout mCarLayout;
    @Bind(R.id.login_mpg_input_layout)
    TextInputLayout mMpgLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    private Driver mDriver = new Driver();
    private ProgressDialog mProgressDialog;
    private SlidingPaneHelper mSlidingPaneHelper;

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setUpSlidingPanel();
        setValues();
    }

    private void setUpSlidingPanel() {

        mSlidingPaneHelper = new SlidingPaneHelper(this, mToolbar, mNavigationView, mDrawerLayout);
        mSlidingPaneHelper.loadView();
        mSlidingPaneHelper.setOnNavDrawerToggledListener(new SlidingPaneHelper.OnNavDrawerToggledListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View view) { }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_login_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setValues() {
        mNameLayout.getEditText().setText(Preferences.getInstance().getName());
        mEmailLayout.getEditText().setText(Preferences.getInstance().getEmail());
        mPhoneLayout.getEditText().setText(Preferences.getInstance().getPhoneNumber());

        mImageUrlLayout.getEditText().setText(Preferences.getInstance().getImageUrl());
        Glide.with(this).load(Preferences.getInstance().getImageUrl()).into(mCircleImageView);
        mImageUrlLayout.getEditText().addTextChangedListener(mTextWatcher);

        mCarLayout.getEditText().setText(Preferences.getInstance().getCarDescription());
        mMpgLayout.getEditText().setText(String.valueOf(Preferences.getInstance().getMpg()));
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            //TODO see if there is a better way to do this
            if(editable.toString().contains(".jpeg") || editable.toString().contains(".JPEG") ||
                    editable.toString().contains(".jpg") || editable.toString().contains(".JPG") ||
                    editable.toString().contains(".png") || editable.toString().contains(".PNG") ||
                    editable.toString().contains(".gif") || editable.toString().contains(".GIF") ||
                    editable.toString().contains(".tif") || editable.toString().contains(".TIF") ||
                    editable.toString().contains(".tiff") || editable.toString().contains(".TIFF")) {
                Glide.with(LoginActivity.this).load(editable.toString()).into(mCircleImageView);
            }
        }
    };
//
    private void save() {

        if(!mNameLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setName(mNameLayout.getEditText().getText().toString());
            mDriver.setName(mNameLayout.getEditText().getText().toString());
        }

        if(!mEmailLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setEmail(mEmailLayout.getEditText().getText().toString());
            mDriver.setEmail(mEmailLayout.getEditText().getText().toString());
        }

        if(!mPhoneLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setPhoneNumber(mPhoneLayout.getEditText().getText().toString());
            mDriver.setPhoneNumber(mPhoneLayout.getEditText().getText().toString());
        }

        if(!mImageUrlLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setImageUrl(mImageUrlLayout.getEditText().getText().toString());
            mDriver.setImageUrl(mImageUrlLayout.getEditText().getText().toString());
        }

        if(!mCarLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setCarDescription(mCarLayout.getEditText().getText().toString());
            mDriver.setCarDescription(mCarLayout.getEditText().getText().toString());
        }

        int mpg;
        try {
            mpg = Integer.valueOf(mMpgLayout.getEditText().getText().toString());
        } catch (Exception e) {
            mpg = 20;
        }

        if(!mMpgLayout.getEditText().getText().toString().isEmpty()) {
            Preferences.getInstance().setMpg(mpg > 0 ? mpg : 20);
            mDriver.setMpg(mpg);
        }

        if(!mDriver.getCarDescription().equals("")) {
            final ApiHelper helper = new ApiHelper(this);
            checkForDriver(helper);
        }

        mSlidingPaneHelper.loadView();
    }

    private void checkForDriver(final ApiHelper helper) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.saving));
        mProgressDialog.show();

        helper.getDriver(Preferences.getInstance().getEmail(), new VolleyRequestListener<DriverResponse>() {
            @Override
            public void onResponse(DriverResponse response) {
                if(response.getDrivers().isEmpty())
                    createDriver(helper);
                else {
                    mDriver.setObjectId(response.getDrivers().get(0).getObjectId());
                    updateDriver(helper);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get Driver Error", error.toString());
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "An error occurred updating your information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createDriver(ApiHelper helper) {
        helper.createDriver(mDriver, new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                Log.d("Driver Response", response.toString());
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Your info has been saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Preferences.getInstance().setCarDescription("");
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "An error occurred updating your information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDriver(ApiHelper helper) {
        helper.updateDriver(mDriver, new VolleyRequestListener() {
            @Override
            public void onResponse(Object response) {
                Log.d("Driver Response", response.toString());
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Your info has been updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Preferences.getInstance().setCarDescription("");
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "An error occurred updating your information", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
