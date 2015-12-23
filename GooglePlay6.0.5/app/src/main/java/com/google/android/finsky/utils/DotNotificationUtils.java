package com.google.android.finsky.utils;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.PromptForFopHelper;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;

public final class DotNotificationUtils
{
  public static void resolveMyAccountLinkNotificationTapped()
  {
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.accountCompletionMyAccountLinkTapCount.get(FinskyApp.get().getCurrentAccountName());
    if (((Integer)localSharedPreference.get()).intValue() == 0) {
      localSharedPreference.put(Integer.valueOf(1));
    }
  }
  
  public static boolean shouldShowAccountCompletionDotNotification()
  {
    return (shouldShowPaymentMethodsCardNotification()) && (((Integer)FinskyPreferences.accountCompletionMyAccountLinkTapCount.get(FinskyApp.get().getCurrentAccountName()).get()).intValue() == 0);
  }
  
  public static boolean shouldShowPaymentMethodsCardNotification()
  {
    if ((FinskyApp.get().getExperiments().isEnabled(12603992L)) && (PromptForFopHelper.shouldPromptForFop(FinskyApp.get().getCurrentAccountName()))) {}
    for (int i = 1; (i != 0) && (((Integer)FinskyPreferences.accountCompletionPaymentsCardShowCount.get(FinskyApp.get().getCurrentAccountName()).get()).intValue() == 0); i = 0) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DotNotificationUtils
 * JD-Core Version:    0.7.0.1
 */