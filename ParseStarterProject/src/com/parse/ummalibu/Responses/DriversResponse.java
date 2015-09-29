package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.objects.Driver;
import com.parse.ummalibu.values.FieldNames;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/28/15.
 */
public class DriversResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<Driver> mDrivers;

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addDrivers(mDrivers);
    }

    public ArrayList<Driver> getDrivers() {
        return mDrivers;
    }
}
