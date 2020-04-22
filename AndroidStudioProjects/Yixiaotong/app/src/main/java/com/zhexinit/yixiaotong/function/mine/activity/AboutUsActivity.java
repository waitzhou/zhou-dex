package com.zhexinit.yixiaotong.function.mine.activity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.utils.Config;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.widget.ToolBar;

import butterknife.BindView;

/**
 * Created by:xukun
 * date:2018/11/12
 * description:关于我们页面
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.text_version)
    TextView text_version;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init() {
        toolBar.back(this);
        toolBar.setBackImage();
        toolBar.setTitle("关于壹校通");

        text_version.setText("当前版本：v"+ Config.getVersionName(this));
    }
}
