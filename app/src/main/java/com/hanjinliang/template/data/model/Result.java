package com.hanjinliang.template.data.model;

/**
 * Created by HanJinLiang on 2018-04-16.
 * 返回数据模板
 */

public class Result<T> {
    private int code;
    private String errMsg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return code == 200;
    }
}
