package com.example.jingweiclassicdemo.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jingweiclassicdemo.R;

/**
 * Author : ZSX
 * Date : 2020-01-09
 * Description :
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String param = getIntent().getStringExtra("params");
        Toast.makeText(this,String.valueOf(param),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}
