package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Database.DatabaseHelper;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Notification;

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
