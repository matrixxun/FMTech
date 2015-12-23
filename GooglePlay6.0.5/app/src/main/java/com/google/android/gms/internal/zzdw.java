package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzhb
public final class zzdw
  implements Iterable<zzdv>
{
  final List<zzdv> zzzM = new LinkedList();
  
  public static boolean zza(zzjo paramzzjo)
  {
    return zzc(paramzzjo) != null;
  }
  
  static zzdv zzc(zzjo paramzzjo)
  {
    Iterator localIterator = zzp.zzbW().iterator();
    while (localIterator.hasNext())
    {
      zzdv localzzdv = (zzdv)localIterator.next();
      if (localzzdv.zzpX == paramzzjo) {
        return localzzdv;
      }
    }
    return null;
  }
  
  public final Iterator<zzdv> iterator()
  {
    return this.zzzM.iterator();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdw
 * JD-Core Version:    0.7.0.1
 */