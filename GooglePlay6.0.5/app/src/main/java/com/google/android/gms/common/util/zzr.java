package com.google.android.gms.common.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import java.util.Iterator;
import java.util.List;

public final class zzr
{
  public static String zzi(Context paramContext, int paramInt)
  {
    List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.pid == paramInt) {
          return localRunningAppProcessInfo.processName;
        }
      }
    }
    return null;
  }
  
  public static String zzx$13d12155(int paramInt)
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramInt + 3;
    int j = 3;
    if (j < i)
    {
      if (j + 4 >= arrayOfStackTraceElement.length) {}
      StackTraceElement localStackTraceElement;
      for (String str = "<bottom of call stack>";; str = localStackTraceElement.getClassName() + "." + localStackTraceElement.getMethodName() + ":" + localStackTraceElement.getLineNumber())
      {
        localStringBuffer.append(str).append(" ");
        j++;
        break;
        localStackTraceElement = arrayOfStackTraceElement[(j + 4)];
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.util.zzr
 * JD-Core Version:    0.7.0.1
 */