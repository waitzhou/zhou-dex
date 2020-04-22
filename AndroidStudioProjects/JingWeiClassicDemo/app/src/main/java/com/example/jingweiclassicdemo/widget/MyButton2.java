package com.example.jingweiclassicdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class MyButton2 extends AppCompatButton {

    private String TAG = this.getClass().getSimpleName();

    public MyButton2(Context context) {
        super(context);
    }

    public MyButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
