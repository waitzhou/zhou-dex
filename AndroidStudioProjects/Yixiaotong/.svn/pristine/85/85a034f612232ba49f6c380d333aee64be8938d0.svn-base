package com.zhexinit.yixiaotong.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Author:xk
 * description: 设置RecyclerView是否可以滑动
 */
public class CanScrollLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;
    public CanScrollLayoutManager(Context context) {
        super(context);
    }

    public CanScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CanScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
