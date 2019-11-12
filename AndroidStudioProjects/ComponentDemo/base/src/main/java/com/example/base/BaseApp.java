package com.example.base;

import android.app.Application;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public abstract class BaseApp extends Application {

    public abstract void initModuleApp(Application pApplication);

    public abstract void initModuleData(Application pApplication);
}
