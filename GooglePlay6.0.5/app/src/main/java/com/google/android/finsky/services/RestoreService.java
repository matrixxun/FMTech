package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.installer.PackageInstallerFacade;
import com.google.android.finsky.installer.PackageInstallerFactory;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.Restore.GetBackupDocumentChoicesResponse;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.persistence.FileBasedKeyValueStore;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RestoreService
  extends Service
{
  private static final Boolean DEBUG_SELF_ANDROID_ID = Boolean.valueOf(false);
  private static int[] sErrorRetryBlacklist;
  private static RestoreService sInstance;
  private boolean mAddedInstallerListener;
  private int mAppIconSize;
  private Map<String, BitmapLoader.BitmapContainer> mBitmapContainers = new HashMap();
  private int mDebugCountAlreadyInstalled;
  private int mDebugCountAlreadyOtherAccount;
  private int mDebugCountAlreadyTracked;
  private int mDebugCountMaxAttemptsExceeded;
  private boolean mHandledStartupIntent;
  private SetupHoldListener mHoldListener;
  private PackageInstallerFacade mPackageInstaller;
  private int mServiceStartId;
  private RestoreTracker mTracker;
  
  public static void deleteStores(Context paramContext)
  {
    try
    {
      File[] arrayOfFile = paramContext.getDir("RestoreTracker", 0).listFiles();
      if (arrayOfFile != null)
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++) {
          arrayOfFile[j].delete();
        }
      }
      return;
    }
    catch (Exception localException)
    {
      FinskyLog.w("Error while cleaning stores: %s", new Object[] { localException });
    }
  }
  
  private void deliverBitmap(String paramString, Bitmap paramBitmap)
  {
    this.mPackageInstaller.setAppIcon(paramString, paramBitmap);
    this.mTracker.finishBitmap(paramString);
  }
  
  private boolean doRetryPackage(String paramString)
  {
    Installer localInstaller = FinskyApp.get().mInstaller;
    this.mTracker.mPackageStatusMap.get(paramString);
    if (!this.mTracker.tryAgainPackage(paramString))
    {
      this.mTracker.removePackage(paramString);
      return false;
    }
    PackageInstallStatus localPackageInstallStatus = (PackageInstallStatus)this.mTracker.mPackageStatusMap.get(paramString);
    if (!shouldRestore(paramString, localPackageInstallStatus.versionCode, localPackageInstallStatus.accountName, localInstaller))
    {
      this.mTracker.removePackage(paramString);
      return false;
    }
    this.mTracker.startPackage(paramString, localPackageInstallStatus.versionCode, localPackageInstallStatus.accountName, localPackageInstallStatus.title, localPackageInstallStatus.priority, localPackageInstallStatus.deliveryToken, localPackageInstallStatus.visible, localPackageInstallStatus.appIconUrl, localPackageInstallStatus.isVpa);
    localInstaller.setVisibility(paramString, ((Boolean)G.showPackageRestoreNotifications.get()).booleanValue(), false, false);
    if (!TextUtils.isEmpty(localPackageInstallStatus.deliveryToken)) {
      localInstaller.setDeliveryToken(paramString, localPackageInstallStatus.deliveryToken);
    }
    int i = localPackageInstallStatus.versionCode;
    String str1 = localPackageInstallStatus.accountName;
    String str2 = localPackageInstallStatus.title;
    if (localPackageInstallStatus.isVpa) {}
    for (String str3 = "restore_vpa";; str3 = "restore")
    {
      localInstaller.requestInstall(paramString, i, str1, str2, false, str3, localPackageInstallStatus.priority, 0);
      if (!TextUtils.isEmpty(localPackageInstallStatus.appIconUrl)) {
        startBitmapDownload(paramString, localPackageInstallStatus.appIconUrl);
      }
      return true;
    }
  }
  
  private static Intent getKickIntent(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, RestoreService.class);
    localIntent.putExtra("kick_installer", true);
    localIntent.setData(Uri.parse("restoreservice://kick"));
    return localIntent;
  }
  
  private static Intent getRestoreIntent(String paramString1, String paramString2, Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, RestoreService.class);
    localIntent.putExtra("aid", paramString1);
    localIntent.putExtra("authAccount", paramString2);
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("restoreservice").appendPath("restoreaccount").appendPath(paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localBuilder.appendPath(paramString2);
    }
    localIntent.setData(localBuilder.build());
    return localIntent;
  }
  
  private boolean handleIntent(Intent paramIntent)
  {
    if (paramIntent.getBooleanExtra("startup", false)) {
      return handleStartupIntent();
    }
    if (paramIntent.getBooleanExtra("kick_installer", false))
    {
      FinskyApp.get().mInstaller.startDeferredInstalls();
      return false;
    }
    if (paramIntent.hasExtra("package"))
    {
      String str9 = paramIntent.getStringExtra("package");
      if (Utils.isDownloadingOrInstalling(FinskyApp.get().mInstaller.getState(str9))) {
        return true;
      }
      return doRetryPackage(str9);
    }
    if (paramIntent.hasExtra("array_packages"))
    {
      String str4 = paramIntent.getStringExtra("authAccount");
      boolean bool1 = paramIntent.getBooleanExtra("visible", false);
      String[] arrayOfString1 = paramIntent.getStringArrayExtra("array_packages");
      int[] arrayOfInt1 = paramIntent.getIntArrayExtra("array_version_codes");
      String[] arrayOfString2 = paramIntent.getStringArrayExtra("array_titles");
      int[] arrayOfInt2 = paramIntent.getIntArrayExtra("array_priorities");
      String[] arrayOfString3 = paramIntent.getStringArrayExtra("array_delivery_tokens");
      String[] arrayOfString4 = paramIntent.getStringArrayExtra("array_app_icon_urls");
      boolean bool2 = paramIntent.getBooleanExtra("is_vpa", false);
      int k = 0;
      int m = 0;
      int n = arrayOfString1.length;
      if (m < n)
      {
        String str5;
        label208:
        String str6;
        int i1;
        String str7;
        int i2;
        String str8;
        Installer localInstaller;
        if (arrayOfString3 != null)
        {
          str5 = arrayOfString3[m];
          str6 = arrayOfString1[m];
          i1 = arrayOfInt1[m];
          str7 = arrayOfString2[m];
          i2 = arrayOfInt2[m];
          str8 = arrayOfString4[m];
          localInstaller = FinskyApp.get().mInstaller;
          if (shouldRestore(str6, i1, str4, localInstaller)) {
            break label289;
          }
        }
        for (int i3 = 0;; i3 = 1)
        {
          if (i3 != 0) {
            k++;
          }
          m++;
          break;
          str5 = null;
          break label208;
          label289:
          this.mTracker.startPackage(str6, i1, str4, str7, i2, str5, bool1, str8, bool2);
          localInstaller.setVisibility(str6, ((Boolean)G.showPackageRestoreNotifications.get()).booleanValue(), false, false);
          if (!TextUtils.isEmpty(str5)) {
            localInstaller.setDeliveryToken(str6, str5);
          }
          localInstaller.requestInstall(str6, i1, str4, str7, true, "restore", i2, 0);
          if (!TextUtils.isEmpty(str8)) {
            startBitmapDownload(str6, str8);
          }
        }
      }
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = Integer.valueOf(arrayOfString1.length);
      arrayOfObject2[1] = Integer.valueOf(arrayOfString1.length - k);
      arrayOfObject2[2] = FinskyLog.scrubPii(str4);
      FinskyLog.d("Start restore of %d packages (%d skipped) for acct:%s", arrayOfObject2);
      if (k > 0)
      {
        if (!((Boolean)FinskyPreferences.setupWizardStartDownloads.get()).booleanValue()) {
          break label486;
        }
        FinskyApp.get().mInstaller.startDeferredInstalls();
      }
      for (;;)
      {
        RestoreTracker.access$2200(this.mTracker);
        return false;
        label486:
        long l = ((Long)G.appRestoreFailsafeMs.get()).longValue();
        setAlarm(getKickIntent(getApplicationContext()), l);
      }
    }
    String str1 = paramIntent.getStringExtra("aid");
    if (TextUtils.isEmpty(str1))
    {
      FinskyLog.e("Expecting a non-empty aid extra", new Object[0]);
      return false;
    }
    if ((DEBUG_SELF_ANDROID_ID.booleanValue()) && (str1.equals("self")))
    {
      str1 = Long.toHexString(((Long)DfeApiConfig.androidId.get()).longValue());
      FinskyLog.d("Using own current android-id %s for test restore", new Object[] { str1 });
    }
    String str2;
    try
    {
      Long.parseLong(str1, 16);
      str2 = paramIntent.getStringExtra("authAccount");
      if (str2 == null) {
        break label672;
      }
      if (AccountHandler.findAccount(str2, FinskyApp.get()) == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = FinskyLog.scrubPii(str2);
        FinskyLog.e("Can't find restore acct:%s", arrayOfObject1);
        return false;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      FinskyLog.e("Provided aid can't be parsed as long", new Object[0]);
      return false;
    }
    restore(str1, str2);
    for (;;)
    {
      return true;
      label672:
      Account[] arrayOfAccount = AccountHandler.getAccounts(this);
      if (arrayOfAccount.length <= 0)
      {
        FinskyLog.e("RestoreService can't run - no accounts configured on device!", new Object[0]);
        return false;
      }
      int i = arrayOfAccount.length;
      for (int j = 0; j < i; j++)
      {
        String str3 = arrayOfAccount[j].name;
        restore(str1, str3);
      }
    }
  }
  
  private boolean handleStartupIntent()
  {
    for (;;)
    {
      try
      {
        if (this.mHandledStartupIntent)
        {
          FinskyLog.w("Redelivery of startup intent - dropping it", new Object[0]);
          return false;
        }
        this.mHandledStartupIntent = true;
        if (this.mTracker.mAccountStatusMap.isEmpty()) {
          break label350;
        }
        Iterator localIterator2 = this.mTracker.mAccountStatusMap.keySet().iterator();
        bool1 = false;
        if (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = FinskyLog.scrubPii(str2);
          FinskyLog.d("Recover fetch for account %s", arrayOfObject);
          restore(((AccountFetchStatus)this.mTracker.mAccountStatusMap.get(str2)).androidId, str2);
          bool1 = true;
          continue;
        }
        if (!this.mTracker.mPackageStatusMap.isEmpty())
        {
          Installer localInstaller = FinskyApp.get().mInstaller;
          Iterator localIterator1 = Lists.newArrayList(this.mTracker.mPackageStatusMap.keySet()).iterator();
          boolean bool2 = bool1;
          if (localIterator1.hasNext())
          {
            String str1 = (String)localIterator1.next();
            if (Utils.isDownloadingOrInstalling(localInstaller.getState(str1)))
            {
              bool2 = true;
              continue;
            }
            PackageInstallStatus localPackageInstallStatus = (PackageInstallStatus)this.mTracker.mPackageStatusMap.get(str1);
            if (localPackageInstallStatus == null) {
              continue;
            }
            if (localPackageInstallStatus.retryTime != 0L)
            {
              long l = localPackageInstallStatus.retryTime + ((Long)G.appRestoreRetryDownloadHoldoffMs.get()).longValue();
              if (System.currentTimeMillis() < l) {
                continue;
              }
            }
            FinskyLog.d("Overdue alarm for %s so retry immediately", new Object[] { str1 });
            if (doRetryPackage(str1)) {
              continue;
            }
            this.mTracker.removePackage(str1);
            continue;
          }
          bool1 = bool2;
        }
      }
      catch (Exception localException)
      {
        FinskyLog.w("Exception restarting: ", new Object[] { localException });
        deleteStores(this);
        return false;
      }
      return bool1;
      label350:
      boolean bool1 = false;
    }
  }
  
  private static boolean inErrorRetryBlacklist(int paramInt)
  {
    if (sErrorRetryBlacklist == null)
    {
      String[] arrayOfString = Utils.commaUnpackStrings((String)G.appRestoreHttpStatusBlacklist.get());
      sErrorRetryBlacklist = new int[arrayOfString.length];
      int j = 0;
      for (;;)
      {
        if (j < arrayOfString.length) {
          try
          {
            sErrorRetryBlacklist[j] = Integer.valueOf(arrayOfString[j]).intValue();
            j++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            for (;;)
            {
              sErrorRetryBlacklist[j] = -2147483648;
            }
          }
        }
      }
    }
    for (int i = 0; i < sErrorRetryBlacklist.length; i++) {
      if (paramInt == sErrorRetryBlacklist[i]) {
        return true;
      }
    }
    return false;
  }
  
  private void notifyHoldListener$6f7cbed2(int paramInt, String paramString, boolean paramBoolean)
  {
    if (this.mHoldListener != null)
    {
      this.mHoldListener.onStatusChange$4a6d67e4(paramInt, paramString, paramBoolean, "RestoreService");
      if (paramInt == 1) {
        this.mHoldListener = null;
      }
    }
  }
  
  public static void recoverRestore(Context paramContext)
  {
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, RestoreService.class);
    localIntent.putExtra("startup", true);
    localIntent.setData(Uri.parse("restoreservice://startup"));
    localContext.startService(localIntent);
  }
  
  public static boolean registerHoldListener(SetupHoldListener paramSetupHoldListener)
  {
    if (paramSetupHoldListener == null)
    {
      if (sInstance != null) {
        sInstance.mHoldListener = null;
      }
      return true;
    }
    if ((sInstance == null) || (!sInstance.mTracker.shouldHold(null))) {
      return false;
    }
    RestoreService localRestoreService = sInstance;
    localRestoreService.mHoldListener = paramSetupHoldListener;
    new Handler(localRestoreService.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        if (RestoreService.this.mTracker.shouldHold(null))
        {
          if (RestoreService.this.mTracker.mInstallerRunningPackage != null)
          {
            String str = RestoreService.this.mTracker.mInstallerRunningPackage;
            RestoreService.PackageInstallStatus localPackageInstallStatus = (RestoreService.PackageInstallStatus)RestoreService.this.mTracker.mPackageStatusMap.get(str);
            if ((localPackageInstallStatus != null) && (localPackageInstallStatus.visible))
            {
              RestoreService.this.notifyHoldListener$6f7cbed2(3, str, localPackageInstallStatus.title, true);
              return;
            }
          }
          RestoreService.this.notifyHoldListener$6f7cbed2(2, null, null, false);
          return;
        }
        RestoreService.this.notifyHoldListener$6f7cbed2(1, null, null, false);
      }
    });
    return true;
  }
  
  private void restore(final String paramString1, final String paramString2)
  {
    AccountFetchStatus localAccountFetchStatus1 = (AccountFetchStatus)this.mTracker.mAccountStatusMap.get(paramString2);
    if ((localAccountFetchStatus1 != null) && (localAccountFetchStatus1.inFlight)) {}
    for (int i = 1; i != 0; i = 0)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = FinskyLog.scrubPii(paramString2);
      FinskyLog.d("Skip restore acct:%s already started", arrayOfObject3);
      return;
    }
    try
    {
      long l = Long.parseLong(paramString1, 16);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = FinskyLog.scrubPii(paramString1);
      arrayOfObject2[1] = FinskyLog.scrubPii(paramString2);
      FinskyLog.d("Start restore aid:%s acct:%s", arrayOfObject2);
      RestoreTracker localRestoreTracker = this.mTracker;
      AccountFetchStatus localAccountFetchStatus2 = (AccountFetchStatus)localRestoreTracker.mAccountStatusMap.get(paramString2);
      if (localAccountFetchStatus2 == null)
      {
        localAccountFetchStatus2 = new AccountFetchStatus((byte)0);
        localAccountFetchStatus2.attempts = 0;
        localRestoreTracker.mAccountStatusMap.put(paramString2, localAccountFetchStatus2);
      }
      localAccountFetchStatus2.attempts = (1 + localAccountFetchStatus2.attempts);
      localAccountFetchStatus2.androidId = paramString1;
      localAccountFetchStatus2.inFlight = true;
      localRestoreTracker.writeAccountStatus(paramString2);
      FinskyApp.get().getDfeApi(paramString2).getBackupDocumentChoices(l, new RestoreResponseListener(paramString2), new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          RestoreService.this.mTracker.finishAccount(paramString2, false, paramAnonymousVolleyError);
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = paramAnonymousVolleyError.getMessage();
          FinskyLog.w("Error while getting list of applications to restore from server: %s", arrayOfObject1);
          RestoreService.RestoreTracker localRestoreTracker = RestoreService.this.mTracker;
          String str = paramString2;
          RestoreService.AccountFetchStatus localAccountFetchStatus = (RestoreService.AccountFetchStatus)localRestoreTracker.mAccountStatusMap.get(str);
          if ((localAccountFetchStatus != null) && (localAccountFetchStatus.attempts >= ((Integer)G.appRestoreFetchListMaxAttempts.get()).intValue()))
          {
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = Integer.valueOf(localAccountFetchStatus.attempts);
            arrayOfObject2[1] = FinskyLog.scrubPii(str);
            FinskyLog.d("Reached limit %d for %s", arrayOfObject2);
            localRestoreTracker.mAccountStatusMap.remove(str);
            localRestoreTracker.writeAccountStatus(str);
          }
          for (int i = 0;; i = 1)
          {
            if (i != 0)
            {
              Intent localIntent = RestoreService.getRestoreIntent(paramString1, paramString2, RestoreService.this);
              long l = RestoreService.access$1400$6669bb53(((Long)G.appRestoreRetryFetchListHoldoffMs.get()).longValue());
              RestoreService.this.setAlarm(localIntent, l);
            }
            return;
          }
        }
      });
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = FinskyLog.scrubPii(paramString1);
      FinskyLog.e("Provided aid can't be parsed as long: %s", arrayOfObject1);
    }
  }
  
  public static void restoreAccounts(Context paramContext, String paramString1, String paramString2)
  {
    if (((Boolean)FinskyPreferences.directedRestoreStarted.get()).booleanValue())
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(paramString2);
      FinskyLog.d("Skipping restore for %s because directedRestoreStarted=true", arrayOfObject);
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    localContext.startService(getRestoreIntent(paramString1, paramString2, localContext));
  }
  
  public static void restorePackages(Context paramContext, boolean paramBoolean1, String paramString, boolean paramBoolean2, String[] paramArrayOfString1, int[] paramArrayOfInt1, String[] paramArrayOfString2, int[] paramArrayOfInt2, String[] paramArrayOfString3, String[] paramArrayOfString4, boolean paramBoolean3)
  {
    if (paramBoolean1) {
      FinskyPreferences.directedRestoreStarted.put(Boolean.valueOf(true));
    }
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, RestoreService.class);
    localIntent.putExtra("authAccount", paramString);
    localIntent.putExtra("visible", paramBoolean2);
    localIntent.putExtra("array_packages", paramArrayOfString1);
    localIntent.putExtra("array_version_codes", paramArrayOfInt1);
    localIntent.putExtra("array_titles", paramArrayOfString2);
    localIntent.putExtra("array_priorities", paramArrayOfInt2);
    localIntent.putExtra("is_vpa", paramBoolean3);
    if (paramArrayOfString3 != null) {
      localIntent.putExtra("array_delivery_tokens", paramArrayOfString3);
    }
    localIntent.putExtra("array_app_icon_urls", paramArrayOfString4);
    localIntent.setData(Uri.parse("restoreservice://restorepackages"));
    localContext.startService(localIntent);
  }
  
  private long setAlarm(Intent paramIntent, long paramLong)
  {
    if (TextUtils.isEmpty(paramIntent.getDataString())) {
      throw new IllegalArgumentException("Alarm intent needs data URI");
    }
    Context localContext = getApplicationContext();
    AlarmManager localAlarmManager = (AlarmManager)localContext.getSystemService("alarm");
    long l = paramLong + System.currentTimeMillis();
    localAlarmManager.set(0, l, PendingIntent.getService(localContext, 0, paramIntent, 0));
    return l;
  }
  
  public static boolean shouldHold()
  {
    return (sInstance != null) && (sInstance.mTracker.shouldHold(null));
  }
  
  private boolean shouldRestore(String paramString1, int paramInt, String paramString2, Installer paramInstaller)
  {
    PlayStore.AppData localAppData = new PlayStore.AppData();
    localAppData.version = paramInt;
    localAppData.hasVersion = true;
    if ((this.mTracker.mPackageStatusMap.get(paramString1) != null) && (!this.mTracker.tryAgainPackage(paramString1)))
    {
      this.mDebugCountMaxAttemptsExceeded = (1 + this.mDebugCountMaxAttemptsExceeded);
      FinskyApp.get().getEventLogger().logBackgroundEvent(113, paramString1, "retry-expired", 0, null, localAppData);
      return false;
    }
    PackageInstallStatus localPackageInstallStatus1 = (PackageInstallStatus)this.mTracker.mPackageStatusMap.get(paramString1);
    if ((localPackageInstallStatus1 != null) && (!paramString2.equals(localPackageInstallStatus1.accountName)))
    {
      this.mDebugCountAlreadyOtherAccount = (1 + this.mDebugCountAlreadyOtherAccount);
      FinskyApp.get().getEventLogger().logBackgroundEvent(113, paramString1, "other-account", 0, null, localAppData);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString1;
      arrayOfObject2[1] = Integer.valueOf(paramInt);
      FinskyLog.d("Skipping restore of %s v:%d because already restoring for another account", arrayOfObject2);
      return false;
    }
    if (Utils.isDownloadingOrInstalling(paramInstaller.getState(paramString1)))
    {
      this.mDebugCountAlreadyTracked = (1 + this.mDebugCountAlreadyTracked);
      FinskyApp.get().getEventLogger().logBackgroundEvent(113, paramString1, "is-tracked", 0, null, localAppData);
      FinskyLog.d("Skipping restore of %s because already restoring", new Object[] { paramString1 });
      return false;
    }
    PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(paramString1);
    if ((localPackageState != null) && (localPackageState.installedVersion >= paramInt))
    {
      this.mDebugCountAlreadyInstalled = (1 + this.mDebugCountAlreadyInstalled);
      localAppData.oldVersion = localPackageState.installedVersion;
      localAppData.hasOldVersion = true;
      FinskyApp.get().getEventLogger().logBackgroundEvent(113, paramString1, "already-installed", 0, null, localAppData);
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = paramString1;
      arrayOfObject1[1] = Integer.valueOf(paramInt);
      arrayOfObject1[2] = Integer.valueOf(localPackageState.installedVersion);
      FinskyLog.d("Skipping restore of %s v:%d because v:%d is installed", arrayOfObject1);
      PackageInstallStatus localPackageInstallStatus2 = new PackageInstallStatus();
      localPackageInstallStatus2.accountName = paramString2;
      localPackageInstallStatus2.versionCode = localPackageState.installedVersion;
      return false;
    }
    FinskyLog.d("Should attempt restore of %s", new Object[] { paramString1 });
    return true;
  }
  
  private void startBitmapDownload(final String paramString1, String paramString2)
  {
    if (this.mAppIconSize < 0) {}
    while (TextUtils.isEmpty(paramString2)) {
      return;
    }
    if (this.mBitmapContainers.containsKey(paramString1))
    {
      FinskyLog.w("Request for already-downloading bitmap for %s", new Object[] { paramString1 });
      return;
    }
    RestoreTracker localRestoreTracker = this.mTracker;
    FetchBitmapStatus localFetchBitmapStatus = (FetchBitmapStatus)localRestoreTracker.mBitmapStatusMap.get(paramString1);
    if (localFetchBitmapStatus == null)
    {
      localFetchBitmapStatus = new FetchBitmapStatus((byte)0);
      localFetchBitmapStatus.attempts = 0;
      localRestoreTracker.mBitmapStatusMap.put(paramString1, localFetchBitmapStatus);
    }
    localFetchBitmapStatus.attempts = (1 + localFetchBitmapStatus.attempts);
    localFetchBitmapStatus.bitmapUrl = paramString2;
    localFetchBitmapStatus.retryTime = 0L;
    BitmapLoader.BitmapContainer localBitmapContainer = FinskyApp.get().mBitmapLoader.get$6721551b(paramString2, this.mAppIconSize, this.mAppIconSize, new BitmapLoader.BitmapLoadedHandler()
    {
      public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
      {
        Bitmap localBitmap = paramAnonymousBitmapContainer.mBitmap;
        if (localBitmap != null)
        {
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = paramString1;
          FinskyLog.d("Received appIcon for %s", arrayOfObject4);
          RestoreService.this.mBitmapContainers.remove(paramString1);
          RestoreService.this.deliverBitmap(paramString1, localBitmap);
          return;
        }
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramString1;
        FinskyLog.w("Unable to download appIcon for %s", arrayOfObject1);
        RestoreService.RestoreTracker localRestoreTracker = RestoreService.this.mTracker;
        String str = paramString1;
        RestoreService.FetchBitmapStatus localFetchBitmapStatus = (RestoreService.FetchBitmapStatus)localRestoreTracker.mBitmapStatusMap.get(str);
        int i;
        if (localFetchBitmapStatus == null) {
          i = 0;
        }
        for (;;)
        {
          if (i != 0)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = paramString1;
            FinskyLog.w("Unable to download appIcon for %s", arrayOfObject2);
          }
          RestoreService.access$1700(RestoreService.this, paramString1, false);
          return;
          if (localFetchBitmapStatus.attempts >= ((Integer)G.appRestoreAppIconMaxAttempts.get()).intValue())
          {
            Object[] arrayOfObject3 = new Object[2];
            arrayOfObject3[0] = Integer.valueOf(localFetchBitmapStatus.attempts);
            arrayOfObject3[1] = str;
            FinskyLog.d("Reached limit %d for %s", arrayOfObject3);
            i = 0;
          }
          else if (!localRestoreTracker.mPackageStatusMap.containsKey(str))
          {
            i = 0;
          }
          else if (FinskyApp.get().mPackageStateRepository.get(str) != null)
          {
            i = 0;
          }
          else
          {
            i = 1;
          }
        }
      }
    });
    Bitmap localBitmap = localBitmapContainer.mBitmap;
    if (localBitmap != null)
    {
      FinskyLog.d("Received cached bitmap for %s", new Object[] { paramString1 });
      deliverBitmap(paramString1, localBitmap);
      return;
    }
    FinskyLog.d("Waiting for bitmap for %s", new Object[] { paramString1 });
    this.mBitmapContainers.put(paramString1, localBitmapContainer);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    sInstance = this;
    this.mTracker = new RestoreTracker((byte)0);
    this.mPackageInstaller = PackageInstallerFactory.sPackageInstaller;
    this.mAppIconSize = this.mPackageInstaller.getAppIconSize();
  }
  
  public void onDestroy()
  {
    if (this.mAddedInstallerListener) {
      FinskyApp.get().mInstaller.removeListener(this.mTracker);
    }
    this.mTracker = null;
    notifyHoldListener$6f7cbed2(1, null, false);
    sInstance = null;
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.mServiceStartId = paramInt2;
    RestoreTracker localRestoreTracker1 = this.mTracker;
    localRestoreTracker1.mStartupRefCount = (1 + localRestoreTracker1.mStartupRefCount);
    Runnable local1 = new Runnable()
    {
      private int mLoaded;
      
      public final void run()
      {
        this.mLoaded = (1 + this.mLoaded);
        if (this.mLoaded == 4)
        {
          RestoreService.RestoreTracker localRestoreTracker = RestoreService.this.mTracker;
          localRestoreTracker.mStartupRefCount = (-1 + localRestoreTracker.mStartupRefCount);
          boolean bool = RestoreService.this.handleIntent(paramIntent);
          RestoreService.access$2000(RestoreService.this);
          if (!bool) {
            RestoreService.this.mTracker.stopServiceIfDone();
          }
        }
      }
    };
    RestoreTracker localRestoreTracker2 = this.mTracker;
    Context localContext1 = getApplicationContext();
    RestoreTracker localRestoreTracker3;
    if (localRestoreTracker2.mAccountStore == null)
    {
      localRestoreTracker2.mAccountStore = new WriteThroughKeyValueStore(new FileBasedKeyValueStore(localContext1.getDir("RestoreTracker", 0), "account-"));
      localRestoreTracker2.mAccountStore.load(new RestoreService.RestoreTracker.1(localRestoreTracker2, local1));
      localRestoreTracker3 = this.mTracker;
      Context localContext2 = getApplicationContext();
      if (localRestoreTracker3.mPackagesStore != null) {
        break label210;
      }
      localRestoreTracker3.mPackagesStore = new WriteThroughKeyValueStore(new FileBasedKeyValueStore(localContext2.getDir("RestoreTracker", 0), "package-"));
      localRestoreTracker3.mPackagesStore.load(new RestoreService.RestoreTracker.2(localRestoreTracker3, local1));
    }
    for (;;)
    {
      FinskyApp.get().mLibraries.load(local1);
      FinskyApp.get().mAppStates.load(local1);
      return 3;
      localRestoreTracker2.mAccountStore.load(local1);
      break;
      label210:
      localRestoreTracker3.mPackagesStore.load(local1);
    }
  }
  
  private static final class AccountFetchStatus
  {
    String androidId;
    int attempts;
    boolean inFlight;
  }
  
  private static final class FetchBitmapStatus
  {
    int attempts;
    String bitmapUrl;
    long retryTime;
  }
  
  public static final class PackageInstallStatus
  {
    String accountName;
    String appIconUrl;
    int attempts;
    String deliveryToken;
    boolean isVpa;
    int priority;
    long retryTime;
    String title;
    int versionCode;
    boolean visible;
  }
  
  final class RestoreResponseListener
    implements Response.Listener<Restore.GetBackupDocumentChoicesResponse>
  {
    private final String mAccountName;
    
    public RestoreResponseListener(String paramString)
    {
      this.mAccountName = paramString;
    }
  }
  
  private final class RestoreTracker
    implements InstallerListener
  {
    final Map<String, RestoreService.AccountFetchStatus> mAccountStatusMap = new HashMap();
    WriteThroughKeyValueStore mAccountStore;
    final Map<String, RestoreService.FetchBitmapStatus> mBitmapStatusMap = new HashMap();
    private int mFailed = 0;
    String mInstallerRunningPackage = null;
    private boolean mIsProgressNotificationEnabled;
    final Map<String, RestoreService.PackageInstallStatus> mPackageStatusMap = new HashMap();
    WriteThroughKeyValueStore mPackagesStore;
    int mStartupRefCount = 0;
    private int mSucceeded = 0;
    
    private RestoreTracker() {}
    
    static RestoreService.PackageInstallStatus convertEntryToPackageStatus(Context paramContext, String paramString, Map<String, String> paramMap)
    {
      PackageManager localPackageManager = FinskyApp.get().getPackageManager();
      try
      {
        localPackageManager.getPackageInfo(paramString, 0);
        return null;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        String str1 = (String)paramMap.get("attempts");
        String str2 = (String)paramMap.get("versionCode");
        String str3 = (String)paramMap.get("accountName");
        String str4 = (String)paramMap.get("title");
        String str5 = (String)paramMap.get("priority");
        String str6 = (String)paramMap.get("deliveryToken");
        String str7 = (String)paramMap.get("visible");
        String str8 = (String)paramMap.get("appIconUrl");
        String str9 = (String)paramMap.get("retryTime");
        String str10 = (String)paramMap.get("isVpa");
        if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str4)) || (TextUtils.isEmpty(str5)) || (TextUtils.isEmpty(str7)) || (TextUtils.isEmpty(str8)) || (TextUtils.isEmpty(str9)))
        {
          FinskyLog.w("Missing data for package %s", new Object[] { paramString });
          return null;
        }
        int i;
        int j;
        int k;
        boolean bool1;
        long l;
        boolean bool2;
        try
        {
          i = Integer.valueOf(str1).intValue();
          j = Integer.valueOf(str2).intValue();
          k = Integer.valueOf(str5).intValue();
          bool1 = Boolean.valueOf(str7).booleanValue();
          l = Long.valueOf(str9).longValue();
          bool2 = Boolean.valueOf(str10).booleanValue();
          if ((i < 0) || (i >= ((Integer)G.appRestoreDownloadMaxAttempts.get()).intValue()))
          {
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = Integer.valueOf(i);
            arrayOfObject2[1] = paramString;
            FinskyLog.d("Reached limit %d for %s", arrayOfObject2);
            return null;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          Object[] arrayOfObject1 = new Object[8];
          arrayOfObject1[0] = paramString;
          arrayOfObject1[1] = str1;
          arrayOfObject1[2] = str2;
          arrayOfObject1[3] = FinskyLog.scrubPii(str3);
          arrayOfObject1[4] = str4;
          arrayOfObject1[5] = str5;
          arrayOfObject1[6] = str7;
          arrayOfObject1[7] = str9;
          FinskyLog.w("Bad data for package %s (%s, %s, %s, %s, %s, %s, %s)", arrayOfObject1);
          return null;
        }
        if (AccountHandler.findAccount(str3, paramContext) == null)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = FinskyLog.scrubPii(str3);
          FinskyLog.w("Unknown account %s", arrayOfObject3);
          return null;
        }
        RestoreService.PackageInstallStatus localPackageInstallStatus = new RestoreService.PackageInstallStatus();
        localPackageInstallStatus.attempts = i;
        localPackageInstallStatus.versionCode = j;
        localPackageInstallStatus.accountName = str3;
        localPackageInstallStatus.title = str4;
        localPackageInstallStatus.priority = k;
        localPackageInstallStatus.deliveryToken = str6;
        localPackageInstallStatus.visible = bool1;
        localPackageInstallStatus.appIconUrl = str8;
        localPackageInstallStatus.retryTime = l;
        localPackageInstallStatus.isVpa = bool2;
        return localPackageInstallStatus;
      }
    }
    
    static RestoreService.AccountFetchStatus convertEntryToStatus(Context paramContext, String paramString, Map<String, String> paramMap)
    {
      if (AccountHandler.findAccount(paramString, paramContext) == null)
      {
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = FinskyLog.scrubPii(paramString);
        FinskyLog.w("Unknown account %s", arrayOfObject4);
        return null;
      }
      String str1 = (String)paramMap.get("attempts");
      String str2 = (String)paramMap.get("aid");
      if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = FinskyLog.scrubPii(paramString);
        FinskyLog.w("Missing data for account %s", arrayOfObject1);
        return null;
      }
      int i;
      try
      {
        i = Integer.valueOf(str1).intValue();
        if ((i < 0) || (i >= ((Integer)G.appRestoreFetchListMaxAttempts.get()).intValue()))
        {
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = Integer.valueOf(i);
          arrayOfObject3[1] = FinskyLog.scrubPii(paramString);
          FinskyLog.d("Reached limit %d for %s", arrayOfObject3);
          return null;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = FinskyLog.scrubPii(paramString);
        arrayOfObject2[1] = str1;
        arrayOfObject2[2] = str2;
        FinskyLog.w("Bad data for account %s (%s, %s)", arrayOfObject2);
        return null;
      }
      RestoreService.AccountFetchStatus localAccountFetchStatus = new RestoreService.AccountFetchStatus((byte)0);
      localAccountFetchStatus.attempts = i;
      localAccountFetchStatus.androidId = str2;
      return localAccountFetchStatus;
    }
    
    private void finishPackage(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramBoolean1) {
        this.mSucceeded = (1 + this.mSucceeded);
      }
      for (;;)
      {
        if ((paramBoolean1) || (!paramBoolean2)) {
          removePackage(paramString);
        }
        stopServiceIfDone();
        return;
        if (!paramBoolean2) {
          this.mFailed = (1 + this.mFailed);
        }
      }
    }
    
    private void notifyProgress()
    {
      if (!this.mIsProgressNotificationEnabled) {
        return;
      }
      Resources localResources = RestoreService.this.getResources();
      Context localContext = RestoreService.this.getApplicationContext();
      NotificationCompat.Builder localBuilder1 = new NotificationCompat.Builder(localContext);
      NotificationManager localNotificationManager = (NotificationManager)localContext.getSystemService("notification");
      PendingIntent localPendingIntent = PendingIntent.getActivity(localContext, -555892993, MainActivity.getMyDownloadsIntent(localContext), 268435456);
      localBuilder1.mColor = localContext.getResources().getColor(2131689697);
      localBuilder1.mVisibility = 0;
      localBuilder1.mLocalOnly = true;
      localBuilder1.mCategory = "status";
      localBuilder1.mContentIntent = localPendingIntent;
      int i = this.mFailed + this.mSucceeded;
      int j = i + this.mPackageStatusMap.size();
      if (j == i)
      {
        NotificationCompat.Builder localBuilder3 = localBuilder1.setContentTitle(localResources.getString(2131361895)).setProgress(j, i, false).setSmallIcon(2130838130);
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(i);
        arrayOfObject2[1] = Integer.valueOf(j);
        localBuilder3.setContentText(localResources.getString(2131361896, arrayOfObject2));
      }
      for (;;)
      {
        localNotificationManager.notify(-555892993, localBuilder1.build());
        return;
        NotificationCompat.Builder localBuilder2 = localBuilder1.setContentTitle(localResources.getString(2131361894)).setProgress(j, i, true).setSmallIcon(17301633);
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(i);
        arrayOfObject1[1] = Integer.valueOf(j);
        localBuilder2.setContentText(localResources.getString(2131361896, arrayOfObject1)).setFlag(2, true);
      }
    }
    
    private void trackPackageForListener(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      RestoreService.PackageInstallStatus localPackageInstallStatus = (RestoreService.PackageInstallStatus)this.mPackageStatusMap.get(paramString);
      if ((localPackageInstallStatus == null) || (localPackageInstallStatus.priority != 1)) {}
      do
      {
        return;
        if (paramBoolean1)
        {
          this.mInstallerRunningPackage = paramString;
          if (localPackageInstallStatus.visible)
          {
            RestoreService.this.notifyHoldListener$6f7cbed2(3, paramString, localPackageInstallStatus.title, true);
            return;
          }
          RestoreService.this.notifyHoldListener$6f7cbed2(2, paramString, null, false);
          return;
        }
        this.mInstallerRunningPackage = null;
        if (paramBoolean2)
        {
          RestoreService.this.notifyHoldListener$6f7cbed2(2, paramString, null, false);
          return;
        }
      } while (shouldHold(paramString));
      RestoreService.this.notifyHoldListener$6f7cbed2(1, paramString, null, false);
    }
    
    private void writePackageStatus(String paramString)
    {
      String str = Uri.encode(paramString);
      RestoreService.PackageInstallStatus localPackageInstallStatus = (RestoreService.PackageInstallStatus)this.mPackageStatusMap.get(paramString);
      if (localPackageInstallStatus == null)
      {
        this.mPackagesStore.delete(str);
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("attempts", Integer.toString(localPackageInstallStatus.attempts));
      localHashMap.put("versionCode", Integer.toString(localPackageInstallStatus.versionCode));
      localHashMap.put("accountName", localPackageInstallStatus.accountName);
      localHashMap.put("title", localPackageInstallStatus.title);
      localHashMap.put("priority", Integer.toString(localPackageInstallStatus.priority));
      if (!TextUtils.isEmpty(localPackageInstallStatus.deliveryToken)) {
        localHashMap.put("deliveryToken", localPackageInstallStatus.deliveryToken);
      }
      localHashMap.put("visible", Boolean.toString(localPackageInstallStatus.visible));
      localHashMap.put("appIconUrl", localPackageInstallStatus.appIconUrl);
      localHashMap.put("retryTime", Long.toString(localPackageInstallStatus.retryTime));
      localHashMap.put("isVpa", Boolean.toString(localPackageInstallStatus.isVpa));
      this.mPackagesStore.put(str, localHashMap);
    }
    
    public final void finishAccount(String paramString, boolean paramBoolean, VolleyError paramVolleyError)
    {
      RestoreService.AccountFetchStatus localAccountFetchStatus = (RestoreService.AccountFetchStatus)this.mAccountStatusMap.get(paramString);
      int i;
      int j;
      if (paramVolleyError == null)
      {
        i = 0;
        BackgroundEventBuilder localBackgroundEventBuilder1 = new BackgroundEventBuilder(118).setErrorCode(i).setExceptionType(paramVolleyError);
        if (localAccountFetchStatus == null) {
          break label112;
        }
        j = localAccountFetchStatus.attempts;
        label54:
        BackgroundEventBuilder localBackgroundEventBuilder2 = localBackgroundEventBuilder1.setAttempts(j);
        FinskyApp.get().getEventLogger(paramString).sendBackgroundEventToSinks(localBackgroundEventBuilder2.event);
        if (!paramBoolean) {
          break label118;
        }
        this.mAccountStatusMap.remove(paramString);
        writeAccountStatus(paramString);
      }
      for (;;)
      {
        stopServiceIfDone();
        return;
        i = InstallPolicies.volleyErrorToInstallerError(paramVolleyError);
        break;
        label112:
        j = 0;
        break label54;
        label118:
        if (localAccountFetchStatus != null) {
          localAccountFetchStatus.inFlight = false;
        }
      }
    }
    
    public final void finishBitmap(String paramString)
    {
      this.mBitmapStatusMap.remove(paramString);
      stopServiceIfDone();
    }
    
    public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
    {
      RestoreService.PackageInstallStatus localPackageInstallStatus1 = (RestoreService.PackageInstallStatus)this.mPackageStatusMap.get(paramString);
      if (localPackageInstallStatus1 == null) {
        return;
      }
      int i = 0;
      switch (paramInt1)
      {
      default: 
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(paramInt1);
        FinskyLog.wtf("enum %s", arrayOfObject3);
      }
      while (i != 0)
      {
        RestoreService.access$1700(RestoreService.this, paramString, true);
        return;
        trackPackageForListener(paramString, true, false);
        i = 0;
        continue;
        FinskyLog.e("Restore package %s download cancelled", new Object[] { paramString });
        trackPackageForListener(paramString, false, false);
        finishPackage(paramString, false, false);
        i = 1;
        continue;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = paramString;
        arrayOfObject2[1] = Integer.valueOf(paramInt2);
        FinskyLog.e("Restore package %s download error %d", arrayOfObject2);
        boolean bool;
        label211:
        Long localLong;
        label267:
        long l2;
        RestoreTracker localRestoreTracker;
        RestoreService.PackageInstallStatus localPackageInstallStatus2;
        if ((!RestoreService.access$1100$6669b7a2(paramInt2)) && (RestoreService.this.mTracker.tryAgainPackage(paramString)))
        {
          bool = true;
          trackPackageForListener(paramString, false, bool);
          finishPackage(paramString, false, bool);
          if (!bool) {
            break label406;
          }
          Intent localIntent = RestoreService.access$1300(RestoreService.this, paramString);
          if ((localPackageInstallStatus1 == null) || (localPackageInstallStatus1.priority != 1)) {
            break label376;
          }
          localLong = (Long)G.appRestoreRetryDownloadHoldoffHighPriorityMs.get();
          long l1 = localLong.longValue();
          l2 = RestoreService.this.setAlarm(localIntent, RestoreService.access$1400$6669bb53(l1));
          localRestoreTracker = RestoreService.this.mTracker;
          localPackageInstallStatus2 = (RestoreService.PackageInstallStatus)localRestoreTracker.mPackageStatusMap.get(paramString);
          if (localPackageInstallStatus2 != null) {
            break label390;
          }
          FinskyLog.d("Unexpected missing package %s, can't write retry time", new Object[] { paramString });
        }
        for (;;)
        {
          FinskyApp.get().mInstaller.promiseInstall$1718defc(paramString, localPackageInstallStatus1.title);
          RestoreService.this.startBitmapDownload(paramString, localPackageInstallStatus1.appIconUrl);
          i = 0;
          break;
          bool = false;
          break label211;
          label376:
          localLong = (Long)G.appRestoreRetryDownloadHoldoffMs.get();
          break label267;
          label390:
          localPackageInstallStatus2.retryTime = l2;
          localRestoreTracker.writePackageStatus(paramString);
        }
        label406:
        i = 1;
        continue;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = paramString;
        arrayOfObject1[1] = Integer.valueOf(paramInt2);
        FinskyLog.e("Restore package %s install error %d", arrayOfObject1);
        trackPackageForListener(paramString, false, false);
        finishPackage(paramString, false, false);
        i = 1;
        continue;
        FinskyLog.d("Restore package %s install complete", new Object[] { paramString });
        trackPackageForListener(paramString, false, false);
        finishPackage(paramString, true, false);
        i = 1;
      }
    }
    
    final void removePackage(String paramString)
    {
      this.mPackageStatusMap.remove(paramString);
      writePackageStatus(paramString);
      notifyProgress();
    }
    
    public final boolean shouldHold(String paramString)
    {
      if (!this.mAccountStatusMap.isEmpty())
      {
        Iterator localIterator2 = this.mAccountStatusMap.values().iterator();
        while (localIterator2.hasNext()) {
          if (((RestoreService.AccountFetchStatus)localIterator2.next()).inFlight) {
            return true;
          }
        }
      }
      if (!this.mPackageStatusMap.isEmpty())
      {
        Iterator localIterator1 = this.mPackageStatusMap.entrySet().iterator();
        while (localIterator1.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator1.next();
          if (((paramString == null) || (!paramString.equals(localEntry.getKey()))) && (((RestoreService.PackageInstallStatus)localEntry.getValue()).priority == 1)) {
            return true;
          }
        }
      }
      return false;
    }
    
    public final void startPackage(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, boolean paramBoolean1, String paramString5, boolean paramBoolean2)
    {
      RestoreService.PackageInstallStatus localPackageInstallStatus = (RestoreService.PackageInstallStatus)this.mPackageStatusMap.get(paramString1);
      if (localPackageInstallStatus == null)
      {
        localPackageInstallStatus = new RestoreService.PackageInstallStatus();
        localPackageInstallStatus.attempts = 0;
      }
      localPackageInstallStatus.attempts = (1 + localPackageInstallStatus.attempts);
      localPackageInstallStatus.versionCode = paramInt1;
      localPackageInstallStatus.accountName = paramString2;
      localPackageInstallStatus.title = paramString3;
      localPackageInstallStatus.priority = paramInt2;
      localPackageInstallStatus.deliveryToken = paramString4;
      localPackageInstallStatus.visible = paramBoolean1;
      localPackageInstallStatus.appIconUrl = paramString5;
      localPackageInstallStatus.retryTime = 0L;
      localPackageInstallStatus.isVpa = paramBoolean2;
      this.mPackageStatusMap.put(paramString1, localPackageInstallStatus);
      writePackageStatus(paramString1);
      notifyProgress();
    }
    
    public final void stopServiceIfDone()
    {
      if ((this.mPackageStatusMap.isEmpty()) && (this.mAccountStatusMap.isEmpty()) && (this.mBitmapStatusMap.isEmpty()) && (this.mStartupRefCount <= 0))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(this.mSucceeded);
        arrayOfObject[1] = Integer.valueOf(this.mFailed);
        FinskyLog.d("Restore complete with %d success and %d failed.", arrayOfObject);
        RestoreService.this.notifyHoldListener$6f7cbed2(1, null, null, false);
        RestoreService.this.stopSelf(RestoreService.this.mServiceStartId);
      }
    }
    
    public final boolean tryAgainPackage(String paramString)
    {
      RestoreService.PackageInstallStatus localPackageInstallStatus = (RestoreService.PackageInstallStatus)this.mPackageStatusMap.get(paramString);
      if (localPackageInstallStatus == null) {
        return false;
      }
      if (localPackageInstallStatus.attempts >= ((Integer)G.appRestoreDownloadMaxAttempts.get()).intValue())
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(localPackageInstallStatus.attempts);
        arrayOfObject[1] = paramString;
        FinskyLog.d("Reached limit %d for %s", arrayOfObject);
        return false;
      }
      return true;
    }
    
    final void writeAccountStatus(String paramString)
    {
      String str = Uri.encode(paramString);
      RestoreService.AccountFetchStatus localAccountFetchStatus = (RestoreService.AccountFetchStatus)this.mAccountStatusMap.get(paramString);
      if (localAccountFetchStatus == null)
      {
        this.mAccountStore.delete(str);
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("attempts", Integer.toString(localAccountFetchStatus.attempts));
      localHashMap.put("aid", localAccountFetchStatus.androidId);
      this.mAccountStore.put(str, localHashMap);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.RestoreService
 * JD-Core Version:    0.7.0.1
 */