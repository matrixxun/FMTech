package com.google.android.finsky.utils;

import android.os.Build;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;

public final class VendingUtils
{
  private static volatile boolean sSystemWasUpgraded = false;
  
  public static boolean wasSystemUpgraded()
  {
    if (sSystemWasUpgraded) {
      return true;
    }
    String str = Build.FINGERPRINT;
    if (str.equals((String)VendingPreferences.LAST_BUILD_FINGERPRINT.get())) {
      return false;
    }
    sSystemWasUpgraded = true;
    VendingPreferences.LAST_BUILD_FINGERPRINT.put(str);
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.VendingUtils
 * JD-Core Version:    0.7.0.1
 */