package com.example.base.rxjava.manager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.example.base.rxjava.DownloadListener;
import com.example.base.rxjava.helper.RetrofitDownloadHelper;
import com.example.base.rxjava.helper.RetrofitHelper;
import com.example.base.rxjava.AESUtils;
import com.example.base.rxjava.Api;
import com.example.base.rxjava.Config;
import com.example.base.rxjava.ResultCallBack;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :
 */
public class BaseHttpManager {

    private String TAG = this.getClass().getSimpleName();


    protected Gson mGson;
    protected Context mContext;
    protected Api mApi;
    protected CompositeDisposable mCompositeDisposable;
    protected SoftReference<HashMap<String, Disposable>> commonTaskMap;
    protected SoftReference<HashMap<String, Disposable>> downloadTaskMap;

    public BaseHttpManager(Context pContext) {
        mGson = new Gson();
        mContext = pContext;
        mApi = RetrofitHelper.getInstance(pContext).provideRetrofit(Api.class);
        mCompositeDisposable = new CompositeDisposable();
        commonTaskMap = new SoftReference<>(new HashMap<String, Disposable>());
        downloadTaskMap = new SoftReference<>(new HashMap<String, Disposable>());
    }

    /**
     * 添加公共参数
     * <p>
     * 'uuid': openId,
     * 'hsman': systemInfo.brand,
     * 'hstype': systemInfo.model,
     * 'osVer': systemInfo.system,
     * 'platform': systemInfo.platform === 'android' ? 2 : 3,
     * 'packageName': 'com.zhexin.jingweiclassic',
     * 'sdkVerName': '1.0.1',
     * 'sdkVerCode': 2,
     */
    protected Map<String, Object> addCommonParamers(Map<String, Object> pMap) {
        pMap.put("uuid", "laozhouceshizhanghao123");
        pMap.put("hsman", Build.BRAND);
        pMap.put("hstype", Build.MODEL);
        pMap.put("osVer", Build.VERSION.SDK);
        pMap.put("platform", "2");
        pMap.put("packageName", mContext.getPackageName());
        pMap.put("sdkVerName", Config.getVersionName(mContext));
        pMap.put("sdkVerCode", Config.getVersionCode(mContext));
        return pMap;
    }

    /**
     * 获取文件下载的retrofit
     * 需要监听下载进度  所以需要多一个监听参数
     */
    protected Api getDownloadApi(DownloadListener pDownloadListener) {
        return RetrofitDownloadHelper.create(pDownloadListener).provideRetrofit(Api.class);
    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj:任意obj对象
     * @return Map
     */
    public Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                if (field.get(obj) != null)
                    value = field.get(obj);
                else
                    value = "";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * obj添加key和sign后返回RequestBody
     */
    protected RequestBody mapToRequestBody(Map<String, Object> obj) {
        String str = mapToString(obj);
        String encryptString = AESUtils.encrypt(str);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), encryptString);
    }


    /**
     * 传入的obj对象
     * 是否  增加key、sign参数
     * <p>
     * retrun string
     */
    private String mapToString(Map<String, Object> pObjectMap) {
        Map map = null;
        if (pObjectMap == null) {
            map = new HashMap();
        }
        if (map != null) {
            return mGson.toJson(map);
        }
        return "";
    }


    /**
     * 将返回的结果进行Gson解析
     * 返回结果校验，并作统一处理，比如抢登问题、需要统一跳转到登陆界面问题等
     */
    public <T> T checkResponse(Object obj, Type type) {
        if (obj == null) return (T) "response data is null";
        else {
            if (obj instanceof String) {
                return (T) obj;
            }
            return (T) mGson.toJson(obj, type);
        }
    }

    /**
     * 发送post请求
     * */
    protected Observable<Object> sendPostRequest(String path, RequestBody pRequestBody) {
        String[] paths = splitPath(path);
        if (paths != null) {
            switch (paths.length) {
                case 1:
                    return mApi.postExecute(paths[0], pRequestBody);
                case 2:
                    return mApi.postExecute(paths[0], paths[1], pRequestBody);
                case 3:
                    return mApi.postExecute(paths[0], paths[1], paths[2], pRequestBody);
                case 4:
                    return mApi.postExecute(paths[0], paths[1], paths[2], paths[3], pRequestBody);
                case 5:
                    return mApi.postExecute(paths[0], paths[1], paths[2], paths[3], paths[4], pRequestBody);
                default:
                    throw new IndexOutOfBoundsException("请求的path拼接不能超过五个");
            }
        }
        return mApi.postExecute(path, pRequestBody);
    }

    /**
     * 发送get请求
     * */
    protected Observable<Object> sendGetRequest(String path, Map<String,Object> pMap) {
        String[] paths = splitPath(path);
        if (paths != null) {
            switch (paths.length) {
                case 1:
                    return mApi.getExecute(paths[0], pMap);
                case 2:
                    return mApi.getExecute(paths[0], paths[1], pMap);
                case 3:
                    return mApi.getExecute(paths[0], paths[1], paths[2], pMap);
                case 4:
                    return mApi.getExecute(paths[0], paths[1], paths[2], paths[3], pMap);
                case 5:
                    return mApi.getExecute(paths[0], paths[1], paths[2], paths[3], paths[4], pMap);
                default:
                    throw new IndexOutOfBoundsException("请求的path拼接不能超过5个");
            }
        }
        return mApi.getExecute(path, pMap);
    }

    /**
     * 将地址拆分
     */
    protected String[] splitPath(String path) {
        if (path != null && path.length() != 0) {
            String[] strings = path.split("/");
            if (strings.length != 0) {
                return strings;
            } else {
                return new String[]{path};
            }
        }
        return null;
    }

    /**
     * 保存文件到本地
     */
    public File saveFile(ResponseBody response, long currentLength, String destFileDir, String destFileName) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        long range = currentLength;
        RandomAccessFile raf = null;
        try {
            is = response.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            final File file = new File(dir, destFileName);
            raf = new RandomAccessFile(file, "rw");
            raf.seek(range);
            while ((len = is.read(buf)) != -1) {
                raf.write(buf, 0, len);
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (raf != null) raf.close();
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取本地文件的长度
     */
    protected long getCurrentFileLength(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    /**
     * 获取默认下载路径
     */
    public String getDownloadDir() {
        StringBuilder path = new StringBuilder();
        try {
            if (mContext.getExternalCacheDir() != null)
                path = path.append(mContext.getExternalCacheDir().getAbsolutePath());
        } catch (NullPointerException e) {
            path = path.append(mContext.getCacheDir().getPath());
        }
        path = path.append("/downloadFile");
        File file = new File(path.toString());
        if (!file.exists())
            file.mkdirs();
        return path.toString();
    }

    /**
     * 获取远程文件的长度
     */
    protected void getDownloadFileLength(final String fileUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(fileUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlcon = null;
                try {
                    urlcon = (HttpURLConnection) url.openConnection();
                    int fileLength = urlcon.getContentLength();
                    Log.e("test", "fileUrl=" + fileUrl);
                    Log.e("test", "fileLength=" + fileLength);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
