package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.internal.reward.client.zzf;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.internal.zzdc;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzl
{
  private static final Object zzqK = new Object();
  private static zzl zzuI;
  private final zza zzuJ = new zza();
  private final zze zzuK = new zze();
  private final zzad zzuL = new zzad();
  private final zzdc zzuM = new zzdc();
  private final zzf zzuN = new zzf();
  
  static
  {
    zzl localzzl = new zzl();
    synchronized (zzqK)
    {
      zzuI = localzzl;
      return;
    }
  }
  
  private static zzl zzcW()
  {
    synchronized (zzqK)
    {
      zzl localzzl = zzuI;
      return localzzl;
    }
  }
  
  public static zza zzcX()
  {
    return zzcW().zzuJ;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzl
 * JD-Core Version:    0.7.0.1
 */