package com.example.jingweiclassicdemo.designpattern.singletonpattern;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class SingletonInnerClass {

    private SingletonInnerClass(){}

    private static class Inner{
        private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
    }

    public static SingletonInnerClass getINSTANCE() {
        return Inner.INSTANCE;
    }
}
