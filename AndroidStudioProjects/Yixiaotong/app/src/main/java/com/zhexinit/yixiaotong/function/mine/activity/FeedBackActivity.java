package com.zhexinit.yixiaotong.function.mine.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.zhexinit.yixiaotong.R;
import com.zhexinit.yixiaotong.base.BaseActivity;
import com.zhexinit.yixiaotong.utils.StringUtils;
import com.zhexinit.yixiaotong.widget.ToolBar;


import butterknife.BindView;

/**
 * Created by:xukun
 * date:2018/11/12
 * description: 意见反馈页面
 */
public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    ToolBar toolBar;
    @BindView(R.id.edit_feedback)
    EditText edit_feedback;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        toolBar.setBackImage();
        toolBar.back(this);
        toolBar.setTitle("意见反馈");
        toolBar.setRightTv("发送");
        toolBar.setRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(edit_feedback.getText().toString())) {
                    showToast("请输入文字");
                } else {
                    showProgressDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideProgressDialog();
                            showToast("提交成功");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 800);
                        }
                    }, 2000);
                }

            }
        });

        edit_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 140) {
                    showToast("最多只能输入140个字");
                }

                //如果 输入的类容包含有Emoji
                CharSequence input = s.subSequence(start, start + count);
                if (isEmojiCharacter(input)) {
                    showToast("不支持emoji表情");
                    //那么就去掉
                    edit_feedback.setText(removeEmoji(s));
                }
                //最后光标移动到最后 TODO 这里可能会有更好的解决方案
                edit_feedback.setSelection(edit_feedback.getText().toString().length());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 去除字符串中的Emoji表情
     * @param source
     * @return
     */
    private String removeEmoji(CharSequence source)
    {
        String result = "";
        for (int i = 0; i < source.length(); i++)
        {
            char c = source.charAt(i);
            if (isEmojiCharacter(c))
            {
                continue;
            }
            result += c;
        }
        return result;
    }

    /**
     * 判断一个字符串中是否包含有Emoji表情
     * @param input
     * @return true 有Emoji
     */
    private boolean isEmojiCharacter(CharSequence input)
    {
        for (int i = 0; i < input.length(); i++)
        {
            if (isEmojiCharacter(input.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是Emoji 表情,抄的那哥们的代码
     *
     * @param codePoint
     * @return true 是Emoji表情
     */
    public static boolean isEmojiCharacter(char codePoint)
    {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

        return !isScopeOf;
    }
}
