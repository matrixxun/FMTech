package com.fmtech.empf.ui.fragments;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 13:42
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 13:42  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class TutorialsFragment extends PageFragment{

    public static TutorialsFragment newInstance(){
        TutorialsFragment newsFragment = new TutorialsFragment();
        return newsFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tutorials;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
