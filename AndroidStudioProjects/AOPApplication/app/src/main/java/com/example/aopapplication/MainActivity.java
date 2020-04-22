package com.example.aopapplication;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aopapplication.aspectDomain.PermissionAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Permission;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    private int mTest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SingletonTest.getInstance().test("12");
        SingletonTest.getInstance().test("34");
        SingletonTest.getInstance().test("56");
        Log.d("5555555", "onCreate: "+TestActivity.TEST);
    }

    public void aspectClick(View view) {
        Log.d("555", "aspectClick: 124");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void aspectClickWithParams(View view) {
        Log.d("555", "aspectClickWithParams:111 =  "+mTest);
        mTest = 100;
        Log.d("555", "aspectClickWithParams:222 =  "+mTest);

    }

    @AspectAnnotation(checkAnnotation = "我是参数")
    public void getValue(View view) {
        Log.d("555", "getValue: ");
    }

    @PermissionAnnotation(permissionType = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void getStoragePermission(View view) {
        Log.d("---------->", "getStoragePermission: ");

        try {
            Method method = this.getClass().getDeclaredMethod("getStoragePermission", View.class);
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            Log.d("-----> 222 ", "getStoragePermission: ----->  "+declaredAnnotations.length);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @PermissionAnnotation(permissionType = {Manifest.permission.READ_SMS,Manifest.permission.READ_CALENDAR})
    public void getPermission2(View view) {

    }

    public void getPluginParams(View view) {
        String className = "com.example.aidltest2.Test";
        Log.d("12344", "getPluginParams: "+ Environment.getExternalStorageDirectory().getAbsolutePath());
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-release-unsigned.apk";
        ClassLoader classLoader = getClassLoader();
        PathClassLoader dexClassLoader = new PathClassLoader(dexPath,null,classLoader);
        try {
            Class localClass = dexClassLoader.loadClass(className);
            Constructor localConstructor = localClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            Method method = localClass.getMethod("getString");
            method.setAccessible(true);
            String result = (String) method.invoke(instance);
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
