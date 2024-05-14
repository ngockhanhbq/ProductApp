package com.example.productapp.services;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERID = "userid";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setUserId(int userId){
        editor.putInt(KEY_USERID, userId);
        editor.apply();
    }
    public int getUserId(){
        return sharedPreferences.getInt(KEY_USERID, -1);
    }


    // Method to clear user session data
    public void clearUserSession() {
        editor.clear();
        editor.apply();
    }
}

