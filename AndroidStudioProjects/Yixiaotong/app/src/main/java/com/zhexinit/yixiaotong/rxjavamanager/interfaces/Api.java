package com.zhexinit.yixiaotong.rxjavamanager.interfaces;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author:@zhousx
 * date: 2018/4/16/15:20.
 * function :retrofit网络请求
 */

public interface Api {

    @GET
    @Headers("AppName:com.zhexinit.yixiaotong")
    Observable<Object> get(@Url String url, @Header("Authorization") String auth,@Header("IMEI") String imei);

    /**
     * 传链接
     */
    @POST
    @Headers("AppName:com.zhexinit.yixiaotong")
    Observable<Object> post(@Url String url, @Header("Authorization") String auth,@Header("IMEI") String imei, @Body RequestBody body);

    /**
     * 不需要参数
     * */
    @POST
    @Headers("AppName:com.zhexinit.yixiaotong")
    Observable<Object> post(@Url String url, @Header("Authorization") String auth,@Header("IMEI") String imei);

    /**
     * 文件下载
     * @param fileUrl
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);
}
