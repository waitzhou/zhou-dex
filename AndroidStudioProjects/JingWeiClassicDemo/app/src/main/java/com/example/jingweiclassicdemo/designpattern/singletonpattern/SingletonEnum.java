package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public enum  SingletonEnum {

    INSTANCE;

    public static SingletonEnum getInstance() {
        return INSTANCE;
    }

}
