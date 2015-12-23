package com.google.android.libraries.bind.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

public final class Util
{
  private static Context appContext;
  private static int memoryClass = -1;
  
  public static void checkPrecondition(boolean paramBoolean)
  {
    if (!paramBoolean) {
      throw new IllegalStateException();
    }
  }
  
  public static void checkPrecondition(boolean paramBoolean, String paramString)
  {
    if (!paramBoolean) {
      throw new IllegalStateException(paramString);
    }
  }
  
  public static String getResourceName(int paramInt)
  {
    if (appContext != null) {
      try
      {
        String str = appContext.getResources().getResourceEntryName(paramInt);
        return str;
      }
      catch (Resources.NotFoundException localNotFoundException) {}
    }
    return Integer.toString(paramInt);
  }
  
  public static boolean isLowMemoryDevice()
  {
    if (memoryClass == -1)
    {
      ActivityManager localActivityManager = (ActivityManager)appContext.getSystemService("activity");
      if (localActivityManager != null) {
        memoryClass = localActivityManager.getMemoryClass();
      }
    }
    return memoryClass < 96;
  }
  
  public static boolean objectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return paramObject2 == null;
    }
    return paramObject1.equals(paramObject2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.util.Util
 * JD-Core Version:    0.7.0.1
 */