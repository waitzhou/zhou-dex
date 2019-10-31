package com.example.aidltestmain;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;
import com.didi.virtualapk.internal.PluginContentResolver;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.btn_01);
        Button button2 = findViewById(R.id.btn_02);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                if(hasPermission()){
                    loadPlugin();
                }else {
                    requestPermission();
                }
                break;
            case R.id.btn_02:
                show();
                break;
            default:
                break;
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (10 == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                this.loadPlugin();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadPlugin() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "sdcard was NOT MOUNTED!", Toast.LENGTH_SHORT).show();
        }
        PluginManager pluginManager = PluginManager.getInstance(this);
        File apk = new File(this.getPackageResourcePath(), "Test.apk");
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
                Log.i(TAG, "Loaded plugin from apk: " + apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File file = new File(this.getFilesDir(), "Test.apk");
                java.io.InputStream inputStream = this.getAssets().open("Test.apk", 2);
                java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
                pluginManager.loadPlugin(file);
                Log.i(TAG, "Loaded plugin from assets: " + file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void show() {

        final String pkg = "com.example.AIDLTest2";
        if (PluginManager.getInstance(this).getLoadedPlugin(pkg) == null) {
            Toast.makeText(this, "plugin [com.didi.virtualapk.demo] not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        // test Activity and Service
        Intent intent = new Intent();
        intent.setClassName(this, "com.example.AIDLTest2.MainActivity");
        startActivity(intent);
    }
}
