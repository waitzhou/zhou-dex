package com.example.componentdemo;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.ApplicationConfig;
import com.example.base.BaseApp;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class MainApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if(isDebug()){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        initModuleApp(this);
        initModuleData(this);
    }

    private boolean isDebug(){
        return BuildConfig.DEBUG;
    }

    @Override
    public void initModuleApp(Application pApplication) {
        for (String moduleApp : ApplicationConfig.moduleApps){
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp) clazz.newInstance();
                baseApp.initModuleApp(this);
            } catch (ClassNotFoundException pE) {
                pE.printStackTrace();
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            } catch (InstantiationException pE) {
                pE.printStackTrace();
            }
        }
    }

    @Override
    public void initModuleData(Application pApplication) {
        for (String moduleApp : ApplicationConfig.moduleApps){
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp) clazz.newInstance();
                baseApp.initModuleData(this);
            } catch (ClassNotFoundException pE) {
                pE.printStackTrace();
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            } catch (InstantiationException pE) {
                pE.printStackTrace();
            }
        }
    }
}
