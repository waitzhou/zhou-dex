package com.example.aidltestmain.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class TestApplication extends Application {

    private String TAG = "TestApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }
}
