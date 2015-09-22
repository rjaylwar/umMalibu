package com.parse.ummalibu.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Talk implements Parcelable {

    @SerializedName(FieldNames.TITLE)
    public String mTitle;

    @SerializedName(FieldNames.MEDIA_PLAYER_TEXT)
    public String mSubtitle;

    @SerializedName(FieldNames.IMAGE_URL)
    public String mImageUrl;

    @SerializedName(FieldNames.AUDIO_URL)
    public String mAudioUrl;

    @SerializedName(FieldNames.DIRECT_LINK)
    public String mBaseUrl;

    public String mDescription;

    @SerializedName(FieldNames.OBJECT_ID)
    public String mId;

    @SerializedName(FieldNames.SOURCE)
    public String mSource;

    @SerializedName(FieldNames.SERIES)
    public String mSeries;

    @SerializedName(FieldNames.ORIGINAL_LINK)
    public String mOriginalLink;

    public String mSeriesImageUrl;

    public Date mCreatedAt;

    public Talk() {

    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public Long getCreateAtLong() {
        return mCreatedAt.getTime();
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public void setCreatedAt(Long createdAtLong) {
        mCreatedAt.setTime(createdAtLong);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        this.mSubtitle = subtitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return mAudioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.mAudioUrl = audioUrl;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public String getSeries() {
        return mSeries;
    }

    public void setSeries(String mSeries) {
        this.mSeries = mSeries;
    }

    public String getOriginalLink() {
        return mOriginalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.mOriginalLink = originalLink;
    }

    public String getSeriesImageUrl() {
        return mSeriesImageUrl;
    }

    public void setSeriesImageUrl(String directLink) {
        this.mSeriesImageUrl = directLink;
    }

    protected Talk(Parcel in) {
        mTitle = in.readString();
        mSubtitle = in.readString();
        mImageUrl = in.readString();
        mAudioUrl = in.readString();
        mBaseUrl = in.readString();
        mDescription = in.readString();
        mId = in.readString();
        mSource = in.readString();
        mSeries = in.readString();
        mOriginalLink = in.readString();
        mSeriesImageUrl = in.readString();
        long tmpMDate = in.readLong();
        mCreatedAt = tmpMDate != -1 ? new Date(tmpMDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mSubtitle);
        dest.writeString(mImageUrl);
        dest.writeString(mAudioUrl);
        dest.writeString(mBaseUrl);
        dest.writeString(mDescription);
        dest.writeString(mId);
        dest.writeString(mSource);
        dest.writeString(mSeries);
        dest.writeString(mOriginalLink);
        dest.writeString(mSeriesImageUrl);
        dest.writeLong(mCreatedAt != null ? mCreatedAt.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Talk> CREATOR = new Parcelable.Creator<Talk>() {
        @Override
        public Talk createFromParcel(Parcel in) {
            return new Talk(in);
        }

        @Override
        public Talk[] newArray(int size) {
            return new Talk[size];
        }
    };
}

