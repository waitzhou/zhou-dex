package com.zhexinit.yixiaotong.utils.commonAdapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Function:
 * created by xwy at 2017/10/26
 */
public class CommonRecyclerHolder extends RecyclerView.ViewHolder{

    //用来存放子View减少fbi次数
    private SparseArray<View> mViewSparseArray;
    public View mItemView;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViewSparseArray = new SparseArray<>();
    }

    public View getRootView(){
        return mItemView;
    }

    public <T extends View> T getView(int id){
        //先从缓存中找
        View view = mViewSparseArray.get(id);
        if (view == null){
            view = mItemView.findViewById(id);
            mViewSparseArray.put(id,view);
        }
        return (T) view;
    }

    public CommonRecyclerHolder setText(int id,String s){
        TextView tv = getView(id);
        tv.setText(s);
        return this;
    }

    public CommonRecyclerHolder setText(int id,SpannableString s){
        TextView tv = getView(id);
        tv.setText(s);
        return this;
    }

    public CommonRecyclerHolder setGravity(int id,int gravity){
        TextView tv = getView(id);
        tv.setGravity(gravity);
        return this;
    }

    public CommonRecyclerHolder setVisibility(int id,int visible){
        View view = getView(id);
        view.setVisibility(visible);
        return this;
    }

    public CommonRecyclerHolder setImageResource(int id,int resId){
        ImageView iv = getView(id);
        iv.setImageResource(resId);
        return this;
    }

    public CommonRecyclerHolder setTextSize(int id, int size) {
        TextView tv = getView(id);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        return this;
    }
    public CommonRecyclerHolder setTextColor(int id, int color) {
        TextView tv = getView(id);
        tv.setTextColor(tv.getContext().getResources().getColor(color));
        return this;
    }
    public CommonRecyclerHolder setBackgroundColor(int id, int color) {
        TextView tv = getView(id);
        tv.setBackgroundColor(tv.getContext().getResources().getColor(color));
        return this;
    }
}
