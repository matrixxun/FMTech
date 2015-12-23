package com.google.android.finsky.activities;

import android.app.Dialog;
import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.auth.AuthApi;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.auth.AuthStateFetchListener;
import com.google.android.finsky.billing.PreAppDownloadWarnings;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.config.PurchaseAuthUtils;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.SelfUpdateResponse;
import com.google.android.finsky.protos.UpdateUserSettingResponse;
import com.google.android.finsky.utils.DeviceConfigurationHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GetSelfUpdateHelper;
import com.google.android.finsky.utils.GetSelfUpdateHelper.Listener;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.UserSettingsUtils;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public class SettingsActivity
  extends BaseSettingsActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private static Boolean sSelfUpdateChecked = null;
  private String mAccountName;
  private AuthApi mAuthApi;
  private FinskyEventLog mEventLog;
  private boolean mIsResumed;
  private PlayStoreUiElementNode mPageNode;
  
  private void configureFingerprintAuth(PreferenceScreen paramPreferenceScreen)
  {
    CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference)paramPreferenceScreen.findPreference("fingerprint-auth");
    if (localCheckBoxPreference != null) {
      localCheckBoxPreference.setChecked(((Boolean)FinskyPreferences.useFingerprintForPurchase.get(this.mAccountName).get()).booleanValue());
    }
  }
  
  private static AutoUpdateEntry getCurrentAutoUpdateEntry()
  {
    boolean bool1 = FinskyApp.get().mInstallPolicies.hasMobileNetwork();
    boolean bool2 = ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue();
    boolean bool3 = ((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue();
    if (bool2)
    {
      if ((bool1) && (bool3)) {
        return AutoUpdateEntry.AUTO_UPDATE_WIFI;
      }
      return AutoUpdateEntry.AUTO_UPDATE_ALWAYS;
    }
    return AutoUpdateEntry.AUTO_UPDATE_NEVER;
  }
  
  private PurchaseAuthEntry getCurrentPurchaseAuthEntry()
  {
    int i = PurchaseAuthUtils.getPurchaseAuthType(this.mAccountName);
    for (PurchaseAuthEntry localPurchaseAuthEntry : PurchaseAuthEntry.values()) {
      if (localPurchaseAuthEntry.mPurchaseAuth == i) {
        return localPurchaseAuthEntry;
      }
    }
    throw new IllegalStateException("PurchaseAuth undefined in PurchaseAuthEntry: " + i);
  }
  
  private void hideSettings(String paramString1, String paramString2)
  {
    PreferenceCategory localPreferenceCategory = (PreferenceCategory)getPreferenceScreen().findPreference(paramString1);
    localPreferenceCategory.removePreference(localPreferenceCategory.findPreference(paramString2));
  }
  
  private void setContentFilterLevel(Bundle paramBundle)
  {
    int i = paramBundle.getInt("content-level-to-set", -100);
    if (i == -100)
    {
      FinskyLog.wtf("Content filter authenticated but no level to set", new Object[0]);
      return;
    }
    this.mEventLog.logSettingsBackgroundEvent(401, Integer.valueOf(i), (Integer)FinskyPreferences.contentFilterLevel.get(), "settings-page");
    FinskyPreferences.contentFilterLevel.put(Integer.valueOf(i));
    setResult(40);
    finish();
  }
  
  private void showSelfUpdateCheckResult(boolean paramBoolean)
  {
    AlertDialogBuilderCompat localAlertDialogBuilderCompat;
    if (this.mIsResumed)
    {
      localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(this);
      if (!paramBoolean) {
        break label45;
      }
    }
    label45:
    for (int i = 2131362728;; i = 2131362727)
    {
      localAlertDialogBuilderCompat.setMessage(i);
      localAlertDialogBuilderCompat.setPositiveButton(2131362418, null);
      localAlertDialogBuilderCompat.create().show();
      return;
    }
  }
  
  private void updateFingerprintAuth(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean2) && (paramBoolean1))
    {
      this.mAuthApi.fetchAuthState(new AuthStateFetchListener()
      {
        public final void onComplete(AuthState paramAnonymousAuthState)
        {
          SettingsActivity.this.startActivityForResult(GaiaAuthActivity.getIntent(SettingsActivity.this, SettingsActivity.this.mAccountName, false, paramAnonymousAuthState, null), 37);
        }
        
        public final void onStart() {}
      });
      return;
    }
    if (paramBoolean2) {
      AuthApi.recordFingerprintKey(this.mAccountName);
    }
    PurchaseAuthUtils.setAndLogFingerprintAuth(this.mAccountName, paramBoolean1, this.mEventLog, "settings-page");
    configureFingerprintAuth(getPreferenceScreen());
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 30) && (paramInt2 == -1))
    {
      Bundle localBundle4 = new Bundle();
      int n = paramIntent.getIntExtra("ContentFilterActivity_selectedFilterLevel", -100);
      if (n == -100)
      {
        FinskyLog.wtf("Content filter returned code 'OK' but no level to set", new Object[0]);
        return;
      }
      localBundle4.putInt("content-level-to-set", n);
      String str3 = (String)FinskyPreferences.contentPin.get();
      if (TextUtils.isEmpty(str3))
      {
        startActivityForResult(PinEntryDialog.getIntent(this, 2131362548, 2131362549, null, localBundle4), 33);
        return;
      }
      startActivityForResult(PinEntryDialog.getIntent(this, 2131362544, 2131362545, str3, localBundle4), 31);
      return;
    }
    if ((paramInt1 == 35) && (paramInt2 == -1))
    {
      setResult(40);
      finish();
      return;
    }
    if ((paramInt1 == 33) && (paramInt2 == -1))
    {
      Bundle localBundle3 = paramIntent.getBundleExtra("PinEntryDialog.extraParams");
      String str2 = paramIntent.getStringExtra("PinEntryDialog.resultPin");
      if (TextUtils.isEmpty(str2))
      {
        FinskyLog.wtf("Create PIN result OK but no PIN sent back.", new Object[0]);
        return;
      }
      startActivityForResult(PinEntryDialog.getIntent(this, 2131362540, 2131362541, str2, localBundle3), 34);
      return;
    }
    if ((paramInt1 == 34) && (paramInt2 == -1))
    {
      String str1 = paramIntent.getStringExtra("PinEntryDialog.resultPin");
      if (TextUtils.isEmpty(str1))
      {
        FinskyLog.wtf("Confirm PIN result OK but no PIN sent back.", new Object[0]);
        return;
      }
      FinskyPreferences.contentPin.put(str1);
      setContentFilterLevel(paramIntent.getBundleExtra("PinEntryDialog.extraParams"));
      return;
    }
    if ((paramInt1 == 31) && (paramInt2 == -1))
    {
      Bundle localBundle2 = paramIntent.getBundleExtra("PinEntryDialog.extraParams");
      if (localBundle2.getInt("content-level-to-set", -100) == ContentLevel.SHOW_ALL.mValue) {
        FinskyPreferences.contentPin.remove();
      }
      setContentFilterLevel(localBundle2);
      return;
    }
    if ((paramInt1 == 32) && (paramInt2 == -1))
    {
      Bundle localBundle1 = paramIntent.getBundleExtra("GaiaAuthActivity_extraParams");
      int k = localBundle1.getInt("purchase-auth-previous", -1);
      int m = localBundle1.getInt("purchase-auth-new", -1);
      if (m == -1)
      {
        FinskyLog.wtf("Missing new value for PurchaseAuth", new Object[0]);
        return;
      }
      PurchaseAuthUtils.setAndLogPurchaseAuth(this.mAccountName, m, Integer.valueOf(k), this.mEventLog, "settings-page");
      return;
    }
    if ((paramInt1 == 36) && (paramInt2 == -1))
    {
      final int i = paramIntent.getIntExtra("purchase-auth-current", -1);
      final int j = paramIntent.getIntExtra("purchase-auth-new", -1);
      this.mAuthApi.fetchAuthState(new AuthStateFetchListener()
      {
        public final void onComplete(AuthState paramAnonymousAuthState)
        {
          SettingsActivity.access$400(SettingsActivity.this, i, j, paramAnonymousAuthState);
        }
        
        public final void onStart() {}
      });
      return;
    }
    if (paramInt1 == 37)
    {
      boolean bool = false;
      if (paramInt2 == -1) {
        bool = true;
      }
      updateFingerprintAuth(bool, true);
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccountName = FinskyApp.get().getCurrentAccountName();
    if (this.mAccountName == null)
    {
      FinskyLog.d("Exit SettingsActivity - no current account.", new Object[0]);
      finish();
      return;
    }
    addPreferencesFromResource(2131165188);
    if (!GooglePlayServicesUtil.isSidewinderDevice(this)) {
      hideSettings("category-general", "receive-emails");
    }
    if (!PreAppDownloadWarnings.isPostponeDownloadUntilWifiEnabled(this)) {
      hideSettings("category-general", "download-mode");
    }
    if (!AuthApi.isFingerprintAvailable(this.mAccountName)) {
      hideSettings("category-user-controls", "fingerprint-auth");
    }
    this.mEventLog = FinskyApp.get().getEventLogger();
    this.mPageNode = new GenericUiElementNode(12, null, null, null);
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this.mPageNode);
    }
    getListView().setCacheColorHint(getResources().getColor(2131689637));
    this.mAuthApi = new AuthApi(AccountHandler.findAccount(this.mAccountName, FinskyApp.get()));
  }
  
  protected void onDestroy()
  {
    if (this.mAuthApi != null) {
      this.mAuthApi.cancelAuthFetchRequest();
    }
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mIsResumed = false;
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    String str1 = paramPreference.getKey();
    int i;
    if ("update-notifications".equals(str1))
    {
      CheckBoxPreference localCheckBoxPreference4 = (CheckBoxPreference)paramPreference;
      VendingPreferences.NOTIFY_UPDATES.put(Boolean.valueOf(localCheckBoxPreference4.isChecked()));
      i = 1;
    }
    for (;;)
    {
      if (i != 0) {
        new BackupManager(this).dataChanged();
      }
      return true;
      if ("update-completion-notifications".equals(str1))
      {
        CheckBoxPreference localCheckBoxPreference3 = (CheckBoxPreference)paramPreference;
        VendingPreferences.NOTIFY_UPDATES_COMPLETION.put(Boolean.valueOf(localCheckBoxPreference3.isChecked()));
        i = 1;
      }
      else if ("auto-add-shortcuts".equals(str1))
      {
        CheckBoxPreference localCheckBoxPreference2 = (CheckBoxPreference)paramPreference;
        VendingPreferences.AUTO_ADD_SHORTCUTS.put(Boolean.valueOf(localCheckBoxPreference2.isChecked()));
        i = 1;
      }
      else if ("clear-history".equals(str1))
      {
        FinskyApp.get().mRecentSuggestions.clearHistory();
        i = 0;
      }
      else if ("content-level".equals(str1))
      {
        if (FinskyApp.get().getExperiments().isEnabled(12602392L))
        {
          startActivityForResult(IntentUtils.createAccountSpecificIntent(this, ContentFiltersActivity2.class, "authAccount", this.mAccountName), 35);
          i = 0;
        }
        else
        {
          startActivityForResult(IntentUtils.createAccountSpecificIntent(this, ContentFilterActivity.class, "authAccount", this.mAccountName), 30);
          i = 0;
        }
      }
      else if ("os-licenses".equals(str1))
      {
        startActivity(WebViewDialog.getIntent$2b01ea99(this, "file:///android_asset/licenses.html"));
        i = 0;
      }
      else if ("build-version".equals(str1))
      {
        this.mEventLog.logClickEvent(282, null, this.mPageNode);
        boolean bool3 = ((Boolean)G.userCheckForSelfUpdateEnabled.get()).booleanValue();
        i = 0;
        if (bool3) {
          if (sSelfUpdateChecked != null)
          {
            showSelfUpdateCheckResult(sSelfUpdateChecked.booleanValue());
            i = 0;
          }
          else
          {
            DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
            localDfeApi.invalidateSelfUpdateCache();
            final String str2 = localDfeApi.getAccountName();
            GetSelfUpdateHelper.getSelfUpdate(localDfeApi, DeviceConfigurationHelper.get(), new GetSelfUpdateHelper.Listener()
            {
              public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
              {
                SettingsActivity.this.showSelfUpdateCheckResult(false);
              }
              
              public final void onResponse(SelfUpdateResponse paramAnonymousSelfUpdateResponse)
              {
                SettingsActivity.access$102(Boolean.valueOf(FinskyApp.get().mSelfUpdateScheduler.checkForSelfUpdate(SelfUpdateScheduler.getNewVersion(paramAnonymousSelfUpdateResponse), str2)));
                SettingsActivity.this.showSelfUpdateCheckResult(SettingsActivity.sSelfUpdateChecked.booleanValue());
              }
            });
            i = 0;
          }
        }
      }
      else if ("purchase-auth".equals(str1))
      {
        Intent localIntent = new Intent(this, PurchaseAuthActivity.class);
        localIntent.putExtra("purchase-auth-current", getCurrentPurchaseAuthEntry().mPurchaseAuth);
        startActivityForResult(localIntent, 36);
        i = 0;
      }
      else
      {
        if ("location".equals(str1))
        {
          final CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)paramPreference;
          final boolean bool2 = localCheckBoxPreference1.isChecked();
          if (bool2) {}
          for (int j = 1;; j = 2)
          {
            UserSettingsUtils.updateUserSetting$2f7b0d8d(1, j, new Response.ErrorListener()
            {
              public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
              {
                CheckBoxPreference localCheckBoxPreference = localCheckBoxPreference1;
                if (!bool2) {}
                for (boolean bool = true;; bool = false)
                {
                  localCheckBoxPreference.setChecked(bool);
                  return;
                }
              }
            });
            i = 0;
            break;
          }
        }
        boolean bool1 = "fingerprint-auth".equals(str1);
        i = 0;
        if (bool1)
        {
          updateFingerprintAuth(((CheckBoxPreference)paramPreference).isChecked(), false);
          i = 0;
        }
      }
    }
  }
  
  protected void onResume()
  {
    int i = 1;
    super.onResume();
    this.mIsResumed = i;
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    ((CheckBoxPreference)localPreferenceScreen.findPreference("update-notifications")).setChecked(((Boolean)VendingPreferences.NOTIFY_UPDATES.get()).booleanValue());
    ((CheckBoxPreference)localPreferenceScreen.findPreference("update-completion-notifications")).setChecked(((Boolean)VendingPreferences.NOTIFY_UPDATES_COMPLETION.get()).booleanValue());
    CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)localPreferenceScreen.findPreference("receive-emails");
    if (localCheckBoxPreference1 != null) {
      localCheckBoxPreference1.setChecked(((Boolean)FinskyPreferences.receiveEmails.get(this.mAccountName).get()).booleanValue());
    }
    SettingsListPreference localSettingsListPreference1 = (SettingsListPreference)localPreferenceScreen.findPreference("auto-update-mode");
    AutoUpdateEntry[] arrayOfAutoUpdateEntry1 = AutoUpdateEntry.values();
    AutoUpdateEntry[] arrayOfAutoUpdateEntry2;
    if (!FinskyApp.get().mInstallPolicies.hasMobileNetwork())
    {
      arrayOfAutoUpdateEntry2 = new AutoUpdateEntry[2];
      System.arraycopy(arrayOfAutoUpdateEntry1, 0, arrayOfAutoUpdateEntry2, 0, 2);
    }
    for (;;)
    {
      localSettingsListPreference1.setEntriesAndValues(arrayOfAutoUpdateEntry2);
      localSettingsListPreference1.setValueAndUpdateSummary(getCurrentAutoUpdateEntry());
      SettingsListPreference localSettingsListPreference2 = (SettingsListPreference)localPreferenceScreen.findPreference("download-mode");
      int k;
      DownloadEntry localDownloadEntry;
      Preference localPreference1;
      label281:
      int j;
      label370:
      CheckBoxPreference localCheckBoxPreference2;
      DfeToc localDfeToc;
      if (localSettingsListPreference2 != null)
      {
        localSettingsListPreference2.setEntriesAndValues(DownloadEntry.values());
        k = ((Integer)FinskyPreferences.downloadNetworkPreference.get()).intValue();
        if (k == 2)
        {
          localDownloadEntry = DownloadEntry.DOWNLOAD_ALWAYS;
          localSettingsListPreference2.setValueAndUpdateSummary(localDownloadEntry);
        }
      }
      else
      {
        ((CheckBoxPreference)localPreferenceScreen.findPreference("auto-add-shortcuts")).setChecked(((Boolean)VendingPreferences.AUTO_ADD_SHORTCUTS.get()).booleanValue());
        boolean bool2 = ((Boolean)G.vendingHideContentRating.get()).booleanValue();
        localPreference1 = localPreferenceScreen.findPreference("content-level");
        if (!bool2) {
          break label522;
        }
        localPreferenceScreen.removePreference(localPreference1);
        localPreferenceScreen.findPreference("purchase-auth").setSummary(getCurrentPurchaseAuthEntry().mResource);
        Preference localPreference2 = localPreferenceScreen.findPreference("build-version");
        String str = FinskyApp.get().mPackageStateRepository.getVersionName(FinskyApp.get().getPackageName());
        Object[] arrayOfObject = new Object[i];
        arrayOfObject[0] = str;
        localPreference2.setSummary(getString(2131362319, arrayOfObject));
        if (FinskyApp.get().getExperiments().isEnabled(12603100L)) {
          break label552;
        }
        j = i;
        localCheckBoxPreference2 = (CheckBoxPreference)localPreferenceScreen.findPreference(UserSettingsUtils.getPreferenceKeyForSetting(i));
        if (localCheckBoxPreference2 != null)
        {
          localDfeToc = FinskyApp.get().mToc;
          if (localDfeToc != null) {
            break label558;
          }
        }
      }
      PreferenceFile.SharedPreference localSharedPreference;
      label522:
      label552:
      label558:
      for (PrivacySetting localPrivacySetting = null;; localPrivacySetting = localDfeToc.getUserPrivacySetting(i))
      {
        localSharedPreference = UserSettingsUtils.getPreferenceForUserSetting(i, this.mAccountName);
        if ((localPrivacySetting != null) && (j == 0) && (localSharedPreference != null)) {
          break label569;
        }
        ((PreferenceGroup)localPreferenceScreen.findPreference("category-user-controls")).removePreference(localCheckBoxPreference2);
        configureFingerprintAuth(localPreferenceScreen);
        localPreferenceScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        if ((getIntent() != null) && (getIntent().hasExtra("setting-key-to-scroll"))) {
          new Handler().post(new Runnable()
          {
            public final void run()
            {
              String str = SettingsActivity.this.getIntent().getStringExtra("setting-key-to-scroll");
              ListAdapter localListAdapter = SettingsActivity.this.getListView().getAdapter();
              if (localListAdapter != null) {}
              for (int i = 0;; i++) {
                if (i < localListAdapter.getCount())
                {
                  if (((localListAdapter.getItem(i) instanceof Preference)) && (str.equals(((Preference)localListAdapter.getItem(i)).getKey()))) {
                    SettingsActivity.this.getListView().setSelection(i);
                  }
                }
                else {
                  return;
                }
              }
            }
          });
        }
        return;
        if (k == 3)
        {
          localDownloadEntry = DownloadEntry.DOWNLOAD_WIFI;
          break;
        }
        localDownloadEntry = DownloadEntry.DOWNLOAD_ASK;
        break;
        if (!FinskyApp.get().getExperiments().isEnabled(12602392L)) {
          break label281;
        }
        localPreference1.setSummary(getString(2131362056));
        break label281;
        j = 0;
        break label370;
      }
      label569:
      Integer localInteger = (Integer)localSharedPreference.get();
      boolean bool1;
      if ((localInteger == null) || (localInteger.intValue() == 0)) {
        bool1 = localPrivacySetting.enabledByDefault;
      }
      for (;;)
      {
        localCheckBoxPreference2.setChecked(bool1);
        break;
        if (localInteger.intValue() == 2) {
          bool1 = false;
        }
      }
      arrayOfAutoUpdateEntry2 = arrayOfAutoUpdateEntry1;
    }
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    boolean bool2;
    boolean bool3;
    if ("auto-update-mode".equals(paramString))
    {
      SettingsListPreference localSettingsListPreference2 = (SettingsListPreference)localPreferenceScreen.findPreference(paramString);
      String str = localSettingsListPreference2.getValue();
      AutoUpdateEntry localAutoUpdateEntry = AutoUpdateEntry.valueOf(str);
      int i = 8.$SwitchMap$com$google$android$finsky$activities$SettingsActivity$AutoUpdateEntry[localAutoUpdateEntry.ordinal()];
      bool2 = false;
      bool3 = false;
      switch (i)
      {
      default: 
        FinskyLog.wtf("Unexpected list pref value %s", new Object[] { str });
      case 1: 
        this.mEventLog.logSettingsBackgroundEvent(402, Integer.valueOf(localAutoUpdateEntry.ordinal()), Integer.valueOf(getCurrentAutoUpdateEntry().ordinal()), null);
        FinskyPreferences.autoUpdateEnabled.put(Boolean.valueOf(bool2));
        FinskyPreferences.autoUpdateWifiOnly.put(Boolean.valueOf(bool3));
        new BackupManager(this).dataChanged();
        localSettingsListPreference2.updateListPreferenceSummary();
      }
    }
    do
    {
      return;
      bool2 = true;
      bool3 = false;
      break;
      bool2 = true;
      bool3 = true;
      break;
      if ("download-mode".equals(paramString))
      {
        SettingsListPreference localSettingsListPreference1 = (SettingsListPreference)localPreferenceScreen.findPreference(paramString);
        DownloadEntry localDownloadEntry = DownloadEntry.valueOf(localSettingsListPreference1.getValue());
        switch (8.$SwitchMap$com$google$android$finsky$activities$SettingsActivity$DownloadEntry[localDownloadEntry.ordinal()])
        {
        default: 
          throw new IllegalStateException("Unexpected download preference");
        case 1: 
          FinskyPreferences.downloadNetworkPreference.put(Integer.valueOf(1));
        }
        for (;;)
        {
          new BackupManager(this).dataChanged();
          localSettingsListPreference1.updateListPreferenceSummary();
          return;
          FinskyPreferences.downloadNetworkPreference.put(Integer.valueOf(2));
          continue;
          FinskyPreferences.downloadNetworkPreference.put(Integer.valueOf(3));
        }
      }
    } while (!"receive-emails".equals(paramString));
    final boolean bool1 = ((CheckBoxPreference)localPreferenceScreen.findPreference(paramString)).isChecked();
    final DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    localDfeApi.updateMarketingEmailsOptIn(bool1, UserSettingsCache.getConsistencyTokens(FinskyApp.get().getCurrentAccountName()), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error writing opt-in value: %s", new Object[] { paramAnonymousVolleyError });
      }
    });
  }
  
  private static enum AutoUpdateEntry
    implements SettingsListPreference.SettingsListEntry
  {
    private final int mResource;
    
    static
    {
      AUTO_UPDATE_ALWAYS = new AutoUpdateEntry("AUTO_UPDATE_ALWAYS", 1, 2131361882);
      AUTO_UPDATE_WIFI = new AutoUpdateEntry("AUTO_UPDATE_WIFI", 2, 2131361883);
      AutoUpdateEntry[] arrayOfAutoUpdateEntry = new AutoUpdateEntry[3];
      arrayOfAutoUpdateEntry[0] = AUTO_UPDATE_NEVER;
      arrayOfAutoUpdateEntry[1] = AUTO_UPDATE_ALWAYS;
      arrayOfAutoUpdateEntry[2] = AUTO_UPDATE_WIFI;
      $VALUES = arrayOfAutoUpdateEntry;
    }
    
    private AutoUpdateEntry(int paramInt)
    {
      this.mResource = paramInt;
    }
    
    public final int getResource()
    {
      return this.mResource;
    }
  }
  
  private static enum DownloadEntry
    implements SettingsListPreference.SettingsListEntry
  {
    private final int mDownloadNetwork;
    int mResource;
    
    static
    {
      DOWNLOAD_ALWAYS = new DownloadEntry("DOWNLOAD_ALWAYS", 1, 2, 2131362985);
      DOWNLOAD_WIFI = new DownloadEntry("DOWNLOAD_WIFI", 2, 3, 2131362986);
      DownloadEntry[] arrayOfDownloadEntry = new DownloadEntry[3];
      arrayOfDownloadEntry[0] = DOWNLOAD_ASK;
      arrayOfDownloadEntry[1] = DOWNLOAD_ALWAYS;
      arrayOfDownloadEntry[2] = DOWNLOAD_WIFI;
      $VALUES = arrayOfDownloadEntry;
    }
    
    private DownloadEntry(int paramInt1, int paramInt2)
    {
      this.mDownloadNetwork = paramInt1;
      this.mResource = paramInt2;
    }
    
    public final int getResource()
    {
      return this.mResource;
    }
  }
  
  private static enum PurchaseAuthEntry
    implements SettingsListPreference.SettingsListEntry
  {
    private final int mPurchaseAuth;
    final int mResource;
    
    static
    {
      NEVER = new PurchaseAuthEntry("NEVER", 2, 0, 2131362611);
      PurchaseAuthEntry[] arrayOfPurchaseAuthEntry = new PurchaseAuthEntry[3];
      arrayOfPurchaseAuthEntry[0] = ALWAYS;
      arrayOfPurchaseAuthEntry[1] = SESSION;
      arrayOfPurchaseAuthEntry[2] = NEVER;
      $VALUES = arrayOfPurchaseAuthEntry;
    }
    
    private PurchaseAuthEntry(int paramInt1, int paramInt2)
    {
      this.mPurchaseAuth = paramInt1;
      this.mResource = paramInt2;
    }
    
    public final int getResource()
    {
      return this.mResource;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.SettingsActivity
 * JD-Core Version:    0.7.0.1
 */