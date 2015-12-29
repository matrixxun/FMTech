package com.fmtech.empf.ui.component.actionbar;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;

import java.util.Stack;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 09:03
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 09:03  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public final class ActionBarHelper {

    public ActionBar mActionBar;
    public ActionBarController mActionBarController;
    private Stack<ActionBarState> mActionBarStateStack;
    private Activity mActivity;
    private CharSequence mCurrentTitle;
    public NavigationManager mNavigationManager;
    public Toolbar mToolbar;
    public int mCurrentBackendId;

    public ActionBarHelper(NavigationManager navigationManager, AppCompatActivity appCompatActivity) {
        this(navigationManager, null, appCompatActivity);
    }

    public ActionBarHelper(NavigationManager navigationManager, ActionBarController actionBarController, AppCompatActivity activity) {
        mActionBar = activity.getDelegate().getSupportActionBar();
        mToolbar = ((Toolbar) activity.findViewById(R.id.toolbar));
        mActivity = activity;
        mNavigationManager = navigationManager;
        mActionBarController = actionBarController;
        mActionBarStateStack = new Stack();
        mActionBarStateStack.push(new ActionBarState(0, null));
        mCurrentBackendId = 0;
        if (mToolbar != null) {
//            mToolbar.setCurrentBackendId(mCurrentBackendId);
//            mToolbar.setNavigationManager(mNavigationManager);
//            mToolbar.setActionBarController(mActionBarController);
            mToolbar.setVisibility(View.INVISIBLE);
        }
//        if (mToolbar != null) {
//            if ((!DotNotificationUtils.shouldShowAccountCompletionDotNotification()) || (((Integer) FinskyPreferences.accountCompletionMainMenuDotTapCount.get(FinskyApp.get().getCurrentAccountName()).get()).intValue() != 0)) {
//                break label306;
//            }
//        }
//        label306:
//        for (int i = 1; ; i = 0) {
//            if (i != 0) {
//                View localView = mToolbar.getSearchView().findViewById(2131755724);
//                if (localView != null) {
//                    localView.setVisibility(0);
//                }
//                mMainMenuUiElementNode = new GenericUiElementNode(5301, null, null, new GenericUiElementNode(5300, null, null, mNavigationManager.getActivePage()));
//                FinskyApp.get().getEventLogger().logPathImpression$7d139cbf(299, mMainMenuUiElementNode);
//            }
//            if (mActionBar != null) {
//                mActionBar.setBackgroundDrawable(getBackgroundColorDrawable(CorpusResourceUtils.getPrimaryColor(mActivity, 0)));
//            }
//            mTransparentBackgroundDrawable = new ColorDrawable(0);
//            navigationManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                public final void onBackStackChanged() {
//                    if (ActionBarHelper.sSawFirstBackstackChange) {
//                        ActionBarHelper.clearSearch();
//                    }
//                    for (; ; ) {
//                        ActionBarHelper.syncState();
//                        return;
//                        ActionBarHelper.access$002$138603();
//                    }
//                }
//            });
//            return;
//        }
    }

    public final void updateDefaultTitle(CharSequence title){
        ((ActionBarState)mActionBarStateStack.get(0)).title = title;
        syncState();
    }

    public final void syncState(){
        CharSequence title = ((ActionBarState) mActionBarStateStack.peek()).title;
        setTitle(title);
        //TODO Many things to do.

    }

    private void setTitle(CharSequence title){
        if(null != mActionBar) {
            mCurrentTitle = title;
            mActionBar.setTitle(mCurrentTitle);
        }
    }

    private static final class ActionBarState {
        public int mode;
        public CharSequence title;

        public ActionBarState(int mode, CharSequence title) {
            this.mode = mode;
            this.title = title;
        }

        public final String toString() {
            return "[type: " + this.mode + ", title: " + this.title + "]";
        }
    }
}
