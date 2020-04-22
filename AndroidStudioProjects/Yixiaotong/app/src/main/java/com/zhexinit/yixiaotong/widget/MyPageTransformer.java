package com.zhexinit.yixiaotong.widget;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author:zhousx
 * date:2018/8/14
 * description:
 */
public class MyPageTransformer implements ViewPager.PageTransformer {

    final float SCALE_MAX = 0.8F;
    final float ALPHA_MAX = 0.5F;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float scale = (position < 0) ? ((1-SCALE_MAX)* position + 1) : ((SCALE_MAX - 1)*position + 1);
        float alpha = (position < 0) ? ((1-ALPHA_MAX)* position + 1) : ((ALPHA_MAX - 1)*position + 1);

        if(position < 0) {
            ViewCompat.setPivotX(page, page.getWidth());
            ViewCompat.setPivotY(page, page.getHeight() / 2);
        } else {
            ViewCompat.setPivotX(page, 0);
            ViewCompat.setPivotY(page, page.getHeight() / 2);
        }
        ViewCompat.setScaleX(page, scale);
        ViewCompat.setScaleY(page, scale);
        //ViewCompat.setAlpha(page, Math.abs(alpha));

    }
}
