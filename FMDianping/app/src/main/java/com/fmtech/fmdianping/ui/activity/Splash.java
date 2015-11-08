package com.fmtech.fmdianping.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.utils.Constants;
import com.fmtech.fmdianping.utils.FMSharedPres;

public class Splash extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(FMSharedPres.shareInstance().getBoolean(Constants.IS_FIRST_LOGIN, true)){
                toGuidePage();
//                FMSharedPres.shareInstance().putBoolean(Constants.IS_FIRST_LOGIN, false).commit();
            }else {
                toMainPage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.sendEmptyMessageDelayed(0, 3000L);
    }

    private void toMainPage(){
        Intent intent = new Intent();
        intent.setClass(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toGuidePage(){
        Intent intent = new Intent();
        intent.setClass(Splash.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

}
