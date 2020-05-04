package com.ph.lease.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String转String函数名为convert
 * String转Date函数名为parse
 * Date转String函数名为format
 * 直接获取日期的函数名前缀为get
 *
 * @author PengHao
 */
public class DateUtil {

    public static final SimpleDateFormat DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat PATTERN_ONE = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 格式："yyyyMMddHHmmss"
     *
     * @return
     */
    public static String getCurrentDate() {
        return PATTERN_ONE.format(new Date());
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return "yyyy-MM-dd HH:mm:ss"格式的字符串
     */
    public static String format(Date date) {
        return DEFAULT.format(date);
    }

    /**
     * @param source 日期字符串
     * @return Date实例，如果解析失败，返回null
     */
    public static Date parse(String source) {
        Date result = null;
        try {
            result = DEFAULT.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 如果有参数format，那么按照format来解析，否则按照DEFAULT模式解析
     *
     * @param source 日期字符串
     * @return Date实例，如果解析失败，返回null
     */
    public static Date parse(String source, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = null;
        if (format != null) {
            try {
                result = format.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            result = parse(source);
        }
        return result;
    }

}
