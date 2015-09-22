package com.parse.ummalibu.Values;

import android.content.Context;

/**
 * Created by rjaylward on 9/22/15.
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
}
