package com.parse.ummalibu.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Notification implements Parcelable {

    @SerializedName(FieldNames.OBJECT_ID)
    public String mObjectId;

    @SerializedName(FieldNames.ALERT)
    public String mAlert;

    public Notification() {};

    public Date mCreatedAt;

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getAlert() {
        return mAlert;
    }

    public void setAlert(String alert) {
        mAlert = alert;
    }

    protected Notification(Parcel in) {
        mObjectId = in.readString();
        mAlert = in.readString();
        long tmpMCreatedAt = in.readLong();
        mCreatedAt = tmpMCreatedAt != -1 ? new Date(tmpMCreatedAt) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mObjectId);
        dest.writeString(mAlert);
        dest.writeLong(mCreatedAt != null ? mCreatedAt.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

}

