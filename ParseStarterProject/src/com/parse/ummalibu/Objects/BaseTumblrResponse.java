package com.parse.ummalibu.objects;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;

/**
 * Created by rjaylward on 10/17/15
 */
public class BaseTumblrResponse implements ApiResponse {

    @SerializedName("meta")
    private TumblrStatus mStatus;

    @SerializedName("response")
    private TumblrResponse mResponse;

    public TumblrStatus getStatus() {
        return mStatus;
    }

    public TumblrResponse getResponse() {
        return mResponse;
    }

    @Override
    public void saveResponse(Context context) {

    }
}