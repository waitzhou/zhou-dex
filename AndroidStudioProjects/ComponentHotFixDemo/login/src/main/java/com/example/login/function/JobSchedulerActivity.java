package com.example.login.function;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.base.Constant;
import com.example.base.rxjava.entity.MultiRequestBody;
import com.example.base.rxjava.entity.RequestStyle;
import com.example.login.InstallUtils;
import com.example.base.rxjava.DownloadListener;
import com.example.base.rxjava.manager.HttpManager;
import com.example.base.rxjava.ResultCallBack;
import com.example.login.R;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import retrofit2.http.POST;

/**
 * Author : ZSX
 * Date : 2019-11-28
 * Description :  定时任务
 */
public class JobSchedulerActivity extends AppCompatActivity {

    private String TAG = "JobSchedulerService";

    private int PERMISSION_REQUEST_CODE_STORAGE = 100;

    TextView mTextView;

    private int jobId = 100;
    Disposable taskDisposable,downloadDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
        mTextView = findViewById(R.id.text);
        this.sendRequest();
    }

    private void sendRequest(){
        MultiRequestBody requestBody = new MultiRequestBody();
        requestBody.style = RequestStyle.POST;
        requestBody.path = Constant.HOME_1;
        Map<String,Object> map = new HashMap<>();
        map.put("type",1);
        requestBody.map = map;

        MultiRequestBody requestBody2 = new MultiRequestBody();
        requestBody2.style = RequestStyle.POST;
        requestBody2.path = Constant.HOME_2;
        final List<MultiRequestBody> list = new ArrayList<>();
        list.add(requestBody);
        list.add(requestBody2);

        HttpManager.getInstance(this).doMultiExecute(list, new ResultCallBack<List<Object>>() {
            @Override
            public void onSuccess(List<Object> pList) {
                for (int i = 0; i < pList.size(); i++) {
                    Log.d(TAG, "onSuccess:\n i = " +i+"    "+new Gson().toJson(pList.get(i)));
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //bindService(new Intent(this,JobSchedulerService.class));
    }

    JobScheduler jobScheduler;

    public void startScheduler(View view) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
                builder.setMinimumLatency(5000);
                assert jobScheduler != null;
                jobScheduler.schedule(builder.build());
            }
        }catch (Exception p){
            p.printStackTrace();
        }
    }

    public void downloadAPK(View view) {
        if(hasPermission()){
            downloadDisposable = HttpManager.getInstance(this).downloadFile(
                    "http://jingweiguoxue-cdn.zhexinit.com/app-releaseStaging.apk",
                    "1.0.0app-releaseStaging.apk", new DownloadListener() {
                        @Override
                        public void startDownload() {

                        }

                        @Override
                        public void inProgress(long currentBytes, long total, boolean finish) {

                        }

                        @Override
                        public void fail(String msg) {
                            Log.d(TAG, "fail: 下载失败 = "+msg);
                        }

                        @Override
                        public void downloadFinish(String path) {
                            Log.d(TAG, "downloadFinish: 下载完成= "+path);
                            installApk(path);
                        }
                    });
        }else {
            requestPermission();
        }
    }

    private void installApk(String path){
        InstallUtils utils = new InstallUtils(this,path);
        utils.install();
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

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(downloadDisposable != null && !downloadDisposable.isDisposed()){
            Log.d(TAG, "onDestroy: 停止下载任务");
            downloadDisposable.dispose();
        }
    }

    public void deleteDir(View view) {
        deleteDirectory(HttpManager.getInstance(this).getDownloadDir());
    }

    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = files[i].delete();
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
}
