package com.zhexinit.yixiaotong.function.mine.entity;


import java.util.List;

/**
 * Created by:xukun
 * date:2018/11/14
 * description: 登录成功返回的信息
 */
public class LoginSuccessResp{
    public String token;
    public String userId;
    public List<InitResp> children; //关联的孩子
    public boolean vistor; //是否是游客
}
