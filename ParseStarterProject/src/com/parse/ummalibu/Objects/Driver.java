package com.parse.ummalibu.objects;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

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
    private int mMpg;

    @SerializedName(FieldNames.PHONE_NUMBER)
    private String mPhoneNumber = "";

    @SerializedName(FieldNames.CAR_DESCRIPTION)
    private String mCarDescription = "";

    @SerializedName(FieldNames.IMAGE_URL)
    private String mImageUrl = "";

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

        values.put(Table.Drivers.CREATED_AT, mCreatedAt.getTime());
        return values;
    }

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addDriver(this);
    }
}
