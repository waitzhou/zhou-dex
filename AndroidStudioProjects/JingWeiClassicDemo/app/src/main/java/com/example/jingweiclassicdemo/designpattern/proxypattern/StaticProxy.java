package com.example.jingweiclassicdemo.designpattern.proxypattern;

import android.util.Log;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class StaticProxy implements IService{

    private IService mIService;
    public StaticProxy(IService iService){
        this.mIService = iService;
    }

    @Override
    public void add(String s) {
        Log.d("test", "add:StaticProxy   start  "+s);
        mIService.add(s);
        Log.d("test", "add:StaticProxy   end  "+s);
    }

    @Override
    public void remove(String s) {
        Log.d("test", "remove:StaticProxy   start  "+s);
        mIService.remove(s);
        Log.d("test", "remove:StaticProxy   end  "+s);
    }


}
