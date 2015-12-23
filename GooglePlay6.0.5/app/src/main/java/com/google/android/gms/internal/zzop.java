package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzop
  implements ThreadFactory
{
  private final int mPriority;
  private final String zzawW;
  private final AtomicInteger zzawX = new AtomicInteger();
  private final ThreadFactory zzawY = Executors.defaultThreadFactory();
  
  public zzop(String paramString)
  {
    this(paramString, (byte)0);
  }
  
  private zzop(String paramString, byte paramByte)
  {
    this.zzawW = ((String)zzx.zzb(paramString, "Name must not be null"));
    this.mPriority = 0;
  }
  
  public final Thread newThread(Runnable paramRunnable)
  {
    Thread localThread = this.zzawY.newThread(new zzoq(paramRunnable, this.mPriority));
    localThread.setName(this.zzawW + "[" + this.zzawX.getAndIncrement() + "]");
    return localThread;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzop
 * JD-Core Version:    0.7.0.1
 */