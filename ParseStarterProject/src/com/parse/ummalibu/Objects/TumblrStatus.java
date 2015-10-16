package com.parse.ummalibu.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjaylward on 10/15/15
 */
public class TumblrStatus {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("msg")
    private String mMessage;

    public String getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

}
