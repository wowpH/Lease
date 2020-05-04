package com.ph.lease.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志类
 *
 * @author PengHao
 * @version 0.2
 */
public class Log {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final int MAX_MSG1 = 60;

    public static void log(String msg) {
        logTime();
        logSpace(5);
        System.out.println(msg);
    }

    public static void log(String msg1, String msg2) {
        logTime();
        logSpace(5);
        System.out.print(msg1);
        logSpace(MAX_MSG1 - msgLength(msg1));
        System.out.println(msg2);
    }

    public static void log(String msg1, Integer msg2) {
        log(msg1, msg2.toString());
    }

    public static void log(int msg1, String msg2) {
        log(String.valueOf(msg1), msg2);
    }

    public static void logTime() {
        System.out.print(simpleDateFormat.format(new Date()));
    }

    public static void logSpace(int n) {
        for (int i = 0; i < n; ++i) {
            System.out.print(' ');
        }
    }

    public static void logLn() {
        System.out.println();
    }

    private static int msgLength(String msg) {
        byte[] msgBytes;
        String nMsg = "";
        try {
            msgBytes = msg.getBytes("GB2312");
            nMsg = new String(msgBytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nMsg.length();
    }

}
