package com.fmtech.empf.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fmtech.empf.MainActivity;
import com.fmtech.empf.ui.component.MPFADialog;
import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/17 17:26
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/17 17:26  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class GuidanceActivity extends Activity{

    private MPFADialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
    }

    public void onSkip(View view){
        mDialog = new MPFADialog(this, "Important Message", "To protect your interest, please never disclose your password to anyone.", "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(GuidanceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mDialog.show();
    }
}
