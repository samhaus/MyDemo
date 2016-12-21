package com.example.administrator.demo.util;


import com.example.administrator.demo.BuildConfig;
import com.orhanobut.logger.Logger;

public class LogUtil {
    public static final boolean isDebug = BuildConfig.DEBUG;

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void i(String msg) {
        if (isDebug)
            Logger.i(msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Logger.init(tag);
        Logger.i(msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Logger.d(msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Logger.init(tag);
        Logger.d(msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Logger.e(msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Logger.init(tag);
        Logger.e(msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Logger.v(msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Logger.init(tag);
        Logger.v(msg);
    }

    public static void json(String msg) {
        if (isDebug)
            Logger.json(msg);
    }

    public static void json(String tag,String msg) {
        if (isDebug)
            Logger.init(tag);
        Logger.json(msg);
    }
}
