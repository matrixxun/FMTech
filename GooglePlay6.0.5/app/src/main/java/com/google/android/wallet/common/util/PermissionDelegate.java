package com.google.android.wallet.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public final class PermissionDelegate
{
  private static PermissionDelegate sDelegate;
  
  public static boolean callerAndSelfHavePermission(Activity paramActivity, String paramString)
  {
    if (selfHasPermission(paramActivity, paramString))
    {
      getInstance();
      getInstance();
      if (hasPermission(paramActivity, AndroidUtils.getCallingPackage(paramActivity), paramString)) {
        return true;
      }
    }
    return false;
  }
  
  public static PermissionDelegate getInstance()
  {
    if (sDelegate == null) {
      sDelegate = new PermissionDelegate();
    }
    return sDelegate;
  }
  
  public static boolean hasAnyLocationPermission(Context paramContext, String paramString)
  {
    return (hasPermission(paramContext, paramString, "android.permission.ACCESS_COARSE_LOCATION")) || (hasPermission(paramContext, paramString, "android.permission.ACCESS_FINE_LOCATION"));
  }
  
  private static boolean hasPermission(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getPackageManager().checkPermission(paramString2, paramString1) == 0;
  }
  
  public static boolean selfHasAnyLocationPermission(Context paramContext)
  {
    getInstance();
    return hasAnyLocationPermission(paramContext, paramContext.getPackageName());
  }
  
  public static boolean selfHasPermission(Context paramContext, String paramString)
  {
    getInstance();
    return hasPermission(paramContext, paramContext.getPackageName(), paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.PermissionDelegate
 * JD-Core Version:    0.7.0.1
 */