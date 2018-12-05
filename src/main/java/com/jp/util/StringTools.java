package com.jp.util;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by zw on 2017/2/17.
 */
public class StringTools {

    public static boolean notEmpty(Object obj) {
        if (obj != null && !obj.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean trimNotEmpty(Object obj) {
        if (obj != null && !obj.toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    public static Long stringToLong(String obj) {
        if (obj != null && !obj.equals("")){
            return Long.valueOf(obj);
        }
        return null;
    }

    public static Integer stringToInteger(String obj) {
        if (obj != null && !obj.equals("")){
            return Integer.valueOf(obj);

        }
        return null;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String normalizeString(String strValue) {
        return ((strValue == null) ? "" : strValue.trim());
    }

    /**
     * 将一个字符串转为合法的sql语句要求的表示方法。主要是对单引号的处理
     *
     *
     * @param str
     * @return 处理后的字符串
     */
    public static String formatSQLString(String str) {
        if (str == null)
            return null;
        StringBuffer buf = new StringBuffer(str);

        int i = 0;
        for (int n = buf.length(); i < n; i++)
            if (buf.charAt(i) == '\'') {
                buf.replace(i, i + 1, "''");
                n++;
                i++;
            }
        return buf.toString();
    }

    /**
     * 将当前日期转换为指定格式的字符串
     *
     * @param pattern
     *            格式
     * @return String
     */
    public static String getDateStringByPattern(String pattern) {
        Date date = new Date();
        return getDateStringByPattern(date, pattern);
    }

    /**
     * 将指定日期转换为指定格式的字符串
     *
     * @param date
     *            ( java.util.Date )
     * @param pattern
     *            日期格式，如："yyyy-MM-dd" ，"yyyy-MM-dd HH:mm:ss"，"HH:mm:ss"
     * @return String
     */
    public static String getDateStringByPattern(Date date, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        String str = sf.format(date);
        return str;
    }

    /**
     * 将指定日期转换为指定格式的字符串
     *
     * @param ts
     *            ( java.util.Timestamp )
     * @param pattern
     *            日期格式，如："yyyy-MM-dd" ，"yyyy-MM-dd HH:mm:ss"，"HH:mm:ss"
     * @return String
     */
    public static String getDateStringByPattern(Timestamp ts, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        String str = sf.format(ts);
        return str;
    }
}
