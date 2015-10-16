package com.parse.ummalibu.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.objects.TumblrTalk;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by rjaylward on 10/15/15
 */
public class TumblrTalkDeserializer implements JsonDeserializer<TumblrTalk> {

    @Override
    public TumblrTalk deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        TumblrTalk talk = gson.fromJson(json, TumblrTalk.class);
        ArrayList<String> tags = new ArrayList<>();

        try {
            JsonArray jsonTags = json.getAsJsonObject().getAsJsonArray("tags");
            for (JsonElement element : jsonTags) {
                String tag = element.getAsString();
                tags.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        talk.setTags(tags);

        return talk;
    }
}
