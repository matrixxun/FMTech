package com.google.android.finsky.billing;

import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;

public final class BillingPreferences
{
  public static PreferenceFile.SharedPreference<String> BILLING_COUNTRIES;
  public static PreferenceFile.SharedPreference<Long> LAST_BILLING_COUNTRIES_REFRESH_MILLIS = sBillingPrefs.value("last_billing_countries_check_v2", Long.valueOf(0L));
  public static PreferenceFile.SharedPreference<Long> LAST_DCB3_PROVISIONING_TIME_MILLIS = sBillingPrefs.value("last_dcb3_provisioning_time_millis", Long.valueOf(0L));
  private static PreferenceFile sBillingPrefs;
  
  static
  {
    PreferenceFile localPreferenceFile = new PreferenceFile("billing_preferences");
    sBillingPrefs = localPreferenceFile;
    BILLING_COUNTRIES = localPreferenceFile.value("billing_countries_v2", null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingPreferences
 * JD-Core Version:    0.7.0.1
 */