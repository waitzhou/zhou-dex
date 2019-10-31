package com.example.gradletest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.gradletest.R;

public class CircleView extends View {

    private int mOuterRadius,mMediumRadius,mInnerRadius;
    private int mOuterColor,mMediumColor,mInnerColor;
    private Paint mPaint;
    private int xPosition,yPosition;
    public CircleView(Context context) {
        //super(context);
        this(context,null,0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.CircleView);
        mOuterRadius = (int)typedArray.getDimension(R.styleable.CircleView_outerRadius,30);
        mMediumRadius = (int)typedArray.getDimension(R.styleable.CircleView_mediumRadius,20);
        mInnerRadius = (int)typedArray.getDimension(R.styleable.CircleView_innerRadius,10);
        mOuterColor = typedArray.getResourceId(R.styleable.CircleView_outerColor,R.color.color1);
        mMediumColor = typedArray.getResourceId(R.styleable.CircleView_mediumColor,R.color.color_green);
        mInnerColor = typedArray.getResourceId(R.styleable.CircleView_innerColor,R.color.color3);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(mOuterRadius > widthSize || mOuterRadius > heightSize){
            widthSize = mOuterRadius;
            heightSize = mOuterRadius;
        }
        int width = resolveSize(widthSize,widthMeasureSpec);
        int height = resolveSize(heightSize,heightSize);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(mOuterColor));
        canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,mOuterRadius,mPaint);
        mPaint.setColor(getResources().getColor(mMediumColor));
        canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,mMediumRadius,mPaint);
        mPaint.setColor(getResources().getColor(mInnerColor));
        canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,mInnerRadius,mPaint);
    }
}
