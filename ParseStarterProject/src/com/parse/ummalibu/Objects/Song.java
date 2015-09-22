package com.parse.ummalibu.Objects;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Song {

    @SerializedName(FieldNames.TITLE)
    public String mTitle;

    @SerializedName(FieldNames.OBJECT_ID)
    public String mObjectId;

    @SerializedName(FieldNames.YOUTUBE_URL)
    public String mYoutubeUrl;

    @SerializedName(FieldNames.SPOTIFY_URL)
    public String mSpotifyUrl;

    @SerializedName(FieldNames.ITUNES_URL)
    public String mItunesUrl;

    Date mCreatedAt;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

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

    public String getYoutubeUrl() {
        return mYoutubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        mYoutubeUrl = youtubeUrl;
    }

    public String getSpotifyUrl() {
        return mSpotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        mSpotifyUrl = spotifyUrl;
    }

    public String getItunesUrl() {
        return mItunesUrl;
    }

    public void setItunesUrl(String itunesUrl) {
        mItunesUrl = itunesUrl;
    }

}

