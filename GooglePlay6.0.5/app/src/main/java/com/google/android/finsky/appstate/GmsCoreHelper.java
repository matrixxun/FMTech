package com.google.android.finsky.appstate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.analytics.PlayStore.NlpRepairStatus;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.PackageManagerHelper.InstallPackageListener;
import com.google.android.finsky.utils.PackageManagerHelper.PackageInstallObserver;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class GmsCoreHelper
{
  private static final String NLP_PACKAGE_NAME;
  private static final String NLP_SHARED_USER_ID = (String)G.nlpSharedUserId.get();
  private static Download sNlpDownload = null;
  private static PlayStore.AppData sNlpLogAppData;
  public final AppStates mAppStates;
  public final Context mContext;
  public final GearheadStateMonitor mGearheadStateMonitor;
  public final Libraries mLibraries;
  
  static
  {
    NLP_PACKAGE_NAME = (String)G.nlpPackageName.get();
    sNlpLogAppData = null;
  }
  
  public GmsCoreHelper(Libraries paramLibraries, AppStates paramAppStates, Context paramContext, GearheadStateMonitor paramGearheadStateMonitor)
  {
    this.mLibraries = paramLibraries;
    this.mAppStates = paramAppStates;
    this.mContext = paramContext;
    this.mGearheadStateMonitor = paramGearheadStateMonitor;
  }
  
  private static boolean checkForNlpDamage(FinskyApp paramFinskyApp, PlayStore.NlpRepairStatus paramNlpRepairStatus)
  {
    if (Build.VERSION.SDK_INT < ((Integer)G.nlpCleanupSdkVersionMin.get()).intValue())
    {
      paramNlpRepairStatus.repairStatus = 4;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
    if (Build.VERSION.SDK_INT > ((Integer)G.nlpCleanupSdkVersionMax.get()).intValue())
    {
      paramNlpRepairStatus.repairStatus = 4;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
    Iterator localIterator = ((LocationManager)paramFinskyApp.getSystemService("location")).getAllProviders().iterator();
    while (localIterator.hasNext()) {
      if ("network".equals((String)localIterator.next()))
      {
        paramNlpRepairStatus.repairStatus = 1;
        paramNlpRepairStatus.hasRepairStatus = true;
        return false;
      }
    }
    PackageInfo localPackageInfo;
    int k;
    try
    {
      String str1 = (String)G.nlpPackageName.get();
      PackageManager localPackageManager = paramFinskyApp.getPackageManager();
      localPackageInfo = localPackageManager.getPackageInfo(str1, 8256);
      int i = localPackageManager.getApplicationEnabledSetting(str1);
      int j = localPackageInfo.applicationInfo.flags;
      paramNlpRepairStatus.flags = j;
      paramNlpRepairStatus.hasFlags = true;
      k = localPackageInfo.versionCode;
      paramNlpRepairStatus.versionCode = k;
      paramNlpRepairStatus.hasVersionCode = true;
      paramNlpRepairStatus.enabled = i;
      paramNlpRepairStatus.hasEnabled = true;
      if ((j & ((Integer)G.nlpCleanupFlagsMask.get()).intValue()) != ((Integer)G.nlpCleanupFlagsSet.get()).intValue())
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(j);
        FinskyLog.d("NLP incorrect flags %d", arrayOfObject3);
        paramNlpRepairStatus.repairStatus = 6;
        paramNlpRepairStatus.hasRepairStatus = true;
        return false;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      FinskyLog.d("NLP package not found", new Object[0]);
      paramNlpRepairStatus.repairStatus = 5;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
    if (k < ((Integer)G.nlpCleanupNlpVersionMin.get()).intValue())
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(k);
      FinskyLog.d("NLP version %d too low", arrayOfObject2);
      paramNlpRepairStatus.repairStatus = 7;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
    if (k > ((Integer)G.nlpCleanupNlpVersionMax.get()).intValue())
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(k);
      FinskyLog.d("NLP version %d too high", arrayOfObject1);
      paramNlpRepairStatus.repairStatus = 7;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
    String str2 = (String)G.nlpCleanupExpectedSignature.get();
    String str3 = (String)G.nlpCleanupExpectedSignatureTestKeys.get();
    String str4 = Sha1Util.secureHash(localPackageInfo.signatures[0].toByteArray());
    if (!str2.equals(str4))
    {
      if (str3.equals(str4))
      {
        paramNlpRepairStatus.foundTestKeys = true;
        paramNlpRepairStatus.hasFoundTestKeys = true;
      }
    }
    else
    {
      FinskyLog.d("NLP package found but reported inactive", new Object[0]);
      paramNlpRepairStatus.repairStatus = 2;
      paramNlpRepairStatus.hasRepairStatus = true;
      return true;
    }
    FinskyLog.d("NLP signature hash mismatch %s", new Object[] { str4 });
    paramNlpRepairStatus.signatureHash = str4;
    if (str4 != null) {}
    for (boolean bool = true;; bool = false)
    {
      paramNlpRepairStatus.hasSignatureHash = bool;
      paramNlpRepairStatus.repairStatus = 8;
      paramNlpRepairStatus.hasRepairStatus = true;
      return false;
    }
  }
  
  public static void cleanupNlp(FinskyApp paramFinskyApp)
  {
    int i = ((Integer)G.nlpCleanupConfigurationId.get()).intValue();
    if (i != ((Integer)FinskyPreferences.nlpCleanupConfigurationId.get()).intValue())
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(i);
      FinskyLog.d("Resetting state because new config id %d", arrayOfObject3);
      FinskyPreferences.nlpCleanupHoldoffUntilBoot.remove();
      FinskyPreferences.nlpCleanupHoldoffAfterInstall.remove();
      FinskyPreferences.nlpCleanupConfigurationId.put(Integer.valueOf(i));
    }
    PlayStore.NlpRepairStatus localNlpRepairStatus = new PlayStore.NlpRepairStatus();
    boolean bool = checkForNlpDamage(paramFinskyApp, localNlpRepairStatus);
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Boolean.valueOf(bool);
    arrayOfObject1[1] = Integer.valueOf(localNlpRepairStatus.repairStatus);
    FinskyLog.d("result=%b type=%d", arrayOfObject1);
    localNlpRepairStatus.holdoffUntilBoot = ((Boolean)FinskyPreferences.nlpCleanupHoldoffUntilBoot.get()).booleanValue();
    localNlpRepairStatus.hasHoldoffUntilBoot = true;
    localNlpRepairStatus.holdoffAfterInstall = ((Boolean)FinskyPreferences.nlpCleanupHoldoffAfterInstall.get()).booleanValue();
    localNlpRepairStatus.hasHoldoffAfterInstall = true;
    int k;
    if (bool)
    {
      if ((((Boolean)FinskyPreferences.nlpCleanupHoldoffUntilBoot.get()).booleanValue()) || (((Boolean)FinskyPreferences.nlpCleanupHoldoffAfterInstall.get()).booleanValue()))
      {
        FinskyLog.d("Skip repair because holdoff set", new Object[0]);
        localNlpRepairStatus.repairStatus = 3;
        localNlpRepairStatus.hasRepairStatus = true;
      }
    }
    else
    {
      if (!((Boolean)G.nlpCleanupLogCommonStatuses.get()).booleanValue())
      {
        int j = localNlpRepairStatus.repairStatus;
        if ((j != 1) && (j != 4)) {
          break label604;
        }
        k = 1;
        label252:
        if (k != 0) {}
      }
      else
      {
        FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
        BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(2);
        localBackgroundEventBuilder.event.nlpRepairStatus = localNlpRepairStatus;
        localFinskyEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      }
      return;
    }
    PlayStore.AppData localAppData = new PlayStore.AppData();
    sNlpLogAppData = localAppData;
    localAppData.oldVersion = localNlpRepairStatus.versionCode;
    sNlpLogAppData.hasOldVersion = true;
    sNlpLogAppData.systemApp = true;
    sNlpLogAppData.hasSystemApp = true;
    sNlpLogAppData.version = localNlpRepairStatus.versionCode;
    sNlpLogAppData.hasVersion = true;
    String str3;
    Object localObject1;
    Object localObject2;
    label404:
    String str4;
    Object localObject3;
    String str5;
    if (localNlpRepairStatus.foundTestKeys)
    {
      String str6 = (String)G.nlpCleanupUrlTestKeys.get();
      String str7 = (String)G.nlpCleanupCookieNameTestKeys.get();
      str3 = (String)G.nlpCleanupCookieValueTestKeys.get();
      localObject1 = str6;
      localObject2 = str7;
      Locale localLocale = Locale.US;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(localNlpRepairStatus.versionCode);
      str4 = String.format(localLocale, (String)localObject1, arrayOfObject2);
      if ((!TextUtils.isEmpty((CharSequence)localObject2)) && (!TextUtils.isEmpty(str3))) {
        break label610;
      }
      localObject3 = null;
      str5 = null;
    }
    for (;;)
    {
      sNlpDownload = new DownloadImpl(str4, "", null, null, (String)localObject3, str5, null, -1L, -1L, null, false, true);
      DownloadQueue localDownloadQueue = paramFinskyApp.mDownloadQueue;
      localDownloadQueue.addListener(new DownloadQueueListener()
      {
        public final void onCancel(Download paramAnonymousDownload) {}
        
        public final void onComplete(Download paramAnonymousDownload)
        {
          if (paramAnonymousDownload != GmsCoreHelper.sNlpDownload) {
            return;
          }
          this.val$eventLogger.logBackgroundEvent(102, this.val$packageName, null, 0, null, GmsCoreHelper.sNlpLogAppData);
          FinskyLog.d("NLP fixer download completed", new Object[0]);
          GmsCoreHelper.access$400(paramAnonymousDownload, this.val$packageName);
        }
        
        public final void onError(Download paramAnonymousDownload, int paramAnonymousInt)
        {
          if (paramAnonymousDownload != GmsCoreHelper.sNlpDownload) {
            return;
          }
          this.val$eventLogger.logBackgroundEvent(104, this.val$packageName, null, paramAnonymousInt, null, GmsCoreHelper.sNlpLogAppData);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(paramAnonymousInt);
          FinskyLog.e("NLP fixer download failed with HTTP status: %d", arrayOfObject);
        }
        
        public final void onNotificationClicked(Download paramAnonymousDownload) {}
        
        public final void onProgress(Download paramAnonymousDownload, DownloadProgress paramAnonymousDownloadProgress) {}
        
        public final void onStart(Download paramAnonymousDownload)
        {
          if (paramAnonymousDownload != GmsCoreHelper.sNlpDownload) {
            return;
          }
          this.val$eventLogger.logBackgroundEvent(101, this.val$packageName, null, 0, null, GmsCoreHelper.sNlpLogAppData);
          FinskyLog.d("NLP fixer download started", new Object[0]);
        }
      });
      localDownloadQueue.add(sNlpDownload);
      paramFinskyApp.getEventLogger().logBackgroundEvent(100, (String)G.nlpPackageName.get(), null, 0, null, sNlpLogAppData);
      break;
      String str1 = (String)G.nlpCleanupUrl.get();
      String str2 = (String)G.nlpCleanupCookieName.get();
      str3 = (String)G.nlpCleanupCookieValue.get();
      localObject1 = str1;
      localObject2 = str2;
      break label404;
      label604:
      k = 0;
      break label252;
      label610:
      str5 = str3;
      localObject3 = localObject2;
    }
  }
  
  public static void insertGmsCore(List<String> paramList)
  {
    if (isAutoUpdateEnabled())
    {
      if (!paramList.contains("com.google.android.gms")) {
        paramList.add("com.google.android.gms");
      }
      return;
    }
    FinskyLog.d("GMS Core auto updating is disabled", new Object[0]);
  }
  
  public static boolean isAutoUpdateEnabled()
  {
    return ((Boolean)G.gmsCoreAutoUpdateEnabled.get()).booleanValue();
  }
  
  public static boolean isGmsCore(Document paramDocument)
  {
    AppDetails localAppDetails = paramDocument.getAppDetails();
    if (localAppDetails == null) {
      return false;
    }
    return "com.google.android.gms".equals(localAppDetails.packageName);
  }
  
  public static boolean isGmsCore(String paramString)
  {
    return "com.google.android.gms".equals(paramString);
  }
  
  public static void onBootCompleted()
  {
    FinskyPreferences.nlpCleanupHoldoffUntilBoot.remove();
  }
  
  public static void removeGmsCore(List<String> paramList)
  {
    paramList.remove("com.google.android.gms");
  }
  
  public static final class GMSCoreNotifier
    implements PackageMonitorReceiver.PackageStatusListener
  {
    private final Context mContext;
    
    public GMSCoreNotifier(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    private static void reconnectNlp(Context paramContext, String paramString)
    {
      if (Build.VERSION.SDK_INT < ((Integer)G.nlpReinstallSdkVersionMin.get()).intValue()) {}
      for (;;)
      {
        return;
        if ((Build.VERSION.SDK_INT > ((Integer)G.nlpReinstallSdkVersionMax.get()).intValue()) || (GmsCoreHelper.NLP_PACKAGE_NAME.equals(paramString)) || (TextUtils.isEmpty(GmsCoreHelper.NLP_SHARED_USER_ID))) {
          continue;
        }
        try
        {
          PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
          boolean bool = GmsCoreHelper.NLP_SHARED_USER_ID.equals(localPackageInfo.sharedUserId);
          if (!bool) {
            continue;
          }
          FinskyLog.d("Found shared UID match between NLP and %s", new Object[] { paramString });
          try
          {
            Uri localUri = Uri.fromFile(new File(paramContext.getPackageManager().getApplicationInfo(GmsCoreHelper.NLP_PACKAGE_NAME, 0).sourceDir));
            PackageManagerHelper.PackageInstallObserver local2 = new PackageManagerHelper.PackageInstallObserver()
            {
              public final void packageInstalled(String paramAnonymousString, int paramAnonymousInt)
              {
                if (paramAnonymousString == null) {
                  paramAnonymousString = GmsCoreHelper.NLP_PACKAGE_NAME;
                }
                Object[] arrayOfObject = new Object[2];
                arrayOfObject[0] = Integer.valueOf(paramAnonymousInt);
                arrayOfObject[1] = paramAnonymousString;
                FinskyLog.d("Result %d re-installing %s", arrayOfObject);
              }
            };
            PackageManagerHelper.installPackage(FinskyApp.get(), localUri, local2, 2);
            return;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException2)
          {
            FinskyLog.w("NameNotFoundException getting info for " + GmsCoreHelper.NLP_PACKAGE_NAME, new Object[0]);
            return;
          }
          return;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException1)
        {
          FinskyLog.w("NameNotFoundException getting info for %s", new Object[] { paramString });
        }
      }
    }
    
    private void setAutoUpdate(final String paramString1, final int paramInt, final String paramString2)
    {
      final AppStates localAppStates = FinskyApp.get().mAppStates;
      localAppStates.load(new Runnable()
      {
        public final void run()
        {
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = paramString1;
          arrayOfObject[1] = Integer.valueOf(paramInt);
          arrayOfObject[2] = paramString2;
          FinskyLog.d("Set autoupdate of %s to %d (%s)", arrayOfObject);
          localAppStates.mStateStore.setAutoUpdate(paramString1, paramInt);
        }
      });
    }
    
    public final void onPackageAdded(String paramString)
    {
      if (GmsCoreHelper.isGmsCore(paramString))
      {
        Intent localIntent = new Intent("com.google.android.gms.GMS_UPDATED");
        localIntent.setPackage("com.google.android.gms");
        this.mContext.sendBroadcast(localIntent);
      }
      for (;;)
      {
        try
        {
          ApplicationInfo localApplicationInfo = this.mContext.getPackageManager().getApplicationInfo(paramString, 0);
          int i = localApplicationInfo.flags;
          if ((i & 0x1) != 0)
          {
            j = 1;
            int k = i & 0x80;
            int m = 0;
            if (k != 0) {
              m = 1;
            }
            if ((j == 0) || (m != 0)) {
              break label131;
            }
            setAutoUpdate(paramString, 2, "downgrade");
            reconnectNlp(this.mContext, paramString);
            return;
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          FinskyLog.w("NameNotFoundException getting info for %s", new Object[] { paramString });
          return;
        }
        int j = 0;
        continue;
        label131:
        setAutoUpdate(paramString, 1, "install/update");
      }
    }
    
    public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
    
    public final void onPackageChanged(String paramString)
    {
      reconnectNlp(this.mContext, paramString);
    }
    
    public final void onPackageFirstLaunch(String paramString) {}
    
    public final void onPackageRemoved(String paramString, boolean paramBoolean)
    {
      if ((!paramBoolean) && (GmsCoreHelper.isGmsCore(paramString))) {
        setAutoUpdate(paramString, 2, "removed");
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.GmsCoreHelper
 * JD-Core Version:    0.7.0.1
 */