package com.google.android.finsky.autoupdate;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.RapidAutoUpdateHelper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.analytics.PlayStore.AutoUpdateData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UpdateCheckerLegacyImpl
  extends UpdateChecker
{
  public UpdateCheckerLegacyImpl(Context paramContext, Libraries paramLibraries, AppStates paramAppStates, DfeApiProvider paramDfeApiProvider, InstallPolicies paramInstallPolicies, Installer paramInstaller, Notifier paramNotifier, GearheadStateMonitor paramGearheadStateMonitor)
  {
    super(paramContext, paramLibraries, paramAppStates, paramDfeApiProvider, paramInstallPolicies, paramInstaller, paramNotifier, paramGearheadStateMonitor);
  }
  
  private void handleUpdates(List<Document> paramList, String paramString, boolean paramBoolean)
  {
    if ((((Boolean)FinskyPreferences.autoUpdateFirstTimeForAccounts.get()).booleanValue()) && (((Long)G.autoUpdateDeliveryHoldoffMs.get()).longValue() > 0L)) {}
    PreferenceFile.SharedPreference localSharedPreference;
    List localList1;
    int i;
    int j;
    List localList4;
    ArrayList localArrayList3;
    for (boolean bool1 = true;; bool1 = false)
    {
      FinskyPreferences.autoUpdateFirstTimeForAccounts.put(Boolean.valueOf(false));
      localSharedPreference = FinskyPreferences.autoUpdatesDiscoveredAtMs;
      localList1 = this.mInstallPolicies.getApplicationsWithUpdates(paramList);
      i = localList1.size();
      boolean bool2 = FinskyApp.get().getExperiments().isEnabled(12603067L);
      j = 0;
      if (bool2)
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        RapidAutoUpdateHelper.extractRapidAutoUpdateApplications(localList1, this.mAppStates, localArrayList1, localArrayList2);
        List localList2 = this.mInstallPolicies.getApplicationsEligibleForAutoUpdate(localArrayList1, true);
        boolean bool3 = localList2.isEmpty();
        j = 0;
        if (!bool3)
        {
          this.mInstaller.updateInstalledApps$189ce961(localList2, "rapid_auto_update", bool1, false, true, true);
          j = 0 + localList2.size();
        }
        List localList3 = this.mInstallPolicies.getApplicationsEligibleForAutoUpdate(localArrayList2, true);
        if (!localList3.isEmpty())
        {
          this.mInstaller.updateInstalledApps$189ce961(localList3, "rapid_auto_update", bool1, false, false, false);
          j += localList3.size();
        }
      }
      localList4 = this.mInstallPolicies.getApplicationsEligibleForAutoUpdate(localList1, false);
      localArrayList3 = new ArrayList();
      Iterator localIterator = localList4.iterator();
      while (localIterator.hasNext())
      {
        Document localDocument = (Document)localIterator.next();
        if (RapidAutoUpdateHelper.isParticipating(localDocument.getAppDetails().packageName)) {
          localArrayList3.add(localDocument);
        }
      }
    }
    List localList6;
    boolean bool6;
    int k;
    int m;
    int i1;
    label582:
    long l;
    if (localList4.isEmpty())
    {
      localSharedPreference.put(Long.valueOf(0L));
      if ((j > 0) && (bool1))
      {
        Object[] arrayOfObject4 = new Object[2];
        arrayOfObject4[0] = Integer.valueOf(j);
        arrayOfObject4[1] = G.autoUpdateDeliveryHoldoffMs.get();
        FinskyLog.d("Auto-update of %d packages will defer for %d ms", arrayOfObject4);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
          public final void run()
          {
            UpdateCheckerLegacyImpl.this.mInstaller.startDeferredInstalls();
          }
        }, ((Long)G.autoUpdateDeliveryHoldoffMs.get()).longValue());
      }
      PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
      localAutoUpdateData.hasNumPackagesInstalled = true;
      localAutoUpdateData.numPackagesInstalled = j;
      localAutoUpdateData.hasNumPackagesDeferred = true;
      localAutoUpdateData.numPackagesDeferred = (i - j);
      FinskyApp.get().getEventLogger().logAutoUpdateData(null, localAutoUpdateData, paramString, null);
      if (!localList1.isEmpty())
      {
        List localList5 = this.mInstallPolicies.getApplicationsEligibleForNewUpdateNotification(localList1);
        markAppsAsNotified(localList5);
        if (((Boolean)VendingPreferences.NOTIFY_UPDATES.get()).booleanValue())
        {
          localList6 = this.mInstallPolicies.getAppsThatRequireUpdateWarnings$7fbb50a7(localList1);
          List localList7 = this.mInstallPolicies.getAppsThatRequireUpdateWarnings$7fbb50a7(localList5);
          bool6 = ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue();
          k = localList1.size();
          m = localList6.size();
          int n = localList5.size();
          if (localList7.size() <= 0) {
            break label856;
          }
          i1 = 1;
          l = System.currentTimeMillis() - ((Long)FinskyPreferences.lastUpdateAvailNotificationTimestampMs.get()).longValue();
          if ((bool6) || (n <= 0)) {
            break label862;
          }
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = Integer.valueOf(n);
          arrayOfObject3[1] = Integer.valueOf(k);
          FinskyLog.d("Notifying user that [%d/%d] applications have new updates.", arrayOfObject3);
          this.mNotifier.showNewUpdatesAvailableMessage(localList5, k);
          FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
        }
      }
    }
    label856:
    label862:
    do
    {
      return;
      if (!((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue())
      {
        localSharedPreference.put(Long.valueOf(0L));
        logRapidUpdateDocumentHeldbackGlobalDisable(paramString, localArrayList3);
        break;
      }
      if (((Long)localSharedPreference.get()).longValue() <= 0L) {
        localSharedPreference.put(Long.valueOf(System.currentTimeMillis()));
      }
      ReschedulerStrategy localReschedulerStrategy = getReschedulerStrategy();
      if ((paramBoolean) || (localReschedulerStrategy.canUpdateNow()))
      {
        boolean bool4 = ((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue();
        Installer localInstaller = this.mInstaller;
        boolean bool5 = ((Boolean)VendingPreferences.NOTIFY_UPDATES_COMPLETION.get()).booleanValue();
        localInstaller.updateInstalledApps$189ce961(localList4, "auto_update", bool1, bool4, true, bool5);
        j += localList4.size();
        localList1.removeAll(localList4);
        this.mNotifier.hideUpdatesAvailableMessage();
        localSharedPreference.put(Long.valueOf(0L));
        break;
      }
      logRapidUpdateDocumentHeldback(paramString, localArrayList3);
      localReschedulerStrategy.schedule();
      break;
      i1 = 0;
      break label582;
      if ((bool6) && (i1 != 0))
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(m);
        arrayOfObject2[1] = Integer.valueOf(k);
        FinskyLog.d("Notifying user [%d/%d] applications have updates that require approval.", arrayOfObject2);
        this.mNotifier.showUpdatesNeedApprovalMessage(localList6, k);
        FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
        return;
      }
    } while (l <= ((Long)G.outstandingNotificationTimeDelayMs.get()).longValue());
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(k);
    FinskyLog.d("Notifying user that %d applications have outstanding updates.", arrayOfObject1);
    this.mNotifier.showOutstandingUpdatesMessage(localList1);
    FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
  }
  
  private void logRapidUpdateDocumentHeldback(String paramString, List<Document> paramList)
  {
    if (paramList.isEmpty()) {
      return;
    }
    PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
    localAutoUpdateData.skippedDueToPower = ReschedulerStrategy.shouldWaitForPower();
    localAutoUpdateData.hasSkippedDueToPower = localAutoUpdateData.skippedDueToPower;
    localAutoUpdateData.skippedDueToWifi = ReschedulerStrategy.shouldWaitForWifi();
    localAutoUpdateData.hasSkippedDueToWifi = localAutoUpdateData.skippedDueToWifi;
    if ((!localAutoUpdateData.skippedDueToPower) && (!localAutoUpdateData.skippedDueToWifi)) {}
    for (boolean bool = true;; bool = false)
    {
      localAutoUpdateData.skippedDueToProjection = bool;
      localAutoUpdateData.hasSkippedDueToProjection = localAutoUpdateData.skippedDueToProjection;
      FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        AppDetails localAppDetails = ((Document)localIterator.next()).getAppDetails();
        localFinskyEventLog.logAutoUpdateData(localAppDetails.packageName, localAutoUpdateData, paramString, getAppData(localAppDetails));
      }
      break;
    }
  }
  
  private void logRapidUpdateDocumentHeldbackGlobalDisable(String paramString, List<Document> paramList)
  {
    if (paramList.isEmpty()) {}
    for (;;)
    {
      return;
      PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
      localAutoUpdateData.skippedDueToGlobalDisabled = true;
      localAutoUpdateData.hasSkippedDueToGlobalDisabled = true;
      FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        AppDetails localAppDetails = ((Document)localIterator.next()).getAppDetails();
        localFinskyEventLog.logAutoUpdateData(localAppDetails.packageName, localAutoUpdateData, paramString, getAppData(localAppDetails));
      }
    }
  }
  
  private void markAppsAsNotified(List<Document> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      AppDetails localAppDetails = ((Document)localIterator.next()).getAppDetails();
      String str = localAppDetails.packageName;
      InstallerDataStore.InstallerData localInstallerData = this.mAppStates.mStateStore.get(localAppDetails.packageName);
      int i = localAppDetails.versionCode;
      if ((localInstallerData == null) || (i > localInstallerData.lastNotifiedVersion)) {
        this.mAppStates.mStateStore.setLastNotifiedVersion(str, i);
      }
    }
  }
  
  protected final void doAutoUpdate(List<Document> paramList, String paramString, boolean paramBoolean)
  {
    GmsCoreHelper localGmsCoreHelper = new GmsCoreHelper(this.mLibraries, this.mAppStates, this.mContext, this.mGearheadStateMonitor);
    Iterator localIterator1 = paramList.iterator();
    Document localDocument2;
    while (localIterator1.hasNext())
    {
      localDocument2 = (Document)localIterator1.next();
      if (GmsCoreHelper.isGmsCore(localDocument2))
      {
        if (GmsCoreHelper.isAutoUpdateEnabled()) {
          break label128;
        }
        FinskyLog.d("GMS Core auto update is disabled", new Object[0]);
      }
    }
    label128:
    AppDetails localAppDetails;
    PackageStateRepository.PackageState localPackageState;
    for (;;)
    {
      localIterator1.remove();
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        Document localDocument1 = (Document)localIterator2.next();
        this.mInstallPolicies.captureEverExternallyHosted(localDocument1);
      }
      if (!LibraryUtils.isAvailable(localDocument2, null, localGmsCoreHelper.mLibraries))
      {
        FinskyLog.d("GMS Core is not available", new Object[0]);
      }
      else
      {
        localAppDetails = localDocument2.getAppDetails();
        if (localAppDetails == null)
        {
          FinskyLog.wtf("Unable to install something without app details", new Object[0]);
        }
        else
        {
          localPackageState = FinskyApp.get().mPackageStateRepository.get("com.google.android.gms");
          if (localPackageState == null) {
            break label685;
          }
          if (!localPackageState.isDisabledByUser) {
            break;
          }
          FinskyLog.d("Not updating com.google.android.gms (disabled)", new Object[0]);
        }
      }
    }
    int i = localPackageState.installedVersion;
    int j;
    if ((localPackageState.isSystemApp) && (!localPackageState.isUpdatedSystemApp)) {
      j = 1;
    }
    for (;;)
    {
      if ((i == -1) || (j != 0))
      {
        InstallerDataStore.InstallerData localInstallerData = FinskyApp.get().mInstallerDataStore.get("com.google.android.gms");
        if ((localInstallerData != null) && (localInstallerData.autoUpdate == 2))
        {
          FinskyLog.d("Not updating com.google.android.gms (was removed)", new Object[0]);
          break;
          j = 0;
          continue;
        }
      }
      if (i >= localAppDetails.versionCode) {
        break;
      }
      if (((Boolean)G.gmsCoreAutoUpdateProjectionHoldoff.get()).booleanValue())
      {
        if (!localGmsCoreHelper.mGearheadStateMonitor.isReady())
        {
          FinskyLog.wtf("Require initialized Gearhead monitor to perform update check.", new Object[0]);
          break;
        }
        if (localGmsCoreHelper.mGearheadStateMonitor.isProjecting())
        {
          FinskyLog.d("GMS Core auto-update skipped while projecting", new Object[0]);
          PlayStore.AppData localAppData = new PlayStore.AppData();
          localAppData.version = localAppDetails.versionCode;
          localAppData.hasVersion = true;
          localAppData.oldVersion = i;
          localAppData.hasOldVersion = true;
          boolean bool1 = false;
          if (localPackageState != null)
          {
            boolean bool2 = localPackageState.isSystemApp;
            bool1 = false;
            if (bool2) {
              bool1 = true;
            }
          }
          localAppData.systemApp = bool1;
          localAppData.hasSystemApp = true;
          PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
          localAutoUpdateData.skippedDueToProjection = true;
          localAutoUpdateData.hasSkippedDueToProjection = true;
          FinskyApp.get().getEventLogger().logAutoUpdateData("com.google.android.gms", localAutoUpdateData, null, localAppData);
          break;
        }
      }
      Installer localInstaller = FinskyApp.get().mInstaller;
      localInstaller.setMobileDataAllowed("com.google.android.gms");
      localInstaller.setVisibility("com.google.android.gms", false, false, false);
      String str = AppActionAnalyzer.getAppDetailsAccount("com.google.android.gms", FinskyApp.get().getCurrentAccountName(), localGmsCoreHelper.mAppStates, localGmsCoreHelper.mLibraries);
      Account localAccount = AccountHandler.findAccount(str, localGmsCoreHelper.mContext);
      if (localAccount == null)
      {
        FinskyLog.d("Cannot update com.google.android.gms because cannot determine update account " + FinskyLog.scrubPii(str), new Object[0]);
        break;
      }
      AccountLibrary localAccountLibrary = localGmsCoreHelper.mLibraries.getAccountLibrary(localAccount);
      if (localAccountLibrary == null)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = FinskyLog.scrubPii(str);
        FinskyLog.d("Cannot update com.google.android.gms because cannot find library for %s.", arrayOfObject);
        break;
      }
      if (!LibraryUtils.isOwned(localDocument2, localAccountLibrary))
      {
        PurchaseInitiator.makeFreePurchase(localAccount, localDocument2, 1, null, null, true, false);
        break;
      }
      PurchaseInitiator.initiateDownload(localAccount, localDocument2);
      break;
      handleUpdates(paramList, paramString, paramBoolean);
      return;
      label685:
      i = -1;
      j = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.UpdateCheckerLegacyImpl
 * JD-Core Version:    0.7.0.1
 */