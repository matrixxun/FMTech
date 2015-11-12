package com.fmtech.fmdianping.main.guide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.app.BaseActivity;
import com.fmtech.fmdianping.ui.activity.GuideActivity;
import com.fmtech.fmdianping.util.Constants;
import com.fmtech.fmdianping.util.FMSharedPres;

public class Splash extends BaseActivity {

    private static final String TAG = "SplashManager";
    private static final String ACTION_SHOW_SPLASH = "com.fmtech.fmdianping.action.SHOW_SPLASH";
    private static final int SHOW_SPLASH = 0;
    private static final int START_GUIDANCE_ACTIVITY = 1;
    private static final int START_GUIDANCE_NEW_COMER_ACTIVITY = 2;
    private static final int START_MAIN_ACTIVITY = 3;
    private static final int ONE_SECOND_TIMER = 4;

    private Bitmap mBgImage = null;
    private ImageView mSplashImage;

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch(msg.what){
                case SHOW_SPLASH:


                    break;
                case START_GUIDANCE_ACTIVITY:
//                    statisticsEvent("index5", "index5_guide", "老用户", 0);
                    Intent guideIntent = new Intent("android.intent.action.VIEW", Uri.parse("fmdianping://guidance"));
                    startActivity(guideIntent);
//                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                    finish();
                    break;
                case START_GUIDANCE_NEW_COMER_ACTIVITY:
//                    statisticsEvent("index5", "index5_guide", "新用户", 0);//For what?
                    Intent guideNewComerIntent = new Intent("android.intent.action.VIEW", Uri.parse("fmdianping://guidancenewcomer"));
                    startActivity(guideNewComerIntent);
//                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                    finish();
                    break;
                case START_MAIN_ACTIVITY:
                    Intent homeIntent = new Intent("android.intent.action.VIEW", Uri.parse("fmdianping://home"));
                    startActivity(homeIntent);
//                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                    finish();
                    break;
                case ONE_SECOND_TIMER:

                    break;
            }
            if(FMSharedPres.shareInstance().getBoolean(Constants.IS_FIRST_LOGIN, true)){
                toGuidePage();
//                FMSharedPres.shareInstance().putBoolean(Constants.IS_FIRST_LOGIN, false).commit();
            }else {
                toMainPage();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplashImage = (ImageView)findViewById(R.id.iv_splash);
        mHandler.sendEmptyMessageDelayed(0, 3000L);
    }

    private void toMainPage(){
        Intent intent = new Intent();
        intent.setClass(Splash.this, MainActivity_bak.class);
        startActivity(intent);
        finish();
    }

    private void toGuidePage(){
        Intent intent = new Intent();
        intent.setClass(Splash.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mBgImage){
            mBgImage.recycle();
        }
    }
}
