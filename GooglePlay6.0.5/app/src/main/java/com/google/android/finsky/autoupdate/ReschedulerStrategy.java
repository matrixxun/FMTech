package com.google.android.finsky.autoupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.net.ConnectivityManagerCompat;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AutoUpdateData;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.config.GservicesValue;

public abstract class ReschedulerStrategy
{
  protected static void continueToUpdateChecks(UpdateChecker.AutoUpdateCompletionStatusListener paramAutoUpdateCompletionStatusListener, boolean paramBoolean)
  {
    UpdateChecker.create().checkForUpdates(paramAutoUpdateCompletionStatusListener, "wifi_checker", paramBoolean);
  }
  
  protected static void loadPrerequisites(Runnable paramRunnable)
  {
    final PowerManager.WakeLock localWakeLock = ((PowerManager)FinskyApp.get().getSystemService("power")).newWakeLock(1, "ReschedulerStrategy");
    Runnable local1 = new Runnable()
    {
      private int mLoaded;
      
      public final void run()
      {
        this.mLoaded = (1 + this.mLoaded);
        if (this.mLoaded == 3)
        {
          this.val$onPrerequisitesLoaded.run();
          localWakeLock.release();
        }
      }
    };
    localWakeLock.acquire();
    FinskyApp.get().mLibraries.load(local1);
    FinskyApp.get().mAppStates.load(local1);
    GearheadStateMonitor.getInstance().initialize(local1);
  }
  
  protected static void logAutoUpdateRescheduled(Integer paramInteger, boolean paramBoolean)
  {
    PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
    localAutoUpdateData.rescheduled = true;
    localAutoUpdateData.hasRescheduled = true;
    localAutoUpdateData.skippedDueToPower = shouldWaitForPower();
    localAutoUpdateData.hasSkippedDueToPower = localAutoUpdateData.skippedDueToPower;
    localAutoUpdateData.skippedDueToWifi = shouldWaitForWifi();
    localAutoUpdateData.hasSkippedDueToWifi = localAutoUpdateData.skippedDueToWifi;
    localAutoUpdateData.skippedDueToProjection = paramBoolean;
    localAutoUpdateData.hasSkippedDueToProjection = localAutoUpdateData.skippedDueToProjection;
    if (paramInteger != null)
    {
      localAutoUpdateData.recheckState = paramInteger.intValue();
      localAutoUpdateData.hasRecheckState = true;
    }
    FinskyApp.get().getEventLogger().logAutoUpdateData(null, localAutoUpdateData, "wifi_checker", null);
  }
  
  protected static void logAutoUpdateRescheduled(boolean paramBoolean)
  {
    logAutoUpdateRescheduled(null, paramBoolean);
  }
  
  public static boolean shouldWaitForPower()
  {
    if (FinskyApp.get().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("plugged", 0) != 0) {
      return false;
    }
    return System.currentTimeMillis() - ((Long)FinskyPreferences.autoUpdatesDiscoveredAtMs.get()).longValue() <= ((Long)G.autoUpdateSkipPowerCheckIntervalMs.get()).longValue();
  }
  
  protected static boolean shouldWaitForPowerOrWifi()
  {
    return (shouldWaitForPower()) || (shouldWaitForWifi());
  }
  
  public static boolean shouldWaitForWifi()
  {
    if ((FinskyApp.get().mInstallPolicies.hasMobileNetwork()) && (!((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue())) {
      return false;
    }
    InstallPolicies localInstallPolicies = FinskyApp.get().mInstallPolicies;
    if (localInstallPolicies.isWifiNetwork())
    {
      int j;
      if (InstallPolicies.SUPPORTS_MOBILE_HOTSPOT) {
        if ((localInstallPolicies.isWifiNetwork()) && (localInstallPolicies.mConnectivityManager.isActiveNetworkMetered())) {
          j = 1;
        }
      }
      while (j == 0)
      {
        return false;
        j = 0;
        continue;
        j = 0;
      }
    }
    if (UiUtils.isAndroidTv())
    {
      if ((localInstallPolicies.hasNetwork()) && (!ConnectivityManagerCompat.isActiveNetworkMetered(localInstallPolicies.mConnectivityManager))) {}
      for (int i = 1; i != 0; i = 0) {
        return false;
      }
    }
    return true;
  }
  
  public abstract boolean canUpdateNow();
  
  public abstract void schedule();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.ReschedulerStrategy
 * JD-Core Version:    0.7.0.1
 */