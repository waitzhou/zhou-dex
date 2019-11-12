package com.example.componenthotfixdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.componenthotfixdemo.utils.TinkerManager;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        Intent intent = new Intent();
        intent.setClassName(this,"com.example.login.LoginActivity");
        startActivity(intent);
    }

    public void load(View view) {
        if(hasPermission()){
            loadPatch();
        }else{
            requestPermission();
        }
    }

    public void loadPatch() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        TinkerManager.loadPatch(path + "/patch_signed_7zip.apk");
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //requestPermission();
            } else {
                loadPatch();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
