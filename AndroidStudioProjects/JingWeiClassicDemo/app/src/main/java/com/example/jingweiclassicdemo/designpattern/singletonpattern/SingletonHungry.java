package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :  饿汉式单例模式
 */
public class SingletonHungry {

    private SingletonHungry(){}

    private static SingletonHungry INSTANCE;

    static {
        INSTANCE = new SingletonHungry();
    }

    public SingletonHungry getINSTANCE() {
        return INSTANCE;
    }
}
