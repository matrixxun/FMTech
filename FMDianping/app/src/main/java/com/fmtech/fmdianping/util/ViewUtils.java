package com.fmtech.fmdianping.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 12:28
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 12:28  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class ViewUtils {
    private static int mScreenHeightPixels;
    private static int mScreenWidthPixels;

    public static int getScreenHeightPixels(Context context){
        if(mScreenHeightPixels > 0){
            return mScreenHeightPixels;
        }
        if(null != context){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            mScreenHeightPixels = displayMetrics.heightPixels;
        }
        return mScreenHeightPixels;
    }

    public static int getScreenWidthPixels(Context context){
        if(mScreenWidthPixels > 0){
            return mScreenWidthPixels;
        }
        if(null != context){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            mScreenWidthPixels = displayMetrics.widthPixels;
        }
        return mScreenWidthPixels;
    }

    public static void showView(View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
    }


}
