package com.fmtech.fmdianping.main.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.util.Constants;
import com.fmtech.fmdianping.util.FMSharedPres;
import com.fmtech.fmdianping.util.ViewUtils;

import java.io.IOException;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/12 21:55
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/12 21:55  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class SplashManager {

    private Context mContext;
    private static SplashManager sInstance;

    private SplashManager(Context context){
        mContext = context.getApplicationContext();
    }
    public static SplashManager instance(Context context){
        if(null == sInstance && null != context){
            sInstance = new SplashManager(context);
        }
        return sInstance;
    }

    /**
     * 获取应用上架渠道对应的应用商店的splash, 确切地说这在ic_splash_screen这个图片上再画上首发应用商店的logo icon;
     */
    public Bitmap getShoufaSplashImage(){
        Bitmap splashBitmap = getLoacalBitmapByAssets(mContext, "splash.png");//首发应用商店的logo icon;
        Bitmap confirmedBitmap = null;
        if(null != splashBitmap){
             confirmedBitmap= toConformBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_splash_screen), splashBitmap);
        }
        return confirmedBitmap;
    }

    private Bitmap getLoacalBitmapByAssets(Context context, String fileName) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            if(Build.VERSION.SDK_INT >= 9){
                options.inDensity = 320;
            }else{
                options.inDensity = 240;
            }
            options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
            return BitmapFactory.decodeStream(context.getResources().getAssets().open(fileName), null, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap toConformBitmap(Bitmap defaultSplashBitmap, Bitmap splashBitmap) {
        if(defaultSplashBitmap == null || null == splashBitmap){
            splashBitmap = null;
            return splashBitmap;
        }
        int splashW = defaultSplashBitmap.getWidth();
        int splashH = defaultSplashBitmap.getHeight();
        int shoufaW = splashBitmap.getWidth();
        int shoufaH = splashBitmap.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(splashW, splashH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawBitmap(defaultSplashBitmap, 0.0F, 0.0F, null);
        canvas.drawBitmap(splashBitmap, (splashW - shoufaW)/2.0F, getForegroundHeight(splashH, shoufaH), null);
        return targetBitmap;
    }

    private int getForegroundHeight(int splashH, int shoufaH)
    {
        if (shoufaH == splashH) {
            return 0;
        }
        int y;
        if (ViewUtils.getScreenHeightPixels(mContext) < splashH) {
            y = (splashH + ViewUtils.getScreenHeightPixels(mContext)) / 2;
        }else{
            y = splashH - shoufaH;
        }
        return y - ViewUtils.getScreenHeightPixels(mContext) / 20;
    }

    public int getSplashDuration(){
        int splashDuration = FMSharedPres.shareInstance().getInt(Constants.PREF_KEY_SPLASH_DURATION, 2000);
        return splashDuration;
    }
}
