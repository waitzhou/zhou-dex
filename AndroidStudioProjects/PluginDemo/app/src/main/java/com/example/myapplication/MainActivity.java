package com.example.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 2018;
    String apkName = "com.example.classifacationplugin_20191101114123.apk";

    String TAG = "MainActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Utils.extractAssets(this, apkName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasPermission()) {
            Log.d(TAG, "loadPlugin");
            loadPlugin();
        } else {
            requestPermission();
        }
        ((TextView)findViewById(R.id.text222)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, "com.example.classifacationplugin.HomeActivity");
        intent.putExtra("type","999");
        startActivity(intent);
    }

    private boolean hasPermission() {
        Log.d(TAG, "hasPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    private void requestPermission() {

        Log.d(TAG, "requestPermission");
        final MainActivity context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean b = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (b) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
            } else {
                Toast.makeText(this, "需要读取SD卡权限", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("权限申请提示：")
                        .setMessage("需要读取SD卡权限，前往设置打开")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.goSettingPage();
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alertDialog.show();
            }
        }
    }

    private void goSettingPage() {
        Context context = MainActivity.this.getApplicationContext();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        MainActivity.this.startActivityForResult(intent, PERMISSION_REQUEST_CODE_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_STORAGE:
                if (hasPermission()) {
                    Log.d(TAG, "loadPlugin");
                    loadPlugin();
                } else {
                    requestPermission();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                loadPlugin();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void loadPlugin() {
        File apk = getFileStreamPath(apkName);

        if (apk.exists()) {
            try {
                PluginManager.getInstance(this).loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
        } else {
            Toast.makeText(this, "plugin apk not exists !!!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "plugin apk not exists !!!");
        }
    }
}
