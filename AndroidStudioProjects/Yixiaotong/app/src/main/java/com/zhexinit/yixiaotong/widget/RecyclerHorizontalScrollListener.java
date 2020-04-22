package com.zhexinit.yixiaotong.widget;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Function:
 * created by xwy at 2017/10/27
 */
public abstract class RecyclerHorizontalScrollListener extends RecyclerView.OnScrollListener {

    // 用来标记是否正在向上滑动
    private boolean isSlidingLeft = false;
    // 用来标记是否正在向下滑动
    private boolean isSlidingRight = false;

    private boolean isRefreshing = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.e("testtetstaestsat", "onScrollStateChanged: "+newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 获取最后一个完全显示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int firstItemPosition = manager.findFirstCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个item，并且是向左滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingLeft) {
                // 加载更多
                onLoadMore();
            } else if (firstItemPosition == 0 && isSlidingRight) {
                refreshOnce();
            }
        }
    }

    private void refreshOnce() {
        if (!isRefreshing) {
            isRefreshing = true;
            onRefresh();
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isRefreshing = false;
        }
    };

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingLeft = dx > 0;
        isSlidingRight = dx <= 0;
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

    /**
     * 刷新
     */
    public abstract void onRefresh();
}
