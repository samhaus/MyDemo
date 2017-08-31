package com.example.administrator.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo.R;


/**
 * @author lsh
 * @version 1.0
 *          对于该应用界面的一些通用处理，包括Toast消息，简单跳转等等，都统一在此处理
 */
public class UIHelper {
    /**
     * 弹出Toast消息，短时间
     *
     * @param context
     * @param msg     int型的
     * @param str     当无法传入int型的，就用该str
     */
    public static void toastMessageShort(Context context, int msg, String str) {
        if (!StringUtil.isEmpty(str) && msg == 0)
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出Toast消息，长时间
     *
     * @param context
     * @param msg     int型的
     * @param str     当无法传入int型的，就用该str
     */
    public static void toastMessageLong(Context context, int msg, String str) {
        if (!StringUtil.isEmpty(str) && msg == 0)
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void activityFinish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.screenleft_enter, R.anim.screenright_exit);
    }

    /**
     * Activity页面之间的简单跳转,跳转后，前一个activity不被finish
     */
    public static void redirectToActivityNoFinishin(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.screenright_enter, R.anim.screenleft_exit);
        intent = null;
    }


    /**
     * Activity页面之间跳转并返回结果
     * 不适用于activity
     */
    public static void startActivityForResult(Activity activity, Intent intent, int RequestCode) {
        activity.startActivityForResult(intent, RequestCode);
        activity.overridePendingTransition(R.anim.screenright_enter, R.anim.screenleft_exit);
        intent = null;
    }

    /**
     * 从fragment切换到下一个Activity页面之间跳转并返回结果
     */
    public static void startAppCompatActivityForResult(Fragment fragment, AppCompatActivity activity, Intent intent, int RequestCode) {
        fragment.startActivityForResult(intent, RequestCode);
        activity.overridePendingTransition(R.anim.screenright_enter, R.anim.screenleft_exit);
        intent = null;
    }

    /**
     * Activity页面之间的简单跳转,跳转后，前一个activity被finish
     */
    public static void redirectToActivityAndFinishin(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.screenright_enter, R.anim.screenleft_exit);
        intent = null;
    }

    /**
     * 在activity的Oncreate状态时，需用该方法才可以获取到某个view的长宽
     * 或者在某些特殊情况下，也只有此方法可以最可靠的获取
     *
     * @return
     */
    public static int getViewWidth(final View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getWidth();
    }

    /**
     * @param view
     * @return 返回para后，用para.height即为高度，同理可找到宽度
     */
    public static ViewGroup.LayoutParams getViewSize(View view) {
        return view.getLayoutParams();
    }

    /**
     * 修改控件的长宽
     *
     * @param width
     * @param height
     * @param view
     * @return para 返回值传入控件的setLayoutParams方法中即可
     */
    public static ViewGroup.LayoutParams setViewSize(int width, int height, View view) {
        ViewGroup.LayoutParams para = view.getLayoutParams();
        if (height != 0) {
            para.height = height;
        }
        if (width != 0) {
            para.width = width;
        }
        return para;
    }

    /**
     * 隐藏activity的软键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().getDecorView();
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public static void showSoftInput(EditText view) {
        view.requestFocus();
        view.setFocusable(true);
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(view, 0);
        }
    }

    /**
     * Textview变色
     *
     * @param tv      Textview
     * @param keyWord 需要变色的关键词
     * @param string  整句话（包含关键词）
     */
    public static void textChangeColor(Context context, TextView tv, String keyWord, String string) {
        if (!StringUtil.isEmpty(keyWord)) {
            SpannableString str = new SpannableString(string);
            if (string.indexOf(keyWord) != -1) {
                str.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)),
                        string.indexOf(keyWord), string.indexOf(keyWord) + keyWord.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv.setText(str);
        } else {
            tv.setText(string);
        }
    }
}
