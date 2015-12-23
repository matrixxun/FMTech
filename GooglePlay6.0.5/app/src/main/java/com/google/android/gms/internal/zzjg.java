package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.ads.internal.util.client.zza;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@zzhb
final class zzjg
{
  final Object zzMl = new Object();
  private final List<Runnable> zzMm = new ArrayList();
  final List<Runnable> zzMn = new ArrayList();
  boolean zzMo = false;
  
  static void zze(Runnable paramRunnable)
  {
    zza.zzLX.post(paramRunnable);
  }
  
  public final void zzhB()
  {
    synchronized (this.zzMl)
    {
      if (this.zzMo) {
        return;
      }
      Iterator localIterator1 = this.zzMm.iterator();
      if (localIterator1.hasNext()) {
        zzip.zza((Runnable)localIterator1.next());
      }
    }
    Iterator localIterator2 = this.zzMn.iterator();
    while (localIterator2.hasNext()) {
      zze((Runnable)localIterator2.next());
    }
    this.zzMm.clear();
    this.zzMn.clear();
    this.zzMo = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjg
 * JD-Core Version:    0.7.0.1
 */