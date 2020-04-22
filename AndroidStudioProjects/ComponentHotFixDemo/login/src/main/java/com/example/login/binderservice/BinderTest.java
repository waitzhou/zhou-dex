package com.example.login.binderservice;

import android.os.Binder;
import android.util.Log;

/**
 * Author : ZSX
 * Date : 2019-11-27
 * Description :
 */
public class BinderTest extends Binder {

    public BinderTest(){

    }

    public void test123(){
        Log.d("BinderTest", "test123: ");
    }
}
