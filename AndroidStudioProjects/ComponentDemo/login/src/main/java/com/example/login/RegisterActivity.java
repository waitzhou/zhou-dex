package com.example.login;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
@Route(path = "/login/register")
public class RegisterActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_register);
        Log.d(TAG, "onCreate: "+android.os.Process.myPid());
    }
}
