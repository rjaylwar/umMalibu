package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.values.Preferences;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/6/15
 */
public class MyUmberRequestResponse implements ApiResponse {
    @SerializedName(FieldNames.RESULTS)
    private ArrayList<UmberRequest> mUmberRequests = new ArrayList<>();

    private ArrayList<UmberRequest> mAsDriver = new ArrayList<>();
    private ArrayList<UmberRequest> mMyRequests = new ArrayList<>();

    @Override
    public void saveResponse(Context context) {
        if(mUmberRequests.size() > 0) {
            DatabaseHelper helper = new DatabaseHelper(context);
            helper.addRequests(mUmberRequests);
        }
    }

    public ArrayList<UmberRequest> getMyRequests() {
        if(!mUmberRequests.isEmpty() && mAsDriver.isEmpty() && mMyRequests.isEmpty())
            sort();

        return mMyRequests;
    }

    public ArrayList<UmberRequest> getRequestToDrive() {
        if(!mUmberRequests.isEmpty() && mAsDriver.isEmpty() && mMyRequests.isEmpty())
            sort();

        return mAsDriver;
    }

    public void sort() {
        String email = Preferences.getInstance().getEmail();
        for(UmberRequest request : mUmberRequests)
            if(request.getEmail().equals(email))
                mMyRequests.add(request);
            else if(request.getDriverEmail().equals(email))
                mAsDriver.add(request);
    }

}
