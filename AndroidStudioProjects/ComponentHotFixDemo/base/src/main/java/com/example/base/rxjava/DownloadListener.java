package com.example.base.rxjava;

/**
 * Author:@zhousx
 * date: 2018/3/5/9:57.
 * function :
 */

public interface DownloadListener {

    void startDownload();

    void inProgress(long currentBytes, long total, boolean finish);

    void fail(String msg);

    void downloadFinish(String path);
}
