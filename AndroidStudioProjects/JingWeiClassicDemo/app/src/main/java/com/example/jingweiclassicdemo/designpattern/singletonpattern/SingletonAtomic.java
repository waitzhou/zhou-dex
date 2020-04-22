package com.example.jingweiclassicdemo.designpattern.singletonpattern;

import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class SingletonAtomic {

    private static final AtomicReference<SingletonAtomic> INSTANCE = new AtomicReference<SingletonAtomic>();

    private SingletonAtomic() {
    }

    public static SingletonAtomic getINSTANCE() {
        for (; ; ) {
            SingletonAtomic singletonAtomic = INSTANCE.get();
            if (null != singletonAtomic) {
                return singletonAtomic;
            }
            singletonAtomic = new SingletonAtomic();
            if (INSTANCE.compareAndSet(null, singletonAtomic)) {
                Log.d("SingletonAtomic", "getINSTANCE: true");
                return singletonAtomic;
            } else {
                Log.d("SingletonAtomic", "getINSTANCE: false");
            }
        }
    }

    public void test(String s){
        Log.d("SingletonAtomic", "test: 11111111   = "+s);
    }
}
