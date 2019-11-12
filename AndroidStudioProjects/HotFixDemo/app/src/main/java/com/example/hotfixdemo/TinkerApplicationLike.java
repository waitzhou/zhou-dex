package com.example.hotfixdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.example.hotfixdemo.utils.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Author : ZSX
 * Date : 2019-11-06
 * Description :
 */
@DefaultLifeCycle(application = ".HotFixTinkerApplication",flags = ShareConstants.TINKER_ENABLE_ALL,loadVerifyFlag = false)
public class TinkerApplicationLike extends DefaultApplicationLike {

    public TinkerApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        TinkerManager.installTinker(this);
        MultiDex.install(base);
    }
}
