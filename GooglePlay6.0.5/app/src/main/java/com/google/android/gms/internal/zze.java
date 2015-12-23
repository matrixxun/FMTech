package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

public final class zze
  implements zzn
{
  private final Executor zzs;
  
  public zze(final Handler paramHandler)
  {
    this.zzs = new Executor()
    {
      public final void execute(Runnable paramAnonymousRunnable)
      {
        paramHandler.post(paramAnonymousRunnable);
      }
    };
  }
  
  public final void zza(zzk<?> paramzzk, zzm<?> paramzzm)
  {
    zza(paramzzk, paramzzm, null);
  }
  
  public final void zza(zzk<?> paramzzk, zzm<?> paramzzm, Runnable paramRunnable)
  {
    paramzzk.zzL = true;
    paramzzk.zzc("post-response");
    this.zzs.execute(new zza(paramzzk, paramzzm, paramRunnable));
  }
  
  public final void zza(zzk<?> paramzzk, zzr paramzzr)
  {
    paramzzk.zzc("post-error");
    zzm localzzm = new zzm(paramzzr);
    this.zzs.execute(new zza(paramzzk, localzzm, null));
  }
  
  private final class zza
    implements Runnable
  {
    private final zzk zzv;
    private final zzm zzw;
    private final Runnable zzx;
    
    public zza(zzk paramzzk, zzm paramzzm, Runnable paramRunnable)
    {
      this.zzv = paramzzk;
      this.zzw = paramzzm;
      this.zzx = paramRunnable;
    }
    
    public final void run()
    {
      if (this.zzv.zzK) {
        this.zzv.zzd("canceled-at-delivery");
      }
      label134:
      for (;;)
      {
        return;
        int i;
        if (this.zzw.zzag == null)
        {
          i = 1;
          if (i == 0) {
            break label91;
          }
          this.zzv.zza(this.zzw.result);
          label50:
          if (!this.zzw.zzah) {
            break label124;
          }
          this.zzv.zzc("intermediate-response");
        }
        for (;;)
        {
          if (this.zzx == null) {
            break label134;
          }
          this.zzx.run();
          return;
          i = 0;
          break;
          label91:
          zzk localzzk = this.zzv;
          zzr localzzr = this.zzw.zzag;
          if (localzzk.zzG == null) {
            break label50;
          }
          localzzk.zzG.zze(localzzr);
          break label50;
          label124:
          this.zzv.zzd("done");
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zze
 * JD-Core Version:    0.7.0.1
 */