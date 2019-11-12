package com.example.login;

import com.example.commonbase.service.IAccountService;

/**
 * Author : ZSX
 * Date : 2019-11-02
 * Description :
 */
public class AccountService implements IAccountService {

    @Override
    public boolean isLogin() {
        return true;
    }

    @Override
    public String getAccountId() {
        return "用户名ID：老周";
    }
}
