package com.fmtech.fmdianping.app;

import android.support.multidex.MultiDexApplication;

import com.fmtech.fmdianping.util.FMSharedPres;
import com.squareup.leakcanary.LeakCanary;

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
 * What have we done in FMApplication:
 *
 */

public class FMApplication extends MultiDexApplication {

    private static FMApplication instance;

    public FMApplication(){
        instance = this;
    }

    public static FMApplication _instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        x.Ext.init(this);
        //SharedPreference util init
        FMSharedPres.init(this);

        checkCrashReport();
    }

    private void checkCrashReport(){

    }
}
