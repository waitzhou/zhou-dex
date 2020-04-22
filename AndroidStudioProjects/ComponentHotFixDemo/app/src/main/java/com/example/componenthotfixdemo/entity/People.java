package com.example.componenthotfixdemo.entity;

/**
 * Author : ZSX
 * Date : 2019-11-22
 * Description :
 */
public class People {

    public String name = "老周的弱引用";

    public int times = 0;

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", times=" + times +
                '}';
    }
}
