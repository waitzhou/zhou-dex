package com.example.base.rxjava;

/**
 * Author : ZSX
 * Date : 2019-12-03
 * Description :
 */
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by Pan on 2017/6/2.
 */
public class AESUtils {
    private static final String KEY = "5bbe702ab0ff86e9";

    /**
     * 数据解密
     * */
    public static String decrypt(String base64EncodeStr) {
        byte[] decrypted = null;
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] data = Base64.decode(base64EncodeStr,Base64.DEFAULT);
            decrypted = cipher.doFinal(data);
            return new String(decrypted, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 　数据加密
     * */
    public static String encrypt(String sourceStr) {
        byte[] encrypted = null;
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] data = sourceStr.getBytes("utf-8");
            encrypted = cipher.doFinal(data);
            return Base64.encodeToString(encrypted,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

