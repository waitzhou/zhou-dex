package com.zhexinit.yixiaotong.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.function.BaseResp;
import com.zhexinit.yixiaotong.function.UserWarehouse;
import com.zhexinit.yixiaotong.function.home.MainActivity;
import com.zhexinit.yixiaotong.function.home.fragment.NoBindChildFragment;
import com.zhexinit.yixiaotong.function.mine.activity.LoginWithSMSActivity;
import com.zhexinit.yixiaotong.function.mine.entity.LoginSuccessResp;
import com.zhexinit.yixiaotong.function.mine.entity.UserInfoResp;
import com.zhexinit.yixiaotong.rxjavamanager.interfaces.ResultCallBack;
import com.zhexinit.yixiaotong.utils.ActivityManagerUtil;
import com.zhexinit.yixiaotong.utils.SharePerfUtils;
import com.zhexinit.yixiaotong.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Author:zhousx
 * date:2018/11/5
 * description:引导页
 */
public class SplashActivity extends AppCompatActivity {

    private long mStartTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ActivityManagerUtil.getAppManager().addActivity(this);//入栈
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏'
        init();
    }




    protected void init() {
        mStartTime = System.currentTimeMillis();
        if (StringUtils.isEmpty(SharePerfUtils.getString(Constant.KEY.USER_PHONE_NUM))) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginWithSMSActivity.class));
                    finish();
                }
            }, 2000);
        } else {
            //initUserInfo();
            getUserInfo();
        }
    }

    /**
     * 后台接口需要init获取相关孩子，token信息
     */
    private void initUserInfo() {
        Map<String, Object> map = new HashMap<>();
        UserWarehouse.getInstance(this).initInfo(map, new ResultCallBack<BaseResp<LoginSuccessResp>>() {
            @Override
            public void onSuccess(BaseResp<LoginSuccessResp> baseResp) {
                if (baseResp.code == 0) {
                    SharePerfUtils.putString(Constant.KEY.INIT_INFO, new Gson().toJson(baseResp.result));
                    getUserInfo();
                } else {
                   // showToast("用户信息失效，请登录");
                    loginFail();
                }
            }

            @Override
            public void onFail(String response) {
                loginFail();
            }
        });
    }

    /**
     * init 失败或者请求用户数据失败
     */
    private void loginFail() {
        String string = SharePerfUtils.getString(Constant.KEY.USER_PHONE_NUM);
        SharePerfUtils.clearPref();
        UserWarehouse.getInstance(this).clearHeader();
        Intent intent = new Intent(SplashActivity.this, LoginWithSMSActivity.class);
        intent.putExtra("phoneNumber", string);
        startActivity(intent);
        finish();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        UserWarehouse.getInstance(this).getUserInfo(new HashMap(), new ResultCallBack<BaseResp<UserInfoResp>>() {
            @Override
            public void onSuccess(BaseResp<UserInfoResp> userInfoResp) {
                if (userInfoResp.code == 0) {
                    SharePerfUtils.putString(Constant.KEY.USER_INFO, new Gson().toJson(userInfoResp.result));
                    if (System.currentTimeMillis() - mStartTime > 2000) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        }, 2000 - (System.currentTimeMillis() - mStartTime));
                    }
                } else {
                    loginFail();
                }
            }

            @Override
            public void onFail(String response) {
                loginFail();
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}