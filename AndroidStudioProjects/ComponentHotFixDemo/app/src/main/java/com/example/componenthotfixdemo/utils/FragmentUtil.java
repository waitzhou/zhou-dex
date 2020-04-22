package com.example.componenthotfixdemo.utils;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-11-19
 * Description : fragment + radioGroup
 */
public class FragmentUtil implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;
    private RadioGroup mRadioGroup;
    private int mCurrentTab, mLayoutId;
    private int mLastShowFragment = -1;

    private String TAG = this.getClass().getSimpleName();

    /**
     * @param fm  FragmentManager
     * @param pFragments  fragment集合
     * @param pRadioGroup RadioGroup
     * @param resId  占位布局ID
    * */
    public FragmentUtil(FragmentManager fm, List<Fragment> pFragments, RadioGroup pRadioGroup, int resId) {
        this(fm, pFragments, pRadioGroup, resId, 0);
    }

    /**
     * @param currentTab 默认选中的位置
     * */
    public FragmentUtil(FragmentManager fm, List<Fragment> pFragments, RadioGroup pRadioGroup, int layoutId, int currentTab) {

        this.mFragmentManager = fm;
        this.mFragmentList = pFragments;
        this.mRadioGroup = pRadioGroup;
        this.mLayoutId = layoutId;
        this.mCurrentTab = currentTab;

        init();
    }

    private void init() {
        Log.d(TAG, "init: mCurrentTab = " + mCurrentTab);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.getChildAt(mCurrentTab).performClick();
    }

    private void switchTab(int selectTab) {
        if (selectTab == mLastShowFragment) {
            Log.d(TAG, "switchTab: tab没有切换");
            return;
        }
        Fragment fragment = mFragmentList.get(selectTab);
        FragmentTransaction transaction = getTransaction();
        if (!fragment.isAdded()) {
            transaction.add(mLayoutId, fragment);
        }
        if (mLastShowFragment == -1) {
            transaction.show(fragment);
        } else {
            Log.d(TAG, "switchTab: mLastShowFragment = " + mLastShowFragment + " selectTab = " + selectTab);
            Fragment fragment1 = mFragmentList.get(mLastShowFragment);
            transaction.hide(fragment1);
            transaction.show(fragment);
        }
        transaction.commit();
        transaction.commitAllowingStateLoss();//允许丢失一些界面状态信息 避免activity出现异常情况，执行onSaveInstanceState之后再commit
        mLastShowFragment = selectTab;
    }

    private void resetRadioGroup(int selectTab) {
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton child = (RadioButton) mRadioGroup.getChildAt(i);
            child.setChecked(selectTab == i);
        }
    }

    private FragmentTransaction getTransaction() {
        if (mFragmentManager != null) {
            return mFragmentManager.beginTransaction();
        } else {
            throw new NullPointerException("FragmentManager is null !");
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup pRadioGroup, int checkId) {
        Log.d(TAG, "onCheckedChanged: ------> " + checkId);
        int position = 0;
        for (int i = 0; i < pRadioGroup.getChildCount(); i++) {
            if(pRadioGroup.getChildAt(i).getId() == checkId){
                position = i;
                break;
            }
        }
        switchTab(position);
        resetRadioGroup(position);
    }
}
