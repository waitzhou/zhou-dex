package com.example.commonbase.service;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public interface IAccountService {

    /**
     * 是否已登陆
     * @return bool
     * */
    boolean isLogin();

    /**
     * 获取用户的accountID
     * @return
     * */
    String getAccountId();
}
