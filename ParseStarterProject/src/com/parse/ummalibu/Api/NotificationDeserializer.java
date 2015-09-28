package com.parse.ummalibu.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Notification;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rjaylward on 9/22/15.
 */
public class NotificationDeserializer implements JsonDeserializer<Notification> {

    @Override
    public Notification deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        Notification notification = gson.fromJson(json, Notification.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

        try {
            Date date = simpleDateFormat.parse(json.getAsJsonObject().getAsJsonPrimitive(FieldNames.CREATED_AT).getAsString().replace("Z", "+00:00"));
            notification.setCreatedAt(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return notification;
    }
}
