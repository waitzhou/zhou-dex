package com.example.componenthotfixdemo.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Author : ZSX
 * Date : 2019-11-19
 * Description :
 */
public class TestIntentService extends IntentService {

    public TestIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent pIntent) {

    }
}
