package com.google.android.finsky.activities;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryAppEntry;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.CertificateSet;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.Users;
import com.google.android.finsky.utils.Users.UserManagerFacade;
import java.util.List;

public final class AppActionAnalyzer
{
  public String[] certificateHashes = LibraryAppEntry.ANY_CERTIFICATE_HASHES;
  public int installedVersion = -1;
  public boolean isActiveDeviceAdmin = false;
  public boolean isContinueLaunch = false;
  public boolean isDisabled = false;
  public boolean isDisabledByUser = false;
  public boolean isInstalled = false;
  public boolean isInstalledOwnedPackage = false;
  public boolean isInstalledSystemApp = false;
  public boolean isLaunchable = false;
  public boolean isRefundable = false;
  public boolean isUpdatedSystemApp = false;
  private String mPackageName;
  public String refundAccount = null;
  
  public AppActionAnalyzer(String paramString, AppStates paramAppStates, Libraries paramLibraries)
  {
    this.mPackageName = paramString;
    AppStates.AppState localAppState = paramAppStates.getApp(this.mPackageName);
    boolean bool5;
    int i;
    label253:
    boolean bool2;
    label269:
    InstallerDataStore.InstallerData localInstallerData;
    label283:
    boolean bool3;
    if ((localAppState != null) && (localAppState.packageManagerState != null))
    {
      this.isInstalled = bool1;
      PackageStateRepository.PackageState localPackageState = localAppState.packageManagerState;
      this.installedVersion = localPackageState.installedVersion;
      this.isInstalledSystemApp = localPackageState.isSystemApp;
      this.isUpdatedSystemApp = localPackageState.isUpdatedSystemApp;
      this.isActiveDeviceAdmin = localPackageState.isActiveDeviceAdmin;
      boolean bool4 = paramAppStates.mPackageManager.canLaunch(this.mPackageName);
      this.isDisabled = localPackageState.isDisabled;
      this.isDisabledByUser = localPackageState.isDisabledByUser;
      if ((bool4) && (!this.isDisabled))
      {
        bool5 = bool1;
        this.isLaunchable = bool5;
      }
    }
    else
    {
      if (this.isInstalled) {
        this.certificateHashes = localAppState.packageManagerState.certificateHashes;
      }
      List localList = paramLibraries.getAppEntries(this.mPackageName, this.certificateHashes);
      if (localList.isEmpty()) {
        break label404;
      }
      i = bool1;
      if ((!this.isInstalled) || (i == 0)) {
        break label410;
      }
      bool2 = bool1;
      this.isInstalledOwnedPackage = bool2;
      localInstallerData = null;
      if (localAppState != null) {
        break label416;
      }
      this.refundAccount = internalGetRefundAccount(localInstallerData, localList, System.currentTimeMillis());
      if (this.refundAccount == null) {
        break label426;
      }
      bool3 = bool1;
      label308:
      this.isRefundable = bool3;
      if ((this.isInstalled) && (i == 0) && (!paramLibraries.getAppOwners(this.mPackageName).isEmpty()))
      {
        Object[] arrayOfObject = new Object[bool1];
        arrayOfObject[0] = this.mPackageName;
        FinskyLog.d("%s is installed but certificate mistmatch", arrayOfObject);
      }
      if ((localAppState != null) && (localAppState.installerData != null)) {
        if (TextUtils.isEmpty(localAppState.installerData.continueUrl)) {
          break label432;
        }
      }
    }
    for (;;)
    {
      this.isContinueLaunch = bool1;
      return;
      bool5 = false;
      break;
      label404:
      i = 0;
      break label253;
      label410:
      bool2 = false;
      break label269;
      label416:
      localInstallerData = localAppState.installerData;
      break label283;
      label426:
      bool3 = false;
      break label308;
      label432:
      bool1 = false;
    }
  }
  
  private static boolean findAccountInOwners(String paramString, List<LibraryAppEntry> paramList)
  {
    if (!TextUtils.isEmpty(paramString)) {
      for (int i = 0; i < paramList.size(); i++) {
        if (((LibraryAppEntry)paramList.get(i)).mAccountName.equals(paramString)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static String getAppDetailsAccount(String paramString1, String paramString2, AppStates paramAppStates, Libraries paramLibraries)
  {
    InstallerDataStore.InstallerData localInstallerData = paramAppStates.mStateStore.get(paramString1);
    PackageStateRepository.PackageState localPackageState = paramAppStates.mPackageManager.get(paramString1);
    List localList = paramLibraries.getAppEntries(paramString1, AppStates.getCertificateHashes(localPackageState));
    int i;
    if ((localPackageState != null) && (!localList.isEmpty()))
    {
      i = 1;
      if ((i == 0) || (localInstallerData == null)) {
        break label92;
      }
      String str2 = localInstallerData.accountForUpdate;
      if (!findAccountInOwners(str2, localList)) {
        break label92;
      }
      paramString2 = str2;
    }
    label92:
    do
    {
      return paramString2;
      i = 0;
      break;
      if ((i != 0) && (localInstallerData != null))
      {
        String str1 = localInstallerData.accountName;
        if (findAccountInOwners(str1, localList)) {
          return str1;
        }
      }
    } while ((findAccountInOwners(paramString2, localList)) || (localList.size() <= 0));
    return ((LibraryAppEntry)localList.get(0)).mAccountName;
  }
  
  static boolean hasCertificateMatch(String[] paramArrayOfString, CertificateSet[] paramArrayOfCertificateSet)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      if ((paramArrayOfCertificateSet.length != 0) && (paramArrayOfString.length != 0) && (paramArrayOfString.length <= paramArrayOfCertificateSet.length)) {}
    }
    label72:
    label78:
    while ((paramArrayOfCertificateSet.length == 0) || (paramArrayOfString.length == 0))
    {
      return false;
      for (int m = 0;; m++)
      {
        if (m >= paramArrayOfString.length) {
          break label78;
        }
        for (int n = 0; n < paramArrayOfCertificateSet.length; n++) {
          if (paramArrayOfString[m].equals(paramArrayOfCertificateSet[n].certificateHash[0])) {
            break label72;
          }
        }
        break;
      }
      return true;
    }
    label151:
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfString.length) {
        break label157;
      }
      for (int j = 0; j < paramArrayOfCertificateSet.length; j++) {
        for (int k = 0; k < paramArrayOfCertificateSet[j].certificateHash.length; k++) {
          if (paramArrayOfString[i].equals(paramArrayOfCertificateSet[j].certificateHash[k])) {
            break label151;
          }
        }
      }
      break;
    }
    label157:
    return true;
  }
  
  private static String internalGetRefundAccount(InstallerDataStore.InstallerData paramInstallerData, List<LibraryAppEntry> paramList, long paramLong)
  {
    long l1 = 0L;
    if (paramInstallerData != null) {
      l1 = paramInstallerData.firstDownloadMs;
    }
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      LibraryAppEntry localLibraryAppEntry = (LibraryAppEntry)paramList.get(j);
      long l2 = localLibraryAppEntry.refundPreDeliveryEndtimeMs;
      if (l1 != 0L) {
        l2 = Math.min(l2, l1 + localLibraryAppEntry.refundPostDeliveryWindowMs);
      }
      if (l2 >= paramLong) {
        return localLibraryAppEntry.mAccountName;
      }
    }
    return null;
  }
  
  public static boolean isMultiUserCertificateConflict(Document paramDocument)
  {
    if (!FinskyApp.get().mUsers.mUserManagerFacade.mayHaveHiddenPackages()) {}
    for (;;)
    {
      return false;
      if (FinskyApp.get().mPackageStateRepository.get(paramDocument.mDocument.docid) == null)
      {
        PackageManager localPackageManager = FinskyApp.get().getPackageManager();
        try
        {
          PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramDocument.mDocument.docid, 8256);
          int i = localPackageInfo.signatures.length;
          String[] arrayOfString = new String[i];
          for (int j = 0; j < i; j++) {
            arrayOfString[j] = Sha1Util.secureHash(localPackageInfo.signatures[j].toByteArray());
          }
          boolean bool = hasCertificateMatch(arrayOfString, paramDocument.getAppDetails().certificateSet);
          if (!bool) {
            return true;
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
      }
    }
    return false;
  }
  
  @TargetApi(21)
  public static boolean isUninstallBlockedByAdmin(String paramString)
  {
    DevicePolicyManager localDevicePolicyManager = (DevicePolicyManager)FinskyApp.get().getSystemService("device_policy");
    return (Build.VERSION.SDK_INT > 21) && (localDevicePolicyManager.isUninstallBlocked(null, paramString));
  }
  
  public final boolean hasUpdateAvailable(Document paramDocument)
  {
    return ((this.isInstalledOwnedPackage) || (this.isInstalledSystemApp)) && (paramDocument.getAppDetails().hasVersionCode) && (paramDocument.getAppDetails().versionCode > this.installedVersion);
  }
  
  public final boolean isUninstallable()
  {
    return (this.isInstalled) && (!this.isActiveDeviceAdmin) && ((!this.isInstalledSystemApp) || (this.isUpdatedSystemApp)) && (!isUninstallBlockedByAdmin(this.mPackageName));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AppActionAnalyzer
 * JD-Core Version:    0.7.0.1
 */