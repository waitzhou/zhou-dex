package com.zhexinit.yixiaotong.function.home.entity.resp;

import java.util.List;

/**
 * Created by:xukun
 * date:2019/1/15
 * description:
 */
public class ScheduleResp {
    public int amClassSize;//上午几节课
    public int pmClassSize;//下午几节课
    public List<List<ClassSchedule>>classSchedule;
    public class ClassSchedule {
        public String courseName; //科目名称
        public String weekday; //星期几
        public int classNumber; //第几节课
    }

    public ClassSchedule getClassSchedule(){
        return new ClassSchedule();
    }
}
