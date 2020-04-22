package com.example.login.function;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * Author : ZSX
 * Date : 2019-12-09
 * Description :
 */
public class ExecutorsActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executors);

        init();
    }

    ExecutorService executorService;

    private void init() {
        executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        executorService.execute(new MyThread("007"));
        executorService.execute(new MyThread("潘玮柏"));
        executorService.execute(new MyThread("哈哈哈"));

        if (executorService != null && !executorService.isShutdown())
            executorService.shutdownNow();
    }

    private class MyThread extends Thread implements Runnable {

        private String mName;

        public MyThread(String name) {
            mName = name;
        }

        @Override
        public void run() {
            super.run();
            try {
                if (mName.equals("007"))
                    Thread.sleep(2000);
                Log.d(TAG, "run: " + mName);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
        }
    }
}
