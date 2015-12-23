package com.google.android.finsky.appstate;

import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Collection;

public abstract interface InstallerDataStore
{
  public abstract InstallerData get(String paramString);
  
  public abstract Collection<InstallerData> getAll();
  
  public abstract void put(InstallerData paramInstallerData);
  
  public abstract void setAccountForUpdate(String paramString1, String paramString2);
  
  public abstract void setActiveSplitId(String paramString1, String paramString2);
  
  public abstract void setAutoAcquireTags(String paramString, String[] paramArrayOfString);
  
  public abstract void setAutoUpdate(String paramString, int paramInt);
  
  public abstract void setCompletedSplitIds(String paramString, String[] paramArrayOfString);
  
  public abstract void setContinueUrl(String paramString1, String paramString2);
  
  public abstract void setDeliveryData(String paramString, AndroidAppDeliveryData paramAndroidAppDeliveryData, long paramLong);
  
  public abstract void setDeliveryToken(String paramString1, String paramString2);
  
  public abstract void setDesiredVersion(String paramString, int paramInt);
  
  public abstract void setExternalReferrer(String paramString1, String paramString2);
  
  public abstract void setExternalReferrerTimestampMs(String paramString, long paramLong);
  
  public abstract void setFirstDownloadMs(String paramString, long paramLong);
  
  public abstract void setFlags(String paramString, int paramInt);
  
  public abstract void setInstallerState(String paramString1, int paramInt, String paramString2);
  
  public abstract void setLastNotifiedVersion(String paramString, int paramInt);
  
  public abstract void setLastUpdateTimestampMs(String paramString, long paramLong);
  
  public abstract void setPermissionsVersion(String paramString, int paramInt);
  
  public abstract void setPersistentFlags(String paramString, int paramInt);
  
  public static final class InstallerData
  {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public String accountForUpdate;
    public String accountName;
    public String activeSplitId;
    private String[] autoAcquireTags;
    public int autoUpdate = 1;
    private String[] completedSplitIds;
    public String continueUrl;
    public AndroidAppDeliveryData deliveryData;
    public long deliveryDataTimestampMs;
    public String deliveryToken;
    public int desiredVersion = -1;
    public String downloadUri;
    public String externalReferrer;
    public long externalReferrerTimestampMs;
    public long firstDownloadMs;
    public int flags;
    public int installerState;
    public int lastNotifiedVersion = -1;
    public long lastUpdateTimestampMs;
    public final String packageName;
    public int permissionsVersion;
    public int persistentFlags;
    public String requestId;
    public String title;
    
    private InstallerData(String paramString)
    {
      this.packageName = paramString;
    }
    
    public InstallerData(String paramString1, int paramInt1, int paramInt2, int paramInt3, AndroidAppDeliveryData paramAndroidAppDeliveryData, long paramLong1, int paramInt4, String paramString2, long paramLong2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt5, long paramLong3, String paramString7, String[] paramArrayOfString1, long paramLong4, int paramInt6, int paramInt7, String paramString8, String[] paramArrayOfString2, String paramString9, String paramString10)
    {
      this.packageName = paramString1;
      this.autoUpdate = paramInt1;
      this.desiredVersion = paramInt2;
      this.lastNotifiedVersion = paramInt3;
      this.deliveryData = paramAndroidAppDeliveryData;
      this.deliveryDataTimestampMs = paramLong1;
      this.installerState = paramInt4;
      this.downloadUri = paramString2;
      this.firstDownloadMs = paramLong2;
      this.externalReferrer = paramString3;
      this.continueUrl = paramString4;
      this.accountName = paramString5;
      this.title = paramString6;
      this.flags = paramInt5;
      this.lastUpdateTimestampMs = paramLong3;
      this.accountForUpdate = paramString7;
      this.autoAcquireTags = paramArrayOfString1;
      this.externalReferrerTimestampMs = paramLong4;
      this.persistentFlags = paramInt6;
      this.permissionsVersion = paramInt7;
      this.deliveryToken = paramString8;
      this.completedSplitIds = paramArrayOfString2;
      this.activeSplitId = paramString9;
      this.requestId = paramString10;
    }
    
    public final String[] getAutoAcquireTags()
    {
      if (this.autoAcquireTags == null) {
        return EMPTY_STRING_ARRAY;
      }
      return this.autoAcquireTags;
    }
    
    public final String[] getCompletedSplitIds()
    {
      if (this.completedSplitIds == null) {
        return EMPTY_STRING_ARRAY;
      }
      return this.completedSplitIds;
    }
    
    public final String toString()
    {
      boolean bool = true;
      Object[] arrayOfObject = new Object[10];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[bool] = Integer.valueOf(this.autoUpdate);
      arrayOfObject[2] = Integer.valueOf(this.desiredVersion);
      if (this.deliveryData != null) {}
      for (;;)
      {
        arrayOfObject[3] = Boolean.valueOf(bool);
        arrayOfObject[4] = Integer.valueOf(this.installerState);
        arrayOfObject[5] = this.downloadUri;
        arrayOfObject[6] = Long.valueOf(this.firstDownloadMs);
        arrayOfObject[7] = this.externalReferrer;
        arrayOfObject[8] = this.continueUrl;
        arrayOfObject[9] = FinskyLog.scrubPii(this.accountName);
        return String.format("(packageName=%s,autoUpdate=%d,desiredVersion=%d,hasDeliveryData=%b,installerState=%d,downloadUri=%s,firstDownloadMs=%d,externalReferrer=%s,continueUrl=%s,account=%s)", arrayOfObject);
        bool = false;
      }
    }
    
    public static final class Builder
    {
      public final InstallerDataStore.InstallerData mInstance;
      
      public Builder(String paramString)
      {
        this.mInstance = new InstallerDataStore.InstallerData(paramString, (byte)0);
      }
      
      public static Builder buildUpon(InstallerDataStore.InstallerData paramInstallerData, String paramString)
      {
        Builder localBuilder = new Builder(paramString);
        if (paramInstallerData != null)
        {
          if (!paramString.equals(paramInstallerData.packageName))
          {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = paramString;
            arrayOfObject[1] = paramInstallerData.packageName;
            FinskyLog.wtf("Package name mismatch,  %s != %s", arrayOfObject);
          }
          localBuilder.setAutoUpdate(paramInstallerData.autoUpdate);
          localBuilder.setDesiredVersion(paramInstallerData.desiredVersion);
          localBuilder.setLastNotifiedVersion(paramInstallerData.lastNotifiedVersion);
          localBuilder.setDeliveryData(paramInstallerData.deliveryData, paramInstallerData.deliveryDataTimestampMs);
          localBuilder.setInstallerState(paramInstallerData.installerState);
          localBuilder.setDownloadUri(paramInstallerData.downloadUri);
          localBuilder.setFirstDownloadMs(paramInstallerData.firstDownloadMs);
          localBuilder.setExternalReferrer(paramInstallerData.externalReferrer);
          localBuilder.setContinueUrl(paramInstallerData.continueUrl);
          localBuilder.setAccountName(paramInstallerData.accountName);
          localBuilder.setTitle(paramInstallerData.title);
          localBuilder.setFlags(paramInstallerData.flags);
          localBuilder.setLastUpdateTimestampMs(paramInstallerData.lastUpdateTimestampMs);
          localBuilder.setAccountForUpdate(paramInstallerData.accountForUpdate);
          localBuilder.setAutoAcquireTags(paramInstallerData.getAutoAcquireTags());
          localBuilder.setExternalReferrerTimestampMs(paramInstallerData.externalReferrerTimestampMs);
          localBuilder.setPersistentFlags(paramInstallerData.persistentFlags);
          localBuilder.setPermissionsVersion(paramInstallerData.permissionsVersion);
          localBuilder.setDeliveryToken(paramInstallerData.deliveryToken);
          localBuilder.setCompletedSplitIds(paramInstallerData.getCompletedSplitIds());
          localBuilder.setActiveSplitId(paramInstallerData.activeSplitId);
          localBuilder.setRequestId(paramInstallerData.requestId);
        }
        return localBuilder;
      }
      
      public final Builder setAccountForUpdate(String paramString)
      {
        InstallerDataStore.InstallerData.access$1602(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setAccountName(String paramString)
      {
        InstallerDataStore.InstallerData.access$1102(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setActiveSplitId(String paramString)
      {
        InstallerDataStore.InstallerData.access$2302(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setAutoAcquireTags(String[] paramArrayOfString)
      {
        InstallerDataStore.InstallerData.access$1702(this.mInstance, paramArrayOfString);
        return this;
      }
      
      public final Builder setAutoUpdate(int paramInt)
      {
        InstallerDataStore.InstallerData.access$202(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setCompletedSplitIds(String[] paramArrayOfString)
      {
        InstallerDataStore.InstallerData.access$2202(this.mInstance, paramArrayOfString);
        return this;
      }
      
      public final Builder setContinueUrl(String paramString)
      {
        InstallerDataStore.InstallerData.access$1402(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setDeliveryData(AndroidAppDeliveryData paramAndroidAppDeliveryData, long paramLong)
      {
        InstallerDataStore.InstallerData.access$502(this.mInstance, paramAndroidAppDeliveryData);
        InstallerDataStore.InstallerData.access$602(this.mInstance, paramLong);
        return this;
      }
      
      public final Builder setDeliveryToken(String paramString)
      {
        InstallerDataStore.InstallerData.access$2102(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setDesiredVersion(int paramInt)
      {
        InstallerDataStore.InstallerData.access$302(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setDownloadUri(String paramString)
      {
        InstallerDataStore.InstallerData.access$802(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setExternalReferrer(String paramString)
      {
        InstallerDataStore.InstallerData.access$1002(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setExternalReferrerTimestampMs(long paramLong)
      {
        InstallerDataStore.InstallerData.access$1802(this.mInstance, paramLong);
        return this;
      }
      
      public final Builder setFirstDownloadMs(long paramLong)
      {
        InstallerDataStore.InstallerData.access$902(this.mInstance, paramLong);
        return this;
      }
      
      public final Builder setFlags(int paramInt)
      {
        InstallerDataStore.InstallerData.access$1302(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setInstallerState(int paramInt)
      {
        InstallerDataStore.InstallerData.access$702(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setLastNotifiedVersion(int paramInt)
      {
        InstallerDataStore.InstallerData.access$402(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setLastUpdateTimestampMs(long paramLong)
      {
        InstallerDataStore.InstallerData.access$1502(this.mInstance, paramLong);
        return this;
      }
      
      public final Builder setPermissionsVersion(int paramInt)
      {
        InstallerDataStore.InstallerData.access$2002(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setPersistentFlags(int paramInt)
      {
        InstallerDataStore.InstallerData.access$1902(this.mInstance, paramInt);
        return this;
      }
      
      public final Builder setRequestId(String paramString)
      {
        InstallerDataStore.InstallerData.access$2402(this.mInstance, paramString);
        return this;
      }
      
      public final Builder setTitle(String paramString)
      {
        InstallerDataStore.InstallerData.access$1202(this.mInstance, paramString);
        return this;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.InstallerDataStore
 * JD-Core Version:    0.7.0.1
 */