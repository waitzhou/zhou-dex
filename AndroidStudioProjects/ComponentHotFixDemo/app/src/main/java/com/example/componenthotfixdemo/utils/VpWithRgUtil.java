package com.example.componenthotfixdemo.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-11-20
 * Description : ViewPager + RadioGroup + fragment 工具类
 * ps : 适用于数量较少的fragment，fragment数量较多时请使用FragmentStatePagerAdapter
 */
public class VpWithRgUtil implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private String TAG = this.getClass().getSimpleName();

    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> mFragmentList;

    public VpWithRgUtil(FragmentManager pManager, ViewPager pViewPager, RadioGroup pRadioGroup, List<Fragment> pFragmentList) {
        this.mFragmentManager = pManager;
        this.mViewPager = pViewPager;
        this.mRadioGroup = pRadioGroup;
        this.mFragmentList = pFragmentList;
        if (pManager != null && pViewPager != null && pRadioGroup != null && pFragmentList != null) {
            if (pRadioGroup.getChildCount() == pFragmentList.size()) {
                initViewPager();
                initRadioGroup();
                mRadioGroup.getChildAt(0).performClick();
            } else {
                throw new IndexOutOfBoundsException("radioGroup的子控件数目和fragment数目不对等，请检查");
            }
        } else {
            throw new NullPointerException("参数为空，请检查参数");
        }

    }

    private void initRadioGroup() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(mFragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //super.destroyItem(container, position, object);
            }
        });
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup pRadioGroup, int checkId) {
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton child = (RadioButton) mRadioGroup.getChildAt(i);
            if(child.getId() == checkId && child.isChecked()){
                if(mViewPager.getCurrentItem() != i) {
                    Log.d(TAG, "onCheckedChanged: " + checkId);
                    mViewPager.setCurrentItem(i);
                }
                return;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton childAt = (RadioButton) mRadioGroup.getChildAt(position);
        if(!childAt.isChecked()) {
            Log.d(TAG, "onPageSelected:  =" + position);
            mRadioGroup.check(childAt.getId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
