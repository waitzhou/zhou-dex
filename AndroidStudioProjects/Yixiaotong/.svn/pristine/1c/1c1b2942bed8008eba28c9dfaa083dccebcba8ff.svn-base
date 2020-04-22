package com.zhexinit.yixiaotong.rxjavamanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class Config {

    public static String Pbk = "30818902818100883C08A9B7821C2FBCF5D876A39503BFCE563999D06A7BDD81EC061454326B6DEFEB5CC30ABB66D9A76D41BD9A41DA9857C26D5CF17FB48E07684B54C634E4DB234D53253B802F007AB5F4316BAE2892D54AF3CC2E9ED7895CB8B2A254104AD61ED618AA95869067A0D9E891B87EBB2BE4695C2D62A898CA078BEC7555864F230203010001";
    public static Properties props;
    /**
     * 语言
     */
    public static String LANGUAGE = "zh";

    /**
     * 总缓存路径
     */
    public static String PATH_SYSTEM_CACHE;
    /**
     * icon缓存路径
     */
    public static String PATH_CACHE_ICON;
    /**
     * 图片缓存路径
     */
    public static String PATH_CACHE_IMAGES;
    /**
     * 图片临时
     */
    public static String PATH_IMAGE_TEMP_PATH;
    /**
     * 临时下载文件
     */
    public static String PATH_DOWN_FILE;


    public static void init(Context context) {
        props = new Properties();
        //设置语言
        setLocale(context);

        //SD卡不存在就缓存到手机内存
        try {
            //  /storage/emulated/0/Android/data/com.cb.weibo/cache/system
            Config.PATH_SYSTEM_CACHE = context.getExternalCacheDir().getPath() + "/system";
        } catch (Exception e) {
            PATH_SYSTEM_CACHE = context.getCacheDir().getPath() + "/system";
        }

        //以下均为缓存目录
        PATH_IMAGE_TEMP_PATH = PATH_SYSTEM_CACHE + "/imageTemp/";
        File imageTemp = new File(PATH_IMAGE_TEMP_PATH);
        imageTemp.mkdirs();

        /**文件缓存*/
        PATH_DOWN_FILE = PATH_SYSTEM_CACHE + "/downloadFile/";
        File fileTemp = new File(PATH_DOWN_FILE);
        fileTemp.mkdirs();
    }


    private static void setLocale(Context context) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        String localeName = Locale.TRADITIONAL_CHINESE.getLanguage() + "_" + Locale.TRADITIONAL_CHINESE.getCountry();
        if (LANGUAGE.equals(localeName)) {
            locale = Locale.TRADITIONAL_CHINESE;
        }
        // 更新默认语言
        Locale.setDefault(locale);
        Configuration config = context.getResources()
                .getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    /**
     * @param @param  context
     * @param @return
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @throws
     * @Description: TODO(获取当前app版本序号)
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 获取APP版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机设备唯一标识
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        @SuppressLint("HardwareIds")
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    /**
     * 返回手机版本
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * @Title: isRootSystem
     * @Description: TODO(判断手机是否root)
     * @param: @return
     * @return: boolean
     * @throws
     */
    private final static int kSystemRootStateEnable = 1;
    private final static int kSystemRootStateUnknow = -1;
    private final static int kSystemRootStateDisable = 0;
    private static int systemRootState = kSystemRootStateUnknow;

    public static boolean isRootSystem() {
        if (systemRootState == kSystemRootStateEnable) {
            return true;
        } else if (systemRootState == kSystemRootStateDisable) {
            return false;
        }
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return true;
                }
            }
        } catch (Exception e) {
        }
        systemRootState = kSystemRootStateDisable;
        return false;
    }

    /**
     * @Description: TODO(判断手机是否暗转了某一个app) @param @param context @param @param
     * str 判定的app包名 @param @return 设定文件 @return boolean 返回类型 @throws
     */
    public static boolean isInstallApk(Context context, String str) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * WiFi是否可用
     */
    public static boolean isConnectToWifi(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 网络是否可用
     */
    public static boolean isConnectToNetWork(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }


    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String rs = byte2hex(digesta);
        return rs;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            device_id = tm.getDeviceId();
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearCookies(Context context, WebView webView) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        if (webView != null) {
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearCache(true);
        }
    }

    /*@SuppressWarnings("deprecation")
    public static void clearCookies(Context context, WebView webView)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieManager.removeExpiredCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }*/

}

