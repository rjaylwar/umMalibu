package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Database.DatabaseHelper;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.UmberRequest;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class UmberRequestResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<UmberRequest> mUmberRequests;

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addRequests(mUmberRequests);
    }

    public ArrayList<UmberRequest> getRequests() {
        return mUmberRequests;
    }
}