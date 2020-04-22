package com.example.jingweiclassicdemo.designpattern.observerpattern;

import java.util.Observable;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class MyObserable extends Observable {

    private String arg1;


    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
        setChanged();
    }

    public void notifyDataChanged(){
        notifyDataChanged(null);
    }

    public void notifyDataChanged(Object obj){
        setChanged();
        notifyObservers(obj);
    }
}
