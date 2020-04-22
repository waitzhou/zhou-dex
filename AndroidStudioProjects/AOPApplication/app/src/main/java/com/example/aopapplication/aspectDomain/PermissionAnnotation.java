package com.example.aopapplication.aspectDomain;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author : ZSX
 * Date : 2019-12-18
 * Description : 获取权限注解
 */
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {

    public String[] permissionType();

}
