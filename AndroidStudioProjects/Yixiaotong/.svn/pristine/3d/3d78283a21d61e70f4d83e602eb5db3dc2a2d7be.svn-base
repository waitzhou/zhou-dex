package com.zhexinit.yixiaotong.widget;

/**
 *公用dialog
 */

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;


public class CommonDialog extends Dialog {
    /***
     * 标题名称控件, 确认按钮 取消
     */
    private TextView tv_title, tv_content, tv_cancel, tv_submit;
    private View lines;

    /**
     * dialog显示的内容
     */
    private String content;
    /**
     * dialog显示的标题
     */
    private String title;
    private Context mContext;

    public CommonDialog(Context context, String title, String content) {

        super(context, R.style.DialogTheme);
        // TODO Auto-generated constructor stub
        setContentView(R.layout.dialog_common);
        this.mContext = context;
        this.content = content;
        this.title = title;
        findView();
    }

    public CommonDialog(Context context, int theme, String title, String content) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        setContentView(R.layout.dialog_common);
        this.content = content;
        this.mContext = context;
        findView();
    }

    private void findView() {
        tv_title = (TextView) findViewById(R.id.tv_dialog_title);
        tv_content = (TextView) findViewById(R.id.tv_dialog_content);
        tv_submit = (TextView) findViewById(R.id.tv_dialog_submit);
        tv_cancel = (TextView) findViewById(R.id.tv_dialog_cancel);
        lines = findViewById(R.id.lines);
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        } else
            noTitle();
        tv_content.setText(content);
    }

    /***
     * 设置确认按钮点击事件
     *
     * @param onClickListener 点击事件监听
     */
    public void setSumbitClick(View.OnClickListener onClickListener) {
        tv_submit.setOnClickListener(onClickListener);
    }

    public void setNegativeClick(View.OnClickListener onClickListener){
        tv_cancel.setOnClickListener(onClickListener);
    }

    public void setNegativeColor(int color){
        tv_cancel.setTextColor(color);
    }

    public void setPositiveColor(int color){
        tv_submit.setTextColor(color);
    }

    public void setPositiveText(String str) {
        if (str != null)
            tv_submit.setText(str);
    }

    public void setNegativeText(String str){
        if(str != null){
            tv_cancel.setText(str);
        }
    }

    public void noTitle(){
        tv_title.setVisibility(View.GONE);
    }

    public void setNagetiveGone(){
        lines.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.GONE);
    }
}
