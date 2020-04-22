package com.example.jingweiclassicdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jingweiclassicdemo.R;
import com.example.jingweiclassicdemo.utils.PermissionUtils;


/**
 * Author : ZSX
 * Date : 2019-12-19
 * Description : 获取权限占位activity
 * */
public class PermissionPlaceHolderActivity extends AppCompatActivity {

    final int REQUEST_CODE = 0x1010;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_placeholder);
        checkPermission();
    }

    private void checkPermission(){
        String[] permissions = getIntent().getStringArrayExtra("permissions");
        if(permissions != null && permissions.length != 0){
            for (String permission : permissions) {
                Log.d("666666", "checkPermission: " + permission);
            }
            PermissionUtils.getInstance().checkPermission(this, permissions,REQUEST_CODE);
        }else
            finish();
    }

    public static void enter(Context context, String[] permissions, PermissionsListener listener){
        mListener = listener;
        Intent intent = new Intent(context, PermissionPlaceHolderActivity.class);
        intent.putExtra("permissions", permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            for (int i = 0; i < permissions.length; i++) {
                Log.d("123456", "onRequestPermissionsResult: permission = "+permissions[i]+"    grant = "+grantResults[i]);
            }
            mListener.onPermissionListener(permissions, grantResults);
            finish();
        }
    }

    private static PermissionsListener mListener;

    public interface PermissionsListener{
        void onPermissionListener(@NonNull String[] permissions, @NonNull int[] grantResults);
    }
}
