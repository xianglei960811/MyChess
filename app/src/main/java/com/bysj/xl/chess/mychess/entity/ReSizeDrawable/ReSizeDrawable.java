package com.bysj.xl.chess.mychess.entity.ReSizeDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;

public class ReSizeDrawable {

    /***
     *
     *   静态类，用于对棋盘的缩放
     * @param context
     * @param drawable
     * @return
     */
    public static BitmapDrawable reSize(Context context, BitmapDrawable drawable) {
        int mScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
        Bitmap bitmap = drawable.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float num = (float) bitmapWidth / ((float) mScreenWidth);
        if (num < 1) {//图片比屏幕小，进行放大
            float numb = ((float) (mScreenWidth / 3) / (float) bitmapWidth) + 1.0f;
            Matrix matrix = new Matrix();
            matrix.postScale(num, numb);
            Log.d("ReSize", "reSize:is big--------------------> ");
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawble = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawble;
        } else if (num > 1) {//图片比屏幕宽度大，进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            Log.d("ReSize", "reSize:is small--------------------> ");
//            Log.e("sss", "reSize: "+mScreenWidth+":"+bitmapWidth+":"+bitmapHeight+":"+matrix.toString() );
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawable = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawable;
        } else {
            Log.d("ReSize", "reSize:is same--------------------> ");
            return drawable;
        }

    }

    /**
     * 对棋子进行缩放
     *
     * @param context
     * @param drawable
     * @return
     */
    public static BitmapDrawable reSizePieces(Context context, BitmapDrawable drawable) {
        int mScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
        Bitmap bitmap = drawable.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float num = (float) bitmapWidth / ((float) mScreenWidth / 9 / 2);
        if (num < 1) {//图片比屏幕小，进行放大
            float numb = ((float) (mScreenWidth / 9 / 6) / (float) bitmapWidth) + 1.0f;
            Matrix matrix = new Matrix();
            matrix.postScale(num, numb);
            Log.d("ReSize", "reSizePieces:is big--------------------> ");
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawble = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawble;
        } else if (num > 1) {//图片比屏幕宽度大，进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            Log.d("ReSize", "reSizePieces:is small--------------------> ");
//            Log.e("sss", "reSize: "+mScreenWidth+":"+bitmapWidth+":"+bitmapHeight+":"+matrix.toString() );
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawable = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawable;
        } else {
            Log.d("ReSize", "reSizePieces:is same--------------------> ");
            return drawable;
        }


    }

    /**
     * 对vector图标缩放
     *
     * @param context
     * @return
     */
    public static BitmapDrawable revector(Context context, Bitmap bitmap) {
        int mScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
//        Bitmap bitmap = drawable.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float num = (float) bitmapWidth / ((float) mScreenWidth / 11);
        if (num < 1) {//图片比屏幕小，进行放大
            float numb = ((float) (mScreenWidth / 11) / (float) bitmapWidth) + 1.0f;
            Matrix matrix = new Matrix();
            matrix.postScale(num, numb);
            Log.d("ReSize", "revector:is big--------------------> ");
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawble = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawble;
        } else if (num > 1) {//图片比屏幕宽度大，进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            Log.d("ReSize", "revector:is small--------------------> ");
//            Log.e("sss", "reSize: "+mScreenWidth+":"+bitmapWidth+":"+bitmapHeight+":"+matrix.toString() );
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawable = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawable;
        } else {
            Log.d("ReSize", "revector:is same--------------------> ");
            return new BitmapDrawable(context.getResources(),bitmap);
        }
    }

    /**
     * 对log图片
     *
     * @param context
     * @param drawable
     * @return
     */
    public static BitmapDrawable relog(Context context, BitmapDrawable drawable) {
        int mScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
        Bitmap bitmap = drawable.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float num = (float) bitmapWidth / ((float) mScreenWidth / 3);
        if (num < 1) {//图片比屏幕小，进行放大
            float numb = ((float) (mScreenWidth / 3) / (float) bitmapWidth) + 1.0f;
            Matrix matrix = new Matrix();
            matrix.postScale(num, numb);
            Log.d("ReSize", "relog:is big--------------------> ");
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawble = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawble;
        } else if (num > 1) {//图片比屏幕宽度大，进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            Log.d("ReSize", "relog:is small--------------------> ");
//            Log.e("sss", "reSize: "+mScreenWidth+":"+bitmapWidth+":"+bitmapHeight+":"+matrix.toString() );
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawable = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawable;
        } else {
            Log.d("ReSize", "relog:is same--------------------> ");
            return drawable;
        }
    }

    /**
     * 对denglu图片
     *
     * @param context
     * @param drawable
     * @return
     */
    public static BitmapDrawable reLogBt(Context context, BitmapDrawable drawable) {
        int mScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
        Bitmap bitmap = drawable.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float num = (float) bitmapWidth / ((float) mScreenWidth / 5*4);
        if (num < 1) {//图片比屏幕小，进行放大
            float numb = ((float) (mScreenWidth / 3 ) / (float) bitmapWidth) + 1.0f;
            Matrix matrix = new Matrix();
            matrix.postScale(num, numb);
            Log.d("ReSize", "reLogBt:is big--------------------> ");
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawble = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawble;
        } else if (num > 1) {//图片比屏幕宽度大，进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            Log.d("ReSize", "reLogBt:is small--------------------> ");
//            Log.e("sss", "reSize: "+mScreenWidth+":"+bitmapWidth+":"+bitmapHeight+":"+matrix.toString() );
            Bitmap reSizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            BitmapDrawable reSizeDrawable = new BitmapDrawable(context.getResources(), reSizeBitmap);
            return reSizeDrawable;
        } else {
            Log.d("ReSize", "reLogBt:is same--------------------> ");
            return drawable;
        }
    }
}
