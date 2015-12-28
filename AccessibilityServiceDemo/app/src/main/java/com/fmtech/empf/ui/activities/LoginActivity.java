package com.fmtech.empf.ui.activities;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/18 12:20
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/18 12:20  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class LoginActivity extends AppCompatActivity{

    private TextView mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        mForgotPassword = (TextView)findViewById(R.id.tv_forgot_password);
//        String forgotPassword = getResources().getString(R.string.forgot_password);
//        mForgotPassword.setText(Html.fromHtml(forgotPassword));
//        mForgotPassword.setText(forgotPassword);
        mForgotPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
        mForgotPassword.getPaint().setAntiAlias(true);//抗锯齿
    }

    public void doRealLogin(View view){
        //TODO API login to server, check login infomation, authorisation

        //login success check if is the first time login
        SharedPreferences sharedPreferences = getSharedPreferences("MPFA_SharedPref", MODE_PRIVATE);
        boolean isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true);
        if(isFirstLogin){//To first login page

        }else{//To second level password page

        }
    }
}
