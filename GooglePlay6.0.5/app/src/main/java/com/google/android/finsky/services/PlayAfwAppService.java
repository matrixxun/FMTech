package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryAppEntry;
import com.google.android.gms.common.GoogleSignatureVerifier;
import java.util.Iterator;
import java.util.List;

public class PlayAfwAppService
  extends Service
{
  private PlayAfwAppServiceImpl mPlayAfwAppService;
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mPlayAfwAppService;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mPlayAfwAppService = new PlayAfwAppServiceImpl(FinskyApp.get().mLibraries, FinskyApp.get().mPackageStateRepository, FinskyApp.get().mAppStates, FinskyApp.get().getExperiments(), new ServiceCallerVerifier(), this);
  }
  
  static final class PlayAfwAppServiceImpl
    extends IPlayAfwAppService.Stub
  {
    private final AppStates mAppStates;
    private final Context mContext;
    private final FinskyExperiments mExperiments;
    private final Libraries mLibraries;
    private final PackageStateRepository mPackageStateRepository;
    private final PlayAfwAppService.ServiceCallerVerifier mServiceCallerVerifier;
    
    public PlayAfwAppServiceImpl(Libraries paramLibraries, PackageStateRepository paramPackageStateRepository, AppStates paramAppStates, FinskyExperiments paramFinskyExperiments, PlayAfwAppService.ServiceCallerVerifier paramServiceCallerVerifier, Context paramContext)
    {
      this.mLibraries = paramLibraries;
      this.mPackageStateRepository = paramPackageStateRepository;
      this.mAppStates = paramAppStates;
      this.mExperiments = paramFinskyExperiments;
      this.mServiceCallerVerifier = paramServiceCallerVerifier;
      this.mContext = paramContext;
    }
    
    public final Bundle validateWorkPackageByPlay(String paramString)
      throws RemoteException
    {
      Bundle localBundle = new Bundle();
      if (!this.mExperiments.isEnabled(12603513L)) {}
      int j;
      do
      {
        int i;
        do
        {
          PackageStateRepository.PackageState localPackageState;
          do
          {
            return localBundle;
            if (!PlayAfwAppService.ServiceCallerVerifier.isUidAfwApp(this.mContext.getPackageManager())) {
              throw new SecurityException("Only AfwApp can access this service");
            }
            localBundle.putBoolean("IsValid", false);
            localPackageState = this.mPackageStateRepository.get(paramString);
          } while (localPackageState == null);
          this.mAppStates.load(null);
          this.mLibraries.blockingLoad();
          Iterator localIterator = this.mLibraries.getAppOwners(localPackageState.packageName, localPackageState.certificateHashes).iterator();
          LibraryAppEntry localLibraryAppEntry;
          do
          {
            Account localAccount;
            do
            {
              boolean bool = localIterator.hasNext();
              i = 0;
              if (!bool) {
                break;
              }
              localAccount = (Account)localIterator.next();
            } while (!"com.google.work".equals(localAccount.type));
            localLibraryAppEntry = this.mLibraries.getAccountLibrary(localAccount).getAppEntry(paramString);
          } while ((localLibraryAppEntry == null) || (!localLibraryAppEntry.isOwnedViaLicense));
          i = 1;
        } while (i == 0);
        this.mAppStates.mStateStore.load();
        InstallerDataStore.InstallerData localInstallerData = this.mAppStates.mStateStore.get(paramString);
        if (localInstallerData == null) {
          break;
        }
        j = localInstallerData.persistentFlags;
      } while (((j & 0x4) == 0) || ((j & 0x2) != 0));
      localBundle.putBoolean("IsValid", true);
      return localBundle;
    }
  }
  
  protected static final class ServiceCallerVerifier
  {
    public static boolean isUidAfwApp(PackageManager paramPackageManager)
    {
      int i = Binder.getCallingUid();
      GoogleSignatureVerifier localGoogleSignatureVerifier = GoogleSignatureVerifier.getInstance();
      String[] arrayOfString1 = paramPackageManager.getPackagesForUid(i);
      boolean bool;
      if ((arrayOfString1 == null) || (arrayOfString1.length == 0))
      {
        bool = false;
        if (bool) {
          break label46;
        }
      }
      for (;;)
      {
        return false;
        bool = localGoogleSignatureVerifier.isPackageGoogleSigned(paramPackageManager, arrayOfString1[0]);
        break;
        label46:
        String[] arrayOfString2 = paramPackageManager.getPackagesForUid(i);
        if (arrayOfString2 != null)
        {
          int j = arrayOfString2.length;
          for (int k = 0; k < j; k++) {
            if ("com.google.android.apps.work.core".equals(arrayOfString2[k])) {
              return true;
            }
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.PlayAfwAppService
 * JD-Core Version:    0.7.0.1
 */