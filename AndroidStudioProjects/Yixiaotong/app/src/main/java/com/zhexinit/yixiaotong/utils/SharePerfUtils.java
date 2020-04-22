package com.zhexinit.yixiaotong.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.zhexinit.yixiaotong.base.SmartCampusApp;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:@zhousx
 * date: 2017/7/6/9:54.
 * function :轻量级数据存储工具类
 */

public class SharePerfUtils {


    /**
     * 获取pref实例
     * */
    private static SharedPreferences getSharePrefInstance(Context context){
        return context.getSharedPreferences("school_pref_name", Context.MODE_PRIVATE);
    }

    /**
     * 存取string类型
     * */
    public static void putString( String key, String value){
        getSharePrefInstance(SmartCampusApp.context).edit().putString(key,value).apply();
    }

    public static String getString(String key){
        return getSharePrefInstance(SmartCampusApp.context).getString(key,null);
    }

    /**
     * 存取Boolean类型
     * */
    public static void putBoolean( String key, boolean value){
        getSharePrefInstance(SmartCampusApp.context).edit().putBoolean(key,value).apply();
    }

    public static boolean getBoolean( String key,boolean defValue ){
        return getSharePrefInstance(SmartCampusApp.context).getBoolean(key,defValue);
    }

    /**
     * 存取int类型
     * */
    public static void putInt(String key, int value){
        getSharePrefInstance(SmartCampusApp.context).edit().putInt(key,value).apply();
    }

    public static int getInt( String key){
        return getSharePrefInstance(SmartCampusApp.context).getInt(key,0);
    }

    /**
     * 存取float类型数据
     * */
    public static void putFloat(String key, float value){
        getSharePrefInstance(SmartCampusApp.context).edit().putFloat(key,value).apply();
    }

    public static float getFloat( String key){
        return getSharePrefInstance(SmartCampusApp.context).getFloat(key,0f);
    }

    /**
     * 存取long型数据
     * */
    public static void putLong( String key, long value){
        getSharePrefInstance(SmartCampusApp.context).edit().putLong(key,value).apply();
    }

    public static long getLong( String key){
        return getSharePrefInstance(SmartCampusApp.context).getLong(key,0);
    }

    /**
     * 存取model类型
     * */
    public static <T> void putModel(String key, T t){
        putString(key, GsonUtil.getInstance().getGson().toJson(t));
    }

    public static <T> T getModel( String key, Type type){
        String value = getString(key);
        return GsonUtil.getInstance().getGson().fromJson(value,type);
    }

    /**
     * 存取list集合
     * */
    public static <T>void putList( String key, List<T> list){
        putString(key,GsonUtil.getInstance().getGson().toJson(list));
    }

    public static <T> T getList( String key, Type type){
        String value = getString(key);
        return GsonUtil.getInstance().getGson().fromJson(value,type);
    }

    /**
     * 存取set集合
     * */
    public static <T>void putSet( String key, Set<T> set){
        putString(key,GsonUtil.getInstance().getGson().toJson(set));
    }

    public static <T> Set<T> getSet( String key, Type type){
        String vlaue = getString(key);
        Set<T> set = null;
        set = GsonUtil.getInstance().getGson().fromJson(vlaue,type);
        if(set == null){
            set = new HashSet<>();
        }
        return set;
    }

    /**
     * 根据key删除一项
     * */
    public static void remove( String key){
        if(SmartCampusApp.context == null){
            return;
        }
        getSharePrefInstance(SmartCampusApp.context).edit().remove(key).apply();
    }

    /**
     * 清空所有
     * */
    public static void clearPref(){
        getSharePrefInstance(SmartCampusApp.context).edit().clear().apply();
    }
}
