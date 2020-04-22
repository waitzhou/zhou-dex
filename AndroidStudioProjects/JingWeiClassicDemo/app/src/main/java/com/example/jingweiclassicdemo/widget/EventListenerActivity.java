package com.example.jingweiclassicdemo.widget;

import android.Manifest;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingweiclassicdemo.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class EventListenerActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listener);

        taskEasy();
    }

    private void taskEasy(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: step1");
                Looper.prepare();
                Log.d(TAG, "run: step2");
                Looper.loop();
                Log.d(TAG, "run: step3");
            }
        });
        thread.start();
        thread.interrupt();
        new TextView(this).post(new Runnable() {
            @Override
            public void run() {

            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    }

    @UiThread
    private void doUI(){
        Log.d("test", "doUI: ");
        Toast.makeText(this,"lalala",Toast.LENGTH_SHORT).show();
    }

    @CheckResult
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public String btnClick1(View view) {
        Log.d("---->", "btnClick1: ");
        return "";
    }

    public void btnClick2(View view) {
        Log.d("---->", "btnClick2: ");
    }

    private void test2(@StyleRes int res){

    }

    private void test(@LayoutRes int res){

    }
}
