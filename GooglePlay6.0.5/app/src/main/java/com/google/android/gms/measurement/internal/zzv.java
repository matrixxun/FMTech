package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;

class zzv
{
  protected final zzt zzbkM;
  
  zzv(zzt paramzzt)
  {
    zzx.zzC(paramzzt);
    this.zzbkM = paramzzt;
  }
  
  public void checkOnWorkerThread()
  {
    this.zzbkM.zzBY().checkOnWorkerThread();
  }
  
  public Clock getClock()
  {
    return this.zzbkM.zzri;
  }
  
  public Context getContext()
  {
    return this.zzbkM.mContext;
  }
  
  public void zzBU()
  {
    this.zzbkM.zzBY().zzBU();
  }
  
  public zzm zzBV()
  {
    return this.zzbkM.zzBV();
  }
  
  public zzz zzBW()
  {
    zzt localzzt = this.zzbkM;
    zzt.zza(localzzt.zzboe);
    return localzzt.zzboe;
  }
  
  public zzae zzBX()
  {
    return this.zzbkM.zzBX();
  }
  
  public zzs zzBY()
  {
    return this.zzbkM.zzBY();
  }
  
  public zzr zzBZ()
  {
    return this.zzbkM.zzBZ();
  }
  
  public zzo zzBh()
  {
    return this.zzbkM.zzBh();
  }
  
  public zzc zzCa()
  {
    return this.zzbkM.zzbnW;
  }
  
  public void zziF()
  {
    if (zzc.isPackageSide()) {
      throw new IllegalStateException("Unexpected call on package side");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzv
 * JD-Core Version:    0.7.0.1
 */