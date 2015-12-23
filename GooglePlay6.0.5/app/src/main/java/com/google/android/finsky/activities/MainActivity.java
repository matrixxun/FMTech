package com.google.android.finsky.activities;

import android.accounts.Account;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Application;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.onboard.EntertainmentOnboardHostActivity;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.autoupdate.UpdateChecker;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.ActionBarController.ActionBarSearchModeListener;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.actionbar.ActionBarHelper.4;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar.Configurator;
import com.google.android.finsky.layout.play.FinskyDrawerLayout;
import com.google.android.finsky.layout.play.FinskyDrawerLayout.3;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.protos.Onboarding;
import com.google.android.finsky.protos.Onboardings;
import com.google.android.finsky.protos.Prompt;
import com.google.android.finsky.protos.ResolveLink.ResolvedLink;
import com.google.android.finsky.protos.Survey;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.protos.UserSettings;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.search.FinskySearch;
import com.google.android.finsky.uninstall.UninstallManagerActivity;
import com.google.android.finsky.utils.AutoUpdateUtils;
import com.google.android.finsky.utils.AutoUpdateUtils.AutoUpdateDialog;
import com.google.android.finsky.utils.BgDataDisabledError;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GPlusUtils;
import com.google.android.finsky.utils.GPlusUtils.CirclePickerListener;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LocationHelper;
import com.google.android.finsky.utils.LocationHelper.LocationProviderChangedReceiver;
import com.google.android.finsky.utils.NotificationListener;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.hats.HappinessSurveyController;
import com.google.android.finsky.utils.hats.HappinessSurveyControllerV2;
import com.google.android.finsky.utils.hats.HappinessSurveyControllerV2.1;
import com.google.android.finsky.utils.hats.HatsUtils;
import com.google.android.finsky.utils.hats.SurveyStore;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.googlehelp.GoogleHelpTogglingRegister;
import com.google.android.gms.googlehelp.internal.common.zzi;
import com.google.android.gms.googlehelp.internal.common.zzi.3;
import com.google.android.gms.googlehelp.zzc;
import com.google.android.gms.people.People;
import com.google.android.gms.people.People.PeopleOptions1p;
import com.google.android.gms.people.People.PeopleOptions1p.Builder;
import com.google.android.play.R.string;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.dfe.api.PlayDfeApiProvider;
import com.google.android.play.drawer.PlayDrawerAdapter;
import com.google.android.play.drawer.PlayDrawerArrowDrawable;
import com.google.android.play.drawer.PlayDrawerLayout;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.search.PlaySearch;
import com.google.android.play.search.PlaySearchToolbar;
import com.google.android.play.utils.PlayCommonLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity
  extends AuthenticatedActivity
  implements SimpleAlertDialog.Listener, PageFragmentHost, ActionBarController
{
  private static boolean IS_HC_OR_ABOVE;
  private static boolean sBillingInitialized;
  private int mBitmapSequenceNumberToDrainFrom = -1;
  public ViewGroup mContentFrame;
  private PlayDrawerArrowDrawable mDrawerArrowDrawable;
  public FinskyDrawerLayout mDrawerLayout;
  private final Handler mHandler = new Handler();
  private HappinessSurveyControllerV2 mHatsControllerV2;
  private HappinessSurveyController mHatsSurveyController;
  private int mLastShownErrorHash;
  private LocationHelper mLocationHelper;
  public NavigationManager mNavigationManager;
  private final NotificationListener mNotificationListener = new NotificationListener()
  {
    public final boolean shouldShowAppNotification$14e1ec69(String paramAnonymousString)
    {
      Document localDocument = MainActivity.this.mNavigationManager.getCurrentDocument();
      return (localDocument == null) || (localDocument.mDocument.backendId != 3) || (!localDocument.getAppDetails().packageName.equals(paramAnonymousString));
    }
    
    public final boolean showAppAlert(String paramAnonymousString, ErrorDialog.Builder paramAnonymousBuilder)
    {
      Document localDocument = MainActivity.this.mNavigationManager.getCurrentDocument();
      if ((localDocument != null) && (localDocument.mDocument.backendId == 3) && (localDocument.getAppDetails().packageName.equals(paramAnonymousString)))
      {
        paramAnonymousBuilder.build().show(MainActivity.this.getSupportFragmentManager());
        return true;
      }
      return false;
    }
    
    public final boolean showAppAlert(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, int paramAnonymousInt)
    {
      Document localDocument = MainActivity.this.mNavigationManager.getCurrentDocument();
      if ((localDocument != null) && (localDocument.mDocument.backendId == 3) && (localDocument.getAppDetails().packageName.equals(paramAnonymousString1))) {
        return MainActivity.this.showErrorDialogForCode(paramAnonymousString2, paramAnonymousString3, paramAnonymousInt, paramAnonymousString1);
      }
      return false;
    }
    
    public final boolean showDocAlert(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4)
    {
      Document localDocument = MainActivity.this.mNavigationManager.getCurrentDocument();
      boolean bool1 = false;
      if (localDocument != null) {
        if (!MainActivity.this.mNavigationManager.getCurrentDocument().mDocument.docid.equals(paramAnonymousString1))
        {
          boolean bool2 = DfeUtils.isSameDocumentDetailsUrl(paramAnonymousString4, MainActivity.this.mNavigationManager.getCurrentDocument().mDocument.detailsUrl);
          bool1 = false;
          if (!bool2) {}
        }
        else
        {
          MainActivity.this.showErrorDialog(paramAnonymousString2, paramAnonymousString3, false);
          bool1 = true;
        }
      }
      return bool1;
    }
  };
  private GoogleApiClient mPeopleClient;
  private Bundle mSavedInstanceState;
  private ActionBarController.ActionBarSearchModeListener mSearchModeListener = null;
  private int mSequenceNumberToDrainFrom = -1;
  private final Runnable mStopPreviewsRunnable = new Runnable()
  {
    public final void run() {}
  };
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (boolean bool = true;; bool = false)
    {
      IS_HC_OR_ABOVE = bool;
      return;
    }
  }
  
  public static Intent getMyDownloadsIntent(Context paramContext)
  {
    return new Intent("com.google.android.finsky.VIEW_MY_DOWNLOADS").setClass(paramContext, MainActivity.class);
  }
  
  public static Intent getMyDownloadsIntent$634a7f4e(Context paramContext)
  {
    return new Intent("com.google.android.finsky.VIEW_MY_DOWNLOADS").setClass(paramContext, MainActivity.class).putExtra("trigger_update_all", true);
  }
  
  private String getReferringPackage()
  {
    try
    {
      List localList = ((ActivityManager)getSystemService("activity")).getRecentTasks(2, 0);
      if (localList.size() > 0)
      {
        Object localObject = ((ActivityManager.RecentTaskInfo)localList.get(0)).baseIntent.getComponent().getPackageName();
        if ((getPackageName().equals(localObject)) && (localList.size() > 1))
        {
          String str = ((ActivityManager.RecentTaskInfo)localList.get(1)).baseIntent.getComponent().getPackageName();
          localObject = str;
        }
        return localObject;
      }
    }
    catch (Exception localException)
    {
      FinskyLog.e(localException, "Exception while getting package.", new Object[0]);
    }
    return null;
  }
  
  private boolean goToEntertainmentOnboarding()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    DfeToc localDfeToc = localFinskyApp.mToc;
    if (localDfeToc == null) {
      return false;
    }
    if (localDfeToc.mToc.themeId == 1) {
      return false;
    }
    FinskyExperiments localFinskyExperiments = localFinskyApp.getExperiments();
    if ((localFinskyExperiments.isEnabled(12603505L)) || (localFinskyExperiments.isEnabled(12603731L))) {}
    for (int i = 1; i == 0; i = 0) {
      return false;
    }
    if (!localFinskyExperiments.isEnabled(12603210L)) {
      return false;
    }
    if (Build.VERSION.SDK_INT < 14) {
      return false;
    }
    if (localDfeToc.getCorpusList().size() <= 2) {
      return false;
    }
    if (((Boolean)FinskyPreferences.hasShownEntertainmentOnboarding.get()).booleanValue()) {
      return false;
    }
    String str = FinskyApp.get().getCurrentAccountName();
    UserSettings localUserSettings = UserSettingsCache.getCachedUserSettings(str);
    int j;
    Onboarding[] arrayOfOnboarding;
    int k;
    if (!localFinskyExperiments.isEnabled(12604043L))
    {
      j = 1;
      if ((j != 0) && (localUserSettings != null) && (localUserSettings.dismissedOnboardings != null))
      {
        arrayOfOnboarding = localUserSettings.dismissedOnboardings.onboarding;
        k = arrayOfOnboarding.length;
      }
    }
    else
    {
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label242;
        }
        Onboarding localOnboarding = arrayOfOnboarding[m];
        if ((localOnboarding.hasType) && (localOnboarding.type == 1))
        {
          FinskyPreferences.hasShownEntertainmentOnboarding.put(Boolean.valueOf(true));
          return false;
          j = 0;
          break;
        }
      }
    }
    label242:
    UserSettingsCache.setCompletedOnboardingUserSetting$505cff1c(str);
    startActivityForResult(EntertainmentOnboardHostActivity.createIntent(this), 50);
    overridePendingTransition(2131034112, 2131034113);
    new DfeBrowse(localFinskyApp.getDfeApi(null), localDfeToc.mToc.homeUrl, new UserContext());
    FinskyPreferences.hasShownEntertainmentOnboarding.put(Boolean.valueOf(true));
    return true;
  }
  
  private void handleIntent()
  {
    hideLoadingIndicator();
    hideErrorMessage();
    Intent localIntent = getIntent();
    String str1 = localIntent.getStringExtra("authAccount");
    if (str1 != null)
    {
      localIntent.removeExtra("authAccount");
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(str1);
      FinskyLog.d("b/5160617: Switching account to %s due to intent", arrayOfObject);
      switchAccount(str1, localIntent);
    }
    label90:
    label93:
    do
    {
      String str2;
      for (;;)
      {
        return;
        PackageStateRepository.PackageState localPackageState1 = FinskyApp.get().mPackageStateRepository.get("com.android.providers.downloads");
        int i;
        PackageStateRepository.PackageState localPackageState2;
        if (localPackageState1 == null)
        {
          FinskyLog.wtf("Cannot find com.android.providers.downloads", new Object[0]);
          i = 0;
          if (i != 0) {
            break label448;
          }
          if (Build.VERSION.SDK_INT >= 19)
          {
            localPackageState2 = FinskyApp.get().mPackageStateRepository.get("com.google.android.gms");
            if (localPackageState2 != null) {
              break label450;
            }
            FinskyLog.wtf("Cannot find com.google.android.gms", new Object[0]);
          }
        }
        for (int j = 0;; j = 1)
        {
          if (j != 0) {
            break label551;
          }
          if (localIntent.hasExtra("error_html_message"))
          {
            boolean bool2 = localIntent.hasExtra("error_title");
            String str9 = null;
            if (bool2) {
              str9 = localIntent.getStringExtra("error_title");
            }
            String str10 = localIntent.getStringExtra("error_html_message");
            String str11 = localIntent.getStringExtra("error_doc_id");
            int n = (str10 + str9 + str11).hashCode();
            if (this.mLastShownErrorHash != n)
            {
              showErrorDialogForCode(str9, str10, localIntent.getIntExtra("error_return_code", -1), str11);
              this.mLastShownErrorHash = n;
            }
          }
          if (this.mDrawerLayout != null) {
            this.mDrawerLayout.closeDrawer();
          }
          str2 = localIntent.getAction();
          if ((!"android.intent.action.SEARCH".equals(str2)) && (!"com.google.android.gms.actions.SEARCH_ACTION".equals(str2))) {
            break label553;
          }
          if (!isTosAccepted()) {
            break;
          }
          String str3 = localIntent.getStringExtra("query");
          FinskyApp.get().mRecentSuggestions.saveRecentQuery(str3, null);
          this.mNavigationManager.goToSearch$36098d51(str3, this.mActionBarHelper.mCurrentBackendId);
          return;
          if ((!localPackageState1.isDisabled) && (!localPackageState1.isDisabledByUser)) {
            break label90;
          }
          FinskyLog.w("Detected disabled com.android.providers.downloads", new Object[0]);
          FragmentManager localFragmentManager1 = getSupportFragmentManager();
          if (localFragmentManager1.findFragmentByTag("download_manager_disabled") == null)
          {
            SimpleAlertDialog.Builder localBuilder1 = new SimpleAlertDialog.Builder();
            localBuilder1.setCallback(null, 40, null).setMessageId(2131362101).setPositiveId(2131362418).setNegativeId(2131361915).setCanceledOnTouchOutside(false);
            localBuilder1.build().show(localFragmentManager1, "download_manager_disabled");
          }
          i = 1;
          break label93;
          break;
          if ((!localPackageState2.isDisabled) && (!localPackageState2.isDisabledByUser)) {
            break label137;
          }
          FinskyLog.w("Detected disabled com.google.android.gms", new Object[0]);
          FragmentManager localFragmentManager2 = getSupportFragmentManager();
          if (localFragmentManager2.findFragmentByTag("gms_core_disabled") == null)
          {
            SimpleAlertDialog.Builder localBuilder2 = new SimpleAlertDialog.Builder();
            localBuilder2.setCallback(null, 42, null).setMessageId(2131362192).setPositiveId(2131362418).setNegativeId(2131361915).setCanceledOnTouchOutside(false);
            localBuilder2.build().show(localFragmentManager2, "gms_core_disabled");
          }
        }
      }
      if (("android.intent.action.VIEW".equals(str2)) || ("android.nfc.action.NDEF_DISCOVERED".equals(str2)) || ("afwapp.android.intent.action.VIEW".equals(str2)))
      {
        this.mNavigationManager.clear();
        String str4 = localIntent.getDataString();
        String str5 = DfeAnalytics.buildAnalyticsUrl("deepLink", str4, localIntent.getAction());
        FinskyApp.get().mAnalytics.logAdMobPageView(str5);
        if (getIntent().getBooleanExtra("dont_resolve_again", false))
        {
          openInBrowser(localIntent);
          return;
        }
        this.mNavigationManager.handleDeepLink(Uri.parse(str4), getReferringPackage());
        return;
      }
      if ("com.google.android.finsky.DETAILS".equals(str2))
      {
        this.mNavigationManager.goToDocPage(localIntent.getDataString());
        return;
      }
      if ("com.google.android.finsky.VIEW_MY_DOWNLOADS".equals(str2))
      {
        this.mNavigationManager.clear();
        boolean bool1 = localIntent.getBooleanExtra("trigger_update_all", false);
        this.mNavigationManager.goToMyDownloads(FinskyApp.get().mToc, bool1);
        return;
      }
      if ("com.google.android.finsky.CORPUS_HOME".equals(str2))
      {
        int m = localIntent.getIntExtra("backend_id", 0);
        String str8 = localIntent.getStringExtra("title");
        if (m == 0) {
          break;
        }
        if (FinskyApp.get().getExperiments().isH20StoreEnabled())
        {
          this.mNavigationManager.redirectToCorpus(localIntent.getDataString(), str8, m, FinskyApp.get().mToc);
          return;
        }
        this.mNavigationManager.goToCorpusHome(localIntent.getDataString(), str8, m, FinskyApp.get().mToc);
        return;
      }
      if ("com.google.android.finsky.VIEW_BROWSE".equals(str2))
      {
        int k = localIntent.getIntExtra("backend_id", 0);
        DfeToc localDfeToc = FinskyApp.get().mToc;
        if (localDfeToc.getCorpus(k) == null)
        {
          this.mNavigationManager.goToAggregatedHome(localDfeToc);
          return;
        }
        String str6 = localIntent.getStringExtra("title");
        String str7 = localIntent.getDataString();
        if (localIntent.getBooleanExtra("clear_back_stack", false)) {
          this.mNavigationManager.clear();
        }
        this.mNavigationManager.goBrowse(str7, str6, k, FinskyApp.get().mToc, null);
        return;
      }
    } while ((!this.mNavigationManager.isBackStackEmpty()) || (goToEntertainmentOnboarding()));
    label137:
    label448:
    label450:
    this.mNavigationManager.goToAggregatedHome(FinskyApp.get().mToc);
    label551:
    label553:
    return;
  }
  
  private void hideErrorMessage()
  {
    findViewById(2131755276).setVisibility(8);
  }
  
  private boolean isAtTopLevelDrawerDestination()
  {
    int i = 1;
    int j = this.mNavigationManager.getCurrentPageType();
    if ((j == i) || (j == 17) || (j == 2) || (j == 10) || (j == 3) || (j == 12) || (j == 13)) {}
    for (;;)
    {
      PageFragment localPageFragment = this.mNavigationManager.getActivePage();
      if ((j == 4) && ((localPageFragment instanceof TabbedBrowseFragment)))
      {
        TabbedBrowseFragment localTabbedBrowseFragment = (TabbedBrowseFragment)localPageFragment;
        if (localTabbedBrowseFragment.mDfeToc.getCorpus(localTabbedBrowseFragment.mUrl) != null) {
          i = 1;
        }
      }
      return i;
      i = 0;
    }
  }
  
  private void maybeClearBackstack()
  {
    if ((!this.mNavigationManager.isBackStackEmpty()) && (shouldClearBackstack()))
    {
      FinskyApp.get().getEventLogger().logBackgroundEvent(525, null);
      this.mNavigationManager.clear();
      if (!goToEntertainmentOnboarding()) {}
    }
    else
    {
      return;
    }
    this.mNavigationManager.goToAggregatedHome(FinskyApp.get().mToc);
  }
  
  private void openInBrowser(Intent paramIntent)
  {
    PackageManager localPackageManager = getPackageManager();
    paramIntent.setComponent(null);
    List localList = localPackageManager.queryIntentActivities(paramIntent, 0);
    int i = localList.size();
    for (int j = 0;; j++) {
      if (j < i)
      {
        ActivityInfo localActivityInfo = ((ResolveInfo)localList.get(j)).activityInfo;
        if (!localActivityInfo.packageName.equals(getPackageName()))
        {
          paramIntent.setComponent(new ComponentName(localActivityInfo.packageName, localActivityInfo.name));
          startActivity(paramIntent);
          finish();
        }
      }
      else
      {
        return;
      }
    }
  }
  
  private static boolean shouldClearBackstack()
  {
    return (FinskyApp.get().getExperiments().isEnabled(742L)) && (((Long)FinskyPreferences.lastTimeBackStackUpdatedMs.get()).longValue() < System.currentTimeMillis() - ((Long)G.backStackExpirationDurationMs.get()).longValue());
  }
  
  private boolean showErrorDialogForCode(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(paramString3);
    switch (paramInt)
    {
    }
    for (;;)
    {
      showErrorDialog(paramString1, paramString2, false);
      return true;
      if (FinskyApp.get().getExperiments().isEnabled(12603367L))
      {
        startActivity(UninstallManagerActivity.createIntent(this.mNavigationManager.getCurrentDocument()));
        return true;
        if ((localPackageState != null) && (localPackageState.isSystemApp)) {}
        for (int i = 1; i == 0; i = 0)
        {
          SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
          localBuilder.setMessageId(2131362246).setPositiveId(2131362418).setNegativeId(2131362807);
          Bundle localBundle = new Bundle();
          localBundle.putString("error_package_name", paramString3);
          localBuilder.setCallback(null, 32, localBundle);
          localBuilder.build().show(getSupportFragmentManager(), "mismatched_certificates");
          return true;
        }
      }
    }
  }
  
  private void showRestartRequiredDialog()
  {
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setCallback(null, 41, null).setMessageId(2131362661).setPositiveId(2131362418).setCanceledOnTouchOutside(false);
    localBuilder.build().show(getSupportFragmentManager(), "restart_required");
  }
  
  public final void disableActionBarOverlay()
  {
    this.mNavigationManager.setActionBarOverlayEnabledForCurrent(false);
    int i = this.mContentFrame.getPaddingTop();
    int j = getDelegate().getSupportActionBar().getHeight();
    int k = Math.max(FinskySearchToolbar.getToolbarHeight(this), j);
    if (i == k) {
      return;
    }
    ViewCompat.setPaddingRelative(this.mContentFrame, ViewCompat.getPaddingStart(this.mContentFrame), k, ViewCompat.getPaddingEnd(this.mContentFrame), this.mContentFrame.getPaddingBottom());
  }
  
  public final void disableStatusBarOverlay()
  {
    this.mNavigationManager.setStatusBarOverlayEnabledForCurrent(false);
  }
  
  public final void enableActionBarOverlay()
  {
    this.mNavigationManager.setActionBarOverlayEnabledForCurrent(true);
    if (this.mContentFrame.getPaddingTop() == 0) {
      return;
    }
    ViewCompat.setPaddingRelative(this.mContentFrame, ViewCompat.getPaddingStart(this.mContentFrame), 0, ViewCompat.getPaddingEnd(this.mContentFrame), this.mContentFrame.getPaddingBottom());
  }
  
  public final void enableStatusBarOverlay()
  {
    this.mNavigationManager.setStatusBarOverlayEnabledForCurrent(true);
  }
  
  @TargetApi(11)
  public final void enterActionBarSearchMode()
  {
    this.mActionBarHelper.enterActionBarTransientOpacityMode(1, null);
    if (this.mSearchModeListener != null) {
      this.mSearchModeListener.onEnterActionBarSearchMode();
    }
    if (IS_HC_OR_ABOVE) {
      this.mContentFrame.setLayerType(2, null);
    }
  }
  
  public final void enterActionBarSectionExpandedMode(CharSequence paramCharSequence, TextSectionTranslatable paramTextSectionTranslatable)
  {
    this.mDrawerLayout.setDrawerLockMode(1);
    ActionBarHelper localActionBarHelper = this.mActionBarHelper;
    localActionBarHelper.enterActionBarTransientOpacityMode(2, paramCharSequence);
    localActionBarHelper.clearSearch();
    localActionBarHelper.mExpandedModeTranslatable = paramTextSectionTranslatable;
    if (localActionBarHelper.mActionBar != null) {
      localActionBarHelper.mActionBarController.setHomeAsUpIndicator(2130837702);
    }
    localActionBarHelper.syncState();
  }
  
  @TargetApi(11)
  public final void enterDrawerOpenMode()
  {
    this.mActionBarHelper.updateNavDawerState(true);
    if (IS_HC_OR_ABOVE) {
      this.mContentFrame.setLayerType(2, null);
    }
  }
  
  @TargetApi(11)
  public final void exitActionBarSearchMode()
  {
    ActionBarHelper localActionBarHelper = this.mActionBarHelper;
    if (localActionBarHelper.isInMode(Integer.valueOf(1)))
    {
      localActionBarHelper.exitCurrentActionBarMode();
      localActionBarHelper.syncState();
    }
    for (;;)
    {
      if (this.mSearchModeListener != null) {
        this.mSearchModeListener.onExitActionBarSearchMode();
      }
      if (IS_HC_OR_ABOVE) {
        this.mContentFrame.setLayerType(0, null);
      }
      return;
      localActionBarHelper.removeModeFromStack(Integer.valueOf(1));
    }
  }
  
  public final void exitActionBarSectionExpandedMode()
  {
    this.mDrawerLayout.setDrawerLockMode(0);
    ActionBarHelper localActionBarHelper = this.mActionBarHelper;
    if (localActionBarHelper.mActionBar != null) {
      localActionBarHelper.mActionBarController.setHomeAsUpIndicator(0);
    }
    localActionBarHelper.mExpandedModeTranslatable = null;
    if (localActionBarHelper.isInMode(Integer.valueOf(2)))
    {
      localActionBarHelper.exitCurrentActionBarMode();
      localActionBarHelper.syncState();
      return;
    }
    localActionBarHelper.removeModeFromStack(Integer.valueOf(2));
  }
  
  @TargetApi(11)
  public final void exitDrawerOpenMode()
  {
    this.mActionBarHelper.updateNavDawerState(false);
    if (IS_HC_OR_ABOVE) {
      this.mContentFrame.setLayerType(0, null);
    }
  }
  
  public final ActionBarController getActionBarController()
  {
    return this;
  }
  
  public final ActionBarHelper getActionBarHelper()
  {
    return this.mActionBarHelper;
  }
  
  public final BitmapLoader getBitmapLoader()
  {
    return FinskyApp.get().mBitmapLoader;
  }
  
  public final DfeApi getDfeApi(String paramString)
  {
    return FinskyApp.get().getDfeApi(paramString);
  }
  
  public final NavigationManager getNavigationManager()
  {
    return this.mNavigationManager;
  }
  
  public final GoogleApiClient getPeopleClient()
  {
    return this.mPeopleClient;
  }
  
  public final Toolbar getToolbar()
  {
    return (Toolbar)findViewById(2131755196);
  }
  
  public final void goBack()
  {
    onBackPressed();
  }
  
  protected final void handleAuthenticationError(VolleyError paramVolleyError)
  {
    if ((paramVolleyError instanceof AuthFailureError))
    {
      Intent localIntent = ((AuthFailureError)paramVolleyError).mResolutionIntent;
      if (localIntent != null)
      {
        this.mWaitingForUserInput = true;
        startActivityForResult(localIntent, 22);
        return;
      }
    }
    hideLoadingIndicator();
    if ((paramVolleyError instanceof BgDataDisabledError)) {
      showBackgroundDataDialog();
    }
    for (;;)
    {
      FinskyDrawerLayout localFinskyDrawerLayout = this.mDrawerLayout;
      localFinskyDrawerLayout.checkIsConfigured();
      if (PlayDrawerLayout.isDrawerOpen(localFinskyDrawerLayout.mDrawerRoot)) {
        break;
      }
      localFinskyDrawerLayout.openDrawer(localFinskyDrawerLayout.mDrawerRoot);
      return;
      if (!this.mNavigationManager.isBackStackEmpty()) {
        this.mNavigationManager.clear();
      }
      String str = ErrorStrings.get(this, paramVolleyError);
      View localView = findViewById(2131755276);
      localView.setVisibility(0);
      ((TextView)localView.findViewById(2131755274)).setText(str);
      localView.findViewById(2131755482).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          String str = FinskyApp.get().getCurrentAccountName();
          if (str == null)
          {
            FinskyLog.d("No account, restarting activity after network error", new Object[0]);
            MainActivity.this.restart();
            return;
          }
          Account localAccount = AccountHandler.findAccount(str, MainActivity.this);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = FinskyLog.scrubPii(localAccount.name);
          FinskyLog.d("b/5160617: Reinitialize account %s on retry button click", arrayOfObject);
          MainActivity.this.reinitialize(localAccount, null, false);
          MainActivity.this.hideErrorMessage();
          MainActivity.this.showLoadingIndicator();
        }
      });
    }
  }
  
  public final void hideSatisfactionSurveyV2()
  {
    if (this.mHatsControllerV2 == null) {
      return;
    }
    this.mHatsControllerV2.hideSurveyPrompt();
  }
  
  public final void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment)
  {
    int i = 1;
    int j = this.mNavigationManager.getCurrentPageType();
    int k;
    int m;
    label26:
    int n;
    if (j == 2)
    {
      k = i;
      if (j != 5) {
        break label66;
      }
      m = i;
      if (j != 7) {
        break label72;
      }
      n = i;
      label35:
      if (j != 4) {
        break label78;
      }
      label40:
      if ((k != 0) || (m != 0) || (n != 0) || (i != 0)) {
        break label83;
      }
    }
    label299:
    for (;;)
    {
      return;
      k = 0;
      break;
      label66:
      m = 0;
      break label26;
      label72:
      n = 0;
      break label35;
      label78:
      i = 0;
      break label40;
      label83:
      NavigationManager localNavigationManager = this.mNavigationManager;
      FinskyExperiments localFinskyExperiments1 = FinskyApp.get().getExperiments();
      FinskyExperiments localFinskyExperiments2 = FinskyApp.get().getExperiments();
      String str;
      if (localNavigationManager.canPromptSearchSurveyForCurrent()) {
        if ((localFinskyExperiments2.isEnabled(745L)) && (HatsUtils.isSurveyEligible((String)G.surveySiteIdSearchResults.get())))
        {
          str = (String)G.surveySiteIdSearchResults.get();
          if (str == null) {
            break label255;
          }
        }
      }
      for (;;)
      {
        if (((this.mHatsSurveyController != null) && (this.mHatsSurveyController.getSiteId().equals(str))) || (paramPageFragment == null) || (str == null)) {
          break label299;
        }
        this.mHatsSurveyController = new HappinessSurveyController(paramPageFragment, str, false);
        this.mHatsSurveyController.start();
        return;
        if ((localFinskyExperiments2.isEnabled(746L)) && (HatsUtils.isSurveyEligible((String)G.surveySiteIdSearchResultsControlGroup.get())))
        {
          str = (String)G.surveySiteIdSearchResultsControlGroup.get();
          break;
        }
        str = null;
        break;
        label255:
        if ((localFinskyExperiments1.isEnabled(747L)) && (HatsUtils.isSurveyEligible((String)G.surveySiteIdOverallApp.get()))) {
          str = (String)G.surveySiteIdOverallApp.get();
        } else {
          str = null;
        }
      }
    }
  }
  
  public final void maybeShowSatisfactionSurveyV2(Survey paramSurvey)
  {
    if (this.mHatsControllerV2 == null) {
      this.mHatsControllerV2 = new HappinessSurveyControllerV2(this, (ViewGroup)findViewById(2131755701));
    }
    HappinessSurveyControllerV2 localHappinessSurveyControllerV2 = this.mHatsControllerV2;
    if ((localHappinessSurveyControllerV2.mParentActivity == null) || (localHappinessSurveyControllerV2.mContainerView == null)) {}
    FinskyApp localFinskyApp;
    do
    {
      return;
      localFinskyApp = FinskyApp.get();
    } while (localFinskyApp.getClientMutationCache(localFinskyApp.getCurrentAccountName()).getSurveyStore().mIsSurveyDialogVisible);
    View localView1 = localHappinessSurveyControllerV2.mContainerView.findViewById(2131756157);
    View localView2;
    if (localView1 == null)
    {
      localView2 = LayoutInflater.from(localHappinessSurveyControllerV2.mParentActivity).inflate(2130969132, localHappinessSurveyControllerV2.mContainerView, false);
      if (!localHappinessSurveyControllerV2.mParentActivity.getResources().getBoolean(2131427333))
      {
        ViewGroup.LayoutParams localLayoutParams = localView2.getLayoutParams();
        double d = UiUtils.getGridColumnContentWidth(localHappinessSurveyControllerV2.mParentActivity.getResources()) / UiUtils.getFeaturedGridColumnCount(localHappinessSurveyControllerV2.mParentActivity.getResources(), 1.0D);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        localHappinessSurveyControllerV2.mParentActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        localLayoutParams.width = ((int)Math.min(d * 2.5D, localDisplayMetrics.widthPixels));
        localView2.setLayoutParams(localLayoutParams);
      }
      ((ButtonBar)localView2.findViewById(2131755300)).setClickListener(new HappinessSurveyControllerV2.1(localHappinessSurveyControllerV2));
      localHappinessSurveyControllerV2.mContainerView.addView(localView2);
    }
    for (;;)
    {
      if (!paramSurvey.equals(localHappinessSurveyControllerV2.mSurvey))
      {
        localHappinessSurveyControllerV2.mSurvey = paramSurvey;
        ((TextView)localView2.findViewById(2131755173)).setText(localHappinessSurveyControllerV2.mSurvey.prompt.promptText);
        ButtonBar localButtonBar = (ButtonBar)localView2.findViewById(2131755300);
        localButtonBar.setPositiveButtonTitle(localHappinessSurveyControllerV2.mSurvey.prompt.acceptButtonText.toUpperCase());
        localButtonBar.setNegativeButtonTitle(localHappinessSurveyControllerV2.mSurvey.prompt.rejectButtonText.toUpperCase());
      }
      if (localView2.getVisibility() == 8) {
        localView2.setVisibility(0);
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        localView2.measure(View.MeasureSpec.makeMeasureSpec(localHappinessSurveyControllerV2.mContainerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(localHappinessSurveyControllerV2.mContainerView.getHeight(), -2147483648));
        localView2.setTranslationY(localView2.getMeasuredHeight());
        ObjectAnimator.ofFloat(localView2, "translationY", new float[] { 0.0F }).start();
      }
      HatsUtils.logSurveyEvent(4, localHappinessSurveyControllerV2.mSurvey.id, -1, localHappinessSurveyControllerV2.mSurvey.context);
      return;
      localView2 = localView1;
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 31) && (paramInt2 == 40))
    {
      FinskyLog.d("b/5160617: Reinitialize with null accountafter user changed content level", new Object[0]);
      runOrScheduleActiveStateRunnable(new Runnable()
      {
        public final void run()
        {
          MainActivity.this.reinitialize(null, null, true);
        }
      });
      return;
    }
    boolean bool3;
    if (paramInt1 == 33)
    {
      int i = -1;
      boolean bool2 = false;
      bool3 = false;
      if (paramIntent != null)
      {
        i = paramIntent.getIntExtra("backend", -1);
        bool2 = paramIntent.getBooleanExtra("involved_heavy_dialogs", true);
        if (!paramIntent.getBooleanExtra("is_game", false))
        {
          bool3 = false;
          paramIntent = null;
        }
      }
      else if ((paramInt2 == -1) && (i == 3) && (!bool2))
      {
        if (!GaiaRecoveryHelper.shouldShowGaiaRecoveryDialog()) {
          break label173;
        }
        new Handler().post(new Runnable()
        {
          public final void run()
          {
            GaiaRecoveryHelper.launchGaiaRecoveryDialog$18e2bb50(MainActivity.this.getResources(), MainActivity.this.getSupportFragmentManager(), "dialog.gaia_recovery_migration");
          }
        });
      }
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if (((Boolean)G.playGamesInstallSuggestionOnlyWhenGameFeatures.get()).booleanValue())
      {
        bool3 = paramIntent.getBooleanExtra("has_game_features", false);
        break;
      }
      bool3 = true;
      break;
      label173:
      int i1;
      label308:
      int j;
      if (((((Boolean)FinskyPreferences.hadPreJsAutoUpdateSettings.get()).booleanValue()) || (((Boolean)G.skipAutoUpdateCleanupClientVersionCheck.get()).booleanValue())) && (!((Boolean)FinskyPreferences.hasAcceptedAutoUpdateMigrationDialog.get()).booleanValue()) && (((Integer)FinskyPreferences.autoUpdateMigrationDialogShownCount.get()).intValue() < ((Integer)G.maxAutoUpdateDialogShownCount.get()).intValue())) {
        switch (((Integer)FinskyPreferences.autoUpdateMigrationDialogShownCount.get()).intValue())
        {
        default: 
          int i2 = ((Integer)G.autoUpdateDialogSubsequentBackoffDays.get()).intValue();
          if (i2 != -1)
          {
            i1 = i2;
            if (((Long)FinskyPreferences.lastUpdateMigrationDialogTimeShown.get()).longValue() + 86400000L * i1 < System.currentTimeMillis()) {
              j = 1;
            }
          }
          break;
        }
      }
      for (;;)
      {
        if (j == 0) {
          break label411;
        }
        new Handler().post(new Runnable()
        {
          public final void run()
          {
            FragmentManager localFragmentManager;
            if (!MainActivity.this.mStateSaved)
            {
              localFragmentManager = MainActivity.this.getSupportFragmentManager();
              if (!((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue()) {
                break label92;
              }
            }
            label92:
            for (int i = 2131361880;; i = 2131361879)
            {
              SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
              localBuilder.setMessageId(i).setPositiveId(2131362418).setNegativeId(2131362394).setCallback(null, 36, null).setEventLog(306, null, 237, 236, null);
              localBuilder.build().show(localFragmentManager, "dialog.auto_update_migration");
              return;
            }
          }
        });
        break;
        j = 1;
        continue;
        i1 = ((Integer)G.autoUpdateDialogFirstBackoffDays.get()).intValue();
        break label308;
        i1 = ((Integer)G.autoUpdateDialogSecondBackoffDays.get()).intValue();
        break label308;
        j = 0;
      }
      label411:
      if (bool3)
      {
        int k = ((Integer)G.playGamesInstallSuggestionMaxShownCount.get()).intValue();
        long l;
        label562:
        int m;
        if (k != 0)
        {
          int n = ((Integer)FinskyPreferences.playGamesInstallSuggestionShownCount.get()).intValue();
          if (n < k)
          {
            PackageStateRepository localPackageStateRepository = FinskyApp.get().mAppStates.mPackageManager;
            PackageStateRepository.PackageState localPackageState = localPackageStateRepository.get("com.google.android.gms");
            if ((localPackageState != null) && (localPackageState.installedVersion >= ((Integer)G.playGamesGmsCoreMinVersion.get()).intValue()) && (localPackageStateRepository.get((String)G.playGamesDocId.get()) == null)) {
              switch (n)
              {
              default: 
                l = ((Long)G.playGamesInstallSuggestionSubsequentBackoffMs.get()).longValue();
                if (l + ((Long)FinskyPreferences.playGamesInstallSuggestionLastTimeShown.get()).longValue() < System.currentTimeMillis()) {
                  m = 1;
                }
                break;
              }
            }
          }
        }
        while (m != 0)
        {
          new Handler().post(new Runnable()
          {
            public final void run()
            {
              if (!MainActivity.this.mStateSaved)
              {
                FragmentManager localFragmentManager = MainActivity.this.getSupportFragmentManager();
                SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
                localBuilder.setLayoutId(2130968979).setPositiveId(2131362224).setNegativeId(2131362394).setViewConfiguration(new Bundle()).setCallback(null, 38, null).setEventLog(318, null, 274, 275, null);
                localBuilder.build().show(localFragmentManager, "dialog.play_games_install_suggestion");
                int i = 1 + ((Integer)FinskyPreferences.playGamesInstallSuggestionShownCount.get()).intValue();
                FinskyPreferences.playGamesInstallSuggestionShownCount.put(Integer.valueOf(i));
                FinskyPreferences.playGamesInstallSuggestionLastTimeShown.put(Long.valueOf(System.currentTimeMillis()));
              }
            }
          });
          break;
          m = 1;
          continue;
          l = ((Long)G.playGamesInstallSuggestionFirstBackoffMs.get()).longValue();
          break label562;
          m = 0;
        }
        if (paramInt1 == 34)
        {
          if (paramIntent != null)
          {
            RedeemCodeResult localRedeemCodeResult = (RedeemCodeResult)paramIntent.getParcelableExtra("RedeemCodeBaseActivity.redeem_code_result");
            if ((localRedeemCodeResult != null) && (localRedeemCodeResult.mLink != null))
            {
              ResolveLink.ResolvedLink localResolvedLink = localRedeemCodeResult.mLink.resolvedLink;
              if (localResolvedLink != null)
              {
                if (!TextUtils.isEmpty(localResolvedLink.browseUrl)) {
                  startActivity(IntentUtils.createBrowseIntent(this, localResolvedLink.browseUrl, null, localResolvedLink.backend, false));
                } else {
                  FinskyLog.wtf("Unexpected missing browseUrl", new Object[0]);
                }
              }
              else {
                FinskyLog.wtf("Unexpected missing resolvedLink", new Object[0]);
              }
            }
          }
        }
        else
        {
          if (paramInt1 == 35)
          {
            if (paramInt2 == -1) {}
            for (boolean bool1 = true;; bool1 = false)
            {
              FinskyApp.get().getEventLogger().logOperationSuccessBackgroundEvent(503, bool1);
              if (!bool1) {
                break;
              }
              FinskyApp.get().getPlayDfeApi(null).invalidatePlusProfile$1385ff();
              this.mHandler.postDelayed(new Runnable()
              {
                public final void run()
                {
                  if (!MainActivity.this.mStateSaved) {
                    MainActivity.this.mDrawerLayout.refresh();
                  }
                }
              }, 3000L);
              break;
            }
          }
          if (paramInt1 == 39)
          {
            if (GPlusUtils.sLastCirclePickerListener != null)
            {
              GPlusUtils.CirclePickerListener localCirclePickerListener;
              ArrayList localArrayList1;
              if (paramInt2 == -1)
              {
                localCirclePickerListener = GPlusUtils.sLastCirclePickerListener;
                localArrayList1 = new ArrayList();
                if (!paramIntent.hasExtra("com.google.android.gms.common.acl.EXTRA_INITIAL_AUDIENCE")) {
                  break label967;
                }
              }
              label967:
              for (Object localObject = paramIntent.getParcelableArrayListExtra("com.google.android.gms.common.acl.EXTRA_INITIAL_AUDIENCE");; localObject = Collections.emptyList())
              {
                if ((localObject != null) && (!((List)localObject).isEmpty())) {
                  localArrayList1.addAll((Collection)localObject);
                }
                ArrayList localArrayList2 = paramIntent.getParcelableArrayListExtra("com.google.android.gms.common.acl.EXTRA_REMOVED_AUDIENCE");
                if (localArrayList2 != null) {
                  localArrayList1.removeAll(localArrayList2);
                }
                ArrayList localArrayList3 = paramIntent.getParcelableArrayListExtra("com.google.android.gms.common.acl.EXTRA_ADDED_AUDIENCE");
                if (localArrayList3 != null) {
                  localArrayList1.addAll(localArrayList3);
                }
                localCirclePickerListener.onCirclesSelected(localArrayList1);
                GPlusUtils.sLastCirclePickerListener = null;
                GPlusUtils.sLastUserToAddObfuscatedId = null;
                GPlusUtils.sIsCirclePickerActive = false;
                break;
              }
            }
          }
          else if ((paramInt1 == 50) && (this.mNavigationManager.isBackStackEmpty())) {
            this.mNavigationManager.goToAggregatedHome(FinskyApp.get().mToc);
          }
        }
      }
    }
  }
  
  public void onBackPressed()
  {
    if (this.mDrawerLayout.isDrawerOpen()) {
      this.mDrawerLayout.closeDrawer();
    }
    PageFragment localPageFragment;
    do
    {
      return;
      localPageFragment = this.mNavigationManager.getActivePage();
    } while (((localPageFragment != null) && (localPageFragment.onBackPressed())) || (this.mNavigationManager.goBack()));
    super.onBackPressed();
  }
  
  protected final void onCleanup()
  {
    FinskyApp.get().drainAllRequests(FinskyApp.get().mRequestQueue.mSequenceGenerator.incrementAndGet(), FinskyApp.get().mBitmapRequestQueue.mSequenceGenerator.incrementAndGet());
    FinskyApp.get().clearCacheAsync(null);
    if (this.mStateSaved)
    {
      FinskyLog.wtf("Should not be here after state was saved", new Object[0]);
      return;
    }
    if (this.mNavigationManager != null)
    {
      this.mNavigationManager.clear();
      this.mNavigationManager.mFragmentManager.executePendingTransactions();
    }
    if (this.mContentFrame != null)
    {
      int i = this.mContentFrame.getChildCount();
      ArrayList localArrayList = new ArrayList();
      for (int j = 0; j < i; j++)
      {
        View localView2 = this.mContentFrame.getChildAt(j);
        int k = localView2.getId();
        if ((k != 2131755275) && (k != 2131755276)) {
          localArrayList.add(localView2);
        }
      }
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        View localView1 = (View)localIterator.next();
        this.mContentFrame.removeView(localView1);
      }
    }
    showLoadingIndicator();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mDrawerLayout.onConfigurationChanged(paramConfiguration);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    if ((paramBundle != null) && (shouldClearBackstack())) {
      paramBundle = null;
    }
    super.onCreate(paramBundle);
    setContentView(2130968828);
    Toolbar localToolbar = (Toolbar)findViewById(2131755196);
    if ((localToolbar instanceof FinskySearchToolbar)) {
      ((FinskySearchToolbar)localToolbar).configure(new FinskySearchToolbar.Configurator(this));
    }
    setSupportActionBar(localToolbar);
    this.mSavedInstanceState = paramBundle;
    this.mContentFrame = ((ViewGroup)findViewById(2131755234));
    this.mNavigationManager = new NavigationManager(this);
    this.mNavigationManager.init(this);
    this.mNavigationManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
    {
      private boolean initialCleanupDone;
      
      public final void onBackStackChanged()
      {
        if (!this.initialCleanupDone)
        {
          MainActivity.this.hideLoadingIndicator();
          MainActivity.this.hideErrorMessage();
          this.initialCleanupDone = true;
        }
        MainActivity.this.syncDrawerArrowDrawable();
        NavigationManager localNavigationManager = MainActivity.this.mNavigationManager;
        if (localNavigationManager.mBackStack.isEmpty()) {}
        for (boolean bool = false; bool; bool = ((NavigationState)localNavigationManager.mBackStack.peek()).isActionBarOverlayEnabled)
        {
          MainActivity.this.enableActionBarOverlay();
          return;
        }
        MainActivity.this.disableActionBarOverlay();
      }
    });
    NavigationManager localNavigationManager;
    ArrayList localArrayList;
    FinskyDrawerLayout localFinskyDrawerLayout;
    boolean bool1;
    label267:
    String str;
    int j;
    label381:
    int k;
    label462:
    GoogleApiClient.Builder localBuilder;
    Api localApi;
    People.PeopleOptions1p.Builder localBuilder1;
    if (paramBundle != null)
    {
      localNavigationManager = this.mNavigationManager;
      localArrayList = paramBundle.getParcelableArrayList("nm_state");
      if ((localArrayList == null) || (localArrayList.size() == 0) || (localNavigationManager.getActivePage() == null)) {
        this.mLastShownErrorHash = paramBundle.getInt("last_shown_error_hash");
      }
    }
    else
    {
      if (!this.mNavigationManager.isBackStackEmpty())
      {
        hideLoadingIndicator();
        hideErrorMessage();
      }
      this.mActionBarHelper = new ActionBarHelper(this.mNavigationManager, this, this);
      this.mDrawerLayout = ((FinskyDrawerLayout)findViewById(2131755700));
      localFinskyDrawerLayout = this.mDrawerLayout;
      FinskyApp localFinskyApp = FinskyApp.get();
      if (GooglePlayServicesUtil.isSidewinderDevice(localFinskyDrawerLayout.getContext())) {
        localFinskyDrawerLayout.setIsMiniProfile(true);
      }
      int i = FinskySearchToolbar.getToolbarHeight(this);
      if ((paramBundle == null) || (!paramBundle.getBoolean("FinskyDrawerLayout.isAccountListExpanded", false))) {
        break label700;
      }
      bool1 = true;
      PlayDfeApiProvider localPlayDfeApiProvider = localFinskyApp.getPlayDfeApiProvider();
      BitmapLoader localBitmapLoader = localFinskyApp.mBitmapLoader;
      if (localFinskyDrawerLayout.mIsConfigured) {
        PlayCommonLog.wtf("PlayDrawer is already configured", new Object[0]);
      }
      localFinskyDrawerLayout.mIsConfigured = true;
      localFinskyDrawerLayout.setActionBarHeight(i);
      localFinskyDrawerLayout.mDrawerAdapter = new PlayDrawerAdapter(this, bool1, localFinskyDrawerLayout, localPlayDfeApiProvider, localBitmapLoader, localFinskyDrawerLayout, localFinskyDrawerLayout.mDrawerList, localFinskyDrawerLayout.mIsMiniProfile);
      localFinskyDrawerLayout.mDrawerList.setAdapter(localFinskyDrawerLayout.mDrawerAdapter);
      str = getString(R.string.play_drawer_title);
      j = GravityCompat.getAbsoluteGravity(8388611, ViewCompat.getLayoutDirection(localFinskyDrawerLayout));
      if (j != 3) {
        break label706;
      }
      localFinskyDrawerLayout.mTitleLeft = str;
      localFinskyDrawerLayout.mDrawerToggle = new ActionBarDrawerToggle(this, localFinskyDrawerLayout, R.string.play_drawer_open, R.string.play_drawer_close);
      localFinskyDrawerLayout.setCurrentAvatarClickable(true);
      localFinskyDrawerLayout.mMainActivity = this;
      localFinskyDrawerLayout.mNavigationManager = this.mNavigationManager;
      localFinskyDrawerLayout.refresh();
      localFinskyDrawerLayout.mNavigationManager.addOnBackStackChangedListener(new FinskyDrawerLayout.3(localFinskyDrawerLayout));
      localFinskyDrawerLayout.mMainActivity.syncDrawerArrowDrawable();
      if ((paramBundle == null) || (!paramBundle.getBoolean("FinskyDrawerLayout.isDrawerOpened", false))) {
        break label721;
      }
      k = 1;
      if (k != 0) {
        localFinskyDrawerLayout.mMainActivity.enterDrawerOpenMode();
      }
      ActionBar localActionBar = getDelegate().getSupportActionBar();
      this.mDrawerArrowDrawable = new PlayDrawerArrowDrawable(localActionBar.getThemedContext());
      localActionBar.setHomeAsUpIndicator(this.mDrawerArrowDrawable);
      syncDrawerArrowDrawable();
      setDefaultKeyMode(3);
      localBuilder = new GoogleApiClient.Builder(this);
      localApi = People.API_1P;
      localBuilder1 = new People.PeopleOptions1p.Builder();
      localBuilder1.zzbsR = 121;
      if (localBuilder1.zzbsR < 0) {
        break label727;
      }
    }
    label700:
    label706:
    label721:
    label727:
    for (boolean bool2 = true;; bool2 = false)
    {
      zzx.zzb(bool2, "Must provide valid client application ID!");
      this.mPeopleClient = localBuilder.addApi(localApi, new People.PeopleOptions1p(localBuilder1, (byte)0)).build();
      this.mLocationHelper = new LocationHelper(this);
      if ((FinskyApp.get().getExperiments().isEnabled(12603396L)) && (zzq.zzdC(14)))
      {
        GoogleHelpTogglingRegister.zzbbb = new zzi(getApplicationContext());
        getApplication().registerActivityLifecycleCallbacks(GoogleHelpTogglingRegister.zzbbb);
        GoogleHelpTogglingRegister.zzbba = true;
      }
      return;
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        NavigationState localNavigationState = (NavigationState)localIterator.next();
        localNavigationManager.mBackStack.push(localNavigationState);
      }
      break;
      bool1 = false;
      break label267;
      if (j != 5) {
        break label381;
      }
      localFinskyDrawerLayout.mTitleRight = str;
      break label381;
      k = 0;
      break label462;
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(2131820544, paramMenu);
    ActionBarHelper localActionBarHelper = this.mActionBarHelper;
    localActionBarHelper.mSearchItem = paramMenu.findItem(2131755201);
    FinskySearchToolbar localFinskySearchToolbar = localActionBarHelper.mToolbar;
    MenuItem localMenuItem = paramMenu.findItem(2131755201);
    View localView1 = MenuItemCompat.getActionView(localMenuItem);
    if ((localFinskySearchToolbar.mLegacySearchActionView == null) && (localView1 != null) && (!(localView1 instanceof PlaySearch))) {
      localFinskySearchToolbar.mLegacySearchActionView = localView1;
    }
    if (localFinskySearchToolbar.mSearchItem != null)
    {
      MenuItemCompat.setActionProvider(localFinskySearchToolbar.mSearchItem, null);
      MenuItemCompat.setOnActionExpandListener(localFinskySearchToolbar.mSearchItem, null);
    }
    if (localMenuItem != null)
    {
      MenuItemCompat.setActionProvider(localMenuItem, localFinskySearchToolbar.getActionProvider());
      MenuItemCompat.setOnActionExpandListener(localMenuItem, localFinskySearchToolbar);
      if ((localFinskySearchToolbar.mExpandedMenuItemId == 2131755201) && (!localFinskySearchToolbar.hasExpandedActionView())) {
        MenuItemCompat.expandActionView(localMenuItem);
      }
    }
    localFinskySearchToolbar.mSearchItem = localMenuItem;
    View localView2 = MenuItemCompat.getActionView(localActionBarHelper.mSearchItem);
    if (!(localView2 instanceof FinskySearch))
    {
      localActionBarHelper.mSearchView = ((SearchView)localView2);
      localActionBarHelper.mSearchView.setOnQueryTextFocusChangeListener(new ActionBarHelper.4(localActionBarHelper));
      SearchManager localSearchManager = (SearchManager)getSystemService("search");
      localActionBarHelper.mSearchView.setSearchableInfo(localSearchManager.getSearchableInfo(getComponentName()));
    }
    localActionBarHelper.mTranslateItem = paramMenu.findItem(2131756239);
    localActionBarHelper.mAutoUpdateItem = paramMenu.findItem(2131756240);
    localActionBarHelper.mEnvironmentItem = paramMenu.findItem(2131756241);
    if (localActionBarHelper.mNavigationManager == null)
    {
      localView2.setVisibility(8);
      localActionBarHelper.mTranslateItem.setVisible(false);
      localActionBarHelper.mAutoUpdateItem.setVisible(false);
      localActionBarHelper.mEnvironmentItem.setVisible(false);
      if (!ActionBarHelper.IS_SEARCH_ALWAYS_VISIBLE) {
        localActionBarHelper.mSearchItem.setVisible(false);
      }
    }
    localActionBarHelper.mIsMenuConfigured = true;
    localActionBarHelper.syncActions();
    return true;
  }
  
  protected void onDestroy()
  {
    this.mNavigationManager.mActivity = null;
    if ((GoogleHelpTogglingRegister.zzbba) && (GoogleHelpTogglingRegister.zzbba))
    {
      GoogleHelpTogglingRegister.zzbba = false;
      zzi localzzi = GoogleHelpTogglingRegister.zzbbb;
      zzc.zza(localzzi.mApiClient, new zzi.3(localzzi));
      getApplication().unregisterActivityLifecycleCallbacks(GoogleHelpTogglingRegister.zzbbb);
    }
    super.onDestroy();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 32: 
      String str = paramBundle.getString("error_package_name");
      FinskyApp.get().mInstaller.uninstallAssetSilently(str, false);
      return;
    case 36: 
      AutoUpdateMigrationHelper.updateDialogTracking();
      return;
    case 40: 
      FinskyLog.d("Shutting down because download manager remains disabled", new Object[0]);
      System.exit(0);
      return;
    }
    FinskyLog.d("Shutting down because gms core remains disabled", new Object[0]);
    System.exit(0);
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    setIntent(paramIntent);
    if (!this.mStateSaved)
    {
      handleIntent();
      onNewIntentDirect(paramIntent);
      return;
    }
    this.mStateSaved = false;
    super.onNewIntent(paramIntent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = 1;
    switch (paramMenuItem.getItemId())
    {
    default: 
      i = super.onOptionsItemSelected(paramMenuItem);
    case 16908332: 
    case 2131756239: 
      ActionBarHelper localActionBarHelper2;
      do
      {
        PageFragment localPageFragment2;
        do
        {
          return i;
          if (isAtTopLevelDrawerDestination())
          {
            FinskyDrawerLayout localFinskyDrawerLayout = this.mDrawerLayout;
            localFinskyDrawerLayout.checkIsConfigured();
            if (PlayDrawerLayout.isDrawerOpen(localFinskyDrawerLayout.mDrawerRoot))
            {
              localFinskyDrawerLayout.closeDrawer(localFinskyDrawerLayout.mDrawerRoot);
              return i;
            }
            localFinskyDrawerLayout.openDrawer(localFinskyDrawerLayout.mDrawerRoot);
            return i;
          }
          this.mDrawerLayout.closeDrawer();
          localPageFragment2 = this.mNavigationManager.getActivePage();
        } while (((localPageFragment2 != null) && (localPageFragment2.onBackPressed())) || (this.mNavigationManager.goUp()));
        super.onBackPressed();
        return i;
        localActionBarHelper2 = this.mActionBarHelper;
      } while (localActionBarHelper2.mExpandedModeTranslatable == null);
      localActionBarHelper2.mExpandedModeTranslatable.toggleTranslation();
      boolean bool = localActionBarHelper2.mExpandedModeTranslatable.isShowingTranslation();
      PageFragment localPageFragment1 = localActionBarHelper2.mNavigationManager.getActivePage();
      FinskyEventLog localFinskyEventLog2 = FinskyApp.get().getEventLogger();
      int i6;
      MenuItem localMenuItem;
      if (bool)
      {
        i6 = 256;
        localFinskyEventLog2.logClickEvent(i6, null, localPageFragment1);
        localMenuItem = localActionBarHelper2.mTranslateItem;
        if (!bool) {
          break label269;
        }
      }
      for (int i7 = 2131362664;; i7 = 2131362803)
      {
        localMenuItem.setTitle(i7);
        return i;
        i6 = 257;
        break;
      }
    case 2131756240: 
      label269:
      ActionBarHelper localActionBarHelper1 = this.mActionBarHelper;
      Document localDocument = localActionBarHelper1.mNavigationManager.getCurrentDocument();
      if (localDocument == null) {
        FinskyLog.wtf("tried to operate on a null doc", new Object[0]);
      }
      for (;;)
      {
        localActionBarHelper1.syncDetailsPageMenuItem();
        return i;
        if (localDocument.mDocument.backendId == 3) {
          break;
        }
        FinskyLog.wtf("tried to operate on a non-apps doc.", new Object[0]);
      }
      String str = localDocument.mDocument.docid;
      label358:
      int m;
      label408:
      label416:
      FinskyEventLog localFinskyEventLog1;
      if (!AutoUpdateUtils.isAutoUpdateEnabled(str))
      {
        int j = i;
        FragmentManager localFragmentManager = getSupportFragmentManager();
        AppStates localAppStates = FinskyApp.get().mAppStates;
        AppStates.AppState localAppState = FinskyApp.get().mAppStates.getApp(str);
        if ((localAppState.installerData == null) || (localAppState.installerData.autoUpdate != i)) {
          break label544;
        }
        m = i;
        if (j == 0) {
          break label550;
        }
        int i1 = i;
        localAppStates.mStateStore.setAutoUpdate(str, i1);
        if ((j != 0) && (!((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue())) {
          new AutoUpdateUtils.AutoUpdateDialog().show(localFragmentManager, "auto_update_dialog");
        }
        localFinskyEventLog1 = FinskyApp.get().getEventLogger();
        if (j == 0) {
          break label556;
        }
      }
      label544:
      label550:
      label556:
      int i4;
      for (int i3 = i;; i4 = 0)
      {
        int i5 = 0;
        if (m != 0) {
          i5 = i;
        }
        Integer localInteger = Integer.valueOf(i5);
        localFinskyEventLog1.sendBackgroundEventToSinks(new BackgroundEventBuilder(403).setToSetting(Integer.valueOf(i3)).setFromSetting(localInteger).setDocument(str).event);
        break;
        int k = 0;
        break label358;
        int n = 0;
        break label408;
        int i2 = 2;
        break label416;
      }
    }
    Toast.makeText(this, "Environment indicator (not visible externally)", 0).show();
    return i;
  }
  
  protected void onPause()
  {
    super.onPause();
    FinskyApp.get().mNotificationHelper.setNotificationListener(null);
    this.mSequenceNumberToDrainFrom = FinskyApp.get().mRequestQueue.mSequenceGenerator.incrementAndGet();
    this.mBitmapSequenceNumberToDrainFrom = FinskyApp.get().mBitmapRequestQueue.mSequenceGenerator.incrementAndGet();
    FinskyPreferences.lastTimeBackStackUpdatedMs.put(Long.valueOf(System.currentTimeMillis()));
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    case 33: 
    case 34: 
    case 35: 
    case 39: 
    case 43: 
    case 44: 
    case 45: 
    case 46: 
    default: 
      if (this.mNavigationManager != null)
      {
        NavigationManager localNavigationManager2 = this.mNavigationManager;
        if ((paramInt == 1) && (paramBundle != null))
        {
          String str = paramBundle.getString("dialog_details_url");
          if ((str != null) && (localNavigationManager2.canNavigate())) {
            localNavigationManager2.goToDocPage(str);
          }
        }
      }
      break;
    }
    Intent localIntent1;
    do
    {
      Intent localIntent2;
      do
      {
        return;
        AutoUpdateMigrationHelper.updateDialogTracking();
        FinskyPreferences.autoUpdateEnabled.put(Boolean.valueOf(true));
        UpdateChecker.migrateAllAppsToUseGlobalUpdateSetting$49bdfac8(FinskyApp.get().mAppStates, true, "cleanup");
        FinskyPreferences.hasAcceptedAutoUpdateMigrationDialog.put(Boolean.valueOf(true));
        return;
        GaiaRecoveryHelper.onPositiveGaiaRecoveryDialogResponse();
        return;
        NavigationManager localNavigationManager1 = this.mNavigationManager;
        new Handler().post(new PlayGamesInstallHelper.1(localNavigationManager1));
        return;
        FinskyLog.d("Attempting to enable download manager", new Object[0]);
        getPackageManager().setApplicationEnabledSetting("com.android.providers.downloads", 1, 0);
        showRestartRequiredDialog();
        return;
        FinskyLog.d("Attempting to enable gms core", new Object[0]);
        getPackageManager().setApplicationEnabledSetting("com.google.android.gms", 1, 0);
        showRestartRequiredDialog();
        return;
        FinskyLog.d("Shutting down after download manager or gms core re-enabled", new Object[0]);
        System.exit(0);
        return;
        localIntent2 = new Intent("android.settings.INTERNAL_STORAGE_SETTINGS");
      } while (localIntent2.resolveActivity(getPackageManager()) == null);
      startActivity(localIntent2);
      return;
      localIntent1 = new Intent("android.settings.MEMORY_CARD_SETTINGS");
    } while (localIntent1.resolveActivity(getPackageManager()) == null);
    startActivity(localIntent1);
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    this.mDrawerLayout.mMainActivity.syncDrawerArrowDrawable();
  }
  
  /* Error */
  protected final void onReady(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokespecial 2131	com/google/android/finsky/activities/AuthenticatedActivity:onReady	(Z)V
    //   5: getstatic 2136	com/google/android/finsky/utils/SessionStatsLogger:sHaveLoggedSessionStats	Z
    //   8: ifne +18 -> 26
    //   11: getstatic 2139	com/google/android/finsky/config/G:enableSessionStatsLog	Lcom/google/android/play/utils/config/GservicesValue;
    //   14: invokevirtual 705	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   17: checkcast 239	java/lang/Boolean
    //   20: invokevirtual 242	java/lang/Boolean:booleanValue	()Z
    //   23: ifne +171 -> 194
    //   26: aload_0
    //   27: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   30: invokevirtual 106	com/google/android/finsky/FinskyApp:getCurrentAccountName	()Ljava/lang/String;
    //   33: invokestatic 2143	com/google/android/finsky/activities/GaiaRecoveryHelper:prefetchAndCacheGaiaAuthRecoveryIntent	(Landroid/content/Context;Ljava/lang/String;)V
    //   36: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   39: aconst_null
    //   40: invokevirtual 302	com/google/android/finsky/FinskyApp:getDfeApi	(Ljava/lang/String;)Lcom/google/android/finsky/api/DfeApi;
    //   43: astore_2
    //   44: aload_2
    //   45: invokeinterface 2148 1 0
    //   50: astore_3
    //   51: invokestatic 696	java/lang/System:currentTimeMillis	()J
    //   54: lstore 4
    //   56: aload_3
    //   57: invokestatic 2153	com/google/android/finsky/billing/PromptForFopHelper:isExperimentEnabled	(Ljava/lang/String;)Z
    //   60: ifne +697 -> 757
    //   63: getstatic 2156	com/google/android/finsky/utils/FinskyLog:DEBUG	Z
    //   66: ifeq +25 -> 91
    //   69: iconst_1
    //   70: anewarray 184	java/lang/Object
    //   73: astore 9
    //   75: aload 9
    //   77: iconst_0
    //   78: aload_3
    //   79: invokestatic 332	com/google/android/finsky/utils/FinskyLog:scrubPii	(Ljava/lang/String;)Ljava/lang/String;
    //   82: aastore
    //   83: ldc_w 2158
    //   86: aload 9
    //   88: invokestatic 338	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   91: iload_1
    //   92: ifeq +739 -> 831
    //   95: aload_0
    //   96: getfield 1510	com/google/android/finsky/activities/MainActivity:mSavedInstanceState	Landroid/os/Bundle;
    //   99: ifnull +23 -> 122
    //   102: aload_0
    //   103: getfield 82	com/google/android/finsky/activities/MainActivity:mNavigationManager	Lcom/google/android/finsky/navigationmanager/NavigationManager;
    //   106: invokevirtual 594	com/google/android/finsky/navigationmanager/NavigationManager:isBackStackEmpty	()Z
    //   109: ifne +13 -> 122
    //   112: aload_0
    //   113: getfield 82	com/google/android/finsky/activities/MainActivity:mNavigationManager	Lcom/google/android/finsky/navigationmanager/NavigationManager;
    //   116: invokevirtual 615	com/google/android/finsky/navigationmanager/NavigationManager:getActivePage	()Lcom/google/android/finsky/fragments/PageFragment;
    //   119: ifnonnull +712 -> 831
    //   122: aload_0
    //   123: invokespecial 1935	com/google/android/finsky/activities/MainActivity:handleIntent	()V
    //   126: aload_0
    //   127: getfield 114	com/google/android/finsky/activities/MainActivity:mDrawerLayout	Lcom/google/android/finsky/layout/play/FinskyDrawerLayout;
    //   130: invokevirtual 1641	com/google/android/finsky/layout/play/FinskyDrawerLayout:refresh	()V
    //   133: aload_0
    //   134: aconst_null
    //   135: putfield 1510	com/google/android/finsky/activities/MainActivity:mSavedInstanceState	Landroid/os/Bundle;
    //   138: getstatic 2160	com/google/android/finsky/activities/MainActivity:sBillingInitialized	Z
    //   141: ifne +52 -> 193
    //   144: iconst_1
    //   145: putstatic 2160	com/google/android/finsky/activities/MainActivity:sBillingInitialized	Z
    //   148: ldc_w 2162
    //   151: iconst_0
    //   152: anewarray 184	java/lang/Object
    //   155: invokestatic 338	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   158: new 61	android/os/Handler
    //   161: dup
    //   162: invokestatic 2168	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   165: invokespecial 2171	android/os/Handler:<init>	(Landroid/os/Looper;)V
    //   168: new 2173	com/google/android/finsky/activities/MainActivity$4
    //   171: dup
    //   172: aload_0
    //   173: invokespecial 2174	com/google/android/finsky/activities/MainActivity$4:<init>	(Lcom/google/android/finsky/activities/MainActivity;)V
    //   176: getstatic 2177	com/google/android/finsky/config/G:initializeBillingDelayMs	Lcom/google/android/play/utils/config/GservicesValue;
    //   179: invokevirtual 705	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   182: checkcast 853	java/lang/Integer
    //   185: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   188: i2l
    //   189: invokevirtual 1362	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   192: pop
    //   193: return
    //   194: iconst_1
    //   195: putstatic 2136	com/google/android/finsky/utils/SessionStatsLogger:sHaveLoggedSessionStats	Z
    //   198: new 2179	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData
    //   201: dup
    //   202: invokespecial 2180	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:<init>	()V
    //   205: astore 10
    //   207: aload 10
    //   209: getstatic 2026	com/google/android/finsky/utils/FinskyPreferences:autoUpdateEnabled	Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   212: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   215: checkcast 239	java/lang/Boolean
    //   218: invokevirtual 242	java/lang/Boolean:booleanValue	()Z
    //   221: putfield 2183	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:globalAutoUpdateEnabled	Z
    //   224: aload 10
    //   226: iconst_1
    //   227: putfield 2186	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasGlobalAutoUpdateEnabled	Z
    //   230: aload 10
    //   232: getstatic 2189	com/google/android/finsky/utils/FinskyPreferences:autoUpdateWifiOnly	Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   235: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   238: checkcast 239	java/lang/Boolean
    //   241: invokevirtual 242	java/lang/Boolean:booleanValue	()Z
    //   244: putfield 2192	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:globalAutoUpdateOverWifiOnly	Z
    //   247: aload 10
    //   249: iconst_1
    //   250: putfield 2195	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasGlobalAutoUpdateOverWifiOnly	Z
    //   253: aload 10
    //   255: getstatic 1240	com/google/android/finsky/utils/FinskyPreferences:autoUpdateMigrationDialogShownCount	Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   258: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   261: checkcast 853	java/lang/Integer
    //   264: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   267: putfield 2198	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:autoUpdateCleanupDialogNumTimesShown	I
    //   270: aload 10
    //   272: iconst_1
    //   273: putfield 2201	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasAutoUpdateCleanupDialogNumTimesShown	Z
    //   276: aload_0
    //   277: invokestatic 2207	com/google/android/finsky/api/AccountHandler:getAccounts	(Landroid/content/Context;)[Landroid/accounts/Account;
    //   280: astore 12
    //   282: aload 12
    //   284: ifnull +17 -> 301
    //   287: aload 10
    //   289: aload 12
    //   291: arraylength
    //   292: putfield 2210	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:numAccountsOnDevice	I
    //   295: aload 10
    //   297: iconst_1
    //   298: putfield 2213	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasNumAccountsOnDevice	Z
    //   301: aload_0
    //   302: invokestatic 2219	com/google/android/finsky/receivers/NetworkStateChangedReceiver:getCachedNetworkInfo	(Landroid/content/Context;)Landroid/net/NetworkInfo;
    //   305: astore 13
    //   307: aload 13
    //   309: ifnull +35 -> 344
    //   312: aload 10
    //   314: aload 13
    //   316: invokevirtual 2224	android/net/NetworkInfo:getType	()I
    //   319: putfield 2227	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:networkType	I
    //   322: aload 10
    //   324: iconst_1
    //   325: putfield 2230	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasNetworkType	Z
    //   328: aload 10
    //   330: aload 13
    //   332: invokevirtual 2233	android/net/NetworkInfo:getSubtype	()I
    //   335: putfield 2236	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:networkSubType	I
    //   338: aload 10
    //   340: iconst_1
    //   341: putfield 2239	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasNetworkSubType	Z
    //   344: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   347: invokevirtual 106	com/google/android/finsky/FinskyApp:getCurrentAccountName	()Ljava/lang/String;
    //   350: astore 14
    //   352: aload 14
    //   354: invokestatic 1328	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   357: ifne +19 -> 376
    //   360: aload 10
    //   362: aload 14
    //   364: invokestatic 2244	com/google/android/finsky/config/PurchaseAuthUtils:getPurchaseAuthType	(Ljava/lang/String;)I
    //   367: putfield 2247	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:purchaseAuthType	I
    //   370: aload 10
    //   372: iconst_1
    //   373: putfield 2250	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasPurchaseAuthType	Z
    //   376: aload 10
    //   378: getstatic 2253	com/google/android/finsky/utils/FinskyPreferences:contentFilterLevel	Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   381: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   384: checkcast 853	java/lang/Integer
    //   387: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   390: putfield 2255	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:contentFilterLevel	I
    //   393: aload 10
    //   395: iconst_1
    //   396: putfield 2258	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasContentFilterLevel	Z
    //   399: getstatic 50	android/os/Build$VERSION:SDK_INT	I
    //   402: bipush 17
    //   404: if_icmplt +313 -> 717
    //   407: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   410: invokevirtual 2262	com/google/android/finsky/FinskyApp:getContentResolver	()Landroid/content/ContentResolver;
    //   413: ldc_w 2264
    //   416: iconst_m1
    //   417: invokestatic 2269	android/provider/Settings$Global:getInt	(Landroid/content/ContentResolver;Ljava/lang/String;I)I
    //   420: istore 15
    //   422: iload 15
    //   424: iconst_m1
    //   425: if_icmpne +418 -> 843
    //   428: ldc_w 2271
    //   431: iconst_0
    //   432: anewarray 184	java/lang/Object
    //   435: invokestatic 358	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   438: new 2273	com/google/android/finsky/analytics/PlayStore$PromptForFopData
    //   441: dup
    //   442: invokespecial 2274	com/google/android/finsky/analytics/PlayStore$PromptForFopData:<init>	()V
    //   445: astore 16
    //   447: aload 16
    //   449: getstatic 2278	com/google/android/finsky/utils/FinskyPreferences:accountHasFop	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   452: aload 14
    //   454: invokevirtual 2283	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   457: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   460: checkcast 239	java/lang/Boolean
    //   463: invokevirtual 242	java/lang/Boolean:booleanValue	()Z
    //   466: putfield 2286	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasFop	Z
    //   469: aload 16
    //   471: iconst_1
    //   472: putfield 2289	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasHasFop	Z
    //   475: aload 16
    //   477: getstatic 2292	com/google/android/finsky/utils/FinskyPreferences:promptForFopAddedFop	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   480: aload 14
    //   482: invokevirtual 2283	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   485: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   488: checkcast 239	java/lang/Boolean
    //   491: invokevirtual 242	java/lang/Boolean:booleanValue	()Z
    //   494: putfield 2295	com/google/android/finsky/analytics/PlayStore$PromptForFopData:fopAdded	Z
    //   497: aload 16
    //   499: iconst_1
    //   500: putfield 2298	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasFopAdded	Z
    //   503: aload 16
    //   505: getstatic 2301	com/google/android/finsky/utils/FinskyPreferences:promptForFopNumDialogImpressions	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   508: aload 14
    //   510: invokevirtual 2283	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   513: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   516: checkcast 853	java/lang/Integer
    //   519: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   522: putfield 2304	com/google/android/finsky/analytics/PlayStore$PromptForFopData:numDialogShown	I
    //   525: aload 16
    //   527: iconst_1
    //   528: putfield 2307	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasNumDialogShown	Z
    //   531: aload 16
    //   533: getstatic 2310	com/google/android/finsky/utils/FinskyPreferences:promptForFopNumFopSelectorImpressions	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   536: aload 14
    //   538: invokevirtual 2283	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   541: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   544: checkcast 853	java/lang/Integer
    //   547: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   550: putfield 2313	com/google/android/finsky/analytics/PlayStore$PromptForFopData:numFopSelectorShown	I
    //   553: aload 16
    //   555: iconst_1
    //   556: putfield 2316	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasNumFopSelectorShown	Z
    //   559: aload 16
    //   561: getstatic 2319	com/google/android/finsky/utils/FinskyPreferences:promptForFopNumSnoozed	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   564: aload 14
    //   566: invokevirtual 2283	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   569: invokevirtual 237	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   572: checkcast 853	java/lang/Integer
    //   575: invokevirtual 1243	java/lang/Integer:intValue	()I
    //   578: putfield 2322	com/google/android/finsky/analytics/PlayStore$PromptForFopData:numSnooze	I
    //   581: aload 16
    //   583: iconst_1
    //   584: putfield 2325	com/google/android/finsky/analytics/PlayStore$PromptForFopData:hasNumSnooze	Z
    //   587: aload 10
    //   589: aload 16
    //   591: putfield 2329	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:promptForFopData	Lcom/google/android/finsky/analytics/PlayStore$PromptForFopData;
    //   594: aload_0
    //   595: invokevirtual 2332	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   598: ldc_w 2334
    //   601: ldc_w 2336
    //   604: ldc_w 2338
    //   607: invokevirtual 2342	android/content/res/Resources:getIdentifier	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    //   610: istore 17
    //   612: iload 17
    //   614: ifeq +22 -> 636
    //   617: aload 10
    //   619: invokestatic 2345	android/content/res/Resources:getSystem	()Landroid/content/res/Resources;
    //   622: iload 17
    //   624: invokevirtual 2349	android/content/res/Resources:getInteger	(I)I
    //   627: putfield 2352	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:downloadDataDirSizeMb	I
    //   630: aload 10
    //   632: iconst_1
    //   633: putfield 2355	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasDownloadDataDirSizeMb	Z
    //   636: aload 10
    //   638: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   641: invokevirtual 2262	com/google/android/finsky/FinskyApp:getContentResolver	()Landroid/content/ContentResolver;
    //   644: ldc_w 2357
    //   647: invokestatic 2363	android/provider/Settings$Secure:getLong	(Landroid/content/ContentResolver;Ljava/lang/String;)J
    //   650: putfield 2366	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:recommendedMaxDownloadOverMobileBytes	J
    //   653: aload 10
    //   655: iconst_1
    //   656: putfield 2369	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasRecommendedMaxDownloadOverMobileBytes	Z
    //   659: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   662: invokevirtual 638	com/google/android/finsky/FinskyApp:getEventLogger	()Lcom/google/android/finsky/analytics/FinskyEventLog;
    //   665: astore 19
    //   667: new 2034	com/google/android/finsky/analytics/BackgroundEventBuilder
    //   670: dup
    //   671: iconst_1
    //   672: invokespecial 2036	com/google/android/finsky/analytics/BackgroundEventBuilder:<init>	(I)V
    //   675: astore 20
    //   677: aload 20
    //   679: getfield 2051	com/google/android/finsky/analytics/BackgroundEventBuilder:event	Lcom/google/android/finsky/analytics/PlayStore$PlayStoreBackgroundActionEvent;
    //   682: aload 10
    //   684: putfield 2375	com/google/android/finsky/analytics/PlayStore$PlayStoreBackgroundActionEvent:sessionInfo	Lcom/google/android/finsky/analytics/PlayStore$PlayStoreSessionData;
    //   687: aload 19
    //   689: aload 20
    //   691: getfield 2051	com/google/android/finsky/analytics/BackgroundEventBuilder:event	Lcom/google/android/finsky/analytics/PlayStore$PlayStoreBackgroundActionEvent;
    //   694: invokevirtual 2055	com/google/android/finsky/analytics/FinskyEventLog:sendBackgroundEventToSinks	(Lcom/google/android/finsky/analytics/PlayStore$PlayStoreBackgroundActionEvent;)V
    //   697: goto -671 -> 26
    //   700: astore 11
    //   702: aload 11
    //   704: ldc_w 2377
    //   707: iconst_0
    //   708: anewarray 184	java/lang/Object
    //   711: invokestatic 2379	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   714: goto -688 -> 26
    //   717: invokestatic 102	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   720: invokevirtual 2262	com/google/android/finsky/FinskyApp:getContentResolver	()Landroid/content/ContentResolver;
    //   723: ldc_w 2264
    //   726: iconst_m1
    //   727: invokestatic 2380	android/provider/Settings$Secure:getInt	(Landroid/content/ContentResolver;Ljava/lang/String;I)I
    //   730: istore 15
    //   732: goto -310 -> 422
    //   735: aload 10
    //   737: iload 21
    //   739: putfield 2383	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:allowUnknownSources	Z
    //   742: aload 10
    //   744: iconst_1
    //   745: putfield 2386	com/google/android/finsky/analytics/PlayStore$PlayStoreSessionData:hasAllowUnknownSources	Z
    //   748: goto -310 -> 438
    //   751: iconst_0
    //   752: istore 21
    //   754: goto -19 -> 735
    //   757: aload_3
    //   758: lload 4
    //   760: invokestatic 2390	com/google/android/finsky/billing/PromptForFopHelper:isHasFopCacheValid	(Ljava/lang/String;J)Z
    //   763: ifne -672 -> 91
    //   766: aload_3
    //   767: lload 4
    //   769: invokestatic 2393	com/google/android/finsky/billing/PromptForFopHelper:isSnoozed	(Ljava/lang/String;J)Z
    //   772: ifeq +34 -> 806
    //   775: getstatic 2156	com/google/android/finsky/utils/FinskyLog:DEBUG	Z
    //   778: ifeq -687 -> 91
    //   781: iconst_1
    //   782: anewarray 184	java/lang/Object
    //   785: astore 8
    //   787: aload 8
    //   789: iconst_0
    //   790: aload_3
    //   791: invokestatic 332	com/google/android/finsky/utils/FinskyLog:scrubPii	(Ljava/lang/String;)Ljava/lang/String;
    //   794: aastore
    //   795: ldc_w 2395
    //   798: aload 8
    //   800: invokestatic 338	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   803: goto -712 -> 91
    //   806: aload_2
    //   807: new 2397	com/google/android/finsky/billing/PromptForFopHelper$1
    //   810: dup
    //   811: aload_3
    //   812: invokespecial 2398	com/google/android/finsky/billing/PromptForFopHelper$1:<init>	(Ljava/lang/String;)V
    //   815: new 2400	com/google/android/finsky/billing/PromptForFopHelper$2
    //   818: dup
    //   819: invokespecial 2401	com/google/android/finsky/billing/PromptForFopHelper$2:<init>	()V
    //   822: invokeinterface 2405 3 0
    //   827: pop
    //   828: goto -737 -> 91
    //   831: aload_0
    //   832: invokespecial 2407	com/google/android/finsky/activities/MainActivity:maybeClearBackstack	()V
    //   835: goto -709 -> 126
    //   838: astore 18
    //   840: goto -181 -> 659
    //   843: iload 15
    //   845: ifeq -94 -> 751
    //   848: iconst_1
    //   849: istore 21
    //   851: goto -116 -> 735
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	854	0	this	MainActivity
    //   0	854	1	paramBoolean	boolean
    //   43	764	2	localDfeApi	DfeApi
    //   50	762	3	str1	String
    //   54	714	4	l	long
    //   785	14	8	arrayOfObject1	Object[]
    //   73	14	9	arrayOfObject2	Object[]
    //   205	538	10	localPlayStoreSessionData	com.google.android.finsky.analytics.PlayStore.PlayStoreSessionData
    //   700	3	11	localException	Exception
    //   280	10	12	arrayOfAccount	Account[]
    //   305	26	13	localNetworkInfo	android.net.NetworkInfo
    //   350	215	14	str2	String
    //   420	424	15	i	int
    //   445	145	16	localPromptForFopData	com.google.android.finsky.analytics.PlayStore.PromptForFopData
    //   610	13	17	j	int
    //   838	1	18	localSettingNotFoundException	android.provider.Settings.SettingNotFoundException
    //   665	23	19	localFinskyEventLog	FinskyEventLog
    //   675	15	20	localBackgroundEventBuilder	BackgroundEventBuilder
    //   737	113	21	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   198	282	700	java/lang/Exception
    //   287	301	700	java/lang/Exception
    //   301	307	700	java/lang/Exception
    //   312	344	700	java/lang/Exception
    //   344	376	700	java/lang/Exception
    //   376	422	700	java/lang/Exception
    //   428	438	700	java/lang/Exception
    //   438	612	700	java/lang/Exception
    //   617	636	700	java/lang/Exception
    //   636	659	700	java/lang/Exception
    //   659	697	700	java/lang/Exception
    //   717	732	700	java/lang/Exception
    //   735	748	700	java/lang/Exception
    //   636	659	838	android/provider/Settings$SettingNotFoundException
  }
  
  protected void onResume()
  {
    super.onResume();
    FinskyApp.get().mNotificationHelper.setNotificationListener(this.mNotificationListener);
    if (this.mDrawerLayout != null) {
      this.mDrawerLayout.mMainActivity.syncDrawerArrowDrawable();
    }
    if (!this.mIsInitializationInProgress) {
      maybeClearBackstack();
    }
  }
  
  public final Object onRetainCustomNonConfigurationInstance()
  {
    this.mHandler.removeCallbacks(this.mStopPreviewsRunnable);
    return super.onRetainCustomNonConfigurationInstance();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mSavedInstanceState != null) {
      paramBundle.putAll(this.mSavedInstanceState);
    }
    for (;;)
    {
      super.onSaveInstanceState(paramBundle);
      paramBundle.putInt("last_shown_error_hash", this.mLastShownErrorHash);
      FinskyDrawerLayout localFinskyDrawerLayout = this.mDrawerLayout;
      localFinskyDrawerLayout.checkIsConfigured();
      paramBundle.putBoolean("FinskyDrawerLayout.isAccountListExpanded", localFinskyDrawerLayout.mDrawerAdapter.mAccountListExpanded);
      paramBundle.putBoolean("FinskyDrawerLayout.isDrawerOpened", localFinskyDrawerLayout.isDrawerOpen());
      return;
      NavigationManager localNavigationManager = this.mNavigationManager;
      if ((localNavigationManager.mBackStack != null) && (!localNavigationManager.mBackStack.isEmpty())) {
        paramBundle.putParcelableArrayList("nm_state", new ArrayList(localNavigationManager.mBackStack));
      }
    }
  }
  
  public boolean onSearchRequested()
  {
    boolean bool1 = isTosAccepted();
    boolean bool2 = false;
    ActionBarHelper localActionBarHelper;
    if (bool1)
    {
      localActionBarHelper = this.mActionBarHelper;
      if (localActionBarHelper.mSearchItem == null) {
        break label76;
      }
      if (!MenuItemCompat.isActionViewExpanded(localActionBarHelper.mSearchItem)) {
        break label65;
      }
      MenuItemCompat.collapseActionView(localActionBarHelper.mSearchItem);
    }
    label65:
    label76:
    for (int i = 1;; i = 0)
    {
      if (i == 0)
      {
        boolean bool3 = super.onSearchRequested();
        bool2 = false;
        if (!bool3) {}
      }
      else
      {
        bool2 = true;
      }
      return bool2;
      MenuItemCompat.expandActionView(localActionBarHelper.mSearchItem);
      break;
    }
  }
  
  protected void onStart()
  {
    super.onStart();
    LocationHelper localLocationHelper = this.mLocationHelper;
    if (LocationHelper.areLocationSuggestionsEnabled())
    {
      if (localLocationHelper.mLocationProviderChangeReceiver != null) {
        FinskyLog.wtf("registerLocationSettingsReceiver was called without unregistering first!", new Object[0]);
      }
    }
    else {
      return;
    }
    localLocationHelper.mLocationProviderChangeReceiver = new LocationHelper.LocationProviderChangedReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.location.PROVIDERS_CHANGED");
    if (Build.VERSION.SDK_INT >= 19) {
      localIntentFilter.addAction("android.location.MODE_CHANGED");
    }
    localLocationHelper.mContext.registerReceiver(localLocationHelper.mLocationProviderChangeReceiver, localIntentFilter);
  }
  
  protected void onStop()
  {
    super.onStop();
    this.mHandler.post(this.mStopPreviewsRunnable);
    if (this.mSequenceNumberToDrainFrom == -1) {
      this.mSequenceNumberToDrainFrom = FinskyApp.get().mRequestQueue.mSequenceGenerator.incrementAndGet();
    }
    if (this.mBitmapSequenceNumberToDrainFrom == -1) {
      this.mBitmapSequenceNumberToDrainFrom = FinskyApp.get().mBitmapRequestQueue.mSequenceGenerator.incrementAndGet();
    }
    FinskyApp.get().drainAllRequests(this.mSequenceNumberToDrainFrom, this.mBitmapSequenceNumberToDrainFrom);
    this.mSequenceNumberToDrainFrom = -1;
    this.mBitmapSequenceNumberToDrainFrom = -1;
    this.mPeopleClient.disconnect();
    LocationHelper localLocationHelper = this.mLocationHelper;
    if (localLocationHelper.mLocationProviderChangeReceiver != null)
    {
      localLocationHelper.mContext.unregisterReceiver(localLocationHelper.mLocationProviderChangeReceiver);
      localLocationHelper.mLocationProviderChangeReceiver = null;
    }
  }
  
  public final void openSettings(String paramString)
  {
    Intent localIntent = new Intent(this, SettingsActivity.class);
    if (!TextUtils.isEmpty(paramString)) {
      localIntent.putExtra("setting-key-to-scroll", paramString);
    }
    startActivityForResult(localIntent, 31);
  }
  
  public final void overrideSearchBoxWidth(int paramInt)
  {
    this.mActionBarHelper.overrideSearchBoxWidth(paramInt);
  }
  
  public final void setActionBarSearchModeListener(ActionBarController.ActionBarSearchModeListener paramActionBarSearchModeListener)
  {
    this.mSearchModeListener = paramActionBarSearchModeListener;
  }
  
  public final void setHomeAsUpIndicator(int paramInt)
  {
    ActionBar localActionBar = getDelegate().getSupportActionBar();
    if (paramInt == 0)
    {
      localActionBar.setHomeAsUpIndicator(this.mDrawerArrowDrawable);
      return;
    }
    localActionBar.setHomeAsUpIndicator(paramInt);
  }
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (!TextUtils.isEmpty(paramString2))
    {
      if (this.mStateSaved)
      {
        FinskyLog.e(paramString2, new Object[0]);
        return;
      }
      ErrorDialog.show(getSupportFragmentManager(), paramString1, paramString2, paramBoolean);
      return;
    }
    FinskyLog.wtf("Unknown error with empty error message.", new Object[0]);
  }
  
  protected final void switchAccount(String paramString, Intent paramIntent)
  {
    super.switchAccount(paramString, paramIntent);
    if (this.mActionBarHelper != null) {
      this.mActionBarHelper.updateCurrentBackendId(0, false);
    }
    if (this.mDrawerLayout != null) {
      this.mDrawerLayout.updateCurrentBackendId(0);
    }
  }
  
  public final void switchToRegularActionBar()
  {
    this.mActionBarHelper.updateActionBarMode(false, -1);
  }
  
  public final void switchToSearchBoxOnlyActionBar(int paramInt)
  {
    this.mActionBarHelper.updateActionBarMode(true, paramInt);
  }
  
  public final void syncDrawerArrowDrawable()
  {
    if (this.mDrawerArrowDrawable == null) {
      return;
    }
    boolean bool = isAtTopLevelDrawerDestination();
    int i;
    NavigationManager localNavigationManager;
    int j;
    int k;
    label49:
    ActionBar localActionBar;
    if (bool)
    {
      i = 0;
      PlayDrawerArrowDrawable localPlayDrawerArrowDrawable = this.mDrawerArrowDrawable;
      localNavigationManager = this.mNavigationManager;
      j = localNavigationManager.mBackStack.size();
      if (j >= 2) {
        break label120;
      }
      k = 0;
      localPlayDrawerArrowDrawable.setState(i, k);
      ActionBarHelper localActionBarHelper = this.mActionBarHelper;
      if (localActionBarHelper.mToolbar != null) {
        localActionBarHelper.mToolbar.setIdleModeDrawerIconState(i);
      }
      localActionBar = getDelegate().getSupportActionBar();
      if (!bool) {
        break label151;
      }
      if (!this.mDrawerLayout.isDrawerOpen()) {
        break label143;
      }
    }
    label143:
    for (int m = 2131362565;; m = 2131362569)
    {
      localActionBar.setHomeActionContentDescription(m);
      return;
      i = 1;
      break;
      label120:
      k = ((NavigationState)localNavigationManager.mBackStack.elementAt(j - 2)).drawerIconStateSwitchType;
      break label49;
    }
    label151:
    localActionBar.setHomeActionContentDescription(0);
  }
  
  public final void updateActionBarTitle(String paramString)
  {
    this.mActionBarHelper.updateDefaultTitle(paramString);
    PageFragment localPageFragment = this.mNavigationManager.getActivePage();
    int i;
    if (localPageFragment != null)
    {
      i = localPageFragment.getActionBarTitleColor();
      ViewGroup localViewGroup = localPageFragment.mDataView;
      if (!(localViewGroup instanceof FinskyHeaderListLayout)) {
        break label81;
      }
      FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)localViewGroup;
      localFinskyHeaderListLayout.setActionBarTitleColor(i);
      int j = localPageFragment.getActionBarColor();
      if (Color.alpha(j) > 0) {
        localFinskyHeaderListLayout.setFloatingControlsBackground(new ColorDrawable(j));
      }
    }
    label81:
    FinskySearchToolbar localFinskySearchToolbar;
    do
    {
      return;
      localFinskySearchToolbar = (FinskySearchToolbar)findViewById(2131755196);
    } while (localFinskySearchToolbar == null);
    localFinskySearchToolbar.setTitleTextColor(i);
  }
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean)
  {
    this.mActionBarHelper.updateCurrentBackendId(paramInt, paramBoolean);
    this.mDrawerLayout.updateCurrentBackendId(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.MainActivity
 * JD-Core Version:    0.7.0.1
 */