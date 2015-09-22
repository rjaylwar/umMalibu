package com.parse.ummalibu.Api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.Prayer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rjaylward on 9/22/15.
 */
public class PrayerDeserializer implements JsonDeserializer<Prayer> {

    @Override
    public Prayer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        Prayer prayer = gson.fromJson(json, Prayer.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

        try {
            Date date = simpleDateFormat.parse(json.getAsJsonObject().getAsJsonObject(FieldNames.CREATED_AT).getAsString().replace("Z", "+00:00"));
            prayer.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return prayer;
    }

}
