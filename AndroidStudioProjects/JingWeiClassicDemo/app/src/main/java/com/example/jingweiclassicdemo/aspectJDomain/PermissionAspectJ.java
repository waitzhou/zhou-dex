package com.example.jingweiclassicdemo.aspectJDomain;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.jingweiclassicdemo.activity.PermissionPlaceHolderActivity;
import com.example.jingweiclassicdemo.annotations.PermissionAnnotation;
import com.example.jingweiclassicdemo.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;

/**
 * Author : ZSX
 * Date : 2019-12-30
 * Description :
 */
@Aspect
public class PermissionAspectJ {

    private String TAG = this.getClass().getSimpleName();

    private final String PointCut_GetPermission = "execution(@com.example.jingweiclassicdemo.annotations.PermissionAnnotation * *(..))";

    @Pointcut(PointCut_GetPermission)
    public void getPermissionMethod(){}

    @Around("getPermissionMethod()")
    public Object executionPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        if(Build.VERSION.SDK_INT < 23){
            Log.d(TAG, "executionPermission: 不需要申请权限");
            return null;
        }
        Object object = joinPoint.getThis();
        Context context = getContext(object);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        PermissionAnnotation annotation = methodSignature.getMethod().getAnnotation(PermissionAnnotation.class);
        assert annotation != null;
        String[] permissions = annotation.permissionType();
        if(!PermissionUtils.getInstance().hasAllPermission(context,permissions)){
            jumpToPlaceHolderActivity(context,permissions,joinPoint);
        }else {
            return joinPoint.proceed();
        }
        return null;
    }

    private void jumpToPlaceHolderActivity(Context context, String[] strings, final ProceedingJoinPoint joinPoint) {
        PermissionPlaceHolderActivity.enter(context, strings, new PermissionPlaceHolderActivity.PermissionsListener() {
            @Override
            public void onPermissionListener(@NonNull String[] permissions, @NonNull int[] grantResults) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        Log.d(TAG, "onPermissionListener: "+permissions[i]+" 权限被拒绝!");
                        arrayList.add(permissions[i]);
                    }
                }
                if(arrayList.size() == 0){
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }else {
                    showDialog();
                    Log.d(TAG, "onPermissionListener: 获取部分权限失败在这里处理");
                }
            }
        });
    }

    /**
     * 部分权限请求失败弹框
     * */
    private void showDialog(){

    }

    private Context getContext(Object object) {
        Context context = null;
        if (object instanceof Activity || object instanceof Application || object instanceof Service) {
            context = (Context) object;
        } else if (object instanceof ContentProvider) {
            context = ((ContentProvider) object).getContext();
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        } else if (object instanceof Dialog) {
            context = ((Dialog) object).getContext();
        } else if (object instanceof View) {
            context = ((View) object).getContext();
        }
        return context;
    }
}
