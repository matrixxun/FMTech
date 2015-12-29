package com.fmtech.empf.ui.component;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fmtech.accessibilityservicedemo.R;
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

public class FMDrawerLayout extends DrawerLayout {

    public List<DrawerAction> mDrawerActions = new ArrayList<DrawerAction>();
    public ListView mDrawerList;
    private NavigationManager mNavigationManager;

    public FMDrawerLayout(Context context) {
        super(context);
    }

    public FMDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDrawerList = (ListView)findViewById(R.id.drawer_list);
    }

    public void refesh(){
        DrawerAction homeAction = new DrawerAction("Home", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });

        DrawerAction tutorialsAction = new DrawerAction("Tutorials", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });

        DrawerAction newsAction = new DrawerAction("News", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });

        DrawerAction securityTipsAction = new DrawerAction("Security Tips", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });

        DrawerAction contactUsAction = new DrawerAction("Contact Us", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });

        DrawerAction settingAction = new DrawerAction("Setting", true, new Runnable() {
            @Override
            public void run() {
                //关闭DrawerLayout

                //跳转到对应的界面

            }
        });
        mDrawerActions.add(homeAction);
        mDrawerActions.add(tutorialsAction);
        mDrawerActions.add(newsAction);
        mDrawerActions.add(securityTipsAction);
        mDrawerActions.add(contactUsAction);
        mDrawerActions.add(settingAction);
    }

    public static final class DrawerAction {
        public final Runnable actionSelectedRunnable;
        public final String actionText;
        public final int activeIconResId;
        public final int activeTextColorResId;
        public final int iconResId;
        public final boolean isActive;
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

        public DrawerAction(String actionText, boolean isActive, Runnable actionSelectedRunnable){
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
