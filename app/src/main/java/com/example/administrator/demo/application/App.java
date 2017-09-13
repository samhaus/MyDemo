package com.example.administrator.demo.application;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import com.example.administrator.demo.BuildConfig;
import com.example.administrator.demo.base.AppContext;
import com.example.administrator.demo.base.BaseActivity;
import com.example.administrator.demo.recycleView.HomeActivity;
import com.example.administrator.demo.service.InitializeService;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.glide_config.GlideCacheUtil;
import com.tencent.bugly.beta.Beta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by lsh on 2016/5/11.
 */
public class App extends Application {
    private static App instance;
    public static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName();
        if (processName != null && !TextUtils.isEmpty(processName)) {
            if (processName.equals(this.getPackageName())) {
                instance = this;
                InitializeService.start(this);
            }
        }
        instance = this;
        AppContext.init(this);
        isDebug = BuildConfig.DEBUG;
        Beta.canShowUpgradeActs.add(HomeActivity.class);//设置只显示在某个页面显示bugly更新弹窗
//        CloudApiSdk.isDebug = !BuildConfig.DEBUG;
    }

    public static App getInstance() {
        return instance;
    }

    private static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/"
                    + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(
                    file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            GlideCacheUtil.getInstance().clearImageAllCache(this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            //设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }
}
