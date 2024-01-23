package com.mobilers.the_little_racetrack_mobile.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "UserData";
    public static final String KEY_USER_DATA = "UserData";
    private Context myContext;

    public MySharedPreferences(Context myContext) {
        this.myContext = myContext;
    }

    public void putStringSetValue(String key, String value) {
        SharedPreferences sharedPreferences = myContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringSetValue(String key) {
        SharedPreferences sharedPreferences = myContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
