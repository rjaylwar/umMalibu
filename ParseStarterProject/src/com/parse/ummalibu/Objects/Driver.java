package com.parse.ummalibu.objects;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class Driver implements ApiResponse {

    @SerializedName(FieldNames.OBJECT_ID)
    private String mObjectId = "";

    @SerializedName(FieldNames.EMAIL)
    private String mEmail = "";

    @SerializedName(FieldNames.NAME)
    private String mName = "";

    @SerializedName(FieldNames.MPG)
    private int mMpg = 20;

    @SerializedName(FieldNames.PHONE_NUMBER)
    private String mPhoneNumber = "";

    @SerializedName(FieldNames.CAR_DESCRIPTION)
    private String mCarDescription = "";

    @SerializedName(FieldNames.IMAGE_URL)
    private String mImageUrl = "http://a2.mzstatic.com/us/r30/Purple1/v4/df/b8/b3/dfb8b375-b66f-a191-2a0a-488651a8afaa/icon175x175.jpeg";

    private Date mCreatedAt;

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getMpg() {
        return mMpg;
    }

    public void setMpg(int mpg) {
        if(mpg > 0)
            mMpg = mpg;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return mEmail.toLowerCase();
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getCarDescription() {
        return mCarDescription;
    }

    public void setCarDescription(String carDescription) {
        mCarDescription = carDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if(!imageUrl.equals(""))
            mImageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Uri getUri() {
        return Table.Drivers.CONTENT_URI;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(Table.Drivers.OBJECT_ID, mObjectId);
        values.put(Table.Drivers.EMAIL, mEmail);
        values.put(Table.Drivers.NAME, mName);
        values.put(Table.Drivers.MPG, mMpg);
        values.put(Table.Drivers.PHONE_NUMBER, mPhoneNumber);
        values.put(Table.Drivers.CAR_DESCRIPTION, mCarDescription);
        values.put(Table.Drivers.IMAGE_URL, mImageUrl);

        values.put(Table.Drivers.CREATED_AT, mCreatedAt == null ? 0 : mCreatedAt.getTime());
        return values;
    }

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addDriver(this);
    }

    public JsonObject getAsJson(boolean includeObjectId) {
        JsonObject body = new JsonObject();

        if(includeObjectId)
            body.addProperty(FieldNames.OBJECT_ID, mObjectId);

        body.addProperty(FieldNames.EMAIL, mEmail);
        body.addProperty(FieldNames.NAME, mName);
        body.addProperty(FieldNames.MPG, mMpg);
        body.addProperty(FieldNames.PHONE_NUMBER, mPhoneNumber);
        body.addProperty(FieldNames.CAR_DESCRIPTION, mCarDescription);
        body.addProperty(FieldNames.IMAGE_URL, mImageUrl);

        return body;
    }
}
