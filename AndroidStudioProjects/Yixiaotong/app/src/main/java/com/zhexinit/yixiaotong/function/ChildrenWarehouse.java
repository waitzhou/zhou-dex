package com.zhexinit.yixiaotong.function;

import android.content.Context;

import com.zhexinit.yixiaotong.base.Constant;
import com.zhexinit.yixiaotong.function.home.entity.req.HomeworkReq;
import com.zhexinit.yixiaotong.function.home.entity.req.VacateReq;
import com.zhexinit.yixiaotong.function.home.entity.req.VacateSubmitReq;
import com.zhexinit.yixiaotong.function.home.entity.resp.AttendanceResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.AttendanceStatisticResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.ClassScheduleResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.ContactResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.HomeFunctionResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.HomewordDetailResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.HomeworkResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.NoticeResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.ScheduleResp;
import com.zhexinit.yixiaotong.function.home.entity.resp.VacateResp;
import com.zhexinit.yixiaotong.function.mine.entity.DetailChildResp;
import com.zhexinit.yixiaotong.function.mine.entity.ParentResp;
import com.zhexinit.yixiaotong.function.mine.entity.RelationsResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by:xukun
 * date:2018/11/15
 * description:
 */
public class ChildrenWarehouse extends BaseWarehouse {
    private static ChildrenWarehouse instance;

    public static ChildrenWarehouse getInstance(Context context) {
        mContext=context;
        if (instance == null) {
            instance = new ChildrenWarehouse(context);
        }
        setHeader();
        return instance;
    }

    private ChildrenWarehouse(Context context) {
        super(context);
    }

    /**
     * 获取孩子详情接口
     */
    public void getChildInfo(Map map, ResultCallBack<BaseResp<DetailChildResp>> callBack) {
        mHttpManager.postExecute(map, Constant.GET_CHILD_INFO, header, callBack);
    }

    /**
     * 校徽开启省电开关
     */
    public void setPowerSaving(Map map, ResultCallBack<BaseResp> callBack) {
        mHttpManager.postExecute(map, Constant.SET_POWER_SAVING, header, callBack);
    }

    /**
     * 获取孩子家长列表信息
     */
    public void getChildParent(Map map, ResultCallBack<BaseResp<List<ParentResp>>> callBack) {
        mHttpManager.postExecute(map, Constant.GET_CHILD_PARENT, header, callBack);
    }

    /**
     * 添加孩子家长
     */
    public void addParent(Map map, ResultCallBack<BaseResp> callBack) {
        mHttpManager.postExecute(map, Constant.ADD_PARENT, header, callBack);
    }

    /**
     * 获取孩子与家长的所有关系列表
     */
    public void getRelation(Map map, ResultCallBack<BaseResp<List<RelationsResp>>> callBack) {
        mHttpManager.postExecute(map, Constant.GET_RELATION, header, callBack);
    }

    /**
     * 删除孩子家长
     */
    public void deleteParent(Map map, ResultCallBack<BaseResp> callBack) {
        mHttpManager.postExecute(map, Constant.DELETE_PARENT, header, callBack);
    }

    /**
     * 获取请假列表界面
     */
    public void getVacateData(VacateReq req, ResultCallBack<BaseResp<List<VacateResp>>> callBack) {
        mHttpManager.postExecute(req, Constant.VACATE_GET, header, callBack);
    }

    /**
     * 添加请假
     */
    public void vacateSubmit(VacateSubmitReq req, ResultCallBack<BaseResp> callBack) {
        mHttpManager.postExecute(req, Constant.VACATE_SUBMIT, header, callBack);
    }

    /**
     * 获取详细信息
     */
    public void getVacateDetailData(Map map, ResultCallBack<BaseResp<VacateResp>> callBack) {
        mHttpManager.postExecute(map, Constant.VACATE_DETAIL, header, callBack);
    }

    /**
     * 获取首页
     */
    public void getHomeNiticeData( ResultCallBack<BaseResp<HomeFunctionResp>> callBack) {
        mHttpManager.postExecute(Constant.HOME_ALL_NO_TICE, header, callBack);
    }

    /**
     * 获取通讯录
     * */
    public void getContactData(ResultCallBack<BaseResp<List<ContactResp>>> callBack){
        mHttpManager.postExecute(Constant.CONTACT_DATA,header,callBack);
    }

    /**
     * 获取考勤列表界面
     * */
    public void getAttendanceData(Map<String,Integer> map, ResultCallBack<BaseResp<List<AttendanceResp>>> callBack){
        mHttpManager.postExecute(map,Constant.ATTENDANCE_DATA,header,callBack);
    }

    /**
     * 获取考勤数据统计
     * */
    public void getAttendanceStatistical(Map<String,String> map,ResultCallBack<BaseResp<AttendanceStatisticResp>> callBack){
        mHttpManager.postExecute(map,Constant.ATTENDANCE_STATISTICAL,header,callBack);
    }

    /**
     * 获取课程表数据
     * */
    public void getClassSchueduleData(Map<String,String> map,ResultCallBack<BaseResp<ScheduleResp>> callBack){
        mHttpManager.postExecute(map,Constant.SCHEDULE_DATA,header,callBack);
    }

    /**
     * 获取作业列表数据
     * */
    public void getHomeworkData(HomeworkReq homeworkReq, ResultCallBack<BaseResp<List<HomeworkResp>>> callBack){
        mHttpManager.postExecute(homeworkReq,Constant.HOMEWORK_LIST,header,callBack);
    }

    /**
     * 获取作业详情
     * */
    public void getHomeworkDetail(Map<String,String> map, ResultCallBack<BaseResp<List<HomewordDetailResp>>> callBack){
        mHttpManager.postExecute(map,Constant.HOMEWORK_DETAIL,header,callBack);
    }

    /**
    * 获取通知数据
    * */
    public void getNoticeData(Map<String,Integer> map, ResultCallBack<BaseResp<List<NoticeResp>>> callBack){
        mHttpManager.postExecute(map,Constant.NOTICE_DATA,header,callBack);
    }
}
