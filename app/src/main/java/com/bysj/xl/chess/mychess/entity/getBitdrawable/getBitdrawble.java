package com.bysj.xl.chess.mychess.entity.getBitdrawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;

/**
 * Author: XL
 * Date: 2018-11-02 16:18
 * Describe:vectordrawable转bitdrawable
 */
public class getBitdrawble {
    public static Bitmap getbitDrawable(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
