package com.example.aopapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Author : ZSX
 * Date : 2019-12-23
 * Description :
 */
public class TestActivity extends AppCompatActivity {

    public static final String TEST = "codeboy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
