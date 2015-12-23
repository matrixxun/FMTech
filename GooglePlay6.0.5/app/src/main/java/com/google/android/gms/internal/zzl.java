package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzl
{
  private AtomicInteger zzX = new AtomicInteger();
  final Map<String, Queue<zzk<?>>> zzY = new HashMap();
  final Set<zzk<?>> zzZ = new HashSet();
  final PriorityBlockingQueue<zzk<?>> zzaa = new PriorityBlockingQueue();
  private final PriorityBlockingQueue<zzk<?>> zzab = new PriorityBlockingQueue();
  private zzg[] zzac;
  private zzc zzad;
  List<Object> zzae = new ArrayList();
  private final zzb zzj;
  private final zzn zzk;
  private final zzf zzz;
  
  private zzl(zzb paramzzb, zzf paramzzf)
  {
    this(paramzzb, paramzzf, 4, new zze(new Handler(Looper.getMainLooper())));
  }
  
  public zzl(zzb paramzzb, zzf paramzzf, byte paramByte)
  {
    this(paramzzb, paramzzf);
  }
  
  private zzl(zzb paramzzb, zzf paramzzf, int paramInt, zzn paramzzn)
  {
    this.zzj = paramzzb;
    this.zzz = paramzzf;
    this.zzac = new zzg[4];
    this.zzk = paramzzn;
  }
  
  public final void start()
  {
    int i = 0;
    if (this.zzad != null)
    {
      zzc localzzc = this.zzad;
      localzzc.zzl = true;
      localzzc.interrupt();
    }
    for (int j = 0; j < this.zzac.length; j++) {
      if (this.zzac[j] != null)
      {
        zzg localzzg2 = this.zzac[j];
        localzzg2.zzl = true;
        localzzg2.interrupt();
      }
    }
    this.zzad = new zzc(this.zzaa, this.zzab, this.zzj, this.zzk);
    this.zzad.start();
    while (i < this.zzac.length)
    {
      zzg localzzg1 = new zzg(this.zzab, this.zzz, this.zzj, this.zzk);
      this.zzac[i] = localzzg1;
      localzzg1.start();
      i++;
    }
  }
  
  public final <T> zzk<T> zze(zzk<T> paramzzk)
  {
    paramzzk.zzI = this;
    synchronized (this.zzZ)
    {
      this.zzZ.add(paramzzk);
      paramzzk.zzH = Integer.valueOf(this.zzX.incrementAndGet());
      paramzzk.zzc("add-to-queue");
      if (!paramzzk.zzJ)
      {
        this.zzab.add(paramzzk);
        return paramzzk;
      }
    }
    for (;;)
    {
      String str;
      synchronized (this.zzY)
      {
        str = paramzzk.zzE;
        if (this.zzY.containsKey(str))
        {
          Object localObject3 = (Queue)this.zzY.get(str);
          if (localObject3 == null) {
            localObject3 = new LinkedList();
          }
          ((Queue)localObject3).add(paramzzk);
          this.zzY.put(str, localObject3);
          if (zzs.DEBUG) {
            zzs.zza("Request for cacheKey=%s is in flight, putting on hold.", new Object[] { str });
          }
          return paramzzk;
        }
      }
      this.zzY.put(str, null);
      this.zzaa.add(paramzzk);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzl
 * JD-Core Version:    0.7.0.1
 */