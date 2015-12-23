package com.google.android.play.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class PlayUtils
{
  private static final Pattern COMMA_PATTERN = Pattern.compile(",");
  private static final String[] EMPTY_ARRAY = new String[0];
  
  @TargetApi(17)
  public static void forceLayoutDirectionToLtr(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      paramContext.getResources().getConfiguration().setLayoutDirection(Locale.US);
    }
  }
  
  public static int getSideMargins(View paramView)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams);
  }
  
  public static int getViewLeftFromParentEnd(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false) {
      return getViewLeftFromParentStart(paramInt1, paramInt2, bool, paramInt3);
    }
  }
  
  public static int getViewLeftFromParentStart(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if (paramBoolean) {
      return paramInt3;
    }
    return paramInt1 - paramInt3 - paramInt2;
  }
  
  public static boolean isTestDevice()
  {
    String str = Build.TAGS;
    String[] arrayOfString;
    if ((str == null) || (str.length() == 0)) {
      arrayOfString = EMPTY_ARRAY;
    }
    for (;;)
    {
      List localList = Arrays.asList(arrayOfString);
      if ((!localList.contains("dev-keys")) && (!localList.contains("test-keys"))) {
        break;
      }
      return true;
      if (str.indexOf(',') == -1) {
        arrayOfString = new String[] { str };
      } else {
        arrayOfString = COMMA_PATTERN.split(str);
      }
    }
    return false;
  }
  
  public static boolean isTv(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    return (localPackageManager.hasSystemFeature("com.google.android.tv")) || (localPackageManager.hasSystemFeature("android.hardware.type.television"));
  }
  
  @SuppressLint({"NewApi"})
  public static boolean useLtr(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 17) {}
    while (paramContext.getResources().getConfiguration().getLayoutDirection() == 0) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.PlayUtils
 * JD-Core Version:    0.7.0.1
 */