package com.example.administrator.demo.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.demo.R;
import com.example.administrator.demo.base.BaseActivity;
import com.example.administrator.demo.util.BatteryListener;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.ScreenUtil;

/**
 * Created by samhaus on 2017/9/13.
 * 电源变化监控
 */
public class BatteryListenActivity extends BaseActivity implements BatteryListener.BatteryStateListener {

    private BatteryListener listener;
    private ToggleButton tbBarrety;
    private TextView tvStatus;

    @Override
    public int generateLayout() {
        return R.layout.activity_battery_listen;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new BatteryListener(this);
        listener.register(BatteryListenActivity.this);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView() {
        tvStatus = (TextView) findViewById(R.id.tv_battery_status);
        tbBarrety = (ToggleButton) findViewById(R.id.tb_battery);
        tbBarrety.setChecked(true);
        tbBarrety.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//点击关闭电源监听
                    tbBarrety.setText("打开电源监听");
                    if (listener != null) {
                        listener.unregister();
                    }
                } else {//点击打开电源监听
                    tbBarrety.setText("关闭电源监听");
                    listener.register(BatteryListenActivity.this);
                }
            }
        });
        tbBarrety.toggle();

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (listener != null) {
            listener.unregister();
        }
    }

    @Override
    public void onStateChanged() {
//        tvStatus.setText("电量发生改变");
        LogUtil.e("电量发生改变");
        ScreenUtil.lightScreen(true);
    }

    @Override
    public void onStateLow() {
        tvStatus.setText("电量低");
        LogUtil.e("电量低");
    }

    @Override
    public void onStateOkay() {
        tvStatus.setText("电量充满");
        LogUtil.e("电量充满");
    }

    @Override
    public void onStatePowerConnected() {
        tvStatus.setText("接通电源");
        LogUtil.e("接通电源");
        ScreenUtil.lightScreen(false);

    }

    @Override
    public void onStatePowerDisconnected() {
        tvStatus.setText("拔出电源");
        LogUtil.e("拔出电源");
        ScreenUtil.lightScreen(false);
    }

}
