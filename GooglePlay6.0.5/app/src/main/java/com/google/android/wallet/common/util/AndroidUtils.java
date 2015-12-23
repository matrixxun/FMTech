package com.google.android.wallet.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

public final class AndroidUtils
{
  public static String getCallingPackage(Activity paramActivity)
  {
    ComponentName localComponentName = paramActivity.getCallingActivity();
    String str;
    if (localComponentName == null) {
      str = null;
    }
    do
    {
      return str;
      str = localComponentName.getPackageName();
    } while (str != null);
    Log.e("AndroidUtils", "getCallingPackage(): Couldn't get a package name from " + localComponentName);
    return null;
  }
  
  @TargetApi(17)
  public static DisplayMetrics getDisplayMetrics(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(localDisplayMetrics);
      return localDisplayMetrics;
    }
    return paramContext.getResources().getDisplayMetrics();
  }
  
  public static boolean isAccessibilityEnabled(Context paramContext)
  {
    return ((AccessibilityManager)paramContext.getSystemService("accessibility")).isEnabled();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.AndroidUtils
 * JD-Core Version:    0.7.0.1
 */