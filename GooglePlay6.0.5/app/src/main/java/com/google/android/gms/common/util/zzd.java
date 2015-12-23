package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.regex.Pattern;

public final class zzd
{
  private static Pattern zzawI = null;
  
  public static boolean zzax(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
  }
  
  public static int zzdA(int paramInt)
  {
    return paramInt / 1000;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.util.zzd
 * JD-Core Version:    0.7.0.1
 */