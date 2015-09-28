package com.parse.ummalibu.values;

import android.content.Context;

/**
 * Created by rjaylward on 9/22/15.
 */
public class ListData extends AbsPreferences {

    private static final String NOTIFICATIONS_EXPIRATION = "notifications_exp";
    private static final String TALKS_EXPIRATION = "talks_exp";
    private static final String EVENTS_EXPIRATION = "events_exp";
    private static final String PRAYERS_EXPIRATION = "prayers_exp";
    private static final String SONGS_EXPIRATION = "songs_exp";
    private static final String REQUESTS_EXPIRATION = "requests_exp";

    protected ListData(Context context) {
        super(context);
    }

    public long getNotificationsExpiration() {
        return getLongPref(NOTIFICATIONS_EXPIRATION);
    }

    public void setNotificationsExpiration(long expiration) {
        setPref(NOTIFICATIONS_EXPIRATION, expiration);
    }

    public long getTalksExpiration() {
        return getLongPref(TALKS_EXPIRATION);
    }

    public void setTalksExpiration(long expiration) {
        setPref(TALKS_EXPIRATION, expiration);
    }

    public long getEventsExpiration() {
        return getLongPref(EVENTS_EXPIRATION);
    }

    public void setEventsExpiration(long expiration) {
        setPref(EVENTS_EXPIRATION, expiration);
    }

    public long getPrayersExpiration() {
        return getLongPref(PRAYERS_EXPIRATION);
    }

    public void setPrayersExpiration(long expiration) {
        setPref(PRAYERS_EXPIRATION, expiration);
    }

    public long getSongsExpiration() {
        return getLongPref(SONGS_EXPIRATION);
    }

    public void setSongsExpiration(long expiration) {
        setPref(SONGS_EXPIRATION, expiration);
    }

    public long getRequestsExpiration() {
        return getLongPref(REQUESTS_EXPIRATION);
    }

    public void setRequestsExpiration(long expiration) {
        setPref(REQUESTS_EXPIRATION, expiration);
    }
}
