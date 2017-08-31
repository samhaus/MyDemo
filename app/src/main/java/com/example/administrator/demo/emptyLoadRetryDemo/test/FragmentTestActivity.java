package com.example.administrator.demo.emptyLoadRetryDemo.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.demo.emptyLoadRetryDemo.LoadingAndRetryManager;
import com.example.administrator.demo.R;


public class FragmentTestActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.id_rl_fragment_container);

        if (fragment == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.id_rl_fragment_container, new NormalFragment()).commit();
        }
    }


}
