package com.example.componentdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class TestActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Runnable runnable1 =  new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 111 = "+ new Date().getTime());
            }
        };
        Runnable runnable2 =  new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 222 = "+ new Date().getTime());
            }
        };
        Runnable runnable3 =  new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 333 = "+ new Date().getTime());
            }
        };
        Runnable runnable4 =  new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 444 = "+ new Date().getTime());
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 555 = "+ new Date().getTime());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TestActivity.this,"666啊",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        MyThread myThread = new MyThread();
        Future<?> submit1 = executorService.schedule(myThread,2000, TimeUnit.MILLISECONDS);
        Future<?> submit2 = executorService.submit(myThread);
        Future<?> submit3 = executorService.submit(myThread);
        Future<?> submit4 = executorService.submit(myThread);
        Future<?> submit5 = executorService.submit(myThread);
        Future<?> submit6 = executorService.submit(myThread);
        Future<?> submit7 = executorService.submit(myThread);
        Future<?> submit8 = executorService.submit(myThread);

        new MyAsyncTask("线程1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("线程2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("线程3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("线程4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("线程5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("线程6").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");


        //MessageQueue queue = mainLooper.getQueue();

        HandlerThread handlerThread = new HandlerThread("子现场"){
            @Override
            public void run() {
                super.run();

            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        //new Handler().postDelayed(runnable,1000);
        //Runnable runnable = new Anomymous();
        runnable.run();
    }

    class Anomymous implements Runnable{
        @Override
        public void run() {
            Toast.makeText(TestActivity.this,"12412124",Toast.LENGTH_SHORT).show();
        }
    }



    class MyAsyncTask extends AsyncTask {

        private String mString;
        public MyAsyncTask(String name){
            this.mString = name;
        }

        @Override
        protected Object doInBackground(Object[] pObjects) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Log.d(TAG, "doInBackground: "+mString+"  "+format.format(new Date().getTime()));
            return null;
        }
    }

    class MyThread implements Runnable{

        private int totalTicket = 200;

        @Override
        public void run() {
            while (totalTicket > 0){
                totalTicket--;
                Log.d(TAG, "run: "+Thread.currentThread().getName() +"卖票--->"+this.totalTicket);
            }
        }
    }
}
