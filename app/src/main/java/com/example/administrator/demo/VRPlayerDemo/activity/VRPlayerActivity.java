package com.example.administrator.demo.VRPlayerDemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.demo.R;
import com.example.administrator.demo.base.MvvmBaseActivity;
import com.example.administrator.demo.databinding.ActivityVrPlayerBinding;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.StringUtil;


/**
 * Created by lsh on 2017/7/18.
 */

public class VRPlayerActivity extends MvvmBaseActivity<ActivityVrPlayerBinding> {


    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_vr_player;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getBinding().setActivity(this);
        Intent intent = getIntent();
        String urlStr = intent.getStringExtra("path");
        LogUtil.e(urlStr+"urlStr===");
        getBinding().player.setVideoPath(StringUtil.isNull(urlStr));
        getBinding().player.setMediaControllerTitle(getBinding().tvTitle);
    }


    public void onBackClick() {
        this.finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getBinding().player.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBinding().player.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBinding().player.onResume();
    }


}
