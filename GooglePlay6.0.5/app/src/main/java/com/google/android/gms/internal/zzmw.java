package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.locks.Lock;

public final class zzmw
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  public final Api<?> zzaoO;
  private final int zzaoP;
  zznf zzaoQ;
  
  public zzmw(Api<?> paramApi, int paramInt)
  {
    this.zzaoO = paramApi;
    this.zzaoP = paramInt;
  }
  
  private void zzoJ()
  {
    zzx.zzb(this.zzaoQ, "Callbacks must be attached to a GoogleApiClient instance before connecting the client.");
  }
  
  public final void onConnected(Bundle paramBundle)
  {
    zzoJ();
    zznf localzznf = this.zzaoQ;
    localzznf.zzXP.lock();
    try
    {
      localzznf.zzaqk.onConnected(paramBundle);
      return;
    }
    finally
    {
      localzznf.zzXP.unlock();
    }
  }
  
  public final void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    zzoJ();
    zznf localzznf = this.zzaoQ;
    Api localApi = this.zzaoO;
    int i = this.zzaoP;
    localzznf.zzXP.lock();
    try
    {
      localzznf.zzaqk.zza(paramConnectionResult, localApi, i);
      return;
    }
    finally
    {
      localzznf.zzXP.unlock();
    }
  }
  
  public final void onConnectionSuspended(int paramInt)
  {
    zzoJ();
    this.zzaoQ.onConnectionSuspended(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmw
 * JD-Core Version:    0.7.0.1
 */