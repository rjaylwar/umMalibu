package com.parse.ummalibu.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rjaylward on 9/22/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        initLayout();
    }

    public abstract int getLayoutId();

    public abstract void initLayout();
}
