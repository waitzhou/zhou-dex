package com.example.componentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonbase.ServiceFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                login(pView);
            }
        });
    }

    public void login(View pView){
        ARouter.getInstance().build("/login/login").withString("param","from main").navigation();
        /*Intent intent = new Intent();//隐式启动
        intent.setClassName(this,"com.example.login.LoginActivity");
        startActivity(intent);*/
    }

    public void share(View pView){
        ARouter.getInstance().build("/share/share").navigation();
    }

    public void ad(View pView){

    }
}
