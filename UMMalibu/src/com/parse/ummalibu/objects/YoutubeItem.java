package com.parse.ummalibu.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;

/**
 * Created by rjaylward on 10/21/15
 */
public class YoutubeItem implements ApiResponse, Parcelable {

//    publishedAt: "2015-10-21T00:19:21.000Z",
//    channelId: "UCZHxMSKJDW78fQ4Zx1C_RgA",
//    title: "Be Lifted Up",
//    description: "Mixing it up this Sunday, Pepperdine students plus worship team regulars leading this morning!",
//    thumbnails: {},
//    channelTitle: "Malibu MizzouFan",
//    playlistId: "PLi1lg8EgWuZTFkpHpc6rb0qu4Lt7pD_u1",
//    position: 0,
//    resourceId: {}
//
    public YoutubeItem () {}

    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("playlistId")
    private String mPlaylistId;
    @SerializedName("publishedAt")
    private String mPublishedDate;

    private String mThumbnailUrl;
    private String mKind;
    private String mVideoId;


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPlaylistId() {
        return mPlaylistId;
    }

    public void setPlaylistId(String playlistId) {
        mPlaylistId = playlistId;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    protected YoutubeItem(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mPlaylistId = in.readString();
        mPublishedDate = in.readString();
        mThumbnailUrl = in.readString();
        mKind = in.readString();
        mVideoId = in.readString();
    }

    public static final Creator<YoutubeItem> CREATOR = new Creator<YoutubeItem>() {
        @Override
        public YoutubeItem createFromParcel(Parcel in) {
            return new YoutubeItem(in);
        }

        @Override
        public YoutubeItem[] newArray(int size) {
            return new YoutubeItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mPlaylistId);
        parcel.writeString(mPublishedDate);
        parcel.writeString(mThumbnailUrl);
        parcel.writeString(mKind);
        parcel.writeString(mVideoId);
    }

    @Override
    public void saveResponse(Context context) {
//        DatabaseHelper helper = DatabaseHelper.getInstance(context);
    }
}
