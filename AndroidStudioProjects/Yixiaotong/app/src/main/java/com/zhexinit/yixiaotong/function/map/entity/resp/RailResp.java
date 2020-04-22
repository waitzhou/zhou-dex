package com.zhexinit.yixiaotong.function.map.entity.resp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author:zhousx
 * date:2018/11/14
 * description:获取围栏列表返回结果
 */
public class RailResp{

    public int id;//": 1,
    public String deviceId;//": "101928374",
    public String railName;//": "家",
    public double longitude;//": 120.272564,
    public double latitude;//": 30.21305,
    public int railRange;//": 200
    public String address;

    public List<TimeList> railTimeList;
}
