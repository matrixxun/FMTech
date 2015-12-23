package com.google.android.finsky;

import android.accounts.Account;
import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.SearchRecentSuggestions;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.android.vending.AssetBrowserActivity;
import com.android.vending.VendingBackupAgent;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.NoCache;
import com.google.android.common.http.UrlRules;
import com.google.android.common.http.UrlRules.Rule;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.StubAnalytics;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeApiImpl;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.DfeNotificationManager;
import com.google.android.finsky.api.DfeRequest;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.InstrumentedBitmapLoader;
import com.google.android.finsky.api.NetworkStateInfo;
import com.google.android.finsky.api.RequestUtils;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStatesReplicator;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.GmsCoreHelper.GMSCoreNotifier;
import com.google.android.finsky.appstate.InMemoryInstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.MigrationAsyncTask;
import com.google.android.finsky.appstate.PackageManagerRepository;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.SQLiteInstallerDataStore;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.creditcard.EscrowRequest;
import com.google.android.finsky.billing.iab.PendingNotificationsService;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.download.DownloadBroadcastReceiver;
import com.google.android.finsky.download.DownloadManagerFactory;
import com.google.android.finsky.download.DownloadNetworkQualityListener;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueImpl;
import com.google.android.finsky.download.obb.ObbPackageTracker;
import com.google.android.finsky.experiments.ExperimentsChangeHandler;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.experiments.FinskyExperiments.TargetsChangeListener;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.PackageInstallerFactory;
import com.google.android.finsky.installer.PackageInstallerImpl;
import com.google.android.finsky.installer.PackageInstallerLegacyImpl;
import com.google.android.finsky.library.AccountsProvider;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.library.LibraryReplicatorsImpl;
import com.google.android.finsky.library.SQLiteLibrary;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.protos.VendingProtos.PendingNotificationsProto;
import com.google.android.finsky.receivers.AccountsChangedReceiver;
import com.google.android.finsky.receivers.BootCompletedReceiver;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.InstallerImpl;
import com.google.android.finsky.receivers.NetworkStateChangedReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.RegisteredReceiver;
import com.google.android.finsky.receivers.RemoveAssetReceiver;
import com.google.android.finsky.services.ContentSyncService;
import com.google.android.finsky.services.DailyHygiene;
import com.google.android.finsky.services.RestoreService;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.DenyAllNetwork;
import com.google.android.finsky.utils.DeviceManagementHelper;
import com.google.android.finsky.utils.ExternalReferrer.ReferrerRebroadcaster;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GELInstallerListener;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.NotificationManager;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PermissionPolicies.PermissionPolicyNotifier;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.PreregistrationHelper;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.TimeProvider.SystemTimeProvider;
import com.google.android.finsky.utils.UninstallRefundTracker;
import com.google.android.finsky.utils.UninstallSubscriptionsTracker;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.Users;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.widget.WidgetUtils;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.gms.ads.identifier.ElegantAdvertisingIdHelper;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.dfe.api.PlayDfeApiContext;
import com.google.android.play.dfe.api.PlayDfeApiImpl;
import com.google.android.play.dfe.api.PlayDfeApiProvider;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.RequestListenerWrapper;
import com.google.android.play.image.BitmapLoader.RequestTimeoutProvider;
import com.google.android.play.image.FifeUrlUtils;
import com.google.android.play.image.TentativeGcRunner;
import com.google.android.play.utils.NetworkInfoUtil.NetworkInfoCache;
import com.google.android.play.utils.PlayCorpusUtils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;
import com.google.android.vending.remoting.api.PendingNotificationsHandler;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApiFactory;
import com.google.android.vending.remoting.api.VendingRequest;
import com.google.android.vending.verifier.PackageVerificationService;
import com.google.android.volley.GoogleHttpClient;
import com.google.android.volley.GoogleHttpClientStack;
import com.google.android.volley.ok.GoogleOkHttpStack;
import com.google.android.volley.ok.GoogleUrlRewriter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FinskyApp
  extends Application
{
  private static final Class<?>[] RECEIVERS = { BootCompletedReceiver.class, PackageMonitorReceiver.RegisteredReceiver.class, AccountsChangedReceiver.class };
  private static FinskyApp sInstance;
  public AccountsProvider mAccountsProvider;
  public AdIdProvider mAdIdProvider;
  public Analytics mAnalytics;
  private VendingApiFactory mApiFactory;
  public AppStates mAppStates;
  public AppStatesReplicator mAppStatesReplicator;
  private NewsstandArticleHandler mArticleHandler;
  public Cache mBitmapCache;
  public BitmapLoader mBitmapLoader;
  public RequestQueue mBitmapRequestQueue;
  public Cache mCache;
  private final Map<String, ClientMutationCache> mClientMutationCaches = new HashMap();
  public Account mCurrentAccount;
  private DfeApi mDfeApiNonAuthenticated;
  private DfeApiProvider mDfeApiProvider;
  public final Map<String, DfeApi> mDfeApis = new HashMap();
  public DfeNotificationManager mDfeNotificationManager;
  public DownloadQueue mDownloadQueue;
  private boolean mEventLoggerInUnitTestMode = false;
  private final Map<String, FinskyEventLog> mEventLoggers = new HashMap();
  private Map<String, FinskyExperiments> mExperimentsByAccountName = new HashMap();
  private ExperimentsChangeHandler mExperimentsChangeHandler;
  public InstallPolicies mInstallPolicies;
  public Installer mInstaller;
  public InstallerDataStore mInstallerDataStore;
  public Libraries mLibraries;
  public LibraryReplicators mLibraryReplicators;
  public Notifier mNotificationHelper;
  public PackageMonitorReceiver mPackageMonitorReceiver;
  public PackageStateRepository mPackageStateRepository;
  public final PendingNotificationsHandler mPendingNotificationsHandler = new PendingNotificationsHandler()
  {
    public final boolean handlePendingNotifications(Context paramAnonymousContext, String paramAnonymousString, VendingProtos.PendingNotificationsProto paramAnonymousPendingNotificationsProto, boolean paramAnonymousBoolean)
    {
      return PendingNotificationsService.handlePendingNotifications(paramAnonymousContext, paramAnonymousString, paramAnonymousPendingNotificationsProto, true);
    }
  };
  private PlayDfeApiProvider mPlayDfeApiProvider;
  private final Map<String, PlayDfeApi> mPlayDfeApis = new HashMap();
  public PreregistrationHelper mPreregistrationHelper;
  public SearchRecentSuggestions mRecentSuggestions;
  public RequestQueue mRequestQueue;
  public SelfUpdateScheduler mSelfUpdateScheduler;
  public DfeToc mToc;
  public Users mUsers;
  public int mVersionCodeOfLastRun;
  
  public FinskyApp()
  {
    sInstance = this;
  }
  
  private Network createNetwork()
  {
    if (Utils.isBackgroundDataEnabled(this))
    {
      FinskyExperiments localFinskyExperiments = getExperiments();
      boolean bool = localFinskyExperiments.isEnabled(12603642L);
      int i;
      if ((localFinskyExperiments.isEnabled(12602748L)) || (localFinskyExperiments.isEnabled(12604235L)) || (localFinskyExperiments.isEnabled(12604236L))) {
        i = 1;
      }
      OkHttpClient localOkHttpClient;
      while ((GooglePlayServicesUtil.isSidewinderDevice(this)) || (((bool) || (i != 0)) && (((Boolean)G.enableOkHttp.get()).booleanValue())))
      {
        Protocol[] arrayOfProtocol = new Protocol[1];
        arrayOfProtocol[0] = Protocol.HTTP_1_1;
        ArrayList localArrayList = Lists.newArrayList(arrayOfProtocol);
        if (!bool) {
          localArrayList.add(Protocol.SPDY_3);
        }
        localOkHttpClient = new OkHttpClient();
        List localList = Util.immutableList(localArrayList);
        if (!localList.contains(Protocol.HTTP_1_1))
        {
          throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + localList);
          i = 0;
        }
        else
        {
          if (localList.contains(Protocol.HTTP_1_0)) {
            throw new IllegalArgumentException("protocols must not contain http/1.0: " + localList);
          }
          if (localList.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
          }
          localOkHttpClient.protocols = Util.immutableList(localList);
          localOkHttpClient.followRedirects = false;
        }
      }
      for (Object localObject = new GoogleOkHttpStack(this, localOkHttpClient, new GoogleUrlRewriter(this), null, ((Boolean)G.enableSensitiveLogging.get()).booleanValue());; localObject = new GoogleHttpClientStack(this, ((Boolean)G.enableSensitiveLogging.get()).booleanValue())) {
        return new BasicNetwork((HttpStack)localObject, new ByteArrayPool(1024 * ((Integer)G.volleyBufferPoolSizeKb.get()).intValue()));
      }
    }
    return new DenyAllNetwork();
  }
  
  public static void drain(RequestQueue paramRequestQueue)
  {
    drain(paramRequestQueue, paramRequestQueue.mSequenceGenerator.incrementAndGet());
  }
  
  private static void drain(RequestQueue paramRequestQueue, int paramInt)
  {
    paramRequestQueue.cancelAll(new RequestQueue.RequestFilter()
    {
      public final boolean apply(Request<?> paramAnonymousRequest)
      {
        if ((paramAnonymousRequest instanceof DfeRequest))
        {
          if (((DfeRequest)paramAnonymousRequest).mAvoidBulkCancel) {
            return false;
          }
        }
        else if ((paramAnonymousRequest instanceof VendingRequest))
        {
          if (((VendingRequest)paramAnonymousRequest).mAvoidBulkCancel) {
            return false;
          }
        }
        else if ((paramAnonymousRequest instanceof EscrowRequest)) {
          return false;
        }
        return paramAnonymousRequest.getSequence() < this.val$seq;
      }
    });
  }
  
  private void enableReceivers()
  {
    PackageManager localPackageManager = getPackageManager();
    Class[] arrayOfClass = RECEIVERS;
    int i = arrayOfClass.length;
    int j = 0;
    for (;;)
    {
      if (j < i)
      {
        Class localClass = arrayOfClass[j];
        try
        {
          ComponentName localComponentName = new ComponentName(this, localClass);
          if (localPackageManager.getComponentEnabledSetting(localComponentName) != 1) {
            localPackageManager.setComponentEnabledSetting(localComponentName, 1, 1);
          }
          j++;
        }
        catch (SecurityException localSecurityException)
        {
          for (;;)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localClass.getSimpleName();
            FinskyLog.wtf("Unable to enable %s", arrayOfObject);
          }
        }
      }
    }
  }
  
  public static FinskyApp get()
  {
    return sInstance;
  }
  
  private File getCacheDir(String paramString)
  {
    File localFile = new File(getCacheDir(), paramString);
    localFile.mkdirs();
    return localFile;
  }
  
  private static boolean updateWidgetUrl(PreferenceFile.SharedPreference<String> paramSharedPreference, String paramString)
  {
    if (!TextUtils.equals((CharSequence)paramSharedPreference.get(), paramString))
    {
      paramSharedPreference.put(paramString);
      return true;
    }
    return false;
  }
  
  public final void clearCacheAsync(final Runnable paramRunnable)
  {
    Runnable local11 = new Runnable()
    {
      private int mClearedCount = 0;
      
      public final void run()
      {
        this.mClearedCount = (1 + this.mClearedCount);
        if ((this.mClearedCount == 2) && (paramRunnable != null)) {
          paramRunnable.run();
        }
      }
    };
    this.mRequestQueue.add(new ClearCacheRequest(this.mCache, local11));
    this.mBitmapRequestQueue.add(new ClearCacheRequest(this.mBitmapCache, local11));
  }
  
  public final void clearRequestCacheAsync(Runnable paramRunnable)
  {
    this.mRequestQueue.add(new ClearCacheRequest(this.mCache, paramRunnable));
  }
  
  public final void drainAllRequests(int paramInt1, int paramInt2)
  {
    drain(this.mRequestQueue, paramInt1);
    BitmapLoader localBitmapLoader = sInstance.mBitmapLoader;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = localBitmapLoader.mInFlightRequests.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if ((((BitmapLoader.RequestListenerWrapper)localBitmapLoader.mInFlightRequests.get(str2)).request == null) || (((BitmapLoader.RequestListenerWrapper)localBitmapLoader.mInFlightRequests.get(str2)).request.getSequence() < paramInt2)) {
        localArrayList.add(str2);
      }
    }
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      localBitmapLoader.mInFlightRequests.remove(str1);
    }
  }
  
  public final Analytics getAnalytics(String paramString)
  {
    return new DfeAnalytics(new Handler(Looper.getMainLooper()), getDfeApi(paramString));
  }
  
  public final NewsstandArticleHandler getArticleHandler()
  {
    if (this.mArticleHandler == null) {
      this.mArticleHandler = new NewsstandArticleHandler();
    }
    return this.mArticleHandler;
  }
  
  public final ClientMutationCache getClientMutationCache(String paramString)
  {
    try
    {
      if (TextUtils.isEmpty(paramString)) {
        FinskyLog.wtf("No account specified.", new Object[0]);
      }
      ClientMutationCache localClientMutationCache = (ClientMutationCache)this.mClientMutationCaches.get(paramString);
      if (localClientMutationCache == null)
      {
        localClientMutationCache = new ClientMutationCache(getApplicationContext(), paramString);
        this.mClientMutationCaches.put(paramString, localClientMutationCache);
      }
      return localClientMutationCache;
    }
    finally {}
  }
  
  public final Account getCurrentAccount()
  {
    if (this.mCurrentAccount == null)
    {
      Account localAccount = AccountHandler.getAccountFromPreferences(this, FinskyPreferences.currentAccount);
      if (localAccount == null)
      {
        FinskyLog.w("No account configured on this device.", new Object[0]);
        return null;
      }
      this.mCurrentAccount = localAccount;
    }
    return this.mCurrentAccount;
  }
  
  public final String getCurrentAccountName()
  {
    Account localAccount = getCurrentAccount();
    if (localAccount != null) {
      return localAccount.name;
    }
    return null;
  }
  
  public final DfeApi getDfeApi(String paramString)
  {
    if (paramString == null)
    {
      paramString = getCurrentAccountName();
      if (paramString == null)
      {
        FinskyLog.w("No account configured on this device.", new Object[0]);
        return null;
      }
    }
    synchronized (this.mDfeApis)
    {
      Object localObject2 = (DfeApi)this.mDfeApis.get(paramString);
      FinskyExperiments localFinskyExperiments = getExperiments(paramString);
      if (localObject2 == null)
      {
        int i = ContentLevel.importFromSettings(this).getDfeValue();
        String str1 = (String)FinskyPreferences.contentFilters2.get();
        Cache localCache = this.mCache;
        DfeNotificationManager localDfeNotificationManager = this.mDfeNotificationManager;
        AdIdProvider localAdIdProvider = this.mAdIdProvider;
        FinskyEventLog localFinskyEventLog = getEventLogger(paramString);
        DeviceManagementHelper localDeviceManagementHelper = DeviceManagementHelper.getInstance(this);
        AndroidAuthenticator localAndroidAuthenticator = new AndroidAuthenticator(this, AccountHandler.findAccount(paramString, this), (String)DfeApiConfig.authTokenType.get());
        TelephonyManager localTelephonyManager = (TelephonyManager)getSystemService("phone");
        String str2 = Long.toHexString(((Long)DfeApiConfig.androidId.get()).longValue());
        String str3 = DfeApiContext.makeUserAgentString(this);
        DfeApiContext localDfeApiContext = new DfeApiContext(this, localAndroidAuthenticator, localCache, localFinskyExperiments, localDfeNotificationManager, Locale.getDefault(), localTelephonyManager.getSimOperator(), (String)DfeApiConfig.clientId.get(), (String)DfeApiConfig.loggingId.get(), i, str1, localAdIdProvider, localFinskyEventLog, str2, str3, localDeviceManagementHelper, null);
        if (FinskyLog.DEBUG) {
          FinskyLog.d("Created new context: %s", new Object[] { localDfeApiContext });
        }
        RequestQueue localRequestQueue = this.mRequestQueue;
        localObject2 = new DfeApiImpl(localRequestQueue, localDfeApiContext);
        this.mDfeApis.put(paramString, localObject2);
      }
      return localObject2;
    }
  }
  
  public final DfeApi getDfeApiNonAuthenticated()
  {
    try
    {
      if (this.mDfeApiNonAuthenticated == null)
      {
        NoCache localNoCache = new NoCache();
        TelephonyManager localTelephonyManager = (TelephonyManager)getSystemService("phone");
        String str1 = Long.toHexString(((Long)DfeApiConfig.androidId.get()).longValue());
        String str2 = DfeApiContext.makeUserAgentString(this);
        DfeApiContext localDfeApiContext = new DfeApiContext(this, null, localNoCache, null, null, Locale.getDefault(), localTelephonyManager.getSimOperator(), (String)DfeApiConfig.clientId.get(), (String)DfeApiConfig.loggingId.get(), 0, "", null, null, str1, str2, null, null);
        this.mDfeApiNonAuthenticated = new DfeApiImpl(this.mRequestQueue, localDfeApiContext);
      }
      DfeApi localDfeApi = this.mDfeApiNonAuthenticated;
      return localDfeApi;
    }
    finally {}
  }
  
  public final DfeApiProvider getDfeApiProvider()
  {
    try
    {
      if (this.mDfeApiProvider == null) {
        this.mDfeApiProvider = new DfeApiProvider()
        {
          public final DfeApi getDfeApi(String paramAnonymousString)
          {
            return FinskyApp.this.getDfeApi(paramAnonymousString);
          }
        };
      }
      DfeApiProvider localDfeApiProvider = this.mDfeApiProvider;
      return localDfeApiProvider;
    }
    finally {}
  }
  
  public final FinskyEventLog getEventLogger()
  {
    if (this.mEventLoggerInUnitTestMode) {
      return getEventLogger(null);
    }
    return getEventLogger(getCurrentAccount());
  }
  
  public final FinskyEventLog getEventLogger(Account paramAccount)
  {
    if (this.mEventLoggerInUnitTestMode) {
      paramAccount = null;
    }
    localMap = this.mEventLoggers;
    if (paramAccount == null) {}
    for (Object localObject2 = null;; localObject2 = paramAccount.name) {
      try
      {
        FinskyEventLog localFinskyEventLog = (FinskyEventLog)this.mEventLoggers.get(localObject2);
        if (localFinskyEventLog == null)
        {
          localFinskyEventLog = new FinskyEventLog(this, paramAccount, getExperiments((String)localObject2));
          this.mEventLoggers.put(localObject2, localFinskyEventLog);
        }
        return localFinskyEventLog;
      }
      finally {}
    }
  }
  
  public final FinskyEventLog getEventLogger(String paramString)
  {
    if (this.mEventLoggerInUnitTestMode) {
      return getEventLogger(null);
    }
    return getEventLogger(AccountHandler.findAccount(paramString, this));
  }
  
  public final FinskyExperiments getExperiments()
  {
    return getExperiments(getCurrentAccountName());
  }
  
  public final FinskyExperiments getExperiments(String paramString)
  {
    FinskyExperiments localFinskyExperiments = (FinskyExperiments)this.mExperimentsByAccountName.get(paramString);
    if (localFinskyExperiments == null)
    {
      FinskyExperiments.TargetsChangeListener[] arrayOfTargetsChangeListener = new FinskyExperiments.TargetsChangeListener[2];
      arrayOfTargetsChangeListener[0] = getExperimentsChangeHandler();
      arrayOfTargetsChangeListener[1] = UserSettingsCache.getExperimentsChangeListener();
      localFinskyExperiments = new FinskyExperiments(paramString, arrayOfTargetsChangeListener);
      this.mExperimentsByAccountName.put(paramString, localFinskyExperiments);
    }
    return localFinskyExperiments;
  }
  
  public final ExperimentsChangeHandler getExperimentsChangeHandler()
  {
    if (this.mExperimentsChangeHandler == null) {
      this.mExperimentsChangeHandler = new ExperimentsChangeHandler(getApplicationContext());
    }
    return this.mExperimentsChangeHandler;
  }
  
  public final PlayDfeApi getPlayDfeApi(Account paramAccount)
  {
    if (paramAccount == null) {
      paramAccount = getCurrentAccount();
    }
    if (paramAccount == null)
    {
      FinskyLog.w("No account configured on this device.", new Object[0]);
      return null;
    }
    synchronized (this.mPlayDfeApis)
    {
      Object localObject2 = (PlayDfeApi)this.mPlayDfeApis.get(paramAccount.name);
      if (localObject2 == null)
      {
        Cache localCache = this.mCache;
        PlayDfeApiContext localPlayDfeApiContext = PlayDfeApiContext.create(this, getPackageName(), localCache, paramAccount);
        if (FinskyLog.DEBUG) {
          FinskyLog.d("Created new PlayDfeApiContext: %s", new Object[] { localPlayDfeApiContext });
        }
        localObject2 = new PlayDfeApiImpl(this.mRequestQueue, localPlayDfeApiContext);
        this.mPlayDfeApis.put(paramAccount.name, localObject2);
      }
      return localObject2;
    }
  }
  
  public final PlayDfeApiProvider getPlayDfeApiProvider()
  {
    try
    {
      if (this.mPlayDfeApiProvider == null) {
        this.mPlayDfeApiProvider = new PlayDfeApiProvider()
        {
          public final PlayDfeApi getPlayDfeApi(Account paramAnonymousAccount)
          {
            return FinskyApp.this.getPlayDfeApi(paramAnonymousAccount);
          }
        };
      }
      PlayDfeApiProvider localPlayDfeApiProvider = this.mPlayDfeApiProvider;
      return localPlayDfeApiProvider;
    }
    finally {}
  }
  
  public final VendingApi getVendingApi()
  {
    Account localAccount = this.mCurrentAccount;
    if (localAccount == null)
    {
      FinskyLog.w("Fall back to primary account.", new Object[0]);
      localAccount = AccountHandler.getFirstAccount(this);
    }
    if (localAccount == null)
    {
      FinskyLog.w("No account configured on this device.", new Object[0]);
      return null;
    }
    return getVendingApi(localAccount);
  }
  
  public final VendingApi getVendingApi(Account paramAccount)
  {
    return this.mApiFactory.getApi(paramAccount);
  }
  
  public final VendingApi getVendingApi(String paramString)
  {
    Account localAccount = AccountHandler.findAccount(paramString, this);
    if (localAccount == null)
    {
      FinskyLog.w("Account %s not configured on this device.", new Object[] { paramString });
      return null;
    }
    return getVendingApi(localAccount);
  }
  
  public final int getVersionCode()
  {
    return this.mPackageStateRepository.get(getPackageName()).installedVersion;
  }
  
  public void onCreate()
  {
    super.onCreate();
    String[] arrayOfString = G.GSERVICES_KEY_PREFIXES;
    Object localObject1 = PlayG.GSERVICES_KEY_PREFIXES;
    if ((arrayOfString == null) || (localObject1 == null)) {
      throw new IllegalArgumentException();
    }
    if (arrayOfString.length == 0) {}
    for (;;)
    {
      GservicesValue.init(this, (String[])localObject1);
      com.google.android.finsky.config.PreferenceFile.sContext = this;
      CrashDetector localCrashDetector = new CrashDetector(this);
      if (localCrashDetector.mDefaultHandler == null)
      {
        localCrashDetector.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(localCrashDetector);
      }
      File localFile1 = getCacheDir("main");
      FinskyExperiments localFinskyExperiments1 = getExperiments();
      int k = ((Integer)G.experimentDefaultMainCacheSizeMb.get()).intValue();
      int m;
      label126:
      long l;
      label425:
      int n;
      label595:
      Object localObject2;
      label733:
      FinskyExperiments localFinskyExperiments3;
      int i2;
      label1346:
      DisplayMetrics localDisplayMetrics3;
      float f5;
      label1368:
      PackageStateRepository.PackageState localPackageState2;
      if (localFinskyExperiments1.isEnabled(12603247L))
      {
        m = (int)(0.5F * k);
        this.mCache = new DiskBasedCache(localFile1, 1024 * (m * 1024));
        this.mAdIdProvider = new ElegantAdvertisingIdHelper(getContentResolver(), this);
        this.mVersionCodeOfLastRun = ((Integer)FinskyPreferences.versionCode.get()).intValue();
        getPackageManager().setComponentEnabledSetting(new ComponentName(this, AssetBrowserActivity.class), 1, 1);
        Object[] arrayOfObject2 = new Object[1];
        GoogleHttpClient localGoogleHttpClient = new GoogleHttpClient(this, "");
        String str = DfeApi.BASE_URI.toString();
        arrayOfObject2[0] = UrlRules.getRules(localGoogleHttpClient.mResolver).matchRule(str).apply(str);
        FinskyLog.d("Initializing network with DFE %s", arrayOfObject2);
        this.mRequestQueue = new RequestQueue(this.mCache, createNetwork(), 2);
        this.mRequestQueue.start();
        this.mApiFactory = new VendingApiFactory(getApplicationContext(), this.mRequestQueue);
        this.mPackageMonitorReceiver = new PackageMonitorReceiver();
        this.mPackageMonitorReceiver.attach(new GmsCoreHelper.GMSCoreNotifier(this));
        this.mPackageMonitorReceiver.attach(new ExternalReferrer.ReferrerRebroadcaster());
        this.mPackageMonitorReceiver.attach(new PermissionPolicies.PermissionPolicyNotifier());
        BillingLocator.initSingleton();
        VendingBackupAgent.registerWithBackup(getApplicationContext());
        com.google.android.finsky.download.obb.ObbFactory.sObbMasterDirectory = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "obb");
        this.mNotificationHelper = new NotificationManager(this);
        if (Build.VERSION.SDK_INT < 21) {
          break label1722;
        }
        PackageInstallerFactory.sPackageInstaller = new PackageInstallerImpl(this);
        this.mDownloadQueue = new DownloadQueueImpl(this, DownloadManagerFactory.getDownloadManager(this), (byte)0);
        RemoveAssetReceiver.initialize(this.mNotificationHelper);
        DownloadBroadcastReceiver.initialize(this.mDownloadQueue);
        this.mDownloadQueue.addListener(new DownloadNetworkQualityListener());
        this.mPackageStateRepository = new PackageManagerRepository(getPackageManager(), this.mPackageMonitorReceiver, (DevicePolicyManager)getSystemService("device_policy"));
        PackageStateRepository.PackageState localPackageState1 = this.mPackageStateRepository.get(getPackageName());
        this.mSelfUpdateScheduler = new SelfUpdateScheduler(this.mDownloadQueue, localPackageState1.installedVersion);
        File localFile2 = getCacheDir("images");
        l = ((Long)FinskyPreferences.deviceDataPartitionInBytes.get()).longValue();
        if (l >= 0L) {
          break label1736;
        }
        Utils.executeMultiThreaded(new AsyncTask() {}, new Void[0]);
        n = ((Integer)G.imageCacheSizeMb.get()).intValue();
        this.mBitmapCache = new DiskBasedCache(localFile2, 1024 * (n * 1024));
        this.mBitmapRequestQueue = new RequestQueue(this.mBitmapCache, createNetwork());
        this.mBitmapRequestQueue.start();
        DisplayMetrics localDisplayMetrics2 = getResources().getDisplayMetrics();
        getExperiments();
        this.mBitmapLoader = new InstrumentedBitmapLoader(getEventLogger(), new NetworkStateInfo(getApplicationContext()), this.mBitmapRequestQueue, localDisplayMetrics2.widthPixels, localDisplayMetrics2.heightPixels, new TentativeGcRunner());
        BitmapLoader localBitmapLoader = this.mBitmapLoader;
        DfeUtils.updateTimeoutValues();
        RequestUtils.updateLegacyDefaultTimeoutMultiplier();
        if (DfeUtils.shouldUseLegacyDefaultTimeOut()) {
          break label2010;
        }
        localObject2 = new BitmapLoader.RequestTimeoutProvider()
        {
          public final int getRequestTimeoutMs()
          {
            return DfeUtils.getRequestTimeoutMs();
          }
        };
        localBitmapLoader.mRequestTimeoutProvider = ((BitmapLoader.RequestTimeoutProvider)localObject2);
        DailyHygiene.goMakeHygieneIfDirty(this, localPackageState1.installedVersion);
        this.mPackageMonitorReceiver.attach(new ObbPackageTracker());
        this.mRecentSuggestions = new SearchRecentSuggestions(this, "com.google.android.finsky.RecentSuggestionsProvider", 1);
        this.mUsers = new Users(this, DeviceManagementHelper.getInstance(this));
        enableReceivers();
        HandlerThread localHandlerThread = new HandlerThread("libraries-thread", 10);
        localHandlerThread.start();
        Handler localHandler1 = new Handler(Looper.getMainLooper());
        Handler localHandler2 = new Handler(localHandlerThread.getLooper());
        this.mAccountsProvider = new AccountsProvider()
        {
          public final Account getAccount(String paramAnonymousString)
          {
            return AccountHandler.findAccount(paramAnonymousString, FinskyApp.this);
          }
          
          public final List<Account> getAccounts()
          {
            return Lists.newArrayList(AccountHandler.getAccounts(FinskyApp.this));
          }
        };
        AccountsProvider localAccountsProvider = this.mAccountsProvider;
        SQLiteLibrary localSQLiteLibrary = new SQLiteLibrary(this);
        this.mLibraries = new Libraries(localAccountsProvider, localSQLiteLibrary, new Handler(Looper.getMainLooper()), localHandler2);
        this.mLibraryReplicators = new LibraryReplicatorsImpl(getDfeApiProvider(), localSQLiteLibrary, this.mLibraries, localHandler1, localHandler2, ((Boolean)G.debugOptionsEnabled.get()).booleanValue());
        this.mLibraries.addListener(new Libraries.Listener()
        {
          public final void onAllLibrariesLoaded()
          {
            FinskyApp.this.mLibraryReplicators.reinitialize();
          }
          
          public final void onLibraryContentsChanged$40bdb4b1() {}
        });
        this.mLibraries.load(null);
        WidgetUtils.registerLibraryMutationsListener(sInstance, this.mLibraryReplicators);
        this.mPreregistrationHelper = new PreregistrationHelper(getDfeApiProvider(), this.mLibraries, this.mLibraryReplicators, this.mPackageStateRepository, new TimeProvider.SystemTimeProvider());
        Handler localHandler3 = new Handler(localHandlerThread.getLooper());
        WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = new WriteThroughInstallerDataStore(new InMemoryInstallerDataStore(), new SQLiteInstallerDataStore(this, null), localHandler3, localHandler1);
        this.mInstallerDataStore = localWriteThroughInstallerDataStore;
        this.mAppStates = new AppStates(null, localWriteThroughInstallerDataStore, this.mPackageStateRepository);
        this.mAppStatesReplicator = new AppStatesReplicator(this.mAccountsProvider, this.mLibraries, this.mPackageStateRepository, this.mApiFactory, localHandler1, localHandler3, this.mAdIdProvider, Build.FINGERPRINT, null);
        ContentSyncService.setupListeners(this.mLibraryReplicators, this.mPackageMonitorReceiver);
        this.mInstallPolicies = new InstallPolicies(getContentResolver(), getPackageManager(), this.mAppStates, this.mInstallerDataStore, this.mLibraries);
        this.mInstaller = new InstallerImpl(this, this.mAppStates, this.mLibraries, this.mDownloadQueue, this.mNotificationHelper, this.mInstallPolicies, this.mPackageMonitorReceiver, this.mUsers, PackageInstallerFactory.sPackageInstaller);
        this.mInstaller.start(new Runnable()
        {
          public final void run()
          {
            GmsCoreHelper.cleanupNlp(FinskyApp.sInstance);
            RestoreService.recoverRestore(FinskyApp.sInstance);
          }
        });
        new GELInstallerListener(this, this.mInstaller);
        this.mDfeNotificationManager = new DfeNotificationManagerImpl(this, this.mInstaller, this.mNotificationHelper, this.mAppStates, this.mLibraryReplicators, this.mAccountsProvider);
        PlayCardUtils.initializeCardTrackers();
        new UninstallRefundTracker(this, this.mAppStates, this.mPackageMonitorReceiver);
        new UninstallSubscriptionsTracker(this, this.mLibraries, this.mPackageMonitorReceiver);
        new MigrationAsyncTask(this).execute(new Void[0]);
        Utils.syncDebugActivityStatus(this);
        com.google.android.play.utils.NetworkInfoUtil.sNetworkInfoCacheInstance = new NetworkInfoUtil.NetworkInfoCache()
        {
          public final NetworkInfo getNetworkInfo(Context paramAnonymousContext)
          {
            return NetworkStateChangedReceiver.getCachedNetworkInfo(paramAnonymousContext);
          }
        };
        localFinskyExperiments3 = getExperiments();
        if (!localFinskyExperiments3.isEnabled(12603123L)) {
          break label2047;
        }
        i2 = 200;
        localDisplayMetrics3 = getResources().getDisplayMetrics();
        if (localDisplayMetrics3.densityDpi > i2) {
          break label2148;
        }
        f5 = 1.0F;
        FifeUrlUtils.setDpiScaleFactor(f5);
        if (!((Boolean)G.analyticsEnabled.get()).booleanValue()) {
          break label2163;
        }
        this.mAnalytics = new DfeAnalytics(new Handler(getMainLooper()), getDfeApi(null));
        PackageVerificationService.migrateAntiMalwareConsent(this);
        localPackageState2 = this.mPackageStateRepository.get("com.google.android.finsky");
        if (localPackageState2 == null) {}
      }
      try
      {
        PackageManager localPackageManager = getPackageManager();
        if (localPackageManager.getApplicationEnabledSetting("com.google.android.finsky") != 2) {
          localPackageManager.setApplicationEnabledSetting("com.google.android.finsky", 2, 1);
        }
        i1 = ((Integer)G.tabskyMinVersion.get()).intValue();
        if (localPackageState2.installedVersion < i1)
        {
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = Integer.valueOf(localPackageState2.installedVersion);
          arrayOfObject3[1] = Integer.valueOf(i1);
          FinskyLog.d("Updating com.google.android.finsky from %d to %d", arrayOfObject3);
          localAccount = AccountHandler.getFirstAccount(this);
          if (localAccount == null) {
            FinskyLog.w("No current account", new Object[0]);
          }
        }
        else
        {
          return;
          if (localObject1.length == 0)
          {
            localObject1 = arrayOfString;
            continue;
          }
          Object[] arrayOfObject1 = (Object[])Array.newInstance(arrayOfString.getClass().getComponentType(), arrayOfString.length + localObject1.length);
          System.arraycopy(arrayOfString, 0, arrayOfObject1, 0, arrayOfString.length);
          int i = arrayOfString.length;
          int j = localObject1.length;
          System.arraycopy(localObject1, 0, arrayOfObject1, i, j);
          localObject1 = arrayOfObject1;
          continue;
          if (localFinskyExperiments1.isEnabled(12603248L))
          {
            m = (int)(0.75F * k);
            break label126;
          }
          if (localFinskyExperiments1.isEnabled(12603249L))
          {
            m = (int)(1.0F * k);
            break label126;
          }
          if (localFinskyExperiments1.isEnabled(12603250L))
          {
            m = (int)(1.25F * k);
            break label126;
          }
          if (localFinskyExperiments1.isEnabled(12603251L))
          {
            m = (int)(1.5F * k);
            break label126;
          }
          m = ((Integer)G.mainCacheSizeMb.get()).intValue();
          break label126;
          label1722:
          PackageInstallerFactory.sPackageInstaller = new PackageInstallerLegacyImpl(this);
          break label425;
          label1736:
          FinskyExperiments localFinskyExperiments2 = getExperiments();
          float f1 = (float)l / 1024.0F / 1024.0F / 1024.0F;
          float f2;
          label1773:
          float f3;
          float f4;
          if (f1 < 4.0F)
          {
            f2 = 20.0F;
            DisplayMetrics localDisplayMetrics1 = getResources().getDisplayMetrics();
            f3 = f2 * (Math.max(localDisplayMetrics1.widthPixels, localDisplayMetrics1.heightPixels) / 1920.0F);
            if (!localFinskyExperiments2.isEnabled(12603118L)) {
              break label1883;
            }
            f4 = f3 * 0.5F;
          }
          for (;;)
          {
            if (f4 <= 150.0F) {
              break label1987;
            }
            n = 150;
            break;
            if (f1 < 8.0F)
            {
              f2 = 50.0F;
              break label1773;
            }
            if (f1 < 32.0F)
            {
              f2 = 80.0F;
              break label1773;
            }
            f2 = 100.0F;
            break label1773;
            label1883:
            if (localFinskyExperiments2.isEnabled(12603119L)) {
              f4 = f3 * 0.75F;
            } else if (localFinskyExperiments2.isEnabled(12603120L)) {
              f4 = f3 * 1.0F;
            } else if (localFinskyExperiments2.isEnabled(12603121L)) {
              f4 = f3 * 1.25F;
            } else if (localFinskyExperiments2.isEnabled(12603122L)) {
              f4 = f3 * 1.5F;
            } else {
              f4 = ((Integer)G.imageCacheSizeMb.get()).intValue();
            }
          }
          label1987:
          if (f4 < 4.0F)
          {
            n = 4;
            break label595;
          }
          n = (int)f4;
          break label595;
          label2010:
          if (RequestUtils.getLegacyDefaultTimeoutMultiplier() != 1.0F)
          {
            localObject2 = new BitmapLoader.RequestTimeoutProvider()
            {
              public final int getRequestTimeoutMs()
              {
                return this.val$requestTimeoutMs;
              }
            };
            break label733;
          }
          localObject2 = null;
          break label733;
          label2047:
          if (localFinskyExperiments3.isEnabled(12603125L))
          {
            i2 = 225;
            break label1346;
          }
          if (localFinskyExperiments3.isEnabled(12603127L))
          {
            i2 = 250;
            break label1346;
          }
          if (localFinskyExperiments3.isEnabled(12603128L))
          {
            i2 = 275;
            break label1346;
          }
          if (localFinskyExperiments3.isEnabled(12603129L))
          {
            i2 = 300;
            break label1346;
          }
          if (localFinskyExperiments3.isEnabled(12603130L))
          {
            i2 = 325;
            break label1346;
          }
          f5 = 1.0F;
          break label1368;
          label2148:
          f5 = i2 / localDisplayMetrics3.densityDpi;
          break label1368;
          label2163:
          this.mAnalytics = new StubAnalytics();
        }
      }
      catch (SecurityException localSecurityException)
      {
        int i1;
        Account localAccount;
        for (;;)
        {
          FinskyLog.w("Unable to disable old finsky package.", new Object[0]);
        }
        this.mInstaller.setMobileDataAllowed("com.google.android.finsky");
        this.mInstaller.setVisibility("com.google.android.finsky", false, false, false);
        this.mInstaller.requestInstall("com.google.android.finsky", i1, localAccount.name, getString(2131361848), false, "suicidal_tabsky", 3, 0);
      }
    }
  }
  
  public final void setToc(DfeToc paramDfeToc)
  {
    int i;
    int j;
    label66:
    int k;
    label92:
    int m;
    label116:
    int n;
    label140:
    int i1;
    if (this.mToc == null)
    {
      i = 1;
      this.mToc = paramDfeToc;
      if (this.mToc != null)
      {
        PreferenceFile.PrefixSharedPreference localPrefixSharedPreference = FinskyPreferences.widgetUrlsByBackend;
        boolean bool1 = updateWidgetUrl(localPrefixSharedPreference.get(0), paramDfeToc.mToc.recsWidgetUrl);
        if ((!updateWidgetUrl(localPrefixSharedPreference.get(3), paramDfeToc.getWidgetUrl(3))) && (!bool1)) {
          break label282;
        }
        j = 1;
        if ((!updateWidgetUrl(localPrefixSharedPreference.get(6), paramDfeToc.getWidgetUrl(6))) && (j == 0)) {
          break label288;
        }
        k = 1;
        if ((!updateWidgetUrl(localPrefixSharedPreference.get(2), paramDfeToc.getWidgetUrl(2))) && (k == 0)) {
          break label294;
        }
        m = 1;
        if ((!updateWidgetUrl(localPrefixSharedPreference.get(1), paramDfeToc.getWidgetUrl(1))) && (m == 0)) {
          break label300;
        }
        n = 1;
        if ((!updateWidgetUrl(localPrefixSharedPreference.get(4), paramDfeToc.getWidgetUrl(4))) && (n == 0)) {
          break label306;
        }
        i1 = 1;
        label164:
        if ((i != 0) || (i1 != 0)) {
          sendBroadcast(new Intent("com.google.android.finsky.action.TOC_SET"));
        }
        FinskyPreferences.receiveEmails.get(this.mCurrentAccount.name).put(Boolean.valueOf(paramDfeToc.hasCurrentUserPreviouslyOptedIn()));
        if (this.mToc.mToc.themeId != 1) {
          break label312;
        }
      }
    }
    label282:
    label288:
    label294:
    label300:
    label306:
    label312:
    for (boolean bool2 = true;; bool2 = false)
    {
      PlayCorpusUtils.setIsEnterpriseTheme(bool2);
      PrivacySetting localPrivacySetting = this.mToc.getUserPrivacySetting(1);
      if ((localPrivacySetting == null) || (!localPrivacySetting.hasCurrentStatus)) {
        break label318;
      }
      FinskyPreferences.locationSuggestionsEnabled.get(getCurrentAccountName()).put(Integer.valueOf(localPrivacySetting.currentStatus));
      return;
      i = 0;
      break;
      j = 0;
      break label66;
      k = 0;
      break label92;
      m = 0;
      break label116;
      n = 0;
      break label140;
      i1 = 0;
      break label164;
    }
    label318:
    FinskyLog.w("TOC didn't contain status for USE_LOCATION_DATA user setting", new Object[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.FinskyApp
 * JD-Core Version:    0.7.0.1
 */