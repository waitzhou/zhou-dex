package com.zhexinit.yixiaotong.utils.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Function:
 * created by xwy at 2017/10/26
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerHolder> {

    private static final String TAG = CommonRecyclerAdapter.class.getSimpleName();
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutId;
    protected List<T> mList;

    public CommonRecyclerAdapter(Context context, List<T> list, int layoutId) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }


    @Override
    public CommonRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        return new CommonRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommonRecyclerHolder holder, final int position) {
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewItemClickListener != null) {
                    mRecyclerViewItemClickListener.recyclerViewItemClick(v, position, mList.get(position));
                }
            }
        });
        holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mLongClickListener != null){
                    mLongClickListener.recyclerViewLongItem(v,position,
                            mList.get(position));
                }
                return false;
            }
        });
        convert(holder, mList.get(position),position);
    }

    public void setData(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public interface RecyclerViewItemClickListener<T> {
        void recyclerViewItemClick(View v, int pos, T bean);
    }

    private RecyclerViewItemClickListener mRecyclerViewItemClickListener;

    public void setItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        mRecyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public interface RecyclerViewItemLongClickListener<T>{
        void recyclerViewLongItem(View v, int pos, T bean);
    }

    private RecyclerViewItemLongClickListener mLongClickListener;
    public void setItemLongClick(RecyclerViewItemLongClickListener longClick){
        mLongClickListener = longClick;
    }

    public abstract void convert(CommonRecyclerHolder holder, T item,int position);

    @Override
    public int getItemCount() {
            return mList == null ?0:mList.size();

    }
}
