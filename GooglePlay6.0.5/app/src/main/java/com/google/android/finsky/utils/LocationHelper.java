package com.google.android.finsky.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.play.utils.config.GservicesValue;

public final class LocationHelper
{
  static long sLastLocationRequestTimestampMs;
  static android.location.Location sLocation;
  static boolean sLocationRequestPending;
  private static boolean sLocationRequestPendingLogged;
  public final Context mContext;
  public LocationProviderChangedReceiver mLocationProviderChangeReceiver;
  private final int mMinLocationUpdateRangeMeters;
  
  public LocationHelper(Context paramContext)
  {
    this.mContext = paramContext;
    this.mMinLocationUpdateRangeMeters = ((Integer)G.minLocationUpdateRangeMeters.get()).intValue();
  }
  
  public static boolean areLocationSuggestionsEnabled()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    if (!localFinskyApp.getExperiments().isEnabled(12603100L)) {}
    Integer localInteger;
    do
    {
      do
      {
        PrivacySetting localPrivacySetting;
        do
        {
          return false;
          localInteger = (Integer)FinskyPreferences.locationSuggestionsEnabled.get(localFinskyApp.getCurrentAccountName()).get();
          DfeToc localDfeToc = localFinskyApp.mToc;
          if (localDfeToc == null) {
            return true;
          }
          localPrivacySetting = localDfeToc.getUserPrivacySetting(1);
        } while (localPrivacySetting == null);
        if (!localPrivacySetting.enabledByDefault) {
          break;
        }
      } while (localInteger.intValue() == 2);
      return true;
    } while ((localInteger.intValue() != 1) && (localInteger.intValue() != 3));
    return true;
  }
  
  public static void clearLocation()
  {
    sLocationRequestPending = false;
    sLocationRequestPendingLogged = false;
    sLocation = null;
  }
  
  public static com.google.android.finsky.protos.Location getLocationProto()
  {
    android.location.Location localLocation = sLocation;
    if (!areLocationSuggestionsEnabled()) {}
    do
    {
      return null;
      if (localLocation != null) {
        break;
      }
    } while ((!sLocationRequestPending) || (sLocationRequestPendingLogged));
    logLocationEvent(1004);
    sLocationRequestPendingLogged = true;
    return null;
    com.google.android.finsky.protos.Location localLocation1 = new com.google.android.finsky.protos.Location();
    localLocation1.latitude = localLocation.getLatitude();
    localLocation1.hasLatitude = true;
    localLocation1.longitude = localLocation.getLongitude();
    localLocation1.hasLongitude = true;
    if (localLocation.hasAccuracy())
    {
      localLocation1.accuracyInMeters = (localLocation.getAccuracy());
      localLocation1.hasAccuracyInMeters = true;
    }
    localLocation1.timestamp = localLocation.getTime();
    localLocation1.hasTimestamp = true;
    return localLocation1;
  }
  
  private static boolean isLocationCurrent(android.location.Location paramLocation)
  {
    if (paramLocation == null) {
      return false;
    }
    return System.currentTimeMillis() - paramLocation.getTime() <= ((Long)G.locationMaxAgeMs.get()).longValue();
  }
  
  static void logLocationEvent(int paramInt)
  {
    FinskyApp.get().getEventLogger().logBackgroundEvent(paramInt, null);
  }
  
  public final void updateLocationIfNeeded()
  {
    int i;
    if (FinskyPreferences.debugLocationOverride.get() != null)
    {
      i = 1;
      if (i == 0) {
        break label61;
      }
      if (sLocation == null)
      {
        LocationOverride localLocationOverride = LocationOverride.unpackFromString((String)FinskyPreferences.debugLocationOverride.get());
        sLocation = localLocationOverride;
        localLocationOverride.setTime(System.currentTimeMillis());
        sLocation.setAccuracy(1.0F);
      }
    }
    label61:
    CachedLocationAccess localCachedLocationAccess;
    CachedLocationAccess.SimpleLocationListener local1;
    LocationManager localLocationManager;
    Criteria localCriteria;
    label256:
    label306:
    for (;;)
    {
      return;
      i = 0;
      break;
      if (!areLocationSuggestionsEnabled())
      {
        sLocation = null;
        return;
      }
      int j;
      android.location.Location localLocation;
      int k;
      if (sLocation == null)
      {
        j = 1;
        if (j == 0) {
          break label354;
        }
        sLastLocationRequestTimestampMs = System.currentTimeMillis();
        localCachedLocationAccess = new CachedLocationAccess();
        localLocation = CachedLocationAccess.getCachedLocation(this.mContext);
        if (isLocationCurrent(localLocation)) {
          break label256;
        }
        k = 0;
      }
      for (;;)
      {
        if (k != 0) {
          break label306;
        }
        sLocation = null;
        sLocationRequestPending = true;
        sLocationRequestPendingLogged = false;
        Context localContext = this.mContext;
        local1 = new CachedLocationAccess.SimpleLocationListener()
        {
          public final void onLocationUpdated(android.location.Location paramAnonymousLocation)
          {
            LocationHelper.sLocationRequestPending = false;
            LocationHelper.sLocation = paramAnonymousLocation;
            LocationHelper.logLocationEvent(1001);
          }
        };
        localLocationManager = (LocationManager)localContext.getSystemService("location");
        localCriteria = new Criteria();
        localCriteria.setAccuracy(2);
        if ((localLocationManager.isProviderEnabled("network")) || (localLocationManager.isProviderEnabled("gps"))) {
          break label308;
        }
        local1.onLocationUpdated(null);
        return;
        long l = System.currentTimeMillis() - sLastLocationRequestTimestampMs;
        if ((!isLocationCurrent(sLocation)) && (l > ((Long)G.locationMaxAgeMs.get()).longValue()))
        {
          j = 1;
          break;
        }
        j = 0;
        break;
        if ((sLocation != null) && (localLocation.distanceTo(sLocation) <= this.mMinLocationUpdateRangeMeters))
        {
          logLocationEvent(1003);
          k = 0;
        }
        else
        {
          sLocation = localLocation;
          logLocationEvent(1000);
          k = 1;
        }
      }
    }
    try
    {
      label308:
      localLocationManager.requestSingleUpdate(localCriteria, new CachedLocationAccess.1(localCachedLocationAccess, local1), null);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.e("No permission to get location. %s", new Object[] { localSecurityException });
      local1.onLocationUpdated(null);
      return;
    }
    label354:
    logLocationEvent(1002);
  }
  
  public static class LocationOverride
    extends android.location.Location
  {
    public final String name;
    
    private LocationOverride(String paramString)
    {
      super();
      this.name = paramString;
    }
    
    public static LocationOverride unpackFromString(String paramString)
    {
      String[] arrayOfString = Utils.commaUnpackStrings(paramString);
      LocationOverride localLocationOverride = new LocationOverride(arrayOfString[0]);
      localLocationOverride.setLatitude(Double.parseDouble(arrayOfString[1]));
      localLocationOverride.setLongitude(Double.parseDouble(arrayOfString[2]));
      return localLocationOverride;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof LocationOverride;
      boolean bool2 = false;
      if (bool1)
      {
        boolean bool3 = distanceTo((LocationOverride)paramObject) < 0.0F;
        bool2 = false;
        if (!bool3) {
          bool2 = true;
        }
      }
      return bool2;
    }
  }
  
  public static final class LocationProviderChangedReceiver
    extends BroadcastReceiver
  {
    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      LocationHelper localLocationHelper = new LocationHelper(paramContext);
      if (Build.VERSION.SDK_INT >= 19)
      {
        if (!"android.location.MODE_CHANGED".equals(paramIntent.getAction())) {
          return;
        }
        try
        {
          if (Settings.Secure.getInt(paramContext.getContentResolver(), "location_mode") == 0)
          {
            LocationHelper.logLocationEvent(1005);
            LocationHelper.clearLocation();
            return;
          }
          LocationHelper.logLocationEvent(1006);
          localLocationHelper.updateLocationIfNeeded();
          return;
        }
        catch (Settings.SettingNotFoundException localSettingNotFoundException)
        {
          FinskyLog.wtf(localSettingNotFoundException, "LOCATION_MODE setting change received but no setting was found!", new Object[0]);
          return;
        }
      }
      LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
      if ((!localLocationManager.isProviderEnabled("network")) && (!localLocationManager.isProviderEnabled("gps")))
      {
        LocationHelper.logLocationEvent(1005);
        LocationHelper.clearLocation();
        return;
      }
      LocationHelper.logLocationEvent(1006);
      localLocationHelper.updateLocationIfNeeded();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.LocationHelper
 * JD-Core Version:    0.7.0.1
 */