package com.hanjinliang.template.data.source;

import com.hanjinliang.template.data.model.Result;
import com.hanjinliang.template.data.source.remote.FastJsonConverterFactory;
import com.hanjinliang.template.data.source.remote.api.ApiService;
import com.hanjinliang.template.tools.image.MyImageLoader;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by HanJinLiang on 2018-04-16.
 * 数据仓库
 */

public class DataRepository {
    private static volatile DataRepository INSTANCE;

    private ApiService service;
    //单例模式
    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MyImageLoader.class) {
                if (INSTANCE == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    INSTANCE = new DataRepository();
                }
            }
        }
        return INSTANCE;
    }

    private DataRepository(){
        OkHttpClient client=new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level
                        .BODY))
                .build();
        String baseUrl="http://www.summernecro.com:8888/";

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new FastJsonConverterFactory())
                .callFactory(client)
                .build();
        service=retrofit.create(ApiService.class);
    }

    public void getTest(Callback<String> cb) {
        service.test().enqueue(cb);
    }
}
