package com.example.gradletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gradletest.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addView();
    }

    private void addView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,-2);
        lp.topMargin = 50;
        //lp.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.cv);
        FlowLayout layout = new FlowLayout(this);
        layout.addTextList(initData(),5);
        LinearLayout relativeLayout = findViewById(R.id.rv);
        relativeLayout.addView(layout,lp);
    }

    private List<String> initData(){
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(String.valueOf(i));
        }
        return strings;
    }
}
