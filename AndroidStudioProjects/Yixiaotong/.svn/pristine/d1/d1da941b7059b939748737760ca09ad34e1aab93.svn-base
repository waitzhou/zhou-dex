package com.zhexinit.yixiaotong.function;

import android.content.Context;

import com.zhexinit.yixiaotong.base.Constant;
import com.zhexinit.yixiaotong.function.map.entity.req.AddRailReq;
import com.zhexinit.yixiaotong.function.map.entity.req.DeleteRailReq;
import com.zhexinit.yixiaotong.function.map.entity.req.UpdateRailReq;
import com.zhexinit.yixiaotong.function.map.entity.resp.PositionResp;
import com.zhexinit.yixiaotong.function.map.entity.resp.RailResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:zhousx
 * date:2018/11/14
 * description:地图相关接口
 */
public class MapWarehouse extends BaseWarehouse {

    private static MapWarehouse instance;

    public static MapWarehouse getInstance(Context context) {
        if (instance == null) {
            instance = new MapWarehouse(context);
        }
        setHeader();
        return instance;
    }

    private MapWarehouse(Context c) {
        super(c);
    }

    /**
     * 获取围栏列表
     *
     * @param id 设备id
     */
    public void getRails(String id, ResultCallBack<BaseResp<List<RailResp>>> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("deviceId", id);
        mHttpManager.postExecute(map, Constant.RAIL_GET, header, callBack);
    }

    /**
     * 添加围栏
     *
     * @param railReq 围栏对象
     * @param callBack 回调
     * */
    public void addRail(AddRailReq railReq,ResultCallBack<BaseResp> callBack){
        mHttpManager.postExecute(railReq,Constant.RAIL_ADD,header,callBack);
    }

    /**
     * 删除围栏
     *
     * @param railReq 围栏对象
     * @param callBack 回调
     * */
    public void deleteRail(DeleteRailReq railReq, ResultCallBack<BaseResp> callBack){
        mHttpManager.postExecute(railReq,Constant.RAIL_DELETE,header,callBack);
    }

    /**
     * 更新围栏
     *
     * @param railReq 围栏对象
     * @param callBack 回调
     * */
    public void updateRail(UpdateRailReq railReq, ResultCallBack<BaseResp> callBack){
        mHttpManager.postExecute(railReq,Constant.RAIL_UPDATE,header,callBack);
    }

    /**
     * 获取child位置信息
     * */
    public void getChildSite(String deviceId,ResultCallBack<BaseResp<PositionResp>> callBack){
        Map<String, String> map = new HashMap<>();
        map.put("deviceId", deviceId);
        mHttpManager.postExecute(map,Constant.POSITION_GET_CHILD,header,callBack);
    }

    /**
     * 获取child列表位置信息
     * */
    public void getChildSiteList(ResultCallBack<BaseResp<List<PositionResp>>> callBack){
        mHttpManager.postExecute(Constant.POSITION_GET_CHILD_LIST,header,callBack);
    }
}
