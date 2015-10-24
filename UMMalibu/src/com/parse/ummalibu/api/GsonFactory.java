package com.parse.ummalibu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ummalibu.objects.Event;
import com.parse.ummalibu.objects.Notification;
import com.parse.ummalibu.objects.Prayer;
import com.parse.ummalibu.objects.Song;
import com.parse.ummalibu.objects.Talk;
import com.parse.ummalibu.objects.TumblrTalk;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.objects.YoutubeItem;
import com.parse.ummalibu.responses.MapsResponse;

/**
 * Created by rjaylward on 9/22/15
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
                .registerTypeAdapter(MapsResponse.class, new MapsResponseDeserializer())
                .registerTypeAdapter(TumblrTalk.class, new TumblrTalkDeserializer())
                .registerTypeAdapter(YoutubeItem.class, new YoutubeItemDeserializer())
                .create();
    }

}
