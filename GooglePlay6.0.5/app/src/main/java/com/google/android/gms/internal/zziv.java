package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzhb
public final class zziv
{
  static zzl zzLD;
  public static final zza<Void> zzLE = new zza() {};
  private static final Object zzqK = new Object();
  
  public zziv(Context paramContext)
  {
    zzLD = zzS(paramContext);
  }
  
  private static zzl zzS(Context paramContext)
  {
    synchronized (zzqK)
    {
      if (zzLD == null) {
        zzLD = zzac.zza$575a9856(paramContext.getApplicationContext());
      }
      zzl localzzl = zzLD;
      return localzzl;
    }
  }
  
  public static abstract interface zza<T> {}
  
  private final class zzc<T>
    extends zzjc<T>
    implements zzm.zzb<T>
  {
    private zzc() {}
    
    public final void zzb(T paramT)
    {
      super.zzh(paramT);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zziv
 * JD-Core Version:    0.7.0.1
 */