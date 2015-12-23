package com.google.android.finsky.receivers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData.Builder;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManagerConstants;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.installer.IMultiUserCoordinatorService;
import com.google.android.finsky.installer.IMultiUserCoordinatorService.Stub;
import com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener;
import com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener.Stub;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.installer.MultiUserCoordinatorService;
import com.google.android.finsky.installer.PackageInstallerFacade;
import com.google.android.finsky.installer.PackageInstallerFactory;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.NotificationManager;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.Users;
import com.google.android.finsky.utils.Users.UserManagerFacade;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class InstallerImpl
  implements DownloadQueueListener, Installer, PackageMonitorReceiver.PackageStatusListener
{
  private static Installer.InstallerProgressReport PROGRESS_DOWNLOAD_PENDING = new Installer.InstallerProgressReport(1, 0L, 0L, 0);
  private static Installer.InstallerProgressReport PROGRESS_NOT_TRACKED = new Installer.InstallerProgressReport(0, 0L, 0L, 0);
  private static Installer.InstallerProgressReport PROGRESS_UNINSTALLING = new Installer.InstallerProgressReport(4, 0L, 0L, 0);
  private final AppStates mAppStates;
  private final Context mContext;
  private IMultiUserCoordinatorService mCoordinatorService;
  private final DownloadQueue mDownloadQueue;
  private final Handler mHandler;
  private final InstallPolicies mInstallPolicies;
  private final InstallerDataStore mInstallerDataStore;
  private InstallerTask mInstallerTask;
  private final Libraries mLibraries;
  IMultiUserCoordinatorServiceListener mListener = new IMultiUserCoordinatorServiceListener.Stub()
  {
    public final void packageAcquired(String paramAnonymousString) {}
    
    public final void packageReleased(final String paramAnonymousString)
    {
      InstallerImpl.this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          InstallerImpl.this.mAppStates.mPackageManager.invalidate(paramAnonymousString);
          InstallerImpl.this.kick(false);
        }
      });
    }
  };
  private final List<InstallerListener> mListeners;
  private final Notifier mNotifier;
  private final PackageInstallerFacade mPackageInstaller;
  private final PackageMonitorReceiver mPackageMonitorReceiver;
  private List<String> mPriorityPackages;
  private boolean mRunning;
  private RemoteServiceConnection mServiceConnection = null;
  private ArrayList<Runnable> mServiceConnectionCallbacks = new ArrayList();
  private Set<String> mUninstallingPackages;
  private final Users mUsers;
  
  public InstallerImpl(Context paramContext, AppStates paramAppStates, Libraries paramLibraries, DownloadQueue paramDownloadQueue, Notifier paramNotifier, InstallPolicies paramInstallPolicies, PackageMonitorReceiver paramPackageMonitorReceiver, Users paramUsers, PackageInstallerFacade paramPackageInstallerFacade)
  {
    this.mContext = paramContext;
    this.mAppStates = paramAppStates;
    this.mLibraries = paramLibraries;
    this.mDownloadQueue = paramDownloadQueue;
    this.mNotifier = paramNotifier;
    this.mInstallPolicies = paramInstallPolicies;
    this.mPackageMonitorReceiver = paramPackageMonitorReceiver;
    this.mUsers = paramUsers;
    this.mPackageInstaller = paramPackageInstallerFacade;
    this.mListeners = new ArrayList();
    this.mInstallerDataStore = paramAppStates.mStateStore;
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mRunning = false;
    this.mUninstallingPackages = new HashSet();
    this.mPriorityPackages = new ArrayList();
  }
  
  private void bindToMultiUserCoordinator(Runnable paramRunnable)
  {
    
    if (this.mCoordinatorService != null) {
      paramRunnable.run();
    }
    Intent localIntent;
    do
    {
      do
      {
        return;
        this.mServiceConnectionCallbacks.add(paramRunnable);
      } while (this.mServiceConnection != null);
      this.mServiceConnection = new RemoteServiceConnection();
      localIntent = MultiUserCoordinatorService.createBindIntent(this.mContext);
    } while (this.mContext.bindService(localIntent, this.mServiceConnection, 5));
    FinskyLog.w("Couldn't start service for %s", new Object[] { localIntent });
  }
  
  public static int calculateAndroidInstallLocation(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 1;
    case 2: 
      return 0;
    }
    return 2;
  }
  
  private void cancelPendingInstall(AppStates.AppState paramAppState)
  {
    if (paramAppState != null)
    {
      String str = paramAppState.packageName;
      FinskyLog.d("Cancel pending install of %s", new Object[] { str });
      this.mPackageInstaller.cancelSession(str);
      if (paramAppState.installerData != null)
      {
        clearInstallerState(paramAppState);
        notifyListeners(str, 2, 0);
      }
    }
  }
  
  private static void captureDownloadProgress(Download paramDownload, PlayStore.AppData paramAppData)
  {
    if (paramAppData != null)
    {
      DownloadProgress localDownloadProgress = paramDownload.getProgress();
      if (localDownloadProgress != null)
      {
        paramAppData.hasDownloadedBytes = true;
        paramAppData.downloadedBytes = localDownloadProgress.bytesCompleted;
      }
    }
  }
  
  private void failPendingForegroundInstalls(Collection<AppStates.AppState> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      AppStates.AppState localAppState = (AppStates.AppState)localIterator.next();
      clearInstallerState(localAppState);
      FinskyApp.get().getEventLogger().logBackgroundEvent(111, localAppState.packageName, "foreground", 978, null, null);
      notifyListeners(localAppState.packageName, 5, 978);
    }
  }
  
  private InstallerTask getInstallerTask(Download paramDownload)
  {
    InstallerTask localInstallerTask;
    if (paramDownload.getNodeId() != null) {
      localInstallerTask = null;
    }
    AppStates.AppState localAppState;
    do
    {
      return localInstallerTask;
      String str = paramDownload.getPackageName();
      if (str == null) {
        return null;
      }
      localInstallerTask = getInstallerTask(str);
      if (localInstallerTask == null)
      {
        this.mDownloadQueue.cancel(paramDownload);
        return null;
      }
      localAppState = this.mAppStates.getApp(str);
    } while ((localAppState != null) && (localAppState.installerData != null));
    this.mDownloadQueue.cancel(paramDownload);
    return null;
  }
  
  private InstallerTask getInstallerTask(String paramString)
  {
    if ((this.mInstallerTask != null) && (this.mInstallerTask.packageName.equals(paramString))) {
      return this.mInstallerTask;
    }
    return null;
  }
  
  private void kick(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          InstallerImpl.this.kick(false);
        }
      });
    }
    for (;;)
    {
      return;
      if (!this.mRunning)
      {
        FinskyLog.d("Installer kick - no action, not running yet", new Object[0]);
        return;
      }
      if (this.mInstallerTask == null) {
        if (multiUserMode())
        {
          List localList3 = this.mAppStates.getAppsToInstall();
          if (localList3.isEmpty())
          {
            Utils.ensureOnMainThread();
            if (this.mServiceConnection == null) {
              continue;
            }
            try
            {
              if (this.mCoordinatorService != null)
              {
                this.mCoordinatorService.registerListener(null);
                this.mCoordinatorService.releaseAllPackages();
              }
              this.mCoordinatorService = null;
              this.mContext.unbindService(this.mServiceConnection);
              this.mServiceConnection = null;
              return;
            }
            catch (RemoteException localRemoteException)
            {
              for (;;)
              {
                FinskyLog.w("Couldn't sign out from coordinator *** received %s", new Object[] { localRemoteException });
              }
            }
          }
          if (this.mCoordinatorService == null)
          {
            bindToMultiUserCoordinator(new Runnable()
            {
              public final void run()
              {
                InstallerImpl.this.kick(false);
              }
            });
            return;
          }
          AppStates.AppState localAppState3 = selectNextTaskMultiUser(localList3, this.mPriorityPackages, this.mCoordinatorService);
          if (localAppState3 != null)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = localAppState3.packageName;
            FinskyLog.d("Installer kick - starting %s", arrayOfObject2);
            this.mInstallerTask = new InstallerTask(localAppState3.packageName, this, this.mAppStates, this.mDownloadQueue, this.mNotifier, this.mInstallPolicies, this.mPackageInstaller);
            this.mInstallerTask.start();
          }
        }
        else
        {
          List localList1 = this.mAppStates.getAppsToInstall();
          List localList2 = this.mPriorityPackages;
          boolean bool = localList1.isEmpty();
          Object localObject = null;
          if (bool) {}
          while (localObject != null)
          {
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = localObject.packageName;
            FinskyLog.d("Installer kick - starting %s", arrayOfObject1);
            this.mInstallerTask = new InstallerTask(localObject.packageName, this, this.mAppStates, this.mDownloadQueue, this.mNotifier, this.mInstallPolicies, this.mPackageInstaller);
            this.mInstallerTask.start();
            return;
            label448:
            for (;;)
            {
              FinskyLog.d("Unexpected: Priority package %s no longer installable", new Object[] { str });
              if (localList2.isEmpty()) {
                break label450;
              }
              String str = (String)localList2.remove(0);
              for (int j = 0;; j++)
              {
                if (j >= localList1.size()) {
                  break label448;
                }
                AppStates.AppState localAppState2 = (AppStates.AppState)localList1.get(j);
                if (localAppState2.packageName.equals(str))
                {
                  localObject = localAppState2;
                  break;
                }
              }
            }
            label450:
            Set localSet = InstallPolicies.getForegroundPackages(this.mContext);
            Iterator localIterator = localList1.iterator();
            label538:
            for (;;)
            {
              if (!localIterator.hasNext()) {
                break label540;
              }
              AppStates.AppState localAppState1 = (AppStates.AppState)localIterator.next();
              if ((0x20000 & localAppState1.installerData.flags) != 0) {}
              for (int i = 1;; i = 0)
              {
                if ((i == 0) && (localSet.contains(localAppState1.packageName))) {
                  break label538;
                }
                localObject = localAppState1;
                break;
              }
            }
            label540:
            failPendingForegroundInstalls(localList1);
            localObject = null;
          }
        }
      }
    }
  }
  
  private boolean multiUserMode()
  {
    return this.mUsers.mUserManagerFacade.hasMultipleUsers();
  }
  
  private boolean recoverDownload(Uri paramUri, int paramInt)
  {
    String str1;
    Object localObject;
    label119:
    int j;
    if (paramUri != null)
    {
      try
      {
        str1 = paramUri.toString();
        if (TextUtils.isEmpty(str1)) {
          return false;
        }
        if (this.mInstallerTask != null)
        {
          Object[] arrayOfObject14 = new Object[1];
          arrayOfObject14[0] = this.mInstallerTask.packageName;
          FinskyLog.w("tried recovery while already handling %s", arrayOfObject14);
          return false;
        }
        Iterator localIterator = this.mAppStates.mStateStore.getAll().iterator();
        InstallerDataStore.InstallerData localInstallerData1;
        do
        {
          boolean bool1 = localIterator.hasNext();
          localObject = null;
          if (!bool1) {
            break;
          }
          localInstallerData1 = (InstallerDataStore.InstallerData)localIterator.next();
        } while (!str1.equals(localInstallerData1.downloadUri));
        localObject = localInstallerData1;
      }
      catch (Exception localException)
      {
        String str2;
        boolean bool2;
        InstallerTask localInstallerTask;
        AppStates.AppState localAppState;
        InstallerDataStore.InstallerData localInstallerData2;
        Object[] arrayOfObject1;
        boolean bool3;
        label298:
        int i;
        label412:
        int k;
        FinskyLog.w("Caught exception while recovering %s: %s", new Object[] { paramUri, localException });
        return false;
      }
      str2 = localObject.packageName;
      FinskyLog.d("Recovering download for running %s", new Object[] { str2 });
      bool2 = multiUserMode();
      if (bool2) {
        try
        {
          if (!this.mCoordinatorService.acquirePackage(str2))
          {
            FinskyLog.w("Can't recover %s *** cannot acquire", new Object[] { str2 });
            return false;
          }
        }
        catch (RemoteException localRemoteException2)
        {
          FinskyLog.w("Acquiring %s *** received %s", new Object[] { str2, localRemoteException2 });
        }
      }
      localInstallerTask = new InstallerTask(str2, this, this.mAppStates, this.mDownloadQueue, this.mNotifier, this.mInstallPolicies, this.mPackageInstaller);
      localAppState = localInstallerTask.mAppStates.getApp(localInstallerTask.packageName);
      localInstallerData2 = localAppState.installerData;
      if ((localInstallerData2 == null) || (localInstallerData2.deliveryData == null))
      {
        arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localInstallerTask.packageName;
        FinskyLog.d("Recovery of %s skipped because incomplete installerdata", arrayOfObject1);
        bool3 = false;
      }
      for (;;)
      {
        if (!bool3) {
          break label1190;
        }
        this.mInstallerTask = localInstallerTask;
        return true;
        localInstallerTask.populateFields(localAppState);
        localInstallerTask.processDeliveryData(localInstallerData2, false);
        localInstallerTask.populateSplitInfo(localInstallerData2);
        i = localInstallerData2.installerState;
        if ((i != 25) && (i != 35) && (TextUtils.isEmpty(localInstallerTask.mActiveSplitId)))
        {
          Object[] arrayOfObject13 = new Object[1];
          arrayOfObject13[0] = localInstallerTask.packageName;
          FinskyLog.d("Recovery of %s skipped because missing mActiveSplitId", arrayOfObject13);
          bool3 = false;
        }
        else
        {
          if (localAppState.packageManagerState == null) {
            break label1274;
          }
          j = localAppState.packageManagerState.installedVersion;
          k = localInstallerData2.desiredVersion;
          localInstallerTask.mRecoveredIntoState = i;
          switch (i)
          {
          default: 
            Object[] arrayOfObject12 = new Object[3];
            arrayOfObject12[0] = localInstallerTask.packageName;
            arrayOfObject12[1] = localInstallerTask.mActiveSplitId;
            arrayOfObject12[2] = Integer.valueOf(i);
            FinskyLog.d("Recovery of %s (%s) skipped because state= %d", arrayOfObject12);
            bool3 = false;
            break;
          case 25: 
            bool3 = localInstallerTask.recoverObb(localAppState, paramUri, paramInt, k, j, false);
            break;
          case 35: 
            bool3 = localInstallerTask.recoverObb(localAppState, paramUri, paramInt, k, j, true);
            break;
          case 45: 
          case 50: 
            if (k <= j)
            {
              Object[] arrayOfObject8 = new Object[4];
              arrayOfObject8[0] = localInstallerTask.packageName;
              arrayOfObject8[1] = localInstallerTask.mActiveSplitId;
              arrayOfObject8[2] = Integer.valueOf(k);
              arrayOfObject8[3] = Integer.valueOf(j);
              FinskyLog.d("Recovery of %s (%s) skipped because desired= %d installed= %d", arrayOfObject8);
              bool3 = false;
            }
            else
            {
              if ((DownloadManagerConstants.isStatusCompleted(paramInt)) || (paramInt == 198)) {
                break label754;
              }
              Object[] arrayOfObject9 = new Object[2];
              arrayOfObject9[0] = localInstallerTask.packageName;
              arrayOfObject9[1] = localInstallerTask.mActiveSplitId;
              FinskyLog.d("Recovery of %s (%s) into downloading APK state", arrayOfObject9);
              Download localDownload = localInstallerTask.generateDownload(localAppState.installerData, localInstallerTask.mActiveSplitId);
              if (localDownload == null) {
                break label1280;
              }
              localDownload.setContentUri(paramUri);
              localInstallerTask.mDownloadQueue.addRecoveredDownload(localDownload);
              bool3 = true;
            }
            break;
          }
        }
      }
    }
    for (;;)
    {
      label754:
      if (DownloadManagerConstants.isStatusSuccess(paramInt))
      {
        Object[] arrayOfObject11 = new Object[2];
        arrayOfObject11[0] = localInstallerTask.packageName;
        arrayOfObject11[1] = localInstallerTask.mActiveSplitId;
        FinskyLog.d("Recovery of %s (%s) into postprocess state", arrayOfObject11);
        localInstallerTask.setInstallerState(50, paramUri.toString());
        localInstallerTask.advanceState();
        bool3 = true;
        break label298;
      }
      Object[] arrayOfObject10 = new Object[3];
      arrayOfObject10[0] = localInstallerTask.packageName;
      arrayOfObject10[1] = localInstallerTask.mActiveSplitId;
      arrayOfObject10[2] = Integer.valueOf(paramInt);
      FinskyLog.d("Recovery of %s (%s) into error state, status= %d", arrayOfObject10);
      localInstallerTask.cancel(false);
      localInstallerTask.logAndNotifyDownloadError(localInstallerTask.packageName, paramInt, null);
      bool3 = false;
      break label298;
      Object[] arrayOfObject7 = new Object[3];
      arrayOfObject7[0] = localInstallerTask.packageName;
      arrayOfObject7[1] = localInstallerTask.mActiveSplitId;
      arrayOfObject7[2] = Integer.valueOf(i);
      FinskyLog.d("Recovery of %s (%s) skipped because state= %d", arrayOfObject7);
      localInstallerTask.cancelCleanup(localAppState);
      bool3 = false;
      break label298;
      Object[] arrayOfObject6 = new Object[3];
      arrayOfObject6[0] = localInstallerTask.packageName;
      arrayOfObject6[1] = localInstallerTask.mActiveSplitId;
      arrayOfObject6[2] = Integer.valueOf(i);
      FinskyLog.d("Recovery of %s (%s) skipped because state= %d", arrayOfObject6);
      localInstallerTask.cancelCleanup(localAppState);
      bool3 = false;
      break label298;
      Object[] arrayOfObject5 = new Object[3];
      arrayOfObject5[0] = localInstallerTask.packageName;
      arrayOfObject5[1] = localInstallerTask.mActiveSplitId;
      arrayOfObject5[2] = Integer.valueOf(i);
      FinskyLog.d("Recovery of %s (%s) skipped because state= %d", arrayOfObject5);
      localInstallerTask.cancelCleanup(localAppState);
      bool3 = false;
      break label298;
      if (k < j)
      {
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = localInstallerTask.packageName;
        arrayOfObject2[1] = Integer.valueOf(k);
        arrayOfObject2[2] = Integer.valueOf(j);
        FinskyLog.d("Recovery of %s skipped because desired= %d installed= %d", arrayOfObject2);
        bool3 = false;
        break label298;
      }
      if (k == j)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = localInstallerTask.packageName;
        FinskyLog.d("Recovery of %s - installation seems complete", arrayOfObject3);
        localInstallerTask.setInstallerState(70, paramUri.toString());
        localInstallerTask.advanceState();
        bool3 = false;
        break label298;
      }
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = localInstallerTask.packageName;
      FinskyLog.d("Recovery of %s with incomplete installation", arrayOfObject4);
      localInstallerTask.cancel(false);
      localInstallerTask.notifyListeners(8, paramInt);
      bool3 = false;
      break label298;
      label1190:
      boolean bool4 = multiUserMode();
      if (bool4) {}
      try
      {
        this.mCoordinatorService.releasePackage(str2);
        return false;
      }
      catch (RemoteException localRemoteException1)
      {
        for (;;)
        {
          FinskyLog.w("Releasing %s *** received %s", new Object[] { str2, localRemoteException1 });
        }
      }
      str1 = null;
      break;
      if (localObject != null) {
        break label119;
      }
      return false;
      label1274:
      j = -1;
      break label412;
      label1280:
      paramInt = 964;
    }
  }
  
  private AppStates.AppState selectNextTaskMultiUser(List<AppStates.AppState> paramList, List<String> paramList1, IMultiUserCoordinatorService paramIMultiUserCoordinatorService)
  {
    int i = -1 + paramList1.size();
    if (i >= 0)
    {
      String str = (String)paramList1.get(i);
      for (int m = 0;; m++)
      {
        int n = paramList.size();
        int i1 = 0;
        if (m < n)
        {
          if (((AppStates.AppState)paramList.get(m)).packageName.equals(str))
          {
            paramList.add(0, (AppStates.AppState)paramList.remove(m));
            i1 = 1;
          }
        }
        else
        {
          if (i1 == 0) {
            paramList1.remove(i);
          }
          i--;
          break;
        }
      }
    }
    Set localSet = InstallPolicies.getForegroundPackages(this.mContext);
    localSet.removeAll(paramList1);
    HashSet localHashSet = new HashSet();
    int j = 0;
    AppStates.AppState localAppState;
    if (j < paramList.size()) {
      localAppState = (AppStates.AppState)paramList.get(j);
    }
    for (;;)
    {
      try
      {
        if ((0x20000 & localAppState.installerData.flags) == 0) {
          break label326;
        }
        k = 1;
        if ((k == 0) && (localSet.contains(localAppState.packageName)))
        {
          localHashSet.add(localAppState);
        }
        else
        {
          if (paramIMultiUserCoordinatorService.acquirePackage(localAppState.packageName))
          {
            paramList1.remove(localAppState.packageName);
            return localAppState;
          }
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localAppState.packageName;
          FinskyLog.d("Skipping install of %s - not acquired", arrayOfObject2);
        }
      }
      catch (RemoteException localRemoteException)
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = localAppState.packageName;
        arrayOfObject1[1] = localRemoteException;
        FinskyLog.w("Couldn't acquire %s (proceed anyway) received %s", arrayOfObject1);
        return localAppState;
      }
      failPendingForegroundInstalls(localHashSet);
      return null;
      j++;
      break;
      label326:
      int k = 0;
    }
  }
  
  public final void addListener(InstallerListener paramInstallerListener)
  {
    Utils.ensureOnMainThread();
    this.mListeners.add(paramInstallerListener);
  }
  
  public final void cancel(String paramString)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramString);
    if (localInstallerTask != null) {
      localInstallerTask.cancel(true);
    }
    for (;;)
    {
      kick(true);
      return;
      cancelPendingInstall(this.mAppStates.getApp(paramString));
    }
  }
  
  public final void cancelAll()
  {
    InstallerTask localInstallerTask = this.mInstallerTask;
    String str = null;
    if (localInstallerTask != null)
    {
      str = this.mInstallerTask.packageName;
      this.mInstallerTask.cancel(true);
    }
    Iterator localIterator = this.mAppStates.getAppsToInstall().iterator();
    while (localIterator.hasNext())
    {
      AppStates.AppState localAppState = (AppStates.AppState)localIterator.next();
      if ((str == null) || (!str.equals(localAppState.packageName))) {
        cancelPendingInstall(localAppState);
      }
    }
    kick(true);
  }
  
  final void clearInstallerState(AppStates.AppState paramAppState)
  {
    if ((paramAppState == null) || (paramAppState.installerData == null)) {
      return;
    }
    InstallerDataStore.InstallerData.Builder localBuilder = InstallerDataStore.InstallerData.Builder.buildUpon(paramAppState.installerData, paramAppState.packageName);
    localBuilder.setDesiredVersion(-1);
    localBuilder.setInstallerState(0);
    localBuilder.setDownloadUri(null);
    localBuilder.setFlags(0);
    localBuilder.setDeliveryToken(null);
    localBuilder.setCompletedSplitIds(null);
    localBuilder.setActiveSplitId(null);
    this.mInstallerDataStore.put(localBuilder.mInstance);
  }
  
  public final int extractInstallLocation(Document paramDocument)
  {
    if (paramDocument != null)
    {
      AppDetails localAppDetails = paramDocument.getAppDetails();
      if ((localAppDetails != null) && (localAppDetails.hasInstallLocation)) {
        return localAppDetails.installLocation;
      }
    }
    return 0;
  }
  
  public final Installer.InstallerProgressReport getProgress(String paramString)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramString);
    if (localInstallerTask != null)
    {
      switch (localInstallerTask.getState())
      {
      case 1: 
      case 2: 
      default: 
        return new Installer.InstallerProgressReport(2, localInstallerTask.mApkCompleted + localInstallerTask.mObbMainCompleted + localInstallerTask.mObbPatchCompleted, localInstallerTask.mTotalSize, localInstallerTask.mDownloadStatus);
      case 0: 
        return InstallerTask.PROGRESS_NOT_TRACKED;
      }
      return InstallerTask.PROGRESS_INSTALLING;
    }
    if (this.mUninstallingPackages.contains(paramString)) {
      return PROGRESS_UNINSTALLING;
    }
    AppStates.AppState localAppState = this.mAppStates.getApp(paramString);
    if (localAppState != null)
    {
      int i = -1;
      if (localAppState.packageManagerState != null) {
        i = localAppState.packageManagerState.installedVersion;
      }
      if ((localAppState.installerData != null) && (localAppState.installerData.desiredVersion > i)) {
        return PROGRESS_DOWNLOAD_PENDING;
      }
    }
    return PROGRESS_NOT_TRACKED;
  }
  
  public final int getState(String paramString)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramString);
    if (localInstallerTask != null) {
      return localInstallerTask.getState();
    }
    if (this.mUninstallingPackages.contains(paramString)) {
      return 4;
    }
    AppStates.AppState localAppState = this.mAppStates.getApp(paramString);
    if (localAppState != null)
    {
      int i = -1;
      if (localAppState.packageManagerState != null) {
        i = localAppState.packageManagerState.installedVersion;
      }
      if ((localAppState.installerData != null) && (localAppState.installerData.desiredVersion > i)) {
        return 1;
      }
    }
    return 0;
  }
  
  final void notifyListeners(final String paramString, final int paramInt1, final int paramInt2)
  {
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        Iterator localIterator = Lists.newArrayList(InstallerImpl.this.mListeners).iterator();
        while (localIterator.hasNext())
        {
          InstallerListener localInstallerListener = (InstallerListener)localIterator.next();
          try
          {
            localInstallerListener.onInstallPackageEvent(paramString, paramInt1, paramInt2);
          }
          catch (Exception localException)
          {
            FinskyLog.wtf(localException, "Exception caught in InstallerListener", new Object[0]);
          }
        }
      }
    });
  }
  
  public final void onCancel(Download paramDownload)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramDownload);
    if (paramDownload.getNodeId() == null) {
      if (localInstallerTask != null) {
        break label57;
      }
    }
    label57:
    for (PlayStore.AppData localAppData = null;; localAppData = localInstallerTask.mLogAppData)
    {
      captureDownloadProgress(paramDownload, localAppData);
      FinskyApp.get().getEventLogger().logBackgroundEvent(103, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      if (localInstallerTask != null) {
        localInstallerTask.cancel(true);
      }
      return;
    }
  }
  
  public final void onComplete(Download paramDownload)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramDownload);
    PlayStore.AppData localAppData;
    InstallerDataStore.InstallerData localInstallerData;
    int i;
    boolean bool1;
    boolean bool2;
    if (paramDownload.getNodeId() == null)
    {
      if (localInstallerTask == null)
      {
        localAppData = null;
        captureDownloadProgress(paramDownload, localAppData);
        FinskyApp.get().getEventLogger().logBackgroundEvent(102, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      }
    }
    else if (localInstallerTask != null)
    {
      localInstallerData = localInstallerTask.mAppStates.getApp(localInstallerTask.packageName).installerData;
      i = -1;
      bool1 = paramDownload.isObb();
      if ((!bool1) || (paramDownload.getObb().isPatch())) {
        break label171;
      }
      bool2 = true;
      label102:
      switch (localInstallerData.installerState)
      {
      }
    }
    for (;;)
    {
      if (i < 0) {
        break label223;
      }
      localInstallerTask.setInstallerState(i, paramDownload.getContentUri());
      localInstallerTask.advanceState();
      return;
      localAppData = localInstallerTask.mLogAppData;
      break;
      label171:
      bool2 = false;
      break label102;
      if ((bool1) && (bool2))
      {
        i = 30;
        continue;
        if ((bool1) && (!bool2))
        {
          i = 40;
          continue;
          if (!bool1) {
            i = 50;
          }
        }
      }
    }
    label223:
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = localInstallerTask.packageName;
    arrayOfObject[1] = localInstallerTask.mActiveSplitId;
    arrayOfObject[2] = Integer.valueOf(localInstallerData.installerState);
    arrayOfObject[3] = Integer.valueOf(i);
    arrayOfObject[4] = Boolean.valueOf(bool1);
    arrayOfObject[5] = Boolean.valueOf(bool2);
    FinskyLog.e("Unexpected download completion states for %s (%s): %d %d %b %b", arrayOfObject);
    localInstallerTask.cancel(false);
    localInstallerTask.logAndNotifyDownloadError(localInstallerTask.packageName, 904, null);
  }
  
  public final void onError(Download paramDownload, int paramInt)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramDownload);
    PlayStore.AppData localAppData;
    int i;
    if (paramDownload.getNodeId() == null)
    {
      if (localInstallerTask == null)
      {
        localAppData = null;
        captureDownloadProgress(paramDownload, localAppData);
        FinskyApp.get().getEventLogger().logBackgroundEvent(104, paramDownload.getDocIdForLog(), null, paramInt, null, localAppData);
      }
    }
    else if (localInstallerTask != null)
    {
      if ((paramInt != 420) && ((paramInt < 500) || (paramInt > 599))) {
        break label167;
      }
      i = 1;
      label78:
      if ((i == 0) || ((!localInstallerTask.tryRestartWithInhibitFlag(4, 8)) && (!localInstallerTask.tryRestartWithInhibitFlag(512, 1024))))
      {
        localInstallerTask.cancel(false);
        if (paramInt != 198) {
          break label193;
        }
        if (localInstallerTask.mShowErrorNotifications)
        {
          if (!paramDownload.isObb()) {
            break label173;
          }
          localInstallerTask.mNotifier.showExternalStorageFull(localInstallerTask.mTitle, localInstallerTask.packageName);
        }
      }
    }
    for (;;)
    {
      localInstallerTask.notifyListeners(3, paramInt);
      return;
      localAppData = localInstallerTask.mLogAppData;
      break;
      label167:
      i = 0;
      break label78;
      label173:
      localInstallerTask.mNotifier.showInternalStorageFull(localInstallerTask.mTitle, localInstallerTask.packageName);
      continue;
      label193:
      localInstallerTask.showDownloadNotification(paramInt, null);
    }
  }
  
  public final void onNotificationClicked(Download paramDownload)
  {
    String str = paramDownload.getPackageName();
    if (str == null)
    {
      FinskyLog.d("Discarding notification click, no packageName for %s", new Object[] { paramDownload });
      return;
    }
    Intent localIntent = NotificationManager.createDefaultClickIntent(this.mContext, str, null, null, DfeUtils.createDetailsUrlFromId(str));
    localIntent.setFlags(268435456);
    this.mContext.startActivity(localIntent);
  }
  
  public final void onPackageAdded(String paramString)
  {
    this.mNotifier.hidePackageRemovedMessage(paramString);
  }
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString) {}
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mNotifier.hideInstallingMessage();
      this.mUninstallingPackages.remove(paramString);
      AppStates.AppState localAppState = this.mAppStates.getApp(paramString);
      if ((localAppState != null) && (localAppState.installerData != null))
      {
        InstallerDataStore.InstallerData localInstallerData = localAppState.installerData;
        int i = localInstallerData.desiredVersion;
        j = 0;
        if (i != -1)
        {
          if ((!paramBoolean) || (localAppState.packageManagerState == null)) {
            break label160;
          }
          int k = localInstallerData.desiredVersion;
          int m = localAppState.packageManagerState.installedVersion;
          j = 0;
          if (m >= k) {}
        }
      }
    }
    label160:
    for (int j = 1;; j = 1)
    {
      if (j != 0) {
        this.mInstallerDataStore.setDesiredVersion(paramString, -1);
      }
      notifyListeners(paramString, 8, 0);
      return;
      this.mNotifier.hideAllMessagesForPackage(paramString);
      this.mNotifier.hidePackageRemoveRequestMessage(paramString);
      break;
    }
  }
  
  public final void onProgress(Download paramDownload, DownloadProgress paramDownloadProgress)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramDownload);
    if (localInstallerTask != null)
    {
      if (!paramDownload.isObb()) {
        break label102;
      }
      if (!paramDownload.getObb().isPatch()) {
        break label91;
      }
      localInstallerTask.mObbPatchCompleted = paramDownloadProgress.bytesCompleted;
    }
    for (;;)
    {
      localInstallerTask.mDownloadStatus = paramDownloadProgress.statusCode;
      localInstallerTask.notifyListeners(1, 0);
      long l = localInstallerTask.mApkCompleted + localInstallerTask.mObbMainCompleted + localInstallerTask.mObbPatchCompleted;
      localInstallerTask.mPackageInstaller.reportProgress(localInstallerTask.packageName, l, localInstallerTask.mTotalSize);
      return;
      label91:
      localInstallerTask.mObbMainCompleted = paramDownloadProgress.bytesCompleted;
      continue;
      label102:
      localInstallerTask.mApkCompleted = paramDownloadProgress.bytesCompleted;
      if ((paramDownloadProgress.bytesCompleted > 0L) && (localInstallerTask.mAppStates.mStateStore.get(localInstallerTask.packageName).firstDownloadMs == 0L)) {
        localInstallerTask.mInstallerDataStore.setFirstDownloadMs(localInstallerTask.packageName, System.currentTimeMillis());
      }
    }
  }
  
  public final void onStart(Download paramDownload)
  {
    InstallerTask localInstallerTask = getInstallerTask(paramDownload);
    PlayStore.AppData localAppData;
    String str;
    InstallerDataStore.InstallerData localInstallerData;
    int i;
    if (paramDownload.getNodeId() == null)
    {
      if (localInstallerTask == null)
      {
        localAppData = null;
        FinskyApp.get().getEventLogger().logBackgroundEvent(101, paramDownload.getDocIdForLog(), null, 0, null, localAppData);
      }
    }
    else if (localInstallerTask != null)
    {
      str = paramDownload.getPackageName();
      localInstallerData = localInstallerTask.mAppStates.getApp(str).installerData;
      switch (localInstallerData.installerState)
      {
      default: 
        i = -1;
      }
    }
    for (;;)
    {
      if (i < 0) {
        break label189;
      }
      localInstallerTask.setInstallerState(i, paramDownload.getContentUri());
      localInstallerTask.notifyListeners(1, 0);
      return;
      localAppData = localInstallerTask.mLogAppData;
      break;
      i = 25;
      continue;
      i = 35;
      continue;
      i = 45;
    }
    label189:
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = str;
    arrayOfObject[1] = localInstallerTask.mActiveSplitId;
    arrayOfObject[2] = Integer.valueOf(localInstallerData.installerState);
    arrayOfObject[3] = Integer.valueOf(-1);
    FinskyLog.e("Unexpected download start states for %s (%s): %d %d", arrayOfObject);
    localInstallerTask.cancel(false);
    localInstallerTask.logAndNotifyDownloadError(str, 903, null);
  }
  
  public final void promiseInstall$1718defc(String paramString1, String paramString2)
  {
    if (this.mPackageInstaller.hasSession(paramString1))
    {
      FinskyLog.w("Promising install for already-existing session for %s", new Object[] { paramString1 });
      this.mPackageInstaller.cancelSession(paramString1);
    }
    this.mPackageInstaller.createSession(paramString1, 0L, paramString2, null, calculateAndroidInstallLocation(0));
  }
  
  public final void removeListener(InstallerListener paramInstallerListener)
  {
    Utils.ensureOnMainThread();
    this.mListeners.remove(paramInstallerListener);
  }
  
  public final void requestInstall(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, int paramInt2, int paramInt3)
  {
    InstallerTask.checkForEmptyTitle("requestInstall", paramString1, paramString3, null);
    if (getState(paramString1) != 0) {
      FinskyLog.d("Dropping install request for %s because already installing", new Object[] { paramString1 });
    }
    AppStates.AppState localAppState;
    label67:
    PlayStore.AppData localAppData;
    label256:
    for (;;)
    {
      return;
      localAppState = this.mAppStates.getApp(paramString1);
      PackageStateRepository.PackageState localPackageState;
      int i;
      if (localAppState != null)
      {
        localPackageState = localAppState.packageManagerState;
        if (localPackageState == null) {
          break label246;
        }
        i = localPackageState.installedVersion;
        localAppData = new PlayStore.AppData();
        localAppData.version = paramInt1;
        localAppData.hasVersion = true;
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
        if (paramInt1 > i) {
          break;
        }
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = paramString1;
        arrayOfObject2[1] = Integer.valueOf(paramInt1);
        arrayOfObject2[2] = Integer.valueOf(i);
        FinskyLog.e("Skipping attempt to download %s version %d over version %d", arrayOfObject2);
        this.mPackageInstaller.cancelSession(paramString1);
        FinskyApp.get().getEventLogger().logBackgroundEvent(112, paramString1, "older-version", 0, null, localAppData);
        if (localAppState.installerData == null) {
          break label252;
        }
      }
      label246:
      label252:
      for (int m = localAppState.installerData.flags;; m = 0)
      {
        if ((m & 0x1) != 0) {
          break label256;
        }
        this.mNotifier.showInstallationFailureMessage(paramString3, paramString1, -1);
        return;
        localPackageState = null;
        break;
        i = -1;
        break label67;
      }
    }
    Object[] arrayOfObject1 = new Object[4];
    arrayOfObject1[0] = paramString1;
    arrayOfObject1[1] = Integer.valueOf(paramInt1);
    arrayOfObject1[2] = Integer.valueOf(paramInt2);
    arrayOfObject1[3] = paramString4;
    FinskyLog.d("Request install of %s v=%d pri=%d for %s", arrayOfObject1);
    FinskyApp.get().getEventLogger().logBackgroundEvent(105, paramString1, paramString4, 0, null, localAppData);
    if (!this.mPackageInstaller.hasSession(paramString1)) {
      this.mPackageInstaller.createSession(paramString1, 0L, paramString3, null, calculateAndroidInstallLocation(paramInt3));
    }
    InstallerDataStore.InstallerData localInstallerData;
    label362:
    InstallerDataStore.InstallerData.Builder localBuilder;
    int j;
    label440:
    int k;
    if (localAppState != null)
    {
      localInstallerData = localAppState.installerData;
      localBuilder = InstallerDataStore.InstallerData.Builder.buildUpon(localInstallerData, paramString1);
      localBuilder.setDesiredVersion(paramInt1);
      localBuilder.setLastNotifiedVersion(paramInt1);
      localBuilder.setAccountName(paramString2);
      localBuilder.setTitle(paramString3);
      localBuilder.setDeliveryData(null, 0L);
      localBuilder.setInstallerState(0);
      localBuilder.setDownloadUri(null);
      localBuilder.setCompletedSplitIds(null);
      if (localInstallerData == null) {
        break label530;
      }
      j = localInstallerData.flags;
      k = 0xFFFF3FFF & 0xFFFFCFFF & 0xFFFFF9FF & j & 0xFFFFFFF3;
      if (paramInt2 != 1) {
        break label536;
      }
      k = 0x4000 | k;
      this.mPriorityPackages.add(0, paramString1);
    }
    for (;;)
    {
      localBuilder.setFlags(k);
      this.mInstallerDataStore.put(localBuilder.mInstance);
      notifyListeners(paramString1, 0, 0);
      if (paramBoolean) {
        break;
      }
      kick(false);
      return;
      localInstallerData = null;
      break label362;
      label530:
      j = 0;
      break label440;
      label536:
      if (paramInt2 == 2)
      {
        k |= 0x8000;
        this.mPriorityPackages.add(paramString1);
      }
    }
  }
  
  public final void setDeliveryToken(String paramString1, String paramString2)
  {
    this.mAppStates.mStateStore.setDeliveryToken(paramString1, paramString2);
  }
  
  public final void setEarlyUpdate(String paramString)
  {
    setFlag(paramString, 65536);
  }
  
  final void setFlag(String paramString, int paramInt)
  {
    WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = this.mAppStates.mStateStore;
    InstallerDataStore.InstallerData localInstallerData = localWriteThroughInstallerDataStore.get(paramString);
    int i = 0;
    if (localInstallerData != null) {
      i = localInstallerData.flags;
    }
    int j = i | paramInt;
    if (j != i) {
      localWriteThroughInstallerDataStore.setFlags(paramString, j);
    }
  }
  
  public final void setMobileDataAllowed(String paramString)
  {
    setFlag(paramString, 2);
  }
  
  public final void setMobileDataProhibited(String paramString)
  {
    WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = this.mAppStates.mStateStore;
    InstallerDataStore.InstallerData localInstallerData = localWriteThroughInstallerDataStore.get(paramString);
    int i = 0;
    if (localInstallerData != null) {
      i = localInstallerData.flags;
    }
    int j = 0xFFFFFFFD & (i | 0x800);
    if (j != i) {
      localWriteThroughInstallerDataStore.setFlags(paramString, j);
    }
  }
  
  public final void setOverrideForegroundCheck(String paramString)
  {
    setFlag(paramString, 131072);
  }
  
  public final void setVisibility(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = this.mAppStates.mStateStore;
    InstallerDataStore.InstallerData localInstallerData = localWriteThroughInstallerDataStore.get(paramString);
    int i = 0;
    if (localInstallerData != null) {
      i = localInstallerData.flags;
    }
    int j = i & 0xFFFFFF6E;
    if (!paramBoolean1) {
      j |= 0x10;
    }
    if (!paramBoolean2) {
      j |= 0x1;
    }
    if (!paramBoolean3) {
      j |= 0x80;
    }
    if (j != i) {
      localWriteThroughInstallerDataStore.setFlags(paramString, j);
    }
  }
  
  public final void start(final Runnable paramRunnable)
  {
    this.mNotifier.hideInstallingMessage();
    this.mDownloadQueue.addListener(this);
    this.mPackageMonitorReceiver.attach(this);
    new AsyncTask() {}.execute(new Void[0]);
  }
  
  public final void startDeferredInstalls()
  {
    kick(true);
  }
  
  final void taskFinished(InstallerTask paramInstallerTask)
  {
    if ((this.mInstallerTask != null) && (paramInstallerTask != this.mInstallerTask))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramInstallerTask.packageName;
      FinskyLog.wtf("Unexpected (late?) finish of task for %s", arrayOfObject);
    }
    this.mInstallerTask = null;
    if (multiUserMode()) {
      bindToMultiUserCoordinator(new Runnable()
      {
        public final void run()
        {
          try
          {
            InstallerImpl.this.mCoordinatorService.releasePackage(this.val$packageName);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = this.val$packageName;
            arrayOfObject[1] = localRemoteException;
            FinskyLog.w("Couldn't release %s *** received %s", arrayOfObject);
          }
        }
      });
    }
    kick(true);
  }
  
  public final void uninstallAssetSilently(String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString))
    {
      FinskyLog.w("Unexpected empty package name", new Object[0]);
      return;
    }
    InstallerTask localInstallerTask = getInstallerTask(paramString);
    if (localInstallerTask != null) {
      localInstallerTask.cancel(true);
    }
    try
    {
      PackageInfo localPackageInfo2 = this.mContext.getPackageManager().getPackageInfo(paramString, 0);
      localPackageInfo1 = localPackageInfo2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException1)
    {
      for (;;)
      {
        PlayStore.AppData localAppData;
        boolean bool;
        label197:
        PackageInfo localPackageInfo1 = null;
      }
    }
    localAppData = null;
    if (localPackageInfo1 != null)
    {
      localAppData = new PlayStore.AppData();
      localAppData.oldVersion = localPackageInfo1.versionCode;
      localAppData.hasOldVersion = true;
      if ((0x1 & localPackageInfo1.applicationInfo.flags) == 0) {
        break label197;
      }
    }
    for (bool = true;; bool = false)
    {
      localAppData.systemApp = bool;
      localAppData.hasSystemApp = true;
      FinskyApp.get().getEventLogger().logBackgroundEvent(114, paramString, null, 0, null, localAppData);
      if (this.mInstallerDataStore.get(paramString) != null) {
        this.mInstallerDataStore.setDesiredVersion(paramString, -1);
      }
      try
      {
        this.mContext.getPackageManager().getPackageInfo(paramString, 0);
        this.mUninstallingPackages.add(paramString);
        notifyListeners(paramString, 7, 0);
        PackageInstallerFactory.sPackageInstaller.uninstallPackage(paramString, paramBoolean);
        return;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException2)
      {
        FinskyLog.d("Skipping uninstall of %s - already uninstalled.", new Object[] { paramString });
        return;
      }
    }
  }
  
  public final void uninstallPackagesByUid$505cbf4b(String paramString)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    try
    {
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 0);
      for (String str : localPackageManager.getPackagesForUid(localPackageInfo.applicationInfo.uid))
      {
        FinskyLog.d("Removing package '%s' (child of '%s')", new Object[] { str, paramString });
        uninstallAssetSilently(str, true);
      }
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      FinskyLog.d("Skipping uninstall of %s - already uninstalled.", new Object[] { paramString });
    }
  }
  
  public final void updateInstalledApps$189ce961(List<Document> paramList, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Document localDocument = (Document)localIterator.next();
      String str = localDocument.mDocument.docid;
      if (getState(str) != 0)
      {
        FinskyLog.d("Ignoring update request for %s because already installing", new Object[] { str });
      }
      else
      {
        setVisibility(str, paramBoolean3, false, paramBoolean4);
        if (paramBoolean2) {
          setMobileDataProhibited(str);
        }
        updateSingleInstalledApp(str, localDocument.getVersionCode(), localDocument.mDocument.title, paramString, paramBoolean1, 3, extractInstallLocation(localDocument));
      }
    }
  }
  
  public final void updateSingleInstalledApp(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean, int paramInt2, int paramInt3)
  {
    InstallerTask.checkForEmptyTitle("updateSingleInstalledApp", paramString1, paramString2, null);
    AppStates.AppState localAppState = this.mAppStates.getApp(paramString1);
    if ((localAppState == null) || (localAppState.packageManagerState == null))
    {
      FinskyLog.d("Cannot update %s because not installed.", new Object[] { paramString1 });
      this.mPackageInstaller.cancelSession(paramString1);
      return;
    }
    String str = AppActionAnalyzer.getAppDetailsAccount(paramString1, FinskyApp.get().getCurrentAccountName(), this.mAppStates, this.mLibraries);
    if (TextUtils.isEmpty(str))
    {
      FinskyLog.d("Cannot update %s because cannot determine update account.", new Object[] { paramString1 });
      this.mPackageInstaller.cancelSession(paramString1);
      return;
    }
    requestInstall(paramString1, paramInt1, str, paramString2, paramBoolean, paramString3, paramInt2, paramInt3);
  }
  
  final class RemoteServiceConnection
    implements ServiceConnection
  {
    RemoteServiceConnection() {}
    
    public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      Utils.ensureOnMainThread();
      InstallerImpl.access$1102(InstallerImpl.this, IMultiUserCoordinatorService.Stub.asInterface(paramIBinder));
      try
      {
        InstallerImpl.this.mCoordinatorService.registerListener(InstallerImpl.this.mListener);
        for (int i = 0; i < InstallerImpl.this.mServiceConnectionCallbacks.size(); i++) {
          ((Runnable)InstallerImpl.this.mServiceConnectionCallbacks.get(i)).run();
        }
        InstallerImpl.this.mServiceConnectionCallbacks.clear();
      }
      catch (RemoteException localRemoteException)
      {
        FinskyLog.w("Couldn't register listener *** received %s", new Object[] { localRemoteException });
        InstallerImpl.this.mContext.unbindService(InstallerImpl.this.mServiceConnection);
        InstallerImpl.access$1102(InstallerImpl.this, null);
        InstallerImpl.this.mServiceConnectionCallbacks.clear();
        if (!InstallerImpl.this.mRunning)
        {
          FinskyLog.w("Force-starting the installer after connection failure", new Object[0]);
          InstallerImpl.access$702$121be608(InstallerImpl.this);
          InstallerImpl.this.kick(true);
        }
        return;
      }
    }
    
    public final void onServiceDisconnected(ComponentName paramComponentName) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.InstallerImpl
 * JD-Core Version:    0.7.0.1
 */