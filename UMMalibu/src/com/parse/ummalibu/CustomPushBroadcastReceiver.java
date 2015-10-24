package com.parse.ummalibu;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rjaylward on 12/21/14
 */
public class CustomPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    private boolean mRideRequest;

    @Override
    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        Intent i;
        if(mRideRequest)
            i = new Intent(context, RideShareActivity.class);
        else
            i = new Intent(context, NotificationsListActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        JSONObject pushData = this.getParsePushData(intent);
        String title = "";
        try {
            title = pushData.optString("title");
        } catch (Exception e) {
            Log.e("Json Exception", e.toString());
        }

        if(title.toLowerCase().contains("request") || title.toLowerCase().contains("ride") && !title.toLowerCase().contains("prayer"))
            mRideRequest = true;

        return super.getNotification(context, intent);
    }

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        return super.getActivity(context, intent);

    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);

    }

    private JSONObject getParsePushData(Intent intent) {
        try {
            return new JSONObject(intent.getStringExtra("com.parse.Data"));
        } catch (JSONException var3) {
            return null;
        }
    }
}
