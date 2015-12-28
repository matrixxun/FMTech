package com.fmtech.empf.util;

import android.content.Context;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/18 10:07
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/18 10:07  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class DimenUtils {

    public static int sp2px(Context context, float spValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5);
    }

    public static int px2sp(Context context, int pxValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue/fontScale + 0.5);
    }

    public static int dip2px(Context context, float dipValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5);
    }

    public static int px2dip(Context context, int pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale +0.5);
    }
}
