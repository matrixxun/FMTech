package com.fmtech.empf.ui.component.drawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.fragments.ContactUsFragment;
import com.fmtech.empf.ui.fragments.FragmentConfig;
import com.fmtech.empf.ui.fragments.HomeFragment;
import com.fmtech.empf.ui.fragments.NewsFragment;
import com.fmtech.empf.ui.fragments.SecurityTipsFragment;
import com.fmtech.empf.ui.fragments.SettingFragment;
import com.fmtech.empf.ui.fragments.TutorialsFragment;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 18:04
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 18:04  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class FMDrawerLayout extends DrawerLayout implements DrawerContentClickListener {

    //    public List<DrawerAction> mDrawerActions = new ArrayList<DrawerAction>();
    public DrawerAdapter mDrawerAdapter;
    public ListView mDrawerList;
    public ViewGroup mDrawerRoot;

    public NavigationManager mNavigationManager;

    public FMDrawerLayout(Context context) {
        super(context);
    }

    public FMDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable drawerShaodow = getResources().getDrawable(R.drawable.drawer_shadow);
//        if (!DrawerLayout.SET_DRAWER_SHADOW_FROM_ELEVATION)
        if (!(Build.VERSION.SDK_INT >= 21)) {
            super.setDrawerShadow(drawerShaodow, GravityCompat.START);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDrawerRoot = (ViewGroup)findViewById(R.id.drawer_root);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
    }

    public void refesh() {
        DrawerAction homeAction = new DrawerAction("Home", true, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.showPage(FragmentConfig.FRAGMENT_HOME, null, HomeFragment.newInstance(), true, new View[0]);
            }
        });

        DrawerAction tutorialsAction = new DrawerAction("Tutorials", false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.showPage(FragmentConfig.FRAGMENT_TUTORIALS, null, TutorialsFragment.newInstance(), true, new View[0]);
            }
        });

        DrawerAction newsAction = new DrawerAction("News", false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.showPage(FragmentConfig.FRAGMENT_NEWS, null, NewsFragment.newInstance(), true, new View[0]);
            }
        });

        DrawerAction securityTipsAction = new DrawerAction("Security Tips", false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.showPage(FragmentConfig.FRAGMENT_SECURITY_TIPS, null, SecurityTipsFragment.newInstance(), true, new View[0]);
            }
        });

        DrawerAction contactUsAction = new DrawerAction("Contact Us", false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.showPage(FragmentConfig.FRAGMENT_CONTACT_US, null, ContactUsFragment.newInstance(), true, new View[0]);
            }
        });

        DrawerAction settingAction = new DrawerAction("Setting", false, new Runnable() {
            @Override
            public void run() {
            mNavigationManager.showPage(FragmentConfig.FRAGMENT_SETTING, null, SettingFragment.newInstance(), true, new View[0]);
            }
        });
        mDrawerAdapter.mDrawerActions.add(homeAction);
        mDrawerAdapter.mDrawerActions.add(tutorialsAction);
        mDrawerAdapter.mDrawerActions.add(newsAction);
        mDrawerAdapter.mDrawerActions.add(securityTipsAction);
        mDrawerAdapter.mDrawerActions.add(contactUsAction);
        mDrawerAdapter.mDrawerActions.add(settingAction);
        mDrawerAdapter.notifyDataSetChanged();
    }

    public final void closeDrawer() {
//        checkIsConfigured();
        if (isDrawerOpen(this.mDrawerRoot)) {
            closeDrawer(this.mDrawerRoot);
        }
    }

    public final boolean isDrawerOpen() {
//        checkIsConfigured();
        return isDrawerOpen(this.mDrawerRoot);
    }

    @Override
    public boolean onDrawActionClicked(DrawerAction drawerAction) {
        if(!drawerAction.isActive){
            drawerAction.actionSelectedRunnable.run();
        }
        return true;
    }

    public static final class DrawerAction {
        public final Runnable actionSelectedRunnable;
        public final String actionText;
        public final int activeIconResId;
        public final int activeTextColorResId;
        public final int iconResId;
//        public final boolean isActive;
        public boolean isActive;
        public final boolean isSeparator;
//        public final boolean hasNotifications;
//        public final boolean isAvailableInDownloadOnly;
//        public final boolean isChild;
//        public final int secondaryIconResId;

        public DrawerAction() {
            this.actionText = null;
            this.iconResId = -1;
            this.activeIconResId = -1;
            this.activeTextColorResId = -1;
            this.isActive = false;
            this.actionSelectedRunnable = null;
            this.isSeparator = true;
//            this.secondaryIconResId = -1;
//            this.isAvailableInDownloadOnly = false;
//            this.isChild = false;
//            this.hasNotifications = false;
        }

        public DrawerAction(String actionText, boolean isActive, Runnable actionSelectedRunnable) {
//            this.actionText = actionText;
//            this.isActive = isActive;
//            this.actionSelectedRunnable = actionSelectedRunnable;
            this(actionText, -1, -1, -1, isActive, actionSelectedRunnable);
        }

        public DrawerAction(String actionText, int iconResId, int activeIconResId, int activeTextColorResId, boolean isActive, Runnable actionSelectedRunnable) {
            this.actionText = actionText;
            this.iconResId = iconResId;
            this.activeIconResId = activeIconResId;
            this.activeTextColorResId = activeTextColorResId;
            this.isActive = isActive;
            this.actionSelectedRunnable = actionSelectedRunnable;
            this.isSeparator = false;
//            this.isAvailableInDownloadOnly = isAvailableInDownloadOnly;
//            this.secondaryIconResId = secondaryIconResId;
//            this.isChild = paramBoolean3;
//            this.hasNotifications = hasNotifications;
        }

    }

}
