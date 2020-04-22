package com.zhexinit.yixiaotong.function.map;

import com.amap.api.location.AMapLocation;

/**
 * Author:zhousx
 * date:2018/6/5
 * description:定位回调
 */
public interface LocationUtilsListener {

    void onLocationSuccess(AMapLocation aMapLocation);

    void onLocationFail(String message);
}
