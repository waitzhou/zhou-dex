package com.example.componenthotfixdemo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author : ZSX
 * Date : 2019-11-20
 * Description : 支持懒加载的fragment
 */
public abstract class LazyLoadFragment extends Fragment {

    private boolean isInit,isVisiable;

    //这个方法会被调用多次
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisiable = isVisibleToUser;
        //是否对用户可见
        onVisiableToUser();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //是否已经完成初始化
        isInit = true;
        onVisiableToUser();
    }

    /**
     * 已经初始化并且对用户可见
     * */
    private void onVisiableToUser(){
        if(isInit && isVisiable){
            lazyLoad();
        }
    }

    protected abstract void lazyLoad();
}
