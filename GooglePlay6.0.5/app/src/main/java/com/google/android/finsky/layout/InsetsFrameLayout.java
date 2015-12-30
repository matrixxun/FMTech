package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;


public class InsetsFrameLayout extends FrameLayout implements InsetsAware {
  public static boolean SUPPORTS_IMMERSIVE_MODE = Build.VERSION.SDK_INT >= 0x15 ? true : false;

  public InsetsFrameLayout(Context context) {
    this(context, 0x0);
  }

  public InsetsFrameLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0x0);
  }

  public InsetsFrameLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @TargetApi(21)
  public WindowInsets onApplyWindowInsets(WindowInsets insets) {
    int childCount = getChildCount();
    boolean hasChildForWindowInsets = false;
    for(int i = 0x0; i < childCount; i = i + 0x1) {
      View child = getChildAt(i);
      if((child instanceof InsetsAware) && (((InsetsAware)child).shouldApplyWindowInsets())) {
        hasChildForWindowInsets = true;
        break;
      }
    }
    if(!hasChildForWindowInsets) {
      setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
      for(int i = 0x0; i < childCount; i = i + 0x1) {
        child = getChildAt(i);
        if(child.getId() == 0x7f1000cc) {
          child.setPadding(0x0, 0x0, 0x0, 0x0);
        }
      }
      return insets.consumeSystemWindowInsets();
    }
    setPadding(0x0, 0x0, 0x0, 0x0);
    for(int i = 0x0; i < childCount; i = i + 0x1) {
      child = getChildAt(i);
      if((isActionBar) || (wantsWindowInsets)) {
        child.dispatchApplyWindowInsets(insets);
      }
    }
    return insets.consumeSystemWindowInsets();
  }

  public final boolean shouldApplyWindowInsets() {
    int childCount = getChildCount();
    for(int i = 0x0; i < childCount; i = i + 0x1) {
      View child = getChildAt(i);
      if((child instanceof InsetsAware) && ((InsetsAware)child.shouldApplyWindowInsets())) {
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