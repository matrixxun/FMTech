package com.google.android.finsky.autoupdate;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiWayUpdateController;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.StoreTypeValidator;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class UpdateChecker
{
  protected final AppStates mAppStates;
  protected final Context mContext;
  protected final DfeApiProvider mDfeApiProvider;
  MultiWayUpdateController mDfeModel;
  protected final GearheadStateMonitor mGearheadStateMonitor;
  protected final InstallPolicies mInstallPolicies;
  protected final Installer mInstaller;
  protected final Libraries mLibraries;
  protected final Notifier mNotifier;
  
  protected UpdateChecker(Context paramContext, Libraries paramLibraries, AppStates paramAppStates, DfeApiProvider paramDfeApiProvider, InstallPolicies paramInstallPolicies, Installer paramInstaller, Notifier paramNotifier, GearheadStateMonitor paramGearheadStateMonitor)
  {
    this.mContext = paramContext;
    this.mLibraries = paramLibraries;
    this.mAppStates = paramAppStates;
    this.mDfeApiProvider = paramDfeApiProvider;
    this.mInstallPolicies = paramInstallPolicies;
    this.mInstaller = paramInstaller;
    this.mNotifier = paramNotifier;
    this.mGearheadStateMonitor = paramGearheadStateMonitor;
  }
  
  public static UpdateChecker create()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    if (localFinskyApp.getExperiments().isEnabled(12603193L)) {
      return new UpdateCheckerImpl(localFinskyApp, localFinskyApp.mLibraries, localFinskyApp.mAppStates, localFinskyApp.getDfeApiProvider(), localFinskyApp.mInstallPolicies, localFinskyApp.mInstaller, localFinskyApp.mNotificationHelper, localFinskyApp.mPackageStateRepository, getReschedulerStrategy(), localFinskyApp.getExperiments(), localFinskyApp.getEventLogger(), GearheadStateMonitor.getInstance());
    }
    return new UpdateCheckerLegacyImpl(localFinskyApp, localFinskyApp.mLibraries, localFinskyApp.mAppStates, localFinskyApp.getDfeApiProvider(), localFinskyApp.mInstallPolicies, localFinskyApp.mInstaller, localFinskyApp.mNotificationHelper, GearheadStateMonitor.getInstance());
  }
  
  protected static ReschedulerStrategy getReschedulerStrategy()
  {
    if ((Build.VERSION.SDK_INT >= 21) && (((Long)G.autoUpdateJobSchedulerMaxTimeoutMs.get()).longValue() > 0L)) {
      return new ReschedulerUsingJobScheduler(FinskyApp.get());
    }
    if ((Build.VERSION.SDK_INT < 21) && (((Boolean)G.enableAutoUpdatePowerAndWifiBroadcastListener.get()).booleanValue()) && (FinskyApp.get().getExperiments().isEnabled(12602735L))) {
      return new ReschedulerUsingBroadcast();
    }
    return new ReschedulerUsingAlarmManager();
  }
  
  public static void migrateAllAppsToUseGlobalUpdateSetting$49bdfac8(AppStates paramAppStates, boolean paramBoolean, String paramString)
  {
    FinskyApp.get().getEventLogger().logBackgroundEvent(404, null, paramString, 0, null, null);
    WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = paramAppStates.mStateStore;
    Iterator localIterator = localWriteThroughInstallerDataStore.getAll().iterator();
    while (localIterator.hasNext())
    {
      InstallerDataStore.InstallerData localInstallerData = (InstallerDataStore.InstallerData)localIterator.next();
      if (!GmsCoreHelper.isGmsCore(localInstallerData.packageName))
      {
        int i = localInstallerData.autoUpdate;
        if ((paramBoolean) || (i == 0))
        {
          String str = localInstallerData.packageName;
          localWriteThroughInstallerDataStore.setAutoUpdate(str, 1);
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str;
          arrayOfObject[1] = Integer.valueOf(1);
          FinskyLog.d("Migrate %s autoupdate from default to %d", arrayOfObject);
        }
      }
    }
  }
  
  public static void migrateAutoUpdateSettings(AppStates paramAppStates)
  {
    
    if (!paramAppStates.mStateStore.isLoaded()) {
      FinskyLog.wtf("Require loaded app states to migrate auto-update state.", new Object[0]);
    }
    boolean bool1 = VendingPreferences.AUTO_UPDATE_BY_DEFAULT.exists();
    boolean bool2 = FinskyPreferences.autoUpdateEnabled.exists();
    if ((!bool1) && (bool2)) {
      return;
    }
    if ((!bool1) && (!bool2) && (FinskyApp.get().mVersionCodeOfLastRun == -1))
    {
      FinskyPreferences.autoUpdateEnabled.put(Boolean.valueOf(true));
      FinskyPreferences.autoUpdateWifiOnly.put(Boolean.valueOf(true));
      return;
    }
    boolean bool3 = ((Boolean)VendingPreferences.AUTO_UPDATE_BY_DEFAULT.get()).booleanValue();
    FinskyPreferences.autoUpdateEnabled.put(Boolean.valueOf(bool3));
    if (!FinskyPreferences.autoUpdateWifiOnly.exists())
    {
      boolean bool5 = true;
      if (VendingPreferences.AUTO_UPDATE_WIFI_ONLY.exists()) {
        bool5 = ((Boolean)VendingPreferences.AUTO_UPDATE_WIFI_ONLY.get()).booleanValue();
      }
      FinskyPreferences.autoUpdateWifiOnly.put(Boolean.valueOf(bool5));
    }
    boolean bool4 = false;
    if (!bool3) {
      bool4 = true;
    }
    migrateAllAppsToUseGlobalUpdateSetting$49bdfac8(paramAppStates, bool4, "version");
    VendingPreferences.AUTO_UPDATE_BY_DEFAULT.remove();
    VendingPreferences.AUTO_UPDATE_WIFI_ONLY.remove();
    FinskyPreferences.hadPreJsAutoUpdateSettings.put(Boolean.valueOf(true));
  }
  
  static void notifyListener(AutoUpdateCompletionStatusListener paramAutoUpdateCompletionStatusListener, boolean paramBoolean)
  {
    if (paramAutoUpdateCompletionStatusListener != null) {
      paramAutoUpdateCompletionStatusListener.updateCheckComplete(paramBoolean);
    }
  }
  
  public final void checkForUpdates(final AutoUpdateCompletionStatusListener paramAutoUpdateCompletionStatusListener, final String paramString, final boolean paramBoolean)
  {
    if (!StoreTypeValidator.isValid(this.mContext))
    {
      FinskyLog.d("Skipping update checks because the store is invalid.", new Object[0]);
      notifyListener(paramAutoUpdateCompletionStatusListener, false);
      return;
    }
    final Account localAccount = FinskyApp.get().getCurrentAccount();
    if (localAccount == null)
    {
      notifyListener(paramAutoUpdateCompletionStatusListener, true);
      return;
    }
    if (!this.mAppStates.mStateStore.isLoaded())
    {
      FinskyLog.wtf("Require loaded app states to perform update check.", new Object[0]);
      notifyListener(paramAutoUpdateCompletionStatusListener, false);
      return;
    }
    if (!this.mLibraries.isLoaded())
    {
      FinskyLog.wtf("Require loaded libraries to perform update check.", new Object[0]);
      notifyListener(paramAutoUpdateCompletionStatusListener, false);
      return;
    }
    if (!this.mGearheadStateMonitor.isReady())
    {
      FinskyLog.wtf("Require initialized Gearhead monitor to perform update check.", new Object[0]);
      notifyListener(paramAutoUpdateCompletionStatusListener, false);
      return;
    }
    migrateAutoUpdateSettings(this.mAppStates);
    new GmsCoreHelper(this.mLibraries, this.mAppStates, this.mContext, this.mGearheadStateMonitor);
    new AsyncTask() {}.execute(new Void[0]);
  }
  
  protected abstract void doAutoUpdate(List<Document> paramList, String paramString, boolean paramBoolean);
  
  protected final PlayStore.AppData getAppData(AppDetails paramAppDetails)
  {
    String str = paramAppDetails.packageName;
    AppStates.AppState localAppState = this.mAppStates.getApp(str);
    PlayStore.AppData localAppData = new PlayStore.AppData();
    localAppData.version = paramAppDetails.versionCode;
    localAppData.hasVersion = true;
    localAppData.oldVersion = localAppState.packageManagerState.installedVersion;
    localAppData.hasOldVersion = true;
    localAppData.systemApp = localAppState.packageManagerState.isSystemApp;
    localAppData.hasSystemApp = true;
    return localAppData;
  }
  
  public static abstract interface AutoUpdateCompletionStatusListener
  {
    public abstract void updateCheckComplete(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.UpdateChecker
 * JD-Core Version:    0.7.0.1
 */