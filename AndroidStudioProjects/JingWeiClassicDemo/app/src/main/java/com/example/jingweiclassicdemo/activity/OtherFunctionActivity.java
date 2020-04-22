package com.example.jingweiclassicdemo.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.example.jingweiclassicdemo.R;
import com.example.jingweiclassicdemo.annotations.PermissionAnnotation;
import com.example.jingweiclassicdemo.designpattern.ObserverTestActivity;
import com.example.jingweiclassicdemo.test.BinderTestActivity;
import com.example.jingweiclassicdemo.widget.DefineComponentActivity;

/**
 * Author : ZSX
 * Date : 2020-01-06
 * Description :
 */
public class OtherFunctionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_function);
    }

    public void designPattern(View view) {
        startActivity(new Intent(this, ObserverTestActivity.class));
    }

    @PermissionAnnotation(permissionType = {Manifest.permission.READ_SMS})
    public void aopAnnotation(View view) {

    }

    public void defineComponent(View view) {
        startActivity(new Intent(this, DefineComponentActivity.class));
    }

    public void optimizedProblem(View view) {
    }

    public void reflectProblem(View view) {
        Intent intent = new Intent();
        Notification build = new NotificationCompat.Builder(this)
                .setContentTitle("测试通知")
                .setContentText("测试通知内容")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify((int) (System.currentTimeMillis() / 1000L), build);

    }

    public void gotoBinder(View view) {
        startActivity(new Intent(this, BinderTestActivity.class));
    }
}
