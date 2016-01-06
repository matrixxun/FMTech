package com.fmtech.empf.ui.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fmtech.empf.utils.PreferenceUtils;

import java.util.Locale;


/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/31 08:36
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/31 08:36  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Translucent Status
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        switchLanguage(PreferenceUtils.shareInstance().getString("language", "CN"));

    }

    protected void switchLanguage(String locale){
        //应用内配置语言
        Resources resources =getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        if(locale.equals("CN")){
            config.locale = Locale.TRADITIONAL_CHINESE; //繁体中文
        }else{
            config.locale = Locale.ENGLISH; //英文
        }

        resources.updateConfiguration(config, dm);

        PreferenceUtils.shareInstance().putString("language", locale).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
