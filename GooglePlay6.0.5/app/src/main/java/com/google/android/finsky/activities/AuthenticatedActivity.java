package com.google.android.finsky.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.billing.InAppBillingSetting;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.ExperimentsChangeHandler;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.finsky.protos.Toc.SelfUpdateConfig;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.NetworkStateChangedReceiver;
import com.google.android.finsky.services.DailyHygiene;
import com.google.android.finsky.utils.ApplicationDismissedDeferrer;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.DeviceConfigurationHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.GetTocHelper.Listener;
import com.google.android.finsky.utils.LocationHelper;
import com.google.android.finsky.utils.RestrictedDeviceHelper;
import com.google.android.finsky.utils.RestrictedDeviceHelper.OnCompleteListener;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.StoreTypeValidator;
import com.google.android.finsky.utils.TosUtil;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.VendingUtils;
import com.google.android.finsky.utils.hats.SurveyStore;
import com.google.android.finsky.utils.hats.SurveyStore.1;
import com.google.android.finsky.utils.hats.SurveyStore.2;
import com.google.android.finsky.widget.WidgetUtils;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.utils.PlayUtils;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;
import java.io.IOException;
import java.util.Map;

public abstract class AuthenticatedActivity
  extends ActionBarActivity
  implements BackgroundDataDialog.BackgroundDataSettingListener
{
  private static final boolean ADD_ACCOUNT_SUPPORTS_CUSTOM_MESSAGE;
  private static boolean sCheckedVersionChanges;
  protected ActionBarHelper mActionBarHelper;
  private boolean mCallOnReadyOnResume = false;
  private boolean mCallToOnReadyShouldUseIntent = false;
  private final DialogInterface.OnClickListener mDeclineCreateAccountListener = new DialogInterface.OnClickListener()
  {
    public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    {
      AuthenticatedActivity.this.finish();
    }
  };
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  boolean mIsInitializationInProgress = false;
  private boolean mJustReturnedFromDialog = false;
  private final NetworkStateChangedReceiver mNetworkChangeReceiver = new NetworkStateChangedReceiver();
  private final DialogInterface.OnClickListener mOnClickCreateAccountListener = new DialogInterface.OnClickListener()
  {
    public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    {
      AuthenticatedActivity.this.addAccount();
      paramAnonymousDialogInterface.cancel();
    }
  };
  private Runnable mOnResumeRunnable;
  public boolean mStateSaved;
  boolean mWaitingForUserInput;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 14) {}
    for (boolean bool = true;; bool = false)
    {
      ADD_ACCOUNT_SUPPORTS_CUSTOM_MESSAGE = bool;
      sCheckedVersionChanges = false;
      return;
    }
  }
  
  private void addAccount()
  {
    String str = AccountHandler.getAccountTypes()[0];
    AccountManager.get(this).addAccount(str, "androidmarket", null, createAddAccountOptions(this), null, new AccountManagerCallback()
    {
      public final void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
      {
        try
        {
          Intent localIntent = (Intent)((Bundle)paramAnonymousAccountManagerFuture.getResult()).getParcelable("intent");
          AuthenticatedActivity.this.startActivityForResult(localIntent, 21);
          AuthenticatedActivity.access$802$212b92e1(AuthenticatedActivity.this);
          return;
        }
        catch (OperationCanceledException localOperationCanceledException)
        {
          FinskyLog.d("Account add canceled. Finishing.", new Object[0]);
          AuthenticatedActivity.this.finish();
          return;
        }
        catch (IOException localIOException)
        {
          FinskyLog.d("IOException while adding account: %s. Finishing.", new Object[] { localIOException });
          AuthenticatedActivity.this.finish();
          return;
        }
        catch (AuthenticatorException localAuthenticatorException)
        {
          FinskyLog.d("AuthenticatorException while adding account: %s. Finishing.", new Object[] { localAuthenticatorException });
          AuthenticatedActivity.this.finish();
        }
      }
    }, null);
  }
  
  public static Bundle createAddAccountOptions(Context paramContext)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("pendingIntent", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    localBundle.putString("introMessage", paramContext.getString(2131361818));
    localBundle.putBoolean("allowSkip", false);
    return localBundle;
  }
  
  private String determineAccount()
  {
    String str1 = getIntent().getStringExtra("authAccount");
    Account localAccount2;
    String str2;
    if (str1 != null)
    {
      localAccount2 = AccountHandler.findAccount(str1, this);
      if (localAccount2 == null)
      {
        FinskyLog.wtf("This app was called with an intent that specified the account %s, which is not a valid account on this device", new Object[] { str1 });
        finish();
        str2 = null;
      }
    }
    do
    {
      return str2;
      return localAccount2.name;
      str2 = FinskyApp.get().getCurrentAccountName();
    } while (AccountHandler.hasAccount(str2, this));
    Account localAccount1 = AccountHandler.getFirstAccount(this);
    if (localAccount1 != null) {
      return localAccount1.name;
    }
    return null;
  }
  
  private void fireOnReadyRunnable(final boolean paramBoolean)
  {
    FinskyApp.get().mInstaller.startDeferredInstalls();
    DailyHygiene.cancelHoldoffPeriod();
    hideLoadingIndicator();
    Runnable local3 = new Runnable()
    {
      public final void run()
      {
        if (AuthenticatedActivity.this.mStateSaved)
        {
          FinskyLog.d("onSaveInstanceState() called, not firing onReady().", new Object[0]);
          AuthenticatedActivity.access$002$212b92e1(AuthenticatedActivity.this);
          AuthenticatedActivity.access$102(AuthenticatedActivity.this, paramBoolean);
          return;
        }
        AuthenticatedActivity.this.onReady(paramBoolean);
      }
    };
    this.mHandler.post(local3);
  }
  
  protected static DfeToc instantiateToc(Toc.TocResponse paramTocResponse)
  {
    return new DfeToc(paramTocResponse);
  }
  
  private static boolean isAccountSwitchNeeded(String paramString)
  {
    return !paramString.equals(FinskyApp.get().getCurrentAccountName());
  }
  
  public static boolean isTosAccepted()
  {
    String str = FinskyApp.get().getCurrentAccountName();
    if (str == null) {}
    DfeToc localDfeToc;
    do
    {
      return false;
      localDfeToc = FinskyApp.get().mToc;
    } while ((localDfeToc == null) || (TosUtil.requiresAcceptance(str, localDfeToc)));
    return true;
  }
  
  private void loadPlusProfileAndContinue(final boolean paramBoolean)
  {
    boolean bool = true;
    FinskyApp.get().getPlayDfeApi(null).getPlusProfile(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
    }, false);
    String str1 = determineAccount();
    if (TosUtil.requiresAcceptance(str1, FinskyApp.get().mToc))
    {
      this.mWaitingForUserInput = bool;
      Intent localIntent = TosUtil.getTosIntent(this, str1, FinskyApp.get().mToc);
      localIntent.setFlags(67108864);
      startActivityForResult(localIntent, 20);
    }
    for (;;)
    {
      if (!bool)
      {
        String str2 = determineAccount();
        if (!TextUtils.isEmpty(str2))
        {
          SurveyStore localSurveyStore = FinskyApp.get().getClientMutationCache(str2).getSurveyStore();
          DfeApi localDfeApi = FinskyApp.get().getDfeApi(localSurveyStore.mAccountName);
          if (localDfeApi != null) {
            localDfeApi.getSurveys(new SurveyStore.1(localSurveyStore), new SurveyStore.2(localSurveyStore));
          }
        }
        Runnable local12 = new Runnable()
        {
          private int mFinished = 0;
          
          public final void run()
          {
            this.mFinished = (1 + this.mFinished);
            if (this.mFinished == 2) {
              AuthenticatedActivity.this.fireOnReadyRunnable(paramBoolean);
            }
          }
        };
        FinskyApp.get().mAppStates.load(local12);
        FinskyApp.get().mLibraries.load(local12);
      }
      return;
      bool = false;
    }
  }
  
  private void tryToUpdateLocationAndContinue(final boolean paramBoolean)
  {
    new LocationHelper(this).updateLocationIfNeeded();
    if (FinskyApp.get().getExperiments().isEnabled(12604495L)) {
      UserSettingsCache.clearUserSettings(determineAccount());
    }
    for (;;)
    {
      FinskyApp.get().mAppStates.load(null);
      FinskyApp.get().mLibraries.load(null);
      final boolean[] arrayOfBoolean = new boolean[1];
      DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
      GetTocHelper.getToc(localDfeApi, true, new GetTocHelper.Listener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          AuthenticatedActivity.this.hideLoadingIndicator();
          AuthenticatedActivity.this.handleAuthenticationError(paramAnonymousVolleyError);
        }
        
        public final void onResponse(Toc.TocResponse paramAnonymousTocResponse)
        {
          if (arrayOfBoolean[0] == 1)
          {
            new ApplicationDismissedDeferrer(AuthenticatedActivity.this.getApplicationContext()).runOnApplicationClose(new Runnable()
            {
              public final void run()
              {
                FinskyLog.d("Exiting process due to toc change", new Object[0]);
                System.exit(0);
              }
            }, 10000);
            return;
          }
          arrayOfBoolean[0] = true;
          DfeToc localDfeToc = AuthenticatedActivity.instantiateToc(paramAnonymousTocResponse);
          FinskyApp.get().setToc(localDfeToc);
          SelfUpdateScheduler localSelfUpdateScheduler = FinskyApp.get().mSelfUpdateScheduler;
          int i = -1;
          if (paramAnonymousTocResponse.selfUpdateConfig != null)
          {
            Toc.SelfUpdateConfig localSelfUpdateConfig = paramAnonymousTocResponse.selfUpdateConfig;
            if (localSelfUpdateConfig.hasLatestClientVersionCode) {
              i = localSelfUpdateConfig.latestClientVersionCode;
            }
          }
          localSelfUpdateScheduler.checkForSelfUpdate(i, this.val$accountName);
          if (paramAnonymousTocResponse.billingConfig != null) {
            InAppBillingSetting.setVersionFromBillingConfig(FinskyApp.get().getCurrentAccountName(), paramAnonymousTocResponse.billingConfig);
          }
          AuthenticatedActivity.access$400(AuthenticatedActivity.this, paramBoolean);
        }
      });
      return;
      UserSettingsCache.updateUserSettingsIfDirty(determineAccount());
    }
  }
  
  protected void handleAuthenticationError(VolleyError paramVolleyError) {}
  
  protected final void hideLoadingIndicator()
  {
    ViewGroup localViewGroup = (ViewGroup)getWindow().findViewById(2131755234);
    if (localViewGroup == null) {
      return;
    }
    localViewGroup.findViewById(2131755275).setVisibility(8);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default: 
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    case 24: 
    case 23: 
      String str;
      do
      {
        do
        {
          return;
          if (paramInt2 == -1)
          {
            RestrictedDeviceHelper.setUserPinValid$1385ff();
            loadPlusProfileAndContinue(true);
            return;
          }
          finish();
          return;
        } while (paramInt2 != -1);
        str = paramIntent.getStringExtra("authAccount");
      } while (str == null);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(str);
      FinskyLog.d("b/5160617: Switch account to %s on resume", arrayOfObject);
      switchToAccount(str);
      return;
    case 20: 
      this.mWaitingForUserInput = false;
      if (paramInt2 == 0)
      {
        finish();
        return;
      }
      this.mJustReturnedFromDialog = true;
      return;
    case 22: 
      if (paramInt2 == 0)
      {
        if (Build.VERSION.SDK_INT >= 14) {
          startActivityForResult(AccountManager.newChooseAccountIntent(FinskyApp.get().getCurrentAccount(), null, AccountHandler.getAccountTypes(), true, null, "androidmarket", null, createAddAccountOptions(this)), 23);
        }
        for (;;)
        {
          FinskyApp.get().getEventLogger().logPathImpression$7d139cbf(310, null);
          return;
          showDialog(0);
        }
      }
      this.mWaitingForUserInput = false;
      this.mJustReturnedFromDialog = true;
      return;
    }
    this.mWaitingForUserInput = false;
    if (AccountHandler.getFirstAccount(this) == null)
    {
      FinskyLog.d("No new account added: Assume the user canceled and finish.", new Object[0]);
      finish();
      return;
    }
    this.mJustReturnedFromDialog = true;
  }
  
  public final void onBackgroundDataNotEnabled()
  {
    finish();
  }
  
  protected void onCleanup() {}
  
  public void onCreate(Bundle paramBundle)
  {
    if (!getResources().getBoolean(2131427352)) {
      PlayUtils.forceLayoutDirectionToLtr(this);
    }
    String str1;
    String str2;
    if (paramBundle != null)
    {
      this.mWaitingForUserInput = paramBundle.getBoolean("waiting_for_user_input");
      str1 = determineAccount();
      str2 = paramBundle.getString("last_used_account");
      if (str1 != null) {
        break label79;
      }
      paramBundle = null;
    }
    for (;;)
    {
      super.onCreate(paramBundle);
      showLoadingIndicator();
      if (StoreTypeValidator.isValid(this)) {
        break;
      }
      AccessRestrictedActivity.showInvalidStoreTypeUI(this);
      finish();
      return;
      label79:
      if ((str2 != null) && (!str1.equals(str2))) {
        paramBundle = null;
      } else if (FinskyApp.get().getExperimentsChangeHandler().mHadStaleExperimentsInLastAppRun) {
        paramBundle = null;
      }
    }
    int j;
    if (!sCheckedVersionChanges)
    {
      int i = FinskyApp.get().getVersionCode();
      if (((Integer)FinskyPreferences.versionCode.get()).intValue() != i)
      {
        FinskyPreferences.versionCode.put(Integer.valueOf(i));
        j = 1;
        if ((j == 0) && (!VendingUtils.wasSystemUpgraded())) {
          break label216;
        }
        if (FinskyLog.DEBUG) {
          FinskyLog.v("Diff version or system, clear cache", new Object[0]);
        }
        DeviceConfigurationHelper.get().invalidateToken();
        UserSettingsCache.updateUserSettingsForAllAccounts();
        FinskyApp.get().clearCacheAsync(new Runnable()
        {
          public final void run()
          {
            AuthenticatedActivity.this.startInitializationActions(true);
          }
        });
      }
    }
    for (;;)
    {
      sCheckedVersionChanges = true;
      return;
      j = 0;
      break;
      label216:
      if (FinskyLog.DEBUG) {
        FinskyLog.v("Same version & system as before", new Object[0]);
      }
      if (!this.mWaitingForUserInput) {
        startInitializationActions(true);
      } else {
        FinskyLog.d("Waiting for user to return from auth screen.", new Object[0]);
      }
    }
  }
  
  protected Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    int i = 0;
    switch (paramInt)
    {
    case 1: 
    default: 
      throw new IllegalStateException("Invalid dialog type id " + paramInt);
    case 2: 
      Resources localResources = getResources();
      AlertDialogBuilderCompat localAlertDialogBuilderCompat2 = new AlertDialogBuilderCompat(this);
      AlertDialogBuilderCompat localAlertDialogBuilderCompat3 = localAlertDialogBuilderCompat2.setMessage(localResources.getString(2131361817)).setCancelable(false).setPositiveButton(localResources.getString(2131362937), this.mOnClickCreateAccountListener).setNegativeButton(localResources.getString(2131362370), this.mDeclineCreateAccountListener);
      DialogInterface.OnKeyListener local4 = new DialogInterface.OnKeyListener()
      {
        public final boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          return paramAnonymousInt == 84;
        }
      };
      if (localAlertDialogBuilderCompat3.mPlatformBuilder != null) {
        localAlertDialogBuilderCompat3.mPlatformBuilder.setOnKeyListener(local4);
      }
      for (;;)
      {
        return localAlertDialogBuilderCompat2.create();
        localAlertDialogBuilderCompat3.mSupportBuilder.setOnKeyListener(local4);
      }
    }
    final Account[] arrayOfAccount = AccountHandler.getAccounts(getApplicationContext());
    String[] arrayOfString = new String[arrayOfAccount.length];
    for (int j = 0; j < arrayOfAccount.length; j++) {
      arrayOfString[j] = arrayOfAccount[j].name;
    }
    String str = FinskyApp.get().getCurrentAccountName();
    label241:
    AlertDialogBuilderCompat localAlertDialogBuilderCompat1;
    DialogInterface.OnCancelListener local6;
    if (i < arrayOfString.length) {
      if (arrayOfString[i].equals(str))
      {
        localAlertDialogBuilderCompat1 = new AlertDialogBuilderCompat(this);
        localAlertDialogBuilderCompat1.setTitle(getString(2131362718));
        localAlertDialogBuilderCompat1.setSingleChoiceItems(arrayOfString, i, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            String str = arrayOfAccount[paramAnonymousInt].name;
            if (AuthenticatedActivity.access$200$29d73ce5(str))
            {
              AuthenticatedActivity.this.setIntent(new Intent());
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = FinskyLog.scrubPii(str);
              FinskyLog.d("b/5160617: Switching account to %s on user action", arrayOfObject);
              AuthenticatedActivity.this.switchAccount(str, null);
            }
            AuthenticatedActivity.this.removeDialog(0);
          }
        });
        local6 = new DialogInterface.OnCancelListener()
        {
          public final void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            AuthenticatedActivity.this.removeDialog(0);
          }
        };
        if (localAlertDialogBuilderCompat1.mPlatformBuilder == null) {
          break label329;
        }
        localAlertDialogBuilderCompat1.mPlatformBuilder.setOnCancelListener(local6);
      }
    }
    for (;;)
    {
      return localAlertDialogBuilderCompat1.create();
      i++;
      break;
      i = -1;
      break label241;
      label329:
      localAlertDialogBuilderCompat1.mSupportBuilder.P.mOnCancelListener = local6;
    }
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    boolean bool = true;
    if (("android.intent.action.MAIN".equals(paramIntent.getAction())) && (paramIntent.hasCategory("android.intent.category.LAUNCHER"))) {
      bool = false;
    }
    startInitializationActions(bool);
  }
  
  protected final void onNewIntentDirect(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
  }
  
  protected void onPause()
  {
    super.onPause();
    FlushLogsReceiver.scheduleLogsFlushOnAppExit();
  }
  
  public void onReady(boolean paramBoolean)
  {
    this.mIsInitializationInProgress = false;
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.mCallOnReadyOnResume)
    {
      this.mCallOnReadyOnResume = false;
      fireOnReadyRunnable(this.mCallToOnReadyShouldUseIntent);
    }
    FlushLogsReceiver.cancelLogsFlush();
  }
  
  protected final void onResumeFragments()
  {
    super.onResumeFragments();
    this.mStateSaved = false;
    if (!Utils.isBackgroundDataEnabled(this))
    {
      showBackgroundDataDialog();
      if (this.mOnResumeRunnable == null) {
        break label47;
      }
      this.mOnResumeRunnable.run();
    }
    label47:
    while (!this.mJustReturnedFromDialog)
    {
      return;
      BackgroundDataDialog.dismissExisting(getSupportFragmentManager());
      break;
    }
    this.mJustReturnedFromDialog = false;
    startInitializationActions(true);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    this.mStateSaved = true;
    paramBundle.putBoolean("waiting_for_user_input", this.mWaitingForUserInput);
    paramBundle.putString("last_used_account", determineAccount());
    super.onSaveInstanceState(paramBundle);
  }
  
  public boolean onSearchRequested()
  {
    return (isTosAccepted()) && (super.onSearchRequested());
  }
  
  protected void onStart()
  {
    super.onStart();
    NetworkStateChangedReceiver.flushCachedState();
    registerReceiver(this.mNetworkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    this.mStateSaved = false;
  }
  
  protected void onStop()
  {
    super.onStop();
    unregisterReceiver(this.mNetworkChangeReceiver);
    NetworkStateChangedReceiver.flushCachedState();
    this.mStateSaved = true;
  }
  
  protected final void reinitialize(Account paramAccount, Intent paramIntent, boolean paramBoolean)
  {
    this.mOnResumeRunnable = null;
    if (paramAccount == null)
    {
      paramAccount = FinskyApp.get().getCurrentAccount();
      if (paramAccount == null)
      {
        restart();
        return;
      }
    }
    FinskyApp localFinskyApp = FinskyApp.get();
    onCleanup();
    if (paramIntent != null)
    {
      setIntent(paramIntent);
      if (paramBoolean) {
        localFinskyApp.clearCacheAsync(null);
      }
      localFinskyApp.setToc(null);
    }
    for (;;)
    {
      synchronized (localFinskyApp.mDfeApis)
      {
        localFinskyApp.mDfeApis.clear();
        if ((localFinskyApp.mCurrentAccount == null) || (!localFinskyApp.mCurrentAccount.equals(paramAccount)))
        {
          i = 1;
          localFinskyApp.mCurrentAccount = paramAccount;
          if (i != 0)
          {
            AccountHandler.saveAccountToPreferences(localFinskyApp.mCurrentAccount, FinskyPreferences.currentAccount);
            localFinskyApp.mAnalytics.reset();
            WidgetUtils.notifyAccountSwitch(localFinskyApp);
          }
          startInitializationActions(true);
          return;
          Intent localIntent = new Intent(this, MainActivity.class);
          localIntent.setAction("android.intent.action.MAIN");
          setIntent(localIntent);
        }
      }
      int i = 0;
    }
  }
  
  @TargetApi(11)
  protected final void restart()
  {
    this.mOnResumeRunnable = null;
    if (Build.VERSION.SDK_INT >= 11)
    {
      recreate();
      return;
    }
    finish();
    final Intent localIntent = getIntent();
    this.mHandler.postDelayed(new Runnable()
    {
      public final void run()
      {
        try
        {
          AuthenticatedActivity.this.startActivity(localIntent);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localIntent.toString();
          FinskyLog.wtf(localActivityNotFoundException, "Intent: %s", arrayOfObject);
          throw localActivityNotFoundException;
        }
      }
    }, 250L);
  }
  
  public final void restartOnResume()
  {
    runOrScheduleActiveStateRunnable(new Runnable()
    {
      public final void run()
      {
        AuthenticatedActivity.this.restart();
      }
    });
  }
  
  protected final void runOrScheduleActiveStateRunnable(Runnable paramRunnable)
  {
    if (!this.mStateSaved)
    {
      paramRunnable.run();
      return;
    }
    this.mOnResumeRunnable = paramRunnable;
  }
  
  protected final void showBackgroundDataDialog()
  {
    BackgroundDataDialog.show(getSupportFragmentManager(), this);
  }
  
  protected final void showLoadingIndicator()
  {
    ViewGroup localViewGroup = (ViewGroup)getWindow().findViewById(2131755234);
    if (localViewGroup == null) {
      return;
    }
    localViewGroup.findViewById(2131755275).setVisibility(0);
  }
  
  protected final void startInitializationActions(final boolean paramBoolean)
  {
    showLoadingIndicator();
    this.mIsInitializationInProgress = true;
    String str = determineAccount();
    if (str == null)
    {
      if (ADD_ACCOUNT_SUPPORTS_CUSTOM_MESSAGE)
      {
        addAccount();
        return;
      }
      showDialog(2);
      return;
    }
    if (isAccountSwitchNeeded(str))
    {
      if (paramBoolean) {}
      for (Intent localIntent = getIntent();; localIntent = null)
      {
        switchAccount(str, localIntent);
        return;
      }
    }
    ExperimentsChangeHandler localExperimentsChangeHandler = FinskyApp.get().getExperimentsChangeHandler();
    if (localExperimentsChangeHandler.mHadStaleExperimentsInLastAppRun)
    {
      FinskyPreferences.hasStaleProcessStableTargets.put(Boolean.valueOf(false));
      localExperimentsChangeHandler.mHadStaleExperimentsInLastAppRun = false;
      FinskyApp.get().clearCacheAsync(new Runnable()
      {
        public final void run()
        {
          AuthenticatedActivity.this.tryToUpdateLocationAndContinue(paramBoolean);
        }
      });
      return;
    }
    tryToUpdateLocationAndContinue(paramBoolean);
  }
  
  protected void switchAccount(String paramString, Intent paramIntent)
  {
    FinskyApp.get().getEventLogger().logSettingsBackgroundEvent(406, Integer.valueOf(0), Integer.valueOf(0), null);
    Account localAccount = AccountHandler.findAccount(paramString, this);
    if (localAccount == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(paramString);
      throw new IllegalArgumentException(String.format("Error, could not switch to %s because the account could not be found on the device", arrayOfObject));
    }
    reinitialize(localAccount, paramIntent, false);
  }
  
  public final void switchToAccount(final String paramString)
  {
    runOrScheduleActiveStateRunnable(new Runnable()
    {
      public final void run()
      {
        AuthenticatedActivity.this.switchAccount(paramString, null);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AuthenticatedActivity
 * JD-Core Version:    0.7.0.1
 */