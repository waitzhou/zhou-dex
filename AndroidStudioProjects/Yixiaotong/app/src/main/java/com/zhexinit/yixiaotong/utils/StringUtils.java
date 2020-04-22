package com.zhexinit.yixiaotong.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static boolean IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2", "X"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================  
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return false;
        }
        // =======================(end)========================  

        // ================ 数字 除最后以为都为数字 ================  
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // =======================(end)========================  

        // ================ 出生年月是否有效 ================  
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // =====================(end)=====================  

        // ================ 地区码时候有效 ================  
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ==============================================  

        // ================ 判断最后一位的值 ================  
        /*int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 12;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }*/
        // =====================(end)=====================  
        return true;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    private static boolean isDataFormat(String str) {
        boolean flag = false;
        // String  
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";  
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-" +
                "9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-" +
                "2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|" +
                "([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|" +
                "(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 判断字符串是否是中文或者包含中文
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否输入的是汉字、字母或数字
     * */
    public static boolean isImputNameFormat(String s){
        String regEx = "[0-9]|[a-z]|[A-Z]|[u4E00-u9FA5]";
        return !isEmpty(s) && s.matches(regEx);
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.
                compile("^(0|86|17951)?(13[0-9]|14[0-9]|15[012356789]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 密码为6~18位数字和字母组合
     */
    public static boolean checkPassword(String vlaues) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        return vlaues.matches(regex);
    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');

    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 描述：隐藏一部分手机号.
     *
     * @param phoneNumber 指定的字符串
     * @return 手机号
     */
    public static String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }

    public static String FilterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        str = UnicodeToGBK(str);
        str = str.trim();

        return str;
    }

    /**
     * @param s
     * @return
     */
    private static String UnicodeToGBK(String s) {
        String[] k = s.split(";");
        String rs = "";
        for (int i = 0; i < k.length; i++) {
            int strIndex = k[i].indexOf("&#");
            String newstr = k[i];
            if (strIndex > -1) {
                String kstr = "";
                if (strIndex > 0) {
                    kstr = newstr.substring(0, strIndex);
                    rs = rs + kstr;
                    newstr = newstr.substring(strIndex);
                }

                int m = Integer.parseInt(newstr.replace("&#", ""));
                char c = (char) m;
                rs = rs + c;
            } else {
                rs = rs + k[i];
            }
        }

        return rs;
    }

    /**
     * 返回不重复的随机数
     * max :最大值 0-max
     */
    public static List<Integer> getRandomList(int max) {
        List<Integer> passwordArray = new ArrayList<>();
        for (int i = 0; i < max + 1; i++) {
            passwordArray.add(i);
        }
        int id[] = new int[10];
        for (int i = 0; i < 10; i++) {
            id[i] = i;
        }
        int last = 9;
        Random r = new Random();
        int temp;
        for (int i = 0; i < 9; i++) {
            temp = Math.abs(r.nextInt() % last);
            passwordArray.set(i, id[temp]);
            id[temp] = id[last];
            id[last] = passwordArray.get(i);
            last--;
        }
        passwordArray.set(9, id[0]);
        return passwordArray;
    }

    public static byte[] hexStringToByteArray(String text) {
        if (text == null)
            return null;
        byte[] result = new byte[text.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            int x = Integer.parseInt(text.substring(i * 2, i * 2 + 2), 16);
            result[i] = x <= 127 ? (byte) x : (byte) (x - 256);
        }
        return result;
    }

    public static final String HexCode[] = {"0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }

    /**
     * 方法的概述  转换位数补0
     *
     * @return String
     * @throws
     * @throws NullPointerException
     */
    public static String padLeft(String str, int len) {
        String pad = "0000000000000000";
        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
    }

    public static String byteArrayToHexString(byte b[]) {
        if (b == null)
            return null;
        String result = "";
        for (int i = 0; i < b.length; i++)
            result = result + byteToHexString(b[i]);
        return result;
    }

    /**
     * 方法的概述 ASCII转16进制
     *
     * @param s
     * @return String
     * @throws
     * @throws NullPointerException
     */
    public static String ASCIItoStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "ASCII");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * @Description: TODO(金额的转换) @param @param str @param @param
     * postion @param @return 设定文件 @return BigDecimal 返回类型 @throws
     */
    public static String changeMoney(String str, int postion) {
        BigDecimal bd = new BigDecimal(str);
        bd = bd.divide(new BigDecimal("100.00"), postion, BigDecimal.ROUND_HALF_UP);
        return bd + "";
    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param dateFormatStr 需要格式的目标字符串  举例 yyyyMMdd
     * @return String 返回转换后的时间字符串
     * @throws ParseException 转换异常
     */
    public static long stringToTime(String dateStr, String dateFormatStr) {
        DateFormat sdf = new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String longToDate(Long millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(millisecond);
        return simpleDateFormat.format(date);
    }

    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String stringToDate(String millisecond, String format) {
        try {
            long date = Long.parseLong(millisecond);
            return longToDate(date, format);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间转化为 小时 天 等表达方式
     * @param noticeTime 时间
     * */
    public static String getTimeDescription(long noticeTime)  {
        long currentTime=System.currentTimeMillis();
        long timeInterval = currentTime - noticeTime;
        if(timeInterval < 0){
            return "刚刚";//时间错误时，默认显示刚刚
        }else {
            //几小时前
            if(timeInterval < 1000 * 60 * 5){
                return "刚刚";
            }else if(timeInterval < 1000 * 60 * 60){
                long count = timeInterval/(1000*60);
                return String.valueOf(count).concat("分钟前");
            }else if(timeInterval < 1000 * 60 * 60 * 24) {
                long count = timeInterval / (1000 * 60 * 60);
                return String.valueOf(count).concat("小时前");
            }else if(timeInterval < 1000 * 60 * 60 * 24 * 7) {
                long count = timeInterval / (1000 * 60 * 60 * 24);
                return String.valueOf(count).concat("天前");
            }else {
                return longToDate(noticeTime,"yyyy年MM月dd日");
            }
        }
    }

    /**
     * 获取网络上的北京时间
     * @throws Exception
     */
    private static long netTime;
    private static long getBJTime(){
           new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url;//取得资源对象
                    try {
                        url = new URL("http://www.baidu.com");
                        final URLConnection uc=url.openConnection();//生成连接对象
                        uc.connect(); //发出连接
                        netTime=uc.getDate();
                        getTimeDescription(netTime);
                        //LogUtils.e("北京时间是："+netTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        return netTime;
    }
    /**
     * 获取引导时间欢饮语
     * */
    public static String getWelcomeData(){
        Date date = new Date();
        int hours = date.getHours();
        if(hours >= 5 && hours < 8){
            return "早上好";
        }else if(hours >= 8 && hours < 12){
            return "上午好";
        }else if(hours >= 12 && hours < 18){
            return "下午好";
        }else {
            return "晚上好";
        }
    }

    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String stringToDate(String millisecond) {
        return stringToDate(millisecond, "yyyy-MM-dd");
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 设置
     */
    public static String setIDCardEncrypt(String s) {
        return setEncryptionMsg(s, 4, s.length() - 4);
    }

    /**
     * 设置电话号码加密信息
     */
    public static String setPhoneEncrypt(String s) {
        return setEncryptionMsg(s, 3, 7);
    }

    /**
     * 设置姓名脱敏
     */
    public static String setNameEncrypt(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        return "*" + s.subSequence(1, s.length());
    }

    /**
     * 将信息中间位隐藏   135*****1234
     */
    public static String setEncryptionMsg(String str, int start, int end) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (start >= end) {
            return "";
        }
        if (end > str.length()) {
            throw new IndexOutOfBoundsException("end is out of str length");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.subSequence(0, start));
        for (int i = 0; i < end - start; i++) {
            sb.append("*");
        }
        sb.append(str.subSequence(end, str.length()));
        return sb.toString();
    }

    /**
     * 钱  精确到。。。
     */
    public static String formatMoney(long price) {
        double db = ((double) price) / 100;
        BigDecimal b = new BigDecimal(db);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    public static String formatMoney(String price) {
        try {
            long temp = Long.parseLong(price);
            return formatMoney(temp);
        } catch (ClassCastException e) {
            throw new ClassCastException("ClassCastException:long can't class cast to string");
        }
    }


    /** * 检测是否有emoji表情 * @param source * @return */
    public static boolean containsEmoji(String source) {                          //两种方法限制emoji
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }


    /** * 判断是否是Emoji * @param codePoint 比较的单个字符 * @return */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * bitmap转换成byte[]
     * */
    public static byte[] bitmapToBytes(Bitmap bitmap){
        if(bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }else {
            return new byte[]{};
        }
    }


    public static String getImagePath(Context context){
        String path;
        //SD卡不存在就缓存到手机内存
        try {
            //  /storage/emulated/0/Android/data/com.cb.weibo/cache/system
            path = context.getExternalCacheDir().getPath() + "/system";
        } catch (Exception e) {
            path = context.getCacheDir().getPath() + "/system";
        }
        path = path.concat("/imageTemp/");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    /**
     * 根据网络图片地址返回一个bitmap
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int getYear(long time){
        return Integer.parseInt(longToDate(time,"yyyy"));
    }

    public static int getMonth(long time){
        return Integer.parseInt(longToDate(time,"MM"));
    }

    public static int getWeekPosition(long time){
        String week = longToDate(time,"EEEE");
        switch (week){
            case "星期一":
                return 1;
            case "星期二":
                return 2;
            case "星期三":
                return 3;
            case "星期四":
                return 4;
            case "星期五":
                return 5;
            case "星期六":
                return 6;
            case "星期日":
                return 7;
                default:return 0;
        }
    }

    public static String  getConstellation(int month,int day){
        int point = -1;
        Double date = Double.parseDouble(month + "." + day);
        if (3.21 <= date && 4.19 >= date) {
            point = 0;
        } else if (4.20 <= date && 5.20 >= date) {
            point = 1;
        } else if (5.21 <= date && 6.21 >= date) {
            point = 2;
        } else if (6.22 <= date && 7.22 >= date) {
            point = 3;
        } else if (7.23 <= date && 8.22 >= date) {
            point = 4;
        } else if (8.23 <= date && 9.22 >= date) {
            point = 5;
        } else if (9.23 <= date && 10.23 >= date) {
            point = 6;
        } else if (10.24 <= date && 11.22 >= date) {
            point = 7;
        } else if (11.23 <= date && 12.21 >= date) {
            point = 8;
        } else if (12.22 <= date && 12.31 >= date) {
            point = 9;
        } else if (1.01 <= date && 1.19 >= date) {
            point = 9;
        } else if (1.20 <= date && 2.18 >= date) {
            point = 10;
        } else if (2.19 <= date && 3.20 >= date) {
            point = 11;
        }
        return contellationArr[point];
    }
    private static final String[] contellationArr = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","魔羯座","水瓶座","双鱼座"};
}
