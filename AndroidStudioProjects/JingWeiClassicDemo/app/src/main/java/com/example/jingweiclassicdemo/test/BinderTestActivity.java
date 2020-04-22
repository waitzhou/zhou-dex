package com.example.jingweiclassicdemo.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.jingweiclassicdemo.R;

/**
 * Author : ZSX
 * Date : 2020-01-09
 * Description :
 */
public class BinderTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater.from(this).setFactory(new LayoutInflater.Factory() {
            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Log.e("BinderTestActivity", "onCreateView: "+ name);
                int count = attrs.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    Log.d("BinderTestActivity", "onCreateView: AttributeName:"+attrs.getAttributeName(i)+"    AttributeValue:"+attrs.getAttributeValue(i));
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_test);
        Toast.makeText(this,String.valueOf(android.os.Process.myPid()),Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            int add = 0;
            try {
                add = aidlInterface.add(1, 6);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("---->", "onServiceConnected: add = "+add+"      ComponentName = "+name.getClassName());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Intent intent;
    public void bindService(View view) {
        intent = new Intent(this,ServiceTest.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        stopService(intent);
        super.onDestroy();
    }

    public void jumpToOtherProcess(View view) {
        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra("params","999");
        startActivity(intent);
    }
}
