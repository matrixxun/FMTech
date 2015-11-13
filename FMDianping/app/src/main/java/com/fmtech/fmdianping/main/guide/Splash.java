package com.fmtech.fmdianping.main.guide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.app.BaseActivity;
import com.fmtech.fmdianping.app.FMEnvironment;
import com.fmtech.fmdianping.base.util.BluetoothHelper;
import com.fmtech.fmdianping.configservice.impl.ConfigHelper;
import com.fmtech.fmdianping.ui.activity.GuideActivity;
import com.fmtech.fmdianping.util.BitmapUtils;
import com.fmtech.fmdianping.util.Constants;
import com.fmtech.fmdianping.util.FMSharedPres;
import com.fmtech.fmdianping.util.ViewUtils;

public class Splash extends BaseActivity {

    private static final String TAG = "SplashManager";
    private static final String ACTION_SHOW_SPLASH = "com.fmtech.fmdianping.action.SHOW_SPLASH";
    private static final String BINARY_CITIES = "PL4JF98GHJSLSNF0IK";
    private static final int SHOW_SPLASH = 0;
    private static final int START_GUIDANCE_ACTIVITY = 1;
    private static final int START_GUIDANCE_NEW_COMER_ACTIVITY = 2;
    private static final int START_MAIN_ACTIVITY = 3;
    private static final int ONE_SECOND_TIMER = 4;

    private Bitmap mSplashBitmap = null;
    private ImageView mSplashImage;

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch(msg.what){
                case SHOW_SPLASH:


                    break;
                case START_GUIDANCE_ACTIVITY:
//                    statisticsEvent("index5", "index5_guide", "老用户", 0);
                    Intent guideIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.URI_GUIDANCE_ACTIVITY));
                    startActivity(guideIntent);
                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                    finish();
                    break;
                case START_GUIDANCE_NEW_COMER_ACTIVITY:
//                    statisticsEvent("index5", "index5_guide", "新用户", 0);//For what?
                    Intent guideNewComerIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.URI_GUIDANCE_NEW_COMER_ACTIVITY));
                    startActivity(guideNewComerIntent);
                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                    finish();
                    break;
                case START_MAIN_ACTIVITY:
                    Intent homeIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.URI_MAIN_ACTIVITY));
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
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
        int currVersionCode;
        int oldVersionCode;
        if(BluetoothHelper.hasBleFeature(getApplicationContext())){
            BluetoothHelper.scanLeDevice(getApplicationContext());

            currVersionCode = FMEnvironment.versionCode();
            oldVersionCode = FMSharedPres.shareInstance().getInt(Constants.PREF_KEY_GUIDANCE_VERSION_CODE, 0);
            //在各应用市场上架时的首发Splash
            if(ConfigHelper.enableShoufaSplash){
                mSplashBitmap = SplashManager.instance(getApplicationContext()).getShoufaSplashImage();
            }

            if(null == mSplashBitmap){
                int reqHeight = ViewUtils.getScreenHeightPixels(this);
                int reqWidth = ViewUtils.getScreenWidthPixels(this);
                mSplashBitmap = BitmapUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_splash_screen, reqWidth, reqHeight);
            }

            setContentView(R.layout.activity_splash);
            mSplashImage = (ImageView)findViewById(R.id.iv_splash);
            mSplashImage.setImageBitmap(mSplashBitmap);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.1F, 1.0F);
            alphaAnimation.setDuration(800L);
            mSplashImage.startAnimation(alphaAnimation);

            //To guidance activity, if app is updated;
//            if(oldVersionCode != 0 && currVersionCode > oldVersionCode){
            if(currVersionCode > oldVersionCode){
//                sendUpgrade();
//                deleteOldVersionCache();
                mHandler.sendEmptyMessageDelayed(START_GUIDANCE_ACTIVITY, SplashManager.instance(getApplicationContext()).getSplashDuration());
            }else{
                //To main activity
                mHandler.sendEmptyMessageDelayed(START_MAIN_ACTIVITY, SplashManager.instance(getApplicationContext()).getSplashDuration());
            }

        }
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
        if(null != mSplashBitmap){
            mSplashBitmap.recycle();
        }
    }
}
