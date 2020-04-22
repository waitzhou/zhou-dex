package com.example.jingweiclassicdemo.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author : ZSX
 * Date : 2020-04-08
 * Description :
 */
public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        test();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer timer = new Timer();
    public void test(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("","");
                test();
            }
        },5*60*1000);
    }
}
