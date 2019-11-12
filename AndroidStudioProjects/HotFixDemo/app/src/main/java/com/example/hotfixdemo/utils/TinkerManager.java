package com.example.hotfixdemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Author : ZSX
 * Date : 2019-11-06
 * Description :
 */
public class TinkerManager {
    //标记是否安装过Tinker
    private static boolean isInstalled =  false;

    private static ApplicationLike mAppLike;

    /**
     * 完成tinker的初始化
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled){
            return;
        }

        //完成Tinker的初始化
        TinkerInstaller.install(mAppLike);
        isInstalled = true;
    }

    /**
     * 完成patch文件的加载
     * @param path
     */
    public static void loadPatch(String path){
        if (Tinker.isTinkerInstalled()){
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),path);
        }else{
            Toast.makeText(getApplicationContext(),"tinker not installed",Toast.LENGTH_SHORT).show();
        }
    }

    private static Context getApplicationContext(){
        if (mAppLike != null){
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }

}
