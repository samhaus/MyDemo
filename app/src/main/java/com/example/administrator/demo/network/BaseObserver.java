package com.example.administrator.demo.network;


import com.example.administrator.demo.bean.CommonRequestBean;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by samhaus on 2017/9/12.
 * RxJava Observable订阅需要传入一个观察者对象，此处封装一个BaseObserver
 */

public abstract class BaseObserver <T> implements Observer<CommonRequestBean<T>> {

    private static final String TAG = "BaseObserver";

//    private Context mContext;

    protected BaseObserver() {
//        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CommonRequestBean<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg());
        }
    }


    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        LogUtil.e(TAG, "onComplete");
    }


    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        ToastUtil.show(msg);
    }

}
