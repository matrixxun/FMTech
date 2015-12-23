package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class VendingPreferences
{
  public static final String[] ACCOUNT_SPECIFIC_PREFIXES;
  public static PreferenceFile.SharedPreference<Boolean> AUTO_ADD_SHORTCUTS;
  @Deprecated
  public static PreferenceFile.SharedPreference<Boolean> AUTO_UPDATE_BY_DEFAULT;
  @Deprecated
  public static PreferenceFile.SharedPreference<Boolean> AUTO_UPDATE_WIFI_ONLY;
  public static PreferenceFile.SharedPreference<Boolean> BACKED_UP;
  public static PreferenceFile.SharedPreference<String> CACHED_GL_EXTENSIONS;
  private static final String[] EMPTY_STRING_ARRAY;
  public static PreferenceFile.SharedPreference<Boolean> GL_DRIVER_CRASHED;
  public static PreferenceFile.SharedPreference<Boolean> INTEREST_BASED_ADS_ENABLED;
  public static PreferenceFile.SharedPreference<String> LAST_BUILD_FINGERPRINT;
  public static PreferenceFile.SharedPreference<Boolean> NOTIFY_UPDATES;
  public static PreferenceFile.SharedPreference<Boolean> NOTIFY_UPDATES_COMPLETION;
  public static PreferenceFile.SharedPreference<String> RESTORED_ANDROID_ID;
  private static PreferenceFile sPrefs;
  
  static
  {
    boolean bool = true;
    EMPTY_STRING_ARRAY = new String[0];
    String[] arrayOfString = new String[5];
    arrayOfString[0] = "last_checkin_hash_";
    arrayOfString[bool] = "last_systems_apps_hash_";
    arrayOfString[2] = "last_market_alarm_timeout_";
    arrayOfString[3] = "last_market_alarm_start_time_";
    arrayOfString[4] = "account_exists_";
    ACCOUNT_SPECIFIC_PREFIXES = arrayOfString;
    PreferenceFile localPreferenceFile1 = new PreferenceFile("vending_preferences");
    sPrefs = localPreferenceFile1;
    CACHED_GL_EXTENSIONS = localPreferenceFile1.value("cached_gl_extensions", null);
    GL_DRIVER_CRASHED = sPrefs.value("gl_driver_crashed", Boolean.valueOf(false));
    LAST_BUILD_FINGERPRINT = sPrefs.value("last_build_fingerprint", null);
    BACKED_UP = sPrefs.value("finsky_backed_up", Boolean.valueOf(false));
    RESTORED_ANDROID_ID = sPrefs.value("finsky_restored_android_id", null);
    INTEREST_BASED_ADS_ENABLED = sPrefs.value("ads_interest_based", Boolean.valueOf(bool));
    NOTIFY_UPDATES = sPrefs.value("notify_updates", Boolean.valueOf(bool));
    NOTIFY_UPDATES_COMPLETION = sPrefs.value("notify_updates_completion", Boolean.valueOf(bool));
    AUTO_UPDATE_WIFI_ONLY = sPrefs.value("update_over_wifi_only", Boolean.valueOf(false));
    AUTO_UPDATE_BY_DEFAULT = sPrefs.value("auto_update_default", Boolean.valueOf(false));
    PreferenceFile localPreferenceFile2 = sPrefs;
    if (Build.VERSION.SDK_INT >= 11) {}
    for (;;)
    {
      AUTO_ADD_SHORTCUTS = localPreferenceFile2.value("auto_add_shortcuts", Boolean.valueOf(bool));
      return;
      bool = false;
    }
  }
  
  public static void clear()
  {
    sPrefs.clear();
  }
  
  private static PreferenceFile.SharedPreference<Boolean> getAccountExists(String paramString)
  {
    return sPrefs.value("account_exists_" + paramString, Boolean.valueOf(false));
  }
  
  public static PreferenceFile getMainPrefs()
  {
    return sPrefs;
  }
  
  public static PreferenceFile.SharedPreference<Long> getMarketAlarmStartTime(String paramString)
  {
    return sPrefs.value("last_market_alarm_start_time_" + paramString, Long.valueOf(0L));
  }
  
  public static PreferenceFile.SharedPreference<Long> getMarketAlarmTimeout(String paramString)
  {
    return sPrefs.value("last_market_alarm_timeout_" + paramString, Long.valueOf(0L));
  }
  
  public static String[] getNewAccounts(Account[] paramArrayOfAccount)
  {
    ArrayList localArrayList = null;
    int i = paramArrayOfAccount.length;
    for (int j = 0; j < i; j++)
    {
      Account localAccount = paramArrayOfAccount[j];
      if (!getAccountExists(localAccount.name).exists())
      {
        if (localArrayList == null) {
          localArrayList = Lists.newArrayList(1);
        }
        localArrayList.add(localAccount.name);
      }
    }
    if (localArrayList == null) {
      return EMPTY_STRING_ARRAY;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static void saveCurrentAccountList(Account[] paramArrayOfAccount)
  {
    SharedPreferences localSharedPreferences = sPrefs.open();
    Map localMap = localSharedPreferences.getAll();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    int i = 0;
    Iterator localIterator = localMap.keySet().iterator();
    label140:
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      if (str1.startsWith("account_exists_"))
      {
        String str2 = str1.substring(15);
        int m = paramArrayOfAccount.length;
        int n = 0;
        label85:
        if (n < m) {
          if (!paramArrayOfAccount[n].name.equals(str2)) {}
        }
        for (int i1 = 1;; i1 = 0)
        {
          if (i1 != 0) {
            break label140;
          }
          localEditor.remove(str1);
          i = 1;
          break;
          n++;
          break label85;
        }
      }
    }
    if (i != 0) {
      PreferenceFile.commit(localEditor);
    }
    int j = paramArrayOfAccount.length;
    for (int k = 0; k < j; k++)
    {
      Account localAccount = paramArrayOfAccount[k];
      if (!getAccountExists(localAccount.name).exists()) {
        getAccountExists(localAccount.name).put(Boolean.valueOf(true));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.VendingPreferences
 * JD-Core Version:    0.7.0.1
 */