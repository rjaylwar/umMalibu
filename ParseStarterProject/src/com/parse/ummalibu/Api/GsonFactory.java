package com.parse.ummalibu.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ummalibu.Objects.Event;
import com.parse.ummalibu.Objects.Notification;
import com.parse.ummalibu.Objects.Prayer;
import com.parse.ummalibu.Objects.Song;
import com.parse.ummalibu.Objects.Talk;
import com.parse.ummalibu.Objects.UmberRequest;

/**
 * Created by rjaylward on 9/22/15.
 */
public class GsonFactory {

    public static Gson createGsonObject() {
        return new GsonBuilder()
                .registerTypeAdapter(Talk.class, new TalkDeserializer())
                .registerTypeAdapter(Event.class, new EventDeserializer())
                .registerTypeAdapter(Prayer.class, new PrayerDeserializer())
                .registerTypeAdapter(Notification.class, new NotificationDeserializer())
                .registerTypeAdapter(Song.class, new SongDeserializer())
                .registerTypeAdapter(UmberRequest.class, new UmberRequestDeserializer())
                .create();
    }

}
