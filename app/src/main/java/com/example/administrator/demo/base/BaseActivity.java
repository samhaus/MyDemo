package com.example.administrator.demo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.administrator.demo.util.AppManager;
import com.example.administrator.demo.util.DialogUtil;
import com.example.administrator.demo.util.StatusBarUtil;
import com.example.administrator.demo.util.ToastUtil;
import com.example.administrator.demo.util.sysbug_fix.InputMethodManagerFix;
import com.example.administrator.demo.util.sysbug_fix.LeakFreeSupportSharedElementCallback;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by samhaus on 2017/8/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @LayoutRes
    public abstract int generateLayout();


    protected boolean isTopImg = false;//顶部是否是图片
    protected boolean isNeedStatusBar = true;
    protected boolean isNeedOrientation = true;//是否需要强制竖屏

    protected ProgressDialog waitDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        //状态栏透明
        if (isNeedStatusBar) {
            StatusBarUtil.setTransparentForWindow(this);
        }

        //导航栏  底部  防止被虚拟按键遮住
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(generateLayout());

        initData(getIntent().getExtras());
        initView();
        //内存泄漏相关检测
        InputMethodManagerFix.fixFocusedViewLeak(getApplication());
        removeActivityFromTransitionManager(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new LeakFreeSupportSharedElementCallback());
            setExitSharedElementCallback(new LeakFreeSupportSharedElementCallback());
        }

        if (isNeedOrientation) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

//        if (!isTopImg) {
//            StatusBarUtil.setStatusBar(this);
//        }
    }

    public abstract void initData(Bundle bundle);

    public abstract void initView();

    @MainThread
    public ProgressDialog showWaitDialog(@NonNull String message) {
        if (waitDialog == null) {
            waitDialog = DialogUtil.getWaitDialog(this, message);
        }
        if (waitDialog != null && !waitDialog.isShowing()) {
            waitDialog.setMessage(message);
            if (!isFinishing()) {
                waitDialog.show();
            }
        }
        return waitDialog;
    }

    @MainThread
    public void hideWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            if (!isFinishing()) {
                waitDialog.dismiss();
                waitDialog = null;
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        ToastUtil.cancelToast();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.cancelToast();
    }

    private static void removeActivityFromTransitionManager(Activity activity) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        Class transitionManagerClass = TransitionManager.class;
        try {
            Field runningTransitionsField = transitionManagerClass.getDeclaredField("sRunningTransitions");
            runningTransitionsField.setAccessible(true);
            //noinspection unchecked
            ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> runningTransitions
                    = (ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>>)
                    runningTransitionsField.get(transitionManagerClass);
            if (runningTransitions.get() == null || runningTransitions.get().get() == null) {
                return;
            }
            ArrayMap map = runningTransitions.get().get();
            View decorView = activity.getWindow().getDecorView();
            if (map.containsKey(decorView)) {
                map.remove(decorView);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
