package com.fmtech.empf.ui.navigationmanager;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.MainActivity;
import com.fmtech.empf.ui.fragments.ContactUsFragment;
import com.fmtech.empf.ui.fragments.FragmentConfig;
import com.fmtech.empf.ui.fragments.HomeFragment;
import com.fmtech.empf.ui.fragments.NewsFragment;
import com.fmtech.empf.ui.fragments.PageFragment;
import com.fmtech.empf.ui.fragments.SecurityTipsFragment;
import com.fmtech.empf.ui.fragments.SettingFragment;
import com.fmtech.empf.ui.fragments.TutorialsFragment;
import com.fmtech.empf.utils.MainThreadStack;

import java.util.Stack;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 09:01
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 09:01  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NavigationManager {

    public MainActivity mActivity;
    public final Stack<NavigationState> mBackStack = new MainThreadStack();
    public FragmentManager mFragmentManager;
    private boolean mShouldFallBackToAppsHome = false;

    public NavigationManager(MainActivity mainActivity){
        init(mainActivity);
    }

    public void init(MainActivity mainActivity){
        mActivity = mainActivity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public static boolean areTransitionsEnabled(){
        return Build.VERSION.SDK_INT >= 22;
    }

    public boolean goBack(){
        if(null == mActivity || mActivity.mStateSaved){
            return false;
        }

//        if(mShouldFallBackToAppsHome){
//            gotoHome();
//            mShouldFallBackToAppsHome = false;
//            return true;
//        }
        try {
            mBackStack.pop();
            mFragmentManager.popBackStack();
            mBackStack.peek();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void gotoHome(){
        if(canNavigate()){
            showPage(FragmentConfig.FRAGMENT_HOME, null, HomeFragment.newInstance(), true, new View[0]);
        }
    }

    public void gotoTutorials(){
        if(canNavigate()){
            showPage(FragmentConfig.FRAGMENT_TUTORIALS, null, TutorialsFragment.newInstance(), false, new View[0]);
        }
    }

    public void gotoNews(){
        if(canNavigate()){
            showPage(FragmentConfig.FRAGMENT_NEWS, null, NewsFragment.newInstance(), false, new View[0]);
        }
    }

    public void gotoSecurityTips(){
        if(canNavigate()){
            showPage(FragmentConfig.FRAGMENT_SECURITY_TIPS, null, SecurityTipsFragment.newInstance(), false, new View[0]);
        }
    }

    public void gotoContactUs(){
        showPage(FragmentConfig.FRAGMENT_CONTACT_US, null, ContactUsFragment.newInstance(), false, new View[0]);
    }

    public void gotoSetting(){
        if(canNavigate()){
            showPage(FragmentConfig.FRAGMENT_SETTING, null, SettingFragment.newInstance(), false, new View[0]);
        }
    }

    public PageFragment getActivePage(){
        return (PageFragment)mFragmentManager.findFragmentById(R.id.content_frame);
    }

    private boolean isHomeHome(){
        int currPageType = getCurrentPageType();
        System.out.println("-------getActivePage() instanceof HomeFragment: "+(getActivePage() instanceof HomeFragment)+" , currPageType: "+currPageType);
        return ((getActivePage() instanceof HomeFragment) && (currPageType == FragmentConfig.FRAGMENT_HOME));
    }

    public boolean isBackStackEmpty(){
        return mFragmentManager.getBackStackEntryCount() == 0;
    }

    public final boolean canNavigate(){
        return (mActivity != null) && (!mActivity.mStateSaved);
    }

    public final void clear(){
        clearInternal();
        mShouldFallBackToAppsHome = false;
    }

    public final void clearInternal(){
//        mBackStack.removeAllElements();
        while(mBackStack.size() > 1){
            mBackStack.pop();
        }
        while(mFragmentManager.getBackStackEntryCount() > 1){
            mFragmentManager.popBackStackImmediate();
        }
    }

    public final int getCurrentPageType(){
        if(mBackStack.isEmpty()){
            return FragmentConfig.FRAGMENT_HOME;
        }
        return ((NavigationState)mBackStack.peek()).pageType;
    }

    public final void popBackStack(){
        if(!mBackStack.isEmpty()){
            mBackStack.pop();
        }
        mFragmentManager.popBackStack();
    }

    public void resetBackstackToAppsHome(){
        if (!isHomeHome()) {
            clearInternal();
        }
        mShouldFallBackToAppsHome = true;
    }

    public final void showPage(int pageType, String url, Fragment fragment, boolean replaceTop, View ... sharedViews){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //** No animation for transition. */
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        if (replaceTop) {
            popBackStack();
        }
        NavigationState navigationState = new NavigationState(pageType, null, url);
        fragmentTransaction.addToBackStack(navigationState.backstackName);
        mBackStack.push(navigationState);
        fragmentTransaction.commit();
        System.out.println("-------mFragmentManager.getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount());
    }

    /*public final void showPage(int paramInt, String paramString, Fragment paramFragment, boolean paramBoolean, View... paramVarArgs)
    {
        FinskyLog.startTiming();
        FragmentTransaction localFragmentTransaction = this.mFragmentManager.beginTransaction();
        if (areTransitionsEnabled())
        {
            int i = paramVarArgs.length;
            int j = 0;
            if (j < i)
            {
                View localView = paramVarArgs[j];
                if (localView != null) {}
                for (String str = localView.getTransitionName();; str = null)
                {
                    if (!TextUtils.isEmpty(str)) {
                        localFragmentTransaction.addSharedElement(localView, str);
                    }
                    j++;
                    break;
                }
            }
        }
        localFragmentTransaction.setTransition$9d93138();
        localFragmentTransaction.replace(2131755234, paramFragment);
        if (paramBoolean) {
            popBackStack();
        }
        NavigationState localNavigationState = new NavigationState(paramInt, null, paramString);
        localFragmentTransaction.addToBackStack(localNavigationState.backstackName);
        this.mBackStack.push(localNavigationState);
        localFragmentTransaction.commit();
    }*/

}
