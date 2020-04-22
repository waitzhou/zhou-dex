package com.zhexinit.yixiaotong.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * created:xukun
 * date:2018/11/7
 * description:验证码倒计时
 */
public class TimerCount {

    private long millisInFuture, countDownInterval;
    private View mView;
    private String finishText;
    CountDownTimer timer;
    boolean type;//是不是需要倒计时结束改变按钮颜色

    public TimerCount(View view,boolean type){
        this(61*1000,1000,view,type);
    }

    public TimerCount(long millisInFuture, long countDownInterval, View view,boolean type) {
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.mView = view;
        this.type=type;
        if (view instanceof Button || mView instanceof TextView)
            init();
    }

    public void init() {
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (type)mView.setSelected(true);
                mView.setClickable(false);
                if (mView instanceof Button) {
                    Button button = (Button) mView;
                    button.setText(String.format("%1$ss", String.valueOf(millisUntilFinished / 1000)));
                } else {
                    TextView textView = (TextView) mView;
                    textView.setText(String.format("%1$ss", String.valueOf(millisUntilFinished / 1000)));
                }
            }

            @Override
            public void onFinish() {
                mView.setClickable(true);
                if (type)mView.setSelected(false);
                if (mView instanceof Button) {
                    Button button = (Button) mView;
                    button.setText("获取验证码");
                } else {
                    TextView textView = (TextView) mView;
                    textView.setText("获取验证码");
                }
            }
        };
    }

    public void start() {
        if (timer != null)
            timer.start();
    }

    public void cancel() {
        if (timer != null) {
            if(mView != null){
                mView.setClickable(true);
                if (type)mView.setSelected(false);
            }
            onFinish();
            timer.cancel();
        }
    }

    public void onFinish(){
        if(timer != null)
            timer.onFinish();
    }

    public void setFinishTest(String s) {
        this.finishText = s;
    }
}