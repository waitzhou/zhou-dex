package com.example.aidltestmain;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class Test2Service extends Service {

    private String TAG = "Test2Service";

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    private IRemotService.Stub mBinder = new IRemotService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getTestResult(String result, ITest2CallBack callback) throws RemoteException {
            Log.d(TAG, "getTestResult: "+result);
            requestResult(result, callback);
            return "666";
        }
    };

    private void requestResult(String s,ITest2CallBack callBack){
        //Toast.makeText(this,"6666666",Toast.LENGTH_SHORT).show();
    }
}
