package com.google.android.finsky.installer;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.android.RapidAutoUpdateHelper;
import com.google.android.RapidAutoUpdateHelper.AutoUpdateData;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.analytics.PlayStore.AutoUpdateData;
import com.google.android.finsky.api.DfeServerError;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.AppPermissionAdapter;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PermissionBucket;
import com.google.android.finsky.utils.PermissionData;
import com.google.android.finsky.utils.PermissionsBucketer;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.DisplayMessageError;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class InstallPolicies
{
  private static final List<String> DEBUG_FORCE_LARGE_SIZE;
  private static final List<String> DEBUG_FORCE_PERMISSIONS;
  public static final boolean SUPPORTS_MOBILE_HOTSPOT;
  public final AppStates mAppStates;
  public final ConnectivityManager mConnectivityManager;
  private final InstallerDataStore mInstallerDataStore;
  private final Libraries mLibraries;
  public long mMaxBytesOverMobile = ((Long)G.downloadBytesOverMobileMaximum.get()).longValue();
  public long mMaxBytesOverMobileRecommended = ((Long)G.downloadBytesOverMobileRecommended.get()).longValue();
  private final PackageManager mPackageManager;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_MOBILE_HOTSPOT = bool;
      DEBUG_FORCE_LARGE_SIZE = new ArrayList();
      DEBUG_FORCE_PERMISSIONS = new ArrayList();
      return;
    }
  }
  
  public InstallPolicies(ContentResolver paramContentResolver, PackageManager paramPackageManager, AppStates paramAppStates, InstallerDataStore paramInstallerDataStore, Libraries paramLibraries)
  {
    for (;;)
    {
      try
      {
        long l2 = Settings.Secure.getLong(paramContentResolver, "download_manager_max_bytes_over_mobile");
        if ((l2 > 0L) && (l2 < this.mMaxBytesOverMobile)) {
          this.mMaxBytesOverMobile = l2;
        }
      }
      catch (Settings.SettingNotFoundException localSettingNotFoundException1)
      {
        long l1;
        continue;
      }
      try
      {
        l1 = Settings.Secure.getLong(paramContentResolver, "download_manager_recommended_max_bytes_over_mobile");
        if ((l1 > 0L) && (l1 < this.mMaxBytesOverMobileRecommended)) {
          this.mMaxBytesOverMobileRecommended = l1;
        }
      }
      catch (Settings.SettingNotFoundException localSettingNotFoundException2) {}
    }
    this.mMaxBytesOverMobileRecommended = Math.min(this.mMaxBytesOverMobileRecommended, this.mMaxBytesOverMobile);
    this.mPackageManager = paramPackageManager;
    this.mConnectivityManager = ((ConnectivityManager)FinskyApp.get().getSystemService("connectivity"));
    this.mAppStates = paramAppStates;
    this.mInstallerDataStore = paramInstallerDataStore;
    this.mLibraries = paramLibraries;
  }
  
  private long getAutoUpdateSizeLimit()
  {
    if (isMobileNetwork()) {
      return this.mMaxBytesOverMobileRecommended;
    }
    return 9223372036854775807L;
  }
  
  public static Set<String> getForegroundPackages(Context paramContext)
  {
    HashSet localHashSet = new HashSet();
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (((PowerManager)paramContext.getSystemService("power")).isScreenOn())
    {
      List localList2 = localActivityManager.getRecentTasks(1, 1);
      if (localList2.size() > 0)
      {
        ActivityManager.RecentTaskInfo localRecentTaskInfo = (ActivityManager.RecentTaskInfo)localList2.get(0);
        if (localRecentTaskInfo.baseIntent != null)
        {
          ComponentName localComponentName = localRecentTaskInfo.baseIntent.getComponent();
          if (localComponentName != null) {
            localHashSet.add(localComponentName.getPackageName());
          }
        }
      }
    }
    if (((Boolean)G.autoUpdateExcludeForegroundServices.get()).booleanValue())
    {
      List localList1 = localActivityManager.getRunningServices(2147483647);
      if (localList1 != null)
      {
        int i = localList1.size();
        for (int j = 0; j < i; j++)
        {
          ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localList1.get(j);
          if (localRunningServiceInfo.foreground) {
            localHashSet.add(localRunningServiceInfo.service.getPackageName());
          }
        }
      }
    }
    return localHashSet;
  }
  
  private InstallWarnings getInstallWarningsForDocument(long paramLong, Document paramDocument, boolean paramBoolean)
  {
    AppDetails localAppDetails = paramDocument.getAppDetails();
    String str = localAppDetails.packageName;
    int i;
    InstallWarnings localInstallWarnings;
    long l;
    label90:
    AppStates.AppState localAppState;
    int j;
    if ((Build.VERSION.SDK_INT > 22) && (paramDocument.getTargetSdk() > 22))
    {
      i = 1;
      localInstallWarnings = new InstallWarnings();
      if (DEBUG_FORCE_LARGE_SIZE.contains(str))
      {
        FinskyLog.w("Forcing true for size limit for package %s", new Object[] { str });
        localInstallWarnings.largeDownload = true;
      }
      if (!localAppDetails.hasInstallationSize) {
        break label189;
      }
      l = localAppDetails.installationSize;
      if (l >= paramLong) {
        localInstallWarnings.largeDownload = true;
      }
      localAppState = this.mAppStates.getApp(localAppDetails.packageName);
      if ((localAppState != null) && (localAppState.packageManagerState != null)) {
        break label195;
      }
      j = 1;
      label133:
      if (i == 0) {
        break label201;
      }
      localInstallWarnings.newPermissions = false;
    }
    label385:
    for (;;)
    {
      if ((j == 0) && (paramBoolean) && (localAppState.installerData != null) && (localAppState.installerData.autoUpdate == 2)) {
        localInstallWarnings.autoUpdateDisabled = true;
      }
      return localInstallWarnings;
      i = 0;
      break;
      label189:
      l = 0L;
      break label90;
      label195:
      j = 0;
      break label133;
      label201:
      int k;
      label297:
      int n;
      if (j == 0)
      {
        Set localSet = AppPermissionAdapter.loadLocalAssetPermissions(AppPermissionAdapter.getPackageInfo(this.mPackageManager, str));
        boolean bool = PermissionsBucketer.hasAcceptedBuckets(FinskyApp.get().mInstallerDataStore, str);
        PermissionData localPermissionData = PermissionsBucketer.getPermissionBuckets(localAppDetails.permission, localSet, bool);
        if ((!DEBUG_FORCE_PERMISSIONS.contains(str)) && (!localPermissionData.mForcePermissionPrompt))
        {
          PermissionBucket localPermissionBucket1 = localPermissionData.mPermissionsBuckets[localPermissionData.mOtherBucketIndex];
          if ((localPermissionBucket1 == null) || (!localPermissionBucket1.hasNewPermissions())) {
            break label369;
          }
          k = 1;
          if (k == 0)
          {
            PermissionBucket[] arrayOfPermissionBucket = localPermissionData.mPermissionsBuckets;
            int m = arrayOfPermissionBucket.length;
            n = 0;
            label317:
            if (n >= m) {
              break label381;
            }
            PermissionBucket localPermissionBucket2 = arrayOfPermissionBucket[n];
            if ((localPermissionBucket2 == null) || (localPermissionBucket2.hasExistingPermissions()) || (!localPermissionBucket2.hasNewPermissions())) {
              break label375;
            }
          }
        }
      }
      label369:
      label375:
      label381:
      for (int i1 = 1;; i1 = 0)
      {
        if (i1 == 0) {
          break label385;
        }
        localInstallWarnings.newPermissions = true;
        break;
        k = 0;
        break label297;
        n++;
        break label317;
      }
    }
  }
  
  @TargetApi(18)
  public static boolean isFreeSpaceSufficient(long paramLong, File paramFile, ContentResolver paramContentResolver)
  {
    StatFs localStatFs = new StatFs(paramFile.getAbsolutePath());
    long l2;
    if (Build.VERSION.SDK_INT >= 18) {
      l2 = localStatFs.getAvailableBytes();
    }
    long l4;
    long l1;
    for (long l3 = localStatFs.getTotalBytes();; l3 = l1 * localStatFs.getBlockCount())
    {
      l4 = ((Long)G.downloadFreeSpaceThresholdBytes.get()).longValue();
      if (l4 <= 0L) {
        break;
      }
      if (l2 - paramLong * ((Integer)G.downloadFreeSpaceApkSizeFactor.get()).intValue() / 100L < l4) {
        break label196;
      }
      return true;
      l1 = localStatFs.getBlockSize();
      l2 = l1 * localStatFs.getAvailableBlocks();
    }
    int i;
    if (Build.VERSION.SDK_INT >= 17) {
      i = Settings.Global.getInt(paramContentResolver, "sys_storage_threshold_percentage", 10);
    }
    for (long l5 = Settings.Global.getLong(paramContentResolver, "sys_storage_threshold_max_bytes", 524288000L);; l5 = Settings.Secure.getLong(paramContentResolver, "sys_storage_threshold_max_bytes", 524288000L))
    {
      l4 = Math.min(l5, l3 * i / 100L);
      break;
      i = Settings.Secure.getInt(paramContentResolver, "sys_storage_threshold_percentage", 10);
    }
    label196:
    return false;
  }
  
  public static int volleyErrorToInstallerError(VolleyError paramVolleyError)
  {
    if ((paramVolleyError instanceof AuthFailureError)) {
      return 920;
    }
    if ((paramVolleyError instanceof DisplayMessageError))
    {
      if ((paramVolleyError instanceof DfeServerError)) {
        return 922;
      }
      return 921;
    }
    if ((paramVolleyError instanceof NetworkError))
    {
      if ((paramVolleyError instanceof NoConnectionError)) {
        return 924;
      }
      return 923;
    }
    if ((paramVolleyError instanceof ParseError)) {
      return 925;
    }
    if ((paramVolleyError instanceof ServerError)) {
      return 926;
    }
    if ((paramVolleyError instanceof TimeoutError)) {
      return 927;
    }
    return 928;
  }
  
  public final boolean canUpdateApp(PackageStateRepository.PackageState paramPackageState, Document paramDocument)
  {
    if (paramPackageState == null) {}
    int i;
    int j;
    String str;
    do
    {
      do
      {
        return false;
        if (!this.mLibraries.isLoaded())
        {
          FinskyLog.wtf("Library not loaded.", new Object[0]);
          return false;
        }
        i = paramPackageState.installedVersion;
        j = paramDocument.getAppDetails().versionCode;
      } while (paramPackageState.isDisabled);
      str = paramPackageState.packageName;
    } while (j <= i);
    if (!LibraryUtils.isAvailable(paramDocument, null, this.mLibraries))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(paramDocument.getAvailabilityRestriction());
      FinskyLog.d("Cannot update unavailable app: pkg=%s,restriction=%d", arrayOfObject);
      return false;
    }
    return true;
  }
  
  public final void captureEverExternallyHosted(Document paramDocument)
  {
    if (paramDocument == null)
    {
      FinskyLog.wtf("Null document provided", new Object[0]);
      return;
    }
    AppDetails localAppDetails = paramDocument.getAppDetails();
    if (localAppDetails == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocument.mDocument.backendDocid;
      FinskyLog.wtf("Null app details provided for %s", arrayOfObject);
      return;
    }
    String str = localAppDetails.packageName;
    if (!localAppDetails.hasEverExternallyHosted)
    {
      FinskyLog.w("No everExternallyHosted provided for %s", new Object[] { str });
      return;
    }
    captureEverExternallyHosted(str, localAppDetails.everExternallyHosted);
  }
  
  public final void captureEverExternallyHosted(String paramString, boolean paramBoolean)
  {
    AppStates.AppState localAppState = this.mAppStates.getApp(paramString);
    if ((localAppState == null) || (localAppState.packageManagerState == null)) {
      FinskyLog.d("Presetting external-hosting status for uninstalled %s", new Object[] { paramString });
    }
    InstallerDataStore.InstallerData localInstallerData;
    int i;
    if (localAppState == null)
    {
      localInstallerData = null;
      if (localInstallerData != null) {
        break label117;
      }
      i = 0;
      label49:
      if (!paramBoolean) {
        break label127;
      }
    }
    label117:
    label127:
    for (int j = 0x4 | i | 0x2;; j = 0x4 | i & 0xFFFFFFFD)
    {
      if (j != i)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Boolean.valueOf(paramBoolean);
        arrayOfObject[1] = paramString;
        FinskyLog.d("Capturing ever-externally-hosted %b for %s", arrayOfObject);
        this.mInstallerDataStore.setPersistentFlags(paramString, j);
      }
      return;
      localInstallerData = localAppState.installerData;
      break;
      i = localInstallerData.persistentFlags;
      break label49;
    }
  }
  
  public final List<Document> getApplicationsEligibleForAutoUpdate(List<Document> paramList, boolean paramBoolean)
  {
    Object localObject;
    if (!this.mLibraries.isLoaded())
    {
      FinskyLog.wtf("Library not loaded.", new Object[0]);
      localObject = Collections.emptyList();
    }
    for (;;)
    {
      return localObject;
      long l = getAutoUpdateSizeLimit();
      Set localSet = getForegroundPackages(FinskyApp.get());
      localObject = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Document localDocument = (Document)localIterator.next();
        AppDetails localAppDetails = localDocument.getAppDetails();
        String str = localAppDetails.packageName;
        AppStates.AppState localAppState = this.mAppStates.getApp(str);
        if ((localAppState == null) || (localAppState.packageManagerState == null))
        {
          FinskyLog.w("Server thinks we have an asset that we don't have : %s", new Object[] { str });
        }
        else if (localAppDetails.versionCode > localAppState.packageManagerState.installedVersion)
        {
          InstallWarnings localInstallWarnings = getInstallWarningsForDocument(l, localDocument, true);
          if (RapidAutoUpdateHelper.isParticipating(str))
          {
            if ((paramBoolean) && (localInstallWarnings.warningRequired()))
            {
              RapidAutoUpdateHelper.AutoUpdateData localAutoUpdateData = RapidAutoUpdateHelper.getAutoUpdateData(str);
              if ((localAutoUpdateData != null) && ((0x4 & localAutoUpdateData.flags) != 0))
              {
                if ((localInstallWarnings.newPermissions) && ((0x2 & localAutoUpdateData.flags) == 2)) {
                  localInstallWarnings.newPermissions = false;
                }
                localInstallWarnings.autoUpdateDisabled = false;
              }
            }
            if (localInstallWarnings.warningRequired())
            {
              PlayStore.AppData localAppData2 = new PlayStore.AppData();
              localAppData2.version = localAppDetails.versionCode;
              localAppData2.hasVersion = true;
              localAppData2.oldVersion = localAppState.packageManagerState.installedVersion;
              localAppData2.hasOldVersion = true;
              localAppData2.systemApp = localAppState.packageManagerState.isSystemApp;
              localAppData2.hasSystemApp = true;
              PlayStore.AutoUpdateData localAutoUpdateData2 = new PlayStore.AutoUpdateData();
              localAutoUpdateData2.hasSkippedDueToLargeDownload = localInstallWarnings.largeDownload;
              localAutoUpdateData2.skippedDueToLargeDownload = localInstallWarnings.largeDownload;
              localAutoUpdateData2.hasSkippedDueToDisabledByUser = localInstallWarnings.autoUpdateDisabled;
              localAutoUpdateData2.skippedDueToDisabledByUser = localInstallWarnings.autoUpdateDisabled;
              localAutoUpdateData2.hasSkippedDueToNewPermission = localInstallWarnings.newPermissions;
              localAutoUpdateData2.skippedDueToNewPermission = localInstallWarnings.newPermissions;
              FinskyApp.get().getEventLogger().logAutoUpdateData(str, localAutoUpdateData2, null, localAppData2);
              continue;
            }
          }
          if (!localInstallWarnings.warningRequired()) {
            if ((((Boolean)G.autoUpdateExcludeRunningPackagesPre.get()).booleanValue()) && (localSet.contains(str)))
            {
              FinskyLog.w("Exclude auto-update for foreground package: %s", new Object[] { str });
              PlayStore.AppData localAppData1 = new PlayStore.AppData();
              localAppData1.version = localAppDetails.versionCode;
              localAppData1.hasVersion = true;
              localAppData1.oldVersion = localAppState.packageManagerState.installedVersion;
              localAppData1.hasOldVersion = true;
              localAppData1.systemApp = localAppState.packageManagerState.isSystemApp;
              localAppData1.hasSystemApp = true;
              PlayStore.AutoUpdateData localAutoUpdateData1 = new PlayStore.AutoUpdateData();
              localAutoUpdateData1.skippedDueToForeground = true;
              localAutoUpdateData1.hasSkippedDueToForeground = true;
              FinskyApp.get().getEventLogger().logAutoUpdateData(str, localAutoUpdateData1, null, localAppData1);
            }
            else
            {
              ((List)localObject).add(localDocument);
            }
          }
        }
      }
    }
  }
  
  public final List<Document> getApplicationsEligibleForNewUpdateNotification(List<Document> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Document localDocument = (Document)localIterator.next();
      AppDetails localAppDetails = localDocument.getAppDetails();
      InstallerDataStore.InstallerData localInstallerData = this.mAppStates.mStateStore.get(localAppDetails.packageName);
      if ((this.mAppStates.getApp(localAppDetails.packageName).installerData == null) || (localAppDetails.versionCode > localInstallerData.lastNotifiedVersion)) {
        localArrayList.add(localDocument);
      }
    }
    return localArrayList;
  }
  
  public final List<Document> getApplicationsWithUpdates(List<Document> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Document localDocument = (Document)localIterator.next();
      String str = localDocument.getAppDetails().packageName;
      if (canUpdateApp(this.mAppStates.mPackageManager.get(str), localDocument)) {
        localArrayList.add(localDocument);
      }
    }
    return localArrayList;
  }
  
  public final List<Document> getAppsThatRequireUpdateWarnings$7fbb50a7(List<Document> paramList)
  {
    long l = getAutoUpdateSizeLimit();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Document localDocument = (Document)localIterator.next();
      if (getInstallWarningsForDocument(l, localDocument, true).warningRequired()) {
        localArrayList.add(localDocument);
      }
    }
    return localArrayList;
  }
  
  public final InstallWarnings getUpdateWarningsForDocument(Document paramDocument, boolean paramBoolean)
  {
    return getInstallWarningsForDocument(getAutoUpdateSizeLimit(), paramDocument, paramBoolean);
  }
  
  public final boolean hasMobileNetwork()
  {
    NetworkInfo localNetworkInfo = this.mConnectivityManager.getNetworkInfo(0);
    boolean bool = false;
    if (localNetworkInfo != null) {
      bool = true;
    }
    return bool;
  }
  
  public final boolean hasNetwork()
  {
    NetworkInfo localNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
  }
  
  public final boolean isMobileNetwork()
  {
    NetworkInfo localNetworkInfo = this.mConnectivityManager.getNetworkInfo(1);
    return (localNetworkInfo == null) || (!localNetworkInfo.isConnected());
  }
  
  public final boolean isWifiNetwork()
  {
    NetworkInfo localNetworkInfo = this.mConnectivityManager.getNetworkInfo(1);
    return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
  }
  
  public static final class InstallWarnings
  {
    public boolean autoUpdateDisabled = false;
    public boolean largeDownload = false;
    public boolean newPermissions = false;
    
    public final boolean warningRequired()
    {
      return (this.autoUpdateDisabled) || (this.largeDownload) || (this.newPermissions);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.InstallPolicies
 * JD-Core Version:    0.7.0.1
 */