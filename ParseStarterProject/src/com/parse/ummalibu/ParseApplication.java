package com.parse.ummalibu;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.instabug.library.Instabug;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.ummalibu.values.Preferences;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class ParseApplication extends Application {

    private RefWatcher mRefWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        ParseApplication application = (ParseApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Preferences.initialize(getApplicationContext());

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);
        //Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        ParseUser.enableAutomaticUser();

        Instabug.initialize(this, getString(R.string.instabug_key));
        mRefWatcher = LeakCanary.install(this);
        subscribeToChannels();

    }

    private void subscribeToChannels() {
        ParsePush.subscribeInBackground("global", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParsePush.subscribeInBackground("android", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParsePush.subscribeInBackground("umber", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Parse Notifications","umber");
                } else {
                    Log.e("Parse Notifications", "failed to subscribe for push", e);
                }
            }
        });
    }
}
