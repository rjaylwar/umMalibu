package com.parse.ummalibu.objects;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ummalibu.database.ApiResponse;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.values.FieldNames;

import java.util.Date;

/**
 * Created by rjaylward on 9/22/15.
 */
public class UmberRequest implements ApiResponse, Parcelable {

    public UmberRequest() {}

    @SerializedName(FieldNames.OBJECT_ID)
    private String mObjectId = "";

    @SerializedName(FieldNames.EMAIL)
    private String mEmail = "";

    @SerializedName(FieldNames.DRIVER_EMAIL)
    private String mDriverEmail = "";

    @SerializedName(FieldNames.NAME)
    private String mName = "";

    @SerializedName(FieldNames.RIDER_IMAGE_URL)
    private String mRiderImageUrl = "";

    @SerializedName(FieldNames.LATITUDE)
    public double mLatitude;

    @SerializedName(FieldNames.LONGITUDE)
    public double mLongitude;

    @SerializedName(FieldNames.PICKUP_LOCATION_NAME)
    public String mPickUpLocation = "";

    @SerializedName(FieldNames.PICKUP_LATITUDE)
    private double mPickupLat;

    @SerializedName(FieldNames.PICKUP_LONGITUDE)
    private double mPickupLong;

    @SerializedName(FieldNames.DESTINATION_NAME)
    private String mDestination = "";

    @SerializedName(FieldNames.DESTINATION_LATITUDE)
    private double mDestinationLat;

    @SerializedName(FieldNames.DESTINATION_LONGITUDE)
    private double mDestinationLong;

    @SerializedName(FieldNames.PHONE_NUMBER)
    private String mPhoneNumber = "";

    @SerializedName(FieldNames.DRIVER_LATITUDE)
    private double mDriverLat;

    @SerializedName(FieldNames.DRIVER_LONGITUDE)
    private double mDriverLon;

    @SerializedName(FieldNames.ETA)
    private long mEta;

    @SerializedName(FieldNames.PATH)
    private String mPath = "";

    @SerializedName(FieldNames.CLAIMED)
    private boolean mClaimed;

    @SerializedName(FieldNames.STARTED)
    private boolean mStarted;

    @SerializedName(FieldNames.IS_PICKED_UP)
    private boolean mIsPickedUp;

    @SerializedName(FieldNames.IS_COMPLETE)
    private boolean mComplete;

    @SerializedName(FieldNames.CANCELED)
    private boolean mCanceled;

    private long mCreatedAt;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getRiderImageUrl() {
        return mRiderImageUrl;
    }

    public void setRiderImageUrl(String riderImageUrl) {
        mRiderImageUrl = riderImageUrl;
    }

    public boolean isClaimed() {
        return mClaimed;
    }

    public void setClaimed(boolean claimed) {
        mClaimed = claimed;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getPickUpLocation() {
        return mPickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        mPickUpLocation = pickUpLocation;
    }

    public double getPickupLat() {
        return mPickupLat;
    }

    public void setPickupLat(double pickupLat) {
        mPickupLat = pickupLat;
    }

    public double getPickupLong() {
        return mPickupLong;
    }

    public void setPickupLong(double pickupLong) {
        mPickupLong = pickupLong;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public double getDestinationLat() {
        return mDestinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        mDestinationLat = destinationLat;
    }

    public double getDestinationLong() {
        return mDestinationLong;
    }

    public void setDestinationLong(double destinationLong) {
        mDestinationLong = destinationLong;
    }

    public Date getCreatedAt() {
        return new Date(mCreatedAt);
    }

    public void setCreatedAt(long date) {
        mCreatedAt = date;
    }
    public boolean isPickedUp() {
        return mIsPickedUp;
    }

    public void setIsPickedUp(boolean isPickedUp) {
        mIsPickedUp = isPickedUp;
    }

    public Date getEta() {
        return new Date(mEta);
    }

    public void setEta(long eta) {
        mEta = eta;
    }

    public double getDriverLat() {
        return mDriverLat;
    }

    public void setDriverLat(double driverLat) {
        mDriverLat = driverLat;
    }

    public double getDriverLon() {
        return mDriverLon;
    }

    public void setDriverLon(double driverLon) {
        mDriverLon = driverLon;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public void setCanceled(boolean canceled) {
        mCanceled = canceled;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDriverEmail() {
        return mDriverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        mDriverEmail = driverEmail;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public boolean isStarted() {
        return mStarted;
    }

    public void setStarted(boolean started) {
        mStarted = started;
    }

    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    public String getMapUrl(int width, int height) {

        String mapString = "https://maps.googleapis.com/maps/api/staticmap?" + "size=" + width + "x" + height +
                "&scale=2&markers=color:green%7Clabel:P%7C" + mPickupLat + "," + mPickupLong + "&markers=color:blue%7Clabel:D%7C" +
                mDestinationLat + "," + mDestinationLong;

        if(!mPath.equals(""))
            mapString += "&path=weight:3%7Ccolor:blue%7Cenc:" + mPath;

        return mapString;
    }

    public Uri getUri() {
        return Table.Requests.CONTENT_URI;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(Table.Requests.OBJECT_ID, mObjectId);
        values.put(Table.Requests.ETA, mEta);
        values.put(Table.Requests.EMAIL, mEmail);
        values.put(Table.Requests.DRIVER_EMAIL, mDriverEmail);
        values.put(Table.Requests.NAME, mName);
        values.put(Table.Requests.RIDER_IMAGE_URL, mRiderImageUrl);
        values.put(Table.Requests.LATITUDE, mLatitude);
        values.put(Table.Requests.LONGITUDE, mLongitude);
        values.put(Table.Requests.PICK_UP_LATITUDE, mPickupLat);
        values.put(Table.Requests.PICK_UP_LONGITUDE, mPickupLong);
        values.put(Table.Requests.PICK_UP_LOCATION_NAME, mPickUpLocation);
        values.put(Table.Requests.DESTINATION_LATITUDE, mDestinationLat);
        values.put(Table.Requests.DESTINATION_LONGITUDE, mDestinationLong);
        values.put(Table.Requests.DESTINATION_LOCATION_NAME, mDestination);
        values.put(Table.Requests.PHONE_NUMBER, mPhoneNumber);
        values.put(Table.Requests.DRIVER_LATITUDE, mDriverLat);
        values.put(Table.Requests.DRIVER_LONGITUDE, mDriverLon);
        values.put(Table.Requests.PATH, mPath);

        values.put(Table.Requests.CLAIMED, mClaimed ? 1 : 0);
        values.put(Table.Requests.STARTED, mStarted ? 1 : 0);
        values.put(Table.Requests.IS_PICKED_UP, mIsPickedUp ? 1 : 0);
        values.put(Table.Requests.IS_COMPLETE, mComplete ? 1 : 0);
        values.put(Table.Requests.CANCELED, mCanceled ? 1 : 0);

        values.put(Table.Requests.CREATED_AT, mCreatedAt);

        return values;
    }

    @Override
    public void saveResponse(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addRequest(this);
    }

    public UmberRequest(Parcel in) {
        mObjectId = in.readString();
        mEmail = in.readString();
        mDriverEmail = in.readString();
        mName = in.readString();
        mRiderImageUrl = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mPickUpLocation = in.readString();
        mPickupLat = in.readDouble();
        mPickupLong = in.readDouble();
        mDestination = in.readString();
        mDestinationLat = in.readDouble();
        mDestinationLong = in.readDouble();
        mPhoneNumber = in.readString();
        mDriverLat = in.readDouble();
        mDriverLon = in.readDouble();
        mEta = in.readLong();
        mPath = in.readString();
        mClaimed = in.readByte() != 0x00;
        mStarted = in.readByte() != 0x00;
        mIsPickedUp = in.readByte() != 0x00;
        mComplete = in.readByte() != 0x00;
        mCanceled = in.readByte() != 0x00;
        mCreatedAt = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mObjectId);
        dest.writeString(mEmail);
        dest.writeString(mDriverEmail);
        dest.writeString(mName);
        dest.writeString(mRiderImageUrl);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mPickUpLocation);
        dest.writeDouble(mPickupLat);
        dest.writeDouble(mPickupLong);
        dest.writeString(mDestination);
        dest.writeDouble(mDestinationLat);
        dest.writeDouble(mDestinationLong);
        dest.writeString(mPhoneNumber);
        dest.writeDouble(mDriverLat);
        dest.writeDouble(mDriverLon);
        dest.writeLong(mEta);
        dest.writeString(mPath);
        dest.writeByte((byte) (mClaimed ? 0x01 : 0x00));
        dest.writeByte((byte) (mStarted ? 0x01 : 0x00));
        dest.writeByte((byte) (mIsPickedUp ? 0x01 : 0x00));
        dest.writeByte((byte) (mComplete ? 0x01 : 0x00));
        dest.writeByte((byte) (mCanceled ? 0x01 : 0x00));
        dest.writeLong(mCreatedAt);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UmberRequest> CREATOR = new Parcelable.Creator<UmberRequest>() {
        @Override
        public UmberRequest createFromParcel(Parcel in) {
            return new UmberRequest(in);
        }

        @Override
        public UmberRequest[] newArray(int size) {
            return new UmberRequest[size];
        }
    };
}
