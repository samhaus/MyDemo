package com.example.administrator.demo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.demo.R;
import com.example.administrator.demo.util.StatusBarUtil;


/**
 * Created by lsh on 05/07/2017.
 */

public abstract class MvvmBaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private View mainView;
    private ViewDataBinding binding;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        super.setContentView(layoutResID);
        setStatusBar();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        super.onCreate(savedInstanceState);
        try {
            binding = DataBindingUtil.setContentView(this, layoutId);
            if (binding != null) {
                mainView = binding.getRoot();
            } else {
                mainView = LayoutInflater.from(this).inflate(layoutId, null);
                setContentView(mainView);
            }

        } catch (NoClassDefFoundError e) {
            mainView = LayoutInflater.from(this).inflate(layoutId, null);
            setContentView(mainView);
        }
        initData(savedInstanceState);
        initView();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Set StatusBar,Child can Override to Set
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    public T getBinding() {
        return (T) binding;
    }

    public abstract int getLayoutId();

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initView();
}
