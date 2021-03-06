package com.example.jingweiclassicdemo.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class MyButton1 extends AppCompatButton {

    private String TAG = this.getClass().getSimpleName();

    public MyButton1(Context context) {
        super(context);
    }

    public MyButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()){
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
        return super.dispatchTouchEvent(event);
    }
}
