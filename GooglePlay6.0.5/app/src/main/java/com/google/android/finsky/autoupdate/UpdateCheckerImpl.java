package com.google.android.finsky.autoupdate;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AutoUpdateData;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.InstallPolicies.InstallWarnings;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class UpdateCheckerImpl
  extends UpdateChecker
{
  private final FinskyEventLog mEventLog;
  private final FinskyExperiments mExperiments;
  private Set<String> mForegroundApps;
  private long mIgnoreHoldOffTriggers;
  private boolean mInstallDeferred;
  private String mLogReason;
  private int mNumDeferred;
  private int mNumInstalled;
  private final PackageStateRepository mPackageStateRepository;
  private final ReschedulerStrategy mReschedulerStrategy;
  
  public UpdateCheckerImpl(Context paramContext, Libraries paramLibraries, AppStates paramAppStates, DfeApiProvider paramDfeApiProvider, InstallPolicies paramInstallPolicies, Installer paramInstaller, Notifier paramNotifier, PackageStateRepository paramPackageStateRepository, ReschedulerStrategy paramReschedulerStrategy, FinskyExperiments paramFinskyExperiments, FinskyEventLog paramFinskyEventLog, GearheadStateMonitor paramGearheadStateMonitor)
  {
    super(paramContext, paramLibraries, paramAppStates, paramDfeApiProvider, paramInstallPolicies, paramInstaller, paramNotifier, paramGearheadStateMonitor);
    this.mReschedulerStrategy = paramReschedulerStrategy;
    this.mPackageStateRepository = paramPackageStateRepository;
    this.mExperiments = paramFinskyExperiments;
    this.mEventLog = paramFinskyEventLog;
  }
  
  private void applyPolicies(List<AutoUpdateEntry> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicAutoUpdatePolicy());
    localArrayList.add(new RapidAutoUpdatePolicy(this.mExperiments));
    localArrayList.add(new GmsCoreAutoUpdatePolicy());
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
      int k = 0;
      int m = localArrayList.size();
      while (k < m)
      {
        ((AutoUpdatePolicy)localArrayList.get(k)).apply(localAutoUpdateEntry);
        k++;
      }
      i++;
    }
  }
  
  private void checkHoldOffTriggers(List<AutoUpdateEntry> paramList)
  {
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
      PackageStateRepository.PackageState localPackageState = localAutoUpdateEntry.mPackageState;
      InstallPolicies.InstallWarnings localInstallWarnings = this.mInstallPolicies.getUpdateWarningsForDocument(localAutoUpdateEntry.mDocument, true);
      if (((0x1 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (localInstallWarnings.largeDownload)) {
        localAutoUpdateEntry.mHoldOffReason = (0x1 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x2 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (localInstallWarnings.newPermissions)) {
        localAutoUpdateEntry.mHoldOffReason = (0x2 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x4 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (localInstallWarnings.autoUpdateDisabled)) {
        localAutoUpdateEntry.mHoldOffReason = (0x4 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x20 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (this.mForegroundApps.contains(localAutoUpdateEntry.mDocument.getAppDetails().packageName))) {
        localAutoUpdateEntry.mHoldOffReason = (0x20 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x10 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && ((0x10 & this.mIgnoreHoldOffTriggers) == 0L)) {
        localAutoUpdateEntry.mHoldOffReason = (0x10 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x40 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (this.mGearheadStateMonitor.isProjecting())) {
        localAutoUpdateEntry.mHoldOffReason = (0x40 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x80 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (localPackageState.isDisabledByUser)) {
        localAutoUpdateEntry.mHoldOffReason = (0x80 | localAutoUpdateEntry.mHoldOffReason);
      }
      if (((0x8 & localAutoUpdateEntry.mHoldOffTrigger) != 0) && (!((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue())) {
        localAutoUpdateEntry.mHoldOffReason = (0x8 | localAutoUpdateEntry.mHoldOffReason);
      }
      i++;
    }
  }
  
  private void install(List<AutoUpdateEntry> paramList)
  {
    int i = 0;
    int j = paramList.size();
    if (i < j)
    {
      AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
      boolean bool1;
      label47:
      boolean bool2;
      label60:
      boolean bool3;
      label73:
      int k;
      if (localAutoUpdateEntry.mHoldOffReason == 0) {
        if ((0x1 & localAutoUpdateEntry.mInstallOptions) != 0)
        {
          bool1 = true;
          if ((0x2 & localAutoUpdateEntry.mInstallOptions) == 0) {
            break label227;
          }
          bool2 = true;
          if ((0x4 & localAutoUpdateEntry.mInstallOptions) == 0) {
            break label233;
          }
          bool3 = true;
          if ((0x8 & localAutoUpdateEntry.mInstallOptions) == 0) {
            break label239;
          }
          k = 1;
          label87:
          if (k != 0) {
            this.mInstaller.setMobileDataProhibited(localAutoUpdateEntry.mDocument.getAppDetails().packageName);
          }
          this.mInstaller.setVisibility(localAutoUpdateEntry.mDocument.getAppDetails().packageName, bool1, bool2, bool3);
          this.mInstaller.updateSingleInstalledApp(localAutoUpdateEntry.mDocument.getAppDetails().packageName, localAutoUpdateEntry.mDocument.getVersionCode(), localAutoUpdateEntry.mDocument.mDocument.title, localAutoUpdateEntry.mInstallReason, this.mInstallDeferred, localAutoUpdateEntry.mInstallPriority, this.mInstaller.extractInstallLocation(localAutoUpdateEntry.mDocument));
          this.mNumInstalled = (1 + this.mNumInstalled);
        }
      }
      for (;;)
      {
        i++;
        break;
        bool1 = false;
        break label47;
        label227:
        bool2 = false;
        break label60;
        label233:
        bool3 = false;
        break label73;
        label239:
        k = 0;
        break label87;
        this.mNumDeferred = (1 + this.mNumDeferred);
      }
    }
  }
  
  private void log(List<AutoUpdateEntry> paramList)
  {
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
      if (((0x1 & localAutoUpdateEntry.mLoggingOptions) != 0) && (localAutoUpdateEntry.mHoldOffReason != 0))
      {
        PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
        if ((0x8 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToGlobalDisabled = true;
          localAutoUpdateData.skippedDueToGlobalDisabled = true;
        }
        if ((0x10 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToWifi = true;
          localAutoUpdateData.skippedDueToWifi = true;
        }
        if ((0x1 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToLargeDownload = true;
          localAutoUpdateData.skippedDueToLargeDownload = true;
        }
        if ((0x2 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToNewPermission = true;
          localAutoUpdateData.skippedDueToNewPermission = true;
        }
        if ((0x40 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToProjection = true;
          localAutoUpdateData.skippedDueToProjection = true;
        }
        if ((0x4 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToDisabledByUser = true;
          localAutoUpdateData.skippedDueToDisabledByUser = true;
        }
        if ((0x20 & localAutoUpdateEntry.mHoldOffReason) != 0)
        {
          localAutoUpdateData.hasSkippedDueToForeground = true;
          localAutoUpdateData.skippedDueToForeground = true;
        }
        AppDetails localAppDetails = localAutoUpdateEntry.mDocument.getAppDetails();
        this.mEventLog.logAutoUpdateData(localAppDetails.packageName, localAutoUpdateData, this.mLogReason, getAppData(localAppDetails));
      }
      i++;
    }
  }
  
  private void notifyPendingUpdates(List<AutoUpdateEntry> paramList)
  {
    if (paramList.isEmpty()) {}
    ArrayList localArrayList1;
    label248:
    int k;
    long l;
    do
    {
      ArrayList localArrayList2;
      ArrayList localArrayList3;
      ArrayList localArrayList4;
      do
      {
        return;
        localArrayList1 = new ArrayList();
        localArrayList2 = new ArrayList();
        localArrayList3 = new ArrayList();
        localArrayList4 = new ArrayList();
        int i = 0;
        int j = paramList.size();
        if (i < j)
        {
          AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
          if ((localAutoUpdateEntry.mHoldOffReason != 0) && ((0x1 & localAutoUpdateEntry.mPendingUpdateNotificationOptions) == 0))
          {
            localArrayList1.add(localAutoUpdateEntry.mDocument);
            if ((localAutoUpdateEntry.mHoldOffReason & AutoUpdateEntry.REASONS_USER_APPROVAL_REQUIRED) == 0) {
              break label248;
            }
          }
          for (int i2 = 1;; i2 = 0)
          {
            if (i2 != 0) {
              localArrayList3.add(localAutoUpdateEntry.mDocument);
            }
            AppDetails localAppDetails = localAutoUpdateEntry.mDocument.getAppDetails();
            String str = localAppDetails.packageName;
            InstallerDataStore.InstallerData localInstallerData = this.mAppStates.mStateStore.get(localAppDetails.packageName);
            int i3 = localAppDetails.versionCode;
            if ((localInstallerData == null) || (i3 > localInstallerData.lastNotifiedVersion))
            {
              this.mAppStates.mStateStore.setLastNotifiedVersion(str, i3);
              localArrayList2.add(localAutoUpdateEntry.mDocument);
              if (i2 != 0) {
                localArrayList4.add(localAutoUpdateEntry.mDocument);
              }
            }
            i++;
            break;
          }
        }
      } while ((localArrayList1.isEmpty()) || (!((Boolean)VendingPreferences.NOTIFY_UPDATES.get()).booleanValue()));
      this.mNotifier.hideUpdatesAvailableMessage();
      boolean bool = ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue();
      k = localArrayList1.size();
      int m = localArrayList3.size();
      int n = localArrayList2.size();
      if (localArrayList4.size() > 0) {}
      for (int i1 = 1;; i1 = 0)
      {
        l = System.currentTimeMillis() - ((Long)FinskyPreferences.lastUpdateAvailNotificationTimestampMs.get()).longValue();
        if ((bool) || (n <= 0)) {
          break;
        }
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Integer.valueOf(n);
        arrayOfObject3[1] = Integer.valueOf(k);
        FinskyLog.d("Notifying user that [%d/%d] applications have new updates.", arrayOfObject3);
        this.mNotifier.showNewUpdatesAvailableMessage(localArrayList2, k);
        FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
        return;
      }
      if ((bool) && (i1 != 0))
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(m);
        arrayOfObject2[1] = Integer.valueOf(k);
        FinskyLog.d("Notifying user [%d/%d] applications have updates that require approval.", arrayOfObject2);
        this.mNotifier.showUpdatesNeedApprovalMessage(localArrayList3, k);
        FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
        return;
      }
    } while (l <= ((Long)G.outstandingNotificationTimeDelayMs.get()).longValue());
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(k);
    FinskyLog.d("Notifying user that %d applications have outstanding updates.", arrayOfObject1);
    this.mNotifier.showOutstandingUpdatesMessage(localArrayList1);
    FinskyPreferences.lastUpdateAvailNotificationTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
  }
  
  private void reschedulePendingUpdates(List<AutoUpdateEntry> paramList)
  {
    int i = 0;
    int j = paramList.size();
    for (;;)
    {
      if (i < j)
      {
        AutoUpdateEntry localAutoUpdateEntry = (AutoUpdateEntry)paramList.get(i);
        if ((localAutoUpdateEntry.mHoldOffReason != 0) && ((0xFFFFFFAF & localAutoUpdateEntry.mHoldOffReason) == 0))
        {
          if (((Long)FinskyPreferences.autoUpdatesDiscoveredAtMs.get()).longValue() <= 0L) {
            FinskyPreferences.autoUpdatesDiscoveredAtMs.put(Long.valueOf(System.currentTimeMillis()));
          }
          this.mReschedulerStrategy.schedule();
        }
      }
      else
      {
        return;
      }
      i++;
    }
  }
  
  protected final void doAutoUpdate(List<Document> paramList, String paramString, boolean paramBoolean)
  {
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      this.mInstallPolicies.captureEverExternallyHosted((Document)paramList.get(i));
      i++;
    }
    long l = 0L;
    if ((paramBoolean) || (this.mReschedulerStrategy.canUpdateNow())) {
      l = 16L;
    }
    this.mLogReason = paramString;
    this.mIgnoreHoldOffTriggers = l;
    this.mForegroundApps = InstallPolicies.getForegroundPackages(FinskyApp.get());
    if ((((Boolean)FinskyPreferences.autoUpdateFirstTimeForAccounts.get()).booleanValue()) && (((Long)G.autoUpdateDeliveryHoldoffMs.get()).longValue() > 0L)) {}
    ArrayList localArrayList;
    for (boolean bool = true;; bool = false)
    {
      this.mInstallDeferred = bool;
      FinskyPreferences.autoUpdateFirstTimeForAccounts.put(Boolean.valueOf(false));
      this.mNumInstalled = 0;
      this.mNumDeferred = 0;
      FinskyPreferences.autoUpdatesDiscoveredAtMs.put(Long.valueOf(0L));
      List localList = this.mInstallPolicies.getApplicationsWithUpdates(paramList);
      localArrayList = new ArrayList(localList.size());
      int k = localList.size();
      for (int m = 0; m < k; m++)
      {
        Document localDocument = (Document)localList.get(m);
        localArrayList.add(new AutoUpdateEntry(localDocument, this.mPackageStateRepository.get(localDocument.getAppDetails().packageName)));
      }
    }
    applyPolicies(localArrayList);
    Collections.sort(localArrayList, AutoUpdateEntry.INSTALL_ORDER);
    checkHoldOffTriggers(localArrayList);
    install(localArrayList);
    log(localArrayList);
    notifyPendingUpdates(localArrayList);
    reschedulePendingUpdates(localArrayList);
    if ((this.mNumInstalled > 0) && (this.mInstallDeferred))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.mNumInstalled);
      arrayOfObject[1] = G.autoUpdateDeliveryHoldoffMs.get();
      FinskyLog.d("Auto-update of %d packages will defer for %d ms", arrayOfObject);
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
      {
        public final void run()
        {
          UpdateCheckerImpl.this.mInstaller.startDeferredInstalls();
        }
      }, ((Long)G.autoUpdateDeliveryHoldoffMs.get()).longValue());
    }
    PlayStore.AutoUpdateData localAutoUpdateData = new PlayStore.AutoUpdateData();
    localAutoUpdateData.hasNumPackagesInstalled = true;
    localAutoUpdateData.numPackagesInstalled = this.mNumInstalled;
    localAutoUpdateData.hasNumPackagesDeferred = true;
    localAutoUpdateData.numPackagesDeferred = this.mNumDeferred;
    this.mEventLog.logAutoUpdateData(null, localAutoUpdateData, this.mLogReason, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.UpdateCheckerImpl
 * JD-Core Version:    0.7.0.1
 */