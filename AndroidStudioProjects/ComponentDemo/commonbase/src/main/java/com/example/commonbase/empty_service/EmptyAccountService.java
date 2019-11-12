package com.example.commonbase.empty_service;

import com.example.commonbase.service.IAccountService;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class EmptyAccountService implements IAccountService {
    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getAccountId() {
        return null;
    }
}
