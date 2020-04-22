package com.example.base.rxjava.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.base.rxjava.DownloadListener;
import com.example.base.rxjava.ResultCallBack;
import com.example.base.rxjava.entity.BaseRequest;
import com.example.base.rxjava.entity.MultiRequestBody;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :  rxjava + retrofit 网络请求主要类
 */
public class HttpManager extends BaseHttpManager {

    private static HttpManager sInstance;

    public HttpManager(Context pContext) {
        super(pContext);
    }

    private String TAG = this.getClass().getSimpleName();

    /**
     * 存在并发请求的情况，故不使用单利模式
     */
    public static HttpManager getInstance(Context context) {
        if (sInstance == null) {
            HttpManager delegate = null;
            synchronized (HttpManager.class) {
                if (sInstance == null) {
                    delegate = new HttpManager(context);
                    sInstance = delegate;
                }
            }
            sInstance = new HttpManager(context);
        }
        return sInstance;
    }

    /**
     * Get请求   -------- > 尚未测试
     *
     * @param path     请求路径
     * @param map      请求参数
     * @param callBack 请求回调
     */
    public <T> void getExecute(@NonNull final String path, final Map<String, Object> map, @NonNull final ResultCallBack<T> callBack) {
        Observable.just(path)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(String pS) throws Exception {
                        return sendGetRequest(path, map);
                    }
                })
                .map(new Function<Object, T>() {
                    @Override
                    public T apply(Object pO) throws Exception {
                        return checkResponse(pO, callBack.mType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T pT) {
                        callBack.onSuccess(pT);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        callBack.onFinish("");
                    }
                });
    }

    /**
     * post请求
     *
     * @param path     请求路径
     * @param callBack 请求回调
     */
    public <T> Disposable postExecute(@NonNull String path, @NonNull ResultCallBack<T> callBack) {
        return this.postExecute(new HashMap<String, Object>(), path, callBack);
    }

    /**
     * post请求
     *
     * @param request  参数对象，最终会转换成map
     * @param path     请求路径
     * @param callBack 请求回调
     */
    public <T> Disposable postExecute(BaseRequest request, @NonNull String path, @NonNull ResultCallBack<T> callBack) {
        return this.postExecute(objectToMap(request), path, callBack);
    }

    /**
     * post请求
     *
     * @param map      参数map对象
     * @param path     请求路径
     * @param callBack 请求回调
     */
    @SuppressLint("CheckResult")
    private <T> Disposable postExecute(Map<String, Object> map, @NonNull final String path, @NonNull final ResultCallBack<T> callBack) {
        if (map == null) {
            throw new NullPointerException("map is empty，please check params");
        }
        Map<String, Object> objectMap = addCommonParamers(map);
        final RequestBody requestBody = mapToRequestBody(objectMap);
        Observable.just(requestBody)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<RequestBody, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(RequestBody pRequestBody) throws Exception {
                        return sendPostRequest(path, requestBody);
                    }
                })
                .map(new Function<Object, T>() {
                    @Override
                    public T apply(@NonNull Object object) throws Exception {
                        return checkResponse(object, callBack.mType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.isDisposed());
                        if (!d.isDisposed()) {
                            mCompositeDisposable.add(d);
                            if (commonTaskMap != null && commonTaskMap.get() != null) {
                                commonTaskMap.get().put(path, d);
                            }
                        }
                    }

                    @Override
                    public void onNext(T t) {
                        callBack.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        callBack.onFinish("任务执行onComplete");
                    }
                });
        return (commonTaskMap != null && commonTaskMap.get() != null) ? commonTaskMap.get().get(path) : null;
    }

    /**
     * 文件下载  下载到默认文件夹
     *
     * @param url      下载链接
     * @param fileName 下载的文件名
     * @param pBack    下载回调
     */
    public Disposable downloadFile(final String url, final String fileName, final DownloadListener pBack) {
        return this.downloadFile(url, getDownloadDir(), fileName, pBack);
    }

    /**
     * 文件下载
     *
     * @param url      下载链接
     * @param dirName  文件夹名
     * @param fileName 下载的文件名
     * @param pBack    下载回调
     */
    public Disposable downloadFile(final String url, final String dirName, final String fileName, final DownloadListener pBack) {
        final long currentFileLength = getCurrentFileLength(dirName + File.separator + fileName);
        Observable.just(url)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(String pS) throws Exception {
                        if (currentFileLength != 0) {
                            try {
                                URL fileUrl = new URL(url);
                                HttpURLConnection urlcon = (HttpURLConnection) fileUrl.openConnection();
                                int fileLength = urlcon.getContentLength();
                                Log.e("test", "fileLength=" + fileLength + "   currentFileLength = " + currentFileLength);
                                if (fileLength != -1 && fileLength > currentFileLength) {
                                    //文件未下载完成
                                    String range = "bytes=" + currentFileLength + "-" + fileLength;
                                    return getDownloadApi(pBack).download(range, url);
                                } else if (fileLength != -1 && fileLength == currentFileLength) {
                                    //已下载的文件大小大于待下载的文件大小，文件已下载完成
                                    new Handler(mContext.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "文件已下载", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    return null;
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return getDownloadApi(pBack).download(url);
                    }
                })
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody pResponseBody) throws Exception {
                        Log.d(TAG, "apply:  saveFile");
                        return saveFile(pResponseBody, currentFileLength, dirName, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe:download = " + d.isDisposed());
                        if (!d.isDisposed()) {
                            if (downloadTaskMap != null && downloadTaskMap.get() != null) {
                                downloadTaskMap.get().put(fileName, d);
                            }
                        }
                    }

                    @Override
                    public void onNext(File pFile) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: download");
                        pBack.fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: download = " + dirName + File.separator + fileName
                                + "  length = " + getCurrentFileLength(dirName + File.separator + fileName));
                        pBack.downloadFinish(dirName + File.separator + fileName);

                    }
                });
        return (downloadTaskMap != null && downloadTaskMap.get() != null) ? downloadTaskMap.get().get(fileName) : null;
    }

    /**
     * 多请求任务
     */
    @SuppressLint("CheckResult")
    public void doMultiExecute(List<MultiRequestBody> pBodyList, final ResultCallBack<List<Object>> pBack) {
        if (pBodyList != null) {
            List<Observable<Object>> observableList = doExecuteStyle(pBodyList);
            Iterable<Observable<Object>> it = this.iterableReverseList(observableList);
            Observable
                    .zip(it, new Function<Object[], List<Object>>() {
                        @Override
                        public List<Object> apply(Object[] pObjects) throws Exception {
                            return new ArrayList<>(Arrays.asList(pObjects));
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver(pBack));
        }
    }

    /**
     * list 转化成 iterable
     */
    private <T> Iterable<T> iterableReverseList(final List<T> l) {
        return new Iterable<T>() {
            @NotNull
            public Iterator<T> iterator() {
                return l.iterator();
            }
        };
    }

    /**
     * 获取observer对象
     */
    private Observer<List<Object>> getObserver(final ResultCallBack<List<Object>> pBack) {
        return new Observer<List<Object>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Object> pObjects) {
                pBack.onSuccess(pObjects);
            }

            @Override
            public void onError(Throwable e) {
                pBack.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                pBack.onFinish("");
            }
        };
    }

    /**
     * 判断请求类型，执行响应的请求
     */
    private List<Observable<Object>> doExecuteStyle(List<MultiRequestBody> pBodyList) {
        List<Observable<Object>> observableList = new ArrayList<>();
        for (MultiRequestBody body : pBodyList) {
            switch (body.style) {
                case GET:
                    observableList.add(getMulti(body));
                    break;
                case POST:
                    observableList.add(postMulti(body));
                    break;
                default:
                    observableList.add(postMulti(body));
                    break;
            }
        }
        return observableList;
    }

    /**
     * 多请求中的post请求
     */
    private Observable<Object> postMulti(final MultiRequestBody pMultiRequestBody) {
        Map<String, Object> map;
        if (pMultiRequestBody.baseRequest != null) {
            map = objectToMap(pMultiRequestBody.baseRequest);
        } else if (pMultiRequestBody.map != null) {
            map = pMultiRequestBody.map;
        } else {
            map = new HashMap<>();
        }
        Map<String, Object> objectMap = addCommonParamers(map);
        final RequestBody requestBody = mapToRequestBody(objectMap);
        Log.d(TAG, "postMulti: " + mGson.toJson(map));
        return Observable.just("").subscribeOn(Schedulers.io()).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String pS) throws Exception {
                return sendPostRequest(pMultiRequestBody.path, requestBody);
            }
        });
    }

    /**
     * 多请求中的get请求
     */
    private Observable<Object> getMulti(MultiRequestBody pMultiRequestBody) {
        if (pMultiRequestBody.map == null) {
            pMultiRequestBody.map = new HashMap();
        }
        return sendGetRequest(pMultiRequestBody.path, pMultiRequestBody.map).subscribeOn(Schedulers.io());
    }

    /**
     * 取消所有任务
     */
    public void cancelAllTask() {
        if (mCompositeDisposable.size() != 0) {
            mCompositeDisposable.dispose();
        }
    }
}
