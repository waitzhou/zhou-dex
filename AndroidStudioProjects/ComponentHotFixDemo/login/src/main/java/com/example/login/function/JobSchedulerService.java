package com.example.login.function;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Author : ZSX
 * Date : 2019-11-28
 * Description :
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    static final int DOWNLOAD_APK = 10086;

    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == DOWNLOAD_APK) {
                Toast.makeText(getApplicationContext(),""+msg.arg1,Toast.LENGTH_SHORT).show();
            } else
                super.handleMessage(msg);
        }
    };

    @Override
    public boolean onStartJob(JobParameters pJobParameters) {

        handler.sendMessage(Message.obtain(handler, DOWNLOAD_APK,666,0));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters pJobParameters) {
        return false;
    }
}
