package com.zhexinit.yixiaotong.function.mine.entity;

/**
 * Created by:xukun
 * date:2018/11/16
 * description:
 */
public class DeviceResp {
    public String deviceId;
    public int devicePower; //电量
    public int powerSavingType; //省电模式状态1：开启 2：关闭
    public int deviceRailCount; //绑定的围栏个数
    public String availableTime; //还可以续航的时间，（文字描述）
}
