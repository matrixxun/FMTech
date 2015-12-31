package com.fmtech.empf.ui.component.layout;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 20:29
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 20:29  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class InsetsFrameLayout extends FrameLayout implements InsetsAware{
    public static boolean SUPPORTS_IMMERSIVE_MODE;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21
            SUPPORTS_IMMERSIVE_MODE = true;
        }else{
            SUPPORTS_IMMERSIVE_MODE = false;
        }
    }

    public InsetsFrameLayout(Context context) {
        this(context, null);
    }

    public InsetsFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 原理：
     * @param insets
     * @return
     */
   /* @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        //Returns a copy of this WindowInsets with the system window insets fully consumed.
        int childCount = getChildCount();
        boolean hasChildForWindowInsets = false;
        for(int i=0; i< childCount; i++){
            View child = getChildAt(i);
            if((child instanceof  InsetsAware)&&(((InsetsAware)child).shouldApplyWindowInsets())){
                hasChildForWindowInsets = true;
                break;
            }
        }
        if(!hasChildForWindowInsets){
            setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getStableInsetRight(), insets.getStableInsetRight());
            for(int i=0; i< childCount; i++){
                View child = getChildAt(i);
                if(child.getId() == R.id.toolbar){
//                    child.setPadding(0, 0 , 0 , 0);
                }
            }
            return insets.consumeSystemWindowInsets();
        }
        setPadding(0, 0 , 0 , 0);
        for(int i = 0x0; i < childCount; i = i + 0x1) {
            View child = getChildAt(i);
//            if((isActionBar) || (wantsWindowInsets)) {
//                child.dispatchApplyWindowInsets(insets);
//            }
        }
        return insets.consumeSystemWindowInsets();
        //return super.onApplyWindowInsets(insets);
    }*/


    @Override
    public boolean shouldApplyWindowInsets() {
        int childCount = getChildCount();
        View child;
        for(int i=0; i< childCount; i++){
            child = getChildAt(i);
            if((child instanceof  InsetsAware)&&(((InsetsAware)child).shouldApplyWindowInsets())){
                return true;
            }
        }
        return false;
    }
}
