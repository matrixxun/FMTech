package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzh
  implements Clock
{
  private static zzh zzawJ;
  
  public static Clock zzrs()
  {
    try
    {
      if (zzawJ == null) {
        zzawJ = new zzh();
      }
      zzh localzzh = zzawJ;
      return localzzh;
    }
    finally {}
  }
  
  public final long currentTimeMillis()
  {
    return System.currentTimeMillis();
  }
  
  public final long elapsedRealtime()
  {
    return SystemClock.elapsedRealtime();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.util.zzh
 * JD-Core Version:    0.7.0.1
 */