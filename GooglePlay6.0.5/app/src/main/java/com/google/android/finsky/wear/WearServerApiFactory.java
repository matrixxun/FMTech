package com.google.android.finsky.wear;

import android.accounts.Account;
import android.text.TextUtils;
import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeApiContext.CheckinConsistencyTokenHelper;
import com.google.android.finsky.api.DfeApiImpl;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.DfeNotificationManager;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.DeviceManagementHelper;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApiContext;
import com.google.android.vending.remoting.api.VendingApiFactory;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class WearServerApiFactory
{
  private static Map<String, Map<String, DfeApi>> sDfeApiMaps;
  static Map<String, Map<String, VendingApi>> sVendingApiMaps;
  
  private static void copyHeader(DfeApiContext paramDfeApiContext1, String paramString1, DfeApiContext paramDfeApiContext2, String paramString2)
  {
    String str = paramDfeApiContext1.peekHeader(paramString1);
    if (!TextUtils.isEmpty(str)) {
      paramDfeApiContext2.addHeader(paramString2, str);
    }
  }
  
  static void copyHeader(VendingApiContext paramVendingApiContext1, String paramString1, VendingApiContext paramVendingApiContext2, String paramString2)
  {
    String str = paramVendingApiContext1.peekHeader(paramString1);
    if (!TextUtils.isEmpty(str)) {
      paramVendingApiContext2.addHeader(paramString2, str);
    }
  }
  
  public static DfeApi getDfeApi(String paramString1, String paramString2)
  {
    Map localMap = sDfeApiMaps;
    Object localObject = null;
    DfeApi localDfeApi = null;
    if (localMap != null)
    {
      localObject = (Map)sDfeApiMaps.get(paramString1);
      localDfeApi = null;
      if (localObject != null) {
        localDfeApi = (DfeApi)((Map)localObject).get(paramString2);
      }
    }
    if (localDfeApi != null) {
      return localDfeApi;
    }
    DfeApiContext localDfeApiContext1 = FinskyApp.get().getDfeApi(paramString1).getApiContext();
    RequestQueue localRequestQueue = FinskyApp.get().mRequestQueue;
    WearDeviceConfigurationHelper localWearDeviceConfigurationHelper = WearDeviceConfigurationHelper.get(paramString2);
    FinskyApp localFinskyApp = FinskyApp.get();
    AndroidAuthenticator localAndroidAuthenticator = localDfeApiContext1.mAuthenticator;
    Cache localCache = localDfeApiContext1.mCache;
    DfeNotificationManager localDfeNotificationManager = localDfeApiContext1.mNotificationManager;
    FinskyEventLog localFinskyEventLog = localDfeApiContext1.mEventLogger;
    FinskyExperiments localFinskyExperiments = localDfeApiContext1.mExperiments;
    Locale localLocale = Locale.getDefault();
    String str1 = (String)DfeApiConfig.clientId.get();
    String str2 = localDfeApiContext1.peekHeader("X-DFE-Filter-Level");
    if (TextUtils.isEmpty(str2)) {}
    for (int i = 0;; i = Integer.valueOf(str2).intValue())
    {
      String str3 = localDfeApiContext1.peekHeader("X-DFE-Content-Filters");
      if (str3 == null) {
        str3 = "";
      }
      DfeApiContext localDfeApiContext2 = new DfeApiContext(localFinskyApp, localAndroidAuthenticator, localCache, localFinskyExperiments, localDfeNotificationManager, localLocale, localWearDeviceConfigurationHelper.simOperator, str1, localWearDeviceConfigurationHelper.loggingId, i, str3, null, localFinskyEventLog, localWearDeviceConfigurationHelper.androidId, DfeApiContext.makeUserAgentString(localWearDeviceConfigurationHelper.version, localWearDeviceConfigurationHelper.versionCode, localWearDeviceConfigurationHelper.buildVersionSdkInt, localWearDeviceConfigurationHelper.buildDevice, localWearDeviceConfigurationHelper.buildHardware, localWearDeviceConfigurationHelper.buildProduct, localWearDeviceConfigurationHelper.buildVersionRelease, localWearDeviceConfigurationHelper.buildModel, localWearDeviceConfigurationHelper.buildId, false), DeviceManagementHelper.getInstance(localFinskyApp), localWearDeviceConfigurationHelper.nodeId);
      copyHeader(localDfeApiContext1, "X-DFE-Device-Id", localDfeApiContext2, "X-DFE-Proxy-Device-Id");
      copyHeader(localDfeApiContext1, "X-DFE-MCCMNC", localDfeApiContext2, "X-DFE-Proxy-MCCMNC");
      copyHeader(localDfeApiContext1, "X-DFE-Logging-Id", localDfeApiContext2, "X-DFE-Proxy-Logging-ID");
      copyHeader(localDfeApiContext1, "User-Agent", localDfeApiContext2, "X-DFE-Proxy-User-Agent");
      localDfeApiContext2.mTokenHelper = new DfeApiContext.CheckinConsistencyTokenHelper()
      {
        public final void addCheckinConsistencyToken(DfeApiContext paramAnonymousDfeApiContext, Map<String, String> paramAnonymousMap)
        {
          String str1 = this.val$nodeHelper.deviceDataVersionInfo;
          if (!TextUtils.isEmpty(str1)) {
            paramAnonymousMap.put("X-DFE-Device-Checkin-Consistency-Token", str1);
          }
          String str2 = paramAnonymousDfeApiContext.getCheckinConsistencyToken();
          if (!TextUtils.isEmpty(str2)) {
            paramAnonymousMap.put("X-DFE-Proxy-Device-Checkin-Consistency-Token", str2);
          }
        }
      };
      DfeApiImpl localDfeApiImpl = new DfeApiImpl(localRequestQueue, localDfeApiContext2);
      if (sDfeApiMaps == null) {
        sDfeApiMaps = new HashMap();
      }
      if (localObject == null)
      {
        localObject = new HashMap();
        sDfeApiMaps.put(paramString1, localObject);
      }
      ((Map)localObject).put(paramString2, localDfeApiImpl);
      return localDfeApiImpl;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearServerApiFactory
 * JD-Core Version:    0.7.0.1
 */