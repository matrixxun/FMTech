package com.google.android.gms.wearable.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.wearable.DataApi.DataListener;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class zzce
  extends zzj<zzbb>
{
  final ExecutorService zzbQV = Executors.newCachedThreadPool();
  private final zzbl<Object> zzchA = new zzbl();
  private final zzbl<DataApi.DataListener> zzchB = new zzbl();
  private final zzbl<Object> zzchC = new zzbl();
  private final zzbl<Object> zzchD = new zzbl();
  private final zzbl<Object> zzchE = new zzbl();
  private final zzbl<Object> zzchF = new zzbl();
  private final zzbl<Object> zzchx = new zzbl();
  private final zzbl<Object> zzchy = new zzbl();
  private final zzbl<Object> zzchz = new zzbl();
  
  public zzce(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zzf paramzzf)
  {
    super(paramContext, paramLooper, 14, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  public final void disconnect()
  {
    this.zzchx.zzb(this);
    this.zzchy.zzb(this);
    this.zzchz.zzb(this);
    this.zzchA.zzb(this);
    this.zzchB.zzb(this);
    this.zzchC.zzb(this);
    this.zzchD.zzb(this);
    this.zzchE.zzb(this);
    this.zzchF.zzb(this);
    super.disconnect();
  }
  
  protected final void zza(int paramInt1, IBinder paramIBinder, Bundle paramBundle, int paramInt2)
  {
    if (Log.isLoggable("WearableClient", 2)) {
      Log.d("WearableClient", "onPostInitHandler: statusCode " + paramInt1);
    }
    if (paramInt1 == 0)
    {
      this.zzchx.zzid(paramIBinder);
      this.zzchy.zzid(paramIBinder);
      this.zzchz.zzid(paramIBinder);
      this.zzchA.zzid(paramIBinder);
      this.zzchB.zzid(paramIBinder);
      this.zzchC.zzid(paramIBinder);
      this.zzchD.zzid(paramIBinder);
      this.zzchE.zzid(paramIBinder);
      this.zzchF.zzid(paramIBinder);
    }
    super.zza(paramInt1, paramIBinder, paramBundle, paramInt2);
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.wearable.BIND";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.wearable.internal.IWearableService";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzce
 * JD-Core Version:    0.7.0.1
 */