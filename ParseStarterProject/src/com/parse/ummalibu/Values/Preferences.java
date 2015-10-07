package com.parse.ummalibu.values;

import android.content.Context;

/**
 * Created by rjaylward on 9/22/15
 */
public class Preferences extends AbsPreferences{

    private static Preferences instance;
    private ListData mListData;

    public static void initialize(Context mContext) {
        instance = new Preferences(mContext);
    }

    public static Preferences getInstance() {
        if(instance == null)
            throw new NullPointerException("instance does not exist");

        return instance;
    }

    protected Preferences(Context context) {
        super(context);

        mListData = new ListData(context);
    }

    public ListData getListData() {
        return mListData;
    }

    public String getEmail() {
        return getStringPref(FieldNames.EMAIL);
    }

    public void setEmail(String email) {
        setPref(FieldNames.EMAIL, email);
    }

    public int getMpg() {
        return getIntPref(FieldNames.ETA);
    }

    public void setMpg(int eta) {
        setPref(FieldNames.ETA, eta);
    }

    public String getName() {
        return getStringPref(FieldNames.NAME);
    }

    public void setName(String name) {
        setPref(FieldNames.NAME, name);
    }

    public String getPhoneNumber() {
        return getStringPref(FieldNames.PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        setPref(FieldNames.PHONE_NUMBER, phoneNumber);
    }

    public String getImageUrl() {
        return getStringPref(FieldNames.IMAGE_URL).equals("") ? Constants.TEST_IMAGE_URL : getStringPref(FieldNames.IMAGE_URL);
    }

    public void setImageUrl(String imageUrl) {
        setPref(FieldNames.IMAGE_URL, imageUrl);
    }

    public String getCarDescription() {
        return getStringPref(FieldNames.CAR_DESCRIPTION);
    }

    public void setCarDescription(String carDescription) {
        setPref(FieldNames.CAR_DESCRIPTION, carDescription);
    }


}
