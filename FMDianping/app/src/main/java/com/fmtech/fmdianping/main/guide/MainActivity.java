package com.fmtech.fmdianping.main.guide;

import android.os.Bundle;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.app.BaseActivity;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/12 18:09
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/12 18:09  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class MainActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }
}
