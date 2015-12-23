package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

public final class zze
{
  private final long zzawv;
  private final int zzaww;
  private final SimpleArrayMap<String, Long> zzawx;
  
  public zze()
  {
    this.zzawv = 60000L;
    this.zzaww = 10;
    this.zzawx = new SimpleArrayMap(10);
  }
  
  public zze(long paramLong)
  {
    this.zzawv = paramLong;
    this.zzaww = 1024;
    this.zzawx = new SimpleArrayMap();
  }
  
  public final Long zzcI(String paramString)
  {
    long l1 = SystemClock.elapsedRealtime();
    long l2 = this.zzawv;
    long l3 = l2;
    for (;;)
    {
      int i;
      try
      {
        if (this.zzawx.size() >= this.zzaww)
        {
          i = -1 + this.zzawx.size();
          if (i >= 0)
          {
            if (l1 - ((Long)this.zzawx.valueAt(i)).longValue() > l3) {
              this.zzawx.removeAt(i);
            }
          }
          else
          {
            long l4 = l3 / 2L;
            Log.w("ConnectionTracker", "The max capacity " + this.zzaww + " is not enough. Current durationThreshold is: " + l4);
            l3 = l4;
          }
        }
        else
        {
          Long localLong = (Long)this.zzawx.put(paramString, Long.valueOf(l1));
          return localLong;
        }
      }
      finally {}
      i--;
    }
  }
  
  public final boolean zzcJ(String paramString)
  {
    for (;;)
    {
      try
      {
        if (this.zzawx.remove(paramString) != null)
        {
          bool = true;
          return bool;
        }
      }
      finally {}
      boolean bool = false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zze
 * JD-Core Version:    0.7.0.1
 */