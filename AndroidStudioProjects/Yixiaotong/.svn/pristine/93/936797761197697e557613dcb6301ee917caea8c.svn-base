package com.zhexinit.yixiaotong.function.home.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerAdapter;
import com.zhexinit.yixiaotong.utils.commonAdapter.CommonRecyclerHolder;
import com.zhexinit.yixiaotong.widget.SharePopupWindow;
import com.zhexinit.yixiaotong.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author:zhousx
 * date:2018/11/6
 * description:成绩首页界面
 */
public class ResultsActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    ToolBar mToolBar;
    private List<String> mStrings = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_results;
    }

    @Override
    protected void init() {

        initToolbar();
        //initContent();
    }

    /**
     * toolbar设置
     * */
    private void initToolbar() {
        mToolBar.setTitle("");
        mToolBar.setBackImage();
        mToolBar.back(this);
        mToolBar.setRightTypeface(getResources().getString(R.string.tv_share));
        mToolBar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSharePopupwindow();
            }
        });
    }

    private void showSharePopupwindow() {
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this,mToolBar);
        sharePopupWindow.show();
    }
}
