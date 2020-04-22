package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.base.rxjava.AESUtils;
import com.example.login.binderservice.BindTestService;
import com.example.login.binderservice.BinderTest;
import com.example.login.function.ClassReflectActivity;
import com.example.login.function.ExecutorsActivity;
import com.example.login.function.JobSchedulerActivity;
import com.example.login.messengeservice.MessengerClientActivity;

import java.util.concurrent.ThreadPoolExecutor;

public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName pComponentName, IBinder pIBinder) {
            Log.d(TAG, "onServiceConnected: --->");
            ILoginInterface iLoginInterface = ILoginInterface.Stub.asInterface(pIBinder);
            try {
                iLoginInterface.getLoginData();
            } catch (RemoteException pE) {
                pE.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName pComponentName) {
            Log.d(TAG, "onServiceDisconnected: --->");
        }
    };

    ServiceConnection mBinderTestConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName pComponentName, IBinder pIBinder) {
            Log.d(TAG, "onServiceConnected: ");
            BinderTest binderTest = (BinderTest) pIBinder;
            binderTest.test123();
        }

        @Override
        public void onServiceDisconnected(ComponentName pComponentName) {
            Log.d(TAG, "onServiceDisconnected: ");

        }
    };

    public void bindService(View view) {
        bindBinderTestService();
    }

    private void bindRemoteService(){
        Intent intent = new Intent(this,RemoteService.class);
        bindService(intent,connection,Context.BIND_AUTO_CREATE);
        //startService(intent);
    }

    private void bindBinderTestService(){
        Intent intent = new Intent(this, BindTestService.class);
        bindService(intent,mBinderTestConnection,Context.BIND_AUTO_CREATE);
    }

    public void jumpmessengerActivity(View view) {
        Intent intent = new Intent(this, MessengerClientActivity.class);
        startActivity(intent);
    }

    public void jobSchedulerActivity(View view) {
        startActivity(new Intent(this, JobSchedulerActivity.class));
    }

    public void reflecttest(View view) {
        startActivity(new Intent(this, ClassReflectActivity.class));
    }

    public void threadsTest(View view) {
        startActivity(new Intent(this, ExecutorsActivity.class));
    }
}
