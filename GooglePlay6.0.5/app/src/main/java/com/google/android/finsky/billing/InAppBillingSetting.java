package com.google.android.finsky.billing;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Toc.BillingConfig;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.VendingPreferences;

public final class InAppBillingSetting
{
  private static PreferenceFile.SharedPreference<Integer> getSharedIabMaxVersionPreference(String paramString)
  {
    PreferenceFile localPreferenceFile = VendingPreferences.getMainPrefs();
    String str = Utils.urlEncode(paramString);
    return localPreferenceFile.value("IAB_VERSION_" + str, Integer.valueOf(0));
  }
  
  public static boolean isEnabled(String paramString, int paramInt)
  {
    PreferenceFile.SharedPreference localSharedPreference;
    if (paramString != null)
    {
      localSharedPreference = getSharedIabMaxVersionPreference(paramString);
      if (localSharedPreference.exists()) {
        break label71;
      }
      localDfeApi = FinskyApp.get().getDfeApi(paramString);
      if (localDfeApi != null) {
        break label43;
      }
      FinskyLog.w("Unknown account %s", new Object[] { paramString });
    }
    label43:
    label71:
    while (paramInt > ((Integer)localSharedPreference.get()).intValue())
    {
      DfeApi localDfeApi;
      return false;
      Toc.TocResponse localTocResponse = GetTocHelper.getTocBlocking$207f3d71(localDfeApi);
      if ((localTocResponse != null) && (localTocResponse.billingConfig != null)) {
        setVersionFromBillingConfig(paramString, localTocResponse.billingConfig);
      }
    }
    return true;
  }
  
  public static void setVersionFromBillingConfig(String paramString, Toc.BillingConfig paramBillingConfig)
  {
    if ((paramBillingConfig != null) && (paramBillingConfig.hasMaxIabApiVersion))
    {
      int i = paramBillingConfig.maxIabApiVersion;
      PreferenceFile.SharedPreference localSharedPreference = getSharedIabMaxVersionPreference(paramString);
      if (localSharedPreference != null) {
        localSharedPreference.put(Integer.valueOf(i));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.InAppBillingSetting
 * JD-Core Version:    0.7.0.1
 */