package com.google.android.finsky.services;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.identifier.AdvertisingIdListenerService;

public class AdIdListener
  extends AdvertisingIdListenerService
{
  public final void onAdvertisingIdInfoChanged(AdvertisingIdClient.Info paramInfo)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInfo.zzpp.hashCode());
    arrayOfObject[1] = Boolean.valueOf(paramInfo.zzpq);
    FinskyLog.d("AdId change: id (hash)=%d limit=%b", arrayOfObject);
  }
  
  public void onCreate()
  {
    super.onCreate();
    FinskyLog.d("AdId refresh", new Object[0]);
    FinskyApp.get().mAdIdProvider.refreshCachedData(true);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.AdIdListener
 * JD-Core Version:    0.7.0.1
 */