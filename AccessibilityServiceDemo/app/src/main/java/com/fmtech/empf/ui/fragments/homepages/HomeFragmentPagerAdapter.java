package com.fmtech.empf.ui.fragments.homepages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fmtech.empf.ui.fragments.PageFragment;

import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 15:30
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 15:30  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<PageFragment> mFragments;

    public HomeFragmentPagerAdapter(FragmentManager fragmentManager, List<PageFragment> fragments){
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public PageFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return null == mFragments? 0 : mFragments.size();
    }


}
