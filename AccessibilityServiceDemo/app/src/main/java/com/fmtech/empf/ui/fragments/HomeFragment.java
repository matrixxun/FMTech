package com.fmtech.empf.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.component.ItemHomeTab;
import com.fmtech.empf.ui.fragments.homepages.HomeFragmentPagerAdapter;
import com.fmtech.empf.ui.fragments.homepages.MessageBoxFragment;
import com.fmtech.empf.ui.fragments.homepages.PersonalAccountFragment;
import com.fmtech.empf.ui.fragments.homepages.PersonalProfileFragment;
import com.fmtech.empf.ui.fragments.homepages.TrusteeContactFragment;

import java.util.ArrayList;
import java.util.List;

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

public class HomeFragment extends PageFragment implements View.OnClickListener{

    private ItemHomeTab mPresonalAccountTab;
    private ItemHomeTab mTrusteeContactTab;
    private ItemHomeTab mMessageBoxTab;
    private ItemHomeTab mPersonalProfileTab;
    private ArrayList<ItemHomeTab> mTabs;
    private ViewPager mViewPager;
    private static int mCurrentIndex = -1;

    private HomeFragmentPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresonalAccountTab = (ItemHomeTab)view.findViewById(R.id.home_tab_personal_account);
        mTrusteeContactTab = (ItemHomeTab)view.findViewById(R.id.home_tab_trustee_contact);
        mMessageBoxTab = (ItemHomeTab)view.findViewById(R.id.home_tab_message_box);
        mPersonalProfileTab = (ItemHomeTab)view.findViewById(R.id.home_tab_personal_profile);
        mViewPager = (ViewPager)view.findViewById(R.id.home_viewpager);

        initViewPager();
        initTabs();
        return view;
    }

    private void initViewPager(){
        if(mFragments.size() ==0){
            mFragments.add(PersonalAccountFragment.newInstance());
            mFragments.add(TrusteeContactFragment.newInstance());
            mFragments.add(MessageBoxFragment.newInstance());
            mFragments.add(PersonalProfileFragment.newInstance());
        }
        mPagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        mCurrentIndex = 0;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                syncTab(mCurrentIndex, position);
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    private void initTabs(){
        mPresonalAccountTab.setOnClickListener(this);
        mPresonalAccountTab.setMessageCount(0);
        mPresonalAccountTab.setSelected(true);
        mPresonalAccountTab.setTabTitle("Personal Account");

        mTrusteeContactTab.setOnClickListener(this);
        mTrusteeContactTab.setMessageCount(0);
        mTrusteeContactTab.setTabTitle("Trustee Contact");

        mMessageBoxTab.setOnClickListener(this);
        mMessageBoxTab.setTabTitle("Message Box");

        mPersonalProfileTab.setOnClickListener(this);
        mPersonalProfileTab.setMessageCount(0);
        mPersonalProfileTab.setTabTitle("Personal Profile");

        if(null == mTabs){
            mTabs = new ArrayList<ItemHomeTab>();
        }else{
            mTabs.clear();
        }
        mTabs.add(mPresonalAccountTab);
        mTabs.add(mTrusteeContactTab);
        mTabs.add(mMessageBoxTab);
        mTabs.add(mPersonalProfileTab);
    }

    private void syncTab(int preIndex, int currIndex){
        mTabs.get(preIndex).setSelected(false);
        mTabs.get(currIndex).setSelected(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void onClick(View v) {
        int currIndex = -1;
        switch (v.getId()){
            case R.id.home_tab_personal_account:
                currIndex = 0;
                break;
            case R.id.home_tab_trustee_contact:
                currIndex = 1;
                break;
            case R.id.home_tab_message_box:
                currIndex = 2;
                break;
            case R.id.home_tab_personal_profile:
                currIndex = 3;
                break;
        }
        if(currIndex != -1){
            mViewPager.setCurrentItem(currIndex, false);
        }

    }
}
