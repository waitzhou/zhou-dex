package com.example.jingweiclassicdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;

/**
 * Author : ZSX
 * Date : 2020-01-13
 * Description :  图片压缩工具类
 */
public class BitmapUtils {

    private static String TAG = "BitmapUtils";

    private static final int DEFAULT_REQUEST_HEIGHT = 200;
    private static final int DEFAULT_REQUEST_WIDTH = 200;

    /**
     * @param context 上下文
     * @param res     图片ID
     *                压缩到指定宽、高
     */
    public static Bitmap compressSize(Context context, int res) {
        return compressSize(context, res, DEFAULT_REQUEST_WIDTH, DEFAULT_REQUEST_HEIGHT);
    }

    /**
     * @param context       上下文
     * @param res           图片ID
     * @param requestWidth  期望宽度
     * @param requestHeight 期望高度
     *                      图片大小压缩，压缩到指定宽、高
     *                      <p>
     *                      bitmap不使用时，调用bitmap.recycle()回收，释放内存
     */
    public static Bitmap compressSize(Context context, Object res, int requestWidth, int requestHeight) {
        if (context == null) {
            throw new NullPointerException("上下文对象为空");
        }
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (res instanceof Integer) {
            BitmapFactory.decodeResource(context.getResources(), (Integer) res, options);
        } else if (res instanceof String) {
            BitmapFactory.decodeFile((String) res, options);
        } else {
            throw new NoSuchElementException("参数不匹配");
        }
        int sampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        Log.d(TAG, "compressSize: sampleSize = " + sampleSize);
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        if (res instanceof Integer) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) res, options);
        } else {
            bitmap = BitmapFactory.decodeFile((String) res, options);
        }
        return bitmap;
    }

    /**
     * @param options       图片的配置
     * @param requestWidth  期望宽度
     * @param requestHeight 期望高度
     *                      计算需要压缩的倍数
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        int bitmapHeight = options.outHeight;
        int bitmapWidth = options.outWidth;
        Log.d(TAG, "getBitmapWidthAndHeight:222  imageWidth = " + bitmapWidth + "\n  imageHeight" + bitmapHeight + "   \n  imageType = " + options.outMimeType);
        int sampleSize = 1;
        while (requestWidth < bitmapWidth || requestHeight < bitmapHeight) {
            sampleSize *= 2;
            bitmapHeight = bitmapHeight / sampleSize;
            bitmapWidth = bitmapWidth / sampleSize;
        }
        Log.d(TAG, "calculateInSampleSize: size = " + sampleSize);
        return sampleSize;
    }

    /**
     * 图片质量压缩
     *
     * @param context
     * @param res 图片资源
     * @param maxSize 压缩到多大以内
     */
    public static Bitmap compressQuanlity(Context context, Object res, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        if (res instanceof String)
            bitmap = BitmapFactory.decodeFile((String) res, options);
        else if (res instanceof Integer)
            bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) res, options);
        else
            throw new NoSuchElementException("params no matched");
        int quanlity = 100;
        Log.d(TAG, "compressQuanlity:1111 =  "+bitmap.getByteCount());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quanlity,baos);
        while (baos.toByteArray().length/1024 > maxSize){
            Log.d(TAG, "compressQuanlity: baos = "+baos.toByteArray().length/1024);
            if(quanlity == 0){
                break;
            }else {
                quanlity -= 10;
            }
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,quanlity,baos);
        }
        Log.d(TAG, "compressQuanlity: 2222 = "+bitmap.getByteCount());
        byte[] bytes = baos.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        if(bitmap != null)
        return bitmap;
        else throw new NullPointerException("bitmap is empty");
    }

    /**
     * 缩放到指定比例
     *
     * @param context
     * @param res 资源
     * */
    public static Bitmap compressMatrix(Context context,Object res){
        return compressMatrix(context, res,0.3f,0.3f);
    }

    /**
     * 缩放到指定比例
     *
     * @param context
     * @param res 资源
     * @param scaleX 缩放宽度  0.3f
     * @param scaleY 缩放高度  0.3f
     * */
    public static Bitmap compressMatrix(Context context,Object res,float scaleX,float scaleY){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        if(res instanceof Integer){
            bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) res,options);
        }else if(res instanceof String){
            bitmap = BitmapFactory.decodeFile((String) res,options);
        }else {
            throw new NoSuchElementException("参数res必须是integer或者String类型的数据");
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX,scaleY);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    /**
     * 通过修改像素密度 压缩图片
     * */
    public static Bitmap compressConfig(Context context,Object res){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = null;
        if(res instanceof Integer){
            bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) res,options);
        }else if(res instanceof String){
            bitmap = BitmapFactory.decodeFile((String) res,options);
        }else {
            throw new NoSuchElementException("参数res必须是integer或者String类型的数据");
        }
        return bitmap;
    }

    /**
     * 获取图片宽、高  供外部使用
     */
    public static BitmapFactory.Options getBitmapWidthAndHeight(Context context, Object res) {
        if (context == null) {
            throw new NullPointerException("上下文对象为空");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        if (res instanceof Integer) {
            BitmapFactory.decodeResource(context.getResources(), (Integer) res, options);
        } else if (res instanceof String) {
            BitmapFactory.decodeFile((String) res, options);
        } else {
            throw new NoSuchElementException("参数不匹配");
        }
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        String imageType = options.outMimeType;
        Log.d(TAG, "getBitmapWidthAndHeight: imageWidth = " + imageWidth + "\n  imageHeight" + imageHeight + "   \n  imageType = " + imageType);
        return options;
    }

    /**
     * 保存图片
     */
    public static File saveBitmap(Context context, Bitmap bitmap, String fileName) {
        return saveBitmap(context, bitmap, 100, fileName);
    }

    /**
     * @param context  上下文
     * @param bitmap
     * @param quanlity 图片质量
     * @param fileName 保存文件名
     *                 保存图片
     */
    public static File saveBitmap(Context context, Bitmap bitmap, int quanlity, String fileName) {
        String saveDir = getSaveDir(context);
        String fileSuffix = getFileSuffix(fileName);
        Bitmap.CompressFormat format = getBitmapFormat(fileSuffix);
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(format, quanlity, out);
            bitmap.recycle();
            Log.d(TAG, "saveBitmap: 图片大小 ：" + file.length() / 1024 + "kb");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取图片后缀名
     */
    public static String getFileSuffix(String name) {
        String[] strings = name.split("\\.");
        for (String s : strings) {
            Log.d(TAG, "getFileSuffix: 文件名拆分：" + s);
        }
        if (strings.length <= 1) {
            throw new NullPointerException("filename is not match regular");
        }
        int suffix = strings.length - 1;
        return strings[suffix];
    }

    /**
     * 获取图片压缩模式
     */
    public static Bitmap.CompressFormat getBitmapFormat(String type) {
        switch (type) {
            case "jpeg":
            case "jpg":
                return Bitmap.CompressFormat.JPEG;
            case "png":
                return Bitmap.CompressFormat.PNG;
            case "webp":
                return Bitmap.CompressFormat.WEBP;
            default:
                throw new NoSuchElementException("bitmap format mistake");
        }
    }

    /**
     * 将图片保存到哪个文件夹，获取保存图片的文件夹
     */
    private static String getSaveDir(Context context) {
        File filesDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();
        File imageFile = null;
        if (filesDir.exists()) {
            String dirName = filesDir.getAbsolutePath() + File.separator + "bitmap";
            imageFile = new File(dirName);
            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }
        } else {
            String dirName = cacheDir.getAbsolutePath() + File.separator + "bitmap";
            imageFile = new File(dirName);
            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }
        }
        return imageFile.getAbsolutePath();
    }
}
