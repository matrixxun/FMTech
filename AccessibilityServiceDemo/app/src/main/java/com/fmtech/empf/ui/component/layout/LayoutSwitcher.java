package com.fmtech.empf.ui.component.layout;

import android.os.Handler;
import android.view.View;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/1/5 11:36
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2016/1/5 11:36  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class LayoutSwitcher {

//    protected final View mContentLayout;
    protected int mDataLayerId;
//    private final int mErrorLayerId;
    private final Handler mHandler = new Handler();
//    private final int mLoadingLayerId;
    int mMode;
    volatile boolean mPendingLoad = false;


    public interface RetryButtonListener{
        public abstract void onRetry();
    }
}
