package com.google.android.finsky.billing.carrierbilling;

import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Map;

public final class CarrierBillingUtils
{
  public static void addPrepareOrBillingProfileParams(boolean paramBoolean, Map<String, String> paramMap)
  {
    String str = BillingLocator.getSimIdentifier();
    if (!TextUtils.isEmpty(str)) {
      paramMap.put("dcbch", str);
    }
    if ((paramBoolean) || (((Boolean)G.dcb3PrepareSendDeviceRebooted.get()).booleanValue()))
    {
      long l1 = System.currentTimeMillis();
      long l2 = SystemClock.elapsedRealtime();
      if (l1 - ((Long)BillingPreferences.LAST_DCB3_PROVISIONING_TIME_MILLIS.get()).longValue() <= l2) {
        break label101;
      }
    }
    label101:
    for (int i = 1;; i = 0)
    {
      BillingPreferences.LAST_DCB3_PROVISIONING_TIME_MILLIS.put(Long.valueOf(System.currentTimeMillis()));
      if (i != 0) {
        paramMap.put("dcbdevicerebooted", "true");
      }
      return;
    }
  }
  
  public static String getSimIdentifier()
  {
    String str1 = BillingLocator.getSubscriberIdFromTelephony();
    if (str1 != null) {
      return Sha1Util.secureHash(str1.getBytes());
    }
    String str2 = BillingLocator.getDeviceIdFromTelephony();
    if (str2 != null) {
      return Sha1Util.secureHash(str2.getBytes());
    }
    return "invalid_sim_id";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils
 * JD-Core Version:    0.7.0.1
 */