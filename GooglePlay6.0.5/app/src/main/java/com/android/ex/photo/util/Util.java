package com.android.ex.photo.util;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;

public final class Util
{
  public static boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      return paramAccessibilityManager.isTouchExplorationEnabled();
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.util.Util
 * JD-Core Version:    0.7.0.1
 */