package com.example.base;

import android.app.Application;

/**
 * Author : ZSX
 * Date : 2019-11-08
 * Description :
 */
public abstract class BaseApplication extends Application {

    public abstract void initModuleData();

    public abstract void initModuleApp();
}
