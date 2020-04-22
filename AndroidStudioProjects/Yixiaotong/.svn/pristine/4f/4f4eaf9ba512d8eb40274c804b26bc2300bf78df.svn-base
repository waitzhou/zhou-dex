package com.zhexinit.yixiaotong.utils;

import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudLifecycleCredentials;
import com.tencent.qcloud.core.auth.SessionQCloudCredentials;
import com.tencent.qcloud.core.common.QCloudClientException;

/**
 * Created by:xukun
 * date:2018/12/11
 * description:
 */
public class MyCosCredentialProvider extends BasicLifecycleCredentialProvider {

    private String tmpSecretId = null;
    private String tmpSecretKey = null;
    private String sessionToken = null;
    private long expiredTime = 0;

    private static MyCosCredentialProvider provider;

    public static MyCosCredentialProvider getInstance() {
        if (provider == null) {
            provider = new MyCosCredentialProvider();
            return provider;
        } else
            return provider;
    }

    public void setData(String tmpSecretId, String tmpSecretKey, String sessionToken, long expiredTime) {
        this.tmpSecretId = tmpSecretId;
        this.tmpSecretKey = tmpSecretKey;
        this.sessionToken = sessionToken;
        this.expiredTime = expiredTime;
    }

    @Override
    protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {

        // 首先从您的临时密钥服务器获取包含了签名信息的响应

        // 然后解析响应，获取密钥信息


        // 最后返回临时密钥信息对象
        return new SessionQCloudCredentials(tmpSecretId, tmpSecretKey, sessionToken, expiredTime);
    }
}
