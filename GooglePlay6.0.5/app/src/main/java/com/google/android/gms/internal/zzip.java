package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@zzhb
public final class zzip
{
  private static final ExecutorService zzKT = Executors.newFixedThreadPool(10, zzay("Default"));
  private static final ExecutorService zzKU = Executors.newFixedThreadPool(5, zzay("Loader"));
  
  public static zzjf<Void> zza(int paramInt, Runnable paramRunnable)
  {
    if (paramInt == 1) {
      zza(zzKU, new Callable() {});
    }
    zza(zzKT, new Callable() {});
  }
  
  public static zzjf<Void> zza(Runnable paramRunnable)
  {
    return zza(0, paramRunnable);
  }
  
  private static <T> zzjf<T> zza(ExecutorService paramExecutorService, final Callable<T> paramCallable)
  {
    localzzjc = new zzjc();
    try
    {
      Runnable local4 = new Runnable()
      {
        public final void run()
        {
          try
          {
            Process.setThreadPriority(10);
            this.zzKW.zzh(paramCallable.call());
            return;
          }
          catch (Exception localException)
          {
            zzp.zzbL().zzb(localException, true);
            this.zzKW.cancel(true);
          }
        }
      }
      {
        public final void run()
        {
          if (this.zzKW.isCancelled()) {
            this.zzKY.cancel(true);
          }
        }
      };
      zzjg localzzjg = localzzjc.zzMd;
      synchronized (localzzjg.zzMl)
      {
        if (localzzjg.zzMo)
        {
          zzjg.zze(local4);
          return localzzjc;
        }
        localzzjg.zzMn.add(local4);
      }
      return localzzjc;
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      zzb.w("Thread execution is rejected.", localRejectedExecutionException);
      localzzjc.cancel(true);
    }
  }
  
  private static ThreadFactory zzay(String paramString)
  {
    new ThreadFactory()
    {
      private final AtomicInteger zzKZ = new AtomicInteger(1);
      
      public final Thread newThread(Runnable paramAnonymousRunnable)
      {
        return new Thread(paramAnonymousRunnable, "AdWorker(" + this.zzLa + ") #" + this.zzKZ.getAndIncrement());
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzip
 * JD-Core Version:    0.7.0.1
 */