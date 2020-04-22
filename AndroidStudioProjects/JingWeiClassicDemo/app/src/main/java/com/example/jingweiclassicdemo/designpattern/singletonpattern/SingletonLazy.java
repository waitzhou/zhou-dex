package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :  懒汉式单例模式
 */
public class SingletonLazy {

    private SingletonLazy(){}

    private volatile static SingletonLazy INSTANCE;

    public static SingletonLazy getINSTANCE() {
        synchronized (SingletonLazy.class){
            if(INSTANCE == null){
                INSTANCE = new SingletonLazy();
            }
        }
        return INSTANCE;
    }
}
