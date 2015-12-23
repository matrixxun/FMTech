package com.google.android.gms.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class zzng
{
  private static final ExecutorService zzaqq = Executors.newFixedThreadPool(2, new zzop("GAC_Executor"));
  
  public static ExecutorService zzpp()
  {
    return zzaqq;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzng
 * JD-Core Version:    0.7.0.1
 */