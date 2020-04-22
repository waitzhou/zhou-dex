package com.example.homeplugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.homeplugin.fragment.ClassificationFragment;
import com.example.homeplugin.fragment.HomeFragment;
import com.example.homeplugin.fragment.SearchFragment;
import com.example.homeplugin.fragment.UserFragment;
import com.example.homeplugin.utils.FragmentTabUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Author : ZSX
 * Date : 2019-12-27
 * Description :
 */
public class HomeMainActivity  extends AppCompatActivity {

    List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);

        init();
    }

    private void init() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.home_radio_group);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(ClassificationFragment.newInstance());
        mFragmentList.add(SearchFragment.newInstance());
        mFragmentList.add(UserFragment.newInstance());
        new FragmentTabUtil(getSupportFragmentManager(),R.id.home_content,radioGroup,mFragmentList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentList = null;
    }
}
