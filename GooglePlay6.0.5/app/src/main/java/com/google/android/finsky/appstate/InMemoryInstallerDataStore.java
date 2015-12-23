package com.google.android.finsky.appstate;

import com.google.android.finsky.protos.AndroidAppDeliveryData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class InMemoryInstallerDataStore
  implements InstallerDataStore
{
  private final Map<String, InstallerDataStore.InstallerData> mAppStates = new HashMap();
  
  public final InstallerDataStore.InstallerData get(String paramString)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = (InstallerDataStore.InstallerData)this.mAppStates.get(paramString);
      return localInstallerData;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Collection<InstallerDataStore.InstallerData> getAll()
  {
    try
    {
      Collection localCollection = this.mAppStates.values();
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void put(InstallerDataStore.InstallerData paramInstallerData)
  {
    try
    {
      this.mAppStates.put(paramInstallerData.packageName, paramInstallerData);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAccountForUpdate(String paramString1, String paramString2)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString1);
      this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setAccountForUpdate(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setActiveSplitId(String paramString1, String paramString2)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString1);
      this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setActiveSplitId(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoAcquireTags(String paramString, String[] paramArrayOfString)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setAutoAcquireTags(paramArrayOfString).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoUpdate(String paramString, int paramInt)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setAutoUpdate(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setCompletedSplitIds(String paramString, String[] paramArrayOfString)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setCompletedSplitIds(paramArrayOfString).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setContinueUrl(String paramString1, String paramString2)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString1);
    this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setContinueUrl(paramString2).mInstance);
  }
  
  public final void setDeliveryData(String paramString, AndroidAppDeliveryData paramAndroidAppDeliveryData, long paramLong)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setDeliveryData(paramAndroidAppDeliveryData, paramLong).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setDeliveryToken(String paramString1, String paramString2)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString1);
    this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setDeliveryToken(paramString2).mInstance);
  }
  
  public final void setDesiredVersion(String paramString, int paramInt)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setDesiredVersion(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrer(String paramString1, String paramString2)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString1);
      this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setExternalReferrer(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrerTimestampMs(String paramString, long paramLong)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString);
    this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setExternalReferrerTimestampMs(paramLong).mInstance);
  }
  
  public final void setFirstDownloadMs(String paramString, long paramLong)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setFirstDownloadMs(paramLong).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setFlags(String paramString, int paramInt)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setFlags(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setInstallerState(String paramString1, int paramInt, String paramString2)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString1);
      this.mAppStates.put(paramString1, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1).setInstallerState(paramInt).setDownloadUri(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastNotifiedVersion(String paramString, int paramInt)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = get(paramString);
      this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setLastNotifiedVersion(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastUpdateTimestampMs(String paramString, long paramLong)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString);
    this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setLastUpdateTimestampMs(paramLong).mInstance);
  }
  
  public final void setPermissionsVersion(String paramString, int paramInt)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString);
    this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setPermissionsVersion(paramInt).mInstance);
  }
  
  public final void setPersistentFlags(String paramString, int paramInt)
  {
    InstallerDataStore.InstallerData localInstallerData = get(paramString);
    this.mAppStates.put(paramString, InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString).setPersistentFlags(paramInt).mInstance);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.InMemoryInstallerDataStore
 * JD-Core Version:    0.7.0.1
 */