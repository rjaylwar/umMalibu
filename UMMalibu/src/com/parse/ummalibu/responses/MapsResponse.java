package com.parse.ummalibu.responses;

import android.content.Context;

import com.parse.ummalibu.database.ApiResponse;

/**
 * Created by rjaylward on 9/28/15.
 */
public class MapsResponse implements ApiResponse {

    private String mDistance;
    private String mTripTime;
    private String mPath;

    // meters
    private double mDistanceValue;

    @Override
    public void saveResponse(Context context) {

    }

    public double getDistanceValue() {
        return mDistanceValue;
    }

    public double getDistanceMiles() {
        return mDistanceValue/1609.34;
    }

    public void setDistanceValue(double distanceValue) {
        mDistanceValue = distanceValue;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getTripTime() {
        return mTripTime;
    }

    public void setTripTime(String tripTime) {
        mTripTime = tripTime;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
