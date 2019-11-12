package com.example.commonbase;

import com.example.commonbase.empty_service.EmptyAccountService;
import com.example.commonbase.service.IAccountService;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class ServiceFactory {

    private IAccountService mIAccountService;

    /**
     * 禁止外部创建ServiceFactory对象
     * */
    private ServiceFactory(){

    }

    /**
     * 通过内部静态类创建ServiceFactory对象
     * */
    public static ServiceFactory getInstance(){
        return Inner.sServiceFactory;
    }

    private static class Inner{
        private static ServiceFactory sServiceFactory = new ServiceFactory();
    }

    /**
     * 接手login组件实现的service实例
     * */
    public void setIAccountService(IAccountService pIAccountService){
        this.mIAccountService = pIAccountService;
    }

    /**
     * 返回login组件的service实例
     * */
    public IAccountService getIAccountService(){
        if(mIAccountService == null){
            mIAccountService = new EmptyAccountService();
        }
        return mIAccountService;
    }
}
