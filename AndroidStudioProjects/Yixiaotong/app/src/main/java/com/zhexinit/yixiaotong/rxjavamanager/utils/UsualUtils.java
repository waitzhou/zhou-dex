/**  
 * @PackageName cn.com.whty.diditravel.utils 
 * @Description: 
 * @author  陈洋
 * @date 2016年8月19日 上午9:24:17  
 */ 
package com.zhexinit.yixiaotong.rxjavamanager.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: UsualUtils 
 * @Description:  常用的集中工具类
 * @author  cheny
 * @date 2016年8月19日 上午9:24:17 
 * @fix
 */

public class UsualUtils {
	
	private final static int kSystemRootStateUnknow = -1;  
	private final static int kSystemRootStateDisable = 0;  
	private final static int kSystemRootStateEnable = 1;  
	private static int systemRootState = kSystemRootStateUnknow;  

	// 生成随机数
		public static String testRandom1() {
			// s="";
			String strRand = "";
			for (int i = 0; i < 20; i++) {
				strRand += String.valueOf((int) (Math.random() * 10));
			}
			return strRand;

		}
	/**   
	 * @Title: isRootSystem   
	 * @Description: TODO(判断手机是否root)   
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 */  
	public static boolean isRootSystem() {  
	    if (systemRootState == kSystemRootStateEnable) {  
	        return true;  
	    } else if (systemRootState == kSystemRootStateDisable) {  
	        return false;  
	    }  
	    File f = null;
	    final String kSuSearchPaths[] = { "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/" };
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
	 * 
	 * @Description: 获取手机的系统版本号 @param: @return: String @throws
	 */
	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager =context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}
 /**   
 * @Title: changeMoney   
 * @Description: TODO(金额转换)   
 * @param: @param str
 * @param: @return      
 * @return: String      
 * @throws   
 */  
public static BigDecimal changeMoney(String str){
	// 缴费金额的单位转换
			double sss = Double.parseDouble(str);
			BigDecimal bd = new BigDecimal(sss / 100);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
	
	return bd;
	 
 }
	/**
	 * 验证手机电话号码
	 * 
	 * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
	 * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
	 * 
	 * @param
	 * @return
	 */
	public static boolean checkMobilephone(String mobilephone) {
		String mobile= "^1(3[0-9]|4[579]|5[0-35-9]|7[01345678]|8[0-9])\\d{8}$";//手机号码
		String cm = "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";// 中国移动正则
		String cu = "^1(3[0-2]|4[5]|5[256]|7[016]|8[56])\\d{8}$";// 中国联通正则
		String ct = "^1(3[34]|53|7[07]|8[019])\\d{8}$";// 中国电信正则
		if (Pattern.matches(mobile, mobilephone)) {
			return true;
		}
		return true;
	}
	
	/**   
	 *  
	 * @Description:判断密码的复杂程度
	 * @param:      
	 * @return: boolean      
	 * @throws   
	 */ 
	public static boolean CheckMyname(String pwd) {
		   boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字  
	        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母  
	        for (int i = 0; i < pwd.length(); i++) {  
	            if (Character.isDigit(pwd.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
	                isDigit = true;  
	            } else if (Character.isLetter(pwd.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
	                isLetter = true;  
	            }  
	        }  
	        String regex = "^[a-zA-Z0-9]+$";
	        boolean isRight = isDigit && isLetter && pwd.matches(regex);  
	        return isRight; 

	}
	/**  
     * 该方法主要使用正则表达式来判断字符串中是否包含字母  
     * @author fenggaopan 2015年7月21日 上午9:49:40  
     * @param cardNum 待检验的原始卡号  
     * @return 返回是否包含  
     */  
    public static boolean judgeContainsStr(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();  
    }
	/** 
	 * 方法的概述	sha加密
	 * @param decript
	 * @return
	 * @return String
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public static String SHA(String decript) {
		try {
			MessageDigest digest = MessageDigest
					.getInstance("SHA");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	    /** 
	     * MD5加密的工具类
	     * @param string
	     * @return
	     * @throws Exception
	     * @return String
	     * @throws IOException
	     * @throws NullPointerException
	     */
	    public static String encode(String string) throws Exception {
	        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	        StringBuilder hex = new StringBuilder(hash.length * 2);
	        for (byte b : hash) {
	            if ((b & 0xFF) < 0x10) {
	                hex.append("0");
	            }
	            hex.append(Integer.toHexString(b & 0xFF));
	        }
	        return hex.toString();
	    }
	
	    /**
	     * @throws JSONException
	     *  
	     * @Description: 获取签名后的数据
	     * @param:   上传的json对象   
	     * @return: String      
	     * @throws   
	     */ 
	public static JSONObject getSign(JSONObject object, String pbk) throws JSONException {
		String keyset = "";
		String valueset = "";
		JSONObject obj = new JSONObject();
		Iterator<?> it = object.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			if(!it.hasNext()){
				keyset=keyset+key;
			}else{
			keyset = keyset + key + ",";
			}
			String value = object.getString(key);
			valueset = valueset + value;

		}
		obj.put("key", keyset);
		try {
			obj.put("sign",UsualUtils.SHA(UsualUtils.encode(valueset+pbk).toUpperCase()).toUpperCase() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;

	}
	
	    /** 
	     * 方法的概述	获取缓存
	     * @param context
	     * @return
	     * @throws Exception
	     * @return String
	     * @throws IOException
	     * @throws NullPointerException
	     */
	    public static String getTotalCacheSize(Context context) throws Exception {
	    	long cacheSize = getFolderSize(context.getCacheDir());
	    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
	    		cacheSize += getFolderSize(context.getExternalCacheDir());
	        }  
	    	return getFormatSize(cacheSize);
	    }
	    
	    /** 
	     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * 
	     *  
	     * @param context 
	     */  
	    public static void cleanInternalCache(Context context) {
	        deleteDir(context.getCacheDir());
	    }  
	  
	    /** 
	     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * 
	     *  
	     * @param context 
	     */  
	    public static void cleanDatabases(Context context) {
	        deleteDir(new File("/data/data/"
	                + context.getPackageName() + "/databases"));  
	    }  
	  
	    /** 
	     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * 
	     *  
	     * @param context 
	     */  
	    public static void cleanSharedPreference(Context context) {
	    	deleteDir(new File("/data/data/"
	                + context.getPackageName() + "/shared_prefs"));  
	    }  
	  
	    /** 
	     * * 按名字清除本应用数据库 * * 
	     *  
	     * @param context 
	     * @param dbName 
	     */  
	    public static void cleanDatabaseByName(Context context, String dbName) {
	        context.deleteDatabase(dbName);  
	    }  
	  
	    /** 
	     * * 清除/data/data/com.xxx.xxx/files下的内容 * * 
	     *  
	     * @param context 
	     */  
	    public static void cleanFiles(Context context) {
	    	deleteDir(context.getFilesDir());  
	    }  
	  
	    /** 
	     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) 
	     *  
	     * @param context 
	     */  
	    public static void cleanExternalCache(Context context) {
	        if (Environment.getExternalStorageState().equals(
	                Environment.MEDIA_MOUNTED)) {
	        	deleteDir(context.getExternalCacheDir());  
	        }  
	    }  
	    /** 
	     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * 
	     *  
	     * @param filePath 
	     * */  
	    public static void cleanCustomCache(String filePath) {
	    	deleteDir(new File(filePath));
	    }  
	  
	    /** 
	     * * 清除本应用所有的数据 * * 
	     *  
	     * @param context 
	     * @param
	     */  
	    public static void cleanApplicationData(Context context) {
	        cleanInternalCache(context);  
	        cleanExternalCache(context);  
//	        cleanDatabases(context);  
//	        cleanSharedPreference(context);  
//	        cleanFiles(context);  
	        Toast.makeText(context, "清除缓存成功", Toast.LENGTH_SHORT).show();
//	        if (filepath == null) {  
//	            return;  
//	        }  
//	        for (String filePath : filepath) {  
//	            cleanCustomCache(filePath);  
//	        }  
	    }  
	  
	    /** 
	     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * 
	     *  
	     * @param
	     */  
	    private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	      
	    // 获取文件  
	    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据  
	    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据  
	    public static long getFolderSize(File file) throws Exception {
	        long size = 0;  
	        try {  
	            File[] fileList = file.listFiles();
	            for (int i = 0; i < fileList.length; i++) {  
	                // 如果下面还有文件  
	                if (fileList[i].isDirectory()) {  
	                    size = size + getFolderSize(fileList[i]);  
	                } else {  
	                    size = size + fileList[i].length();  
	                }  
	            }  
	        } catch (Exception e) {
	            e.printStackTrace();  
	        }  
	        return size;  
	    }  
	      
	    /** 
	     * 删除指定目录下文件及目录 
	     *  
	     * @param deleteThisPath 
	     * @param
	     * @return 
	     */  
	    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
	        if (!TextUtils.isEmpty(filePath)) {
	            try {  
	                File file = new File(filePath);
	                if (file.isDirectory()) {// 如果下面还有文件  
	                    File files[] = file.listFiles();
	                    for (int i = 0; i < files.length; i++) {  
	                        deleteFolderFile(files[i].getAbsolutePath(), true);  
	                    }  
	                }  
	                if (deleteThisPath) {  
	                    if (!file.isDirectory()) {// 如果是文件，删除  
	                        file.delete();  
	                    } else {// 目录  
	                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除  
	                            file.delete();  
	                        }  
	                    }  
	                }  
	            } catch (Exception e) {
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	      
	    /** 
	     * 格式化单位 
	     *  
	     * @param size 
	     * @return 
	     */  
	    public static String getFormatSize(double size) {
	        double kiloByte = size / 1024;  
	        if (kiloByte < 1) {  
	            return size + "Byte";  
	        }  
	  
	        double megaByte = kiloByte / 1024;  
	        if (megaByte < 1) {  
	            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
	            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
	                    .toPlainString() + "KB";  
	        }  
	  
	        double gigaByte = megaByte / 1024;  
	        if (gigaByte < 1) {  
	            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
	            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
	                    .toPlainString() + "MB";  
	        }  
	  
	        double teraBytes = gigaByte / 1024;  
	        if (teraBytes < 1) {  
	            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
	            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
	                    .toPlainString() + "GB";  
	        }  
	        BigDecimal result4 = new BigDecimal(teraBytes);
	        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
	                + "TB";  
	    }  
	      
	      
	    public static String getCacheSize(File file) throws Exception {
	        return getFormatSize(getFolderSize(file));  
	    }  
}
