package com.google.android.finsky.services;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiBulkPreregistrationDetails;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.autoupdate.UpdateChecker.AutoUpdateCompletionStatusListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.gearhead.GearheadStateMonitor;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.library.SQLiteLibrary;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.SelfUpdateResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.DeviceConfigurationHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GetSelfUpdateHelper;
import com.google.android.finsky.utils.GetSelfUpdateHelper.Listener;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.GetTocHelper.Listener;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.finsky.utils.PreregistrationHelper;
import com.google.android.finsky.utils.PreregistrationHelper.3;
import com.google.android.finsky.utils.PreregistrationHelper.4;
import com.google.android.finsky.utils.PreregistrationHelper.5;
import com.google.android.finsky.utils.PreregistrationHelper.PreregistrationHygieneListener;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.finsky.wear.WearSupportService;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.config.GservicesValue;

public class DailyHygiene
  extends Service
{
  public static final long BOOT_DELAY_MS;
  private static final long HOLDOFF_INTERVAL_MS;
  public static final long IMMEDIATE_DELAY_MS = ((Long)G.dailyHygieneImmediateDelayMs.get()).longValue();
  private static final float INITIAL_SCHEDULE_JITTER_FACTOR = ((Float)G.dailyHygieneInitialJitterFactor.get()).floatValue();
  private static final long REGULAR_INTERVAL_MS;
  private static final float RESCHEDULE_JITTER_FACTOR = ((Float)G.dailyHygieneJitterFactor.get()).floatValue();
  private static final int[] RETRY_INTERVALS = { 1, 3, 9, 27, 81 };
  private static final long RETRY_INTERVAL_MS;
  private static final Intent UPDATE_CHECK = new Intent(FinskyApp.get(), DailyHygiene.class);
  private static boolean sHygieneInProgress = false;
  
  static
  {
    BOOT_DELAY_MS = ((Long)G.dailyHygieneBootDelayMs.get()).longValue();
    REGULAR_INTERVAL_MS = ((Long)G.dailyHygieneRegularIntervalMs.get()).longValue();
    HOLDOFF_INTERVAL_MS = ((Long)G.dailyHygieneHoldoffIntervalMs.get()).longValue();
    RETRY_INTERVAL_MS = ((Long)G.dailyHygieneRetryIntervalMs.get()).longValue();
  }
  
  private void beginSelfUpdateCheck()
  {
    if ((((Boolean)G.disableSidewinderSelfupdate.get()).booleanValue()) && (GooglePlayServicesUtil.isSidewinderDevice(FinskyApp.get())))
    {
      loadAndReplicateAndContinue();
      return;
    }
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    if (localDfeApi == null)
    {
      loadAndReplicateAndContinue();
      return;
    }
    final int i = FinskyApp.get().getVersionCode();
    final PlayStore.AppData localAppData = new PlayStore.AppData();
    localAppData.oldVersion = 0;
    localAppData.hasOldVersion = true;
    localAppData.systemApp = true;
    localAppData.hasSystemApp = true;
    final FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    final String str = localDfeApi.getAccountName();
    GetSelfUpdateHelper.getSelfUpdate(localDfeApi, DeviceConfigurationHelper.get(), new GetSelfUpdateHelper.Listener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        localFinskyEventLog.logBackgroundEvent(119, FinskyApp.get().getPackageName(), null, 0, paramAnonymousVolleyError.getClass().getSimpleName(), localAppData);
        DailyHygiene.this.loadAndReplicateAndContinue();
      }
      
      public final void onResponse(SelfUpdateResponse paramAnonymousSelfUpdateResponse)
      {
        SelfUpdateScheduler localSelfUpdateScheduler = FinskyApp.get().mSelfUpdateScheduler;
        int i = SelfUpdateScheduler.getNewVersion(paramAnonymousSelfUpdateResponse);
        if (i > 0)
        {
          localAppData.version = i;
          localAppData.hasVersion = true;
        }
        localFinskyEventLog.logBackgroundEvent(119, FinskyApp.get().getPackageName(), null, 0, null, localAppData);
        if ((localSelfUpdateScheduler.checkForSelfUpdate(i, str)) && ((((Boolean)G.dailyHygieneHoldoffDuringSelfUpdate.get()).booleanValue()) || (DailyHygiene.emergencyDailyHygiene(i))))
        {
          FinskyLog.d("Self-update started or running - defer daily hygiene", new Object[0]);
          DailyHygiene.access$200$75d0b98(DailyHygiene.this);
          return;
        }
        DailyHygiene.this.loadAndReplicateAndContinue();
      }
    });
  }
  
  public static void cancelHoldoffPeriod()
  {
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.dailyHygieneHoldoffComplete;
    if (((Boolean)localSharedPreference.get()).booleanValue()) {
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Boolean.valueOf(isProvisioned());
    FinskyLog.d("Canceling holdoff. Provisioned=%b", arrayOfObject);
    localSharedPreference.put(Boolean.valueOf(true));
  }
  
  private static boolean emergencyDailyHygiene(int paramInt)
  {
    int i = ((Integer)G.dailyHygieneImmediateRunVersionCodeLow.get()).intValue();
    int j = ((Integer)G.dailyHygieneImmediateRunVersionCodeHigh.get()).intValue();
    if ((i <= 0) || (j <= 0)) {
      return false;
    }
    if ((paramInt >= i) && (paramInt <= j))
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = Integer.valueOf(paramInt);
      arrayOfObject[2] = Integer.valueOf(j);
      FinskyLog.w("Scheduling emergency daily hygiene, %d <= %d <= %d", arrayOfObject);
      return true;
    }
    return false;
  }
  
  public static void goMakeHygieneIfDirty(Context paramContext, int paramInt)
  {
    int i;
    if (((Long)FinskyPreferences.dailyHygieneLastSuccessMs.get()).longValue() < System.currentTimeMillis() - REGULAR_INTERVAL_MS)
    {
      i = 1;
      if ((i == 0) && (!emergencyDailyHygiene(paramInt)))
      {
        int j = ((Integer)FinskyPreferences.lastReplicatedDatabaseVersion.get()).intValue();
        SQLiteLibrary.getVersion();
        if (j == 10) {
          break label90;
        }
      }
    }
    label90:
    for (int k = 1;; k = 0)
    {
      if (k == 0) {
        break label96;
      }
      FinskyLog.d("Dirty, need more hygiene.", new Object[0]);
      schedule(paramContext, IMMEDIATE_DELAY_MS);
      return;
      i = 0;
      break;
    }
    label96:
    FinskyLog.d("No need to run daily hygiene.", new Object[0]);
  }
  
  public static boolean isHygieneInProgress()
  {
    return sHygieneInProgress;
  }
  
  @TargetApi(17)
  public static boolean isProvisioned()
  {
    ContentResolver localContentResolver = FinskyApp.get().getContentResolver();
    if (Build.VERSION.SDK_INT >= 17) {}
    for (int i = Settings.Global.getInt(localContentResolver, "device_provisioned", 0);; i = Settings.Secure.getInt(localContentResolver, "device_provisioned", 0))
    {
      boolean bool = false;
      if (i != 0) {
        bool = true;
      }
      return bool;
    }
  }
  
  private static long jitter(long paramLong, float paramFloat)
  {
    return ((1.0F + paramFloat * (2.0F * ((float)Math.random() - 0.5F))) * (float)paramLong);
  }
  
  private void loadAndReplicateAndContinue()
  {
    if (emergencyDailyHygiene(FinskyApp.get().getVersionCode()))
    {
      reschedule(true);
      return;
    }
    Runnable local3 = new Runnable()
    {
      private int mLoaded;
      
      public final void run()
      {
        this.mLoaded = (1 + this.mLoaded);
        if (this.mLoaded == 4) {
          DailyHygiene.access$400(DailyHygiene.this);
        }
      }
    };
    FinskyApp.get().mLibraries.load(local3);
    FinskyApp.get().mLibraryReplicators.replicateAllAccounts(local3, "daily-hygiene");
    FinskyApp.get().mAppStates.load(local3);
    GearheadStateMonitor.getInstance().initialize(local3);
  }
  
  private void reschedule(boolean paramBoolean)
  {
    long l;
    if (paramBoolean) {
      if (((Long)FinskyPreferences.dailyHygieneLastSuccessMs.get()).longValue() == 0L)
      {
        l = jitter(REGULAR_INTERVAL_MS, INITIAL_SCHEDULE_JITTER_FACTOR);
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Float.valueOf((float)l / 3600000.0F);
        FinskyLog.d("Scheduling first run in %1.1f hours", arrayOfObject3);
        FinskyPreferences.dailyHygieneLastSuccessMs.put(Long.valueOf(System.currentTimeMillis()));
        FinskyPreferences.lastReplicatedDatabaseVersion.put(Integer.valueOf(SQLiteLibrary.getVersion()));
      }
    }
    for (;;)
    {
      schedule(this, l);
      sHygieneInProgress = false;
      stopSelf();
      return;
      l = jitter(REGULAR_INTERVAL_MS, RESCHEDULE_JITTER_FACTOR);
      break;
      int i = 1 + ((Integer)FinskyPreferences.dailyHygieneFailedCount.get()).intValue();
      if (i <= RETRY_INTERVALS.length)
      {
        l = jitter(RETRY_INTERVALS[(i - 1)] * RETRY_INTERVAL_MS, RESCHEDULE_JITTER_FACTOR);
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Long.valueOf(l / 60000L);
        arrayOfObject2[1] = Integer.valueOf(i);
        FinskyLog.d("Scheduling new run in %d minutes (failures=%d)", arrayOfObject2);
        FinskyPreferences.dailyHygieneFailedCount.put(Integer.valueOf(i));
      }
      else
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        FinskyLog.d("Giving up. (failures=%d)", arrayOfObject1);
        FinskyPreferences.dailyHygieneFailedCount.remove();
        l = jitter(REGULAR_INTERVAL_MS, RESCHEDULE_JITTER_FACTOR);
      }
    }
  }
  
  public static void schedule(Context paramContext, long paramLong)
  {
    ((AlarmManager)paramContext.getSystemService("alarm")).set(0, paramLong + System.currentTimeMillis(), PendingIntent.getService(paramContext, 0, UPDATE_CHECK, 0));
  }
  
  private void startWearService(final boolean paramBoolean)
  {
    WearSupportService.startHygiene(FinskyApp.get());
    final FinskyApp localFinskyApp = FinskyApp.get();
    final PreregistrationHelper localPreregistrationHelper = localFinskyApp.mPreregistrationHelper;
    PreregistrationHelper.PreregistrationHygieneListener local5 = new PreregistrationHelper.PreregistrationHygieneListener()
    {
      public final void onFoundNewRelease(Document paramAnonymousDocument, String paramAnonymousString)
      {
        PreregistrationHelper localPreregistrationHelper = localPreregistrationHelper;
        Context localContext = DailyHygiene.this.getBaseContext();
        Notifier localNotifier = localFinskyApp.mNotificationHelper;
        BitmapLoader localBitmapLoader = localFinskyApp.mBitmapLoader;
        int i = localContext.getResources().getDimensionPixelSize(2131493346);
        Common.Image localImage = ThumbnailUtils.getImageFromDocument(paramAnonymousDocument, i, i, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
        if (localImage == null) {
          localNotifier.showPreregistrationReleasedMessage(paramAnonymousDocument, paramAnonymousString, null);
        }
        PreregistrationHelper.3 local3;
        BitmapLoader.BitmapContainer localBitmapContainer;
        do
        {
          return;
          local3 = new PreregistrationHelper.3(localPreregistrationHelper, localNotifier, paramAnonymousDocument, paramAnonymousString);
          localBitmapContainer = localBitmapLoader.get$6721551b(localImage.imageUrl, i, i, local3);
        } while (localBitmapContainer.mBitmap == null);
        local3.onResponse(localBitmapContainer);
        localBitmapContainer.cancelRequest();
      }
      
      public final void onPreregistrationHygieneFinished(boolean paramAnonymousBoolean)
      {
        DailyHygiene localDailyHygiene = DailyHygiene.this;
        if ((paramBoolean) && (paramAnonymousBoolean)) {}
        for (boolean bool = true;; bool = false)
        {
          DailyHygiene.access$600(localDailyHygiene, bool);
          return;
        }
      }
    };
    Context localContext = getBaseContext();
    if (FinskyApp.get().getExperiments().isEnabled(12603110L))
    {
      local5.onPreregistrationHygieneFinished(true);
      return;
    }
    if (!localPreregistrationHelper.mLibraries.isLoaded())
    {
      FinskyLog.wtf("Require loaded libraries to perform pre-registration hygiene.", new Object[0]);
      local5.onPreregistrationHygieneFinished(true);
      return;
    }
    MultiBulkPreregistrationDetails localMultiBulkPreregistrationDetails = new MultiBulkPreregistrationDetails();
    localMultiBulkPreregistrationDetails.addDataChangedListener(new PreregistrationHelper.4(localPreregistrationHelper, localMultiBulkPreregistrationDetails, local5, localContext));
    localMultiBulkPreregistrationDetails.addErrorListener(new PreregistrationHelper.5(localPreregistrationHelper, local5));
    localMultiBulkPreregistrationDetails.addRequests(localPreregistrationHelper.mLibraries);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.dailyHygieneHoldoffComplete;
    int i;
    if (((Boolean)localSharedPreference.get()).booleanValue()) {
      i = 0;
    }
    while (i != 0)
    {
      schedule(this, HOLDOFF_INTERVAL_MS);
      return 2;
      if (isProvisioned())
      {
        FinskyLog.d("No holdoff required - already provisioned", new Object[0]);
        localSharedPreference.put(Boolean.valueOf(true));
        i = 0;
      }
      else if (((Long)G.dailyHygieneProvisionHoldoffMaxMs.get()).longValue() <= 0L)
      {
        FinskyLog.d("No holdoff required - disabled", new Object[0]);
        localSharedPreference.put(Boolean.valueOf(true));
        i = 0;
      }
      else
      {
        FinskyLog.d("DailyHygiene holdoff continue", new Object[0]);
        i = 1;
      }
    }
    FinskyLog.d("Beginning daily hygiene", new Object[0]);
    sHygieneInProgress = true;
    if (FinskyApp.get().getExperiments().isEnabled(12603142L))
    {
      FinskyLog.w("Experiments preload disabled by kill-switch", new Object[0]);
      beginSelfUpdateCheck();
      return 2;
    }
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    if ((localDfeApi == null) || (!((Boolean)G.dailyHygienePreloadExperiments.get()).booleanValue()))
    {
      beginSelfUpdateCheck();
      return 2;
    }
    GetTocHelper.getToc(localDfeApi, false, new GetTocHelper.Listener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.w("Unable to preload experiments: %s", new Object[] { paramAnonymousVolleyError });
        DailyHygiene.this.beginSelfUpdateCheck();
      }
      
      public final void onResponse(Toc.TocResponse paramAnonymousTocResponse)
      {
        DailyHygiene.this.beginSelfUpdateCheck();
      }
    });
    return 2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.DailyHygiene
 * JD-Core Version:    0.7.0.1
 */