package com.zhexinit.yixiaotong.function.mine.activity;

import android.webkit.WebView;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.widget.ToolBar;

import butterknife.BindView;

/**
 * Created by:xukun
 * date:2018/11/17
 * description:
 */
public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void init() {
        toolBar.back(this);
        toolBar.setBackImage();
        toolBar.setTitle("帮助中心");

        WebView webView=findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/help.html");
    }
}
