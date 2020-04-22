package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description : 懒汉式基本写法
 */
public class SingletonLazy2 {

    private SingletonLazy2(){}

    private static SingletonLazy2 INSTANCE;

    public static SingletonLazy2 getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new SingletonLazy2();
        }
        return INSTANCE;
    }
}
