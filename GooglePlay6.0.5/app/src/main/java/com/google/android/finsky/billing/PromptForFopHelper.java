package com.google.android.finsky.billing;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.BuyInstruments.CheckInstrumentResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public final class PromptForFopHelper
{
  public static void expireHasNoFop(String paramString)
  {
    FinskyPreferences.accountHasFopLastUpdateMs.get(paramString).remove();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = FinskyLog.scrubPii(paramString);
    FinskyLog.d("Invalidated has_fop cache. (account=%s)", arrayOfObject);
    snooze(paramString);
  }
  
  private static long getSnoozePeriodMs(String paramString)
  {
    String str1;
    if (isPromptFopScheduleV2Enabled(paramString)) {
      str1 = (String)G.promptForFopSnoozeScheduleM2.get();
    }
    for (int i = ((Integer)FinskyPreferences.promptForFopNumSnoozed2.get(paramString).get()).intValue(); str1 == null; i = ((Integer)FinskyPreferences.promptForFopNumSnoozed.get(paramString).get()).intValue())
    {
      FinskyLog.d("No snooze schedule.", new Object[0]);
      return -1L;
      str1 = (String)G.promptForFopSnoozeScheduleM.get();
    }
    String[] arrayOfString = Utils.commaUnpackStrings(str1);
    int j = Math.min(i - 1, -1 + arrayOfString.length);
    if (j < 0)
    {
      FinskyLog.e("Invalid snooze schedule: %s", new Object[] { str1 });
      return -1L;
    }
    String str2 = arrayOfString[j];
    try
    {
      long l = Long.parseLong(str2);
      return 1000L * (l * 60L);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      FinskyLog.e("Invalid snooze period: %s. Schedule: %s", new Object[] { str2, str1 });
    }
    return -1L;
  }
  
  public static boolean isExperimentEnabled(String paramString)
  {
    return FinskyApp.get().getExperiments(paramString).isEnabled(12602035L);
  }
  
  public static boolean isHasFopCacheValid(String paramString, long paramLong)
  {
    if (((Boolean)FinskyPreferences.accountHasFop.get(paramString).get()).booleanValue())
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = FinskyLog.scrubPii(paramString);
        FinskyLog.d("has_fop=true cache valid. (account=%s)", arrayOfObject3);
      }
      return true;
    }
    if (((Long)FinskyPreferences.accountHasFopLastUpdateMs.get(paramString).get()).longValue() + ((Long)G.hasFopCacheTimeoutMs.get()).longValue() > paramLong)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = FinskyLog.scrubPii(paramString);
        FinskyLog.d("has_fop=false cache valid. (account=%s)", arrayOfObject2);
      }
      return true;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = FinskyLog.scrubPii(paramString);
    FinskyLog.d("has_fop=false cache invalid. (account=%s)", arrayOfObject1);
    return false;
  }
  
  private static boolean isPromptFopScheduleV2Enabled(String paramString)
  {
    return FinskyApp.get().getExperiments(paramString).isEnabled(12603126L);
  }
  
  public static boolean isSnoozed(String paramString, long paramLong)
  {
    if (isPromptFopScheduleV2Enabled(paramString)) {}
    for (int i = ((Integer)FinskyPreferences.promptForFopNumSnoozed2.get(paramString).get()).intValue(); i <= 0; i = ((Integer)FinskyPreferences.promptForFopNumSnoozed.get(paramString).get()).intValue()) {
      return false;
    }
    long l1 = ((Long)FinskyPreferences.promptForFopLastSnoozedTimestampMs.get(paramString).get()).longValue();
    long l2 = getSnoozePeriodMs(paramString);
    if ((l2 < 0L) || (l1 + l2 > paramLong)) {}
    for (boolean bool = true;; bool = false)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Boolean.valueOf(bool);
        arrayOfObject[1] = FinskyLog.scrubPii(paramString);
        FinskyLog.d("is_snoozed=%b (account=%s)", arrayOfObject);
      }
      return bool;
    }
  }
  
  public static boolean shouldPromptForFop(String paramString)
  {
    long l = System.currentTimeMillis();
    return (isExperimentEnabled(paramString)) && (isHasFopCacheValid(paramString, l)) && (!((Boolean)FinskyPreferences.accountHasFop.get(paramString).get()).booleanValue()) && (!isSnoozed(paramString, l));
  }
  
  public static void snooze(String paramString)
  {
    FinskyPreferences.promptForFopLastSnoozedTimestampMs.get(paramString).put(Long.valueOf(System.currentTimeMillis()));
    if (isPromptFopScheduleV2Enabled(paramString)) {}
    for (PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.promptForFopNumSnoozed2.get(paramString);; localSharedPreference = FinskyPreferences.promptForFopNumSnoozed.get(paramString))
    {
      localSharedPreference.put(Integer.valueOf(1 + ((Integer)localSharedPreference.get()).intValue()));
      long l = getSnoozePeriodMs(paramString);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Long.valueOf(l);
      arrayOfObject[1] = FinskyLog.scrubPii(paramString);
      FinskyLog.d("Snoozing for %d ms (account=%s)", arrayOfObject);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.PromptForFopHelper
 * JD-Core Version:    0.7.0.1
 */