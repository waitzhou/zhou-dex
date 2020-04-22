package com.zhexinit.yixiaotong.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.graphics.BitmapFactory.decodeStream;

/**
 * 图片工具类，包括图片解析、计算等
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-5-4
 * Time: AM9:15
 */
public class BitmapUtil {

    private final static long DELEDT_TIME_LIMIT = 691200000;

    /**
     * 从文件中解析图片，并将适配屏幕大小
     *
     * @param context
     * @param path    图片文件的路径
     * @return
     */
    public static Bitmap decodeFileFitScreen(Context context, String path) {

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;


        return decodeFileFitSize(context, path, screenWidth, screenHeight);
    }

    /**
     * 从文件中解析图片，并且解析出来的
     * 图片要和view的宽高一致
     *
     * @param context
     * @param path
     * @param view
     * @return
     */
    public static Bitmap decodeFileFitView(Context context, String path, View view) {
        int[] viewSize = getImageViewSize(context, view);
        int width = viewSize[0];
        int height = viewSize[1];

        return decodeFileFitSize(context, path, width, height);
    }

    /**
     * 获取网络图片流
     *
     * @param path
     * @return
     */
    public static InputStream getImageFromNetWeibo(String path) {
        InputStream data = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                data = conn.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //超时
        }
        return data;
    }


    /**
     * 下载图片
     *
     * @param path 图片的url
     * @return
     */
    public static byte[] getImageFromNet(String path) {
        byte[] data = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream in = conn.getInputStream();
                data = readInputStream(in);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 从输入流中读取所有内容
     *
     * @param inStream
     * @return
     */
    private static byte[] readInputStream(InputStream inStream) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                byteOutputStream.write(buffer, 0, len);
            }
            inStream.close();
            byteOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteOutputStream.toByteArray();
    }

    /**
     * 获取View的宽和高
     *
     * @param context
     * @param view
     * @return
     */
    public static int[] getImageViewSize(Context context, View view) {
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

        final ViewGroup.LayoutParams params = view.getLayoutParams();

        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : view.getWidth(); // Get actual image width
        if (width <= 0) width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(view, "mMaxWidth"); // Check maxWidth parameter
        //if (width <= 0) width = maxImageWidth;
        if (width <= 0) width = displayMetrics.widthPixels;

        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : view.getHeight(); // Get actual image height
        if (height <= 0) height = params.height; // Get layout height parameter
        if (height <= 0)
            height = getImageViewFieldValue(view, "mMaxHeight"); // Check maxHeight parameter
        //if (height <= 0) height = maxImageHeight;
        if (height <= 0) height = displayMetrics.heightPixels;

        return new int[]{width, height};
    }

    /**
     * 将图片解析成指定的 宽高
     *
     * @param context
     * @param path    图片存储路径
     * @param width   指定的宽
     * @param height  指定的高
     * @return
     */
    public static Bitmap decodeFileFitSize(Context context, String path, int width, int height) {


        return decodeSampledBitmapFromPath(context, path, width, height);
    }

    /**
     * 从输入流中解析出适配指定宽高的图片
     *
     * @param context
     * @param inputStream
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeStreamFitSize(Context context, InputStream inputStream, int width, int height) {
        return decodeSampledBitmapFromStream(context, inputStream, width, height);
    }

    /**
     * 从输入流中解析出 适配屏幕宽高的图片
     *
     * @param context
     * @param inputStream
     * @return
     */
    public static Bitmap decodeStreamFitScreen(Context context, InputStream inputStream) {

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;


        return decodeStreamFitSize(context, inputStream, screenWidth, screenHeight);
    }


    /**
     * 从输入流中解析出指定宽高的图片
     *
     * @param ctx
     * @param stream
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static Bitmap decodeSampledBitmapFromStream(Context ctx, InputStream stream,
                                                        int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeStream(stream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        calculateBitmapConfig(options, ctx, reqWidth, reqHeight);
        return decodeStream(stream, null, options);
    }

    /**
     * 从文件中解析出指定宽高的图片
     *
     * @param ctx
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(Context ctx, String path,
                                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        calculateBitmapConfig(options, ctx, reqWidth, reqHeight);

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 计算图片的配置，针对图片进行缩放
     *
     * @param opt
     * @param ctx
     * @param reqW
     * @param reqH
     */
    private static void calculateBitmapConfig(BitmapFactory.Options opt, Context ctx, int reqW, int reqH) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        int pid[] = new int[]{android.os.Process.myPid()};
        Debug.MemoryInfo memoryInfo[] = am.getProcessMemoryInfo(pid);
        long totalMemory = memoryInfo[0].getTotalPss();
        long used = memoryInfo[0].dalvikPss;

        if ((totalMemory - used) * 1024 < reqW * reqH * 4 + 1024 * 1024) {
            opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
        }

    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 通过配置sampleSize 来控制图片的大小
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        //zyp,OOM in decoding large image
        //TODO: this is not a good solution
        //
        while (inSampleSize > 0 && (options.outHeight / inSampleSize > 960 || options.outHeight * options.outWidth / inSampleSize / inSampleSize >= 960 * 480)) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /**
     * 通过反射机制，获取view里某属性的值
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 用滤镜将秃瓢调成灰度图像，常用语未登录状体的头像
     *
     * @param imageDrawable
     * @return
     */
    public static Drawable setImageGray(Drawable imageDrawable) {
        imageDrawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        imageDrawable.setColorFilter(cf);
        return imageDrawable;
    }

    /**
     * 水印图片
     *
     * @param photo     主视图
     * @param watermark 水印图
     * @param mark_x    水印图的横坐标
     * @param mark_y    水印图的纵坐标
     * @return
     */
    private Bitmap createBitmap(Bitmap photo, Bitmap watermark, int mark_x, int mark_y) {

        //左上角 mark_x = 0；mark_y=0;
        //右上角 mark_x = photo.getWidth() - watermark.getWidth()；mark_y=0;
        //左下角 mark_x = 0；mark_y=photo.getHeight() - watermark.getHeight();
        /*左上角 mark_x = photo.getWidth() - watermark.getWidth()；
         mark_y = photo.getHeight() - watermark.getHeight();*/
        if (photo == null) {
            return null;

        }
        int photoWidth = photo.getWidth();
        int photoHeight = photo.getHeight();
        int markWidth = watermark.getWidth();
        int markHeight = watermark.getHeight();

        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(photoWidth, photoHeight, Bitmap.Config.ARGB_8888);
        // 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);

        // draw src into
        cv.drawBitmap(photo, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, mark_x, mark_y, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储

        return newb;
    }

    /**
     * 压缩图片到指定大小，大于1为放大图片，小于1为缩小图片
     *
     * @param image
     * @return
     */
    public static Bitmap comp(Bitmap image, float rate) {

        if (rate <= 0) {
            return image;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = image.getHeight() * rate;//这里设置高度为800f
        float ww = image.getWidth() * rate;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        image.recycle();
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = decodeStream(isBm, null, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap screenShotBitmap;


    public interface BlurViewListener {
        void onBlured(Bitmap bitmap);
    }

    /**
     * 获取blur后的图片的引用
     *
     * @return
     */
    public static Bitmap getViewBlurBitmap() {
        return screenShotBitmap;
    }

    /**
     * 释放blur后的图片
     */
    public static void releaseViewBlurBitmap() {
        if (screenShotBitmap != null && screenShotBitmap.isRecycled() == false) {
            screenShotBitmap.recycle();
        }
        screenShotBitmap = null;
    }

    /**
     * 截屏，并将图片保存到文件中去，返回文件路径
     *
     * @return
     */
    public static Bitmap screenShotBitmap(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        final int statusBarHeight = frame.top;

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        final Bitmap bitmap = view.getDrawingCache();
        return bitmap;

    }


    /**
     * 保存图片
     *
     * @param b
     * @param strFileName
     */
    private static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        File file = new File(strFileName);
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 70, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Base64字符串转成图片
     */
    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将bitmap转成base64
     */
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

/*    public static void saveFile(String filename, byte [] data)throws Exception {
        if(data != null){
            String filepath = Config.PATH_IMAGE_TEMP_PATH+filename;
            File file  = new File(filepath);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }*/

    //旋转图片显示
    public static int changeImageBitmap(String imagePath) throws Exception {
        ExifInterface exif = new ExifInterface(imagePath);
        int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        int turn = 0;
        if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
            turn = 3;
        } else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
            turn = 2;
        } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
            turn = 1;
        }
        return turn;
    }

    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * 质量压缩方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int targetSize) {
        if (targetSize < 0 || targetSize > 10000) {
            throw new IllegalAccessError("params i is not legal");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > targetSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.WEBP, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        byte[] bitmapArray = baos.toByteArray();
        Log.e("testsssss", "compressImage: "+bitmapArray.toString());
        try {
            baos.flush();
            baos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //ByteArrayInputStream isBm = new ByteArrayInputStream(bitmapArray);//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmapEnd = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        return bitmapEnd;//把ByteArrayInputStream数据生成图片
    }

    /**
     * 返回bitmap大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap revitionImageSize(Context context, String path, int size) {
        try {
            // 取得图片
            InputStream temp = context.getAssets().open(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
            options.inJustDecodeBounds = true;
            // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
            decodeStream(temp, null, options);
            // 关闭流
            temp.close();

            // 生成压缩的图片
            int i = 0;
            Bitmap bitmap = null;
            while (true) {
                // 这一步是根据要设置的大小，使宽和高都能满足
                if ((options.outWidth >> i <= size)
                        && (options.outHeight >> i <= size)) {
                    // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                    temp = context.getAssets().open(path);
                    // 这个参数表示 新生成的图片为原始图片的几分之一。
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                    options.inJustDecodeBounds = false;

                    bitmap = decodeStream(temp, null, options);
                    break;
                }
                i += 1;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 图片压缩方法一
     * <p>
     * 计算 bitmap大小，如果超过64kb，则进行压缩
     *
     * @param bitmap
     * @return
     */
    public static Bitmap imageCompressL(Bitmap bitmap, int size) {
        double targetwidth = Math.sqrt(size * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 按比例大小进行压缩
     * @Param srcPath:路径
     * @Param targetWidth:压缩宽度阈值
     * @Param targetHeight：压缩到高度阈值
     * @Param targetSize: 压缩大小阈值(单位kb)
     * */
    public static Bitmap compressForScale(String srcPath, float targetWidth, float targetHeight, int targetSize){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,options);
        options.inJustDecodeBounds = false;
        int width = options.outWidth;
        int height = options.outHeight;
        int scale = 1;
        if(width > targetWidth){
            scale = (int) (options.outWidth/targetWidth);
        }else if(height >targetHeight){
            scale = (int)(options.outHeight/targetHeight);
        }
        if(scale < 0){
            scale = 1;
        }
        options.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(srcPath,options);
        return bitmap;
        //return compressImage(bitmap,targetSize);
    }

    /**
     * 图片保存到相册
     * */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ghQRCode";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = StringUtils.longToDate(System.currentTimeMillis(),"yyyyMMddHHmmss") + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 图片保存到相册
     * */
    public static boolean saveImageToGallery(Context context, Bitmap bmp,boolean isRecycle) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ghQRCode";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName =  StringUtils.longToDate(System.currentTimeMillis(),"yyyyMMddHHmmss") + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}