package com.example.commonlib;

import android.content.Intent;

import com.example.commonlib.emptyService.EmptyLoginService;
import com.example.commonlib.service.ILoginService;

/**
 * Author : ZSX
 * Date : 2019-11-07
 * Description :
 */
public class ServiceFactory {

    private ILoginService mLoginService;

    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return Inner.sFactory;
    }

    private static class Inner{
        private static ServiceFactory sFactory = new ServiceFactory();
    }

    public ILoginService getLoginService(){
        if(mLoginService == null){
            mLoginService = new EmptyLoginService();
        }
        return mLoginService;
    }
}
