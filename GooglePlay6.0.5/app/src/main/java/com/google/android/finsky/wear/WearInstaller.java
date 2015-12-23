package com.google.android.finsky.wear;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData.Builder;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.List;

public final class WearInstaller
  implements DownloadQueueListener
{
  int mBusyRefcount;
  final DownloadQueue mDownloadQueue;
  final GoogleApiClient mGoogleApiClient;
  final InstallPolicies mInstallPolicies;
  WearInstallerTask mInstallerTask;
  private final Libraries mLibraries;
  private final Handler mMainThreadHandler;
  final WearAppStatesFactory mWearAppStatesFactory;
  final WearSupportService mWearSupportService;
  
  public WearInstaller(WearSupportService paramWearSupportService, WearAppStatesFactory paramWearAppStatesFactory, Libraries paramLibraries, DownloadQueue paramDownloadQueue, InstallPolicies paramInstallPolicies, GoogleApiClient paramGoogleApiClient)
  {
    this.mWearSupportService = paramWearSupportService;
    this.mWearAppStatesFactory = paramWearAppStatesFactory;
    this.mLibraries = paramLibraries;
    this.mDownloadQueue = paramDownloadQueue;
    this.mInstallPolicies = paramInstallPolicies;
    this.mGoogleApiClient = paramGoogleApiClient;
    this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    this.mBusyRefcount = 0;
  }
  
  private WearInstallerTask getInstallerTask(Download paramDownload)
  {
    String str1 = paramDownload.getNodeId();
    WearInstallerTask localWearInstallerTask;
    if (str1 == null) {
      localWearInstallerTask = null;
    }
    AppStates.AppState localAppState;
    do
    {
      return localWearInstallerTask;
      if (!WearDeviceConfigurationHelper.checkKnownNodeId(str1))
      {
        FinskyLog.w("Cancel download %s because bad node", new Object[] { paramDownload });
        this.mDownloadQueue.cancel(paramDownload);
        return null;
      }
      String str2 = paramDownload.getPackageName();
      localWearInstallerTask = getInstallerTask(str1, str2);
      if (localWearInstallerTask == null)
      {
        FinskyLog.w("Cancel download %s because no InstallerTask", new Object[] { paramDownload });
        this.mDownloadQueue.cancel(paramDownload);
        return null;
      }
      if (!localWearInstallerTask.nodeId.equals(paramDownload.getNodeId()))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramDownload;
        arrayOfObject[1] = localWearInstallerTask.nodeId;
        FinskyLog.w("Cancel download %s because InstallerTask node %s", arrayOfObject);
        this.mDownloadQueue.cancel(paramDownload);
        return null;
      }
      localAppState = this.mWearAppStatesFactory.createAppStates(str1).getApp(str2);
    } while ((localAppState != null) && (localAppState.installerData != null));
    FinskyLog.w("Cancel download %s no installerdata", new Object[] { paramDownload });
    this.mDownloadQueue.cancel(paramDownload);
    return null;
  }
  
  private WearInstallerTask getInstallerTask(String paramString1, String paramString2)
  {
    if ((this.mInstallerTask != null) && (this.mInstallerTask.packageName.equals(paramString2)) && (this.mInstallerTask.nodeId.equals(paramString1))) {
      return this.mInstallerTask;
    }
    return null;
  }
  
  static void reportStartupStatus(String paramString1, AppStates.AppState paramAppState, int paramInt, String paramString2)
  {
    int i;
    if (paramInt == 0)
    {
      i = 110;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramAppState.packageName;
      arrayOfObject2[1] = paramString1;
      FinskyLog.w("Successful remote install of %s (%s)", arrayOfObject2);
    }
    for (;;)
    {
      PlayStore.AppData localAppData = new PlayStore.AppData();
      localAppData.hasVersion = true;
      localAppData.version = paramAppState.installerData.desiredVersion;
      if (paramAppState.packageManagerState != null)
      {
        int j = paramAppState.packageManagerState.installedVersion;
        if (localAppData.version != j)
        {
          localAppData.hasOldVersion = true;
          localAppData.oldVersion = paramAppState.packageManagerState.installedVersion;
        }
      }
      localAppData.hasForWear = true;
      localAppData.forWear = true;
      localAppData.systemApp = paramAppState.packageManagerState.isSystemApp;
      localAppData.hasSystemApp = true;
      FinskyApp.get().getEventLogger().logBackgroundEvent(i, paramAppState.packageName, null, paramInt, null, localAppData);
      return;
      i = 111;
      Object[] arrayOfObject1 = new Object[4];
      arrayOfObject1[0] = paramAppState.packageName;
      arrayOfObject1[1] = paramString1;
      arrayOfObject1[2] = Integer.valueOf(paramInt);
      arrayOfObject1[3] = paramString2;
      FinskyLog.w("Failed remote install of %s (%s) because %d (%s)", arrayOfObject1);
    }
  }
  
  final void clearInstallerState(AppStates.AppState paramAppState)
  {
    if ((paramAppState == null) || (paramAppState.installerData == null)) {
      return;
    }
    WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = this.mWearAppStatesFactory.getInstallerDataStore(paramAppState.nodeId);
    InstallerDataStore.InstallerData.Builder localBuilder = InstallerDataStore.InstallerData.Builder.buildUpon(paramAppState.installerData, paramAppState.packageName);
    localBuilder.setDesiredVersion(-1);
    localBuilder.setInstallerState(0);
    localBuilder.setDownloadUri(null);
    localBuilder.setFlags(0);
    localBuilder.setDeliveryToken(null);
    localBuilder.setCompletedSplitIds(null);
    localBuilder.setActiveSplitId(null);
    localBuilder.setRequestId(null);
    localWriteThroughInstallerDataStore.put(localBuilder.mInstance);
  }
  
  public final int getState(String paramString1, String paramString2)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramString1, paramString2);
    int i;
    if (localWearInstallerTask != null)
    {
      AppStates.AppState localAppState2 = localWearInstallerTask.mAppStates.getApp(localWearInstallerTask.packageName);
      i = 0;
      if (localAppState2 != null)
      {
        InstallerDataStore.InstallerData localInstallerData3 = localAppState2.installerData;
        i = 0;
        if (localInstallerData3 != null)
        {
          int m = localAppState2.installerData.installerState;
          i = 0;
          switch (m)
          {
          default: 
            i = 2;
          }
        }
      }
    }
    int j;
    int k;
    do
    {
      AppStates.AppState localAppState1;
      InstallerDataStore.InstallerData localInstallerData1;
      do
      {
        do
        {
          boolean bool;
          do
          {
            return i;
            return 3;
            return 5;
            bool = WearDeviceConfigurationHelper.checkKnownNodeId(paramString1);
            i = 0;
          } while (!bool);
          localAppState1 = this.mWearAppStatesFactory.createAppStates(paramString1).getApp(paramString2);
          i = 0;
        } while (localAppState1 == null);
        localInstallerData1 = localAppState1.installerData;
        i = 0;
      } while (localInstallerData1 == null);
      InstallerDataStore.InstallerData localInstallerData2 = localAppState1.installerData;
      if (localInstallerData2.installerState == 90) {
        return 5;
      }
      j = -1;
      if (localAppState1.packageManagerState != null) {
        j = localAppState1.packageManagerState.installedVersion;
      }
      k = localInstallerData2.desiredVersion;
      i = 0;
    } while (k <= j);
    return 1;
  }
  
  /* Error */
  public final boolean isIdle()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 128	com/google/android/finsky/wear/WearInstaller:mInstallerTask	Lcom/google/android/finsky/wear/WearInstallerTask;
    //   6: ifnonnull +18 -> 24
    //   9: aload_0
    //   10: getfield 56	com/google/android/finsky/wear/WearInstaller:mBusyRefcount	I
    //   13: istore_3
    //   14: iload_3
    //   15: ifgt +9 -> 24
    //   18: iconst_1
    //   19: istore_2
    //   20: aload_0
    //   21: monitorexit
    //   22: iload_2
    //   23: ireturn
    //   24: iconst_0
    //   25: istore_2
    //   26: goto -6 -> 20
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	34	0	this	WearInstaller
    //   29	4	1	localObject	Object
    //   19	7	2	bool	boolean
    //   13	2	3	i	int
    // Exception table:
    //   from	to	target	type
    //   2	14	29	finally
  }
  
  public final void kick()
  {
    try
    {
      this.mBusyRefcount = (1 + this.mBusyRefcount);
      this.mMainThreadHandler.post(new Runnable()
      {
        public final void run()
        {
          for (;;)
          {
            int j;
            int m;
            WearInstallerTask localWearInstallerTask;
            AppStates.AppState localAppState2;
            synchronized (WearInstaller.this)
            {
              WearInstaller localWearInstaller2 = WearInstaller.this;
              localWearInstaller2.mBusyRefcount = (-1 + localWearInstaller2.mBusyRefcount);
              WearInstaller localWearInstaller3 = WearInstaller.this;
              if (localWearInstaller3.mInstallerTask == null)
              {
                String[] arrayOfString = WearDeviceConfigurationHelper.getNodeIds();
                int i = arrayOfString.length;
                j = 0;
                if (j < i)
                {
                  String str = arrayOfString[j];
                  AppStates localAppStates = localWearInstaller3.mWearAppStatesFactory.createAppStates(str);
                  List localList = localAppStates.getAppsToInstall();
                  int k = localList.size();
                  m = 0;
                  if (m >= k) {
                    break label298;
                  }
                  AppStates.AppState localAppState3 = (AppStates.AppState)localList.get(m);
                  if (localAppState3.installerData.installerState == 90) {
                    break label292;
                  }
                  localAppState1 = localAppState3;
                  if (localAppState1 == null) {
                    break label1147;
                  }
                  Object[] arrayOfObject1 = new Object[2];
                  arrayOfObject1[0] = str;
                  arrayOfObject1[1] = localAppState1.packageName;
                  FinskyLog.d("Wear node %s kick - starting %s", arrayOfObject1);
                  localWearInstaller3.mInstallerTask = new WearInstallerTask(localAppState1.packageName, str, FinskyApp.get(), localWearInstaller3, localAppStates, localWearInstaller3.mDownloadQueue, localWearInstaller3.mInstallPolicies, localWearInstaller3.mGoogleApiClient);
                  localWearInstallerTask = localWearInstaller3.mInstallerTask;
                  localAppState2 = localWearInstallerTask.mAppStates.getApp(localWearInstallerTask.packageName);
                  if ((localAppState2 != null) && (localAppState2.installerData != null)) {
                    break label304;
                  }
                  Object[] arrayOfObject2 = new Object[1];
                  arrayOfObject2[0] = localWearInstallerTask.packageName;
                  FinskyLog.wtf("Unexpected missing installer data for %s", arrayOfObject2);
                  localWearInstallerTask.cancel$1385ff();
                }
                label270:
                if (localWearInstaller3.mInstallerTask == null) {
                  localWearInstaller3.mWearSupportService.stopSelfIfIdle();
                }
              }
              return;
            }
            label292:
            m++;
            continue;
            label298:
            AppStates.AppState localAppState1 = null;
            continue;
            label304:
            InstallerDataStore.InstallerData localInstallerData1 = localAppState2.installerData;
            int n = localInstallerData1.installerState;
            InstallerDataStore.InstallerData localInstallerData2 = localAppState2.installerData;
            boolean bool1;
            label336:
            int i1;
            boolean bool2;
            label371:
            boolean bool3;
            if (localAppState2.packageManagerState != null)
            {
              bool1 = true;
              localWearInstallerTask.mIsUpdate = bool1;
              localWearInstallerTask.mTitle = localInstallerData2.title;
              i1 = localInstallerData2.flags;
              if ((i1 & 0x10) != 0) {
                break label786;
              }
              bool2 = true;
              localWearInstallerTask.mShowProgress = bool2;
              if ((i1 & 0x1) != 0) {
                break label792;
              }
              bool3 = true;
              label388:
              localWearInstallerTask.mShowErrorNotifications = bool3;
              if ((i1 & 0x80) != 0) {
                break label798;
              }
            }
            label786:
            label792:
            label798:
            for (boolean bool4 = true;; bool4 = false)
            {
              localWearInstallerTask.mShowCompletionNotifications = bool4;
              localWearInstallerTask.mLogAppData = new PlayStore.AppData();
              localWearInstallerTask.mLogAppData.version = localInstallerData2.desiredVersion;
              localWearInstallerTask.mLogAppData.hasVersion = true;
              localWearInstallerTask.mLogAppData.forWear = true;
              localWearInstallerTask.mLogAppData.hasForWear = true;
              if (localAppState2.packageManagerState != null)
              {
                localWearInstallerTask.mLogAppData.oldVersion = localAppState2.packageManagerState.installedVersion;
                localWearInstallerTask.mLogAppData.hasOldVersion = true;
                localWearInstallerTask.mLogAppData.systemApp = localAppState2.packageManagerState.isSystemApp;
                localWearInstallerTask.mLogAppData.hasSystemApp = true;
              }
              if ((i1 & 0x200) != 0)
              {
                localWearInstallerTask.mLogAppData.downloadGzip = true;
                localWearInstallerTask.mLogAppData.hasDownloadGzip = true;
              }
              if ((n > 0) && (localInstallerData1.deliveryData != null)) {
                localWearInstallerTask.processDeliveryData(localInstallerData1, false);
              }
              i2 = -1;
              localWearInstallerTask.mRecoveredIntoState = n;
              switch (n)
              {
              default: 
                Object[] arrayOfObject8 = new Object[3];
                arrayOfObject8[0] = Integer.valueOf(n);
                arrayOfObject8[1] = localWearInstallerTask.packageName;
                arrayOfObject8[2] = localWearInstallerTask.nodeId;
                FinskyLog.wtf("Unknown state %d for %s (%s)", arrayOfObject8);
                localWearInstallerTask.cancel$1385ff();
                break label270;
                bool1 = false;
                break label336;
                bool2 = false;
                break label371;
                bool3 = false;
                break label388;
              }
            }
            int i2 = 0;
            for (;;)
            {
              if ((i2 != -1) && (i2 != n)) {
                localWearInstallerTask.setInstallerState(i2, localInstallerData1.downloadUri);
              }
              localWearInstallerTask.advanceState();
              break;
              i2 = 10;
              continue;
              Object[] arrayOfObject7 = new Object[3];
              arrayOfObject7[0] = localWearInstallerTask.packageName;
              arrayOfObject7[1] = localWearInstallerTask.nodeId;
              arrayOfObject7[2] = Integer.valueOf(n);
              FinskyLog.w("Cannot restart %s (%s) from downloading state %d", arrayOfObject7);
              localWearInstallerTask.cancel$1385ff();
              localWearInstallerTask.logAndNotifyDownloadError(localWearInstallerTask.packageName, 905, null);
              break;
              Object[] arrayOfObject6 = new Object[3];
              arrayOfObject6[0] = localWearInstallerTask.packageName;
              arrayOfObject6[1] = localWearInstallerTask.nodeId;
              arrayOfObject6[2] = Integer.valueOf(n);
              FinskyLog.w("Cannot restart %s (%s) from downloading state %d", arrayOfObject6);
              localWearInstallerTask.cancelCleanupState(localAppState2);
              localWearInstallerTask.logAndNotifyDownloadError(localWearInstallerTask.packageName, 909, null);
              break;
              Object[] arrayOfObject5 = new Object[3];
              arrayOfObject5[0] = Integer.valueOf(n);
              arrayOfObject5[1] = localWearInstallerTask.packageName;
              arrayOfObject5[2] = localWearInstallerTask.nodeId;
              FinskyLog.w("Restarting %d for %s (%s)", arrayOfObject5);
              localWearInstallerTask.cancelCleanupState(localAppState2);
              localWearInstallerTask.logAndNotifyDownloadError(localWearInstallerTask.packageName, 907, null);
              break;
              i2 = 70;
              continue;
              i2 = 70;
            }
            Object[] arrayOfObject4 = new Object[3];
            arrayOfObject4[0] = Integer.valueOf(n);
            arrayOfObject4[1] = localWearInstallerTask.packageName;
            arrayOfObject4[2] = localWearInstallerTask.nodeId;
            FinskyLog.wtf("Illegal state for wearable, state %d for %s (%s)", arrayOfObject4);
            localWearInstallerTask.cancel$1385ff();
            continue;
            Object[] arrayOfObject3 = new Object[3];
            arrayOfObject3[0] = Integer.valueOf(n);
            arrayOfObject3[1] = localWearInstallerTask.packageName;
            arrayOfObject3[2] = localWearInstallerTask.nodeId;
            FinskyLog.wtf("Illegal state for wearable, state %d for %s (%s)", arrayOfObject3);
            localWearInstallerTask.cancel$1385ff();
            continue;
            label1147:
            j++;
          }
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void onCancel(Download paramDownload)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramDownload);
    if (paramDownload.getNodeId() != null) {
      if (localWearInstallerTask != null) {
        break label51;
      }
    }
    label51:
    for (PlayStore.AppData localAppData = null;; localAppData = localWearInstallerTask.mLogAppData)
    {
      FinskyApp.get().getEventLogger().logBackgroundEvent(103, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      if (localWearInstallerTask != null) {
        localWearInstallerTask.cancel$1385ff();
      }
      return;
    }
  }
  
  public final void onComplete(Download paramDownload)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramDownload);
    if (paramDownload.getNodeId() != null) {
      if (localWearInstallerTask != null) {
        break label89;
      }
    }
    InstallerDataStore.InstallerData localInstallerData;
    label89:
    for (PlayStore.AppData localAppData = null;; localAppData = localWearInstallerTask.mLogAppData)
    {
      FinskyApp.get().getEventLogger().logBackgroundEvent(102, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      if (localWearInstallerTask != null)
      {
        localInstallerData = localWearInstallerTask.mAppStates.getApp(localWearInstallerTask.packageName).installerData;
        if (localInstallerData.installerState != 45) {
          break;
        }
        localWearInstallerTask.setInstallerState(50, paramDownload.getContentUri());
        localWearInstallerTask.advanceState();
      }
      return;
    }
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = localWearInstallerTask.packageName;
    arrayOfObject[1] = localWearInstallerTask.nodeId;
    arrayOfObject[2] = Integer.valueOf(localInstallerData.installerState);
    FinskyLog.e("Unexpected download completion state for %s (%s): %d", arrayOfObject);
    localWearInstallerTask.cancel$1385ff();
    localWearInstallerTask.logAndNotifyDownloadError(localWearInstallerTask.packageName, 904, null);
  }
  
  public final void onError(Download paramDownload, int paramInt)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramDownload);
    if (paramDownload.getNodeId() != null) {
      if (localWearInstallerTask != null) {
        break label53;
      }
    }
    label53:
    for (PlayStore.AppData localAppData = null;; localAppData = localWearInstallerTask.mLogAppData)
    {
      FinskyApp.get().getEventLogger().logBackgroundEvent(104, paramDownload.getDocIdForLog(), null, paramInt, null, localAppData);
      if (localWearInstallerTask != null) {
        localWearInstallerTask.cancel$1385ff();
      }
      return;
    }
  }
  
  public final void onNotificationClicked(Download paramDownload) {}
  
  public final void onProgress(Download paramDownload, DownloadProgress paramDownloadProgress)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramDownload);
    if (localWearInstallerTask != null)
    {
      localWearInstallerTask.mApkCompleted = paramDownloadProgress.bytesCompleted;
      if ((paramDownloadProgress.bytesCompleted > 0L) && (localWearInstallerTask.mAppStates.mStateStore.get(localWearInstallerTask.packageName).firstDownloadMs == 0L)) {
        localWearInstallerTask.mInstallerDataStore.setFirstDownloadMs(localWearInstallerTask.packageName, System.currentTimeMillis());
      }
      localWearInstallerTask.mDownloadStatus = paramDownloadProgress.statusCode;
    }
  }
  
  public final void onStart(Download paramDownload)
  {
    WearInstallerTask localWearInstallerTask = getInstallerTask(paramDownload);
    if (paramDownload.getNodeId() != null) {
      if (localWearInstallerTask != null) {
        break label102;
      }
    }
    String str;
    InstallerDataStore.InstallerData localInstallerData;
    int i;
    label102:
    for (PlayStore.AppData localAppData = null;; localAppData = localWearInstallerTask.mLogAppData)
    {
      FinskyApp.get().getEventLogger().logBackgroundEvent(101, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      if (localWearInstallerTask != null)
      {
        str = paramDownload.getPackageName();
        localInstallerData = localWearInstallerTask.mAppStates.getApp(str).installerData;
        i = localInstallerData.installerState;
        if ((i != 40) && (i != 45)) {
          break;
        }
        localWearInstallerTask.setInstallerState(45, paramDownload.getContentUri());
      }
      return;
    }
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = str;
    arrayOfObject[1] = localWearInstallerTask.nodeId;
    arrayOfObject[2] = Integer.valueOf(localInstallerData.installerState);
    arrayOfObject[3] = Integer.valueOf(i);
    FinskyLog.e("Unexpected download start state for %s (%s): %d", arrayOfObject);
    localWearInstallerTask.cancel$1385ff();
    localWearInstallerTask.logAndNotifyDownloadError(str, 903, null);
  }
  
  public final void requestInstall(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    if (getState(paramString1, paramString2) != 0)
    {
      FinskyLog.d("Dropping install request on %s for %s because already installing", new Object[] { paramString1, paramString2 });
      return;
    }
    AppStates localAppStates = this.mWearAppStatesFactory.createAppStates(paramString1);
    AppStates.AppState localAppState = localAppStates.getApp(paramString2);
    PackageStateRepository.PackageState localPackageState;
    if (localAppState != null)
    {
      localPackageState = localAppState.packageManagerState;
      if (localPackageState == null) {
        break label213;
      }
    }
    PlayStore.AppData localAppData;
    label213:
    for (int i = localPackageState.installedVersion;; i = -1)
    {
      localAppData = new PlayStore.AppData();
      localAppData.version = paramInt;
      localAppData.hasVersion = true;
      localAppData.forWear = true;
      localAppData.hasForWear = true;
      if (i >= 0)
      {
        localAppData.oldVersion = i;
        localAppData.hasOldVersion = true;
      }
      if (localPackageState != null)
      {
        localAppData.systemApp = localAppState.packageManagerState.isSystemApp;
        localAppData.hasSystemApp = true;
      }
      if (paramInt > i) {
        break label219;
      }
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = paramString2;
      arrayOfObject2[1] = Integer.valueOf(paramInt);
      arrayOfObject2[2] = Integer.valueOf(i);
      FinskyLog.e("Skipping attempt to download %s version %d over version %d", arrayOfObject2);
      FinskyApp.get().getEventLogger().logBackgroundEvent(112, paramString2, "older-version", 0, null, localAppData);
      return;
      localPackageState = null;
      break;
    }
    label219:
    if (TextUtils.isEmpty(paramString3))
    {
      String str = AppActionAnalyzer.getAppDetailsAccount(paramString2, FinskyApp.get().getCurrentAccountName(), localAppStates, this.mLibraries);
      if (TextUtils.isEmpty(str))
      {
        FinskyLog.d("Cannot update on %s of %s because cannot determine update account.", new Object[] { paramString1, paramString2 });
        return;
      }
      paramString3 = str;
    }
    Object[] arrayOfObject1 = new Object[4];
    arrayOfObject1[0] = paramString1;
    arrayOfObject1[1] = paramString2;
    arrayOfObject1[2] = Integer.valueOf(paramInt);
    arrayOfObject1[3] = paramString4;
    FinskyLog.d("Request install on %s of %s v=%d for %s", arrayOfObject1);
    FinskyApp.get().getEventLogger().logBackgroundEvent(105, paramString2, paramString4, 0, null, localAppData);
    InstallerDataStore.InstallerData localInstallerData;
    InstallerDataStore.InstallerData.Builder localBuilder;
    if (localAppState != null)
    {
      localInstallerData = localAppState.installerData;
      localBuilder = InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString2);
      localBuilder.setDesiredVersion(paramInt);
      localBuilder.setLastNotifiedVersion(paramInt);
      localBuilder.setAccountName(paramString3);
      localBuilder.setDeliveryData(null, 0L);
      localBuilder.setInstallerState(0);
      localBuilder.setDownloadUri(null);
      localBuilder.setRequestId(null);
      if (localInstallerData == null) {
        break label464;
      }
    }
    label464:
    for (int j = localInstallerData.flags;; j = 0)
    {
      localBuilder.setFlags(0x8000 | 0xFFFFBFFF & 0xFFFFCFFF & 0xFFFFF9FF & j & 0xFFFFFFF3);
      localAppStates.mStateStore.put(localBuilder.mInstance);
      return;
      localInstallerData = null;
      break;
    }
  }
  
  final void taskFinished(WearInstallerTask paramWearInstallerTask)
  {
    if ((this.mInstallerTask != null) && (paramWearInstallerTask != this.mInstallerTask))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramWearInstallerTask.packageName;
      arrayOfObject[1] = paramWearInstallerTask.nodeId;
      FinskyLog.wtf("Unexpected (late?) finish of task for %s (%s)", arrayOfObject);
    }
    this.mInstallerTask = null;
    kick();
  }
  
  private static final class ActiveRequest
  {
    final String assetId;
    Uri assetUri;
    final String[] destinationNodes;
    public AppStates.AppState mAppState;
    String mMessage;
    int mStatus;
    final String nodeId;
    final String packageName;
    final String requestId;
    Uri responseUri;
    final Uri uri;
    
    ActiveRequest(DataItem paramDataItem)
    {
      this.uri = paramDataItem.getUri();
      List localList = this.uri.getPathSegments();
      if ((localList != null) && (localList.size() >= 3)) {
        this.packageName = ((String)localList.get(1));
      }
      for (this.requestId = ((String)localList.get(2));; this.requestId = null)
      {
        DataMap localDataMap = DataMap.fromByteArray(paramDataItem.getData());
        this.assetId = localDataMap.getString("assetIdentifier");
        this.destinationNodes = localDataMap.getStringArray("nodeIds");
        if ((this.destinationNodes == null) || (this.destinationNodes.length != 1)) {
          break;
        }
        this.nodeId = this.destinationNodes[0];
        return;
        this.packageName = null;
      }
      this.nodeId = null;
    }
    
    public final void discard(List<Uri> paramList)
    {
      if (this.assetUri != null) {
        paramList.add(this.assetUri);
      }
      if (this.responseUri != null) {
        paramList.add(this.responseUri);
      }
      paramList.add(this.uri);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearInstaller
 * JD-Core Version:    0.7.0.1
 */