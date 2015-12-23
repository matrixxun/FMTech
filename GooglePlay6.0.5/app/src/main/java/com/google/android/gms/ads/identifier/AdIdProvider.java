package com.google.android.gms.ads.identifier;

public abstract interface AdIdProvider
{
  public abstract String getAdIdBlocking();
  
  public abstract String getPublicAndroidId();
  
  public abstract Boolean isLimitAdTrackingEnabled();
  
  public abstract void refreshCachedData(boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.identifier.AdIdProvider
 * JD-Core Version:    0.7.0.1
 */