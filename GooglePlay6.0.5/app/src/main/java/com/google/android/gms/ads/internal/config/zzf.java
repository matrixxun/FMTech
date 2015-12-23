package com.google.android.gms.ads.internal.config;

import android.content.SharedPreferences;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzja;
import java.util.concurrent.Callable;

@zzhb
public final class zzf
{
  boolean zzqM = false;
  final Object zzqp = new Object();
  SharedPreferences zzvG = null;
  
  public final <T> T zzd(final Flag<T> paramFlag)
  {
    synchronized (this.zzqp)
    {
      if (!this.zzqM)
      {
        Object localObject3 = paramFlag.zzvC;
        return localObject3;
      }
      zzja.zzb(new Callable()
      {
        public final T call()
        {
          return paramFlag.zza(zzf.this.zzvG);
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.config.zzf
 * JD-Core Version:    0.7.0.1
 */