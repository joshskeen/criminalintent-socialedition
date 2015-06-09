package com.bignerdranch.android.criminalintent;

import android.app.Application;

import com.facebook.FacebookSdk;

public class CriminalIntentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
