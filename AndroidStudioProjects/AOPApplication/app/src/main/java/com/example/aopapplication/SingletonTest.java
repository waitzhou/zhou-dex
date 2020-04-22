package com.example.aopapplication;

import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author : ZSX
 * Date : 2019-12-20
 * Description :
 */
public class  SingletonTest {

    //INSTANCE;

    public void test(String str){
        Log.d("123456", "test: "+str);
    }

    private static final AtomicReference instance2 = new AtomicReference();

    private SingletonTest(){}

    public static SingletonTest getInstance(){
        for (;;){
            SingletonTest singletonTest = (SingletonTest) instance2.get();
            if(null != singletonTest){
                return singletonTest;
            }
            singletonTest = new SingletonTest();
            if(instance2.compareAndSet(null,singletonTest)){
                return singletonTest;
            }
        }
    }
}
