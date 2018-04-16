package com.hanjinliang.template.data.source.remote.api;

import com.hanjinliang.template.data.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HanJinLiang on 2018-04-16.
 */

public interface ApiService {
    @GET("server/server/getAllVideosByGet")
    Call<String> test();
}
