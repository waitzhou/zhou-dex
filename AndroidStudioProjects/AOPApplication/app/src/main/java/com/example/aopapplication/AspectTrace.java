package com.example.aopapplication;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;

/**
 * Author : ZSX
 * Date : 2019-12-18
 * Description :
 */
@Aspect
public class AspectTrace {

    //切点为指定方法
    private static final String POINTCUT_FILED = "execution(* com.example.aopapplication.MainActivity.aspectClick(..))";

    //切点为指定变量
    private static final String POINTCUT_FILED_WITH_PARAMS = "set(int com.example.aopapplication.MainActivity.mTest) && args(newValue) && target(t)";

    private static final String POINTCUT_ANNOTATION_1 = "execution(@com.example.aopapplication.AspectAnnotation * *(..))";

    @Pointcut(POINTCUT_FILED)
    public void annontationONMethodTrace(){}

    @Pointcut(POINTCUT_ANNOTATION_1)
    public void annotationTest(){}

    @Around("annotationTest()")
    public Object dealTest(ProceedingJoinPoint joinPoint) throws Throwable {
        //方法执行前
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Log.d("dealTest", "dealTest:111 "+className +"   "+methodName);

        AspectAnnotation declaredAnnotation = methodSignature.getMethod().getAnnotation(AspectAnnotation.class);
        assert declaredAnnotation != null;
        String s = declaredAnnotation.checkAnnotation();
        Log.d("dealTest", "dealTest: value = "+s);
        Object object = joinPoint.proceed();
        return object;
    }

    /**
     * 监听指定方法
     * */
    @Around("annontationONMethodTrace()")
    public Object weaveOnMethodJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        Log.i("MainActivity", "before joinPoint proceed className = " + className + " methodName = " + methodName);

        Object result  = joinPoint.proceed();
        Log.i("MainActivity", "after joinPoint proceed className = " + className + " methodName = " + methodName);

        return result;
    }

    /**
     * 监听指定变量改变
     * */
    //@Before(POINTCUT_FILED_WITH_PARAMS)
    public void onFeild(JoinPoint joinPoint,Object newValue,Object t) throws IllegalAccessException {
        FieldSignature fieldSignature = (FieldSignature) joinPoint.getSignature();
        String fileName = fieldSignature.getName();
        Field field = fieldSignature.getField();
        field.setAccessible(true);
        Class clazz = fieldSignature.getFieldType();
        String clazzName = clazz.getSimpleName();

        Object oldValue = field.get(t);

        Log.i("MainActivity",
                "\nonFiled value = " + newValue.toString()
                        + "\ntarget = " + t.toString()
                        + "\n fieldSignature =" + fieldSignature.toString()
                        + "\nfield = " + field.toString()
                        + "\nFileName = " + fileName
                        + "\nclazzName = " + clazzName
                        + " \noldValue = " + oldValue.toString() );
    }
}
