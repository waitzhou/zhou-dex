package com.example.jingweiclassicdemo;

import android.app.Application;
import android.content.Context;

import com.didi.virtualapk.PluginManager;
import com.example.jingweiclassicdemo.activity.CrashHandler;
import com.example.jingweiclassicdemo.hook.NotificationProxy;

/**
 * Author : ZSX
 * Date : 2019-12-27
 * Description :
 */
public class HostApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
        NotificationProxy.hookNotificationManager(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(
                new CrashHandler(Thread.getDefaultUncaughtExceptionHandler()));
    }
}
