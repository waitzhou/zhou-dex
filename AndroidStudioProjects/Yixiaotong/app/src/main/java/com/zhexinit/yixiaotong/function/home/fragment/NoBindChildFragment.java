package com.zhexinit.yixiaotong.function.home.fragment;

import android.os.Bundle;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.base.BaseFragment;
import com.zhexinit.yixiaotong.widget.ToolBar;

import butterknife.BindView;

/**
 * Author:zhousx
 * date:2018/11/22
 * description:没有绑定孩子界面
 */
public class NoBindChildFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    ToolBar mToolBar;

    public static NoBindChildFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NoBindChildFragment fragment = new NoBindChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_no_bind_child;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mToolBar.setTitle("壹校通");
    }
}
