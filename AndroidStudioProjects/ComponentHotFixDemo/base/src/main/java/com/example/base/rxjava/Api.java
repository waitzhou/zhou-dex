package com.example.base.rxjava;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :
 */
public interface Api {

    @GET("/{path}")
    Observable<Object> getExecute(@Path("path") String path, @QueryMap Map<String,Object> map);

    @GET("/{group}/{path}")
    Observable<Object> getExecute(@Path("group") String group, @Path("path") String path, @QueryMap Map<String,Object> map);

    @GET("/{group}/{group1}/{path}")
    Observable<Object> getExecute(@Path("group") String group,@Path("group1") String group1,
                            @Path("path") String path, @QueryMap Map<String,Object> map);

    @GET("/{group}/{group1}/{group2}/{path}")
    Observable<Object> getExecute(@Path("group") String group,@Path("group1") String group1,
                            @Path("group2") String group2,@Path("path") String path, @QueryMap Map<String,Object> map);

    @GET("/{group}/{group1}/{group2}/{group3}/{path}")
    Observable<Object> getExecute(@Path("group") String group,@Path("group1") String group1,@Path("group2") String group2,
                            @Path("group3") String group3,@Path("path") String path, @QueryMap Map<String,Object> map);


    @POST("/{path}")
    Observable<Object> postExecute(@Path("path") String path, @Body RequestBody pRequestBody);

    @POST("/{group}/{path}")
    Observable<Object> postExecute(@Path("group") String group,@Path("path") String path, @Body RequestBody pRequestBody);

    @POST("/{group}/{group1}/{path}")
    Observable<Object> postExecute(@Path("group") String group,@Path("group1") String group1,
                                   @Path("path") String path, @Body RequestBody pRequestBody);

    @POST("/{group}/{group1}/{group2}/{path}")
    Observable<Object> postExecute(@Path("group") String group,@Path("group1") String group1,
                                   @Path("group2") String group2,@Path("path") String path, @Body RequestBody pRequestBody);

    @POST("/{group}/{group1}/{group2}/{group3}/{path}")
    Observable<Object> postExecute(@Path("group") String group,@Path("group1") String group1,
                                   @Path("group2") String group2,@Path("group3") String group3,@Path("path") String path, @Body RequestBody pRequestBody);

    /**
     * 文件下载
     * @param fileUrl 文件下载路径
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

    /**
     * 文件下载，断点续传
     * @param fileUrl 文件下载路径
     * @param range 需要的文件范围（需要传入的Range字符串形如 bytes=200-1000）
     * */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("Range")String range,@Url String fileUrl);
}
