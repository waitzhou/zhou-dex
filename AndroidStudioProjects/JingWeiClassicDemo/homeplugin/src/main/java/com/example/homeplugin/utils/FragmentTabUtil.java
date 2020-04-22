package com.example.homeplugin.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-12-30
 * Description : fragment + radio group 切换工具类
 */
public class FragmentTabUtil implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager mFragmentManager;
    private int mContentId, mShowPage = -1;
    private List<Fragment> mFragmentList;

    public FragmentTabUtil(FragmentManager fm, int contentId, RadioGroup radioGroup, List<Fragment> fragmentList, int defaultDisplayFragmentPos) {
        mFragmentManager = fm;
        mContentId = contentId;
        mFragmentList = fragmentList;
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(defaultDisplayFragmentPos).performClick();
    }

    public FragmentTabUtil(FragmentManager fm, int contentId, RadioGroup radioGroup, List<Fragment> fragmentList) {
        this(fm, contentId, radioGroup, fragmentList, 0);
    }

    private void showPage(int position) {
        if (position == mShowPage) {
            return;
        }
        Fragment fragment = mFragmentList.get(position);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            fragmentTransaction.add(mContentId, fragment);
        }
        if (mShowPage != -1) {
            fragmentTransaction.hide(mFragmentList.get(mShowPage)).show(fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        mShowPage = position;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View childAt = group.getChildAt(i);
            if (childAt.getId() == checkedId) {
                showPage(i);
            }
        }
    }
}
