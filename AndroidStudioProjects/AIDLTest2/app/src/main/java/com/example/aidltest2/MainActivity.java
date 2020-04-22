package com.example.aidltest2;


import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aidltestmain.IRemotService;
import com.example.aidltestmain.ITest2CallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent service = new Intent("com.main.mainapplication.intent.action.FORURL");
                service.setPackage("com.example.aidltestmain");
                bindService(service,serviceConnection,BIND_AUTO_CREATE);
            }
        });
    }

    private IRemotService remotService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            remotService = IRemotService.Stub.asInterface(iBinder);
            Log.e(TAG, "onServiceConnected: " + remotService);
            try {
                String s = remotService.getTestResult("123456", new ITest2CallBack() {
                    @Override
                    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

                    }

                    @Override
                    public void getResultSuccess(String s) throws RemoteException {

                    }

                    @Override
                    public void getResultFail(String msg) throws RemoteException {

                    }

                    @Override
                    public IBinder asBinder() {
                        return null;
                    }
                });
                Log.d(TAG, "onServiceConnected: s = "+s);
                textView.setText(s);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            remotService = null;
        }
    };


    public void getPluginParams(View view) {

    }
}
