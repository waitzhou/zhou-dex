package com.example.commonlib.emptyService;

import com.example.commonlib.service.ILoginService;

/**
 * Author : ZSX
 * Date : 2019-11-07
 * Description :
 */
public class EmptyLoginService implements ILoginService {

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }
}
