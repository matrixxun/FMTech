package com.fmtech.fmdianping.base.widget;

import android.view.View;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 21:51
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 21:51  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public interface FlipperAdapter<T> {
    public abstract T getNextItem(T param);

    public abstract T getPreviousItem(T param);

    public abstract View getView(T param, View view);

    public abstract void onMoved(T param, T param2);

    public abstract void onMoving(T param, T param2);

    public abstract void onTap(T param);

    public abstract void recycleView(View view);
}
