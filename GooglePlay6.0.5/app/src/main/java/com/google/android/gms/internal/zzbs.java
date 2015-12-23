package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.List;

@zzhb
public final class zzbs
{
  final Object zzqp;
  int zztr;
  List<zzbr> zzts;
  
  public final boolean zza(zzbr paramzzbr)
  {
    synchronized (this.zzqp)
    {
      return this.zzts.contains(paramzzbr);
    }
  }
  
  public final boolean zzb(zzbr paramzzbr)
  {
    synchronized (this.zzqp)
    {
      Iterator localIterator = this.zzts.iterator();
      while (localIterator.hasNext())
      {
        zzbr localzzbr = (zzbr)localIterator.next();
        if ((paramzzbr != localzzbr) && (localzzbr.zztq.equals(paramzzbr.zztq)))
        {
          localIterator.remove();
          return true;
        }
      }
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbs
 * JD-Core Version:    0.7.0.1
 */