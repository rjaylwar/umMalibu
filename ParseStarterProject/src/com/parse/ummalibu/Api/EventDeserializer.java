package com.parse.ummalibu.Api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Event;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class EventDeserializer implements JsonDeserializer<Event> {

    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        Event event = gson.fromJson(json, Event.class);

        event.setImageUrl(json.getAsJsonObject().getAsJsonObject("graphic").get("url").getAsString());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        try {
            Date date = simpleDateFormat.parse(json.getAsJsonObject().getAsJsonObject(FieldNames.CREATED_AT).getAsString());
            event.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return event;
    }

}
