package com.zhexinit.yixiaotong.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhexinit.yixiaotong.R;


/**
 * Created by zhousx on 2017/10/18.
 * Function: 给recyclerView设置分隔线
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    private Context mContext;
    private int lineHeight = 2;
    private int mColor;
    private int mOrientation = VERTICAL_LIST;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public void setLineHeight(int height){
        lineHeight = height;
    }

    public void setLineColor(int color){
        mColor = ContextCompat.getColor(mContext,color);
    }

    public void setOrientation(int orientation){
        mOrientation = orientation;
    }

    public RecyclerViewDivider(Context context) {
        this(context,VERTICAL_LIST);
    }

    public RecyclerViewDivider(Context context, int orientation) {
        mColor =  R.color.gray_line;
        mContext = context;
        mOrientation = orientation;
    }

    public RecyclerViewDivider(Context context, int orientation, int color) {
        mContext = context;
        mColor = ContextCompat.getColor(context,color);
        mOrientation = orientation;
    }

    public RecyclerViewDivider(Context context, int orientation, int color, int height) {
        mContext = context;
        mColor = ContextCompat.getColor(context,color);
        mOrientation = orientation;
        lineHeight = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            drawVerticalLine(c, parent, state);
        } else {
            drawHorizontalLine(c, parent, state);
        }
    }

    //画竖线
    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop();
            final int bottom = child.getBottom();
            final int left = child.getLeft()-(params.leftMargin+params.rightMargin)/2-lineHeight;
            final int right = left + lineHeight;
            Paint paint = new Paint();
            paint.setColor(mColor);
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft();
            final int right = child.getRight();
            final int top = child.getTop()-(params.leftMargin+params.rightMargin)/2-lineHeight;
            final int bottom = top + lineHeight;
            Paint paint = new Paint();
            paint.setColor(mColor);
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            //画竖线，就是往右偏移一个分割线的高度
            outRect.set(0, 0, lineHeight, 0);
        } else {
            //画横线，就是往下偏移一个分割线的宽度
            outRect.set(0, 0, 0, lineHeight);
        }
    }
}
