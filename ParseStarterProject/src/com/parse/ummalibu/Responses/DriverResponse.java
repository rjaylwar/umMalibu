package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Driver;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class DriverResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<Driver> mDrivers;

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        helper.addDriver(mDrivers.get(0));
    }

    public ArrayList<Driver> getDrivers() {
        return mDrivers;
    }

}
