package com.example.productapp;
import android.app.Application;

import com.example.productapp.services.UserSession;

public class Session extends Application {

    private static UserSession userSession;

    @Override
    public void onCreate() {
        super.onCreate();
        userSession = new UserSession(this);
    }

    public static UserSession getUserSession() {
        return userSession;
    }
}
