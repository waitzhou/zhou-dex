package com.example.aopapplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author : ZSX
 * Date : 2019-12-18
 * Description :
 */
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectAnnotation {

    public String checkAnnotation() default "默认参数";
}
