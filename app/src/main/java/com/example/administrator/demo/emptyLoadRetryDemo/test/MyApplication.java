package com.example.administrator.demo.emptyLoadRetryDemo.test;

import android.app.Application;

import com.example.administrator.demo.emptyLoadRetryDemo.LoadingAndRetryManager;
import com.example.administrator.demo.R;


/**
 * Created by zhy on 15/8/27.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }
}
