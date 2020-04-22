package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description : DCL 双重校验单例模式
 */
public class SingletonDCL {

    private SingletonDCL(){}

    private volatile static SingletonDCL INSTANCE;

    public static SingletonDCL getINSTANCE() {
        if(INSTANCE == null){
            synchronized (SingletonDCL.class){
                if(INSTANCE == null){
                    INSTANCE = new SingletonDCL();
                }
            }
        }
        return INSTANCE;
    }
}
