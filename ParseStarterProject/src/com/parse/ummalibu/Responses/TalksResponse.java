package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Talk;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class TalksResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    private ArrayList<Talk> mTalks;

    @Override
    public void saveResponse(Context context) {

    }

    public ArrayList<Talk> getTalksListResult() {
        return mTalks;
    }

    public void setTalksListResult(ArrayList<Talk> talks) {
        mTalks = talks;
    }
}
