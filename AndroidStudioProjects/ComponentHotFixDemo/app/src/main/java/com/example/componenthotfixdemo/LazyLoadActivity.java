package com.example.componenthotfixdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.componenthotfixdemo.fragment.ClassificationFragment;
import com.example.componenthotfixdemo.fragment.HomeFragment;
import com.example.componenthotfixdemo.fragment.SearchFragment;
import com.example.componenthotfixdemo.fragment.UserFragment;
import com.example.componenthotfixdemo.utils.FragmentUtil;
import com.example.componenthotfixdemo.utils.VpWithRgUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-11-19
 * Description :
 */
public class LazyLoadActivity extends AppCompatActivity {

    ViewPager mViewPager;
    RadioGroup mRadioGroup;
    FrameLayout mFrameLayout;

    private List<Fragment> mFragmentList;

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load);
        Log.d(TAG, "onCreate:");

        mViewPager = findViewById(R.id.viewPager);
        mRadioGroup = findViewById(R.id.radioGroup);
        mFrameLayout = findViewById(R.id.container);

        initFragmentList();
        //new FragmentUtil(getSupportFragmentManager(),mFragmentList,mRadioGroup,R.id.container);
       new VpWithRgUtil(getSupportFragmentManager(),mViewPager,mRadioGroup,mFragmentList);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(ClassificationFragment.newInstance());
        mFragmentList.add(SearchFragment.newInstance());
        mFragmentList.add(UserFragment.newInstance());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
