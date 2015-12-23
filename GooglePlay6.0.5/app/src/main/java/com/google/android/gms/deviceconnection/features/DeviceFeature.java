package com.google.android.gms.deviceconnection.features;

public abstract interface DeviceFeature
{
  public abstract String getFeatureName();
  
  public abstract long getLastConnectionTimestampMillis();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.deviceconnection.features.DeviceFeature
 * JD-Core Version:    0.7.0.1
 */