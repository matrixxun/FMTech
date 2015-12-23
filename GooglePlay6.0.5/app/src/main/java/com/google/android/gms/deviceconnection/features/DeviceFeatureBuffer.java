package com.google.android.gms.deviceconnection.features;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class DeviceFeatureBuffer
  extends AbstractDataBuffer<DeviceFeature>
{
  public DeviceFeatureBuffer(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  public final DeviceFeature get(int paramInt)
  {
    return new zza(this.zzapd, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.deviceconnection.features.DeviceFeatureBuffer
 * JD-Core Version:    0.7.0.1
 */