package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class zzmv<R extends Result>
  extends PendingResult<R>
{
  private boolean zzK;
  final Object zzaoF = new Object();
  protected final zza<R> zzaoG;
  final ArrayList<PendingResult.zza> zzaoH = new ArrayList();
  private ResultCallback<? super R> zzaoI;
  volatile boolean zzaoJ;
  private boolean zzaoK;
  private zzq zzaoL;
  private Integer zzaoM;
  private volatile zznu<R> zzaoN;
  volatile R zzaov;
  private final CountDownLatch zzqd = new CountDownLatch(1);
  
  @Deprecated
  protected zzmv(Looper paramLooper)
  {
    this.zzaoG = new zza(paramLooper);
  }
  
  private R get()
  {
    boolean bool = true;
    synchronized (this.zzaoF)
    {
      if (!this.zzaoJ)
      {
        zzx.zza(bool, "Result has already been consumed.");
        zzx.zza(isReady(), "Result is not ready.");
        Result localResult = this.zzaov;
        this.zzaov = null;
        this.zzaoI = null;
        this.zzaoJ = true;
        zzoH();
        return localResult;
      }
      bool = false;
    }
  }
  
  private boolean isCanceled()
  {
    synchronized (this.zzaoF)
    {
      boolean bool = this.zzK;
      return bool;
    }
  }
  
  private void zzb(R paramR)
  {
    this.zzaov = paramR;
    this.zzaoL = null;
    this.zzqd.countDown();
    this.zzaov.getStatus();
    if (this.zzaoI != null)
    {
      this.zzaoG.removeMessages(2);
      if (!this.zzK) {
        this.zzaoG.zza(this.zzaoI, get());
      }
    }
    Iterator localIterator = this.zzaoH.iterator();
    while (localIterator.hasNext()) {
      ((PendingResult.zza)localIterator.next()).zzC$e184e5d();
    }
    this.zzaoH.clear();
  }
  
  public static void zzc(Result paramResult)
  {
    if ((paramResult instanceof Releasable)) {}
    try
    {
      ((Releasable)paramResult).release();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.w("BasePendingResult", "Unable to release " + paramResult, localRuntimeException);
    }
  }
  
  public final R await()
  {
    boolean bool1 = true;
    boolean bool2;
    if (Looper.myLooper() != Looper.getMainLooper()) {
      bool2 = bool1;
    }
    for (;;)
    {
      zzx.zza(bool2, "await must not be called on the UI thread");
      boolean bool3;
      if (!this.zzaoJ)
      {
        bool3 = bool1;
        label28:
        zzx.zza(bool3, "Result has already been consumed");
        if (this.zzaoN != null) {
          break label78;
        }
        zzx.zza(bool1, "Cannot await if then() has been called.");
      }
      try
      {
        this.zzqd.await();
        zzx.zza(isReady(), "Result is not ready.");
        return get();
        bool2 = false;
        continue;
        bool3 = false;
        break label28;
        label78:
        bool1 = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          zzF(Status.zzaoA);
        }
      }
    }
  }
  
  public final R await(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool1 = true;
    boolean bool2;
    if ((paramLong <= 0L) || (Looper.myLooper() != Looper.getMainLooper())) {
      bool2 = bool1;
    }
    for (;;)
    {
      zzx.zza(bool2, "await must not be called on the UI thread when time is greater than zero.");
      boolean bool3;
      if (!this.zzaoJ)
      {
        bool3 = bool1;
        label40:
        zzx.zza(bool3, "Result has already been consumed.");
        if (this.zzaoN != null) {
          break label106;
        }
        zzx.zza(bool1, "Cannot await if then() has been called.");
      }
      try
      {
        if (!this.zzqd.await(paramLong, paramTimeUnit)) {
          zzF(Status.zzaoC);
        }
        zzx.zza(isReady(), "Result is not ready.");
        return get();
        bool2 = false;
        continue;
        bool3 = false;
        break label40;
        label106:
        bool1 = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          zzF(Status.zzaoA);
        }
      }
    }
  }
  
  public final void cancel()
  {
    synchronized (this.zzaoF)
    {
      if ((this.zzK) || (this.zzaoJ)) {
        return;
      }
      zzq localzzq = this.zzaoL;
      if (localzzq == null) {}
    }
    try
    {
      this.zzaoL.cancel();
      label42:
      zzc(this.zzaov);
      this.zzaoI = null;
      this.zzK = true;
      zzb(zzb(Status.zzaoD));
      return;
      localObject2 = finally;
      throw localObject2;
    }
    catch (RemoteException localRemoteException)
    {
      break label42;
    }
  }
  
  public final boolean isReady()
  {
    return this.zzqd.getCount() == 0L;
  }
  
  public final void setResultCallback(ResultCallback<? super R> paramResultCallback)
  {
    boolean bool1 = true;
    boolean bool2;
    if (!this.zzaoJ)
    {
      bool2 = bool1;
      zzx.zza(bool2, "Result has already been consumed.");
    }
    for (;;)
    {
      synchronized (this.zzaoF)
      {
        if (this.zzaoN != null) {
          break label94;
        }
        zzx.zza(bool1, "Cannot set callbacks if then() has been called.");
        if (isCanceled()) {
          return;
        }
        if (isReady())
        {
          this.zzaoG.zza(paramResultCallback, get());
          return;
        }
      }
      this.zzaoI = paramResultCallback;
      continue;
      bool2 = false;
      break;
      label94:
      bool1 = false;
    }
  }
  
  public final void zzF(Status paramStatus)
  {
    synchronized (this.zzaoF)
    {
      if (!isReady())
      {
        zza(zzb(paramStatus));
        this.zzaoK = true;
      }
      return;
    }
  }
  
  public final void zza(PendingResult.zza paramzza)
  {
    if (!this.zzaoJ) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "Result has already been consumed.");
      zzx.zzb(true, "Callback cannot be null.");
      synchronized (this.zzaoF)
      {
        if (isReady())
        {
          this.zzaov.getStatus();
          paramzza.zzC$e184e5d();
          return;
        }
        this.zzaoH.add(paramzza);
      }
    }
  }
  
  public final void zza(R paramR)
  {
    boolean bool1 = true;
    for (;;)
    {
      synchronized (this.zzaoF)
      {
        if ((this.zzaoK) || (this.zzK))
        {
          zzc(paramR);
          return;
        }
        if (!isReady())
        {
          bool2 = bool1;
          zzx.zza(bool2, "Results have already been set");
          if (this.zzaoJ) {
            break label81;
          }
          zzx.zza(bool1, "Result has already been consumed");
          zzb(paramR);
          return;
        }
      }
      boolean bool2 = false;
      continue;
      label81:
      bool1 = false;
    }
  }
  
  public abstract R zzb(Status paramStatus);
  
  public final Integer zzoC()
  {
    return this.zzaoM;
  }
  
  protected void zzoH() {}
  
  public static final class zza<R extends Result>
    extends Handler
  {
    public zza()
    {
      this(Looper.getMainLooper());
    }
    
    public zza(Looper paramLooper)
    {
      super();
    }
    
    public final void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        Log.wtf("BasePendingResult", "Don't know how to handle message: " + paramMessage.what, new Exception());
        return;
      case 1: 
        Pair localPair = (Pair)paramMessage.obj;
        ResultCallback localResultCallback = (ResultCallback)localPair.first;
        Result localResult = (Result)localPair.second;
        try
        {
          localResultCallback.onResult(localResult);
          return;
        }
        catch (RuntimeException localRuntimeException)
        {
          zzmv.zzc(localResult);
          throw localRuntimeException;
        }
      }
      ((zzmv)paramMessage.obj).zzF(Status.zzaoC);
    }
    
    public final void zza(ResultCallback<? super R> paramResultCallback, R paramR)
    {
      sendMessage(obtainMessage(1, new Pair(paramResultCallback, paramR)));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmv
 * JD-Core Version:    0.7.0.1
 */