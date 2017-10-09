package com.example.administrator.demo.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by samhaus on 2017/9/12.
 */

@Entity
public class CommonRequestBean<E> {

    @SerializedName("error_code")
    private int error_code;
    @SerializedName("reason")
    private String reason;
    @SerializedName("result")
    private E result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CommonRequestBean{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
