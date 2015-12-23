package com.google.android.finsky.appstate;

import android.accounts.Account;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryAppEntry;
import com.google.android.finsky.library.LibraryEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppStates
{
  private final String mNodeId;
  public final PackageStateRepository mPackageManager;
  public final WriteThroughInstallerDataStore mStateStore;
  
  public AppStates(String paramString, WriteThroughInstallerDataStore paramWriteThroughInstallerDataStore, PackageStateRepository paramPackageStateRepository)
  {
    this.mNodeId = paramString;
    this.mStateStore = paramWriteThroughInstallerDataStore;
    this.mPackageManager = paramPackageStateRepository;
  }
  
  public static String[] getCertificateHashes(PackageStateRepository.PackageState paramPackageState)
  {
    if (paramPackageState == null) {
      return LibraryAppEntry.ANY_CERTIFICATE_HASHES;
    }
    return paramPackageState.certificateHashes;
  }
  
  public final AppState getApp(String paramString)
  {
    InstallerDataStore.InstallerData localInstallerData = this.mStateStore.get(paramString);
    PackageStateRepository.PackageState localPackageState = this.mPackageManager.get(paramString);
    if ((localInstallerData != null) || (localPackageState != null)) {
      return new AppState(paramString, this.mNodeId, localPackageState, localInstallerData);
    }
    return null;
  }
  
  public final List<AppState> getAppsToInstall()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mStateStore.getAll().iterator();
    while (localIterator.hasNext())
    {
      InstallerDataStore.InstallerData localInstallerData = (InstallerDataStore.InstallerData)localIterator.next();
      if (localInstallerData.desiredVersion != -1)
      {
        PackageStateRepository.PackageState localPackageState = this.mPackageManager.get(localInstallerData.packageName);
        if ((localPackageState == null) || (localInstallerData.desiredVersion > localPackageState.installedVersion)) {
          localArrayList.add(new AppState(localInstallerData.packageName, this.mNodeId, localPackageState, localInstallerData));
        }
      }
    }
    return localArrayList;
  }
  
  public final Map<String, List<String>> getOwnedByAccountBlocking(Libraries paramLibraries, boolean paramBoolean)
  {
    HashMap localHashMap1 = new HashMap();
    Iterator localIterator1 = paramLibraries.getAccountLibraries().iterator();
    while (localIterator1.hasNext())
    {
      AccountLibrary localAccountLibrary = (AccountLibrary)localIterator1.next();
      ArrayList localArrayList2 = new ArrayList();
      localHashMap1.put(localAccountLibrary.mAccount.name, localArrayList2);
    }
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator2 = this.mStateStore.getAll().iterator();
    while (localIterator2.hasNext())
    {
      InstallerDataStore.InstallerData localInstallerData3 = (InstallerDataStore.InstallerData)localIterator2.next();
      localHashMap2.put(localInstallerData3.packageName, localInstallerData3);
    }
    Iterator localIterator3 = this.mPackageManager.getAllBlocking().iterator();
    while (localIterator3.hasNext())
    {
      PackageStateRepository.PackageState localPackageState = (PackageStateRepository.PackageState)localIterator3.next();
      InstallerDataStore.InstallerData localInstallerData2 = (InstallerDataStore.InstallerData)localHashMap2.remove(localPackageState.packageName);
      localArrayList1.add(new AppState(localPackageState.packageName, this.mNodeId, localPackageState, localInstallerData2));
    }
    if (!paramBoolean)
    {
      Iterator localIterator6 = localHashMap2.values().iterator();
      while (localIterator6.hasNext())
      {
        InstallerDataStore.InstallerData localInstallerData1 = (InstallerDataStore.InstallerData)localIterator6.next();
        localArrayList1.add(new AppState(localInstallerData1.packageName, this.mNodeId, null, localInstallerData1));
      }
    }
    Iterator localIterator4 = localArrayList1.iterator();
    while (localIterator4.hasNext())
    {
      AppState localAppState = (AppState)localIterator4.next();
      String[] arrayOfString = getCertificateHashes(localAppState.packageManagerState);
      Iterator localIterator5 = paramLibraries.getAppEntries(localAppState.packageName, arrayOfString).iterator();
      while (localIterator5.hasNext()) {
        ((List)localHashMap1.get(((LibraryAppEntry)localIterator5.next()).mAccountName)).add(localAppState.packageName);
      }
    }
    return localHashMap1;
  }
  
  public final boolean load(Runnable paramRunnable)
  {
    return this.mStateStore.load(paramRunnable);
  }
  
  public static final class AppState
  {
    public final InstallerDataStore.InstallerData installerData;
    public final String nodeId;
    public final PackageStateRepository.PackageState packageManagerState;
    public final String packageName;
    
    public AppState(String paramString1, String paramString2, PackageStateRepository.PackageState paramPackageState, InstallerDataStore.InstallerData paramInstallerData)
    {
      this.packageName = paramString1;
      this.nodeId = paramString2;
      this.packageManagerState = paramPackageState;
      this.installerData = paramInstallerData;
    }
    
    public final String toString()
    {
      int i = -1;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[1] = this.nodeId;
      if (this.packageManagerState != null) {}
      for (int j = this.packageManagerState.installedVersion;; j = i)
      {
        arrayOfObject[2] = Integer.valueOf(j);
        if (this.installerData != null) {
          i = this.installerData.desiredVersion;
        }
        arrayOfObject[3] = Integer.valueOf(i);
        return String.format("{package=%s nodeid=%s installed=%d desired=%d}", arrayOfObject);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.AppStates
 * JD-Core Version:    0.7.0.1
 */