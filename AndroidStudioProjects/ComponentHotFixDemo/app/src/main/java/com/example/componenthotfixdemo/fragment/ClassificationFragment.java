package com.example.componenthotfixdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.componenthotfixdemo.R;
import com.example.componenthotfixdemo.base.LazyLoadFragment;


/**
 * Author : ZSX
 * Date : 2019-11-19
 * Description :
 */
public class ClassificationFragment extends LazyLoadFragment {

    private String TAG = this.getClass().getSimpleName();
    private TextView mText;

    public static ClassificationFragment newInstance() {

        Bundle args = new Bundle();

        ClassificationFragment fragment = new ClassificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.text);
    }

    protected void lazyLoad() {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mText.setText("分类");
            }
        }, 1000);
    }

    private static Handler sHandler = new Handler(Looper.getMainLooper());
}
