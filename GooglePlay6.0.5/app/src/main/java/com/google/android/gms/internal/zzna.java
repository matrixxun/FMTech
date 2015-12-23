package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Map;

public final class zzna
  implements zzne
{
  private final zznf zzape;
  
  public zzna(zznf paramzznf)
  {
    this.zzape = paramzznf;
  }
  
  public final void begin() {}
  
  public final void connect() {}
  
  public final void disconnect()
  {
    this.zzape.zzj(null);
  }
  
  public final void onConnected(Bundle paramBundle) {}
  
  public final void onConnectionSuspended(int paramInt)
  {
    this.zzape.zzj(null);
    this.zzape.zzaqn.zzcM(paramInt);
  }
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    return zzb(paramT);
  }
  
  public final void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt) {}
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    try
    {
      this.zzape.zzaoR.zzb(paramT);
      Api.zzb localzzb = this.zzape.zzaoR.zza(paramT.zzor());
      if ((!localzzb.isConnected()) && (this.zzape.zzaqj.containsKey(paramT.zzor())))
      {
        paramT.zzE(new Status(17));
        return paramT;
      }
      paramT.zzb(localzzb);
      return paramT;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      this.zzape.zza(new zznf.zza(this)
      {
        public final void zzoS()
        {
          zzna.this.onConnectionSuspended(1);
        }
      });
    }
    return paramT;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzna
 * JD-Core Version:    0.7.0.1
 */