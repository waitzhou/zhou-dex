package com.example.classifacationplugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Author : ZSX
 * Date : 2019-11-01
 * Description :
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_home);
        String type = getIntent().getStringExtra("type");
        Toast.makeText(this,type,Toast.LENGTH_SHORT).show();
    }
}
