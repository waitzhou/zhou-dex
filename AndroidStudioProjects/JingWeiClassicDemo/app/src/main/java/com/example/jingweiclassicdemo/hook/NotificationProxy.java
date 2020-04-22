package com.example.jingweiclassicdemo.hook;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author : ZSX
 * Date : 2020-01-03
 * Description :
 */
public class NotificationProxy implements InvocationHandler {

    private Object mObject;

    public NotificationProxy(Object mObject) {
        this.mObject = mObject;
    }

    public static void hookNotificationManager(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method method = notificationManager.getClass().getDeclaredMethod("getService");
            method.setAccessible(true);
            //获取代理对象
            final Object sService = method.invoke(notificationManager);
            Log.d("[app]", "sService=" + sService);
            /*Class<?> INotificationManagerClazz = Class.forName("android.app.INotificationManager");
            Object proxy = Proxy.newProxyInstance(INotificationManagerClazz.getClassLoader(),
                    new Class[]{INotificationManagerClazz},new NotificationProxy(sService));*/
            Object proxy = getProxy(sService);
            //获取原来的对象
            Field mServiceField = notificationManager.getClass().getDeclaredField("sService");
            mServiceField.setAccessible(true);
            //替换
            mServiceField.set(notificationManager, proxy);
            Log.d("[app]", "Hook NoticeManager成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getProxy(Object object){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = object.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader,interfaces,new NotificationProxy(object));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("[app]", "方法为:" + method.getName());
        /**
         * 做一些业务上的判断
         * 这里以发送通知为准,发送通知最终的调用了enqueueNotificationWithTag
         */
        if (method.getName().equals("enqueueNotificationWithTag")) {
            //具体的逻辑
            for (int i = 0; i < args.length; i++) {
                if (args[i]!=null){
                    Log.d("[app]", "参数为:" + args[i].toString());
                }
            }
            //做些其他事情，然后替换参数之类
            return method.invoke(mObject, args);
        }
        return null;
    }
}
