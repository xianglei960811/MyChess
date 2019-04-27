package com.bysj.xl.chess.mychess.entity;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * author:向磊
 * date:2019/4/26
 * Describe:用于图片流转化
 */
public class BItmapToString {

    public static byte[] bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
