package com.hanjinliang.projecttemplate.tools.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hanjinliang.projecttemplate.app.TemplateApp;

import java.io.File;

/**
 * Created by HanJinLiang on 2018-04-13.
 * 图片加载真正的实现
 */

public class GlideLoader implements ILoaderStrategy {
    @Override
    public void loadImage(final LoaderOptions options) {
        RequestManager requestManager=Glide.with(TemplateApp.getApp());

        DrawableTypeRequest drawableTypeRequest=null;
        if(options.drawableResId!=0){
            drawableTypeRequest=requestManager.load(options.drawableResId);
        }else if(!TextUtils.isEmpty(options.url)){
            drawableTypeRequest=requestManager.load(options.url);
        }else if(options.file!=null){
            drawableTypeRequest=requestManager.load(options.file);
        }else if(options.uri!=null){
            drawableTypeRequest=requestManager.load(options.uri);
        }
        if(options.placeholderResId!=0){
            drawableTypeRequest.placeholder(options.placeholderResId);
        }else if(options.placeholder!=null){
            drawableTypeRequest.placeholder(options.placeholder);
        }

        if(options.errorResId!=0){
            drawableTypeRequest.error(options.errorResId);
        }
        if(options.isCenterCrop){
            drawableTypeRequest.centerCrop();
        }
        if(options.isCenterInside){
            drawableTypeRequest.fitCenter();
        }
        if(options.skipLocalCache){
            drawableTypeRequest.skipMemoryCache(options.skipLocalCache);
        }

        if(options.targetWidth!=0&&options.targetHeight!=0){
            drawableTypeRequest.override(options.targetWidth,options.targetHeight);
        }

        if(options.callBack!=null) {
            drawableTypeRequest.asBitmap();
            drawableTypeRequest.listener(new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    options.callBack.onBitmapFailed(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    options.callBack.onBitmapLoaded((Bitmap) resource);
                    return false;
                }
            });
        }
        if(options.bitmapAngle!=0) {
            drawableTypeRequest.transform(new GlideRoundTransform(TemplateApp.getApp(),options.bitmapAngle));
        }

        if(options.targetView!=null){
            drawableTypeRequest.into((ImageView) options.targetView);
        }

    }

    /**
     * 圆角
     */
    public class GlideRoundTransform extends BitmapTransformation {

        private  float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

    /**
     * 圆形
     */
    public class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private   Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }

    }


}
