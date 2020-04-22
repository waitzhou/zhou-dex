package com.example.base.rxjava.entity;

import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.http.POST;


/**
 * Author : ZSX
 * Date : 2019-12-05
 * Description : 多任务请求体
 */
public class  MultiRequestBody {

    public BaseRequest baseRequest = null;//请求对象

    public Type type;//返回的数据类型

    public Map<String,Object> map = null;//请求map

    public String path;//请求路径

    public RequestStyle style = RequestStyle.POST;

}
