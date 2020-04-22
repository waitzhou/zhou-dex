package com.example.login.messengeservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Author : ZSX
 * Date : 2019-11-27
 * Description :
 */
public class MessengerService extends Service {

    private String TAG = this.getClass().getSimpleName();

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private ArrayList<Messenger> mArrayList = new ArrayList<>();

    static final int MSG_REGISTER_CLIENT = 1;

    static final int MSG_UNREGISTER_CLIENT = 2;

    static final int MSG_SET_VALUE = 3;

    @Override
    public IBinder onBind(Intent pIntent) {
        Log.d(TAG, "onBind: --------->"+android.os.Process.myPid());
        return mMessenger.getBinder();
    }

    class MessengerHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MSG_REGISTER_CLIENT:
                    mArrayList.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    Toast.makeText(getApplicationContext(),"解除绑定",Toast.LENGTH_SHORT).show();
                    for (int i = mArrayList.size() - 1; i >= 0 ; i--) {
                        try {
                            mArrayList.get(i).send(Message.obtain(null,MSG_UNREGISTER_CLIENT,0,0,"解除绑定"));
                        } catch (RemoteException pE) {
                            pE.printStackTrace();
                        }
                    }
                    mArrayList.remove(msg.replyTo);
                    break;
                case MSG_SET_VALUE:
                    for (int i = mArrayList.size() - 1; i >= 0 ; i--) {
                        try {
                            mArrayList.get(i).send(Message.obtain(null,MSG_SET_VALUE,1000,0));
                        } catch (RemoteException pE) {
                            pE.printStackTrace();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
