package com.zhexinit.yixiaotong.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;


/**
 * Author:@zhousx
 * date: 2017/7/26/13:46.
 * function :获取屏幕宽高
 */

public class DipUtils {

    public static int getScreenWidth(Context context) {
        /*WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);*/

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        return dm.widthPixels; // 屏幕宽（像素，如：3200px）

        //return point.x;
    }

    public static int getScreenHeight(Context context) {
        /*WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);*/

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）

        return dm.heightPixels; // 屏幕高（像素，如：1280px）

        //return point.y;
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     * 3      *
     * 4      * @param context
     * 5      * @return
     * 6
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;

    }

    /**
     * 将px值转换为 dip 或者dp 值,保证尺寸大小不变.   *   * @param px Value   * @param scale   *       (DisplayMetrics类中属性 density)   *       density 就是px 向 dp或sp的换算比例   *   * @return   *
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将 px 值转换为dp 或者 dip 值,保证尺寸大小不变   * @param dipValue   * @param scale   *        (DisplayMetrics类中属性 density)   * @return   *
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px 值转换成 sp值,保证文字大小不变   *   * @param pxValue   * @param fontScale   *       (DisplayMetrics类中属性 density)   * @return   *
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / fontScale) + 0.5f);
    }

    /**
     * 将 px 值转换为dp 或者 dip 值,保证尺寸大小不变   * @param dipValue   * @param scale   *        (DisplayMetrics类中属性 density)   * @return   *
     */
    public static int sp2dip(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * density 是dp 和sp 相对于px 的换算比例   * 而系统也提供了TypedValue 类帮助转换   *
     */
    public static int dp2px(Context context, int dp) {    //通过TypedValue工具类来进行转换
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
