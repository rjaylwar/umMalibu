package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.UmberRequest;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class UmberRequestResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<UmberRequest> mUmberRequests = new ArrayList<>();

    @Override
    public void saveResponse(Context context) {
        if(mUmberRequests.size() > 0) {
            DatabaseHelper helper = new DatabaseHelper(context);
            helper.addRequests(mUmberRequests);
        }
    }

    public ArrayList<UmberRequest> getRequests() {
        return mUmberRequests;
    }
}