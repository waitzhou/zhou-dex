package com.example.login.binderservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Author : ZSX
 * Date : 2019-11-27
 * Description :
 */
public class BindTestService extends Service {

    @Override
    public IBinder onBind(Intent pIntent) {
        Log.d("BindTestService", "onBind: "+android.os.Process.myPid());
        return new BinderTest();
    }
}
