package com.parse.ummalibu.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Talk;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rjaylward on 9/22/15.
 */
public class TalkDeserializer implements JsonDeserializer<Talk> {


    @Override
    public Talk deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        Talk talk = gson.fromJson(json, Talk.class);

        talk.setSeriesImageUrl(json.getAsJsonObject().getAsJsonObject("graphic").get("url").getAsString());

        if (talk.getSubtitle() != null) {
            int index = talk.getSubtitle().indexOf("<br>");
            if(index > -1) talk.setSubtitle(talk.getSubtitle().substring(index + 4)); //4 == "<br>".length();
        }

        String description = "";

        for(JsonElement element : json.getAsJsonObject().getAsJsonArray("verses")) {
            if(element.getAsString().length() > 1)
                description += element.getAsString();
            else
                description += "\n\n";
        }

        talk.setDescription(description);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

        try {
            Date date = simpleDateFormat.parse(json.getAsJsonObject().getAsJsonPrimitive(FieldNames.CREATED_AT).getAsString().replace("Z", "+00:00"));
            talk.setCreatedAt(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return talk;
    }
}

