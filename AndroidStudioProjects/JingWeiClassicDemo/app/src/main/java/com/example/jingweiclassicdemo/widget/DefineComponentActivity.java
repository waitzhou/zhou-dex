package com.example.jingweiclassicdemo.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.example.jingweiclassicdemo.R;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class DefineComponentActivity extends AppCompatActivity {

    ViewStub mViewStub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_component);

        init();
    }

    private static Handler sHandler = new Handler();

    private void init() {
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewStub.setVisibility(View.VISIBLE);
            }
        },2000);
    }

    public void eventListener(View view) {
        startActivity(new Intent(this,EventListenerActivity.class));
    }
}
