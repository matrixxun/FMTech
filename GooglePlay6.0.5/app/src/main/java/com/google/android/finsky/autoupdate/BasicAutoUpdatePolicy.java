package com.google.android.finsky.autoupdate;

import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;

public final class BasicAutoUpdatePolicy
  implements AutoUpdatePolicy
{
  public final void apply(AutoUpdateEntry paramAutoUpdateEntry)
  {
    paramAutoUpdateEntry.mHoldOffTrigger = (0x5F | paramAutoUpdateEntry.mHoldOffTrigger);
    if (((Boolean)G.autoUpdateExcludeRunningPackagesPre.get()).booleanValue()) {
      paramAutoUpdateEntry.mHoldOffTrigger = (0x20 | paramAutoUpdateEntry.mHoldOffTrigger);
    }
    if (((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue()) {
      paramAutoUpdateEntry.mInstallOptions = (0x8 | paramAutoUpdateEntry.mInstallOptions);
    }
    paramAutoUpdateEntry.mInstallOptions = (0x1 | paramAutoUpdateEntry.mInstallOptions);
    if (((Boolean)VendingPreferences.NOTIFY_UPDATES_COMPLETION.get()).booleanValue()) {
      paramAutoUpdateEntry.mInstallOptions = (0x4 | paramAutoUpdateEntry.mInstallOptions);
    }
    paramAutoUpdateEntry.mInstallReason = "auto_update";
    paramAutoUpdateEntry.mInstallPriority = 3;
    paramAutoUpdateEntry.mInstallOrder = 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.BasicAutoUpdatePolicy
 * JD-Core Version:    0.7.0.1
 */