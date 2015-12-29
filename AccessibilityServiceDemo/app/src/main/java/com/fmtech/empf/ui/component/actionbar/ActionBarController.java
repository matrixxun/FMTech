package com.fmtech.empf.ui.component.actionbar;

import android.support.v7.widget.Toolbar;

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

public interface ActionBarController {

    public abstract void disableActionBarOverlay();

    public abstract void disableStatusBarOverlay();

    public abstract void enableActionBarOverlay();

    public abstract void enableStatusBarOverlay();

    public abstract void enterActionBarSearchMode();

//    public abstract void enterActionBarSectionExpandedMode(CharSequence charSequence, TextSectionTranslatable textSectionTranslatable);

    public abstract void exitActionBarSearchMode();

    public abstract void exitActionBarSectionExpandedMode();

    public abstract Toolbar getToolbar();

    public abstract void setActionBarSearchModeListener(ActionBarSearchModeListener actionBarSearchModeListener);

    public abstract void setHomeAsUpIndicator(int param);

    public static abstract interface ActionBarSearchModeListener {
        public abstract void onEnterActionBarSearchMode();

        public abstract void onExitActionBarSearchMode();
    }
}
