package com.fmtech.empf.ui.fragments;

import com.fmtech.empf.image.BitmapLoader;
import com.fmtech.empf.ui.component.actionbar.ActionBarController;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 12:11
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 12:11  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public interface PageFragmentHost {

    public abstract ActionBarController getActionBarController();

    public abstract BitmapLoader getBitmapLoader();

//    public abstract DfeApi getDfeApi(String paramString);

    public abstract NavigationManager getNavigationManager();

//    public abstract GoogleApiClient getPeopleClient();

    public abstract void goBack();

//    public abstract void hideSatisfactionSurveyV2();

//    public abstract void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment);

//    public abstract void maybeShowSatisfactionSurveyV2(Survey paramSurvey);

    public abstract void overrideSearchBoxWidth(int paramInt);

    public abstract void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean);

    public abstract void switchToRegularActionBar();

//    public abstract void switchToSearchBoxOnlyActionBar(int paramInt);

    public abstract void updateActionBarTitle(String paramString);

    public abstract void updateCurrentBackendId(int paramInt, boolean paramBoolean);
}
