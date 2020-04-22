package com.zhexinit.yixiaotong.utils.encryptutil;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SymmetricVerifyUtil extends AbstractVerifyUtil {
	private static String AES_KEY = "d9525l7ed7dad3sd";
 //   private SymmetricVerifyUtil instance = 
	public String getDescryptedParams(String encryptParams){
		// UrlDecode密文
        String urlDecoded = null;
        try {
            urlDecoded = URLDecoder.decode(encryptParams, "UTF-8");
            // base64解密
            byte[] base64Decoded = Base64Utils.decode(urlDecoded);
            // AES解密
            byte[] aesDecrypted = AESUtils.decrypt(base64Decoded, AES_KEY);
            // 获得明文
            return new String(aesDecrypted);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

	public boolean doVerify(String decryptedParams) {
		// 生成原文的md5
		String params = getParams(decryptedParams);
		String md5String =  DigestUtils.md5Hex(params);
		String sign = getSign(decryptedParams);
		return sign.equals(md5String);

	}

	public static String encrypt(String params) throws Exception {
		// 使用客户端私钥对该串数据签名
		String sign = new String(Hex.encodeHex(DigestUtils.md5(params)));
		
		// 将签名组合入参数
		params = params + "&sign=" + sign;
		// AES加密
		byte[] aesEncrypted = AESUtils.encrypt(params.getBytes(), AES_KEY);
		// 将密文base64
		String base64Encrypted = Base64Utils.encode(aesEncrypted);
		return URLEncoder.encode(base64Encrypted, "UTF-8");
	}

}
