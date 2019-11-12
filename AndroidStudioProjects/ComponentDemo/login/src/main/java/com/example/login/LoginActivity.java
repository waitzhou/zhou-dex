package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonbase.ServiceFactory;

@Route(path = "/login/login")
public class LoginActivity extends AppCompatActivity {
    TextView mParamText,mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mParamText = findViewById(R.id.text_param);
        mUsername = findViewById(R.id.text_username);
        String str = getIntent().getStringExtra("param");
        mParamText.setText(str);
        String str2 = ServiceFactory.getInstance().getIAccountService().getAccountId();
        mUsername.setText(str2);
    }

    public void register(View pView){
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
