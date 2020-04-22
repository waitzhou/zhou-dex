package com.example.login.messengeservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

/**
 * Author : ZSX
 * Date : 2019-11-27
 * Description :
 */
public class MessengerClientActivity extends AppCompatActivity {

    private TextView mStatusText;

    boolean mIsBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
        mStatusText = findViewById(R.id.text);
    }

    Messenger mService = null;
    Messenger mMessenger = new Messenger(new MessengerHandler233());

    class MessengerHandler233 extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_SET_VALUE:
                    mStatusText.setText("接受传递过来的参数 ：" + msg.arg1);
                    break;
                case MessengerService.MSG_UNREGISTER_CLIENT:
                    mStatusText.setText((String)msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName pComponentName, IBinder pIBinder) {
            mIsBind = true;
            mService = new Messenger(pIBinder);
            mStatusText.setText("服务已连接");

            Message message = Message.obtain(null, MessengerService.MSG_REGISTER_CLIENT);
            message.replyTo = mMessenger;
            try {
                mService.send(message);
                mStatusText.setText("发送messenger并注册");
                message = Message.obtain(null, MessengerService.MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(message);
            } catch (RemoteException pE) {
                pE.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName pComponentName) {
            mIsBind = false;
            mStatusText.setText("服务连接失败");
        }
    };

    public void bindMessengerService(View view) {
        bindService(new Intent(this, MessengerService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onClick2(View view) {
    }

    public void onClick1(View view) {
        if (mIsBind) {
            Message message = Message.obtain(null, MessengerService.MSG_UNREGISTER_CLIENT);
            try {
                message.replyTo = mMessenger;
                mService.send(message);
            } catch (RemoteException pE) {
                pE.printStackTrace();
            }
        }
        unbindService(mConnection);
    }
}
