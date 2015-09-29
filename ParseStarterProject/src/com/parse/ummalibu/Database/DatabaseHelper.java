package com.parse.ummalibu.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parse.ummalibu.objects.UmLocation;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.objects.Driver;
import com.parse.ummalibu.objects.Notification;
import com.parse.ummalibu.objects.Talk;
import com.parse.ummalibu.objects.UmberRequest;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class DatabaseHelper {

    private ContentResolver mContentResolver;
    public static final int DATABASE_VERSION = 1;
    DatabaseProvider.OpenDatabaseHelper mOpenDatabaseHelper;

    public DatabaseHelper(Context context) {
        mContentResolver = context.getApplicationContext().getContentResolver();
        mOpenDatabaseHelper = new DatabaseProvider.OpenDatabaseHelper(context);
    }

    public synchronized void addNotification(Notification notification) {
//        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FieldNames.ALERT, notification.getAlert());
        values.put(FieldNames.OBJECT_ID, notification.getObjectId());
        values.put(FieldNames.CREATED_AT, notification.getCreatedAt().getTime());

        // Inserting Row
        mContentResolver.insert(Table.Notifications.CONTENT_URI, values);

//        db.insertWithOnConflict(FieldNames.NOTIFICATIONS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
//        db.close(); // Closing database connection

    }

    public synchronized void addNotifications(ArrayList<Notification> notifications) {

        ArrayList<ContentValues> valuesArray = new ArrayList<>();

        for(Notification notification : notifications) {
            ContentValues values = new ContentValues();
            values.put(FieldNames.ALERT, notification.getAlert());
            values.put(FieldNames.OBJECT_ID, notification.getObjectId());
            values.put(FieldNames.CREATED_AT, notification.getCreatedAt().getTime());

            valuesArray.add(values);
        }

        mContentResolver.bulkInsert(Table.Notifications.CONTENT_URI, valuesArray.toArray(new ContentValues[notifications.size()]));
    }

    public synchronized Notification getNotification(String objectId) {
        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();

        String[] projection = {
                FieldNames.ALERT,
                FieldNames.OBJECT_ID,
                FieldNames.CREATED_AT
        };

        String sortOrder =
                FieldNames.CREATED_AT + " DESC";

        Cursor cursor = db.query(
                FieldNames.NOTIFICATIONS,           // The table to query
                projection,                         // The columns to return
                FieldNames.OBJECT_ID + " = ?",      // The columns for the WHERE clause
                new String[] {objectId},            // The values for the WHERE clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                sortOrder                           // The sort order
        );

        Notification notification = new Notification();

        try {
            if(cursor != null) {
                cursor.moveToFirst();

                notification.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FieldNames.CREATED_AT))));
                notification.setAlert(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.ALERT)));
                notification.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.OBJECT_ID)));
            }

        } finally {
            if(cursor != null)
                cursor.close();
        }

        // return contact
        return notification;
    }

    public synchronized ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FieldNames.NOTIFICATIONS + " ORDER BY " + FieldNames.CREATED_AT + " DESC";

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    Notification notification = new Notification();
                    notification.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FieldNames.CREATED_AT))));
                    notification.setAlert(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.ALERT)));
                    notification.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.OBJECT_ID)));
                    notifications.add(notification);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        // return contact list
        return notifications;
    }

    public synchronized void addAllTalks(ArrayList<Talk> talks) {

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();;

        for(Talk talk : talks) {

            ContentValues values = new ContentValues();
            values.put(FieldNames.OBJECT_ID, talk.getId());
            values.put(FieldNames.CREATED_AT, talk.getCreatedAt().getTime());
            values.put(FieldNames.TITLE, talk.getTitle());
            values.put(FieldNames.MEDIA_PLAYER_TEXT, talk.getSubtitle());
            values.put(FieldNames.IMAGE_URL, talk.getImageUrl());
            values.put(FieldNames.AUDIO_URL, talk.getAudioUrl());
            values.put(FieldNames.DIRECT_LINK, talk.getBaseUrl());
            values.put(FieldNames.DESCRIPTION, talk.getDescription());
            values.put(FieldNames.SOURCE, talk.getSource());
            values.put(FieldNames.SERIES, talk.getSeries());
            values.put(FieldNames.ORIGINAL_LINK, talk.getOriginalLink());
            values.put(FieldNames.SERIES_IMAGE_URL, talk.getSeriesImageUrl());

            //add the row
            db.insertWithOnConflict(FieldNames.TALKS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

        db.close(); // Closing database connection

    }

    public synchronized ArrayList<Talk> getAllTalks() {

        ArrayList<Talk> talks = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FieldNames.TALKS + " ORDER BY " + FieldNames.CREATED_AT + " DESC";

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    Talk talk = new Talk();
                    talk.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FieldNames.CREATED_AT))));
                    talk.setId(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.OBJECT_ID)));
                    talk.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.TITLE)));
                    talk.setSubtitle(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.MEDIA_PLAYER_TEXT)));
                    talk.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.IMAGE_URL)));
                    talk.setAudioUrl(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.AUDIO_URL)));
                    talk.setBaseUrl(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.DIRECT_LINK)));
                    talk.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.DESCRIPTION)));
                    talk.setSource(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.SOURCE)));
                    talk.setSeries(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.SERIES)));
                    talk.setOriginalLink(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.ORIGINAL_LINK)));
                    talk.setSeriesImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.SERIES_IMAGE_URL)));

                    talks.add(talk);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        return talks;
    }

    public synchronized ArrayList<UmLocation> getAllLocations() {

        ArrayList<UmLocation> locations = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table.UmLocations.TABLE_NAME + " ORDER BY \'" + Table.UmLocations.NAME + "\' DESC";

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    UmLocation location = new UmLocation();

                    location.setId(cursor.getString(cursor.getColumnIndexOrThrow(FieldNames.OBJECT_ID)));
                    location.setName(cursor.getString(cursor.getColumnIndexOrThrow(Table.UmLocations.NAME)));
                    location.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(Table.UmLocations.IMAGE_URL)));
                    location.setLat(cursor.getLong(cursor.getColumnIndexOrThrow(Table.UmLocations.LATITUDE)));
                    location.setLon(cursor.getLong(cursor.getColumnIndexOrThrow(Table.UmLocations.LONGITUDE)));
                    location.setType(cursor.getString(cursor.getColumnIndexOrThrow(Table.UmLocations.TYPE)));
                    location.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(Table.UmLocations.ADDRESS)));

                    locations.add(location);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        return locations;
    }

    public synchronized ArrayList<Driver> getAllDrivers() {

        ArrayList<Driver> drivers = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FieldNames.DRIVERS + " ORDER BY " + FieldNames.CREATED_AT + " DESC";

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    Driver driver = new Driver();
                    driver.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Table.Drivers.CREATED_AT))));
                    driver.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.OBJECT_ID)));
                    driver.setName(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.NAME)));
                    driver.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.EMAIL)));
                    driver.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.IMAGE_URL)));
                    driver.setCarDescription(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.CAR_DESCRIPTION)));
                    driver.setMpg(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Drivers.MPG)));
                    driver.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.PHONE_NUMBER)));

                    drivers.add(driver);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        return drivers;
    }

    public synchronized Driver getDriver(String email) {
        String selectQuery = "SELECT * FROM " + Table.Drivers.TABLE_NAME + " WHERE " + Table.Drivers.EMAIL + " = \'" + email + "\'";
        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                Driver driver = new Driver();
                driver.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Table.Drivers.CREATED_AT))));
                driver.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.OBJECT_ID)));
                driver.setName(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.NAME)));
                driver.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.EMAIL)));
                driver.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.IMAGE_URL)));
                driver.setCarDescription(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.CAR_DESCRIPTION)));
                driver.setMpg(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Drivers.MPG)));
                driver.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(Table.Drivers.PHONE_NUMBER)));

                return driver;
            } else
                return null;
        } finally {
            cursor.close();
        }
    }

    public synchronized ArrayList<UmberRequest> getRequests() {
        ArrayList<UmberRequest> requests = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + Table.Requests.TABLE_NAME + " ORDER BY " + Table.Requests.ETA + " DESC";

        SQLiteDatabase db = mOpenDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    UmberRequest request = new UmberRequest();
                    request.setCreatedAt(cursor.getLong(cursor.getColumnIndexOrThrow(Table.Requests.CREATED_AT)));
                    request.setObjectId(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.OBJECT_ID)));
                    request.setName(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.NAME)));
                    request.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.EMAIL)));
                    request.setRiderImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.RIDER_IMAGE_URL)));
                    request.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.LATITUDE)));
                    request.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.LONGITUDE)));
                    request.setPickupLat(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.PICK_UP_LATITUDE)));
                    request.setPickupLong(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.PICK_UP_LONGITUDE)));
                    request.setPickUpLocation(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.PICK_UP_LOCATION_NAME)));
                    request.setDestinationLat(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.DESTINATION_LATITUDE)));
                    request.setDestinationLong(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.DESTINATION_LONGITUDE)));
                    request.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.DESTINATION_LOCATION_NAME)));
                    request.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.PHONE_NUMBER)));
                    request.setDriverLat(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.DRIVER_LATITUDE)));
                    request.setDriverLon(cursor.getDouble(cursor.getColumnIndexOrThrow(Table.Requests.DRIVER_LONGITUDE)));
                    request.setEta(cursor.getLong(cursor.getColumnIndexOrThrow(Table.Requests.ETA)));
                    request.setDriverEmail(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.DRIVER_EMAIL)));
                    request.setPath(cursor.getString(cursor.getColumnIndexOrThrow(Table.Requests.PATH)));

                    request.setClaimed(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Requests.CLAIMED)) != 0);
                    request.setStarted(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Requests.STARTED)) != 0);
                    request.setIsPickedUp(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Requests.IS_PICKED_UP)) != 0);
                    request.setComplete(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Requests.IS_COMPLETE)) != 0);
                    request.setCanceled(cursor.getInt(cursor.getColumnIndexOrThrow(Table.Requests.CANCELED)) != 0);

                    requests.add(request);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }

        return requests;
    }

    public synchronized void updateRequest(UmberRequest request) {
        mContentResolver.update(Table.Requests.CONTENT_URI, request.toContentValues(), Table.Requests.OBJECT_ID + " = " + request.getObjectId(), null);
    }

    public synchronized void addRequest(UmberRequest request) {
        mContentResolver.insert(Table.Requests.CONTENT_URI, request.toContentValues());
    }

    public synchronized void addDriver(Driver driver) {
        mContentResolver.insert(Table.Drivers.CONTENT_URI, driver.toContentValues());
    }

    public synchronized void addRequests(ArrayList<UmberRequest> requests) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for(UmberRequest request : requests) {
            values.add(request.toContentValues());
        }

        mContentResolver.bulkInsert(Table.Requests.CONTENT_URI, values.toArray(new ContentValues[requests.size()]));
    }

    public synchronized void addDrivers(ArrayList<Driver> drivers) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for(Driver driver : drivers) {
            values.add(driver.toContentValues());
        }

        mContentResolver.bulkInsert(Table.Drivers.CONTENT_URI, values.toArray(new ContentValues[drivers.size()]));
    }

}
