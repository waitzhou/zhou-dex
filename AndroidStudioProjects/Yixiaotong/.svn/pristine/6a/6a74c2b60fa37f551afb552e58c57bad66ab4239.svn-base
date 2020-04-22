package com.zhexinit.yixiaotong.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.utils.DipUtils;
import com.zhexinit.yixiaotong.utils.StatusBarCompat;


/**
 * Author:@zhousx
 * date: 2017/8/8/11:02.
 * function :toolbar常用类
 */

public class ToolBar extends RelativeLayout {

    public TextView title, rightTv,leftImg,titleRightDrawable;
    public ImageView rightImg;
    public RelativeLayout mLayout;
    public LinearLayout mLayoutTitle;
    private TextView tool_bar_left_tv;
    private Context mContext;

    public ToolBar(Context context) {
        this(context, null);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        if(Build.VERSION.SDK_INT > 19){
            setMinimumHeight(DipUtils.dp2px(mContext,45)+StatusBarCompat.getStatusBarHeight(mContext));
            setPadding(0, StatusBarCompat.getStatusBarHeight(mContext),0,0);
        }
        setBackground(getResources().getDrawable(R.drawable.gradient_theme));
    }

    public void ShowLeftContent(String Content) {
        tool_bar_left_tv= (TextView) findViewById(R.id.tool_bar_left_tv);
        tool_bar_left_tv.setText(Content);
        tool_bar_left_tv.setVisibility(View.VISIBLE);
    }

    public void setLeftTvClick(OnClickListener listener){
        tool_bar_left_tv= (TextView) findViewById(R.id.tool_bar_left_tv);
        tool_bar_left_tv.setOnClickListener(listener);
    }

    public void setBackgroudColor(int color) {
        mLayout = (RelativeLayout) findViewById(R.id.rl);
        mLayout.setBackgroundResource(color);
    }

    public void setTitleColor(int color) {
        title = (TextView) findViewById(R.id.tool_bar_title);
        title.setTextColor(color);
    }

    public void setTitle(String s) {
        title = (TextView) findViewById(R.id.tool_bar_title);
        title.setText(s);
    }

    public void setTitleRightDrawable(int res){
        Typeface typeface =  Typeface.createFromAsset(mContext.getAssets(),mContext.getString(R.string.tv_path));
        titleRightDrawable = findViewById(R.id.tool_bar_title_right_drawable);
        titleRightDrawable.setVisibility(VISIBLE);
        titleRightDrawable.setText(mContext.getString(res));
        titleRightDrawable.setTypeface(typeface);
    }

    public void setTitleClickListener(OnClickListener listener){
        LinearLayout layout = findViewById(R.id.ll_title);
        layout.setOnClickListener(listener);
    }

    public void setBackImage() {
        setBackImage(0);
    }

    public void setBackImage(int res) {
        leftImg = findViewById(R.id.tool_bar_left_img);
        leftImg.setVisibility(VISIBLE);
        if (res != 0) {
            try {
                leftImg.setText(mContext.getString(res));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),mContext.getResources().getString(R.string.tv_path));
            leftImg.setTypeface(typeface);
        }
    }

    public void setRightImg(int res) {
        rightImg = (ImageView) findViewById(R.id.tool_bar_right_image);
        rightImg.setVisibility(VISIBLE);
        rightImg.setImageResource(res);
    }

    public void setRightTv(String s) {
        rightTv = (TextView) findViewById(R.id.tool_bar_right_tv);
        rightTv.setVisibility(VISIBLE);
        rightTv.setText(s);
    }

    public void setRightTv(String s, int color, Context context) {
        setRightTv(s);
        rightTv.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setRightTypeface(String s){
        rightTv =  findViewById(R.id.tool_bar_right_tv);
        rightTv.setVisibility(VISIBLE);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),mContext.getResources().getString(R.string.tv_path));
        rightTv.setText(s);
        rightTv.setTypeface(typeface);
    }

    public void setRightTvClickListener(OnClickListener listener) {
        rightTv = (TextView) findViewById(R.id.tool_bar_right_tv);
        rightTv.setOnClickListener(listener);
    }

    public void setRightIvClick(OnClickListener listener) {
        rightImg = (ImageView) findViewById(R.id.tool_bar_right_image);
        rightImg.setOnClickListener(listener);
    }

    public void setLeftImageClickListener(OnClickListener listener) {
        leftImg = findViewById(R.id.tool_bar_left_img);
        leftImg.setOnClickListener(listener);
    }

    public void setLinesGone() {
        View lines = findViewById(R.id.lines);
        lines.setVisibility(GONE);
    }

    public void setRightTvGone() {
        rightTv.setVisibility(GONE);
    }

    public void setRightIvGone() {
        rightImg = (ImageView) findViewById(R.id.tool_bar_right_image);
        rightImg.setVisibility(GONE);
    }

    public void back(final Activity activity) {
        leftImg = findViewById(R.id.tool_bar_left_img);
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
