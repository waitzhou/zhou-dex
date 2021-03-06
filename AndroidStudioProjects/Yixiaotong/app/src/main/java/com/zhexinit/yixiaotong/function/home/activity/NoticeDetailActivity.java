package com.zhexinit.yixiaotong.function.home.activity;

import android.graphics.Typeface;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.function.home.entity.resp.NoticeResp;
import com.zhexinit.yixiaotong.utils.GsonUtil;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.widget.ToolBar;

import butterknife.BindView;

/**
 * Author:zhousx
 * date:2018/11/10
 * description:通知详情
 */
public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void init() {

        initToolbar();
        initContent();
    }

    private void initContent() {
        NoticeResp noticeResp = GsonUtil.getInstance().getGson().fromJson(getIntent().getStringExtra("item"), NoticeResp.class);
        tvTitle.setText(noticeResp.noticeTitle);
        tvDesc.setText(noticeResp.noticeContent);
//        if (noticeResp.messageType == 1) {
//            tvTitle.setText(noticeResp.noticeTitle);
//            tvDesc.setText(noticeResp.noticeContent);
//        } else {
//            tvTitle.setText(noticeResp.attendanceTitle);
//            tvDesc.setText(noticeResp.attendanceContent);
//        }
        tvTime.setText(StringUtils.longToDate(noticeResp.messageDate,"yyyy年MM月dd日"));
        tvClass.setText(noticeResp.schoolName.concat(noticeResp.classOtherName).concat("  ").concat(noticeResp.childName));
        tvPublisher.setText(noticeResp.schoolName.concat(noticeResp.classOtherName).concat("  ").concat("发"));
    }

    private void initToolbar() {
        toolBar.setTitle("通知详情");
        toolBar.setBackImage();
        toolBar.back(this);
    }

}
