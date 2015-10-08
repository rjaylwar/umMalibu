package com.parse.ummalibu.api;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.ummalibu.objects.UmberRequest;

/**
 * Created by rjaylward on 9/29/15
 */
public class NotificationsHelper {

    static final String RIDER_ = "rider_";
    static final String DRIVER_ = "driver_";

    public static void subscribeAsRider(String objectId) {
        ParsePush.subscribeInBackground(RIDER_ + objectId, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Rider channel", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("Rider channel", "failed to subscribe for push", e);
                }
            }
        });
    }

    public static void subscribeAsDriver(String objectId) {
        ParsePush.subscribeInBackground(DRIVER_ + objectId, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Driver channel", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("Driver channel", "failed to subscribe for push", e);
                }
            }
        });
    }

    public static void sendUnclaimNotification(UmberRequest request) {
        ParsePush push = new ParsePush();
        push.setChannel(RIDER_ + request.getObjectId());
        push.setMessage("Your request was unclaimed, it will be put back in the list of open requests");
        push.sendInBackground();
    }

    public static void sendCancelNotification(UmberRequest request) {
        ParsePush push = new ParsePush();
        push.setChannel(DRIVER_ + request.getObjectId());
        push.setMessage(request.getName() + " canceled their ride request");
        push.sendInBackground();
    }

    public static void sendNewRequestNotification(UmberRequest request) {
        ParsePush push = new ParsePush();
        push.setChannel("umber");
        push.setMessage("A new request was made for a ride to " + request.getDestination() + " was posted.");
        push.sendInBackground();
    }

    public static void sendStartedNotification(UmberRequest request) {
        ParsePush push = new ParsePush();
        push.setChannel(RIDER_ + request.getObjectId());
        push.setMessage("Your ride is on its way!");
        push.sendInBackground();
    }

    public static void sendArrivedNotification(UmberRequest request) {
        ParsePush push = new ParsePush();
        push.setChannel(RIDER_ + request.getObjectId());
        push.setMessage("Your ride is waiting for you at " + request.getDestination());
        push.sendInBackground();
    }
}
