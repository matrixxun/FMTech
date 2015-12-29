package com.fmtech.empf.ui.navigationmanager;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.MainActivity;
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

    private boolean goBack(){
        if(null == mActivity || mActivity.mStateSaved){
            return false;
        }

        mBackStack.pop();
        mFragmentManager.popBackStack();
        mBackStack.peek();
        return true;
    }

    private boolean isHomeHome(){
        //TODO
        return false;
    }

    public boolean isBackStackEmpty(){
        return mFragmentManager.getBackStackEntryCount() == 0;
    }

    public final boolean canNavigate(){
        return (mActivity != null) && (mActivity.mStateSaved);
    }

    public final void clear(){
        clearInternal();
        mShouldFallBackToAppsHome = false;
    }

    public final void clearInternal(){
        mBackStack.removeAllElements();
        while(mFragmentManager.getBackStackEntryCount() > 0){
            mFragmentManager.popBackStackImmediate();
        }
    }

    public final int getCurrentPageType(){
        if(mBackStack.isEmpty()){
            return 0;
        }
        return ((NavigationState)mBackStack.peek()).pageType;
    }

    public final void popBackStack(){
        if(!mBackStack.isEmpty()){
            mBackStack.pop();
        }
        mFragmentManager.popBackStack();
    }

    public final void showPage(int pageType, String url, Fragment fragment, boolean isPopBackStack, View ... views){
        //// TODO: 2015/12/29  要显示的页面为当前页时不进行切换
        //解决实现逻辑

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //** No animation for transition. */
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        if (isPopBackStack) {
            popBackStack();
        }
        NavigationState navigationState = new NavigationState(pageType, null, url);
        fragmentTransaction.addToBackStack(navigationState.backstackName);
        mBackStack.push(navigationState);
        fragmentTransaction.commit();
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
