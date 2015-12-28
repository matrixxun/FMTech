package com.fmtech.empf.ui.fragments;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.component.ItemHomeTab;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/25 14:49
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/25 14:49  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class HomeFragment extends PageFragment{

    private ItemHomeTab mPresonalAccountTab;
    private ItemHomeTab mTrusteeContactTab;
    private ItemHomeTab mMessageBoxTab;
    private ItemHomeTab mPersonalProfileTab;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
