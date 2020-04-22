package com.example.jingweiclassicdemo.designpattern.observerpattern;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class MyObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null)
            Log.d("--------------->", "update: " + arg.toString());
        else
            Log.d("-------------->", "update: arg is null");
    }
}
