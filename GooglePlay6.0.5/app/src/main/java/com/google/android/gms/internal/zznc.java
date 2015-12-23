package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zznc
  implements zzne
{
  private final zznf zzape;
  
  public zznc(zznf paramzznf)
  {
    this.zzape = paramzznf;
  }
  
  public final void begin()
  {
    Iterator localIterator = this.zzape.zzapQ.values().iterator();
    while (localIterator.hasNext()) {
      ((Api.zzb)localIterator.next()).disconnect();
    }
    this.zzape.zzaoR.zzapR = Collections.emptySet();
  }
  
  public final void connect()
  {
    zznf localzznf = this.zzape;
    localzznf.zzXP.lock();
    try
    {
      localzznf.zzaqk = new zznb(localzznf, localzznf.zzapu, localzznf.zzapv, localzznf.zzaon, localzznf.zzaoo, localzznf.zzXP, localzznf.mContext);
      localzznf.zzaqk.begin();
      localzznf.zzaqh.signalAll();
      return;
    }
    finally
    {
      localzznf.zzXP.unlock();
    }
  }
  
  public final void disconnect()
  {
    this.zzape.zzaoR.zzpd();
  }
  
  public final void onConnected(Bundle paramBundle) {}
  
  public final void onConnectionSuspended(int paramInt) {}
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    this.zzape.zzaoR.zzapK.add(paramT);
    return paramT;
  }
  
  public final void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt) {}
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznc
 * JD-Core Version:    0.7.0.1
 */