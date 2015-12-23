package com.google.android.finsky.wear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.AppStatesReplicator;
import com.google.android.finsky.appstate.AppStatesReplicator.Listener;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.SelfUpdateResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.GetSelfUpdateHelper;
import com.google.android.finsky.utils.GetSelfUpdateHelper.Listener;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.wearable.Wearable;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public class WearSupportService
  extends Service
{
  private List<String> mActiveNodes;
  private Handler mBackgroundHandler;
  private boolean mConnected;
  private List<Runnable> mConnectingCallbacks;
  private GoogleApiClient mGoogleApiClient;
  private WearInstaller mInstaller;
  private List<Runnable> mInstallerStartCallbacks = new ArrayList();
  private boolean mInstallerStarted = false;
  private Handler mNotificationHandler;
  private int mServiceStartId;
  private int mStartupRefCount = 0;
  private WearAppStatesFactory mWearAppStatesFactory;
  
  private void continueStartInstaller()
  {
    this.mStartupRefCount = (1 + this.mStartupRefCount);
    WearInstaller localWearInstaller = this.mInstaller;
    Runnable local5 = new Runnable()
    {
      public final void run()
      {
        WearSupportService.access$010(WearSupportService.this);
        int i = WearSupportService.this.mInstallerStartCallbacks.size();
        for (int j = 0; j < i; j++) {
          WearSupportService.this.mNotificationHandler.post((Runnable)WearSupportService.this.mInstallerStartCallbacks.get(j));
        }
        WearSupportService.this.mInstallerStartCallbacks.clear();
        WearSupportService.access$902$379d1cb0(WearSupportService.this);
      }
    };
    localWearInstaller.mDownloadQueue.addListener(localWearInstaller);
    Utils.executeMultiThreaded(new WearInstaller.3(localWearInstaller, local5), new Void[0]);
  }
  
  private void handleNodeChange$505cbf4b(final String paramString)
  {
    final Uri localUri = Uri.parse(paramString);
    final String str = localUri.getHost();
    this.mStartupRefCount = (1 + this.mStartupRefCount);
    setupAppStatesFactory();
    this.mWearAppStatesFactory.load(str, new Runnable()
    {
      public final void run()
      {
        WearSupportService.access$010(WearSupportService.this);
        List localList = localUri.getPathSegments();
        String str1 = (String)localList.get(0);
        int i;
        switch (str1.hashCode())
        {
        default: 
          i = -1;
          switch (i)
          {
          default: 
            label67:
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramString;
            FinskyLog.wtf("Unexpected command in %s", arrayOfObject);
          }
          break;
        }
        for (;;)
        {
          WearSupportService.this.stopSelfIfIdle();
          return;
          if (!str1.equals("package_info")) {
            break;
          }
          i = 0;
          break label67;
          if (!str1.equals("device_configuration")) {
            break;
          }
          i = 1;
          break label67;
          if (!str1.equals("request_status")) {
            break;
          }
          i = 2;
          break label67;
          String str2 = (String)localList.get(1);
          WearPackageStateRepository.get(WearSupportService.this, str, WearSupportService.this.mGoogleApiClient).invalidate(str2);
          continue;
          WearDeviceConfigurationHelper.invalidate();
          continue;
          localList.get(1);
        }
      }
    });
  }
  
  private void setupAppStatesFactory()
  {
    setupWorkerThreads();
    if (this.mWearAppStatesFactory == null) {
      this.mWearAppStatesFactory = new WearAppStatesFactory(this, this.mGoogleApiClient, this.mNotificationHandler, this.mBackgroundHandler);
    }
  }
  
  private void setupWorkerThreads()
  {
    if (this.mNotificationHandler == null) {
      this.mNotificationHandler = new Handler(Looper.getMainLooper());
    }
    if (this.mBackgroundHandler == null)
    {
      HandlerThread localHandlerThread = new HandlerThread("wear-nodes-content-sync", 10);
      localHandlerThread.start();
      this.mBackgroundHandler = new Handler(localHandlerThread.getLooper());
    }
  }
  
  public static void startHygiene(Context paramContext)
  {
    if (!FinskyApp.get().getExperiments().isEnabled(12603948L))
    {
      FinskyLog.d("disabled", new Object[0]);
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, WearSupportService.class);
    localIntent.setData(Uri.parse("wearsupportservice://hygiene"));
    localIntent.putExtra("command", "hygiene");
    localContext.startService(localIntent);
  }
  
  public static void startNodeUpdates(Context paramContext, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    if (!FinskyApp.get().getExperiments().isEnabled(12603948L))
    {
      FinskyLog.d("disabled", new Object[0]);
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, WearSupportService.class);
    localIntent.setData(Uri.parse("wearsupportservice://node_updates"));
    localIntent.putExtra("command", "node_updates");
    if (paramArrayList1 != null) {
      localIntent.putStringArrayListExtra("changed_uri_list", paramArrayList1);
    }
    if (paramArrayList2 != null) {
      localIntent.putStringArrayListExtra("deleted_uri_list", paramArrayList2);
    }
    localContext.startService(localIntent);
  }
  
  public static void startPackageBroadcast(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (!FinskyApp.get().getExperiments().isEnabled(12603948L))
    {
      FinskyLog.d("disabled", new Object[0]);
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, WearSupportService.class);
    localIntent.setData(Uri.parse("wearsupportservice://package_broadcast/" + paramString2));
    localIntent.putExtra("command", "package_broadcast");
    localIntent.putExtra("node_id", paramString1);
    localIntent.putExtra("package_name", paramString2);
    localIntent.putExtra("deleted", paramBoolean);
    localContext.startService(localIntent);
  }
  
  private void startTrackingNode(String paramString)
  {
    try
    {
      if (this.mActiveNodes == null) {
        this.mActiveNodes = new ArrayList();
      }
      this.mActiveNodes.add(paramString);
      return;
    }
    finally {}
  }
  
  private void stopTrackingNode(String paramString)
  {
    try
    {
      this.mActiveNodes.remove(paramString);
      stopSelfIfIdle();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onDestroy()
  {
    if (this.mInstaller != null)
    {
      WearInstaller localWearInstaller = this.mInstaller;
      localWearInstaller.mDownloadQueue.removeListener(localWearInstaller);
    }
    WearDeviceConfigurationHelper.invalidate();
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT < 18)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(Build.VERSION.SDK_INT);
      FinskyLog.d("Not supported on API %d device", arrayOfObject);
      stopSelf(paramInt2);
      return 2;
    }
    this.mStartupRefCount = (1 + this.mStartupRefCount);
    this.mServiceStartId = paramInt2;
    Runnable local1 = new Runnable()
    {
      private int mLoaded;
      
      public final void run()
      {
        this.mLoaded = (1 + this.mLoaded);
        if (this.mLoaded == 2)
        {
          WearSupportService.access$010(WearSupportService.this);
          WearSupportService.access$100(WearSupportService.this, paramIntent);
        }
      }
    };
    FinskyApp.get().mLibraries.load(local1);
    if ((this.mGoogleApiClient != null) && (this.mConnected)) {
      local1.run();
    }
    for (;;)
    {
      return 3;
      if (this.mConnectingCallbacks == null) {
        this.mConnectingCallbacks = new ArrayList(1);
      }
      this.mConnectingCallbacks.add(local1);
      if (this.mConnectingCallbacks.size() <= 1)
      {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
        {
          public final void onConnected(Bundle paramAnonymousBundle)
          {
            Utils.ensureOnMainThread();
            WearSupportService.access$1202(WearSupportService.this, true);
            WearSupportService.access$1400(WearSupportService.this);
          }
          
          public final void onConnectionSuspended(int paramAnonymousInt)
          {
            Utils.ensureOnMainThread();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(paramAnonymousInt);
            FinskyLog.d("onConnectionSuspended: %d", arrayOfObject);
            WearSupportService.access$1202(WearSupportService.this, false);
            WearSupportService.access$1302$58ae4310(WearSupportService.this);
            WearSupportService.access$1400(WearSupportService.this);
          }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener()
        {
          public final void onConnectionFailed(ConnectionResult paramAnonymousConnectionResult)
          {
            Utils.ensureOnMainThread();
            FinskyLog.d("onConnectionFailed: %s", new Object[] { paramAnonymousConnectionResult });
            WearSupportService.access$1202(WearSupportService.this, false);
            WearSupportService.access$1302$58ae4310(WearSupportService.this);
            WearSupportService.access$1400(WearSupportService.this);
          }
        }).build();
        this.mGoogleApiClient.connect();
      }
    }
  }
  
  final void stopSelfIfIdle()
  {
    try
    {
      Utils.ensureOnMainThread();
      if ((this.mStartupRefCount <= 0) && ((this.mActiveNodes == null) || (this.mActiveNodes.isEmpty())) && ((this.mInstaller == null) || (this.mInstaller.isIdle()))) {
        stopSelf(this.mServiceStartId);
      }
      return;
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearSupportService
 * JD-Core Version:    0.7.0.1
 */