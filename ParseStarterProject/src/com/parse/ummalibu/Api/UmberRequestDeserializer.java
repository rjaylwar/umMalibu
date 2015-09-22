package com.parse.ummalibu.Api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.Values.FieldNames;
import com.parse.ummalibu.Objects.UmberRequest;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rjaylward on 9/22/15.
 */
public class UmberRequestDeserializer implements JsonDeserializer<UmberRequest> {

    @Override
    public UmberRequest deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        UmberRequest umberRequest = gson.fromJson(jsonElement, UmberRequest.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

        try {
            Date date = simpleDateFormat.parse(jsonElement.getAsJsonObject().getAsJsonObject(FieldNames.CREATED_AT).getAsString().replace("Z", "+00:00"));
            umberRequest.setCreatedAt(date);

            Date eta = new Date(jsonElement.getAsJsonObject().getAsJsonObject(FieldNames.ETA).getAsLong());
            umberRequest.setEta(eta.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
