package com.fmtech.empf.ui.fragments.homepages;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.fragments.PageFragment;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 14:52
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 14:52  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class PersonalProfileFragment extends PageFragment{

    private String mBreadcrumb;

    public static PersonalProfileFragment newInstance(){
        PersonalProfileFragment personalProfileFragment= new PersonalProfileFragment();
        return personalProfileFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBreadcrumb = mContext.getString(R.string.fragment_title_personal_profile);
//        rebindActionBar();
    }

    @Override
    public void rebindActionBar() {
        this.mPageFragmentHost.updateActionBarTitle(mBreadcrumb);
        mPageFragmentHost.updateCurrentBackendId(0, true);
        mPageFragmentHost.switchToRegularActionBar();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.pager_personal_profile;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
