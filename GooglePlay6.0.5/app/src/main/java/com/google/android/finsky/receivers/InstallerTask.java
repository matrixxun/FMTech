package com.google.android.finsky.receivers;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadManagerConstants;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.Storage;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.PackageInstallerFacade;
import com.google.android.finsky.installer.PackageInstallerFacade.InstallListener;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.local.AssetUtils;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.protos.AndroidAppPatchData;
import com.google.android.finsky.protos.DeliveryResponse;
import com.google.android.finsky.protos.HttpCookie;
import com.google.android.finsky.protos.SplitDeliveryData;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.Sha1Util.DigestResult;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Users;
import com.google.android.finsky.utils.Users.UserManagerFacade;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.DisplayMessageError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

final class InstallerTask
{
  static Installer.InstallerProgressReport PROGRESS_INSTALLING = new Installer.InstallerProgressReport(3, 0L, 0L, 0);
  static Installer.InstallerProgressReport PROGRESS_NOT_TRACKED;
  private static final String[] SUPPORTED_PATCH_FORMATS = { "1", "2", "3" };
  private SplitDeliveryData mActiveSplit = null;
  String mActiveSplitId = null;
  long mApkCompleted;
  private long mApkSize;
  final AppStates mAppStates;
  private List<String> mCompletedSplits = new ArrayList();
  final DownloadQueue mDownloadQueue;
  int mDownloadStatus;
  private final InstallPolicies mInstallPolicies;
  private final InstallerImpl mInstaller;
  final InstallerDataStore mInstallerDataStore;
  private boolean mIsUpdate;
  PlayStore.AppData mLogAppData;
  private boolean mMobileDataAllowed;
  final Notifier mNotifier;
  private Obb mObbMain;
  long mObbMainCompleted;
  private Obb mObbPatch;
  long mObbPatchCompleted;
  final PackageInstallerFacade mPackageInstaller;
  int mRecoveredIntoState;
  private List<String> mRequiredSplits = new ArrayList();
  private boolean mShowCompletionNotifications;
  boolean mShowErrorNotifications;
  private boolean mShowProgress;
  String mTitle;
  long mTotalSize;
  public final String packageName;
  
  static
  {
    PROGRESS_NOT_TRACKED = new Installer.InstallerProgressReport(0, 0L, 0L, 0);
  }
  
  public InstallerTask(String paramString, InstallerImpl paramInstallerImpl, AppStates paramAppStates, DownloadQueue paramDownloadQueue, Notifier paramNotifier, InstallPolicies paramInstallPolicies, PackageInstallerFacade paramPackageInstallerFacade)
  {
    this.packageName = paramString;
    this.mInstaller = paramInstallerImpl;
    this.mAppStates = paramAppStates;
    this.mDownloadQueue = paramDownloadQueue;
    this.mNotifier = paramNotifier;
    this.mInstallerDataStore = paramAppStates.mStateStore;
    this.mInstallPolicies = paramInstallPolicies;
    this.mPackageInstaller = paramPackageInstallerFacade;
  }
  
  private boolean canDownloadPatch(SplitDeliveryData paramSplitDeliveryData, int paramInt)
  {
    if (!this.packageName.equals(this.mActiveSplitId))
    {
      Object[] arrayOfObject10 = new Object[2];
      arrayOfObject10[0] = this.packageName;
      arrayOfObject10[1] = this.mActiveSplitId;
      FinskyLog.d("Split %s (%s) cannot be patched (yet)", arrayOfObject10);
      return false;
    }
    if ((paramInt & 0x4) != 0)
    {
      if (paramSplitDeliveryData.patchData != null) {
        return true;
      }
      Object[] arrayOfObject9 = new Object[3];
      arrayOfObject9[0] = this.packageName;
      arrayOfObject9[1] = this.mActiveSplitId;
      arrayOfObject9[2] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Missing patch for %s (%s) while is_patch set in %d", arrayOfObject9);
      return false;
    }
    if ((paramInt & 0x8) != 0) {
      return false;
    }
    AndroidAppPatchData localAndroidAppPatchData = paramSplitDeliveryData.patchData;
    if (localAndroidAppPatchData == null) {
      return false;
    }
    switch (localAndroidAppPatchData.patchFormat)
    {
    default: 
      Object[] arrayOfObject8 = new Object[3];
      arrayOfObject8[0] = this.packageName;
      arrayOfObject8[1] = this.mActiveSplitId;
      arrayOfObject8[2] = Integer.valueOf(localAndroidAppPatchData.patchFormat);
      FinskyLog.wtf("Can't download patch %s (%s) because format (%d) is unsupported", arrayOfObject8);
      return false;
    }
    PackageStateRepository.PackageState localPackageState = this.mAppStates.mPackageManager.get(this.packageName);
    if (localPackageState == null)
    {
      logPatchFailure(this.packageName, "no-base-app-installed", 0, null);
      return false;
    }
    int i = localAndroidAppPatchData.baseVersionCode;
    if (localPackageState.installedVersion != i)
    {
      logPatchFailure(this.packageName, "wrong-base-app-installed", 0, null);
      Object[] arrayOfObject7 = new Object[4];
      arrayOfObject7[0] = this.packageName;
      arrayOfObject7[1] = this.mActiveSplitId;
      arrayOfObject7[2] = Integer.valueOf(i);
      arrayOfObject7[3] = Integer.valueOf(localPackageState.installedVersion);
      FinskyLog.d("Cannot patch %s (%s), need version %d but has %d", arrayOfObject7);
      return false;
    }
    String[] arrayOfString = new String[1];
    boolean bool = isInstalledForwardLocked(this.packageName, arrayOfString);
    String str = arrayOfString[0];
    if (bool)
    {
      logPatchFailure(this.packageName, "base-app-dirs-mismatch", 0, null);
      Object[] arrayOfObject6 = new Object[3];
      arrayOfObject6[0] = this.packageName;
      arrayOfObject6[1] = this.mActiveSplitId;
      arrayOfObject6[2] = str;
      FinskyLog.d("Cannot patch %s (%s), source dir is %s", arrayOfObject6);
      return false;
    }
    long l1 = Storage.dataPartitionAvailableSpace();
    long l2 = ((Integer)G.downloadPatchFreeSpaceFactor.get()).intValue() * paramSplitDeliveryData.downloadSize / 100L;
    if (l1 < l2)
    {
      logPatchFailure(this.packageName, "free-space", 0, null);
      Object[] arrayOfObject5 = new Object[4];
      arrayOfObject5[0] = this.packageName;
      arrayOfObject5[1] = this.mActiveSplitId;
      arrayOfObject5[2] = Long.valueOf(l2);
      arrayOfObject5[3] = Long.valueOf(l1);
      FinskyLog.d("Cannot patch %s (%s), need %d, free %d", arrayOfObject5);
      return false;
    }
    File localFile = new File(str);
    if (!localFile.exists())
    {
      logPatchFailure(this.packageName, "base-file-exists", 0, null);
      Object[] arrayOfObject4 = new Object[3];
      arrayOfObject4[0] = this.packageName;
      arrayOfObject4[1] = this.mActiveSplitId;
      arrayOfObject4[2] = localFile;
      FinskyLog.d("Cannot patch %s (%s), file does not exist %s", arrayOfObject4);
      return false;
    }
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(localFile);
      Sha1Util.DigestResult localDigestResult = Sha1Util.digest(localFileInputStream);
      if (!localAndroidAppPatchData.baseSignature.equals(localDigestResult.hashBase64))
      {
        logPatchFailure(this.packageName, "base-file-signature", 0, null);
        Object[] arrayOfObject3 = new Object[4];
        arrayOfObject3[0] = this.packageName;
        arrayOfObject3[1] = this.mActiveSplitId;
        arrayOfObject3[2] = localAndroidAppPatchData.baseSignature;
        arrayOfObject3[3] = localDigestResult.hashBase64;
        FinskyLog.d("Cannot patch %s (%s), bad hash, expect %s actual %s", arrayOfObject3);
        return false;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      logPatchFailure(this.packageName, "base-file-FileNotFoundException", 0, localFileNotFoundException);
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = this.packageName;
      arrayOfObject2[1] = this.mActiveSplitId;
      arrayOfObject2[2] = localFile;
      FinskyLog.d("Cannot patch %s (%s), FileNotFoundException, %s", arrayOfObject2);
      return false;
    }
    catch (IOException localIOException)
    {
      logPatchFailure(this.packageName, "base-file-otherexception", 0, localIOException);
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = this.packageName;
      arrayOfObject1[1] = this.mActiveSplitId;
      arrayOfObject1[2] = localIOException.getClass().getSimpleName();
      FinskyLog.d("Cannot patch %s (%s), unexpected exception %s", arrayOfObject1);
      return false;
    }
    return true;
  }
  
  public static void checkForEmptyTitle(String paramString1, String paramString2, String paramString3, InstallerDataStore.InstallerData paramInstallerData)
  {
    if (!TextUtils.isEmpty(paramString3)) {
      return;
    }
    if (paramInstallerData == null)
    {
      FinskyLog.e("b/11413796 - installerData is null", new Object[0]);
      if (paramString3 == null) {
        FinskyLog.wtf("b/11413796 - %s for %s (null title)", new Object[] { paramString1, paramString2 });
      }
    }
    else
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = FinskyLog.scrubPii(paramInstallerData.accountName);
      FinskyLog.e("b/11413796 - acct:  %s", arrayOfObject1);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInstallerData.desiredVersion);
      FinskyLog.e("b/11413796 - vers:  %d", arrayOfObject2);
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = paramInstallerData.downloadUri;
      FinskyLog.e("b/11413796 - uri:   %s", arrayOfObject3);
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(paramInstallerData.flags);
      FinskyLog.e("b/11413796 - flags: %d", arrayOfObject4);
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(paramInstallerData.installerState);
      FinskyLog.e("b/11413796 - state: %d", arrayOfObject5);
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = paramInstallerData.packageName;
      FinskyLog.e("b/11413796 - pkg:   %s", arrayOfObject6);
      Object[] arrayOfObject7 = new Object[1];
      if (paramInstallerData.deliveryData != null) {}
      for (boolean bool = true;; bool = false)
      {
        arrayOfObject7[0] = Boolean.valueOf(bool);
        FinskyLog.e("b/11413796 - data?  %b", arrayOfObject7);
        break;
      }
    }
    FinskyLog.wtf("b/11413796 - %s for %s (empty title)", new Object[] { paramString1, paramString2 });
  }
  
  private static void cleanExternalStorage()
  {
    File localFile = FinskyApp.get().getExternalFilesDir(null);
    if (localFile != null)
    {
      File[] arrayOfFile = localFile.listFiles();
      if (arrayOfFile != null)
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++) {
          arrayOfFile[j].delete();
        }
      }
    }
  }
  
  private void extractObbInfo(InstallerDataStore.InstallerData paramInstallerData)
  {
    AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
    if ((localAndroidAppDeliveryData != null) && (localAndroidAppDeliveryData.additionalFile.length > 0))
    {
      this.mObbMain = AssetUtils.extractObb(localAndroidAppDeliveryData, paramInstallerData.packageName, false);
      if (this.mObbMain != null) {
        this.mObbMain.syncStateWithStorage();
      }
      this.mObbPatch = AssetUtils.extractObb(localAndroidAppDeliveryData, paramInstallerData.packageName, true);
      if (this.mObbPatch != null) {
        this.mObbPatch.syncStateWithStorage();
      }
    }
  }
  
  private Download generateObbDownload(InstallerDataStore.InstallerData paramInstallerData, Obb paramObb)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramInstallerData.title;
    String str1 = localFinskyApp.getString(2131362395, arrayOfObject);
    String str2 = paramInstallerData.packageName;
    HttpCookie localHttpCookie = localAndroidAppDeliveryData.downloadAuthCookie[0];
    Uri localUri = Uri.fromFile(paramObb.getTempFile());
    long l = localAndroidAppDeliveryData.downloadSize;
    String str3 = paramObb.getUrl();
    String str4 = localHttpCookie.name;
    String str5 = localHttpCookie.value;
    boolean bool1;
    if (!this.mMobileDataAllowed)
    {
      bool1 = true;
      if (this.mShowProgress) {
        break label146;
      }
    }
    label146:
    for (boolean bool2 = true;; bool2 = false)
    {
      return new DownloadImpl(str3, str1, str2, null, str4, str5, localUri, l, l, paramObb, bool1, bool2);
      bool1 = false;
      break;
    }
  }
  
  private String getCertHashForHiddenPackage()
  {
    if (!FinskyApp.get().mUsers.mUserManagerFacade.mayHaveHiddenPackages()) {}
    for (;;)
    {
      return null;
      PackageManager localPackageManager = FinskyApp.get().getPackageManager();
      try
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.packageName, 8256);
        if ((0x800000 & localPackageInfo.applicationInfo.flags) == 0)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = this.packageName;
          Signature[] arrayOfSignature = localPackageInfo.signatures;
          int i = 0;
          if (arrayOfSignature != null) {
            i = localPackageInfo.signatures.length;
          }
          arrayOfObject[1] = Integer.valueOf(i);
          FinskyLog.d("Found %s with %d signatures installed for another user or hidden", arrayOfObject);
          if ((localPackageInfo.signatures != null) && (localPackageInfo.signatures.length != 0))
          {
            String str = Sha1Util.secureHash(localPackageInfo.signatures[0].toByteArray());
            return str;
          }
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    }
    return null;
  }
  
  private int getInstalledVersionForHiddenPackage(AppStates.AppState paramAppState)
  {
    if (!FinskyApp.get().mUsers.mUserManagerFacade.mayHaveHiddenPackages()) {}
    for (;;)
    {
      return -1;
      if (paramAppState.packageManagerState == null)
      {
        PackageManager localPackageManager = FinskyApp.get().getPackageManager();
        try
        {
          PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.packageName, 8192);
          if ((0x800000 & localPackageInfo.applicationInfo.flags) == 0)
          {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = this.packageName;
            arrayOfObject[1] = Integer.valueOf(localPackageInfo.versionCode);
            FinskyLog.d("Found %s version %d installed for another user", arrayOfObject);
            int i = localPackageInfo.versionCode;
            return i;
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
      }
    }
    return -1;
  }
  
  private static boolean isGzippedPatch(SplitDeliveryData paramSplitDeliveryData)
  {
    if ((paramSplitDeliveryData == null) || (paramSplitDeliveryData.patchData == null)) {}
    while ((paramSplitDeliveryData.patchData.patchFormat != 2) && (paramSplitDeliveryData.patchData.patchFormat != 3)) {
      return false;
    }
    return true;
  }
  
  private static boolean isInstalledForwardLocked(String paramString, String[] paramArrayOfString)
  {
    PackageManager localPackageManager = FinskyApp.get().getPackageManager();
    try
    {
      ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(paramString, 0);
      String str1 = localApplicationInfo.sourceDir;
      String str2 = localApplicationInfo.publicSourceDir;
      if (paramArrayOfString != null) {
        paramArrayOfString[0] = str1;
      }
      boolean bool1;
      if (!TextUtils.isEmpty(str1))
      {
        boolean bool2 = str1.equals(str2);
        bool1 = false;
        if (bool2) {}
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  private boolean loadActiveSplitInfo(String paramString, AndroidAppDeliveryData paramAndroidAppDeliveryData)
  {
    Object localObject = null;
    if (paramString.equals(this.packageName))
    {
      localObject = new SplitDeliveryData();
      ((SplitDeliveryData)localObject).downloadSize = paramAndroidAppDeliveryData.downloadSize;
      ((SplitDeliveryData)localObject).gzippedDownloadSize = paramAndroidAppDeliveryData.gzippedDownloadSize;
      ((SplitDeliveryData)localObject).downloadUrl = paramAndroidAppDeliveryData.downloadUrl;
      ((SplitDeliveryData)localObject).signature = paramAndroidAppDeliveryData.signature;
      ((SplitDeliveryData)localObject).downloadUrl = paramAndroidAppDeliveryData.downloadUrl;
      ((SplitDeliveryData)localObject).gzippedDownloadUrl = paramAndroidAppDeliveryData.gzippedDownloadUrl;
      ((SplitDeliveryData)localObject).patchData = paramAndroidAppDeliveryData.patchData;
    }
    for (;;)
    {
      this.mActiveSplit = ((SplitDeliveryData)localObject);
      if (localObject == null) {
        break;
      }
      return true;
      for (SplitDeliveryData localSplitDeliveryData : paramAndroidAppDeliveryData.splitDeliveryData) {
        if (paramString.equals(localSplitDeliveryData.id)) {
          localObject = localSplitDeliveryData;
        }
      }
    }
    return false;
  }
  
  private void logCopyFailure(String paramString1, String paramString2, int paramInt, Exception paramException)
  {
    if (paramInt == 0) {
      paramInt = 963;
    }
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(127).setDocument(paramString1).setReason(paramString2).setErrorCode(paramInt).setExceptionType(paramException).setAppData(this.mLogAppData);
    FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  private void logPatchFailure(String paramString1, String paramString2, int paramInt, Exception paramException)
  {
    if (paramInt == 0) {
      paramInt = 917;
    }
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(108).setDocument(paramString1).setReason(paramString2).setErrorCode(paramInt).setExceptionType(paramException).setAppData(this.mLogAppData);
    FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  private boolean requireExternalStorageOrCancel(InstallerDataStore.InstallerData paramInstallerData)
  {
    if (Storage.externalStorageAvailable()) {
      return false;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.packageName;
    FinskyLog.e("Cancel download of %s because no external storage for OBB", arrayOfObject);
    cancel(false);
    notifyListeners(3, 901);
    FinskyApp.get().getEventLogger().logBackgroundEvent(112, this.packageName, "obb-no-external-storage", 901, null, this.mLogAppData);
    if (this.mShowErrorNotifications) {
      this.mNotifier.showExternalStorageMissing(paramInstallerData.title, this.packageName);
    }
    return true;
  }
  
  private void startNextDownload(InstallerDataStore.InstallerData paramInstallerData)
  {
    int i = paramInstallerData.installerState;
    if (i < 20) {
      i = 20;
    }
    int j = -1;
    if (this.mCompletedSplits.isEmpty())
    {
      AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
      long l = localAndroidAppDeliveryData.downloadSize;
      SplitDeliveryData[] arrayOfSplitDeliveryData = localAndroidAppDeliveryData.splitDeliveryData;
      int k = arrayOfSplitDeliveryData.length;
      for (int m = 0; m < k; m++) {
        l += arrayOfSplitDeliveryData[m].downloadSize;
      }
      if (InstallPolicies.isFreeSpaceSufficient(l, Environment.getDataDirectory(), FinskyApp.get().getContentResolver())) {}
      for (int n = 0; n != 0; n = 1)
      {
        return;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.packageName;
        FinskyLog.e("Cancel download of %s because insufficient free space", arrayOfObject2);
        cancel(false);
        notifyListeners(3, 908);
        FinskyApp.get().getEventLogger().logBackgroundEvent(112, this.packageName, "no-internal-storage", 908, null, this.mLogAppData);
        if (this.mShowErrorNotifications) {
          this.mNotifier.showInternalStorageFull(paramInstallerData.title, this.packageName);
        }
      }
    }
    if (paramInstallerData.deliveryData.encryptionParams != null)
    {
      String str = paramInstallerData.packageName;
      FinskyLog.wtf("Server sent encryption params for %s", new Object[] { str });
      cancel(false);
      logAndNotifyDownloadError(str, 913, null);
      return;
    }
    Download localDownload = null;
    switch (i)
    {
    }
    for (;;)
    {
      if (j < 0) {
        break label496;
      }
      this.mDownloadQueue.add(localDownload);
      FinskyApp.get().getEventLogger().logBackgroundEvent(100, localDownload.getDocIdForLog(), null, 0, null, this.mLogAppData);
      setInstallerState(j, null);
      return;
      if (this.mObbMain != null)
      {
        if (requireExternalStorageOrCancel(paramInstallerData)) {
          break;
        }
        this.mObbMain.syncStateWithStorage();
        if (this.mObbMain.getState() == 4)
        {
          this.mObbMain.getTempFile().delete();
          localDownload = generateObbDownload(paramInstallerData, this.mObbMain);
          j = 20;
          continue;
        }
      }
      if (this.mObbPatch != null)
      {
        if (requireExternalStorageOrCancel(paramInstallerData)) {
          break;
        }
        this.mObbPatch.syncStateWithStorage();
        if (this.mObbPatch.getState() == 4)
        {
          this.mObbPatch.getTempFile().delete();
          localDownload = generateObbDownload(paramInstallerData, this.mObbPatch);
          j = 30;
          continue;
        }
      }
      localDownload = generateDownload(paramInstallerData, null);
      if (localDownload == null)
      {
        setInstallerState(60, null);
        advanceState();
        return;
      }
      j = 40;
    }
    label496:
    Object[] arrayOfObject1 = new Object[4];
    arrayOfObject1[0] = this.packageName;
    arrayOfObject1[1] = this.mActiveSplitId;
    arrayOfObject1[2] = Integer.valueOf(paramInstallerData.installerState);
    arrayOfObject1[3] = Integer.valueOf(j);
    FinskyLog.e("Unexpected download start states for %s (%s): %d %d", arrayOfObject1);
    cancel(false);
    logAndNotifyDownloadError(this.packageName, 902, null);
  }
  
  private void updateLogWithActiveSplitInfo()
  {
    if ((this.mRequiredSplits.size() < 2) || (TextUtils.isEmpty(this.mActiveSplitId))) {
      this.mLogAppData.splitId = "";
    }
    for (this.mLogAppData.hasSplitId = false;; this.mLogAppData.hasSplitId = true)
    {
      if ((this.mActiveSplit != null) && (this.mActiveSplit.patchData != null))
      {
        this.mLogAppData.patchFormat = this.mActiveSplit.patchData.patchFormat;
        this.mLogAppData.hasPatchFormat = true;
        if (isGzippedPatch(this.mActiveSplit))
        {
          this.mLogAppData.downloadGzip = true;
          this.mLogAppData.hasDownloadGzip = true;
        }
      }
      return;
      this.mLogAppData.splitId = this.mActiveSplitId;
    }
  }
  
  public final void addAppShortcut()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    if (UiUtils.isAndroidTv()) {
      return;
    }
    if (!new AppActionAnalyzer(this.packageName, FinskyApp.get().mAppStates, FinskyApp.get().mLibraries).isLaunchable)
    {
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = this.packageName;
      FinskyLog.d("Skip shortcut for non-launchable %s", arrayOfObject4);
      return;
    }
    PackageManager localPackageManager = localFinskyApp.getPackageManager();
    try
    {
      localApplicationInfo = localPackageManager.getApplicationInfo(this.packageName, 0);
      Resources localResources = localPackageManager.getResourcesForApplication(localApplicationInfo);
      str = localResources.getResourceName(localApplicationInfo.icon);
      localIntent1 = localPackageManager.getLaunchIntentForPackage(this.packageName);
      int i = localPackageManager.getActivityInfo(localIntent1.getComponent(), 0).labelRes;
      if (i == 0) {
        break label272;
      }
      localObject = localResources.getString(i);
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      for (;;)
      {
        ApplicationInfo localApplicationInfo;
        String str;
        Intent localIntent1;
        Intent localIntent2;
        Intent.ShortcutIconResource localShortcutIconResource;
        Object[] arrayOfObject3;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.packageName;
        FinskyLog.w("Unable to load resources for %s", arrayOfObject2);
        return;
        CharSequence localCharSequence = localPackageManager.getApplicationLabel(localApplicationInfo);
        Object localObject = localCharSequence;
      }
    }
    catch (Exception localException)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.packageName;
      FinskyLog.wtf(localException, "Unable to add shortcut for %s", arrayOfObject1);
    }
    localIntent2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    localIntent2.putExtra("android.intent.extra.shortcut.NAME", (CharSequence)localObject);
    localIntent2.putExtra("android.intent.extra.shortcut.INTENT", localIntent1);
    localShortcutIconResource = new Intent.ShortcutIconResource();
    localShortcutIconResource.packageName = this.packageName;
    localShortcutIconResource.resourceName = str;
    localIntent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", localShortcutIconResource);
    localIntent2.putExtra("duplicate", false);
    localFinskyApp.sendBroadcast(localIntent2);
    arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = this.packageName;
    FinskyLog.d("Requested shortcut for %s", arrayOfObject3);
    return;
    label272:
  }
  
  final void advanceState()
  {
    final AppStates.AppState localAppState;
    label54:
    InstallerDataStore.InstallerData localInstallerData1;
    for (;;)
    {
      localAppState = this.mAppStates.getApp(this.packageName);
      if ((localAppState == null) || (localAppState.installerData == null))
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = this.packageName;
        arrayOfObject1[1] = this.mActiveSplitId;
        FinskyLog.wtf("Unexpected missing installer data for %s (%s)", arrayOfObject1);
        cancel(true);
        return;
      }
      localInstallerData1 = localAppState.installerData;
      switch (localInstallerData1.installerState)
      {
      case 25: 
      case 35: 
      case 45: 
      default: 
        Object[] arrayOfObject12 = new Object[3];
        arrayOfObject12[0] = Integer.valueOf(localInstallerData1.installerState);
        arrayOfObject12[1] = this.packageName;
        arrayOfObject12[2] = this.mActiveSplitId;
        FinskyLog.wtf("Bad state %d for %s (%s)", arrayOfObject12);
        cancel(true);
        return;
      case 0: 
        int i6 = localInstallerData1.desiredVersion;
        PackageStateRepository.PackageState localPackageState1 = localAppState.packageManagerState;
        if (localPackageState1 != null) {}
        for (int i7 = localPackageState1.installedVersion;; i7 = -1)
        {
          if (i7 < i6) {
            break label291;
          }
          if (i7 > i6) {
            this.mAppStates.mStateStore.setDesiredVersion(this.packageName, i7);
          }
          setInstallerState(70, null);
          break;
        }
        label291:
        int i8 = getInstalledVersionForHiddenPackage(localAppState);
        if (i8 < i6) {
          break label364;
        }
        if (i8 > i6) {
          this.mAppStates.mStateStore.setDesiredVersion(this.packageName, i8);
        }
        int i14 = 0x20 | localInstallerData1.flags;
        this.mInstallerDataStore.setFlags(this.packageName, i14);
        setInstallerState(60, null);
      }
    }
    label364:
    int i9;
    InstallerDataStore.InstallerData localInstallerData6;
    final String str10;
    int i10;
    if (localInstallerData1.deliveryData == null)
    {
      i9 = 0;
      if (i9 != 0) {
        break label968;
      }
      checkForEmptyTitle("requestDeliveryData", this.packageName, this.mTitle, localAppState.installerData);
      localInstallerData6 = localAppState.installerData;
      str10 = localInstallerData6.packageName;
      i10 = localInstallerData6.desiredVersion;
      if ((0x10000 & localInstallerData6.flags) == 0) {
        break label681;
      }
    }
    String str11;
    String str12;
    FinskyApp localFinskyApp;
    Account localAccount2;
    label681:
    for (int i11 = 1;; i11 = 0)
    {
      str11 = localInstallerData6.deliveryToken;
      str12 = localInstallerData6.accountForUpdate;
      localFinskyApp = FinskyApp.get();
      if (i11 != 0) {
        break label2497;
      }
      boolean bool8 = TextUtils.isEmpty(str12);
      localAccount2 = null;
      if (!bool8)
      {
        localAccount2 = AccountHandler.findAccount(str12, localFinskyApp);
        if (localAccount2 == null)
        {
          Object[] arrayOfObject11 = new Object[2];
          arrayOfObject11[0] = FinskyLog.scrubPii(str12);
          arrayOfObject11[1] = str10;
          FinskyLog.w("Account %s for update of %s no longer exists.", arrayOfObject11);
          this.mInstallerDataStore.setAccountForUpdate(str10, null);
        }
      }
      if (localAccount2 == null)
      {
        str12 = localInstallerData6.accountName;
        localAccount2 = AccountHandler.findAccount(str12, localFinskyApp);
      }
      if (localAccount2 != null) {
        break label687;
      }
      FinskyLog.d("Invalid account %s", new Object[] { str12 });
      cancel(false);
      logAndNotifyDownloadError(str10, 906, null);
      return;
      long l3 = localInstallerData1.deliveryDataTimestampMs;
      if ((l3 != 0L) && (l3 + ((Long)G.deliveryDataExpirationMs.get()).longValue() < System.currentTimeMillis()))
      {
        this.mInstallerDataStore.setDeliveryData(this.packageName, null, 0L);
        int i12 = localInstallerData1.flags;
        int i13 = i12 & 0xFFFFEDFB;
        if (i13 != i12) {
          this.mInstallerDataStore.setFlags(this.packageName, i13);
        }
        i9 = 0;
        break;
      }
      i9 = 1;
      break;
    }
    label687:
    String str13 = str12;
    for (Account localAccount1 = localAccount2;; localAccount1 = null)
    {
      PackageStateRepository.PackageState localPackageState2 = localAppState.packageManagerState;
      Integer localInteger1 = null;
      if (localPackageState2 != null)
      {
        boolean bool7 = ((Boolean)G.downloadSendBaseVersionCode.get()).booleanValue();
        localInteger1 = null;
        if (bool7) {
          localInteger1 = Integer.valueOf(localAppState.packageManagerState.installedVersion);
        }
      }
      String str14;
      Response.Listener local1;
      Response.ErrorListener local2;
      if (localAppState.packageManagerState != null)
      {
        str14 = localAppState.packageManagerState.certificateHashes[0];
        this.mPackageInstaller.reportProgress(str10, 0L, 0L);
        local1 = new Response.Listener() {};
        local2 = new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
          {
            int i = InstallPolicies.volleyErrorToInstallerError(paramAnonymousVolleyError);
            if ((paramAnonymousVolleyError instanceof DisplayMessageError)) {}
            for (String str = ((DisplayMessageError)paramAnonymousVolleyError).mDisplayErrorHtml;; str = null)
            {
              Object[] arrayOfObject = new Object[2];
              arrayOfObject[0] = Integer.valueOf(i);
              arrayOfObject[1] = str;
              FinskyLog.d("Received VolleyError %d (%s)", arrayOfObject);
              InstallerTask.this.cancel(false);
              BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(104).setDocument(str10).setErrorCode(i).setExceptionType(paramAnonymousVolleyError).setAppData(InstallerTask.this.mLogAppData);
              FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
              InstallerTask.this.notifyListeners(3, i);
              InstallerTask.this.showDownloadNotification(i, str);
              return;
            }
          }
        };
        if (i11 == 0) {
          break label911;
        }
        FinskyLog.d("Request earlyDelivery for %s", new Object[] { str10 });
        boolean bool6 = localFinskyApp.getPackageName().equals(str10);
        String str15 = null;
        if (bool6) {
          str15 = SelfUpdateScheduler.getCertificateHashSelfUpdateMD5(localFinskyApp);
        }
        DfeApi localDfeApi = localFinskyApp.getDfeApiNonAuthenticated();
        Integer localInteger2 = Integer.valueOf(i10);
        String[] arrayOfString = SUPPORTED_PATCH_FORMATS;
        localDfeApi.earlyDelivery$5b28aa05(str10, localInteger2, localInteger1, arrayOfString, str15, str14, str11, local1, local2);
      }
      for (;;)
      {
        setInstallerState(10, localInstallerData6.downloadUri);
        return;
        str14 = getCertHashForHiddenPackage();
        break;
        label911:
        byte[] arrayOfByte = localFinskyApp.mLibraries.getAccountLibrary(localAccount1).getServerToken(AccountLibrary.LIBRARY_ID_APPS);
        localFinskyApp.getDfeApi(str13).delivery$5df18dd4(str10, arrayOfByte, Integer.valueOf(i10), localInteger1, SUPPORTED_PATCH_FORMATS, null, str14, str11, local1, local2);
      }
      label968:
      processDeliveryData(localInstallerData1, false);
      populateSplitInfo(localInstallerData1);
      startNextDownload(localInstallerData1);
      return;
      final InstallerDataStore.InstallerData localInstallerData4 = localAppState.installerData;
      int i3;
      label1004:
      final InstallerDataStore.InstallerData localInstallerData5;
      final boolean bool3;
      label1034:
      final boolean bool4;
      if ((0x4 & localInstallerData4.flags) == 0)
      {
        i3 = 0;
        if (i3 != 0) {
          break label54;
        }
        localInstallerData5 = localAppState.installerData;
        int i4 = localInstallerData5.flags;
        if ((i4 & 0x200) == 0) {
          break label1335;
        }
        bool3 = true;
        if ((i4 & 0x1000) == 0) {
          break label1341;
        }
        bool4 = true;
        label1046:
        boolean bool5 = this.mPackageInstaller.requireCopy(bool4);
        if ((bool3) || (bool5)) {
          break label1347;
        }
      }
      for (int i5 = 0; i5 == 0; i5 = 1)
      {
        final long l2 = this.mActiveSplit.downloadSize;
        String str8 = this.mActiveSplit.signature;
        String str9 = localAppState.installerData.downloadUri;
        final Uri localUri3 = Uri.parse(str9);
        setInstallerState(57, str9);
        Object[] arrayOfObject9 = new Object[2];
        arrayOfObject9[0] = this.packageName;
        arrayOfObject9[1] = str9;
        FinskyLog.d("Prepare to verify %s from %s", arrayOfObject9);
        Utils.executeMultiThreaded(new AsyncTask()
        {
          private Sha1Util.DigestResult doInBackground$1a3e6397()
          {
            FinskyApp localFinskyApp = FinskyApp.get();
            try
            {
              InputStream localInputStream = localFinskyApp.getContentResolver().openInputStream(localUri3);
              Sha1Util.DigestResult localDigestResult;
              Object[] arrayOfObject1;
              Object[] arrayOfObject2;
              return null;
            }
            catch (FileNotFoundException localFileNotFoundException)
            {
              try
              {
                localDigestResult = Sha1Util.digest(localInputStream);
                return localDigestResult;
              }
              catch (IOException localIOException)
              {
                arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = InstallerTask.this.packageName;
                arrayOfObject2[1] = localIOException;
                FinskyLog.w("IOException while copying %s: %s", arrayOfObject2);
              }
              localFileNotFoundException = localFileNotFoundException;
              arrayOfObject1 = new Object[2];
              arrayOfObject1[0] = InstallerTask.this.packageName;
              arrayOfObject1[1] = localUri3;
              FinskyLog.w("FileNotFoundException for %s: %s", arrayOfObject1);
              return null;
            }
          }
        }, new Void[0]);
        return;
        if (!this.packageName.equals(this.mActiveSplitId))
        {
          Object[] arrayOfObject10 = new Object[2];
          arrayOfObject10[0] = this.packageName;
          arrayOfObject10[1] = this.mActiveSplitId;
          FinskyLog.wtf("Can't patch splits (yet) for %s (%s)", arrayOfObject10);
          i3 = 0;
          break label1004;
        }
        String str5 = localInstallerData4.downloadUri;
        final Uri localUri1 = Uri.parse(str5);
        setInstallerState(55, localUri1);
        Object[] arrayOfObject7 = new Object[4];
        arrayOfObject7[0] = this.packageName;
        arrayOfObject7[1] = this.mActiveSplitId;
        arrayOfObject7[2] = str5;
        arrayOfObject7[3] = Integer.valueOf(this.mActiveSplit.patchData.patchFormat);
        FinskyLog.d("Prepare to patch %s (%s) from %s format %d", arrayOfObject7);
        Utils.executeMultiThreaded(new AsyncTask()
        {
          /* Error */
          private Sha1Util.DigestResult doInBackground$1a3e6397()
          {
            // Byte code:
            //   0: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
            //   3: lstore_1
            //   4: aload_0
            //   5: getfield 24	com/google/android/finsky/receivers/InstallerTask$8:val$installerData	Lcom/google/android/finsky/appstate/InstallerDataStore$InstallerData;
            //   8: getfield 54	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:packageName	Ljava/lang/String;
            //   11: astore_3
            //   12: aconst_null
            //   13: astore 4
            //   15: aconst_null
            //   16: astore 5
            //   18: invokestatic 60	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
            //   21: astore 8
            //   23: aload 8
            //   25: invokevirtual 66	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
            //   28: pop
            //   29: aload 8
            //   31: invokevirtual 70	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
            //   34: astore 10
            //   36: new 72	java/io/RandomAccessFile
            //   39: dup
            //   40: new 74	java/io/File
            //   43: dup
            //   44: aload 10
            //   46: aload_3
            //   47: iconst_0
            //   48: invokevirtual 80	android/content/pm/PackageManager:getApplicationInfo	(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
            //   51: getfield 85	android/content/pm/ApplicationInfo:sourceDir	Ljava/lang/String;
            //   54: invokespecial 88	java/io/File:<init>	(Ljava/lang/String;)V
            //   57: ldc 90
            //   59: invokespecial 93	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
            //   62: astore 7
            //   64: aload_0
            //   65: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   68: aload 8
            //   70: aload_0
            //   71: getfield 26	com/google/android/finsky/receivers/InstallerTask$8:val$downloadUri	Landroid/net/Uri;
            //   74: aload_0
            //   75: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   78: invokestatic 97	com/google/android/finsky/receivers/InstallerTask:access$2700	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/protos/SplitDeliveryData;
            //   81: invokevirtual 101	com/google/android/finsky/receivers/InstallerTask:getPatch	(Landroid/content/Context;Landroid/net/Uri;Lcom/google/android/finsky/protos/SplitDeliveryData;)Ljava/io/InputStream;
            //   84: astore 11
            //   86: aload 11
            //   88: astore 4
            //   90: aload 4
            //   92: ifnonnull +119 -> 211
            //   95: aload 7
            //   97: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   100: aload 4
            //   102: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   105: aconst_null
            //   106: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   109: aconst_null
            //   110: areturn
            //   111: astore 23
            //   113: aload_0
            //   114: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   117: aload_3
            //   118: ldc 109
            //   120: iconst_0
            //   121: aload 23
            //   123: invokestatic 113	com/google/android/finsky/receivers/InstallerTask:access$2600	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   126: iconst_1
            //   127: anewarray 115	java/lang/Object
            //   130: astore 24
            //   132: aload 24
            //   134: iconst_0
            //   135: aload_0
            //   136: getfield 26	com/google/android/finsky/receivers/InstallerTask$8:val$downloadUri	Landroid/net/Uri;
            //   139: aastore
            //   140: ldc 117
            //   142: aload 24
            //   144: invokestatic 123	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   147: aconst_null
            //   148: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   151: aconst_null
            //   152: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   155: aconst_null
            //   156: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   159: aconst_null
            //   160: areturn
            //   161: astore 21
            //   163: aload_0
            //   164: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   167: aload_3
            //   168: ldc 125
            //   170: iconst_0
            //   171: aload 21
            //   173: invokestatic 113	com/google/android/finsky/receivers/InstallerTask:access$2600	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   176: iconst_1
            //   177: anewarray 115	java/lang/Object
            //   180: astore 22
            //   182: aload 22
            //   184: iconst_0
            //   185: aload_0
            //   186: getfield 26	com/google/android/finsky/receivers/InstallerTask$8:val$downloadUri	Landroid/net/Uri;
            //   189: aastore
            //   190: ldc 127
            //   192: aload 22
            //   194: invokestatic 123	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   197: aconst_null
            //   198: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   201: aconst_null
            //   202: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   205: aconst_null
            //   206: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   209: aconst_null
            //   210: areturn
            //   211: aload_0
            //   212: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   215: invokestatic 131	com/google/android/finsky/receivers/InstallerTask:access$1800	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/installer/PackageInstallerFacade;
            //   218: aload_3
            //   219: aload_0
            //   220: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   223: invokestatic 135	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   226: aload_0
            //   227: getfield 28	com/google/android/finsky/receivers/InstallerTask$8:val$expectByteCount	J
            //   230: invokeinterface 141 5 0
            //   235: astore 14
            //   237: aload 14
            //   239: astore 5
            //   241: new 143	com/google/android/finsky/utils/Sha1Util$DigestStream
            //   244: dup
            //   245: aload 5
            //   247: invokespecial 146	com/google/android/finsky/utils/Sha1Util$DigestStream:<init>	(Ljava/io/OutputStream;)V
            //   250: astore 15
            //   252: aload_0
            //   253: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   256: aload_0
            //   257: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   260: invokestatic 97	com/google/android/finsky/receivers/InstallerTask:access$2700	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/protos/SplitDeliveryData;
            //   263: aload_0
            //   264: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   267: invokestatic 135	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   270: aload 7
            //   272: aload 4
            //   274: aload 15
            //   276: aload_0
            //   277: getfield 28	com/google/android/finsky/receivers/InstallerTask$8:val$expectByteCount	J
            //   280: aload_0
            //   281: getfield 30	com/google/android/finsky/receivers/InstallerTask$8:val$downloadUriString	Ljava/lang/String;
            //   284: invokevirtual 150	com/google/android/finsky/receivers/InstallerTask:applyPatch	(Lcom/google/android/finsky/protos/SplitDeliveryData;Ljava/lang/String;Ljava/io/RandomAccessFile;Ljava/io/InputStream;Ljava/io/OutputStream;JLjava/lang/String;)Z
            //   287: istore 16
            //   289: iload 16
            //   291: ifne +86 -> 377
            //   294: aload 7
            //   296: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   299: aload 4
            //   301: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   304: aload 5
            //   306: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   309: aconst_null
            //   310: areturn
            //   311: astore 12
            //   313: aload_0
            //   314: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   317: aload_3
            //   318: ldc 152
            //   320: iconst_0
            //   321: aload 12
            //   323: invokestatic 155	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   326: iconst_3
            //   327: anewarray 115	java/lang/Object
            //   330: astore 13
            //   332: aload 13
            //   334: iconst_0
            //   335: aload_3
            //   336: aastore
            //   337: aload 13
            //   339: iconst_1
            //   340: aload_0
            //   341: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   344: invokestatic 135	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   347: aastore
            //   348: aload 13
            //   350: iconst_2
            //   351: aload 12
            //   353: aastore
            //   354: ldc 157
            //   356: aload 13
            //   358: invokestatic 123	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   361: aload 7
            //   363: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   366: aload 4
            //   368: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   371: aconst_null
            //   372: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   375: aconst_null
            //   376: areturn
            //   377: aload 15
            //   379: invokevirtual 160	com/google/android/finsky/utils/Sha1Util$DigestStream:getDigest	()Lcom/google/android/finsky/utils/Sha1Util$DigestResult;
            //   382: astore 17
            //   384: aload_0
            //   385: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   388: invokestatic 131	com/google/android/finsky/receivers/InstallerTask:access$1800	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/installer/PackageInstallerFacade;
            //   391: aload 5
            //   393: invokeinterface 163 2 0
            //   398: iconst_4
            //   399: anewarray 115	java/lang/Object
            //   402: astore 20
            //   404: aload 20
            //   406: iconst_0
            //   407: aload_3
            //   408: aastore
            //   409: aload 20
            //   411: iconst_1
            //   412: aload_0
            //   413: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   416: invokestatic 135	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   419: aastore
            //   420: aload 20
            //   422: iconst_2
            //   423: aload_0
            //   424: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   427: invokestatic 97	com/google/android/finsky/receivers/InstallerTask:access$2700	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/protos/SplitDeliveryData;
            //   430: getfield 169	com/google/android/finsky/protos/SplitDeliveryData:patchData	Lcom/google/android/finsky/protos/AndroidAppPatchData;
            //   433: getfield 175	com/google/android/finsky/protos/AndroidAppPatchData:patchFormat	I
            //   436: invokestatic 181	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
            //   439: aastore
            //   440: aload 20
            //   442: iconst_3
            //   443: lload_1
            //   444: invokestatic 49	android/os/SystemClock:elapsedRealtime	()J
            //   447: lsub
            //   448: invokestatic 186	java/lang/Long:valueOf	(J)Ljava/lang/Long;
            //   451: aastore
            //   452: ldc 188
            //   454: aload 20
            //   456: invokestatic 191	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   459: aload 7
            //   461: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   464: aload 4
            //   466: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   469: aload 5
            //   471: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   474: aload 17
            //   476: areturn
            //   477: astore 18
            //   479: aload_0
            //   480: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   483: aload_3
            //   484: ldc 193
            //   486: iconst_0
            //   487: aload 18
            //   489: invokestatic 155	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   492: iconst_3
            //   493: anewarray 115	java/lang/Object
            //   496: astore 19
            //   498: aload 19
            //   500: iconst_0
            //   501: aload_3
            //   502: aastore
            //   503: aload 19
            //   505: iconst_1
            //   506: aload_0
            //   507: getfield 22	com/google/android/finsky/receivers/InstallerTask$8:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   510: invokestatic 135	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   513: aastore
            //   514: aload 19
            //   516: iconst_2
            //   517: aload 18
            //   519: aastore
            //   520: ldc 195
            //   522: aload 19
            //   524: invokestatic 123	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   527: aload 7
            //   529: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   532: aload 4
            //   534: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   537: aload 5
            //   539: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   542: aconst_null
            //   543: areturn
            //   544: astore 6
            //   546: aconst_null
            //   547: astore 7
            //   549: aload 7
            //   551: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   554: aload 4
            //   556: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   559: aload 5
            //   561: invokestatic 107	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   564: aload 6
            //   566: athrow
            //   567: astore 6
            //   569: goto -20 -> 549
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	572	0	this	8
            //   3	441	1	l	long
            //   11	491	3	str	String
            //   13	542	4	localObject1	Object
            //   16	544	5	localObject2	Object
            //   544	21	6	localObject3	Object
            //   567	1	6	localObject4	Object
            //   62	488	7	localRandomAccessFile	java.io.RandomAccessFile
            //   21	48	8	localFinskyApp	FinskyApp
            //   34	11	10	localPackageManager	PackageManager
            //   84	3	11	localInputStream	InputStream
            //   311	41	12	localIOException1	IOException
            //   330	27	13	arrayOfObject1	Object[]
            //   235	3	14	localOutputStream	java.io.OutputStream
            //   250	128	15	localDigestStream	com.google.android.finsky.utils.Sha1Util.DigestStream
            //   287	3	16	bool	boolean
            //   382	93	17	localDigestResult	Sha1Util.DigestResult
            //   477	41	18	localIOException2	IOException
            //   496	27	19	arrayOfObject2	Object[]
            //   402	53	20	arrayOfObject3	Object[]
            //   161	11	21	localFileNotFoundException	FileNotFoundException
            //   180	13	22	arrayOfObject4	Object[]
            //   111	11	23	localNameNotFoundException	PackageManager.NameNotFoundException
            //   130	13	24	arrayOfObject5	Object[]
            // Exception table:
            //   from	to	target	type
            //   36	64	111	android/content/pm/PackageManager$NameNotFoundException
            //   36	64	161	java/io/FileNotFoundException
            //   211	237	311	java/io/IOException
            //   384	398	477	java/io/IOException
            //   18	36	544	finally
            //   36	64	544	finally
            //   113	147	544	finally
            //   163	197	544	finally
            //   64	86	567	finally
            //   211	237	567	finally
            //   241	289	567	finally
            //   313	361	567	finally
            //   377	384	567	finally
            //   384	398	567	finally
            //   398	459	567	finally
            //   479	527	567	finally
          }
        }, new Void[0]);
        i3 = 1;
        break label1004;
        label1335:
        bool3 = false;
        break label1034;
        label1341:
        bool4 = false;
        break label1046;
        label1347:
        String str6 = localInstallerData5.downloadUri;
        final Uri localUri2 = Uri.parse(str6);
        setInstallerState(52, localUri2);
        final long l1 = this.mActiveSplit.downloadSize;
        String str7 = this.mActiveSplit.signature;
        Object[] arrayOfObject8 = new Object[4];
        arrayOfObject8[0] = this.packageName;
        arrayOfObject8[1] = this.mActiveSplitId;
        arrayOfObject8[2] = str6;
        arrayOfObject8[3] = Long.valueOf(l1);
        FinskyLog.d("Prepare to copy %s (%s) from %s (expect %d bytes)", arrayOfObject8);
        Utils.executeMultiThreaded(new AsyncTask()
        {
          /* Error */
          private Sha1Util.DigestResult doInBackground$1a3e6397()
          {
            // Byte code:
            //   0: aload_0
            //   1: getfield 26	com/google/android/finsky/receivers/InstallerTask$7:val$installerData	Lcom/google/android/finsky/appstate/InstallerDataStore$InstallerData;
            //   4: getfield 50	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:packageName	Ljava/lang/String;
            //   7: astore_1
            //   8: aconst_null
            //   9: astore_2
            //   10: aconst_null
            //   11: astore_3
            //   12: invokestatic 56	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
            //   15: astore 5
            //   17: aload 5
            //   19: invokevirtual 62	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
            //   22: aload_0
            //   23: getfield 28	com/google/android/finsky/receivers/InstallerTask$7:val$downloadUri	Landroid/net/Uri;
            //   26: invokevirtual 68	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
            //   29: astore 8
            //   31: aload 8
            //   33: astore_2
            //   34: aload_0
            //   35: getfield 30	com/google/android/finsky/receivers/InstallerTask$7:val$isGzipped	Z
            //   38: istore 9
            //   40: iload 9
            //   42: ifeq +19 -> 61
            //   45: new 70	java/util/zip/GZIPInputStream
            //   48: dup
            //   49: aload_2
            //   50: sipush 8192
            //   53: invokespecial 73	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;I)V
            //   56: astore 22
            //   58: aload 22
            //   60: astore_2
            //   61: aload_0
            //   62: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   65: invokestatic 77	com/google/android/finsky/receivers/InstallerTask:access$1800	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/installer/PackageInstallerFacade;
            //   68: aload_1
            //   69: aload_0
            //   70: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   73: invokestatic 81	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   76: aload_0
            //   77: getfield 32	com/google/android/finsky/receivers/InstallerTask$7:val$expectByteCount	J
            //   80: invokeinterface 87 5 0
            //   85: astore 12
            //   87: aload 12
            //   89: astore_3
            //   90: invokestatic 93	android/os/SystemClock:elapsedRealtime	()J
            //   93: lstore 13
            //   95: new 95	com/google/android/finsky/utils/Sha1Util$DigestStream
            //   98: dup
            //   99: aload_3
            //   100: invokespecial 98	com/google/android/finsky/utils/Sha1Util$DigestStream:<init>	(Ljava/io/OutputStream;)V
            //   103: astore 15
            //   105: aload_2
            //   106: aload 15
            //   108: invokestatic 104	com/google/android/finsky/utils/Utils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
            //   111: aload 15
            //   113: invokevirtual 107	com/google/android/finsky/utils/Sha1Util$DigestStream:getDigest	()Lcom/google/android/finsky/utils/Sha1Util$DigestResult;
            //   116: astore 18
            //   118: iconst_4
            //   119: anewarray 109	java/lang/Object
            //   122: astore 19
            //   124: aload 19
            //   126: iconst_0
            //   127: aload_1
            //   128: aastore
            //   129: aload 19
            //   131: iconst_1
            //   132: aload_0
            //   133: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   136: invokestatic 81	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   139: aastore
            //   140: aload 19
            //   142: iconst_2
            //   143: aload_0
            //   144: getfield 32	com/google/android/finsky/receivers/InstallerTask$7:val$expectByteCount	J
            //   147: invokestatic 115	java/lang/Long:valueOf	(J)Ljava/lang/Long;
            //   150: aastore
            //   151: aload 19
            //   153: iconst_3
            //   154: invokestatic 93	android/os/SystemClock:elapsedRealtime	()J
            //   157: lload 13
            //   159: lsub
            //   160: invokestatic 115	java/lang/Long:valueOf	(J)Ljava/lang/Long;
            //   163: aastore
            //   164: ldc 117
            //   166: aload 19
            //   168: invokestatic 123	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   171: aload_0
            //   172: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   175: invokestatic 77	com/google/android/finsky/receivers/InstallerTask:access$1800	(Lcom/google/android/finsky/receivers/InstallerTask;)Lcom/google/android/finsky/installer/PackageInstallerFacade;
            //   178: aload_3
            //   179: invokeinterface 126 2 0
            //   184: aload_2
            //   185: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   188: aload_3
            //   189: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   192: aload 18
            //   194: areturn
            //   195: astore 6
            //   197: aload_0
            //   198: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   201: aload_1
            //   202: ldc 132
            //   204: iconst_0
            //   205: aload 6
            //   207: invokestatic 136	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   210: iconst_1
            //   211: anewarray 109	java/lang/Object
            //   214: astore 7
            //   216: aload 7
            //   218: iconst_0
            //   219: aload_0
            //   220: getfield 28	com/google/android/finsky/receivers/InstallerTask$7:val$downloadUri	Landroid/net/Uri;
            //   223: aastore
            //   224: ldc 138
            //   226: aload 7
            //   228: invokestatic 141	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   231: aconst_null
            //   232: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   235: aconst_null
            //   236: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   239: aconst_null
            //   240: areturn
            //   241: astore 23
            //   243: aload_0
            //   244: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   247: aload_1
            //   248: ldc 143
            //   250: iconst_0
            //   251: aload 23
            //   253: invokestatic 136	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   256: iconst_1
            //   257: anewarray 109	java/lang/Object
            //   260: astore 24
            //   262: aload 24
            //   264: iconst_0
            //   265: aload 23
            //   267: invokevirtual 147	java/io/IOException:getMessage	()Ljava/lang/String;
            //   270: aastore
            //   271: ldc 149
            //   273: aload 24
            //   275: invokestatic 141	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   278: aload_2
            //   279: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   282: aconst_null
            //   283: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   286: aconst_null
            //   287: areturn
            //   288: astore 10
            //   290: aload_0
            //   291: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   294: aload_1
            //   295: ldc 151
            //   297: iconst_0
            //   298: aload 10
            //   300: invokestatic 136	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   303: iconst_3
            //   304: anewarray 109	java/lang/Object
            //   307: astore 11
            //   309: aload 11
            //   311: iconst_0
            //   312: aload_1
            //   313: aastore
            //   314: aload 11
            //   316: iconst_1
            //   317: aload_0
            //   318: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   321: invokestatic 81	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   324: aastore
            //   325: aload 11
            //   327: iconst_2
            //   328: aload 10
            //   330: aastore
            //   331: ldc 153
            //   333: aload 11
            //   335: invokestatic 141	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   338: aload_2
            //   339: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   342: aconst_null
            //   343: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   346: aconst_null
            //   347: areturn
            //   348: astore 16
            //   350: aload_0
            //   351: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   354: aload_1
            //   355: ldc 155
            //   357: iconst_0
            //   358: aload 16
            //   360: invokestatic 136	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   363: iconst_3
            //   364: anewarray 109	java/lang/Object
            //   367: astore 17
            //   369: aload 17
            //   371: iconst_0
            //   372: aload_1
            //   373: aastore
            //   374: aload 17
            //   376: iconst_1
            //   377: aload_0
            //   378: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   381: invokestatic 81	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   384: aastore
            //   385: aload 17
            //   387: iconst_2
            //   388: aload 16
            //   390: aastore
            //   391: ldc 153
            //   393: aload 17
            //   395: invokestatic 141	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   398: aload_2
            //   399: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   402: aload_3
            //   403: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   406: aconst_null
            //   407: areturn
            //   408: astore 20
            //   410: aload_0
            //   411: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   414: aload_1
            //   415: ldc 157
            //   417: iconst_0
            //   418: aload 20
            //   420: invokestatic 136	com/google/android/finsky/receivers/InstallerTask:access$2100	(Lcom/google/android/finsky/receivers/InstallerTask;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
            //   423: iconst_3
            //   424: anewarray 109	java/lang/Object
            //   427: astore 21
            //   429: aload 21
            //   431: iconst_0
            //   432: aload_1
            //   433: aastore
            //   434: aload 21
            //   436: iconst_1
            //   437: aload_0
            //   438: getfield 24	com/google/android/finsky/receivers/InstallerTask$7:this$0	Lcom/google/android/finsky/receivers/InstallerTask;
            //   441: invokestatic 81	com/google/android/finsky/receivers/InstallerTask:access$2200	(Lcom/google/android/finsky/receivers/InstallerTask;)Ljava/lang/String;
            //   444: aastore
            //   445: aload 21
            //   447: iconst_2
            //   448: aload 20
            //   450: aastore
            //   451: ldc 159
            //   453: aload 21
            //   455: invokestatic 141	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
            //   458: aload_2
            //   459: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   462: aload_3
            //   463: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   466: aconst_null
            //   467: areturn
            //   468: astore 4
            //   470: aload_2
            //   471: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   474: aload_3
            //   475: invokestatic 130	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
            //   478: aload 4
            //   480: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	481	0	this	7
            //   7	426	1	str	String
            //   9	462	2	localObject1	Object
            //   11	464	3	localObject2	Object
            //   468	11	4	localObject3	Object
            //   15	3	5	localFinskyApp	FinskyApp
            //   195	11	6	localFileNotFoundException	FileNotFoundException
            //   214	13	7	arrayOfObject1	Object[]
            //   29	3	8	localInputStream	InputStream
            //   38	3	9	bool	boolean
            //   288	41	10	localIOException1	IOException
            //   307	27	11	arrayOfObject2	Object[]
            //   85	3	12	localOutputStream	java.io.OutputStream
            //   93	65	13	l	long
            //   103	9	15	localDigestStream	com.google.android.finsky.utils.Sha1Util.DigestStream
            //   348	41	16	localIOException2	IOException
            //   367	27	17	arrayOfObject3	Object[]
            //   116	77	18	localDigestResult	Sha1Util.DigestResult
            //   122	45	19	arrayOfObject4	Object[]
            //   408	41	20	localIOException3	IOException
            //   427	27	21	arrayOfObject5	Object[]
            //   56	3	22	localGZIPInputStream	GZIPInputStream
            //   241	25	23	localIOException4	IOException
            //   260	14	24	arrayOfObject6	Object[]
            // Exception table:
            //   from	to	target	type
            //   17	31	195	java/io/FileNotFoundException
            //   45	58	241	java/io/IOException
            //   61	87	288	java/io/IOException
            //   95	171	348	java/io/IOException
            //   171	184	408	java/io/IOException
            //   12	17	468	finally
            //   17	31	468	finally
            //   34	40	468	finally
            //   45	58	468	finally
            //   61	87	468	finally
            //   90	95	468	finally
            //   95	171	468	finally
            //   171	184	468	finally
            //   197	231	468	finally
            //   243	278	468	finally
            //   290	338	468	finally
            //   350	398	468	finally
            //   410	458	468	finally
          }
        }, new Void[0]);
      }
      int n;
      final String str4;
      boolean bool1;
      if ((0x20 & localAppState.installerData.flags) == 0)
      {
        n = 0;
        if (n != 0) {
          break label54;
        }
        InstallerDataStore.InstallerData localInstallerData3 = localAppState.installerData;
        if (this.mObbMain != null)
        {
          this.mObbMain.syncStateWithStorage();
          if (this.mObbMain.getState() == 4)
          {
            this.mObbMain.finalizeTempFile();
            this.mObbMain.syncStateWithStorage();
          }
          int i2 = this.mObbMain.getState();
          if ((i2 != 5) && (i2 != 3))
          {
            Object[] arrayOfObject6 = new Object[1];
            arrayOfObject6[0] = this.packageName;
            FinskyLog.e("Can't find main obb file for %s", arrayOfObject6);
            cancel(false);
            logAndNotifyDownloadError(this.packageName, 911, null);
          }
        }
        if (this.mObbPatch != null)
        {
          this.mObbPatch.syncStateWithStorage();
          if (this.mObbPatch.getState() == 4)
          {
            this.mObbPatch.finalizeTempFile();
            this.mObbPatch.syncStateWithStorage();
          }
          int i1 = this.mObbPatch.getState();
          if ((i1 != 5) && (i1 != 3))
          {
            Object[] arrayOfObject5 = new Object[1];
            arrayOfObject5[0] = this.packageName;
            FinskyLog.e("Can't find patch obb file for %s", arrayOfObject5);
            cancel(false);
            logAndNotifyDownloadError(this.packageName, 912, null);
          }
        }
        str4 = localInstallerData3.downloadUri;
        if (localAppState.packageManagerState == null) {
          break label1935;
        }
        bool1 = true;
        label1729:
        this.mIsUpdate = bool1;
        if ((Build.VERSION.SDK_INT < ((Integer)G.preserveForwardLockApiMin.get()).intValue()) || (Build.VERSION.SDK_INT > ((Integer)G.preserveForwardLockApiMax.get()).intValue()) || (!isInstalledForwardLocked(this.packageName, null))) {
          break label1941;
        }
      }
      label1935:
      label1941:
      for (boolean bool2 = true;; bool2 = false)
      {
        FinskyApp.get().getEventLogger().logBackgroundEvent(106, this.packageName, null, 0, null, this.mLogAppData);
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = this.packageName;
        FinskyLog.d("Begin install of %s", arrayOfObject4);
        if (this.mShowProgress) {
          FinskyApp.get().mNotificationHelper.showInstallingMessage(this.mTitle, this.packageName, this.mIsUpdate);
        }
        this.mPackageInstaller.install(this.packageName, bool2, new PackageInstallerFacade.InstallListener()
        {
          private void commonFinish(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString)
          {
            if (str4 != null) {
              InstallerTask.this.mDownloadQueue.release(Uri.parse(str4));
            }
            FinskyApp.get().getEventLogger().logBackgroundEvent(paramAnonymousInt1, InstallerTask.this.packageName, null, paramAnonymousInt2, paramAnonymousString, InstallerTask.this.mLogAppData);
            InstallerTask.access$1400$60a8152e(InstallerTask.this);
            InstallerTask.this.advanceState();
          }
          
          public final void installFailed(int paramAnonymousInt, String paramAnonymousString)
          {
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = InstallerTask.this.packageName;
            arrayOfObject[1] = Integer.valueOf(paramAnonymousInt);
            arrayOfObject[2] = paramAnonymousString;
            FinskyLog.w("Install failure of %s: %d %s", arrayOfObject);
            if (InstallerTask.this.mShowErrorNotifications) {
              InstallerTask.access$1200(InstallerTask.this, InstallerTask.this.packageName, paramAnonymousInt);
            }
            commonFinish(111, paramAnonymousInt, paramAnonymousString);
          }
          
          public final void installSucceeded()
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = InstallerTask.this.packageName;
            FinskyLog.d("Successful install of %s", arrayOfObject);
            if ((InstallerTask.this.mShowCompletionNotifications) && (!InstallerTask.this.mIsUpdate) && (((Boolean)VendingPreferences.AUTO_ADD_SHORTCUTS.get()).booleanValue())) {
              InstallerTask.this.addAppShortcut();
            }
            commonFinish(110, 0, null);
          }
        });
        setInstallerState(60, str4);
        notifyListeners(4, 0);
        return;
        new AsyncTask()
        {
          private Integer doInBackground$9ecd34e()
          {
            try
            {
              Integer localInteger = Integer.valueOf(PackageManagerHelper.installExistingPackage(FinskyApp.get(), localAppState.packageName));
              return localInteger;
            }
            catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
            return Integer.valueOf(-3);
          }
        }.execute(new Void[0]);
        setInstallerState(60, null);
        notifyListeners(4, 0);
        n = 1;
        break;
        bool1 = false;
        break label1729;
      }
      this.mNotifier.hideInstallingMessage();
      int i = -1;
      if (localAppState.packageManagerState != null) {
        i = localAppState.packageManagerState.installedVersion;
      }
      if (i != localAppState.installerData.desiredVersion)
      {
        cancelCleanup(localAppState);
        notifyListeners(5, 910);
        return;
      }
      if ((this.mObbMain != null) && (this.mObbMain.getState() != 5))
      {
        this.mObbMain.syncStateWithStorage();
        if (this.mObbMain.getState() != 3)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = this.packageName;
          FinskyLog.e("Lost main obb file for %s", arrayOfObject3);
          cancelCleanup(localAppState);
          logAndNotifyDownloadError(this.packageName, 911, null);
          return;
        }
      }
      if ((this.mObbPatch != null) && (this.mObbPatch.getState() != 5))
      {
        this.mObbPatch.syncStateWithStorage();
        if (this.mObbPatch.getState() != 3)
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.packageName;
          FinskyLog.e("Lost patch obb file for %s", arrayOfObject2);
          cancelCleanup(localAppState);
          notifyListeners(5, 912);
          showDownloadNotification(912, null);
          return;
        }
      }
      Obb localObb1 = this.mObbMain;
      Obb localObb2 = this.mObbPatch;
      String str1 = this.packageName;
      if ((localObb1 != null) || (localObb2 != null) || (!FinskyApp.get().getExperiments().isEnabled(12603143L))) {
        if ((localObb1 == null) || (localObb1.getState() != 3)) {
          break label2491;
        }
      }
      label2491:
      for (File localFile1 = localObb1.getFile();; localFile1 = null)
      {
        File localFile2 = null;
        if (localObb2 != null)
        {
          int m = localObb2.getState();
          localFile2 = null;
          if (m == 3) {
            localFile2 = localObb2.getFile();
          }
        }
        File[] arrayOfFile = ObbFactory.getParentDirectory(str1).listFiles();
        if (arrayOfFile != null)
        {
          int j = arrayOfFile.length;
          for (int k = 0; k < j; k++)
          {
            File localFile3 = arrayOfFile[k];
            if (((localFile1 == null) || (!localFile1.equals(localFile3))) && ((localFile2 == null) || (!localFile2.equals(localFile3))))
            {
              FinskyLog.d("OBB cleanup %s", new Object[] { localFile3 });
              localFile3.delete();
            }
          }
        }
        cleanExternalStorage();
        setInstallerState(80, null);
        notifyListeners(6, 0);
        if (this.mIsUpdate) {
          this.mInstallerDataStore.setLastUpdateTimestampMs(this.packageName, System.currentTimeMillis());
        }
        InstallerDataStore.InstallerData localInstallerData2 = localAppState.installerData;
        checkForEmptyTitle("cleanup", this.packageName, localInstallerData2.title, localInstallerData2);
        if (!this.mShowCompletionNotifications) {
          break;
        }
        String str2 = localInstallerData2.title;
        String str3 = localInstallerData2.continueUrl;
        this.mNotifier.showSuccessfulInstallMessage(str2, this.packageName, str3, this.mIsUpdate);
        break;
        this.mInstaller.clearInstallerState(localAppState);
        this.mInstaller.taskFinished(this);
        return;
      }
      label2497:
      str13 = str12;
    }
  }
  
  /* Error */
  final boolean applyPatch(SplitDeliveryData paramSplitDeliveryData, String paramString1, java.io.RandomAccessFile paramRandomAccessFile, InputStream paramInputStream, java.io.OutputStream paramOutputStream, long paramLong, String paramString2)
  {
    // Byte code:
    //   0: aload_1
    //   1: getfield 321	com/google/android/finsky/protos/SplitDeliveryData:patchData	Lcom/google/android/finsky/protos/AndroidAppPatchData;
    //   4: getfield 336	com/google/android/finsky/protos/AndroidAppPatchData:patchFormat	I
    //   7: tableswitch	default:+25 -> 32, 1:+68->75, 2:+68->75, 3:+81->88
    //   33: anewarray 4	java/lang/Object
    //   36: astore 22
    //   38: aload 22
    //   40: iconst_0
    //   41: aload_0
    //   42: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   45: aastore
    //   46: aload 22
    //   48: iconst_1
    //   49: aload_2
    //   50: aastore
    //   51: aload 22
    //   53: iconst_2
    //   54: aload_1
    //   55: getfield 321	com/google/android/finsky/protos/SplitDeliveryData:patchData	Lcom/google/android/finsky/protos/AndroidAppPatchData;
    //   58: getfield 336	com/google/android/finsky/protos/AndroidAppPatchData:patchFormat	I
    //   61: invokestatic 326	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   64: aastore
    //   65: ldc_w 1187
    //   68: aload 22
    //   70: invokestatic 331	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   73: iconst_0
    //   74: ireturn
    //   75: aload_3
    //   76: aload 4
    //   78: aload 5
    //   80: lload 6
    //   82: invokestatic 1193	com/google/android/finsky/installer/Gdiff:patch	(Ljava/io/RandomAccessFile;Ljava/io/InputStream;Ljava/io/OutputStream;J)J
    //   85: pop2
    //   86: iconst_1
    //   87: ireturn
    //   88: new 1195	java/io/BufferedInputStream
    //   91: dup
    //   92: aload 4
    //   94: sipush 4096
    //   97: invokespecial 1198	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   100: astore 17
    //   102: new 1200	java/io/BufferedOutputStream
    //   105: dup
    //   106: aload 5
    //   108: sipush 16384
    //   111: invokespecial 1203	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   114: astore 18
    //   116: aload_3
    //   117: aload 18
    //   119: aload 17
    //   121: lload 6
    //   123: invokestatic 1209	com/google/android/finsky/installer/BsPatch:applyPatchInternal	(Ljava/io/RandomAccessFile;Ljava/io/OutputStream;Ljava/io/InputStream;J)V
    //   126: aload 18
    //   128: invokevirtual 1214	java/io/OutputStream:flush	()V
    //   131: iconst_1
    //   132: ireturn
    //   133: astore 19
    //   135: aload 18
    //   137: invokevirtual 1214	java/io/OutputStream:flush	()V
    //   140: aload 19
    //   142: athrow
    //   143: astore 13
    //   145: aload 8
    //   147: ldc_w 1216
    //   150: ldc_w 1218
    //   153: invokevirtual 1222	java/lang/String:replaceFirst	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   156: astore 14
    //   158: invokestatic 134	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   161: invokevirtual 746	com/google/android/finsky/FinskyApp:getContentResolver	()Landroid/content/ContentResolver;
    //   164: aload 14
    //   166: invokestatic 1055	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   169: invokevirtual 1228	android/content/ContentResolver:getType	(Landroid/net/Uri;)Ljava/lang/String;
    //   172: astore 15
    //   174: aload_0
    //   175: aload_0
    //   176: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   179: ldc_w 1230
    //   182: iconst_0
    //   183: aload 13
    //   185: invokespecial 275	com/google/android/finsky/receivers/InstallerTask:logPatchFailure	(Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
    //   188: iconst_3
    //   189: anewarray 4	java/lang/Object
    //   192: astore 16
    //   194: aload 16
    //   196: iconst_0
    //   197: aload_0
    //   198: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   201: aastore
    //   202: aload 16
    //   204: iconst_1
    //   205: aload_2
    //   206: aastore
    //   207: aload 16
    //   209: iconst_2
    //   210: aload 15
    //   212: aastore
    //   213: ldc_w 1232
    //   216: aload 16
    //   218: invokestatic 910	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   221: iconst_0
    //   222: ireturn
    //   223: astore 11
    //   225: aload_0
    //   226: aload_0
    //   227: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   230: ldc_w 1234
    //   233: iconst_0
    //   234: aload 11
    //   236: invokespecial 275	com/google/android/finsky/receivers/InstallerTask:logPatchFailure	(Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
    //   239: iconst_3
    //   240: anewarray 4	java/lang/Object
    //   243: astore 12
    //   245: aload 12
    //   247: iconst_0
    //   248: aload_0
    //   249: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   252: aastore
    //   253: aload 12
    //   255: iconst_1
    //   256: aload_2
    //   257: aastore
    //   258: aload 12
    //   260: iconst_2
    //   261: aload 11
    //   263: aastore
    //   264: ldc_w 1236
    //   267: aload 12
    //   269: invokestatic 910	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   272: goto -51 -> 221
    //   275: astore 9
    //   277: aload_0
    //   278: aload_0
    //   279: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   282: ldc_w 1238
    //   285: iconst_0
    //   286: aload 9
    //   288: invokespecial 275	com/google/android/finsky/receivers/InstallerTask:logPatchFailure	(Ljava/lang/String;Ljava/lang/String;ILjava/lang/Exception;)V
    //   291: iconst_3
    //   292: anewarray 4	java/lang/Object
    //   295: astore 10
    //   297: aload 10
    //   299: iconst_0
    //   300: aload_0
    //   301: getfield 91	com/google/android/finsky/receivers/InstallerTask:packageName	Ljava/lang/String;
    //   304: aastore
    //   305: aload 10
    //   307: iconst_1
    //   308: aload_2
    //   309: aastore
    //   310: aload 10
    //   312: iconst_2
    //   313: aload 9
    //   315: aastore
    //   316: ldc_w 1236
    //   319: aload 10
    //   321: invokestatic 910	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   324: goto -103 -> 221
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	327	0	this	InstallerTask
    //   0	327	1	paramSplitDeliveryData	SplitDeliveryData
    //   0	327	2	paramString1	String
    //   0	327	3	paramRandomAccessFile	java.io.RandomAccessFile
    //   0	327	4	paramInputStream	InputStream
    //   0	327	5	paramOutputStream	java.io.OutputStream
    //   0	327	6	paramLong	long
    //   0	327	8	paramString2	String
    //   275	39	9	localException	Exception
    //   295	25	10	arrayOfObject1	Object[]
    //   223	39	11	localIOException	IOException
    //   243	25	12	arrayOfObject2	Object[]
    //   143	41	13	localPatchFormatException	com.google.android.finsky.installer.PatchFormatException
    //   156	9	14	str1	String
    //   172	39	15	str2	String
    //   192	25	16	arrayOfObject3	Object[]
    //   100	20	17	localBufferedInputStream	java.io.BufferedInputStream
    //   114	22	18	localBufferedOutputStream	java.io.BufferedOutputStream
    //   133	8	19	localObject	Object
    //   36	33	22	arrayOfObject4	Object[]
    // Exception table:
    //   from	to	target	type
    //   116	126	133	finally
    //   0	32	143	com/google/android/finsky/installer/PatchFormatException
    //   32	73	143	com/google/android/finsky/installer/PatchFormatException
    //   75	86	143	com/google/android/finsky/installer/PatchFormatException
    //   88	116	143	com/google/android/finsky/installer/PatchFormatException
    //   126	131	143	com/google/android/finsky/installer/PatchFormatException
    //   135	143	143	com/google/android/finsky/installer/PatchFormatException
    //   0	32	223	java/io/IOException
    //   32	73	223	java/io/IOException
    //   75	86	223	java/io/IOException
    //   88	116	223	java/io/IOException
    //   126	131	223	java/io/IOException
    //   135	143	223	java/io/IOException
    //   0	32	275	java/lang/Exception
    //   32	73	275	java/lang/Exception
    //   75	86	275	java/lang/Exception
    //   88	116	275	java/lang/Exception
    //   126	131	275	java/lang/Exception
    //   135	143	275	java/lang/Exception
  }
  
  public final void cancel(boolean paramBoolean)
  {
    AppStates.AppState localAppState = this.mAppStates.getApp(this.packageName);
    if ((localAppState != null) && (localAppState.installerData != null) && (localAppState.installerData.installerState >= 50))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[1] = this.mActiveSplitId;
      FinskyLog.d("Cannot cancel installing %s (%s) - too late", arrayOfObject);
    }
    do
    {
      return;
      cancelCleanup(localAppState);
    } while (!paramBoolean);
    notifyListeners(2, 0);
  }
  
  final void cancelCleanup(AppStates.AppState paramAppState)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.packageName;
    arrayOfObject[1] = this.mActiveSplitId;
    FinskyLog.d("Cancel running installation of %s (%s)", arrayOfObject);
    Download localDownload = this.mDownloadQueue.getByPackageName(this.packageName, null);
    if (localDownload != null) {
      this.mDownloadQueue.cancel(localDownload);
    }
    this.mPackageInstaller.cancelSession(this.packageName);
    this.mInstaller.clearInstallerState(paramAppState);
    cleanExternalStorage();
    if ((this.mObbMain == null) && (this.mObbPatch == null) && (paramAppState != null) && (paramAppState.installerData != null) && (paramAppState.installerData.deliveryData != null)) {
      extractObbInfo(paramAppState.installerData);
    }
    if (this.mObbMain != null)
    {
      File localFile2 = this.mObbMain.getTempFile();
      if (localFile2 != null) {
        localFile2.delete();
      }
      if ((!this.mIsUpdate) && (this.mObbMain.isFinalized())) {
        this.mObbMain.getFile().delete();
      }
    }
    if (this.mObbPatch != null)
    {
      File localFile1 = this.mObbPatch.getTempFile();
      if (localFile1 != null) {
        localFile1.delete();
      }
      if ((!this.mIsUpdate) && (this.mObbPatch.isFinalized())) {
        this.mObbPatch.getFile().delete();
      }
    }
    this.mInstaller.taskFinished(this);
  }
  
  final Download generateDownload(InstallerDataStore.InstallerData paramInstallerData, String paramString)
  {
    AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
    if (!TextUtils.isEmpty(paramString)) {}
    ArrayList localArrayList;
    for (String str1 = paramString; !loadActiveSplitInfo(str1, localAndroidAppDeliveryData); str1 = (String)localArrayList.get(0))
    {
      Object[] arrayOfObject4 = new Object[2];
      arrayOfObject4[0] = str1;
      arrayOfObject4[1] = this.packageName;
      FinskyLog.wtf("Cannot find split id %s for package %s", arrayOfObject4);
      return null;
      localArrayList = Lists.newArrayList(this.mRequiredSplits);
      localArrayList.removeAll(this.mCompletedSplits);
      if (localArrayList.isEmpty()) {
        return null;
      }
    }
    this.mActiveSplitId = str1;
    this.mInstallerDataStore.setActiveSplitId(this.packageName, this.mActiveSplitId);
    updateLogWithActiveSplitInfo();
    String str2 = paramInstallerData.title;
    String str3 = paramInstallerData.packageName;
    HttpCookie localHttpCookie = localAndroidAppDeliveryData.downloadAuthCookie[0];
    int i = paramInstallerData.flags;
    int j = i & 0xFFFFEDFB;
    SplitDeliveryData localSplitDeliveryData1 = this.mActiveSplit;
    int k;
    Uri localUri;
    String str4;
    long l2;
    long l3;
    label386:
    String str5;
    String str6;
    boolean bool1;
    if ((0x10000 & i) != 0)
    {
      k = 0;
      localUri = null;
      if (k != 0)
      {
        j = 0x1000 | j;
        this.mLogAppData.downloadExternal = true;
        this.mLogAppData.hasDownloadExternal = true;
        String str7 = String.valueOf(System.currentTimeMillis());
        localUri = Uri.fromFile(new File(FinskyApp.get().getExternalFilesDir(null), str7));
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = str3;
        arrayOfObject3[1] = this.mActiveSplitId;
        FinskyLog.d("Downloading %s (%s) via external storage", arrayOfObject3);
      }
      if (!canDownloadPatch(this.mActiveSplit, i)) {
        break label616;
      }
      j |= 0x4;
      AndroidAppPatchData localAndroidAppPatchData = this.mActiveSplit.patchData;
      str4 = localAndroidAppPatchData.downloadUrl;
      l2 = -1L;
      l3 = localAndroidAppPatchData.maxPatchSize;
      this.mLogAppData.downloadPatch = true;
      this.mLogAppData.hasDownloadPatch = true;
      this.mLogAppData.patchFormat = this.mActiveSplit.patchData.patchFormat;
      this.mLogAppData.hasPatchFormat = true;
      if (isGzippedPatch(this.mActiveSplit))
      {
        this.mLogAppData.downloadGzip = true;
        this.mLogAppData.hasDownloadGzip = true;
      }
      if (j != i) {
        this.mInstallerDataStore.setFlags(str3, j);
      }
      str5 = localHttpCookie.name;
      str6 = localHttpCookie.value;
      if (this.mMobileDataAllowed) {
        break label900;
      }
      bool1 = true;
      label430:
      if (this.mShowProgress) {
        break label906;
      }
    }
    label900:
    label906:
    for (boolean bool2 = true;; bool2 = false)
    {
      return new DownloadImpl(str4, str2, str3, null, str5, str6, localUri, l2, l3, null, bool1, bool2);
      if ((i & 0x1000) == 0)
      {
        if ((i & 0x2000) != 0)
        {
          k = 0;
          break;
        }
        long l5 = localSplitDeliveryData1.downloadSize;
        long l6 = ((Long)G.downloadExternalFileSizeMinBytes.get()).longValue();
        if (FinskyApp.get().getExperiments().isEnabled(12602358L)) {
          l6 = 0L;
        }
        if (l5 < l6)
        {
          k = 0;
          break;
        }
        long l7 = Storage.externalStorageAvailableSpace();
        if (l7 <= 0L)
        {
          k = 0;
          break;
        }
        if (l7 - ((Long)G.downloadExternalFreeSpaceThresholdBytes.get()).longValue() < l5 * ((Integer)G.downloadExternalFreeSpaceFactor.get()).intValue() / 100L)
        {
          k = 0;
          break;
        }
      }
      k = 1;
      break;
      label616:
      SplitDeliveryData localSplitDeliveryData2 = this.mActiveSplit;
      long l1 = Storage.dataPartitionAvailableSpace();
      int m;
      if ((i & 0x200) != 0)
      {
        if (localSplitDeliveryData2.gzippedDownloadUrl != null) {
          break label869;
        }
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = this.packageName;
        arrayOfObject2[1] = this.mActiveSplitId;
        arrayOfObject2[2] = Integer.valueOf(i);
        FinskyLog.wtf("Missing gzipped apk for %s (%s) while apk_is_gzipped set in %d", arrayOfObject2);
        m = 0;
      }
      for (;;)
      {
        if (m == 0) {
          break label875;
        }
        j = 0x200 | j;
        str4 = this.mActiveSplit.gzippedDownloadUrl;
        l2 = -1L;
        l3 = this.mActiveSplit.gzippedDownloadSize;
        this.mLogAppData.downloadGzip = true;
        this.mLogAppData.hasDownloadGzip = true;
        break;
        if ((i & 0x400) != 0)
        {
          m = 0;
        }
        else if (TextUtils.isEmpty(localSplitDeliveryData2.gzippedDownloadUrl))
        {
          m = 0;
        }
        else
        {
          long l4 = 110L * (localSplitDeliveryData2.downloadSize + localSplitDeliveryData2.gzippedDownloadSize) / 100L;
          if (l1 < l4)
          {
            logCopyFailure(this.packageName, "gzip-free-space", 0, null);
            Object[] arrayOfObject1 = new Object[4];
            arrayOfObject1[0] = this.packageName;
            arrayOfObject1[1] = this.mActiveSplitId;
            arrayOfObject1[2] = Long.valueOf(l4);
            arrayOfObject1[3] = Long.valueOf(l1);
            FinskyLog.d("Cannot use gzipped apk %s (%s), need %d, free %d", arrayOfObject1);
            m = 0;
          }
          else
          {
            label869:
            m = 1;
          }
        }
      }
      label875:
      str4 = this.mActiveSplit.downloadUrl;
      l2 = this.mActiveSplit.downloadSize;
      l3 = l2;
      break label386;
      bool1 = false;
      break label430;
    }
  }
  
  final InputStream getPatch(Context paramContext, Uri paramUri, SplitDeliveryData paramSplitDeliveryData)
  {
    try
    {
      Object localObject = paramContext.getContentResolver().openInputStream(paramUri);
      if (isGzippedPatch(paramSplitDeliveryData))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject, 8192);
        localObject = localGZIPInputStream;
      }
      return localObject;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramUri;
      arrayOfObject2[1] = localFileNotFoundException.getMessage();
      FinskyLog.w("FileNotFoundException %s %s", arrayOfObject2);
      logPatchFailure(this.packageName, "patch-FileNotFoundException", 0, localFileNotFoundException);
      return null;
    }
    catch (IOException localIOException)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramUri;
      arrayOfObject1[1] = localIOException.getMessage();
      FinskyLog.w("IOException %s %s", arrayOfObject1);
      logPatchFailure(this.packageName, "patch-IOException", 0, localIOException);
    }
    return null;
  }
  
  public final int getState()
  {
    AppStates.AppState localAppState = this.mAppStates.getApp(this.packageName);
    int i = 0;
    int j;
    if (localAppState != null)
    {
      InstallerDataStore.InstallerData localInstallerData = localAppState.installerData;
      i = 0;
      if (localInstallerData != null)
      {
        j = localAppState.installerData.installerState;
        i = 0;
      }
    }
    switch (j)
    {
    default: 
      i = 2;
    case 0: 
    case 70: 
    case 80: 
      return i;
    }
    return 3;
  }
  
  final void logAndNotifyDownloadError(String paramString, int paramInt, Exception paramException)
  {
    FinskyApp.get().getEventLogger().logBackgroundEvent(104, paramString, null, paramInt, null, this.mLogAppData);
    notifyListeners(3, paramInt);
    showDownloadNotification(paramInt, null);
  }
  
  final void notifyListeners(int paramInt1, int paramInt2)
  {
    this.mInstaller.notifyListeners(this.packageName, paramInt1, paramInt2);
  }
  
  final void populateFields(AppStates.AppState paramAppState)
  {
    InstallerDataStore.InstallerData localInstallerData = paramAppState.installerData;
    boolean bool1;
    int i;
    boolean bool2;
    if (paramAppState.packageManagerState != null)
    {
      bool1 = true;
      this.mIsUpdate = bool1;
      this.mTitle = localInstallerData.title;
      checkForEmptyTitle("populateFields", this.packageName, this.mTitle, localInstallerData);
      i = localInstallerData.flags;
      if ((i & 0x10) != 0) {
        break label266;
      }
      bool2 = true;
      label59:
      this.mShowProgress = bool2;
      if ((i & 0x1) != 0) {
        break label272;
      }
    }
    label266:
    label272:
    for (boolean bool3 = true;; bool3 = false)
    {
      this.mShowErrorNotifications = bool3;
      int j = i & 0x80;
      boolean bool4 = false;
      if (j == 0) {
        bool4 = true;
      }
      this.mShowCompletionNotifications = bool4;
      this.mLogAppData = new PlayStore.AppData();
      this.mLogAppData.version = localInstallerData.desiredVersion;
      this.mLogAppData.hasVersion = true;
      if (paramAppState.packageManagerState != null)
      {
        this.mLogAppData.oldVersion = paramAppState.packageManagerState.installedVersion;
        this.mLogAppData.hasOldVersion = true;
        this.mLogAppData.systemApp = paramAppState.packageManagerState.isSystemApp;
        this.mLogAppData.hasSystemApp = true;
      }
      if ((i & 0x1000) != 0)
      {
        this.mLogAppData.downloadExternal = true;
        this.mLogAppData.hasDownloadExternal = true;
      }
      if ((i & 0x200) != 0)
      {
        this.mLogAppData.downloadGzip = true;
        this.mLogAppData.hasDownloadGzip = true;
      }
      if ((i & 0x4) != 0)
      {
        this.mLogAppData.downloadPatch = true;
        this.mLogAppData.hasDownloadPatch = true;
      }
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label59;
    }
  }
  
  final void populateSplitInfo(InstallerDataStore.InstallerData paramInstallerData)
  {
    this.mCompletedSplits.clear();
    for (String str : paramInstallerData.getCompletedSplitIds()) {
      this.mCompletedSplits.add(str);
    }
    this.mActiveSplitId = paramInstallerData.activeSplitId;
    if (!TextUtils.isEmpty(this.mActiveSplitId)) {
      loadActiveSplitInfo(this.mActiveSplitId, paramInstallerData.deliveryData);
    }
    updateLogWithActiveSplitInfo();
  }
  
  final void processDeliveryData(InstallerDataStore.InstallerData paramInstallerData, boolean paramBoolean)
  {
    AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
    this.mRequiredSplits.clear();
    this.mRequiredSplits.add(this.packageName);
    long l = 0L;
    for (SplitDeliveryData localSplitDeliveryData : localAndroidAppDeliveryData.splitDeliveryData)
    {
      this.mRequiredSplits.add(localSplitDeliveryData.id);
      l += localSplitDeliveryData.downloadSize;
    }
    this.mApkSize = (l + localAndroidAppDeliveryData.downloadSize);
    this.mTotalSize = this.mApkSize;
    this.mApkCompleted = 0L;
    this.mObbMainCompleted = 0L;
    this.mObbPatchCompleted = 0L;
    extractObbInfo(paramInstallerData);
    if ((this.mObbMain != null) && (this.mObbMain.getState() == 4)) {
      this.mTotalSize += this.mObbMain.getSize();
    }
    if ((this.mObbPatch != null) && (this.mObbPatch.getState() == 4)) {
      this.mTotalSize += this.mObbPatch.getSize();
    }
    int k = paramInstallerData.flags;
    if ((k & 0x800) != 0) {
      this.mMobileDataAllowed = false;
    }
    boolean bool1;
    label316:
    do
    {
      if (!this.mPackageInstaller.hasSession(this.packageName))
      {
        String str = paramInstallerData.title;
        checkForEmptyTitle("processDeliveryData", this.packageName, str, paramInstallerData);
        boolean bool3 = localAndroidAppDeliveryData.hasInstallLocation;
        int m = 0;
        if (bool3) {
          m = localAndroidAppDeliveryData.installLocation;
        }
        this.mPackageInstaller.createSession(this.packageName, this.mApkSize, str, null, InstallerImpl.calculateAndroidInstallLocation(m));
      }
      return;
      if ((k & 0x2) == 0) {
        break;
      }
      bool1 = true;
      this.mMobileDataAllowed = bool1;
    } while ((!paramBoolean) || (this.mMobileDataAllowed));
    if (this.mTotalSize < this.mInstallPolicies.mMaxBytesOverMobileRecommended) {}
    for (boolean bool2 = true;; bool2 = false)
    {
      this.mMobileDataAllowed = bool2;
      if (!this.mMobileDataAllowed) {
        break;
      }
      this.mInstaller.setFlag(this.packageName, 2);
      break;
      bool1 = false;
      break label316;
    }
  }
  
  final boolean recoverObb(AppStates.AppState paramAppState, Uri paramUri, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (String str = "Patch"; paramInt2 <= paramInt3; str = "Main")
    {
      Object[] arrayOfObject5 = new Object[4];
      arrayOfObject5[0] = this.packageName;
      arrayOfObject5[1] = str;
      arrayOfObject5[2] = Integer.valueOf(paramInt2);
      arrayOfObject5[3] = Integer.valueOf(paramInt3);
      FinskyLog.d("Recovery of %s %s Obb skipped because desired= %d installed= %d", arrayOfObject5);
      return false;
    }
    if ((!DownloadManagerConstants.isStatusCompleted(paramInt1)) && (paramInt1 != 198))
    {
      Object[] arrayOfObject4 = new Object[2];
      arrayOfObject4[0] = this.packageName;
      arrayOfObject4[1] = str;
      FinskyLog.d("Recovery of %s %s Obb into downloading OBB state", arrayOfObject4);
      if (paramBoolean) {}
      for (Obb localObb2 = this.mObbPatch;; localObb2 = this.mObbMain)
      {
        Download localDownload = generateObbDownload(paramAppState.installerData, localObb2);
        localDownload.setContentUri(paramUri);
        this.mDownloadQueue.addRecoveredDownload(localDownload);
        return true;
      }
    }
    if (DownloadManagerConstants.isStatusSuccess(paramInt1))
    {
      Obb localObb1;
      if (paramBoolean)
      {
        localObb1 = this.mObbPatch;
        localObb1.syncStateWithStorage();
        if ((localObb1.getState() != 4) || (localObb1.finalizeTempFile())) {
          break label287;
        }
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = this.packageName;
        arrayOfObject3[1] = str;
        FinskyLog.d("Recovery of %s %s Obb skipped - finalize failed", arrayOfObject3);
        cancel(false);
        if (!paramBoolean) {
          break label279;
        }
      }
      label279:
      for (int j = 912;; j = 911)
      {
        logAndNotifyDownloadError(this.packageName, j, null);
        return false;
        localObb1 = this.mObbMain;
        break;
      }
      label287:
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.packageName;
      arrayOfObject2[1] = str;
      FinskyLog.d("Recovery of %s %s Obb into ready to install state", arrayOfObject2);
      if (paramBoolean) {}
      for (int i = 40;; i = 30)
      {
        setInstallerState(i, paramUri.toString());
        advanceState();
        return true;
      }
    }
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = this.packageName;
    arrayOfObject1[1] = str;
    arrayOfObject1[2] = Integer.valueOf(paramInt1);
    FinskyLog.d("Recovery of %s %s Obb into error state, status= %d", arrayOfObject1);
    cancel(false);
    logAndNotifyDownloadError(this.packageName, paramInt1, null);
    return false;
  }
  
  final void setInstallerState(int paramInt, Uri paramUri)
  {
    if (paramUri != null) {}
    for (String str = paramUri.toString();; str = null)
    {
      setInstallerState(paramInt, str);
      return;
    }
  }
  
  final void setInstallerState(int paramInt, String paramString)
  {
    this.mInstallerDataStore.setInstallerState(this.packageName, paramInt, paramString);
  }
  
  final void showDownloadNotification(int paramInt, String paramString)
  {
    if (this.mShowErrorNotifications) {
      this.mNotifier.showDownloadErrorMessage(this.mTitle, this.packageName, paramInt, paramString, this.mIsUpdate);
    }
  }
  
  public final void start()
  {
    AppStates.AppState localAppState;
    InstallerDataStore.InstallerData localInstallerData;
    int i;
    int j;
    try
    {
      localAppState = this.mAppStates.getApp(this.packageName);
      if ((localAppState == null) || (localAppState.installerData == null))
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = this.packageName;
        FinskyLog.wtf("Unexpected missing installer data for %s", arrayOfObject3);
        cancel(true);
        return;
      }
      localInstallerData = localAppState.installerData;
      i = localInstallerData.installerState;
      populateFields(localAppState);
      if ((i > 0) && (localInstallerData.deliveryData != null))
      {
        processDeliveryData(localInstallerData, false);
        populateSplitInfo(localInstallerData);
      }
      j = -1;
      this.mRecoveredIntoState = i;
      switch (i)
      {
      default: 
        Object[] arrayOfObject7 = new Object[3];
        arrayOfObject7[0] = Integer.valueOf(i);
        arrayOfObject7[1] = this.packageName;
        arrayOfObject7[2] = this.mActiveSplitId;
        FinskyLog.wtf("Unknown state %d for %s (%s)", arrayOfObject7);
        cancel(true);
        return;
      }
    }
    catch (Exception localException1)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.packageName;
      arrayOfObject1[1] = localException1;
      FinskyLog.w("Exception starting %s: %s", arrayOfObject1);
      try
      {
        cancelCleanup(this.mAppStates.getApp(this.packageName));
        return;
      }
      catch (Exception localException2)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = this.packageName;
        arrayOfObject2[1] = localException2;
        FinskyLog.w("Exception cleaning %s: %s", arrayOfObject2);
        return;
      }
      j = 0;
    }
    for (;;)
    {
      if ((j != -1) && (j != i)) {
        setInstallerState(j, localInstallerData.downloadUri);
      }
      advanceState();
      return;
      Object[] arrayOfObject6 = new Object[3];
      arrayOfObject6[0] = this.packageName;
      arrayOfObject6[1] = this.mActiveSplitId;
      arrayOfObject6[2] = Integer.valueOf(i);
      FinskyLog.w("Cannot restart %s (%s) from downloading state %d", arrayOfObject6);
      cancel(false);
      logAndNotifyDownloadError(this.packageName, 905, null);
      return;
      Object[] arrayOfObject5 = new Object[3];
      arrayOfObject5[0] = Integer.valueOf(i);
      arrayOfObject5[1] = this.packageName;
      arrayOfObject5[2] = this.mActiveSplitId;
      FinskyLog.w("Restarting %d for %s (%s)", arrayOfObject5);
      cancelCleanup(localAppState);
      logAndNotifyDownloadError(this.packageName, 909, null);
      return;
      Object[] arrayOfObject4 = new Object[3];
      arrayOfObject4[0] = Integer.valueOf(i);
      arrayOfObject4[1] = this.packageName;
      arrayOfObject4[2] = this.mActiveSplitId;
      FinskyLog.w("Restarting %d for %s (%s)", arrayOfObject4);
      cancelCleanup(localAppState);
      logAndNotifyDownloadError(this.packageName, 907, null);
      return;
      j = 70;
      continue;
      j = 10;
    }
  }
  
  public final String toString()
  {
    return this.packageName;
  }
  
  final boolean tryRestartWithInhibitFlag(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.packageName;
    arrayOfObject[bool] = this.mActiveSplitId;
    arrayOfObject[2] = Integer.valueOf(paramInt2);
    FinskyLog.d("Retry download of %s (%s) (inhibit %d)", arrayOfObject);
    InstallerDataStore localInstallerDataStore = this.mInstallerDataStore;
    int i = localInstallerDataStore.get(this.packageName).flags;
    if ((i & paramInt1) != 0) {}
    for (;;)
    {
      if (bool)
      {
        int j = (i | paramInt2) & (paramInt1 ^ 0xFFFFFFFF);
        localInstallerDataStore.setFlags(this.packageName, j);
        if ((paramInt1 & 0x4) != 0)
        {
          this.mLogAppData.downloadPatch = false;
          this.mLogAppData.hasDownloadPatch = false;
          this.mLogAppData.downloadGzip = false;
          this.mLogAppData.hasDownloadGzip = false;
          this.mLogAppData.patchFormat = 0;
          this.mLogAppData.hasPatchFormat = false;
        }
        if ((paramInt1 & 0x200) != 0)
        {
          this.mLogAppData.downloadGzip = false;
          this.mLogAppData.hasDownloadGzip = false;
        }
        if ((paramInt1 & 0x1000) != 0)
        {
          this.mLogAppData.downloadExternal = false;
          this.mLogAppData.hasDownloadExternal = false;
        }
        setInstallerState(40, null);
        advanceState();
      }
      return bool;
      bool = false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.InstallerTask
 * JD-Core Version:    0.7.0.1
 */