package com.zhexinit.yixiaotong.function.home.entity.resp;

import java.util.List;

/**
 * Author:zhousx
 * date:2018/11/12
 * description:通讯录resp
 */
public class ContactResp {

    /**
     *   {
     "schoolName": "超神学院",
     "className": "1",
     "gradeName": "一年级",
     "childName": "王小到",
     "teachers": [
     {
     "id": 9,
     "teacherName": "证书徐",
     "mobile": "13515716127",
     "icon": "",
     "email": "",
     "sex": 1,
     "category": "数学",
     "classMaster": false
     }
     ]
     },
     * */
     public String schoolName;//": "超神学院",
     public String classInfo;//": "1",
     public String gradeInfo;//": "一年级",
    public String classOtherName;//班级别名，这个字段是解决班级为"实验班"的这种情况
     public String childName;//": "王小到",

    public boolean isOpen;

    public List<Teachers> teachers;

    public class Teachers{
        public int id;//: 9,
        public String teacherName;//": "证书徐",
        public String mobile;//": "13515716127",
        public String icon;//": "",
        public String email;//": "",
        public int sex;//": 1,
        public String category;//": "数学",
        public boolean classMaster;//": false
        public String teacherTitle;
    }
}