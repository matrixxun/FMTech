package com.google.android.finsky.config;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.protos.Purchase.AuthenticationInfo;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.utils.config.GservicesValue;

public final class PurchaseAuthUtils
{
  public static Purchase.AuthenticationInfo createAuthenticationInfo(String paramString)
  {
    int i = getPurchaseAuthType(paramString);
    Long localLong = (Long)FinskyPreferences.lastGaiaAuthTimestamp.get(paramString).get();
    long l = System.currentTimeMillis();
    Purchase.AuthenticationInfo localAuthenticationInfo = new Purchase.AuthenticationInfo();
    switch (i)
    {
    default: 
      throw new IllegalArgumentException("Unexpected purchaseAuth specified " + i);
    case 0: 
      localAuthenticationInfo.authenticationPreference = 3;
      localAuthenticationInfo.hasAuthenticationPreference = true;
      return localAuthenticationInfo;
    case 2: 
      localAuthenticationInfo.authenticationPreference = 2;
      localAuthenticationInfo.hasAuthenticationPreference = true;
      return localAuthenticationInfo;
    }
    localAuthenticationInfo.authenticationPreference = 4;
    localAuthenticationInfo.hasAuthenticationPreference = true;
    if ((localLong != null) && (localLong.longValue() != 0L))
    {
      localAuthenticationInfo.lastAuthTimestampMillis = localLong.longValue();
      localAuthenticationInfo.hasLastAuthTimestampMillis = true;
    }
    localAuthenticationInfo.flowStartedTimestampMillis = l;
    localAuthenticationInfo.hasFlowStartedTimestampMillis = true;
    return localAuthenticationInfo;
  }
  
  public static int getPurchaseAuthType(String paramString)
  {
    int i = ((Integer)FinskyPreferences.purchaseAuthType.get(paramString).get()).intValue();
    if (i == -1) {
      i = ((Integer)G.defaultPurchaseAuthentication.get()).intValue();
    }
    return i;
  }
  
  public static void setAndLogFingerprintAuth(String paramString1, boolean paramBoolean, FinskyEventLog paramFinskyEventLog, String paramString2)
  {
    int i = 1;
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.useFingerprintForPurchase.get(paramString1);
    boolean bool = ((Boolean)localSharedPreference.get()).booleanValue();
    int j;
    Integer localInteger;
    if (bool != paramBoolean)
    {
      if (!paramBoolean) {
        break label76;
      }
      j = i;
      localInteger = Integer.valueOf(j);
      if (!bool) {
        break label82;
      }
    }
    for (;;)
    {
      paramFinskyEventLog.logSettingsBackgroundEvent(408, localInteger, Integer.valueOf(i), paramString2);
      localSharedPreference.put(Boolean.valueOf(paramBoolean));
      return;
      label76:
      j = 0;
      break;
      label82:
      i = 0;
    }
  }
  
  public static void setAndLogPurchaseAuth(String paramString1, int paramInt, Integer paramInteger, FinskyEventLog paramFinskyEventLog, String paramString2)
  {
    PreferenceFile.SharedPreference localSharedPreference1 = FinskyPreferences.purchaseAuthType.get(paramString1);
    PreferenceFile.SharedPreference localSharedPreference2 = FinskyPreferences.purchaseAuthVersionCode.get(paramString1);
    localSharedPreference1.put(Integer.valueOf(paramInt));
    localSharedPreference2.put(Integer.valueOf(FinskyApp.get().getVersionCode()));
    paramFinskyEventLog.logSettingsBackgroundEvent(400, Integer.valueOf(paramInt), paramInteger, paramString2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.config.PurchaseAuthUtils
 * JD-Core Version:    0.7.0.1
 */