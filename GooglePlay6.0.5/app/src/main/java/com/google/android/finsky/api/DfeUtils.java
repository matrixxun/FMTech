package com.google.android.finsky.api;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.play.utils.NetworkInfoUtil;
import com.google.android.play.utils.config.GservicesValue;

public final class DfeUtils
{
  private static boolean sIsTimeoutExperimentMultiplier100PercentEnabled = false;
  private static boolean sIsTimeoutExperimentMultiplier125PercentEnabled = false;
  private static boolean sIsTimeoutExperimentMultiplier150PercentEnabled = false;
  private static boolean sIsTimeoutExperimentMultiplier175PercentEnabled = false;
  private static boolean sIsTimeoutExperimentMultiplier50PercentEnabled;
  private static boolean sIsTimeoutExperimentMultiplier75PercentEnabled;
  private static boolean sUseLegacyDefaultTimeOut = true;
  
  static
  {
    sIsTimeoutExperimentMultiplier50PercentEnabled = false;
    sIsTimeoutExperimentMultiplier75PercentEnabled = false;
  }
  
  public static String base64Encode(byte[] paramArrayOfByte)
  {
    return Base64.encodeToString(paramArrayOfByte, 10);
  }
  
  public static String createDetailsUrlFromId(String paramString)
  {
    return "details?doc=" + paramString;
  }
  
  private static Uri.Builder createSearchUrlBuilder(String paramString, int paramInt)
  {
    if (paramInt == 9) {
      paramInt = 0;
    }
    return DfeApi.SEARCH_CHANNEL_URI.buildUpon().appendQueryParameter("c", Integer.toString(paramInt)).appendQueryParameter("q", paramString);
  }
  
  public static String formSearchUrl(String paramString, int paramInt)
  {
    return createSearchUrlBuilder(paramString, paramInt).build().toString();
  }
  
  public static String formSearchUrlWithFprDisabled(String paramString, int paramInt)
  {
    Uri.Builder localBuilder = createSearchUrlBuilder(paramString, paramInt);
    localBuilder.appendQueryParameter("fpr", "0");
    return localBuilder.build().toString();
  }
  
  public static int getRequestTimeoutMs()
  {
    int i;
    if (sUseLegacyDefaultTimeOut) {
      i = Math.round(((Integer)DfeApiConfig.dfeRequestTimeoutMs.get()).intValue() * RequestUtils.getLegacyDefaultTimeoutMultiplier());
    }
    do
    {
      return i;
      switch (NetworkInfoUtil.getNetworkType(FinskyApp.get().getApplicationContext()))
      {
      case 5: 
      default: 
        i = ((Integer)DfeApiConfig.dfeRequestTimeout3GMs.get()).intValue();
      }
      while (sIsTimeoutExperimentMultiplier50PercentEnabled)
      {
        return (int)(0.5F * i);
        i = ((Integer)DfeApiConfig.dfeRequestTimeout2GMs.get()).intValue();
        continue;
        i = ((Integer)DfeApiConfig.dfeRequestTimeout3GMs.get()).intValue();
        continue;
        i = ((Integer)DfeApiConfig.dfeRequestTimeout4GMs.get()).intValue();
        continue;
        i = ((Integer)DfeApiConfig.dfeRequestTimeoutWifiMs.get()).intValue();
      }
      if (sIsTimeoutExperimentMultiplier75PercentEnabled) {
        return (int)(0.75F * i);
      }
      if (sIsTimeoutExperimentMultiplier100PercentEnabled) {
        return (int)(1.0F * i);
      }
      if (sIsTimeoutExperimentMultiplier125PercentEnabled) {
        return (int)(1.25F * i);
      }
      if (sIsTimeoutExperimentMultiplier150PercentEnabled) {
        return (int)(1.5F * i);
      }
    } while (!sIsTimeoutExperimentMultiplier175PercentEnabled);
    return (int)(1.75F * i);
  }
  
  public static boolean isSameDocumentDetailsUrl(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    return TextUtils.equals(Uri.parse(paramString1).getQueryParameter("doc"), Uri.parse(paramString2).getQueryParameter("doc"));
  }
  
  public static boolean shouldUseLegacyDefaultTimeOut()
  {
    return sUseLegacyDefaultTimeOut;
  }
  
  public static void updateTimeoutValues()
  {
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
    sIsTimeoutExperimentMultiplier50PercentEnabled = localFinskyExperiments.isEnabled(12603144L);
    sIsTimeoutExperimentMultiplier75PercentEnabled = localFinskyExperiments.isEnabled(12603145L);
    sIsTimeoutExperimentMultiplier100PercentEnabled = localFinskyExperiments.isEnabled(12603146L);
    sIsTimeoutExperimentMultiplier125PercentEnabled = localFinskyExperiments.isEnabled(12603147L);
    sIsTimeoutExperimentMultiplier150PercentEnabled = localFinskyExperiments.isEnabled(12603148L);
    sIsTimeoutExperimentMultiplier175PercentEnabled = localFinskyExperiments.isEnabled(12603149L);
    if ((!sIsTimeoutExperimentMultiplier50PercentEnabled) && (!sIsTimeoutExperimentMultiplier75PercentEnabled) && (!sIsTimeoutExperimentMultiplier100PercentEnabled) && (!sIsTimeoutExperimentMultiplier125PercentEnabled) && (!sIsTimeoutExperimentMultiplier150PercentEnabled) && (!sIsTimeoutExperimentMultiplier175PercentEnabled)) {}
    for (boolean bool = true;; bool = false)
    {
      sUseLegacyDefaultTimeOut = bool;
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeUtils
 * JD-Core Version:    0.7.0.1
 */