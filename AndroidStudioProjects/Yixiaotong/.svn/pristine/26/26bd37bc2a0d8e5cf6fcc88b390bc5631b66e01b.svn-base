package com.zhexinit.yixiaotong.utils;

/**
 * Created on 2015/11/5.
 * Author: wang
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * @Description: Activity管理类：用于管理Activity和退出程序
 */
public class ActivityManagerUtil {

    // Activity栈
    private Stack<WeakReference<Activity>> activityStack;
    // 单例模式
    private volatile static ActivityManagerUtil instance;

    private ActivityManagerUtil() {
        activityStack = new Stack<>();
    }

    /**
     * 单一实例
     */
    public static ActivityManagerUtil getAppManager() {
        ActivityManagerUtil appManager = instance;
        if (appManager == null) {
            synchronized (ActivityManagerUtil.class) {
                appManager = instance;
                if (appManager == null) {
                    appManager = new ActivityManagerUtil();
                    instance = appManager;
                }
            }
        }
        return appManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(new WeakReference<>(activity));
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.size() > 0) {
            WeakReference<Activity> activity = activityStack.lastElement();
            if (activity != null) {
                return activity.get();
            }
        }
        return null;
    }

    /**
     * 查询是否含有此activity
     *
     * @param activity
     * @return
     */
    public boolean isHasActivity(Activity activity) {
        for (WeakReference<Activity> activityWeakReference : activityStack) {
            if (activity.equals(activityWeakReference.get())) {
                return true;
            }
        }
        return false;
    }

    public boolean isHasActivity(Class<?> cls) {
        for (WeakReference<Activity> activityWeakReference : activityStack) {
            if (cls.equals(activityWeakReference.get().getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement().get();
        activityStack.remove(activityStack.size() - 1);
        activity.finish();
    }

    /**
     * 移除指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        for (WeakReference<Activity> activityWeakReference : activityStack) {
            if (activity.equals(activityWeakReference.get())) {
                activityStack.remove(activityWeakReference);
                return;
            }
        }
    }

    public void removeActivity(WeakReference<Activity> activity) {
        activityStack.remove(activity);
    }

    /**
     * 移除指定名称activity
     *
     * @param cls
     */
    public void removeActivity(Class<?> cls) {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity.get() != null) {
                if (activity.get().getClass().equals(cls)) {
                    activityStack.remove(activity);
                    return;
                }
            }

        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        removeActivity(activity);
        activity.finish();
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity.get() != null) {
                if (activity.get().getClass().equals(cls)) {
                    removeActivity(activity);
                    activity.get().finish();
                    return;
                }
            }

        }
    }

    /**
     * 结束除指定类名外的Activity
     */
    public void finishOtherActivity(Class<?> cls) {
        Iterator<WeakReference<Activity>> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next().get();
            if (!activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                try {
                    activityStack.get(i).get().finish();
                }catch (Exception e){
                    LogUtils.e("结束所有activity崩了");
                }
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        AppExit(context, false);
    }

    @SuppressLint("MissingPermission")
    public void AppExit(Context context, boolean exit) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            if (exit)
                System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
