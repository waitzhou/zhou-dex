package com.example.share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonbase.ServiceFactory;
import com.example.base.utils.HttpUtils;

@Route(path = "/share/share")
public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

    }

    public void btn1Click(View pView){
        String id = ServiceFactory.getInstance().getIAccountService().getAccountId();
        ((TextView)pView).setText(id);
    }

    public void btn2Click(View pView){
        ARouter.getInstance().build("/login/login").withString("param","from share").navigation();
    }
}
