package com.example.administrator.demo.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.example.administrator.demo.application.App;

/**
 * Created by starry on 2016/6/23.
 */
public class ToastUtil {
    private static Toast toast;

    private ToastUtil() {
        throw new UnsupportedOperationException("ToastUtil can't be init");
    }

    public static void show(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showNetDisable() {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), "网络不可用，请检查网络", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText("网络不可用，请检查网络");
        }
        toast.show();
    }

    public static void showEmptyHint(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), str + "不能为空", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str + "不能为空");
        }
        toast.show();
    }
}
