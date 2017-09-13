package com.example.administrator.demo.network;

import com.example.administrator.demo.bean.CommonRequestBean;
import com.example.administrator.demo.bean.TestBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samhaus on 2017/9/7.
 */

public interface Apis {

    @GET("/test")
    Observable<CommonRequestBean<TestBean>> getTest(@Query("para1") String t,
                                                    @Query("para2") String t1);
}
