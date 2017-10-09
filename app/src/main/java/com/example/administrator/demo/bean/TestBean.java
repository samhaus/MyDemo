package com.example.administrator.demo.bean;

/**
 * Created by samhaus on 2017/9/12.
 */

public class TestBean {

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "code=" + code +
                ", text='" + text + '\'' +
                '}';
    }
}
