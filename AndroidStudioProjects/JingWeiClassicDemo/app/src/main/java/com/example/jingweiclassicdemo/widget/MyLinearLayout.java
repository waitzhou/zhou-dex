package com.example.jingweiclassicdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class MyLinearLayout extends LinearLayout {

    private String TAG = this.getClass().getSimpleName();

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent:   ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent:   ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "dispatchTouchEvent:   ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "dispatchTouchEvent:   ACTION_POINTER_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
