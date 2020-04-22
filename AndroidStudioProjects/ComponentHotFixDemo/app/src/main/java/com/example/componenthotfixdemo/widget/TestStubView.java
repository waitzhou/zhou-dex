package com.example.componenthotfixdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author : ZSX
 * Date : 2019-11-20
 * Description :
 */
public class TestStubView extends View implements View.OnTouchListener {

    private String TAG = this.getClass().getSimpleName();

    public TestStubView(Context context) {
        this(context,null);
    }

    public TestStubView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestStubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }*/
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View pView, MotionEvent pMotionEvent) {
        Log.d(TAG, "onTouch: ");
        return false;
    }
}
