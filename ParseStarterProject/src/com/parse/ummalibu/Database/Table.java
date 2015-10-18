package com.parse.ummalibu.database;

import android.net.Uri;

import com.parse.ummalibu.values.FieldNames;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Table {

    public static final String RAW = "raw";
    public static final Uri RAW_QUERY = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + RAW);
    public static final String ORDER_KEY = "order_key";

    public static final class Requests {
        public static final String TABLE_NAME = "requests";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
        public static final String NAME = FieldNames.NAME;
        public static final String RIDER_IMAGE_URL = FieldNames.RIDER_IMAGE_URL;
        public static final String LATITUDE = FieldNames.LATITUDE;
        public static final String LONGITUDE = FieldNames.LONGITUDE;
        public static final String PICK_UP_LATITUDE = FieldNames.PICKUP_LATITUDE;
        public static final String PICK_UP_LONGITUDE = FieldNames.PICKUP_LONGITUDE;
        public static final String PICK_UP_LOCATION_NAME = FieldNames.PICKUP_LOCATION_NAME;
        public static final String DESTINATION_LATITUDE = FieldNames.DESTINATION_LATITUDE;
        public static final String DESTINATION_LONGITUDE = FieldNames.DESTINATION_LONGITUDE;
        public static final String DESTINATION_LOCATION_NAME = FieldNames.DESTINATION_NAME;
        public static final String PHONE_NUMBER = FieldNames.PHONE_NUMBER;
        public static final String DRIVER_LATITUDE = FieldNames.DRIVER_LATITUDE;
        public static final String DRIVER_LONGITUDE = FieldNames.DRIVER_LONGITUDE;
        public static final String ETA = FieldNames.ETA;
        public static final String EMAIL = FieldNames.EMAIL;
        public static final String DRIVER_EMAIL = FieldNames.DRIVER_EMAIL;
        public static final String PATH = FieldNames.PATH;
        public static final String CLAIMED = FieldNames.CLAIMED;
        public static final String STARTED = FieldNames.STARTED;
        public static final String IS_PICKED_UP = FieldNames.IS_PICKED_UP;
        public static final String IS_COMPLETE = FieldNames.IS_COMPLETE;
        public static final String CANCELED = FieldNames.CANCELED;
    }

    public static final class  Drivers {
        public static final String TABLE_NAME = "drivers";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
        public static final String NAME = FieldNames.NAME;
        public static final String EMAIL = FieldNames.EMAIL;
        public static final String CAR_DESCRIPTION = FieldNames.CAR_DESCRIPTION;
        public static final String IMAGE_URL = FieldNames.IMAGE_URL;
        public static final String PHONE_NUMBER = FieldNames.PHONE_NUMBER;
        public static final String MPG = FieldNames.MPG;
    }

    public static final class Notifications {
        public static final String TABLE_NAME = "notifications";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String ALERT = FieldNames.ALERT;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
    }

    public static final class Prayers {
        public static final String TABLE_NAME = "prayers";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String NAME = FieldNames.NAME;
        public static final String PRAYER_REQUEST = FieldNames.PRAYER_REQUEST;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
    }

    public static final class Events {
        public static final String TABLE_NAME = "events";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String TITLE = FieldNames.TITLE;
        public static final String IMAGE_URL = FieldNames.IMAGE_URL;
        public static final String TUMBLR_URL = FieldNames.TUMBLR_URL;
        public static final String ORIGINAL_LINK = FieldNames.ORIGINAL_LINK;
        public static final String DESCRIPTION_SHORT =  FieldNames.DESCRIPTION_SHORT;
        public static final String DESCRIPTION_LONG = FieldNames.DESCRIPTION_LONG;
        public static final String IS_OVER = FieldNames.IS_OVER;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
        public static final String DATE = FieldNames.DATE;
        public static final String EVENT_DATE = FieldNames.EVENT_DATE;
        public static final String COST = FieldNames.COST;
    }

    public static final class Songs {
        public static final String TABLE_NAME = "songs";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String TITLE = FieldNames.TITLE;
        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String YOUTUBE_URL = FieldNames.YOUTUBE_URL;
        public static final String SPOTIFY_URL = FieldNames.SPOTIFY_URL;
        public static final String ITUNES_URL = FieldNames.ITUNES_URL;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
    }

    public static final class Talks {
        public static final String TABLE_NAME = "talks";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
        public static final String TITLE = FieldNames.TITLE;
        public static final String MEDIA_PLAYER_TEXT = FieldNames.MEDIA_PLAYER_TEXT;
        public static final String IMAGE_URL = FieldNames.IMAGE_URL;
        public static final String AUDIO_URL = FieldNames.AUDIO_URL;
        public static final String DIRECT_LINK = FieldNames.DIRECT_LINK;
        public static final String SOURCE = FieldNames.SOURCE;
        public static final String SERIES = FieldNames.SERIES;
        public static final String ORIGINAL_LINK = FieldNames.ORIGINAL_LINK;
        public static final String DESCRIPTION = FieldNames.DESCRIPTION;
        public static final String SERIES_IMAGE_URL = FieldNames.SERIES_IMAGE_URL;
        public static final String CREATED_AT = FieldNames.CREATED_AT;
    }

    public static final class UmLocations {
        public static final String TABLE_NAME = "um_locations";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String NAME = FieldNames.NAME;
        public static final String ADDRESS = FieldNames.ADDRESS;
        public static final String IMAGE_URL = FieldNames.IMAGE_URL;
        public static final String TYPE = FieldNames.TYPE;
        public static final String LATITUDE = FieldNames.LATITUDE;
        public static final String LONGITUDE = FieldNames.LONGITUDE;
        public static final String OBJECT_ID = FieldNames.OBJECT_ID;
    }

    public static final class TumblrTalks {
        public static final String TABLE_NAME = "tumblr_talks";
        public static final Uri CONTENT_URI = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.UM." + TABLE_NAME;

        public static final String TITLE = "artist";
        public static final String SUBTITLE = "caption";
        public static final String IMAGE_URL = "album_art";
        public static final String AUDIO_URL = "audio_url";
        public static final String BASE_URL = "embed";
        public static final String DESCRIPTION = "description";
        public static final String OBJECT_ID = "object_id";
        public static final String SOURCE = "source";
        public static final String SERIES = "album";
        public static final String ORIGINAL_LINK = "original_link";
        public static final String SERIES_IMAGE_URL = "series_image_url";
        public static final String TIMESTAMP = "timestamp";
    }
}

