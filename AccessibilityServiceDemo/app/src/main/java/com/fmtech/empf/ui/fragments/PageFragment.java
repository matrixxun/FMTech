package com.fmtech.empf.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.component.actionbar.ActionBarController;
import com.fmtech.empf.ui.component.layout.LayoutSwitcher;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;
import com.fmtech.empf.utils.CorpusResourceUtils;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/24 10:16
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/24 10:16  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public abstract class PageFragment extends Fragment {

    public ActionBarController mActionBarController;
    public LayoutSwitcher mLayoutSwitcher;
    public NavigationManager mNavigationManager;
    public PageFragmentHost mPageFragmentHost;

    public PageFragment() {

    }

    public Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPageFragmentHost = (PageFragmentHost)getActivity();
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onBackPressed() {
        return false;
    }

    public void rebindActionBar(){

    };

    public abstract int getLayoutRes();

    public abstract void rebindViews();

    public abstract void requestData();

    public int getActionBarTitleColor() {
        return this.mContext.getResources().getColor(R.color.colorPrimary);
    }

    public int getActionBarColor() {
        return CorpusResourceUtils.getPrimaryColor(this.mContext, 0);
    }
}
