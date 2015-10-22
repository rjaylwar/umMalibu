package com.parse.ummalibu.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.objects.YoutubeItem;

import java.lang.reflect.Type;

/**
 * Created by rjaylward on 10/22/15
 */
public class YoutubeItemDeserializer implements JsonDeserializer<YoutubeItem> {

    @Override
    public YoutubeItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            Gson gson = new Gson();
            YoutubeItem youtubeItem = gson.fromJson(json.getAsJsonObject().get("snippet").getAsJsonObject(), YoutubeItem.class);

            try {
                youtubeItem.setKind(json.getAsJsonObject().get("resourceId").getAsJsonObject()
                        .get("kind").getAsJsonPrimitive().getAsString());
            } catch (Exception e) {
                Log.d("Get Kind", e.getMessage(), e.getCause());
            }

            try {
                youtubeItem.setVideoId(json.getAsJsonObject().get("resourceId").getAsJsonObject()
                        .get("videoId").getAsJsonPrimitive().getAsString());
            } catch (Exception e) {
                Log.d("Get Video Id", e.getMessage(), e.getCause());
            }

            try {
                youtubeItem.setKind(json.getAsJsonObject().get("thumbnails").getAsJsonObject()
                        .get("standard").getAsJsonObject()
                        .get("url").getAsJsonPrimitive().getAsString());
            } catch (Exception e) {
                Log.d("Get Thumbnail Url", e.getMessage(), e.getCause());
            }

            return youtubeItem;
        }
        catch (Exception e) {
            return null;
        }
    }

}
