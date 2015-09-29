package com.parse.ummalibu.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.parse.ummalibu.R;

/**
 * Created by rjaylward on 9/24/15.
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            Log.d("No toolbar here", e.toString());
        }
    }

    public abstract int getLayoutId();

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