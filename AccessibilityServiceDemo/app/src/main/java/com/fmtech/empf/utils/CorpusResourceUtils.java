package com.fmtech.empf.utils;

import android.content.Context;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 12:20
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 12:20  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public final class CorpusResourceUtils {

    public static int getPrimaryColor(Context paramContext, int paramInt) {
        return paramContext.getResources().getColor(getPrimaryColorResId(paramInt));
    }

    public static int getPrimaryColorResId(int paramInt) {
        return R.color.colorPrimary;
    }
}
