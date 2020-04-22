package com.zhexinit.yixiaotong.rxjavamanager.entity.req;

import java.lang.reflect.Type;

/**
 * Author:@zhousx
 * date: 2018/2/26/15:20.
 * function :
 */

public class MultiRequestModel {

    public MultiRequestModel(){}

    /**
     * 请求类型
     */
    public enum RequestType {
        Post(1),Get(2);

        int value;

        RequestType(int value) {
            this.value = value;
        }
    }

    public RequestType mRequestType;

    public BaseRequest baseRequestModel;

    public String path;

    public String header;

    public Type type;

    public MultiRequestModel(RequestType requestType,String path, BaseRequest baseRequestModel) {
        mRequestType = requestType;
        this.baseRequestModel = baseRequestModel;
        this.path = path;
    }

    public MultiRequestModel(RequestType requestType,String path,BaseRequest baseRequestModel,Type type) {
        mRequestType = requestType;
        this.baseRequestModel = baseRequestModel;
        this.type = type;
        this.path = path;
    }
}
