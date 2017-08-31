package com.example.administrator.demo.base;

import android.app.Application;
import android.content.Context;

/**
 * author: lsh
 * Date: 2017-03-21
 * Time: 17:48
 * <p>
 * 上层提供给底层lib的context
 */

public class AppContext {
    public static Context mAppContext;


    public static void init(Context context) {
        if (mAppContext == null) {
            mAppContext = context.getApplicationContext();
        } else {
            throw new IllegalStateException("set context duplicate");
        }
    }

    public static Application getContext() {
        if (mAppContext == null) {
            throw new NullPointerException("mAppContext == null");
        } else {
            return (Application) mAppContext;
        }
    }
}
