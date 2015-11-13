package com.fmtech.fmdianping.main.guide.guidance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.fmtech.fmdianping.app.BaseActivity;
import com.fmtech.fmdianping.app.FMEnvironment;
import com.fmtech.fmdianping.util.Constants;
import com.fmtech.fmdianping.util.FMSharedPres;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/12 21:07
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/12 21:07  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class GuidanceActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void gotoMainActivity(){
        //save new version code
        FMSharedPres.shareInstance().putInt(Constants.PREF_KEY_GUIDANCE_VERSION_CODE, FMEnvironment.versionCode()).commit();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.URI_MAIN_ACTIVITY));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            gotoMainActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
