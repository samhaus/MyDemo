package com.example.administrator.demo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.administrator.demo.R;
import com.example.administrator.demo.base.MvvmBaseActivity;
import com.example.administrator.demo.recycleView.HomeActivity;
import com.example.administrator.demo.util.StatusBarUtil;

//做一个MVVM模式的欢迎页
public class WelcomeActivity extends MvvmBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump();
            }
        }, 3000);
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void jump() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
        ;
    }
}
