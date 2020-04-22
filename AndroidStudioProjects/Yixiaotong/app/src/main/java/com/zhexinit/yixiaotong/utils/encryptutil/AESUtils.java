package com.zhexinit.yixiaotong.utils.encryptutil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Pan on 2017/6/2.
 */
public class AESUtils {
    public static byte[] decrypt(byte[] data, String key) {
        byte[] decrypted = null;
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    public static byte[] encrypt(byte[] data, String key) {
        byte[] encrypted = null;
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }


}
