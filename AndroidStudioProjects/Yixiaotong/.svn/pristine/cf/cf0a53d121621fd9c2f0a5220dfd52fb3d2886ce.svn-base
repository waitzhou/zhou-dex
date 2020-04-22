package com.zhexinit.yixiaotong.utils.encryptutil;


/**
 * 
 * 基础的拆解参数的类
 * 
 * @author <a href="mailto:zhuqingwei@zhexinit.com" >朱晴蔚</a>
 * @version 1.0.0
 */
public abstract class AbstractVerifyUtil {
	private final static String SIGN_IDEX_FALG = "sign=";
	private final static String PARAMS_IDEX_FALG = "&sign=";
	/**
	 * 
	 * 验证密文是否有效
	 * @author <a href="mailto:zhuqingwei@zhexinit.com" >朱晴蔚</a>
	 * @param decryptedParams
	 * @return
	 */
	public abstract boolean doVerify(String decryptedParams);
	/**
	 * 
	 * 从明文中获取参数
	 * @author <a href="mailto:zhuqingwei@zhexinit.com" >朱晴蔚</a>
	 * @param encryptParams
	 * @return
	 * @throws Exception
	 */
	public abstract String getDescryptedParams(String encryptParams) throws Exception;
	
	public  String getSign(String decryptedParams) {
		int index = decryptedParams.indexOf(SIGN_IDEX_FALG);
		if (index != -1) {
			return decryptedParams.substring(index + SIGN_IDEX_FALG.length());
		}
		return null;
	}

	public String getParams(String decryptedParams) {
		int index = decryptedParams.indexOf(PARAMS_IDEX_FALG);
		if (index != -1) {
			return decryptedParams.substring(0, decryptedParams.indexOf(PARAMS_IDEX_FALG));
		}
		return null;
	}

}
