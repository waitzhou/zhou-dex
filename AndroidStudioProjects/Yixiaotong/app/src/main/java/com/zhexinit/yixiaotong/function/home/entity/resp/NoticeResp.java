package com.zhexinit.yixiaotong.function.home.entity.resp;

/**
 * Author:zhousx
 * date:2018/11/20
 * description:通知返回结果
 */
public class NoticeResp {

       public int messageType; //1常规通知，7是围栏提醒，8是低电量提醒
       public long messageDate;
       public int teacherId;//": 1,
       public String classId;//": null,
       public int schoolId;//": 1,
       public String noticeTitle;//": "测试通知",
       public String noticeContent;//": "哈哈哈哈哈哈哈哈哈哈哈",

       public String childName;
       public String schoolName;
//       public String gradeInfo;
//       public String classInfo;
       public String classOtherName;
       public String attendanceTitle;
       public String attendanceContent;
       public String messageIcon;
       public String deviceId;

}
