package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.objects.UmLocation;
import com.parse.ummalibu.values.FieldNames;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/25/15
 */
public class UmLocationsResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<UmLocation> mLocations;

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        helper.addLocations(mLocations);
    }

    public ArrayList<UmLocation> getLocations() {
        return mLocations;
    }
}
