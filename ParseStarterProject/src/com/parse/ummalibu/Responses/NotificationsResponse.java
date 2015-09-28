package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Notification;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class NotificationsResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    ArrayList<Notification> mNotifications;

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addNotifications(mNotifications);
    }

    public ArrayList<Notification> getNotifications() {
        return mNotifications;
    }
}
