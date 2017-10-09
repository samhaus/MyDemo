package com.example.administrator.demo.network;

import com.example.administrator.demo.bean.CommonRequestBean;
import com.example.administrator.demo.bean.TestBean;
import com.example.administrator.demo.util.AppConstants;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samhaus on 2017/9/7.
 */

public interface Apis {

    //聚合数据机器人对话测试url
    @GET("/robot/index?key=" + AppConstants.JUHE_APPKEY)
    Observable<CommonRequestBean<TestBean>> getTest(@Query("info") String info);
}
