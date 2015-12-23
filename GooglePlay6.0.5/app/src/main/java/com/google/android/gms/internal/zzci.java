package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@zzhb
public final class zzci
{
  final Object zzqp = new Object();
  private final List<zzcg> zzxB = new LinkedList();
  private final Map<String, String> zzxC = new LinkedHashMap();
  zzci zzxF;
  boolean zzxl = true;
  
  public zzci(String paramString1, String paramString2)
  {
    this.zzxC.put("action", paramString1);
    this.zzxC.put("ad_format", paramString2);
  }
  
  public final boolean zza(zzcg paramzzcg, long paramLong, String... paramVarArgs)
  {
    synchronized (this.zzqp)
    {
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++)
      {
        zzcg localzzcg = new zzcg(paramLong, paramVarArgs[j], paramzzcg);
        this.zzxB.add(localzzcg);
      }
      return true;
    }
  }
  
  public final zzcg zzf(long paramLong)
  {
    if (!this.zzxl) {
      return null;
    }
    return new zzcg(paramLong, null, null);
  }
  
  public final void zzf(String paramString1, String paramString2)
  {
    if ((!this.zzxl) || (TextUtils.isEmpty(paramString2))) {}
    zzcc localzzcc;
    do
    {
      return;
      localzzcc = zzp.zzbL().zzgU();
    } while (localzzcc == null);
    for (;;)
    {
      synchronized (this.zzqp)
      {
        zzcf localzzcf1 = (zzcf)localzzcc.zzxq.get(paramString1);
        if (localzzcf1 != null)
        {
          localObject3 = localzzcf1;
          Map localMap = this.zzxC;
          localMap.put(paramString1, ((zzcf)localObject3).zze((String)localMap.get(paramString1), paramString2));
          return;
        }
      }
      zzcf localzzcf2 = zzcf.zzxu;
      Object localObject3 = localzzcf2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzci
 * JD-Core Version:    0.7.0.1
 */