package com.parse.ummalibu.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Event implements Parcelable {

    @SerializedName(FieldNames.TITLE)
    public String mTitle;

    @SerializedName(FieldNames.TUMBLR_URL)
    public String mLink;

    @SerializedName(FieldNames.DATE)
    public String mDateString;

    @SerializedName(FieldNames.COST)
    public String mCost;

    @SerializedName(FieldNames.DESCRIPTION_LONG)
    public String mDescriptionLong;

    @SerializedName(FieldNames.DESCRIPTION_SHORT)
    public String mDescriptionShort;

    @SerializedName(FieldNames.IS_OVER)
    public boolean mIsOver;

    @SerializedName(FieldNames.OBJECT_ID)
    public String mObjectId;

    public String mImageUrl;

    public Date mDate;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getEventTitle() {
        return mTitle;
    }

    public void setEventTitle(String title) {
        mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getDateString() {
        return mDateString;
    }

    public void setDateString(String dateString) {
        mDateString = dateString;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public String getDescriptionLong() {
        return mDescriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        mDescriptionLong = descriptionLong;
    }

    public String getDescriptionShort() {
        return mDescriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        mDescriptionShort = descriptionShort;
    }

    public boolean isOver() {
        return mIsOver;
    }

    public void setIsOver(boolean isOver) {
        mIsOver = isOver;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    protected Event(Parcel in) {
        mTitle = in.readString();
        mLink = in.readString();
        mDateString = in.readString();
        mCost = in.readString();
        mDescriptionLong = in.readString();
        mDescriptionShort = in.readString();
        mIsOver = in.readByte() != 0x00;
        mObjectId = in.readString();
        mImageUrl = in.readString();
        long tmpMDate = in.readLong();
        mDate = tmpMDate != -1 ? new Date(tmpMDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mLink);
        dest.writeString(mDateString);
        dest.writeString(mCost);
        dest.writeString(mDescriptionLong);
        dest.writeString(mDescriptionShort);
        dest.writeByte((byte) (mIsOver ? 0x01 : 0x00));
        dest.writeString(mObjectId);
        dest.writeString(mImageUrl);
        dest.writeLong(mDate != null ? mDate.getTime() : -1L);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}

