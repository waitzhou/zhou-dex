package com.example.jingweiclassicdemo.designpattern.proxypattern;

import android.util.Log;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class UserImpl implements IService {
    @Override
    public void add(String s) {
        Log.d("UserImpl", "add: UserImpl"+s);
    }

    @Override
    public void remove(String s) {
        Log.d("UserImpl", "remove: UserImpl"+s);
    }
}
