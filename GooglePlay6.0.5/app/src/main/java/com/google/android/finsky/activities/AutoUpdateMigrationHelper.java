package com.google.android.finsky.activities;

import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyPreferences;

public final class AutoUpdateMigrationHelper
{
  static final void updateDialogTracking()
  {
    int i = 1 + ((Integer)FinskyPreferences.autoUpdateMigrationDialogShownCount.get()).intValue();
    FinskyPreferences.autoUpdateMigrationDialogShownCount.put(Integer.valueOf(i));
    FinskyPreferences.lastUpdateMigrationDialogTimeShown.put(Long.valueOf(System.currentTimeMillis()));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AutoUpdateMigrationHelper
 * JD-Core Version:    0.7.0.1
 */