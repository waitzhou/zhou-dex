package com.zhexinit.yixiaotong.function;

import android.content.Context;

import com.zhexinit.yixiaotong.base.Constant;
import com.zhexinit.yixiaotong.rxjavamanager.http.HttpManager;
import com.zhexinit.yixiaotong.utils.SharePerfUtils;
import com.zhexinit.yixiaotong.utils.StringUtils;

/**
 * Author:zhousx
 * date:2018/11/14
 * description:
 */
public class BaseWarehouse {

    public static String header;
    public static  Context mContext;
    protected HttpManager mHttpManager;

    public BaseWarehouse(Context context) {
        this.mContext = context;
        mHttpManager = HttpManager.getInstance(context);
    }

    static void setHeader() {
        if (StringUtils.isEmpty(header)) {
            header = SharePerfUtils.getString(Constant.KEY.USER_TOKEN);
        }
    }

    public void clearHeader(){
        header = "";
    }
}
