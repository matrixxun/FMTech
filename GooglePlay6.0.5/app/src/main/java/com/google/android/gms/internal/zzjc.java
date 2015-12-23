package com.google.android.gms.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzhb
public class zzjc<T>
  implements zzjf<T>
{
  private boolean zzBY = false;
  private T zzMb = null;
  private boolean zzMc = false;
  final zzjg zzMd = new zzjg();
  private final Object zzqp = new Object();
  
  public boolean cancel(boolean paramBoolean)
  {
    if (!paramBoolean) {
      return false;
    }
    synchronized (this.zzqp)
    {
      if (this.zzMc) {
        return false;
      }
    }
    this.zzBY = true;
    this.zzMc = true;
    this.zzqp.notifyAll();
    this.zzMd.zzhB();
    return true;
  }
  
  public T get()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzMc;
      if (bool) {}
    }
    try
    {
      this.zzqp.wait();
      label23:
      if (this.zzBY)
      {
        throw new CancellationException("CallbackFuture was cancelled.");
        localObject2 = finally;
        throw localObject2;
      }
      Object localObject3 = this.zzMb;
      return localObject3;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label23;
    }
  }
  
  public T get(long paramLong, TimeUnit paramTimeUnit)
    throws TimeoutException
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzMc;
      if (!bool) {}
      try
      {
        long l = paramTimeUnit.toMillis(paramLong);
        if (l != 0L) {
          this.zzqp.wait(l);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        label43:
        Object localObject3;
        break label43;
      }
      if (!this.zzMc) {
        throw new TimeoutException("CallbackFuture timed out.");
      }
    }
    if (this.zzBY) {
      throw new CancellationException("CallbackFuture was cancelled.");
    }
    localObject3 = this.zzMb;
    return localObject3;
  }
  
  public boolean isCancelled()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzBY;
      return bool;
    }
  }
  
  public boolean isDone()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzMc;
      return bool;
    }
  }
  
  public final void zzh(T paramT)
  {
    synchronized (this.zzqp)
    {
      if (this.zzBY) {
        return;
      }
      if (this.zzMc) {
        throw new IllegalStateException("Provided CallbackFuture with multiple values.");
      }
    }
    this.zzMc = true;
    this.zzMb = paramT;
    this.zzqp.notifyAll();
    this.zzMd.zzhB();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjc
 * JD-Core Version:    0.7.0.1
 */