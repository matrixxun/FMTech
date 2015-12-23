package com.google.android.gms.internal;

import java.util.concurrent.Future;

@zzhb
public abstract class zzim
{
  volatile Thread zzKM;
  private boolean zzKN = false;
  private final Runnable zzx = new Runnable()
  {
    public final void run()
    {
      zzim.this.zzKM = Thread.currentThread();
      zzim.this.zzbB();
    }
  };
  
  public abstract void zzbB();
  
  public final Future zzhf()
  {
    if (this.zzKN) {
      return zzip.zza(1, this.zzx);
    }
    return zzip.zza(this.zzx);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzim
 * JD-Core Version:    0.7.0.1
 */