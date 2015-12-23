package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public final class zzad<T extends IInterface>
  extends zzj<T>
{
  private final Api.zzd<T> zzauJ;
  
  public zzad(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zzf paramzzf, Api.zzd paramzzd)
  {
    super(paramContext, paramLooper, paramInt, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    this.zzauJ = paramzzd;
  }
  
  protected final T zzaa(IBinder paramIBinder)
  {
    return this.zzauJ.zzaa$13514312();
  }
  
  protected final String zzgp()
  {
    return this.zzauJ.zzgp();
  }
  
  protected final String zzgq()
  {
    return this.zzauJ.zzgq();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzad
 * JD-Core Version:    0.7.0.1
 */