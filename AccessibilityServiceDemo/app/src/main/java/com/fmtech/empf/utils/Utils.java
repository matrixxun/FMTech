package com.fmtech.empf.utils;

import android.os.Looper;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 09:45
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 09:45  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class Utils {

    public static void ensureOnMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        throw new IllegalStateException("This method must be called from the UI thread.");
    }
}
