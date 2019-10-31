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
            try {
                String s = remotService.getTestResult("123456",null);
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


}
