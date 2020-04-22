package com.example.homeplugin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homeplugin.R;

/**
 * Author : ZSX
 * Date : 2019-12-30
 * Description :
 */
public class ClassificationFragment extends Fragment {

    public static ClassificationFragment newInstance() {

        Bundle args = new Bundle();

        ClassificationFragment fragment = new ClassificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_classification,container,false);
        return view;
    }
}