package com.fmtech.empf.ui.component.drawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;
import com.fmtech.empf.utils.CommonResourceUtils;

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
        DrawerAction homeAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_home), true, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoHome();
            }
        });

        DrawerAction tutorialsAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_tutorials), false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoTutorials();
            }
        });

        DrawerAction newsAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_news), false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoNews();
            }
        });

        DrawerAction securityTipsAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_security_tips), false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoSecurityTips();
            }
        });

        DrawerAction contactUsAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_contact_us), false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoContactUs();
            }
        });

        DrawerAction settingAction = new DrawerAction(CommonResourceUtils.getString(R.string.fragment_title_setting), false, new Runnable() {
            @Override
            public void run() {
                mNavigationManager.resetBackstackToAppsHome();
                mNavigationManager.gotoSetting();
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
