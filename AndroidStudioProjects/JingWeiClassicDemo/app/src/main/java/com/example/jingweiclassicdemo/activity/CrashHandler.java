package com.example.jingweiclassicdemo.activity;

/**
 * Author : ZSX
 * Date : 2020-04-14
 * Description :
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public CrashHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            getStackTraceInfo(e);
        }finally {
            uncaughtExceptionHandler.uncaughtException(t, e);
        }
    }

    /**
     * @param throwable 异常信息
     * */
    private void getStackTraceInfo(Throwable throwable){
        
    }
}
