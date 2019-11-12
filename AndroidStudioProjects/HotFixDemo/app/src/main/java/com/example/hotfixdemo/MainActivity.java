package com.example.hotfixdemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotfixdemo.utils.TinkerManager;

import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View pView) {
        Toast.makeText(this,"12441215",Toast.LENGTH_SHORT).show();
        if (hasPermission()) {
            loadPatch();
        } else {
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
