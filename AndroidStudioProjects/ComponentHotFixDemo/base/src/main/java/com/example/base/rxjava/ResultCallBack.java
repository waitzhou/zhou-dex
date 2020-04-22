package com.example.base.rxjava;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author:@zhousx
 * date: 2018/4/16/15:39.
 * function :回调
 */

public abstract class ResultCallBack<T> {

    public Type mType = String.class;

    public ResultCallBack() {
        mType = getSuperclassTypeParameter(getClass()) == null ? String.class :  getSuperclassTypeParameter(getClass());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            //throw new RuntimeException("Missing type parameter.");
            return String.class;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 请求成功回调
     *
     * @param t 成功的model
     */
    public abstract void onSuccess(T t);

    /**
     * 请求失败回调
     *
     * @param error 失败信息
     */
    public abstract void onError(String error);

    /**
     * 请求失败回调
     *
     * @param message 完成、取消的时候执行
     */
     public void onFinish(String message){};

     public void inProgress(long currentBytes, long total, boolean finish){};




}
