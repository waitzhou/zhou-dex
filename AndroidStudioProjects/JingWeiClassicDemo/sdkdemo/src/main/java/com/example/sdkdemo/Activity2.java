package com.example.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Author : ZSX
 * Date : 2020-04-16
 * Description :
 */
public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
    }

    public void activity2GoBack(View view) {
        startActivity(new Intent(this,SdkDemo.class));
    }
}
