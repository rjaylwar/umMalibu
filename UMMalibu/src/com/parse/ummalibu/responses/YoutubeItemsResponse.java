package com.parse.ummalibu.responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.objects.YoutubeItem;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/22/15
 */
public class YoutubeItemsResponse implements ApiResponse {

    @SerializedName("items")
    private ArrayList<YoutubeItem> mYoutubeItems;

    @Override
    public void saveResponse(Context context) { }

    public ArrayList<YoutubeItem> getYoutubeItems() {
        return mYoutubeItems;
    }

    public boolean isNull() {
        return mYoutubeItems == null;
    }

    public boolean isEmpty() {
        return mYoutubeItems.isEmpty();
    }

}
