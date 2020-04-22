package com.example.base.rxjava.entity;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :
 */
public class ResponseBody<T> {

    public int code;
    public String msg;
    public T data;
}
