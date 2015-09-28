package com.parse.ummalibu.objects;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 9/24/15.
 */
public class UmLocation {

    @SerializedName(FieldNames.OBJECT_ID)
    private String mId;

    @SerializedName(FieldNames.NAME)
    private String mName;

    @SerializedName(FieldNames.ADDRESS)
    private String mAddress;

    @SerializedName(FieldNames.IMAGE_URL)
    private String mImageUrl;

    @SerializedName(FieldNames.TYPE)
    private String mType;

    @SerializedName(FieldNames.LATITUDE)
    private long mLat;

    @SerializedName(FieldNames.LONGITUDE)
    private long mLon;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public long getLat() {
        return mLat;
    }

    public void setLat(long lat) {
        mLat = lat;
    }

    public long getLon() {
        return mLon;
    }

    public void setLon(long lon) {
        mLon = lon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
