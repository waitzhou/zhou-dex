package com.example.jingweiclassicdemo.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Author : ZSX
 * Date : 2020-01-08
 * Description :
 */
public class ServiceTest extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,String.valueOf(android.os.Process.myPid()),Toast.LENGTH_SHORT).show();
        return binder;
    }


    private IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d("-----> ", "add: -----> enter  aidl  service");
            return a+b;
        }
    };
}
