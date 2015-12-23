package com.google.android.finsky.services;

import android.app.Service;
import android.content.Intent;
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
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.Libraries;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class PlayGearheadService
  extends Service
{
  private PlayGearheadServiceImpl mPlayGearheadServiceImpl;
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mPlayGearheadServiceImpl;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mPlayGearheadServiceImpl = new PlayGearheadServiceImpl(FinskyApp.get().mLibraries, FinskyApp.get().mPackageStateRepository, FinskyApp.get().mAppStates);
  }
  
  private final class PlayGearheadServiceImpl
    extends IPlayGearheadService.Stub
  {
    private final AppStates mAppStates;
    private final Libraries mLibraries;
    private final PackageStateRepository mPackageStateRepository;
    
    public PlayGearheadServiceImpl(Libraries paramLibraries, PackageStateRepository paramPackageStateRepository, AppStates paramAppStates)
    {
      this.mLibraries = paramLibraries;
      this.mPackageStateRepository = paramPackageStateRepository;
      this.mAppStates = paramAppStates;
    }
    
    public final Bundle validatePackageAcquiredByPlay(String paramString)
      throws RemoteException
    {
      Bundle localBundle = new Bundle();
      localBundle.putBoolean("Finsky.IsValid", false);
      PackageStateRepository.PackageState localPackageState = this.mPackageStateRepository.get(paramString);
      if (localPackageState == null) {}
      int i;
      do
      {
        do
        {
          return localBundle;
          this.mAppStates.load(null);
          this.mLibraries.blockingLoad();
        } while (this.mLibraries.getAppOwners(localPackageState.packageName, localPackageState.certificateHashes).isEmpty());
        if (!((Boolean)G.gearheadExternalHostingLockout.get()).booleanValue()) {
          break;
        }
        this.mAppStates.mStateStore.load();
        InstallerDataStore.InstallerData localInstallerData = this.mAppStates.mStateStore.get(paramString);
        if (localInstallerData == null) {
          break;
        }
        i = localInstallerData.persistentFlags;
      } while (((i & 0x4) == 0) || ((i & 0x2) != 0));
      localBundle.putBoolean("Finsky.IsValid", true);
      return localBundle;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.PlayGearheadService
 * JD-Core Version:    0.7.0.1
 */