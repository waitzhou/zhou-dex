package com.zhexinit.yixiaotong.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CommonUtils {

    /**
     * 判读是不是正确的手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 图片 base64加密
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String imaBase64(Bitmap bitmap){
        if (bitmap==null)return "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);
        byte[] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 日期格式化
     */
    public static String getUpdatTime(long time){
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        Date dt = new Date(time);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    /**
     * 把分钟转化为小时
     * @return
     */
    public static String setMinuteToHour(int time){
        int hours = (int) Math.floor(time / 3600);
        int minute = time % 3600;
        if (time<3600){
            return time+"分钟";
        }else if (minute==0){
            return hours+"小时";
        }else {
            return hours+"小时"+minute+"分";
        }
    }

    /**
     * 把分钟转化为相应的日期 hh:mm
     * @param time
     * @return
     */
    public static String setMinuteToDate(int time){
        int hours = (int) Math.floor(time / 60);
        int minute = time % 60;

        String h=hours<10?"0"+hours:String.valueOf(hours);
        String m=minute<10?"0"+minute:String.valueOf(minute);
        return h+":"+m;
    }

    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 判断只能是数字、英文，汉字
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
}
