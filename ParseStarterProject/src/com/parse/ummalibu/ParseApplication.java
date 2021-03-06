package com.parse.ummalibu;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);
    //Parse.enableLocalDatastore(this);
    // Add your initialization code here
    Parse.initialize(this, "M8lsPPcaku0od8yzToKaHKR11TUFlsZuRPorClFt", "ovSmqOh3BFF0IrQu5zrhFHPxOsIQYA88bOHWEc42");

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
      
    // If you would like all objects to be private by default, remove this line.
    defaultACL.setPublicReadAccess(true);
    //defaultACL.setPublicWriteAccess(true);
    
    ParseACL.setDefaultACL(defaultACL, true);

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

  }
}
