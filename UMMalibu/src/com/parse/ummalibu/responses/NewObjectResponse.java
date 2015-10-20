package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 10/8/15
 */
public class NewObjectResponse implements ApiResponse {

    @SerializedName(FieldNames.OBJECT_ID)
    String mObjectId;

    @Override
    public void saveResponse(Context context) { }

    public String getObjectId() {
        return mObjectId;
    }
}
