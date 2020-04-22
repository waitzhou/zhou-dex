package com.zhexinit.yixiaotong.function.mine.entity;

/**
 * Created by:xukun
 * date:2018/11/15
 * description: 孩子信息
 */
public class ChildResp {
    public String childId;
    public int schoolId;
    public int childClassId;
    public int teacherId;
    public String childName;
    public int userRelation; //与当前家长的关系（1是爸爸...）
    public String icon;
    public int sex; //1男，2女，3未知
    public String birthday;
    public String schoolName;
//    public String classInfo;
//    public String gradeInfo;
    public String classOtherName;//班级别名，这个字段是解决班级为"实验班"的这种情况

    public String deviceId;
    public int parentCount; //有多少个家长
    public int devicePower;  //设备的电量
    public boolean primaryAccount; //是否是主账号
    public String parentMobile; //孩子主账号电话号码
    public String masterTeacher; //班主任
    public int deviceStatus; //设备状态，0:未绑定设备，1:正常绑定状态，2:设备处于离线状态
    public int status;//小孩状态，1:在校 2:已毕业 3:转校 4:留级
    public String enrollmentYear;//毕业年份
}