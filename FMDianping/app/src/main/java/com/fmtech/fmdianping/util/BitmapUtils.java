package com.fmtech.fmdianping.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 14:43
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 14:43  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class BitmapUtils {
    public static final int DEFAULT_DENSITY = 480;

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if(width > reqWidth || height > reqHeight){
            int halfHeight = height/2;
            int halfWidth = width/2;

            while((halfHeight / inSampleSize) > reqHeight && (halfWidth /inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
