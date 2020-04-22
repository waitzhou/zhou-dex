package com.example.jingweiclassicdemo.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.example.jingweiclassicdemo.R;
import com.example.jingweiclassicdemo.annotations.PermissionAnnotation;
import com.example.jingweiclassicdemo.utils.BitmapUtils;
import com.example.jingweiclassicdemo.widget.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final String PLUGIN_CUSTOM_NAME = "CustomPlugin.apk";
    private static final String PLUGIN_HOME_NAME = "HomePlugin.apk";
    private final int REQUEST_PERMISSION_CODE = 100;
    private String tempPluginName;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Utils.extractAssets(newBase, PLUGIN_CUSTOM_NAME);
        Utils.extractAssets(newBase, PLUGIN_HOME_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (hasPermission()) {
            loadPlugin();
        } else {
            requestPermission();
        }
        loadImage();
    }

    @PermissionAnnotation(permissionType = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void loadImage() {
        Bitmap bitmap = BitmapUtils.compressConfig(this, R.mipmap.pic_term_4);
        File file = BitmapUtils.saveBitmap(this,bitmap,"pic_term_4.jpg");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        assert imageView != null;
        imageView.setImageURI(Uri.fromFile(file));
    }

    public void plugin1Click(View view) {
        if (hasPermission()) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.example.customplugin.CustomMainActivity");
            startActivity(intent);
        } else {
            Log.d(TAG, "plugin2Click: 没有权限");
        }
    }

    public void plugin2Click(View view) {
        if (hasPermission()) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.example.homeplugin.HomeMainActivity");
            startActivity(intent);
        } else {
            Log.d(TAG, "plugin2Click: 没有权限");
        }
    }

    private boolean hasPermission() {
        Log.d(TAG, "hasPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPlugin();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadPlugin() {
        loadPlugin(PLUGIN_CUSTOM_NAME);
        loadPlugin(PLUGIN_HOME_NAME);
    }

    private void loadPlugin(String pluginName) {
        File apk = getFileStreamPath(pluginName);
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


    public void otherFunction(View view) {
        startActivity(new Intent(this, OtherFunctionActivity.class));
    }
}
