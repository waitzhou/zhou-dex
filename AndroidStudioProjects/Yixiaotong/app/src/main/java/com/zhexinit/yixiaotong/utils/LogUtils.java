package com.zhexinit.yixiaotong.utils;

import android.util.Log;

/**
 * Author:zhousx
 * date:2018/11/14
 * description:log 日志
 */
public class LogUtils {

    public static String TAG = "SmartCampusLog";

    public static void e(String s){
        Log.e(TAG, "e: " + s);
    }

    public static void d(String s){
        Log.d(TAG, "e: " + s);
    }

    public static void i(String s){
        Log.i(TAG, "e: " + s);
    }

    public static void v(String s){
        Log.v(TAG, "e: " + s);
    }
}
