package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.didi.virtualapk.PluginManager;

/**
 * Author : ZSX
 * Date : 2019-10-31
 * Description :
 */
public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }
}
