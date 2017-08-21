package com.example.administrator.demo.WindowMgrDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.demo.R;
import com.example.administrator.demo.util.ToastUtil;

public class WindowMgrActivity extends AppCompatActivity {
    private static final String TAG = "WindowMgrActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_mgr);
//        final WindowService service = new WindowService();
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("开启悬浮层");
                Intent intent = new Intent(WindowMgrActivity.this, WindowService.class);
                startService(intent);
            }
        });

        findViewById(R.id.bt_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("关闭悬浮层");
//                service.onDestroy();
            }
        });
    }

}
