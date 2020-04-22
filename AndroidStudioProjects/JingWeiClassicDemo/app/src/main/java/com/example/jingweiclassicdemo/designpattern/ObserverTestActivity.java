package com.example.jingweiclassicdemo.designpattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jingweiclassicdemo.R;
import com.example.jingweiclassicdemo.designpattern.observerpattern.MyObserable;
import com.example.jingweiclassicdemo.designpattern.observerpattern.MyObserver;
import com.example.jingweiclassicdemo.designpattern.proxypattern.DynamicProxy;
import com.example.jingweiclassicdemo.designpattern.proxypattern.IService;
import com.example.jingweiclassicdemo.designpattern.proxypattern.StaticProxy;
import com.example.jingweiclassicdemo.designpattern.proxypattern.UserImpl;
import com.example.jingweiclassicdemo.designpattern.singletonpattern.SingletonAtomic;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author : ZSX
 * Date : 2020-01-02
 * Description :
 */
public class ObserverTestActivity extends AppCompatActivity {

    TextView mTextView;
    MyObserable myObserable;
    MyObserver mObserver;

    private int mInt = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_test);
        String s = "L";
        Log.d("------->  length  ", "onCreate: "+s.length());
        init();
    }

    private void init() {

        mTextView = (TextView) findViewById(R.id.text);
        myObserable = new MyObserable();
        mObserver = new MyObserver();
        myObserable.addObserver(mObserver);
    }

    public void click(View view) {
        Log.d("-------　>", "click: ");
        //++mInt;
        myObserable.notifyDataChanged("12306");
        Lock lock = new ReentrantLock();
        lock.tryLock();
        lock.unlock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myObserable.deleteObservers();
    }

    public void staticProxy(View view) {
        StaticProxy staticProxy = new StaticProxy(new UserImpl());
        staticProxy.add("静态代理 add");
        staticProxy.remove("静态代理 remove");
    }

    public void dynamicProxy(View view) {
        DynamicProxy dynamicProxy = new DynamicProxy(new UserImpl());
        IService proxy = (IService)dynamicProxy.getProxy();
        Method method = null;
        try {
            method = proxy.getClass().getDeclaredMethod("add", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            dynamicProxy.invoke(proxy,method,new Object[]{"hhh"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        /*proxy.add("动态代理 add");
        proxy.remove("动态代理  remove");*/
    }

    public void singletonInstance(View view) {
        AtomicReference<String> reference = new AtomicReference<>("123");
        String s = reference.get();
        boolean b = reference.compareAndSet("1234", s);
        Log.d("test", "singletonInstance:11 =  "+b);
        boolean b2 = reference.compareAndSet("123", s);
        Log.d("test", "singletonInstance:22 =  "+b2);
        SingletonAtomic.getINSTANCE().test("1");
        SingletonAtomic.getINSTANCE().test("2");
        SingletonAtomic.getINSTANCE().test("3");
    }

    public void bitmapDecode(Bitmap bitmap){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

    }
}
