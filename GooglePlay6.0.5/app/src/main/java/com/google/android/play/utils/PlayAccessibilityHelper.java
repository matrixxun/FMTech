package com.google.android.play.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

public final class PlayAccessibilityHelper
{
  @TargetApi(16)
  public static void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString)
  {
    if ((Build.VERSION.SDK_INT >= 16) && (!TextUtils.isEmpty(paramString))) {
      paramAccessibilityNodeInfo.setClassName(paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.PlayAccessibilityHelper
 * JD-Core Version:    0.7.0.1
 */