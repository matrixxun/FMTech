package com.fmtech.empf.ui.fragments;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/24 10:18
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/24 10:18  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class LoginSignupFragment extends PageFragment{

    public static LoginSignupFragment newInstance(){
        LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
        return loginSignupFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login_signup;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
