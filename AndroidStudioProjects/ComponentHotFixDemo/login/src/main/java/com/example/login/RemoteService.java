package com.example.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Author : ZSX
 * Date : 2019-11-25
 * Description :
 */
public class RemoteService extends Service {

    private String TAG = this.getClass().getSimpleName();

    public RemoteService(){}

    @Override
    public IBinder onBind(Intent pIntent) {
        return mIBinder;
    }

    private IBinder mIBinder = new ILoginInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void getLoginData() throws RemoteException {
            Log.d(TAG, "getLoginData: ");
        }
    };
}
