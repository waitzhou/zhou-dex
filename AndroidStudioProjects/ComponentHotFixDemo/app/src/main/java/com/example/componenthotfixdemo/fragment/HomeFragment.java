package com.example.componenthotfixdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.componenthotfixdemo.R;
import com.example.componenthotfixdemo.activity.EventTestActivity;
import com.example.componenthotfixdemo.base.LazyLoadFragment;

/**
 * Author : ZSX
 * Date : 2019-11-19
 * Description :
 */
public class HomeFragment extends LazyLoadFragment implements View.OnClickListener {

    private String TAG = this.getClass().getSimpleName();
    private TextView mText;
    private Button mButton;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.text);
        mButton = view.findViewById(R.id.btn_event);
        mButton.setOnClickListener(this);
    }

    private void eventInteruptClick(View pView){

    }

    protected void lazyLoad() {
        Log.d(TAG, "lazyLoad: ");
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mText.setText("主页");
            }
        },1000);
    }

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onClick(View pView) {
        switch (pView.getId()){
            case R.id.btn_event:
                startActivity(new Intent(getActivity(), EventTestActivity.class));
                break;
        }
    }
}
