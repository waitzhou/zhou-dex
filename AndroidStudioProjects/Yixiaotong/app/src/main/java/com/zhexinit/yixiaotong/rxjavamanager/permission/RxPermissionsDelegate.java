package com.zhexinit.yixiaotong.rxjavamanager.permission;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Author:@zhousx
 * date: 2018/4/16/14:59.
 * function :权限申请
 */

public class RxPermissionsDelegate {

    private static RxPermissionsDelegate sInstance;
    private Activity mContext;

    public static RxPermissionsDelegate getInstance(Activity context) {
        if (sInstance == null) {
            RxPermissionsDelegate delegate = null;
            synchronized (RxPermissionsDelegate.class) {
                if (sInstance == null) {
                    delegate = new RxPermissionsDelegate(context);
                    sInstance = delegate;
                }
            }
        }
        return sInstance;
    }

    public RxPermissionsDelegate(Activity context) {
        this.mContext = context;
    }

    public boolean isShouldRequestPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private RxPermissions mPermissions;

    public void requestPermissions(final PermissionCallBack callBack, final String... strings) {
        if (strings != null && strings.length != 0) {
            if (mPermissions == null) {
                mPermissions = new RxPermissions(mContext);
                mPermissions.requestEach(strings)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    callBack.permissionSuccess();
                                } else {
                                    mPermissions.shouldShowRequestPermissionRationale(mContext, strings);
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(mContext, "please give params about permission", Toast.LENGTH_SHORT).show();
        }
    }

    public interface PermissionCallBack {
        void permissionSuccess();
    }
}
