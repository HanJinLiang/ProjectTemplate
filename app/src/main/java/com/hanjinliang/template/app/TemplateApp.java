package com.hanjinliang.template.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hanjinliang.template.tools.image.GlideLoader;
import com.hanjinliang.template.tools.image.MyImageLoader;


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

        Utils.init(this);
    }
}
