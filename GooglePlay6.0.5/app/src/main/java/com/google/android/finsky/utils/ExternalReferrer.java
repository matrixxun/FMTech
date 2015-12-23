package com.google.android.finsky.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.play.utils.config.GservicesValue;

public final class ExternalReferrer
{
  private static final boolean ICE_CREAM_SANDWICH_OR_GREATER;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 14) {}
    for (boolean bool = true;; bool = false)
    {
      ICE_CREAM_SANDWICH_OR_GREATER = bool;
      return;
    }
  }
  
  public static String getExternalReferrer(Uri paramUri)
  {
    String str1 = paramUri.getQueryParameter("referrer");
    if (TextUtils.isEmpty(str1))
    {
      String str2 = paramUri.getQueryParameter("gclid");
      if (TextUtils.isEmpty(str2)) {
        return null;
      }
      str1 = Uri.encode("gclid=" + str2);
    }
    return str1;
  }
  
  private static void logExternalReferrer(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt1).setDocument(paramString1).setReason(paramString2);
    if (paramInt2 >= 0)
    {
      PlayStore.AppData localAppData = new PlayStore.AppData();
      localAppData.version = paramInt2;
      localAppData.hasVersion = true;
      localBackgroundEventBuilder.setAppData(localAppData);
    }
    FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  public static void saveExternalReferrer(final String paramString, Common.Docid paramDocid)
  {
    if ((paramDocid.backend != 3) || (paramDocid.type != 1)) {
      return;
    }
    final String str = paramDocid.backendDocid;
    AppStates localAppStates = FinskyApp.get().mAppStates;
    Runnable local1 = new Runnable()
    {
      public final void run()
      {
        AppStates.AppState localAppState = this.val$appStates.getApp(str);
        WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = this.val$appStates.mStateStore;
        int i = FinskyApp.get().mInstaller.getState(str);
        String str = ExternalReferrer.saveReferrer(paramString, str, localAppState, i, localWriteThroughInstallerDataStore);
        if (str == null)
        {
          if (TextUtils.isEmpty(paramString))
          {
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = str;
            FinskyLog.d("Capture referrer for %s (empty)", arrayOfObject3);
          }
          for (;;)
          {
            ExternalReferrer.logExternalReferrer(515, str, -1, null);
            return;
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = str;
            FinskyLog.d("Capture referrer for %s", arrayOfObject2);
          }
        }
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = str;
        arrayOfObject1[1] = str;
        FinskyLog.d("Dropped referrer for %s because %s", arrayOfObject1);
        int j = -1;
        if ((localAppState != null) && (localAppState.packageManagerState != null)) {
          j = localAppState.packageManagerState.installedVersion;
        }
        ExternalReferrer.logExternalReferrer(516, str, j, str);
      }
    };
    if ((Looper.myLooper() == Looper.getMainLooper()) && (localAppStates.mStateStore.isLoaded()))
    {
      local1.run();
      return;
    }
    localAppStates.load(local1);
  }
  
  public static void saveExternalReferrerForDocId(String paramString1, String paramString2)
  {
    if ((!TextUtils.isEmpty(paramString2)) && (DocUtils.docidToBackend(paramString2) == 3)) {
      saveExternalReferrer(paramString1, DocUtils.createDocid(3, 1, paramString2));
    }
  }
  
  public static void saveExternalReferrerForUrl(String paramString1, String paramString2)
  {
    saveExternalReferrerForDocId(paramString1, Uri.parse(paramString2).getQueryParameter("doc"));
  }
  
  static String saveReferrer(String paramString1, String paramString2, AppStates.AppState paramAppState, int paramInt, InstallerDataStore paramInstallerDataStore)
  {
    int i = 0;
    String str1 = null;
    boolean bool1 = Utils.isDownloadingOrInstalling(paramInt);
    int j;
    int k;
    label39:
    InstallerDataStore.InstallerData localInstallerData1;
    if ((paramAppState != null) && (paramAppState.packageManagerState != null))
    {
      j = 1;
      if ((bool1) || (j != 0)) {
        break label149;
      }
      k = 1;
      localInstallerData1 = paramInstallerDataStore.get(paramString2);
      if (localInstallerData1 != null) {
        break label312;
      }
    }
    label149:
    label312:
    for (int m = 0;; m = localInstallerData1.persistentFlags)
    {
      int n = m;
      if (i != 0)
      {
        n = m & 0xFFFFFFF7;
        paramInstallerDataStore.setExternalReferrer(paramString2, null);
        paramInstallerDataStore.setExternalReferrerTimestampMs(paramString2, 0L);
      }
      if (k != 0)
      {
        n = m | 0x8;
        paramInstallerDataStore.setExternalReferrer(paramString2, paramString1);
        paramInstallerDataStore.setExternalReferrerTimestampMs(paramString2, System.currentTimeMillis());
      }
      if (n != m) {
        paramInstallerDataStore.setPersistentFlags(paramString2, n);
      }
      return str1;
      j = 0;
      break;
      i = 0;
      String str2 = null;
      if (paramAppState != null)
      {
        InstallerDataStore.InstallerData localInstallerData2 = paramAppState.installerData;
        i = 0;
        str2 = null;
        if (localInstallerData2 != null)
        {
          InstallerDataStore.InstallerData localInstallerData3 = paramAppState.installerData;
          str2 = localInstallerData3.externalReferrer;
          boolean bool2 = TextUtils.isEmpty(str2);
          i = 0;
          if (!bool2)
          {
            long l1 = localInstallerData3.externalReferrerTimestampMs;
            long l2 = ((Long)G.externalReferrerLifespanMs.get()).longValue();
            boolean bool3 = l2 < 0L;
            i = 0;
            if (bool3)
            {
              boolean bool4 = l1 + l2 < System.currentTimeMillis();
              i = 0;
              if (bool4)
              {
                str2 = null;
                i = 1;
              }
            }
          }
        }
      }
      if (TextUtils.isEmpty(str2))
      {
        str1 = "already-installed";
        k = 0;
        break label39;
      }
      if (str2.equals(paramString1))
      {
        k = 1;
        str1 = null;
        break label39;
      }
      str1 = "already-captured";
      k = 0;
      break label39;
    }
  }
  
  public static class ExternalReferrerService
    extends Service
  {
    private int mStartId;
    private int mStartupCount;
    
    public static void sendReferrer(Context paramContext, String paramString, boolean paramBoolean)
    {
      Intent localIntent = new Intent(paramContext, ExternalReferrerService.class);
      localIntent.putExtra("package_name", paramString);
      localIntent.putExtra("is_added", paramBoolean);
      localIntent.setData(Uri.parse("externalreferrerservice://sendreferrer/" + paramString));
      paramContext.startService(localIntent);
    }
    
    public IBinder onBind(Intent paramIntent)
    {
      return null;
    }
    
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
      this.mStartId = paramInt2;
      this.mStartupCount = (1 + this.mStartupCount);
      Runnable local1 = new Runnable()
      {
        private int mLoaded;
        
        public final void run()
        {
          this.mLoaded = (1 + this.mLoaded);
          if (this.mLoaded == 2)
          {
            ExternalReferrer.ExternalReferrerService.access$110(ExternalReferrer.ExternalReferrerService.this);
            ExternalReferrer.access$200(this.val$packageName, this.val$isAdded);
            if (ExternalReferrer.ExternalReferrerService.this.mStartupCount <= 0) {
              ExternalReferrer.ExternalReferrerService.this.stopSelf(ExternalReferrer.ExternalReferrerService.this.mStartId);
            }
          }
        }
      };
      FinskyApp.get().mLibraries.load(local1);
      FinskyApp.get().mAppStates.load(local1);
      return 3;
    }
  }
  
  public static final class ReferrerRebroadcaster
    implements PackageMonitorReceiver.PackageStatusListener
  {
    public final void onPackageAdded(String paramString)
    {
      ExternalReferrer.ExternalReferrerService.sendReferrer(FinskyApp.get(), paramString, true);
    }
    
    public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
    
    public final void onPackageChanged(String paramString) {}
    
    public final void onPackageFirstLaunch(String paramString)
    {
      FinskyLog.d("Package first launch for %s", new Object[] { paramString });
      ExternalReferrer.ExternalReferrerService.sendReferrer(FinskyApp.get(), paramString, false);
    }
    
    public final void onPackageRemoved(String paramString, boolean paramBoolean) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ExternalReferrer
 * JD-Core Version:    0.7.0.1
 */