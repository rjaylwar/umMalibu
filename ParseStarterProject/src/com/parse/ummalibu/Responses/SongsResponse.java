package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Song;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class SongsResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    ArrayList<Song> mSongs;

    @Override
    public void saveResponse(Context context) {

    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

}
