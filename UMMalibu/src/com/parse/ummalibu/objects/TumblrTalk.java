package com.parse.ummalibu.objects;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.util.Util;
import com.parse.ummalibu.values.FieldNames;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rjaylward on 10/1/15
 */
public class TumblrTalk implements Parcelable {

    public TumblrTalk() {}

    // TUMBLR VIDEO VIEWS
    @SerializedName("type")
    public String mType;
    @SerializedName("thumbnail_url")
    public String mThumbnailUrl;
    @SerializedName("permalink_url")
    public String mPermalinkUrl;

    public String getType() {
        return mType;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public String getVideoUrl() {
        if(mPermalinkUrl.contains("www.youtube.com/watch?v=")) {
            mPermalinkUrl = mPermalinkUrl.replace("watch?v=", "embed/");
        } else if(mPermalinkUrl.contains("vimeo.com/") && !mPermalinkUrl.contains("player")) {
            mPermalinkUrl = mPermalinkUrl.replace("vimeo.com", "player.vimeo.com/video");
        }
        return mPermalinkUrl;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        mPermalinkUrl = permalinkUrl;
    }

    // TUMBLR AUDIO TALKS
    @SerializedName("artist")
    public String mTitle;

    @SerializedName("caption")
    public String mSubtitle;

    @SerializedName("album_art")
    public String mImageUrl;

    @SerializedName("audio_url")
    public String mAudioUrl;

    @SerializedName("embed")
    public String mBaseUrl;

    public String mDescription;

    @SerializedName(FieldNames.OBJECT_ID)
    public String mId;

    @SerializedName(FieldNames.SOURCE)
    public String mSource;

    @SerializedName("album")
    public String mSeries;

    @SerializedName(FieldNames.ORIGINAL_LINK)
    public String mOriginalLink;

    public String mSeriesImageUrl;

    @SerializedName("timestamp")
    public long mTimestamp;

    public ArrayList<String> mTags;

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public Date getCreatedAt() {
        return new Date(mTimestamp);
    }

    public String getTitle() {
        if(mTitle != null)
            return mTitle;
        else if(mType != null)
            return mType.toUpperCase();
        else
            return null;
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
        if(mImageUrl != null)
            return mImageUrl;
        else
            return mThumbnailUrl;
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

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(Table.TumblrTalks.OBJECT_ID, mId);
        values.put(Table.TumblrTalks.TITLE, mTitle);
        values.put(Table.TumblrTalks.SUBTITLE, mSubtitle);
        values.put(Table.TumblrTalks.IMAGE_URL, mImageUrl);
        values.put(Table.TumblrTalks.AUDIO_URL, mAudioUrl);
        values.put(Table.TumblrTalks.BASE_URL, mBaseUrl);
        values.put(Table.TumblrTalks.DESCRIPTION, mDescription);
        values.put(Table.TumblrTalks.SOURCE, mSource);
        values.put(Table.TumblrTalks.SERIES, mSeries);
        values.put(Table.TumblrTalks.ORIGINAL_LINK, mOriginalLink);
        values.put(Table.TumblrTalks.SERIES_IMAGE_URL, mSeriesImageUrl);
        values.put(Table.TumblrTalks.TYPE, mType);
        values.put(Table.TumblrTalks.THUMBNAIL_URL, mThumbnailUrl);
        values.put(Table.TumblrTalks.PERMALINK_URL, mPermalinkUrl);
        values.put(Table.TumblrTalks.TAGS, Util.printArrayListAsString(mTags));
        values.put(Table.TumblrTalks.TIMESTAMP, mTimestamp);

        return values;
    }

    protected TumblrTalk(Parcel in) {
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
        mTimestamp = in.readLong();
        mType = in.readString();
        mThumbnailUrl = in.readString();
        mPermalinkUrl = in.readString();

        if (in.readByte() == 0x01) {
            mTags = new ArrayList<>();
            in.readList(mTags, String.class.getClassLoader());
        } else
            mTags = null;

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
        dest.writeLong(mTimestamp);
        dest.writeList(mTags);
        dest.writeString(mType);
        dest.writeString(mThumbnailUrl);
        dest.writeString(mPermalinkUrl);

        if (mTags == null)
            dest.writeByte((byte) (0x00));
        else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mTags);
        }
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
