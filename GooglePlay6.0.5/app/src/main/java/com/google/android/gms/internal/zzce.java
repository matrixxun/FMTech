package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.util.Clock;

@zzhb
public final class zzce
{
  public static boolean zza(zzci paramzzci, zzcg paramzzcg, String... paramVarArgs)
  {
    if ((paramzzci == null) || (paramzzcg == null)) {}
    while ((!paramzzci.zzxl) || (paramzzcg == null)) {
      return false;
    }
    return paramzzci.zza(paramzzcg, zzp.zzbM().elapsedRealtime(), paramVarArgs);
  }
  
  public static zzcg zzb(zzci paramzzci)
  {
    if (paramzzci == null) {
      return null;
    }
    return paramzzci.zzf(zzp.zzbM().elapsedRealtime());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzce
 * JD-Core Version:    0.7.0.1
 */