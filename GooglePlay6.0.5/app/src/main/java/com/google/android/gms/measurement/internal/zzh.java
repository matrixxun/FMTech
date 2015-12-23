package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzx;

final class zzh
{
  final String mName;
  final String zzbkS;
  final long zzbmt;
  final long zzbmu;
  final long zzbmv;
  
  zzh(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3)
  {
    zzx.zzcG(paramString1);
    zzx.zzcG(paramString2);
    boolean bool2;
    if (paramLong1 >= 0L)
    {
      bool2 = bool1;
      zzx.zzab(bool2);
      if (paramLong2 < 0L) {
        break label78;
      }
    }
    for (;;)
    {
      zzx.zzab(bool1);
      this.zzbkS = paramString1;
      this.mName = paramString2;
      this.zzbmt = paramLong1;
      this.zzbmu = paramLong2;
      this.zzbmv = paramLong3;
      return;
      bool2 = false;
      break;
      label78:
      bool1 = false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzh
 * JD-Core Version:    0.7.0.1
 */