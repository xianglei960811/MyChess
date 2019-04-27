package com.bysj.xl.chess.mychess.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bysj.xl.chess.mychess.R;

/**
 * author:向磊
 * date:2019/2/20
 * Describe:加载图片
 */
public class LoadImage {
    private static final String TAG = "LoadImage";
    private static LoadImage INSTANCE = null;
    private Drawable drawable_place = null;
    private Drawable drawable_error = null;
    private Context context;

    private LoadImage(Context context) {
        this.context = context;
        drawable_place = ContextCompat.getDrawable(context, R.drawable.ic_waiting);
        drawable_error = ContextCompat.getDrawable(context, R.drawable.ic_error);
    }

    public static LoadImage getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (LoadImage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoadImage(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 加载方形图片
     *
     * @param bytes
     * @param view
     */
    public void loadHeadImage(byte[] bytes, ImageView view) {
        Log.d(TAG, "loadHeadIma: ----------------------->");

        Glide.with(context)
                .load(bytes)
                .listener(new RequestListener<byte[], GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, byte[] model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e(TAG, "onException: 图片加载失败-----------------------------》");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, byte[] model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e(TAG, "onResourceReady: -------------------------------->加载成功图片");
                        return false;
                    }
                })
                .placeholder(drawable_place)
                .error(drawable_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param bytes     图片二进制流
     * @param imageView 加载控件
     */
    public void LoadRoundImage(byte[] bytes, final ImageView imageView) {
        Glide.with(context)
                .load(bytes)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        (imageView).setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }
}
