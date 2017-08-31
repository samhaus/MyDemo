package com.example.administrator.demo.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.example.administrator.demo.base.AppContext;


/**
 * Created by starry on 2016/6/23.
 */
public class ToastUtil {
    private static Toast toast;

    private ToastUtil() {
        throw new UnsupportedOperationException("ToastUtil can't be init");
    }

    @UiThread
    public static void show(@NonNull String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), msg, 2000);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    @UiThread
    public static void show(@NonNull String msg,int time) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), msg, time);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    @UiThread
    public static void showNetDisable() {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "网络不可用，请检查网络", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText("网络不可用，请检查网络");
        }
        toast.show();
    }

    @UiThread
    public static void showEmptyHint(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), str + "不能为空", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str + "不能为空");
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
