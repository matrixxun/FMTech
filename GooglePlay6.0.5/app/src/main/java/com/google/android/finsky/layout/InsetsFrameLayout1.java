package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class InsetsFrameLayout1 extends FrameLayout implements InsetsAware {
    public static boolean SUPPORTS_IMMERSIVE_MODE;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            SUPPORTS_IMMERSIVE_MODE = true;
        }else{
            SUPPORTS_IMMERSIVE_MODE = false;
        }
    }

    public InsetsFrameLayout1(Context paramContext) {
        this(paramContext, null);
    }

    public InsetsFrameLayout1(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public InsetsFrameLayout1(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @TargetApi(21)
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
//        int childCount = getChildCount();
//        for (int j = 0; ; j++) {
//            int k = 0;
//            if (j < childCount) {
//                View view = getChildAt(j);
//                if (((view instanceof InsetsAware)) && (((InsetsAware) view).shouldApplyWindowInsets())) {
//                    k = 1;
//                }
//            } else {
//                if (k != 0) {
//                    break label124;
//                }
//                setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
//                for (int i2 = 0; i2 < childCount; i2++) {
//                    View localView2 = getChildAt(i2);
//                    if (localView2.getId() == 2131755212) {
//                        localView2.setPadding(0, 0, 0, 0);
//                    }
//                }
//            }
//        }
//        return insets.consumeSystemWindowInsets();
//        label124:
//        setPadding(0, 0, 0, 0);
//        int m = 0;
//        if (m < childCount) {
//            View localView1 = getChildAt(m);
//            int n;
//            if (localView1.getId() == 2131755212) {
//                n = 1;
//                label162:
//                if ((!(localView1 instanceof InsetsAware)) || (!((InsetsAware) localView1).shouldApplyWindowInsets())) {
//                    break label215;
//                }
//            }
//            label215:
//            for (int i1 = 1; ; i1 = 0) {
//                if ((n != 0) || (i1 != 0)) {
//                    localView1.dispatchApplyWindowInsets(insets);
//                }
//                m++;
//                break;
//                n = 0;
//                break label162;
//            }
//        }
//        return insets.consumeSystemWindowInsets();
        int childCount = getChildCount();
        boolean hasChildForWindowInsets = false;
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if((child instanceof InsetsAware) && (((InsetsAware)child).shouldApplyWindowInsets())) {
                hasChildForWindowInsets = true;
                break;
            }
        }
        if(!hasChildForWindowInsets) {
            setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            for(int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if(child.getId() == 0x7f1000cc) {//2131755212
                    child.setPadding(0, 0, 0, 0);
                }
            }
            return insets.consumeSystemWindowInsets();
        }
        setPadding(0, 0, 0, 0);
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if((isActionBar) || (wantsWindowInsets)) {
                child.dispatchApplyWindowInsets(insets);
            }
        }
        return insets.consumeSystemWindowInsets();
    }

    public final boolean shouldApplyWindowInsets() {
        int childCount = getChildCount();
        for (int j = 0; j < childCount; j++) {
            View child = getChildAt(j);
            if (((child instanceof InsetsAware)) && (((InsetsAware) child).shouldApplyWindowInsets())) {
                return true;
            }
        }
        return false;
    }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.layout.InsetsFrameLayout

 * JD-Core Version:    0.7.0.1

 */