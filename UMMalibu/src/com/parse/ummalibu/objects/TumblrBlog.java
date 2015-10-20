package com.parse.ummalibu.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjaylward on 10/15/15
 */
public class TumblrBlog {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("name")
    private String mName;

    @SerializedName("posts")
    private int mPosts;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("updated")
    private long mUpdated;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("likes")
    private int mLikes;

    public String getTitle() {
        return mTitle;
    }

    public String getName() {
        return mName;
    }

    public int getPosts() {
        return mPosts;
    }

    public String getUrl() {
        return mUrl;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getLikes() {
        return mLikes;
    }

}
