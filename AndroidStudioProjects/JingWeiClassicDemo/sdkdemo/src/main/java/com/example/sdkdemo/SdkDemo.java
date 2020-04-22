package com.example.sdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Author : ZSX
 * Date : 2020-04-15
 * Description :
 */
public class SdkDemo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdkdemo);
    }

    public String addMethod(int a,int b){
        return "jisuanjieguo："+a+b;
    }

    public void goback(View view) {
        Toast.makeText(this,addMethod(1,2),Toast.LENGTH_SHORT).show();

    }
}
