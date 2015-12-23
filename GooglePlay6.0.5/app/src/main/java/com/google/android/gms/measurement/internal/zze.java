package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;

abstract class zze
{
  private static volatile Handler zzQu;
  volatile long zzQv;
  private final zzt zzbkM;
  private boolean zzbmm;
  private final Runnable zzx;
  
  zze(zzt paramzzt)
  {
    zzx.zzC(paramzzt);
    this.zzbkM = paramzzt;
    this.zzbmm = true;
    this.zzx = new Runnable()
    {
      public final void run()
      {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
          zze.zza(zze.this).zzBY().zzg(this);
          return;
        }
        if (zze.this.zzQv != 0L) {}
        for (int i = 1;; i = 0)
        {
          zze.zza$5e6c6ec4(zze.this);
          if ((i == 0) || (!zze.zzb(zze.this))) {
            break;
          }
          zze.this.run();
          return;
        }
      }
    };
  }
  
  private Handler getHandler()
  {
    if (zzQu != null) {
      return zzQu;
    }
    try
    {
      if (zzQu == null) {
        zzQu = new Handler(this.zzbkM.mContext.getMainLooper());
      }
      Handler localHandler = zzQu;
      return localHandler;
    }
    finally {}
  }
  
  public final void cancel()
  {
    this.zzQv = 0L;
    getHandler().removeCallbacks(this.zzx);
  }
  
  public abstract void run();
  
  public final void zzr(long paramLong)
  {
    cancel();
    if (paramLong >= 0L)
    {
      this.zzQv = this.zzbkM.zzri.currentTimeMillis();
      if (!getHandler().postDelayed(this.zzx, paramLong)) {
        this.zzbkM.zzBh().zzbmW.zzm("Failed to schedule delayed post. time", Long.valueOf(paramLong));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zze
 * JD-Core Version:    0.7.0.1
 */