package com.example.jingweiclassicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author : ZSX
 * Date : 2019-12-18
 * Description :
 */
public class PermissionUtils {

    private String TAG = this.getClass().getSimpleName();

    public static PermissionUtils getInstance() {
        return Inner.INSTANCE;
    }

    private static class Inner {
        private static final PermissionUtils INSTANCE = new PermissionUtils();
    }

    private PermissionUtils() {
    }

    /**
     * 检查权限
     */
    public void checkPermission(Activity clazz, String[] permissions, int requestCode) {
        ArrayList<String> strings = new ArrayList<String>();
        for (String permission : permissions) {
            if (!hasPermission(clazz, permission)) {
                strings.add(permission);
            }
        }
        requestPermission(clazz, Arrays.copyOf(strings.toArray(), strings.size(), String[].class), requestCode);
    }

    /**
     * 是否已经申请权限
     */
    private boolean hasPermission(Context context, String string) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context != null)
                return ActivityCompat.checkSelfPermission(context, string) == PackageManager.PERMISSION_GRANTED;
            else return false;
        } else {
            return true;
        }
    }

    /**
     * 是否所有权限都请求成功
     */
    public boolean hasAllPermission(Context context, String[] strings) {
        if(strings == null || strings.length == 0){
            return true;
        }
        for (String string : strings) {
            if (!hasPermission(context, string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     */
    private void requestPermission(Activity context, String[] strings, int requestCode) {
        ActivityCompat.requestPermissions(context, strings, requestCode);
    }
}
