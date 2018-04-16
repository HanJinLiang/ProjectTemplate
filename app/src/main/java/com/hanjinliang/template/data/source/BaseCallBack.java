package com.hanjinliang.template.data.source;

import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.template.data.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HanJinLiang on 2018-04-16.
 */

public abstract class BaseCallBack<T> implements Callback<Result<T>>{

    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        if (response.isSuccessful() && response.body() != null && response.body().success()) {
            onSuccess(response.body().getData());
        } else {
            onError(response, null);
        }
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        onError(null, t);
    }

    protected abstract void onSuccess(T data);

    protected void onError(Response<Result<T>> response, Throwable t) {
        if (response != null && response.body() != null) {
            ToastUtils.showShort(response.body().getErrMsg());
            if(response.body().getCode()==403){//用户无权限   跳转到登录界面

            }
        } else {
            ToastUtils.showShort("网络连接异常");
        }
    }
}
