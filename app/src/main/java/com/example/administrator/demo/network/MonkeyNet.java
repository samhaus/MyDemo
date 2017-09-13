package com.example.administrator.demo.network;

import android.content.Context;
import android.telecom.Call;
import android.view.View;

import com.example.administrator.demo.bean.CommonRequestBean;
import com.example.administrator.demo.util.LogUtil;
import com.zy.mocknet.MockNet;
import com.zy.mocknet.application.MockConnection;
import com.zy.mocknet.application.MockConnectionFactory;
import com.zy.mocknet.application.selector.RandomSelector;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by samhaus on 2017/9/12.
 * 用于搭建本地网络服务端接口测试
 */

public class MonkeyNet {

    private MockNet mockNet;
private Context context;
    public MonkeyNet(Context context) {
        this.context=context;
        initMockNet();
    }

    public void initMockNet() {

        String json = "{" +
                "name: samhaus" +
                "}";
        byte[] con;
        try {
            con = json.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            con = json.getBytes();
        }

        // 创建 MockNet
        mockNet = MockNet.create()
                // 当 GET 请求网址没有匹配时，会匹配 /*，返回 'general connection'
                .addConnection(MockConnectionFactory.getInstance()
                        .createGeneralConnection("/*", "general connection"))
                // 当 POST 请求网址没有匹配时，会匹配 /*，返回 'general connection'
                .addConnection(MockConnectionFactory.getInstance()
                        .createGeneralConnection(MockConnection.POST, "/*", "general connection"))
                // 当 GET 请求为 127.0.0.1:8088/test 时, 返回 'first test'
                .addConnection(new MockConnection.Builder()
                        .setMethod(MockConnection.GET)
                        .setUrl("/test")
                        .setResponseBody("text/json", "first test")
                        .addResponseHeader("Content-Length", "" + "first test".length())
                        .addRequestHeader("Content-Length", "" + con.length)
                        .setVerifyHeaders(true)
                )
                // 当 GET 请求为 127.0.0.1:8088/test 时, 返回 'second test'
                .addConnection(new MockConnection.Builder()
                        .setMethod(MockConnection.GET)
                        .setUrl("/test")
                        .setResponseBody("text/json", "second test")
                        .addResponseHeader("Content-Length", "" + "second test".length())
                        .addRequestHeader("Content-Length", "" + con.length)
                        .setVerifyHeaders(true)
                )
                .addConnection(new MockConnection.Builder()
                        .setMethod(MockConnection.GET)
                        .setUrl("/test")
                        .setResponseBody("text/json", con, con.length)
                        .addResponseHeader("Content-Length", "" + con.length)
                        .addRequestHeader("Content-Length", "" + con.length)
                        .setVerifyHeaders(true)
                )
                .addConnection(new MockConnection.Builder()
                        .setMethod(MockConnection.POST)
                        .setUrl("/test")
                        .setResponseBody("text/json", con, con.length)
                        .addResponseHeader("Content-Length", "" + con.length)
                        .addRequestHeader("Content-Length", "" + con.length)
                        .setVerifyHeaders(true)
                )
                .setSelector(new RandomSelector());
    }

    public void startServer() {
        mockNet.start();
    }

    public void stopServer() {
        mockNet.stop();
    }

    public void postForm() {

//        requestQueue.add(new StringRequest(Request.Method.POST, URL_MAIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Logger.d(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Logger.d(error.toString());
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> para = new HashMap<>();
//                para.put("para1", "val1");
//                para.put("para2", "val2");
//                return para;
//            }
//        });
    }

    public void postMultipart() {
        Map<String, String> params = new HashMap<>();
        params.put("para1", "val1");
        params.put("para2", "val2");
//        requestQueue.add(new MultipartRequest(URL_MAIN, params,
//                new VolleyInterface(this) {
//                    @Override
//                    public void onMySuccess(String result) {
//                        Logger.d(result);
//                    }
//
//                    @Override
//                    public void onMyError(VolleyError error) {
//                        Logger.d("error");
//                    }
//                }));
    }

    public void postFile() {

    }

    public void get() {
        Observable observable= RetrofitManager.build().getTest("val1","val2");
        observable.compose(RxSchedulers.compose()).subscribe(new BaseObserver<CommonRequestBean>() {
            @Override
            protected void onHandleSuccess(CommonRequestBean o) {
                LogUtil.e("onHandleSuccess---"+o.toString());
            }

            @Override
            public void onNext(CommonRequestBean o) {
                LogUtil.e("onNext---"+o.toString());
            }
        });

    }

}
