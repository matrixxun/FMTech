package com.google.android.finsky.api;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.auth.AuthTokenUtils;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.Location;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens.ConsistencyTokenInfo;
import com.google.android.finsky.utils.DeviceManagementHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.LocationHelper;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.gms.checkin.CheckinServiceClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.UrlTools;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class DfeApiContext
{
  AdIdProvider mAdIdProvider;
  public final AndroidAuthenticator mAuthenticator;
  public final Cache mCache;
  final Context mContext;
  final DeviceManagementHelper mDeviceManagementHelper;
  public final FinskyEventLog mEventLogger;
  public final FinskyExperiments mExperiments;
  private final Map<String, String> mHeaders = new HashMap();
  private String mLastAuthToken;
  private NetworkStateProvider mNetworkStateProvider;
  final String mNodeId;
  public final DfeNotificationManager mNotificationManager;
  public CheckinConsistencyTokenHelper mTokenHelper;
  
  public DfeApiContext(Context paramContext, AndroidAuthenticator paramAndroidAuthenticator, Cache paramCache, FinskyExperiments paramFinskyExperiments, DfeNotificationManager paramDfeNotificationManager, Locale paramLocale, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, AdIdProvider paramAdIdProvider, FinskyEventLog paramFinskyEventLog, String paramString5, String paramString6, DeviceManagementHelper paramDeviceManagementHelper, String paramString7)
  {
    if (((Long)DfeApiConfig.androidId.get()).longValue() == 0L) {
      FinskyLog.w("Unexpected android-id = 0", new Object[0]);
    }
    this.mContext = paramContext;
    this.mAuthenticator = paramAndroidAuthenticator;
    this.mCache = paramCache;
    this.mNotificationManager = paramDfeNotificationManager;
    this.mExperiments = paramFinskyExperiments;
    this.mAdIdProvider = paramAdIdProvider;
    this.mNodeId = paramString7;
    this.mHeaders.put("X-DFE-Device-Id", paramString5);
    this.mHeaders.put("Accept-Language", paramLocale.getLanguage() + "-" + paramLocale.getCountry());
    if (!TextUtils.isEmpty(paramString1)) {
      this.mHeaders.put("X-DFE-MCCMNC", paramString1);
    }
    if (!TextUtils.isEmpty(paramString2)) {
      this.mHeaders.put("X-DFE-Client-Id", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      this.mHeaders.put("X-DFE-Logging-Id", paramString3);
    }
    this.mHeaders.put("User-Agent", paramString6);
    addHeaderForContentFilters(paramInt, paramString4);
    this.mEventLogger = paramFinskyEventLog;
    this.mNetworkStateProvider = new NetworkStateInfo(this.mContext);
    if ((((Boolean)G.dfeManagedContextDetectionEnabled.get()).booleanValue()) && ((this.mExperiments == null) || (!this.mExperiments.isEnabled(12603109L)))) {}
    String str2;
    for (this.mDeviceManagementHelper = paramDeviceManagementHelper;; this.mDeviceManagementHelper = null)
    {
      String str1 = DfeApi.BASE_URI.toString();
      str2 = UrlTools.rewrite(this.mContext, str1);
      if (str2 != null) {
        break;
      }
      throw new RuntimeException("BASE_URI blocked by UrlRules: " + str1);
    }
    Utils.checkUrlIsSecure(str2);
  }
  
  public static void addUserSettingsConsistencyTokens(UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens, Map<String, String> paramMap)
  {
    if (paramUserSettingsConsistencyTokens.consistencyTokenInfo == null) {}
    for (;;)
    {
      return;
      for (int i = 0; i < paramUserSettingsConsistencyTokens.consistencyTokenInfo.length; i++) {
        paramMap.put(paramUserSettingsConsistencyTokens.consistencyTokenInfo[i].requestHeader, paramUserSettingsConsistencyTokens.consistencyTokenInfo[i].consistencyToken);
      }
    }
  }
  
  public static UserContext getUserContext()
  {
    Location localLocation = LocationHelper.getLocationProto();
    UserContext localUserContext = null;
    if (localLocation != null)
    {
      localUserContext = new UserContext();
      localUserContext.location = localLocation;
    }
    return localUserContext;
  }
  
  public static String makeShortUserAgentString(Context paramContext)
  {
    try
    {
      String str1 = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      String str2 = String.format(Locale.US, "Android-Finsky/%s", new Object[] { str1 });
      return str2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Can't find our own package", localNameNotFoundException);
    }
  }
  
  public static String makeUserAgentString(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      String str1 = localPackageInfo.versionName;
      int i = localPackageInfo.versionCode;
      boolean bool = paramContext.getResources().getBoolean(2131427334);
      String str2 = makeUserAgentString(str1, i, Build.VERSION.SDK_INT, Build.DEVICE, Build.HARDWARE, Build.PRODUCT, Build.VERSION.RELEASE, Build.MODEL, Build.ID, bool);
      return str2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Can't find our own package", localNameNotFoundException);
    }
  }
  
  public static String makeUserAgentString(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean)
  {
    String str1 = sanitizeHeaderValue(paramString2);
    String str2 = sanitizeHeaderValue(paramString3);
    String str3 = sanitizeHeaderValue(paramString4);
    String str4 = sanitizeHeaderValue(paramString5);
    String str5 = sanitizeHeaderValue(paramString6);
    String str6 = sanitizeHeaderValue(paramString7);
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[11];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = "3";
      arrayOfObject[2] = Integer.valueOf(paramInt1);
      arrayOfObject[3] = Integer.valueOf(paramInt2);
      arrayOfObject[4] = str1;
      arrayOfObject[5] = str2;
      arrayOfObject[6] = str3;
      arrayOfObject[7] = str4;
      arrayOfObject[8] = str5;
      arrayOfObject[9] = str6;
      arrayOfObject[10] = Integer.valueOf(i);
      return String.format(localLocale, "Android-Finsky/%s (api=%s,versionCode=%d,sdk=%d,device=%s,hardware=%s,product=%s,platformVersionRelease=%s,model=%s,buildId=%s,isWideScreen=%d)", arrayOfObject);
    }
  }
  
  public static String sanitizeHeaderValue(String paramString)
  {
    return Uri.encode(paramString).replace("(", "").replace(")", "");
  }
  
  public final void addHeader(String paramString1, String paramString2)
  {
    try
    {
      this.mHeaders.put(paramString1, paramString2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void addHeaderForContentFilters(int paramInt, String paramString)
  {
    if ((!FinskyApp.get().getExperiments().isEnabled(12602392L)) || (!((Boolean)FinskyPreferences.contentFilters2VisitedOnce.get()).booleanValue())) {
      this.mHeaders.put("X-DFE-Filter-Level", String.valueOf(paramInt));
    }
    if (FinskyApp.get().getExperiments().isEnabled(12602392L)) {
      this.mHeaders.put("X-DFE-Content-Filters", paramString);
    }
  }
  
  public final Account getAccount()
  {
    if (this.mAuthenticator == null) {
      return null;
    }
    return this.mAuthenticator.mAccount;
  }
  
  public final String getAccountName()
  {
    Account localAccount = getAccount();
    if (localAccount == null) {
      return null;
    }
    return localAccount.name;
  }
  
  public final String getCheckinConsistencyToken()
  {
    boolean bool = ((Boolean)DfeApiConfig.consistencyTokenEnabled.get()).booleanValue();
    Object localObject = null;
    if (bool)
    {
      int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
      localObject = null;
      if (i != 0) {}
    }
    try
    {
      Context localContext = this.mContext;
      zzx.zzcy("Calling this from your main thread can lead to deadlock.");
      String str = CheckinServiceClient.zzb(localContext, CheckinServiceClient.zzq(localContext));
      localObject = str;
      return localObject;
    }
    catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = localGooglePlayServicesRepairableException.getMessage();
      FinskyLog.e("Unable to fetch token, GooglePlayServicesRepairableException: %s", arrayOfObject3);
      return null;
    }
    catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localGooglePlayServicesNotAvailableException.getMessage();
      FinskyLog.e("Unable to fetch token, GooglePlayServicesNotAvailableException: %s", arrayOfObject2);
      return null;
    }
    catch (IOException localIOException)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localIOException.getMessage();
      FinskyLog.e("Unable to fetch token, IOException: %s", arrayOfObject1);
    }
    return null;
  }
  
  public final NetworkInfo getCurrentNetworkInfo()
  {
    return this.mNetworkStateProvider.getCurrentNetworkInfo();
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(this.mHeaders);
      if (this.mExperiments != null)
      {
        if (this.mExperiments.hasEnabledTargets()) {
          localHashMap.put("X-DFE-Supported-Targets", this.mExperiments.getEnabledTargetsHeaderValue());
        }
        if (this.mExperiments.hasUnsupportedTargets()) {
          localHashMap.put("X-DFE-Other-Targets", this.mExperiments.getUnsupportedTargetsHeaderValue());
        }
      }
      String str = (String)FinskyPreferences.tocCookie.get(getAccountName()).get();
      if (!TextUtils.isEmpty(str)) {
        localHashMap.put("X-DFE-Cookie", str);
      }
      if (this.mAuthenticator != null)
      {
        this.mLastAuthToken = this.mAuthenticator.getAuthToken();
        AuthTokenUtils.putGoogleLoginAuthTokenInHeader(localHashMap, this.mLastAuthToken);
      }
      return localHashMap;
    }
    finally {}
  }
  
  public final void invalidateAuthToken()
  {
    if (this.mLastAuthToken != null)
    {
      if (this.mAuthenticator != null) {
        this.mAuthenticator.invalidateAuthToken(this.mLastAuthToken);
      }
      this.mLastAuthToken = null;
    }
  }
  
  public final String peekHeader(String paramString)
  {
    try
    {
      String str = (String)this.mHeaders.get(paramString);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[DfeApiContext headers={");
    int i = 1;
    Iterator localIterator = this.mHeaders.keySet().iterator();
    if (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (i != 0) {
        i = 0;
      }
      for (;;)
      {
        localStringBuilder.append(str).append(": ").append((String)this.mHeaders.get(str));
        break;
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append("}]");
    return localStringBuilder.toString();
  }
  
  public static abstract interface CheckinConsistencyTokenHelper
  {
    public abstract void addCheckinConsistencyToken(DfeApiContext paramDfeApiContext, Map<String, String> paramMap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeApiContext
 * JD-Core Version:    0.7.0.1
 */