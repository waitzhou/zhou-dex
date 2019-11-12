package com.example.share;

import android.app.Application;

import com.example.base.BaseApp;

/**
 * Author : ZSX
 * Date : 2019-11-04
 * Description :
 */
public class ShareApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application pApplication) {

    }

    @Override
    public void initModuleData(Application pApplication) {

    }
}
