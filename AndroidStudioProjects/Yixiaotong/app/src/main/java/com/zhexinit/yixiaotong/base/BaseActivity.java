package com.zhexinit.yixiaotong.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.utils.ActivityManagerUtil;
import com.zhexinit.yixiaotong.utils.StatusBarCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zhexinit.yixiaotong.utils.StatusBarCompat.getStatusBarHeight;


/**
 * Author:zhousx
 * date:2018/6/4
 * description:
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toast mtoast = null;
    protected Dialog progressDialog;
    /**
     * 状态栏基本颜色，可以动态修改
     */
    public int color = R.color.theme_center_color;

    protected int TYPE_HORIZONTAL = 0, TYPE_VERTICAL = 1;

    protected Unbinder mUnbinder;

    protected void setStatusColor(int statusColor) {
        this.color = statusColor;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusColor(color);

        setContentView(getLayoutId());
        StatusBarCompat.compat(this, ContextCompat.getColor(this, color));//设置状态栏
        mUnbinder = ButterKnife.bind(this);//绑定
        ActivityManagerUtil.getAppManager().addActivity(this);//入栈
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏'
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 设置布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 获取recyclerview的布局管理者
     */
    public LinearLayoutManager getLayoutManager() {
        return getLayoutManager(TYPE_VERTICAL, true);
    }

    /**
     * 设置layoutManager
     *
     * @param type     类型：横向纵向
     * @param isScroll 是否滑动 false：不能滑动（外部包括scrollView） true:可以滑动，外部不包括scrollView
     */
    public LinearLayoutManager getLayoutManager(int type, final boolean isScroll) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return isScroll;
            }
        };
        if (type == TYPE_HORIZONTAL) {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        } else {
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return layoutManager;
    }


    /**
     * @Description: Toast的显示方法，防止Toast叠加
     * @param: msg显示的文本信息
     * @return: void
     */
    public void showToast(String msg) {
        if (mtoast == null) {
            mtoast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
            mtoast.setText(msg);
        } else {
            mtoast.setText(msg);
            mtoast.setDuration(Toast.LENGTH_SHORT);
        }
        mtoast.show();
    }

    /**
     * 正在加载，展示加载中
     */
    public void showProgressDialog() {
        showProgressDialog("正在加载中..");
    }

    TextView msg;

    public void showProgressDialog(boolean cancelable) {
        showProgressDialog("正在加载中..",cancelable);
    }

    public void showProgressDialog(String s) {
        showProgressDialog(s,true);
    }

    public void showProgressDialog(String s,boolean cancelable) {
        SHOW_TIME = System.currentTimeMillis();
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.load_dialog);
            progressDialog.setContentView(R.layout.layout_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            msg = progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText(s);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(cancelable);

        } else {
            msg =  progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText(s);
        }
        setBackgroundAlpha(0.7f);
        progressDialog.show();
    }

    /**
     * 设置屏幕背景透明度
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 加载狂最短时间
     * */
    private int SHOW_MIN_TIME = 1000;
    private long SHOW_TIME;
    /**
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (isFinishing()) {
            return;
        }
        if (null != progressDialog && progressDialog.isShowing()) {
            long timeCount = System.currentTimeMillis() - SHOW_TIME;
            if (timeCount > SHOW_MIN_TIME)
                progressDialog.dismiss();
            else
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setBackgroundAlpha(1.0f);
                        progressDialog.dismiss();
                    }
                },SHOW_MIN_TIME - timeCount);
        }
        SHOW_TIME = 0;
    }


    /**
     * 隐藏提示加载
     */
    public void hideProgressDialog(boolean isBackgroudChanged) {
        if (isFinishing()) {
            return;
        }
        if (null != progressDialog && progressDialog.isShowing()) {
            if (isBackgroudChanged)
                setBackgroundAlpha(1.0f);
            progressDialog.dismiss();
        }
    }

    /**
     * view为接收软键盘输入的视图
     */
    public void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断软键盘的状态
     */
    public boolean softInputIsActive() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (softInputIsActive()) {
            hideSoftInput(getWindow().getDecorView().getRootView());
        }
        if (mtoast != null) {
            mtoast.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
        if (mtoast != null)
            mtoast.cancel();
//        UserWarehouse.onDestroy();
//        PetWarehouse.onDestroy();
        if (progressDialog != null)
            progressDialog.cancel();
        ActivityManagerUtil.getAppManager().removeActivity(this);//出栈
    }

}