package com.parse.ummalibu.database;

import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 9/22/15.
 */
public class TablesSQL {

    public static final String CREATE_TALKS_TABLE = "CREATE TABLE " + FieldNames.TALKS + " ( " +
            Table.Talks.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Talks.TITLE + " TEXT, " +
            Table.Talks.MEDIA_PLAYER_TEXT + " TEXT, " +
            Table.Talks.IMAGE_URL + " TEXT, " +
            Table.Talks.AUDIO_URL + " TEXT, " +
            Table.Talks.DIRECT_LINK + " TEXT, " +
            Table.Talks.SOURCE + " TEXT, " +
            Table.Talks.SERIES + " TEXT, " +
            Table.Talks.ORIGINAL_LINK + " TEXT, " +
            Table.Talks.DESCRIPTION + " TEXT, " +
            Table.Talks.SERIES_IMAGE_URL + " TEXT, " +
            Table.Talks.CREATED_AT + " INTEGER " + ");";

    public static final String CREATE_EVENTS_TABLE = "CREATE TABLE " + FieldNames.EVENTS + " ( " +
            Table.Events.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Events.TITLE + " TEXT, " +
            Table.Events.DATE + " TEXT, " +
            Table.Events.EVENT_DATE + " TEXT, " +
            Table.Events.COST + " TEXT, " +
            Table.Events.IMAGE_URL + " TEXT, " +
            Table.Events.TUMBLR_URL + " TEXT, " +
            Table.Events.ORIGINAL_LINK + " TEXT, " +
            Table.Events.DESCRIPTION_SHORT + " TEXT, " +
            Table.Events.DESCRIPTION_LONG + " TEXT, " +
            Table.Events.IS_OVER + " INTEGER, " +
            Table.Events.CREATED_AT + " INTEGER " + ");";

    public static final String CREATE_SONGS_TABLE = "CREATE TABLE " + FieldNames.SONGS + " ( " +
            Table.Songs.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Songs.TITLE + " TEXT, " +
            Table.Songs.YOUTUBE_URL + " TEXT, " +
            Table.Songs.SPOTIFY_URL + " TEXT, " +
            Table.Songs.ITUNES_URL + " TEXT, " +
            Table.Songs.CREATED_AT + " INTEGER " + ");";

    public static final String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + FieldNames.NOTIFICATIONS + " ( " +
            Table.Notifications.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Notifications.ALERT + " TEXT, " +
            Table.Notifications.CREATED_AT + " INTEGER " + ");";

    public static final String CREATE_PRAYERS_TABLE = "CREATE TABLE " + Table.Prayers.TABLE_NAME + " ( " +
            Table.Prayers.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Prayers.PRAYER_REQUEST + " TEXT, " +
            Table.Prayers.NAME + " TEXT, " +
            Table.Prayers.CREATED_AT + " INTEGER " + ");";

    public static final String CREATE_DRIVERS_TABLE = "CREATE TABLE " + Table.Drivers.TABLE_NAME + " ( " +
            Table.Drivers.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.Drivers.EMAIL + " TEXT, " +
            Table.Drivers.NAME + " TEXT, " +
            Table.Drivers.CAR_DESCRIPTION + " TEXT, " +
            Table.Drivers.IMAGE_URL + " TEXT, " +
            Table.Drivers.PHONE_NUMBER + " TEXT, " +
            Table.Drivers.CREATED_AT + " INTEGER, " +
            Table.Drivers.MPG + " INTEGER " + ");";

    public static final String CREATE_UM_LOCATIONS_TABLE = "CREATE TABLE " + Table.UmLocations.TABLE_NAME + " ( " +
            Table.UmLocations.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE, " +
            Table.UmLocations.NAME + " TEXT, " +
            Table.UmLocations.ADDRESS + " TEXT, " +
            Table.UmLocations.IMAGE_URL + " TEXT, " +
            Table.UmLocations.TYPE + " TEXT, " +
            Table.UmLocations.LATITUDE + " INTEGER, " +
            Table.UmLocations.LONGITUDE + " INTEGER " + ");";

    public static final String CREATE_UMBER_REQUESTS_TABLE = "CREATE TABLE " + FieldNames.REQUESTS + " ( " +
            Table.Requests.OBJECT_ID + " TEXT PRIMARY KEY UNIQUE," +
            Table.Requests.CREATED_AT + " INTEGER, " +
            Table.Requests.EMAIL + " TEXT, " +
            Table.Requests.NAME + " TEXT, " +
            Table.Requests.RIDER_IMAGE_URL + " TEXT, " +
            Table.Requests.LATITUDE + " INTEGER, " +
            Table.Requests.LONGITUDE + " INTEGER, " +
            Table.Requests.PICK_UP_LATITUDE + " INTEGER, " +
            Table.Requests.PICK_UP_LONGITUDE + " INTEGER, " +
            Table.Requests.PICK_UP_LOCATION_NAME + " TEXT, " +
            Table.Requests.DESTINATION_LATITUDE + " INTEGER, " +
            Table.Requests.DESTINATION_LONGITUDE + " INTEGER, " +
            Table.Requests.DESTINATION_LOCATION_NAME  + " TEXT, " +
            Table.Requests.PHONE_NUMBER  + " TEXT, " +
            Table.Requests.DRIVER_LATITUDE  + " INTEGER, " +
            Table.Requests.DRIVER_LONGITUDE  + " INTEGER, " +
            Table.Requests.ETA + " INTEGER, " +
            Table.Requests.DRIVER_EMAIL + " TEXT, " +
            Table.Requests.PATH + " TEXT, " +
            Table.Requests.CLAIMED + " TINYINT DEFAULT 0, " +
            Table.Requests.STARTED + " TINYINT DEFAULT 0, " +
            Table.Requests.IS_PICKED_UP + " TINYINT DEFAULT 0, " +
            Table.Requests.IS_COMPLETE + " TINYINT DEFAULT 0, " +
            Table.Requests.CANCELED + " TINYINT DEFAULT 0 " + ");";

    public static final String[] TABLE_NAMES = {CREATE_TALKS_TABLE, CREATE_EVENTS_TABLE, CREATE_SONGS_TABLE,
            CREATE_PRAYERS_TABLE, CREATE_NOTIFICATIONS_TABLE, CREATE_UMBER_REQUESTS_TABLE, CREATE_DRIVERS_TABLE,
            CREATE_UM_LOCATIONS_TABLE};

    public static String deleteTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }
}

