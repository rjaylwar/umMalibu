package com.parse.ummalibu.Responses;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.Database.ApiResponse;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Event;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class EventsResponse implements ApiResponse {

    @SerializedName(FieldNames.RESULTS)
    ArrayList<Event> mEvents;

    @Override
    public void saveResponse(Context context) {

    }

    public ArrayList<Event> getEventsArray() {
        return mEvents;
    }

}
