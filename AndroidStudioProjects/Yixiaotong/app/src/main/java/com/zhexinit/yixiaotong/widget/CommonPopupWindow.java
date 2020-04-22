package com.zhexinit.yixiaotong.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;


/**
 * Author:@zhousx
 * date: 2017/9/28/16:19.
 * function :常用底部弹出框
 */

public class CommonPopupWindow extends PopupWindow {

    private Activity mActivity;
    private View mView;

    private TextView text1, text2, text3;

    public CommonPopupWindow(Activity activity, View view) {
        mActivity = activity;
        mView = view;
        findView();
    }

    public CommonPopupWindow(Activity activity, View view, String text1, String text2){
        this(activity,view);
        setText1(text1);
        setText2(text2);
    }

    public CommonPopupWindow(Activity activity, View view, String text1, String text2, String text3){
        this(activity, view, text1, text2);
        setText3(text3);
    }

    private void findView() {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popup_window_bottom, null);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);

        text1 = contentView.findViewById(R.id.tv_camera);
        text2 = contentView.findViewById(R.id.tv_take_photo);
        text3 = contentView.findViewById(R.id.tv_cancel);

        /*text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1f);
            }
        });
        setAnimationStyle(R.style.PopupBottomAnim);
    }

    public void popupDismiss(){
        setWindowAlpha(1f);
    }

    public void setWindowAlpha(float alpha){
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = alpha;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    public void show(){
        setWindowAlpha(0.7f);
        showAtLocation(mView, Gravity.BOTTOM, 0, 0);
    }

    public void setText1(String str){
        text1.setText(str);
    }

    public void setText1(String str, int color){
        setText1(str);
        text1.setTextColor(ContextCompat.getColor(mActivity, color));
    }

    public void setText1(String str, int color, int tes){
        setText1(str, color);
        text1.setTextSize(tes);
    }

    public void setOnText1ClickListener(View.OnClickListener onClickListener){
        text1.setOnClickListener(onClickListener);
    }

    public void setOnText2ClickListener(View.OnClickListener onClickListener){
        text2.setOnClickListener(onClickListener);
    }

    public void setOnText3ClickListener(View.OnClickListener onClickListener){
        text3.setOnClickListener(onClickListener);
    }

    public void setText2(String str){
        text2.setText(str);
    }

    public void setText2(String str, int color){
        setText2(str);
        text2.setTextColor(ContextCompat.getColor(mActivity, color));
    }

    public void setText3(String str){
        text3.setText(str);
    }

    public void setText3(String str, int color){
        setText3(str);
        text3.setTextColor(ContextCompat.getColor(mActivity, color));
    }
}
