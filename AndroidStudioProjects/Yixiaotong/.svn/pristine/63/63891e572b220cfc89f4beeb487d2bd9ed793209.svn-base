package com.zhexinit.yixiaotong.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhexinit.yixiaotong.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Author:zhousx
 * date:2018/11/29
 * description:弹出可放大图片的弹框
 */
public class ShowScaleImagePopupWindow extends PopupWindow {

    private Activity mActivity;
    private View mView;
    private String mUrl;

    public ShowScaleImagePopupWindow(Activity activity, View view, String url) {
        mActivity = activity;
        mView = view;
        this.mUrl = url;
        if (mUrl != null && !mUrl.equals(""))
            findView();
    }

    private void findView() {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.popup_window_scale_image, null);
        PhotoView imageView = contentView.findViewById(R.id.photo_imageView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        Glide.with(mActivity)
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                dismiss();
            }
        });
        setAnimationStyle(R.style.PopupCenterAnim);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupDismiss();
            }
        });
    }

    private void popupDismiss() {
        setWindowAlpha(1f);
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = alpha;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    public void show() {
        if(mUrl != null && !mUrl.equals("")) {
            //setWindowAlpha(0f);
            showAtLocation(mView, Gravity.CENTER,0,0);
        }
    }

}
