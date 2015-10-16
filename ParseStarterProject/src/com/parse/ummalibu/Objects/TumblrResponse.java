package com.parse.ummalibu.objects;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/15/15
 */
public class TumblrResponse implements ApiResponse {

    @SerializedName("blog")
    TumblrBlog mBlog;

    @SerializedName("posts")
    ArrayList<TumblrTalk> mTumblrTalks;

    public TumblrBlog getBlog() {
        return mBlog;
    }

    public void setBlog(TumblrBlog blog) {
        mBlog = blog;
    }

    public ArrayList<TumblrTalk> getTumblrTalks() {
        return mTumblrTalks;
    }

    public void setTumblrTalks(ArrayList<TumblrTalk> tumblrTalks) {
        mTumblrTalks = tumblrTalks;
    }

    public boolean isEmpty() {
        return mTumblrTalks.isEmpty();
    }

    @Override
    public void saveResponse(Context context) {
        //TODO save the response
    }
}
