package com.fmtech.fmdianping;

import android.app.Application;

import com.fmtech.fmdianping.utils.FMSharedPres;

import org.xutils.x;
/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/8 15:18
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/8 15:18  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class FMApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //SharedPreference util init
        FMSharedPres.init(this);
    }
}
