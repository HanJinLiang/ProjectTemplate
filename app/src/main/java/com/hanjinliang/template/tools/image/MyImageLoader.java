package com.hanjinliang.template.tools.image;

import android.net.Uri;

import java.io.File;

/**
 * Created by HanJinLiang on 2018-04-13.
 * 图片加载
 */

public class MyImageLoader {
    private static ILoaderStrategy sLoader;
    private static volatile MyImageLoader sInstance;

    private MyImageLoader() {
    }

    //单例模式
    public static MyImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (MyImageLoader.class) {
                if (sInstance == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    sInstance = new MyImageLoader();
                }
            }
        }
        return sInstance;
    }

    //提供实时替换图片加载框架的接口
    public void setImageLoader(ILoaderStrategy loader) {
        if (loader != null) {
            sLoader = loader;
        }
    }

    public LoaderOptions load(String path) {
        return new LoaderOptions(path);
    }

    public LoaderOptions load(int drawable) {
        return new LoaderOptions(drawable);
    }

    public LoaderOptions load(File file) {
        return new LoaderOptions(file);
    }

    public LoaderOptions load(Uri uri) {
        return new LoaderOptions(uri);
    }

    public void loadOptions(LoaderOptions options) {
        sLoader.loadImage(options);
    }


}
