package com.zhexinit.yixiaotong.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.utils.DipUtils;

/**
 * Author:zhousx
 * date:2018/12/5
 * description:绘制三角形
 */
public class TriangleReverseView extends View {

    private int mFillColor;//填充色
    private int mWidth;//宽度
    private int mHeight;//高度
    private int mOrientation;

    /**
     * Orientation_vertical  倒三角
     * Orientation_horizontal 正三角
     * */
    private int Orientation_horizontal = 2,Orientation_vertical = 1;

    private Context mContext;
    private Paint mPaint;

    public TriangleReverseView(Context context) {
        this(context,null);
    }

    public TriangleReverseView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TriangleReverseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleReverseView,defStyleAttr,0);
        mFillColor = typedArray.getColor(R.styleable.TriangleReverseView_fillColor,ContextCompat.getColor(mContext ,R.color.white));
        mOrientation = typedArray.getInteger(R.styleable.TriangleReverseView_orientation,1);
        typedArray.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST){
        }
        mWidth = widthSize;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
        }
        mHeight = heightSize;
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mFillColor);
        //实例化路径
        Path path = new Path();
        if(mOrientation == Orientation_horizontal) {
            path.moveTo(0, 0);// 此点为多边形的起点
            path.lineTo(DipUtils.dip2px(mContext,mWidth), -DipUtils.dip2px(mContext,mHeight));
            path.lineTo(0, 0);
            path.close(); // 使这些点构成封闭的多边形
        }else {
            path.moveTo(0, 0);// 此点为多边形的起点
            path.lineTo(DipUtils.dip2px(mContext,mWidth), DipUtils.dip2px(mContext,mHeight));
            path.lineTo(0, 0);
            path.close(); // 使这些点构成封闭的多边形
        }
        canvas.drawPath(path, mPaint);
    }
}
