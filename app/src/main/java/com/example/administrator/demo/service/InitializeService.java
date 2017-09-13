package com.example.administrator.demo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.example.administrator.demo.R;
import com.example.administrator.demo.application.App;
import com.example.administrator.demo.base.AppContext;
import com.example.administrator.demo.util.AppConstants;
import com.example.administrator.demo.util.LogUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


public class InitializeService extends IntentService {

    public static final String ACTION_INIT_LIBRARY = "service.action.initThirdLibrary";

    public InitializeService() {
        super("InitializeService");
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setPackage(context.getPackageName());
        intent.setAction(ACTION_INIT_LIBRARY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_LIBRARY.equals(action)) {
                handleAction();
            }
        }
    }

    private void handleAction() {
//        if (LeakCanary.isInAnalyzerProcess(AppContext.getContext())) {
//            return;
//        }
        //api、网关
//        AppConfigurationInitializer.init();
        //初始化自、定义字体
//        Calligraphy、ild());
        //chrome调试工具
//        Stetho.initializeWithDefaults(AppContext.getContext());
        //bugly 初始化
        appUpdateConfig();
        Bugly.init(getApplicationContext(), AppConstants.BUGLY_APPID, false);
        if (App.isDebug) {
            initStrictMode();
//            LeakCanary.install(AppContext.getContext());
//            BlockCanary.install(this, new AppBlockCanaryContext(getApplicationContext())).start();
        }
//        else {
//            AppCrashHandler.getInstance().init();
//        }
    }

    /**
     * app更新属性配置
     */
    private void appUpdateConfig() {
        /**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true;
        Beta.autoDownloadOnWifi = true;//设置Wifi下自动下载
        Beta.canShowApkInfo = true;//设置是否显示弹窗中的apk信息
        Beta.showInterruptedStrategy = true;//设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
    }

    private void initStrictMode() {
        StrictMode.noteSlowCall("slowCallInThread");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()//自定义的耗时调用
                .detectDiskReads()//磁盘读取操作
                .detectDiskWrites()//磁盘写入操作
                .detectNetwork()//网络操作
                .detectAll()
                .penaltyDeath()
                .penaltyLog()
                .penaltyDialog()
                .penaltyFlashScreen()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
