package com.parse.ummalibu.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.parse.ummalibu.R;

/**
 * Created by rjaylward on 9/24/15
 */
public abstract class ToolbarActivity extends BaseActivity {

    public Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        try {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        } catch (NullPointerException e) {
            Log.d("No toolbar here", e.toString());
        }
    }

    public abstract int getLayoutId();

    @Override
    public void initLayout() { }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home :
                //Calls this class's onBackPressed, which includes the listener and postFinish stuff
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}