package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;

final class zza
{
  final String mAppVersion;
  final String zzbkS;
  final String zzblS;
  final String zzblT;
  final String zzblU;
  final long zzblV;
  final long zzblW;
  final String zzblX;
  final long zzblY;
  final long zzblZ;
  final boolean zzbma;
  
  zza(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, String paramString5, String paramString6, long paramLong3, long paramLong4, boolean paramBoolean)
  {
    zzx.zzcG(paramString1);
    if (paramLong1 >= 0L) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzab(bool);
      this.zzbkS = paramString1;
      this.zzblS = paramString2;
      if (TextUtils.isEmpty(paramString3)) {
        paramString3 = null;
      }
      this.zzblT = paramString3;
      this.zzblU = paramString4;
      this.zzblV = paramLong1;
      this.zzblW = paramLong2;
      this.mAppVersion = paramString5;
      this.zzblX = paramString6;
      this.zzblY = paramLong3;
      this.zzblZ = paramLong4;
      this.zzbma = paramBoolean;
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zza
 * JD-Core Version:    0.7.0.1
 */