package com.example.gradletest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.gradletest.R;

import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-09-20
 * Description :
 */
public class FlowLayout extends ViewGroup {

    private int mCellSpec, mLineSpec;
    private String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        //super(context);
        this(context, null, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context pContext, AttributeSet pAttributeSet) {
        TypedArray typedArray = pContext.obtainStyledAttributes(pAttributeSet, R.styleable.FlowLayout);
        mCellSpec = (int) typedArray.getDimension(R.styleable.FlowLayout_cellSpec, 0);
        mLineSpec = (int) typedArray.getDimension(R.styleable.FlowLayout_lineSpec, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int lineWidth = 0, lineHeight = 0, width = 0, height = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                if (i == childCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //如果是最后一行，则加上最后一行的高度  并取宽度的最大值
            if (childCount - 1 == i) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean pB, int pI, int pI1, int pI2, int pI3) {
        int widthUsed = 0, heightUsed = 0, maxHeight = 0;
        int width = getWidth();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (child.getVisibility() == GONE) {
                continue;
            }
            if (childWidth + widthUsed > width - getPaddingLeft() - getPaddingRight()) {
                heightUsed += maxHeight;
                maxHeight = childHeight;
                widthUsed = 0;
            }
            child.layout(widthUsed + lp.leftMargin,
                    heightUsed + lp.topMargin,
                    widthUsed + childWidth - lp.rightMargin,
                    heightUsed + childHeight - lp.bottomMargin);
            widthUsed += childWidth;
            maxHeight = Math.max(childHeight, maxHeight);
        }
    }

    public void addTextList(List<String> texts, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (texts == null || texts.size() == 0) {
            throw new NullPointerException("传入的textList不能为空");
        }
        for (int i = 0; i < texts.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setHeight(60);
            textView.setPadding(80,0,80,0);
            textView.setText(texts.get(i));
            textView.setBackgroundResource(R.drawable.bg_flow);
            MarginLayoutParams lp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
            lp.leftMargin = leftMargin;
            lp.topMargin = topMargin;
            lp.rightMargin = rightMargin;
            lp.bottomMargin = bottomMargin;
            textView.setLayoutParams(lp);
            this.addView(textView);
        }
    }

    public void addTextList(List<String> texts, int margin) {
        this.addTextList(texts, margin, margin, margin, margin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

}
