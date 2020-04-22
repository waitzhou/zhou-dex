package com.example.base.rxjava.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;

import com.example.base.rxjava.AESUtils;
import com.example.base.rxjava.NetwokUtils;
import com.example.base.rxjava.ResultCallBack;
import com.example.base.rxjava.entity.DownloadResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :
 */
public class RetrofitHelper {

    private volatile static RetrofitHelper sInstance;

    static final int DEFAULT_TIMEOUT = 10;//请求超时时长

    static final String BASE_URL = "https://test-jwgx.zhexinit.com";//请求基本地址

    static final String CACHE_NAME = "HttpCache";//存储的内存名称

    static final int CACHE_MAX_SIZE = 1024 * 1024 * 50;//内存最大地址

    private int defaultClient = 0;
    private Context mContext;
    private OkHttpClient.Builder mOKBuilder;
    private Retrofit mRetrofit;
    private SparseArrayCompat<Object> mServiceCache;

    private String TAG = this.getClass().getSimpleName();

    private RetrofitHelper(Context pContext) {
        this.mContext = pContext;
        init();
    }

    public static RetrofitHelper getInstance(Context context) {
        RetrofitHelper serviceFactory = sInstance;
        if (serviceFactory == null) {
            synchronized (RetrofitHelper.class) {
                serviceFactory = sInstance;
                if (serviceFactory == null) {
                    serviceFactory = new RetrofitHelper(context);
                    sInstance = serviceFactory;
                }
            }
        }
        return serviceFactory;
    }

    private void init() {

        initOKHttpClient();
        initRetrofit();
        initArray();
    }

    private void initArray() {
        mServiceCache = new SparseArrayCompat<>();
    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        mRetrofit = new Retrofit.Builder()
                .client(mOKBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void initOKHttpClient() {
        mOKBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(getLogInterceptor())
                .addInterceptor(getResponseInterceptor())
                .addNetworkInterceptor(getCacheInterceptor())
                .sslSocketFactory(getSSLSocketFactory())
                .hostnameVerifier(getHostnameVerifier())
                .cache(getCache());
    }

    /**
     * 对返回的数据进行解密
     * */
    private Interceptor getResponseInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain pChain) throws IOException {
                Request request = pChain.request();
                Response response = pChain.proceed(request);
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if(body != null){
                        BufferedSource source = body.source();
                        source.request(Long.MAX_VALUE);
                        Buffer buffer = source.buffer();
                        Charset charset = Charset.forName("UTF-8");
                        MediaType mediaType = body.contentType();
                        if(mediaType != null){
                            charset = mediaType.charset(charset);
                        }
                        assert charset != null;
                        String s = buffer.clone().readString(charset);
                        String decrypt = AESUtils.decrypt(s);
                        ResponseBody responseBody = ResponseBody.create(mediaType, decrypt.trim());
                        response = response.newBuilder().body(responseBody).build();
                    }
                }
                return response;
            }
        };
    }

    /**
     * 获取内存
     */
    private Cache getCache() {
        File cacheFile = new File(mContext.getExternalCacheDir(), CACHE_NAME);//缓存地址
        return new Cache(cacheFile, CACHE_MAX_SIZE);
    }

    /**
     * 开启log日志
     */
    private Interceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 设置缓存
     */
    private Interceptor getCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                //对request的设置用来指定有网/无网下所走的方式
                //对response的设置用来指定有网/无网下的缓存时长

                Request request = chain.request();
                if (!NetwokUtils.isNetworkConnected(mContext)) {
                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
                    //有网络时则根据缓存时长来决定是否发出请求
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE).build();
                }

                Response response = chain.proceed(request);
                if (NetwokUtils.isNetworkConnected(mContext)) {
                    //有网络情况下，超过1分钟，则重新请求，否则直接使用缓存数据
                    int maxAge = 60; //缓存一分钟
                    String cacheControl = "public,max-age=" + maxAge;
                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
                    //将其超时时间maxAge设为0即可
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma").build();
                } else {
                    //无网络时直接取缓存数据，该缓存数据保存1周
                    int maxStale = 60 * 60 * 24 * 7 * 3;  //1周
                    return response.newBuilder()
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .removeHeader("Pragma").build();
                }

            }
        };
    }

    /**
     * https不安全证书
     */
    private SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    /**
     * 获取对应的服务
     *
     * @param service 服务
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T provideRetrofit(Class<T> service) {
        T t = (T) mServiceCache.get(service.hashCode());
        if (t == null) {
            t = mRetrofit.create(service);
            mServiceCache.put(service.hashCode(), t);
        }
        return t;
    }
}
