package com.example.login;

import android.app.Application;

import com.example.base.BaseApp;
import com.example.commonbase.ServiceFactory;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class LoginApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application pApplication) {
        ServiceFactory.getInstance().setIAccountService(new AccountService());
    }

    @Override
    public void initModuleData(Application pApplication) {


    }
}
