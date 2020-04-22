package com.example.componenthotfixdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componenthotfixdemo.R;
import com.example.componenthotfixdemo.entity.People;
import com.example.componenthotfixdemo.widget.TestStubView;
import com.example.componenthotfixdemo.widget.TestViewGroup;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : ZSX
 * Date : 2019-11-20
 * Description :
 */
public class EventTestActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    TestViewGroup mTestViewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_test);
        mTestViewGroup = findViewById(R.id.test_view_group);

        testWeakReference();
        List list = new ArrayList<>();
        list.add("666");
        list.add(1);
        testT(list);
    }

    private void testWeakReference() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeakReference<People> reference = new WeakReference<>(new People());

            }
        }).start();
    }

    private void testT(List<?> pList){
        Toast.makeText(this, "泛型测试 = "+pList.get(0).toString(), Toast.LENGTH_SHORT).show();

    }

    private void addStub() {
        TestStubView testStubView = new TestStubView(this);
        testStubView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(300, 300);
        layoutParams.topMargin = 30;
        layoutParams.leftMargin = 50;
        mTestViewGroup.addView(testStubView, layoutParams);
    }
}
