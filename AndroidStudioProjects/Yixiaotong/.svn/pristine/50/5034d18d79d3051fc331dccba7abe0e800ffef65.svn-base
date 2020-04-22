package com.zhexinit.yixiaotong.utils;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Author:@zhousx
 * date: 2017/7/6/11:17.
 * function :gson 单例模式
 */

public class GsonUtil {

    private volatile static GsonUtil sInstance;

    private Gson mGson;

    /*public static GsonUtil getInstance(){
        if(sInstance == null){
            GsonUtil gsonUtil = sInstance;
            if(gsonUtil == null){
                synchronized (GsonUtil.class){
                    gsonUtil = new GsonUtil();
                    sInstance = gsonUtil;
                }
            }
        }
        return sInstance;
    }*/

    public static GsonUtil getInstance(){
        if(sInstance == null){
            sInstance = new GsonUtil();
        }
        return sInstance;
    }

    public Gson getGson(){
        if(mGson == null){
           if(Build.VERSION.SDK_INT > 23) {
               GsonBuilder gsonBuilder = new GsonBuilder()
                       .excludeFieldsWithModifiers(
                               Modifier.FINAL,
                               Modifier.TRANSIENT,
                               Modifier.STATIC);
               mGson = gsonBuilder.create();
           }else{
               mGson = new Gson();
           }
        }
        return mGson;
    }
}
