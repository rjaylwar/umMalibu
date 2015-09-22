package com.parse.ummalibu.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Prayer implements Parcelable {

    @SerializedName(FieldNames.OBJECT_ID)
    public String mObjectId;

    @SerializedName(FieldNames.NAME)
    public String mName;

    @SerializedName(FieldNames.PRAYER_REQUEST)
    public String mPrayerRequest;

    public Date mDate;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrayerRequest() {
        return mPrayerRequest;
    }

    public void setPrayerRequest(String prayerRequest) {
        mPrayerRequest = prayerRequest;
    }

    protected Prayer(Parcel in) {
        mObjectId = in.readString();
        mName = in.readString();
        mPrayerRequest = in.readString();
        long tmpMDate = in.readLong();
        mDate = tmpMDate != -1 ? new Date(tmpMDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mObjectId);
        dest.writeString(mName);
        dest.writeString(mPrayerRequest);
        dest.writeLong(mDate != null ? mDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Prayer> CREATOR = new Parcelable.Creator<Prayer>() {
        @Override
        public Prayer createFromParcel(Parcel in) {
            return new Prayer(in);
        }

        @Override
        public Prayer[] newArray(int size) {
            return new Prayer[size];
        }
    };
}

