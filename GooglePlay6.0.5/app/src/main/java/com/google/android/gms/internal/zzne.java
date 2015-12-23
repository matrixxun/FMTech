package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;

public abstract interface zzne
{
  public abstract void begin();
  
  public abstract void connect();
  
  public abstract void disconnect();
  
  public abstract void onConnected(Bundle paramBundle);
  
  public abstract void onConnectionSuspended(int paramInt);
  
  public abstract <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT);
  
  public abstract void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt);
  
  public abstract <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzne
 * JD-Core Version:    0.7.0.1
 */