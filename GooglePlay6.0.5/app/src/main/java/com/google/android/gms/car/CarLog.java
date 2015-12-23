package com.google.android.gms.car;

import android.util.Log;

public final class CarLog
{
  public static boolean sForceLogs;
  
  public static final boolean isLoggable(String paramString, int paramInt)
  {
    return ((sForceLogs) && (paramInt >= 3)) || (Log.isLoggable(paramString, paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarLog
 * JD-Core Version:    0.7.0.1
 */