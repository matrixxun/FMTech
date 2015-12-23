package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.deviceconnection.DeviceConnections.GetDeviceFeaturesResult;
import com.google.android.gms.deviceconnection.features.DeviceFeatureBuffer;

public final class zzot
  extends zzj<zzov>
{
  public zzot(Context paramContext, Looper paramLooper, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 20, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.deviceconnection.service.START";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.deviceconnection.internal.IDeviceConnectionService";
  }
  
  private static final class zza
    extends zzos
  {
    private final zzmu.zzb<DeviceConnections.GetDeviceFeaturesResult> zzaqB;
    
    public zza(zzmu.zzb<DeviceConnections.GetDeviceFeaturesResult> paramzzb)
    {
      this.zzaqB = ((zzmu.zzb)zzx.zzb(paramzzb, "Holder must not be null"));
    }
    
    public final void zza(DataHolder paramDataHolder)
    {
      this.zzaqB.zzu(new zzot.zzb(paramDataHolder));
    }
  }
  
  private static final class zzb
    implements DeviceConnections.GetDeviceFeaturesResult
  {
    private final Status zzUc;
    private final DeviceFeatureBuffer zzaAT;
    
    public zzb(DataHolder paramDataHolder)
    {
      this.zzUc = new Status(paramDataHolder.zzakr);
      this.zzaAT = new DeviceFeatureBuffer(paramDataHolder);
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
    
    public final DeviceFeatureBuffer getSummaries()
    {
      return this.zzaAT;
    }
    
    public final void release()
    {
      this.zzaAT.release();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzot
 * JD-Core Version:    0.7.0.1
 */