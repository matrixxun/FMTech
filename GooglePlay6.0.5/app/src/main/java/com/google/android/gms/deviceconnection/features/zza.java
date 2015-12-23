package com.google.android.gms.deviceconnection.features;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;

public final class zza
  extends zzc
  implements DeviceFeature
{
  public zza(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }
  
  public final String getFeatureName()
  {
    return getString("feature_name");
  }
  
  public final long getLastConnectionTimestampMillis()
  {
    return getLong("last_connection_timestamp");
  }
  
  public final String toString()
  {
    return zzw.zzB(this).zzh("FeatureName", getString("feature_name")).zzh("Timestamp", Long.valueOf(getLong("last_connection_timestamp"))).toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.deviceconnection.features.zza
 * JD-Core Version:    0.7.0.1
 */