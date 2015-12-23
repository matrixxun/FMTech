package com.google.android.finsky.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public final class CachedLocationAccess
{
  public static Location getCachedLocation(Context paramContext)
  {
    try
    {
      LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
      Location localLocation1 = localLocationManager.getLastKnownLocation("gps");
      Location localLocation2 = localLocationManager.getLastKnownLocation("network");
      if ((localLocation1 != null) && (localLocation2 != null))
      {
        f1 = localLocation1.getAccuracy();
        f2 = localLocation2.getAccuracy();
        if (f1 >= f2) {}
      }
      while (localLocation1 != null)
      {
        float f1;
        float f2;
        return localLocation1;
        return localLocation2;
      }
      if (localLocation2 != null) {
        return localLocation2;
      }
      return null;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.e("No permission to get location. %s", new Object[] { localSecurityException });
    }
    return null;
  }
  
  public static abstract interface SimpleLocationListener
  {
    public abstract void onLocationUpdated(Location paramLocation);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.CachedLocationAccess
 * JD-Core Version:    0.7.0.1
 */