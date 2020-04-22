package com.example.login.function;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author : ZSX
 * Date : 2019-11-29
 * Description : class反射测试
 */
public class ClassReflectActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_reflect);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void testMethod(View pView){
        try {
            Class clazz = Class.forName("com.example.login.entity.Worker");
            Constructor constructor = clazz.getConstructor();
            Object worker = constructor.newInstance();

            Method method = clazz.getMethod("setName", String.class);
            Method getName = clazz.getMethod("getName");

            method.invoke(worker,"laozhou");

            Log.d(TAG, "onStart: " + getName.invoke(worker));
        } catch (ClassNotFoundException pE) {
            pE.printStackTrace();
        } catch (NoSuchMethodException pE) {
            pE.printStackTrace();
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
        } catch (InstantiationException pE) {
            pE.printStackTrace();
        } catch (InvocationTargetException pE) {
            pE.printStackTrace();
        }
    }

    public void testFeilds(View view) {
        Class clazz = null;
        try {
            clazz = Class.forName("com.example.login.entity.Worker");
            Field[] fields = clazz.getFields();
            for (Field f:fields) {
                Log.d(TAG, "testFeilds: 111"+f.getName());
            }
            Field[] fields1 = clazz.getDeclaredFields();
            for (Field f:fields1) {
                Log.d(TAG, "testFeilds: 2222"+f.getName());
            }
        } catch (ClassNotFoundException pE) {
            pE.printStackTrace();
        }
    }
}
