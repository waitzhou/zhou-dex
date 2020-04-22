package com.example.jingweiclassicdemo.designpattern.proxypattern;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class DynamicProxy implements InvocationHandler {

    private IService target;
    public DynamicProxy(IService o){
        this.target = o;
    }

    public UserImpl getProxy(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class[] classes = target.getClass().getInterfaces();
        return (UserImpl) Proxy.newProxyInstance(classLoader,classes,this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("test", "invoke: DynamicProxy methodName = "+method.getName() +"  start  "+ args[0]);
        Object result = method.invoke(target,args);
        Log.d("test", "invoke: DynamicProxy  methodName = "+method.getName() +"   end");
        return result;
    }
}
