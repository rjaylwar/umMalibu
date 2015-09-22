package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Prayer;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class PrayersResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    ArrayList<Prayer> mPrayers;

    @Override
    public void saveResponse(Context context) {

    }

    public ArrayList<Prayer> getPrayers() {
        return mPrayers;
    }

}
