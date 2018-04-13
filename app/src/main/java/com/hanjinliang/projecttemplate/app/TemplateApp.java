package com.hanjinliang.projecttemplate.app;

import android.app.Application;

import com.hanjinliang.projecttemplate.tools.image.GlideLoader;
import com.hanjinliang.projecttemplate.tools.image.MyImageLoader;


/**
 * Created by HanJinLiang on 2018-04-13.
 */

public class TemplateApp extends Application {
    private static TemplateApp sInstance;

    //单例模式
    public static TemplateApp getApp() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        //设置图片加载为Glide
        MyImageLoader.getInstance().setImageLoader(new GlideLoader());
    }
}
