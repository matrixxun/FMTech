package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.BlockingQueue;

public final class zzc
  extends Thread
{
  private static final boolean DEBUG = zzs.DEBUG;
  private final BlockingQueue<zzk<?>> zzh;
  private final BlockingQueue<zzk<?>> zzi;
  private final zzb zzj;
  private final zzn zzk;
  volatile boolean zzl = false;
  
  public zzc(BlockingQueue<zzk<?>> paramBlockingQueue1, BlockingQueue<zzk<?>> paramBlockingQueue2, zzb paramzzb, zzn paramzzn)
  {
    this.zzh = paramBlockingQueue1;
    this.zzi = paramBlockingQueue2;
    this.zzj = paramzzb;
    this.zzk = paramzzn;
  }
  
  public final void run()
  {
    if (DEBUG) {
      zzs.zza("start new dispatcher", new Object[0]);
    }
    Process.setThreadPriority(10);
    this.zzj.zza();
    final zzk localzzk;
    label73:
    zzb.zza localzza;
    int i;
    for (;;)
    {
      try
      {
        localzzk = (zzk)this.zzh.take();
        localzzk.zzc("cache-queue-take");
        if (!localzzk.zzK) {
          break label73;
        }
        localzzk.zzd("cache-discard-canceled");
        continue;
        if (!this.zzl) {
          continue;
        }
      }
      catch (InterruptedException localInterruptedException) {}
      return;
      localzza = this.zzj.zza(localzzk.zzE);
      if (localzza == null)
      {
        localzzk.zzc("cache-miss");
        this.zzi.put(localzzk);
      }
      else
      {
        if (localzza.zze >= System.currentTimeMillis()) {
          break label261;
        }
        i = 1;
        label124:
        if (i == 0) {
          break;
        }
        localzzk.zzc("cache-hit-expired");
        localzzk.zzO = localzza;
        this.zzi.put(localzzk);
      }
    }
    localzzk.zzc("cache-hit");
    zzm localzzm = localzzk.zza(new zzi(localzza.data, localzza.zzg));
    localzzk.zzc("cache-hit-parsed");
    if (localzza.zzf < System.currentTimeMillis()) {}
    for (int j = 1;; j = 0)
    {
      if (j == 0)
      {
        this.zzk.zza(localzzk, localzzm);
        break;
      }
      localzzk.zzc("cache-hit-refresh-needed");
      localzzk.zzO = localzza;
      localzzm.zzah = true;
      this.zzk.zza(localzzk, localzzm, new Runnable()
      {
        public final void run()
        {
          try
          {
            zzc.zza(zzc.this).put(localzzk);
            return;
          }
          catch (InterruptedException localInterruptedException) {}
        }
      });
      break;
      label261:
      i = 0;
      break label124;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzc
 * JD-Core Version:    0.7.0.1
 */