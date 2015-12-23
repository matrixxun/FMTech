package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Message;
import com.google.android.gms.common.internal.zzx;

public final class zznl<L>
{
  public volatile L mListener;
  private final zznl<L>.com.google.android.gms.internal.zznl.com.google.android.gms.internal.zznl.com.google.android.gms.internal.zznl.zza zzaqs;
  
  public final void zza(zzb<? super L> paramzzb)
  {
    zzx.zzb(paramzzb, "Notifier must not be null");
    Message localMessage = this.zzaqs.obtainMessage(1, paramzzb);
    this.zzaqs.sendMessage(localMessage);
  }
  
  private final class zza
    extends Handler
  {
    public final void handleMessage(Message paramMessage)
    {
      int i = 1;
      if (paramMessage.what == i) {}
      zznl.zzb localzzb;
      Object localObject;
      for (;;)
      {
        zzx.zzab(i);
        zznl localzznl = this.zzaqt;
        localzzb = (zznl.zzb)paramMessage.obj;
        localObject = localzznl.mListener;
        if (localObject != null) {
          break;
        }
        localzzb.zzoR();
        return;
        int j = 0;
      }
      try
      {
        localzzb.zzv(localObject);
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        localzzb.zzoR();
        throw localRuntimeException;
      }
    }
  }
  
  public static abstract interface zzb<L>
  {
    public abstract void zzoR();
    
    public abstract void zzv(L paramL);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznl
 * JD-Core Version:    0.7.0.1
 */