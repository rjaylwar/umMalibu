package com.parse.ummalibu.objects;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 9/24/15.
 */
public class UmLocation implements Parcelable {

    public UmLocation () {}

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
    private double mLat;

    @SerializedName(FieldNames.LONGITUDE)
    private double mLon;

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

    public String getFormattedTitle() {
        return mName + " - " + mAddress;
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

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double lon) {
        mLon = lon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public LatLng getLatLng() {
        return new LatLng(mLat, mLon);
    }

    protected UmLocation(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mAddress = in.readString();
        mImageUrl = in.readString();
        mType = in.readString();
        mLat = in.readDouble();
        mLon = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mImageUrl);
        dest.writeString(mType);
        dest.writeDouble(mLat);
        dest.writeDouble(mLon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UmLocation> CREATOR = new Parcelable.Creator<UmLocation>() {
        @Override
        public UmLocation createFromParcel(Parcel in) {
            return new UmLocation(in);
        }

        @Override
        public UmLocation[] newArray(int size) {
            return new UmLocation[size];
        }
    };

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(FieldNames.OBJECT_ID, mId);
        values.put(FieldNames.NAME, mName);
        values.put(FieldNames.ADDRESS, mAddress);
        values.put(FieldNames.IMAGE_URL, mImageUrl);
        values.put(FieldNames.TYPE, mType);
        values.put(FieldNames.LATITUDE, mLat);
        values.put(FieldNames.LONGITUDE, mLon);

        return values;
    }
}
