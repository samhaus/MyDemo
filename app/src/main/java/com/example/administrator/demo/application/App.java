package com.example.administrator.demo.application;

import android.app.Application;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by wangz on 2016/5/23.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName();
        if (processName != null && !TextUtils.isEmpty(processName)) {
            if (processName.equals(this.getPackageName())) {
                instance = this;
//                InitializeService.start(this);
            }
        }
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
}
