package com.fmtech.empf.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.fmtech.empf.ui.component.MPFADialog;
import com.fmtech.accessibilityservicedemo.R;

import java.util.Locale;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/16 11:21
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/16 11:21  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class SelectLanguageActivity extends Activity implements View.OnClickListener{

    private Button mBtnLanguageCN;
    private Button mBtnLanguageEN;
    private MPFADialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        mBtnLanguageCN = (Button)findViewById(R.id.btn_language_cn);
        mBtnLanguageEN = (Button)findViewById(R.id.btn_language_en);
        initViews();
    }

    private void initViews(){
        mBtnLanguageCN.setOnClickListener(this);
        mBtnLanguageEN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_language_cn:
                changeLanguage("CN");
                break;
            case R.id.btn_language_en:
                changeLanguage("EN");
                break;
        }
    }

    private void changeLanguage(String lang){
        //应用内配置语言
        Resources resources =getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        if(lang.equals("CN")){
            config.locale = Locale.TRADITIONAL_CHINESE; //繁体中文
        }else{
            config.locale = Locale.ENGLISH; //英文
        }

        resources.updateConfiguration(config, dm);
        //Two Buttons
        dialog= new MPFADialog(SelectLanguageActivity.this, null, null, true, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                Intent intent = new Intent(SelectLanguageActivity.this, GuidanceActivity.class);
                startActivity(intent);
            }
        }, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //On Button
//        dialog= new MPFADialog(SelectLanguage.this, null, null, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }
}
