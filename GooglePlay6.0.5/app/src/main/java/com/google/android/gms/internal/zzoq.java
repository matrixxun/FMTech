package com.google.android.gms.internal;

import android.os.Process;

final class zzoq
  implements Runnable
{
  private final int mPriority;
  private final Runnable zzx;
  
  public zzoq(Runnable paramRunnable, int paramInt)
  {
    this.zzx = paramRunnable;
    this.mPriority = paramInt;
  }
  
  public final void run()
  {
    Process.setThreadPriority(this.mPriority);
    this.zzx.run();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzoq
 * JD-Core Version:    0.7.0.1
 */