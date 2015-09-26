package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Objects.UmLocation;
import com.parse.ummalibu.Values.FieldNames;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/25/15.
 */
public class UmLocationsResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<UmLocation> mLocations;

    @Override
    public void saveResponse(Context context) {

    }
}
