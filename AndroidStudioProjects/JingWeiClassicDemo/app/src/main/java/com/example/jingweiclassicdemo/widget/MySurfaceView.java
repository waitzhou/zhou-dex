package com.example.jingweiclassicdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.jingweiclassicdemo.R;

/**
 * Author : ZSX
 * Date : 2019-12-31
 * Description :
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Thread mThread;
    private Canvas mCanvas;
    private volatile boolean flag;
    private float mCircle = 10;

    public MySurfaceView(Context context) {
        //super(context);
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context, null, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        flag = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        mSurfaceHolder.removeCallback(this);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Thread.sleep(3000);
                Draw();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void Draw() {
        mCanvas = mSurfaceHolder.lockCanvas();
        if (mCanvas != null) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL);
            if (mCircle >= getWidth() / 2) {
                mCircle = 0;
            } else {
                mCircle++;
            }
            Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.home_selected)).getBitmap();
            mCanvas.drawBitmap(bitmap, 0, 0, paint);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 8; j++) {
                    mCanvas.drawCircle((getWidth()/5) * i + (getWidth() / 10), (getHeight() / 8) *j+(getHeight()/16),mCircle,paint);
                }
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
