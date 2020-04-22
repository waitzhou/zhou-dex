package com.zhexinit.yixiaotong.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;

/**
 * Author:zhousx
 * date:2018/8/7
 * description:滑动到顶部刷新
 */
public class RefreshWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_HEADER = 2000;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public static final int LOADING = 1;
    // 加载完成
    public static final int LOADING_COMPLETE = 2;
    // 加载到底
    public static final int LOADING_END = 3;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private int mPageCount;

    public RefreshWrapper(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void setRecyclerView(RecyclerView recyclerView, int pageCount) {
        this.mRecyclerView = recyclerView;
        this.mPageCount = pageCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return mAdapter.getItemViewType(position - 1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderHolder(headerView);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.mLine.setVisibility(View.VISIBLE);

            switch (loadState) {
                case LOADING:
                    headerHolder.mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    headerHolder.mProgressBar.setVisibility(View.GONE);
                    headerHolder.mTvLoading.setVisibility(View.GONE);
                    headerHolder.mLine.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    headerHolder.mProgressBar.setVisibility(View.GONE);
                    headerHolder.mTvLoading.setVisibility(View.VISIBLE);
                    headerHolder.mTvLoading.setText("没\n有\n更\n多\n了");
                    break;
                default:
                    break;
            }
        } else {
            mAdapter.onBindViewHolder(holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter.getItemCount() == 0) {
            return 0;
        } else
            return mAdapter.getItemCount() + 1;
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgressBar;
        TextView mTvLoading;
        View mLine;

        public HeaderHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.item_progress_bar);
            mTvLoading = itemView.findViewById(R.id.item_tv_loading);
            mLine = itemView.findViewById(R.id.item_line);
        }
    }

    public void setLoadingState(int state) {
        this.loadState = state;
        notifyDataSetChanged();
    }
}
