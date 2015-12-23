package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;

final class zzaa
{
  long zzBV;
  final Clock zzri;
  
  public zzaa(Clock paramClock)
  {
    zzx.zzC(paramClock);
    this.zzri = paramClock;
  }
  
  public final void start()
  {
    this.zzBV = this.zzri.elapsedRealtime();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzaa
 * JD-Core Version:    0.7.0.1
 */