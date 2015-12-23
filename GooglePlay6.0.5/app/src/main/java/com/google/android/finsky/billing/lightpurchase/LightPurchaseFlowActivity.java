package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppsPermissionsActivity;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreBackgroundActionEvent;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.billing.DownloadNetworkDialogFragment;
import com.google.android.finsky.billing.DownloadNetworkSettingsDialogFragment;
import com.google.android.finsky.billing.DownloadSizeDialogFragment;
import com.google.android.finsky.billing.PreAppDownloadWarnings.Listener;
import com.google.android.finsky.billing.PromptForFopHelper;
import com.google.android.finsky.billing.promptforfop.PromptForFopActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.DocUtils.OfferFilter;
import com.google.android.finsky.utils.FamilyUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.finsky.utils.PurchaseInitiator.SuccessListener;
import com.google.android.finsky.utils.SignatureUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.wallet.common.util.AndroidUtils;
import java.util.List;

public class LightPurchaseFlowActivity
  extends AuthenticatedActivity
  implements SimpleAlertDialog.Listener, PreAppDownloadWarnings.Listener
{
  private Account mAccount;
  private boolean mAppPermissionsLaunched;
  private String mAppTitle;
  private int mAppVersionCode;
  private String mAppsContinueUrl;
  private boolean mCalledByFirstPartyPackage;
  private Document mDoc;
  private Common.Docid mDocid;
  private String mDocidStr;
  private boolean mFailed;
  private final Handler mHandler = new Handler();
  private boolean mHeavyDialogShown;
  private int mIndirectProvisioningType;
  private DocUtils.OfferFilter mOfferFilter;
  private String mOfferId;
  private boolean mOfferRequiresCheckout;
  private int mOfferType;
  private boolean mPostSuccessItemOpened;
  private String mReferralUrl;
  private Bundle mSavedInstanceState;
  private boolean mTosLaunched;
  private String mVoucherId;
  
  private void acquire(Bundle paramBundle, boolean paramBoolean)
  {
    AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount);
    if (LibraryUtils.isOfferOwned(this.mDocid, localAccountLibrary, this.mOfferType))
    {
      if (this.mDocid.type == 1)
      {
        if (paramBoolean)
        {
          showNetworkDownloadDialog();
          return;
        }
        if (paramBundle != null)
        {
          showDownloadSizeWarning(paramBundle);
          return;
        }
        logConfirmFreeDownload();
        FinskyApp.get().mInstallPolicies.captureEverExternallyHosted(this.mDoc);
        FinskyApp.get().mInstallerDataStore.setContinueUrl(this.mDocid.backendDocid, this.mAppsContinueUrl);
        FinskyApp.get().mInstaller.requestInstall(this.mDocid.backendDocid, this.mAppVersionCode, this.mAccount.name, this.mAppTitle, false, "single_install", 2, FinskyApp.get().mInstaller.extractInstallLocation(this.mDoc));
        success();
        return;
      }
      int j;
      int k;
      if (this.mOfferRequiresCheckout)
      {
        if ((this.mDocid.type == 15) || (this.mOfferType == 13))
        {
          j = 1;
          if ((this.mDocid.backend != 2) || (j == 0)) {
            break label372;
          }
          String str2 = AccountLibrary.getLibraryIdFromBackend(2);
          LibraryEntry localLibraryEntry = localAccountLibrary.getLibrary(str2).get(new LibraryEntry(this.mAccount.name, str2, 2, this.mDocid.backendDocid, this.mDocid.type, this.mOfferType));
          if ((localLibraryEntry == null) || (((LibrarySubscriptionEntry)localLibraryEntry).isAutoRenewing)) {
            break label372;
          }
          k = 1;
          label276:
          if ((k != 0) && (((Boolean)G.musicAppSubscriptionResignupEnabled.get()).booleanValue())) {
            break label386;
          }
        }
      }
      else {
        if (this.mDocid.type != 15) {
          break label378;
        }
      }
      label372:
      label378:
      for (int i = 2131362772;; i = 2131362097)
      {
        String str1 = getString(i);
        SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
        localBuilder.setMessage(str1).setPositiveId(2131362418).setCallback(null, 4, null);
        localBuilder.build().show(getSupportFragmentManager(), "LightPurchaseFlowActivity.errorDialog");
        return;
        j = 0;
        break;
        k = 0;
        break label276;
      }
    }
    label386:
    if (this.mOfferRequiresCheckout)
    {
      PurchaseParams.Builder localBuilder1 = PurchaseParams.builder();
      localBuilder1.docid = this.mDocid;
      localBuilder1.docidStr = this.mDocidStr;
      localBuilder1.offerType = this.mOfferType;
      localBuilder1.offerId = this.mOfferId;
      localBuilder1.callingPackage = getCallingPackage();
      PurchaseParams.Builder localBuilder2 = localBuilder1.setAppData(this.mAppVersionCode, this.mAppTitle, this.mAppsContinueUrl);
      localBuilder2.voucherId = this.mVoucherId;
      if ((this.mDoc != null) && (FinskyApp.get().getExperiments(this.mAccount.name).isEnabled(12603718L))) {
        localBuilder2.setDocument(this.mDoc);
      }
      if (this.mDocid.type == 1)
      {
        AppDetails localAppDetails = this.mDoc.getAppDetails();
        if (localAppDetails.hasEverExternallyHosted) {
          localBuilder2.appEverExternallyHosted = Boolean.valueOf(localAppDetails.everExternallyHosted);
        }
        if (localAppDetails.hasInstallLocation) {
          localBuilder2.appInstallLocation = localAppDetails.installLocation;
        }
      }
      if ((this.mIndirectProvisioningType != 0) && (FinskyApp.get().getExperiments(this.mAccount.name).isEnabled(12604230L))) {
        localBuilder2.indirectProvisioningType = this.mIndirectProvisioningType;
      }
      PurchaseParams localPurchaseParams = localBuilder2.build();
      byte[] arrayOfByte = getIntent().getByteArrayExtra("LightPurchaseFlowActivity.serverLogsCookie");
      startActivityForResult(PurchaseActivity.createIntent(this.mAccount, localPurchaseParams, arrayOfByte, paramBundle), 1);
      return;
    }
    if (paramBoolean)
    {
      showNetworkDownloadDialog();
      return;
    }
    if (paramBundle != null)
    {
      showDownloadSizeWarning(paramBundle);
      return;
    }
    PurchaseInitiator.SuccessListener local1 = new PurchaseInitiator.SuccessListener()
    {
      public final void onFreePurchaseSuccess$5f474518()
      {
        if (LightPurchaseFlowActivity.this.mDocid.type == 1)
        {
          LightPurchaseFlowActivity.this.logConfirmFreeDownload();
          LightPurchaseFlowActivity.this.success();
        }
        do
        {
          return;
          if ((LightPurchaseFlowActivity.this.mDocid.backend != 6) || (FinskyApp.get().getExperiments().isEnabled(12602952L))) {
            break;
          }
        } while (ConsumptionUtils.showAppNeededDialogIfNeeded(LightPurchaseFlowActivity.this, LightPurchaseFlowActivity.this.mAccount, LightPurchaseFlowActivity.this.mDoc, LightPurchaseFlowActivity.this.getSupportFragmentManager(), null, 5));
        LightPurchaseFlowActivity.this.success();
        return;
        LightPurchaseFlowActivity.this.launchPostSuccessConsumptionFlow();
      }
    };
    PurchaseInitiator.makeFreePurchase(this.mAccount, this.mDoc, this.mOfferType, this.mAppsContinueUrl, local1, true, true);
  }
  
  public static Intent createExternalPurchaseIntent$109c371d(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent(FinskyApp.get(), LightPurchaseFlowActivity.class);
    localIntent.setAction("com.android.vending.billing.PURCHASE");
    localIntent.addCategory("android.intent.category.DEFAULT");
    localIntent.putExtra("backend", paramInt1);
    localIntent.putExtra("document_type", paramInt2);
    localIntent.putExtra("backend_docid", paramString1);
    localIntent.putExtra("full_docid", paramString2);
    localIntent.putExtra("offer_type", 1);
    if (!TextUtils.isEmpty(paramString3)) {
      localIntent.putExtra("referral_url", paramString3);
    }
    return localIntent;
  }
  
  public static Intent createIntent$202310a9(Account paramAccount, Document paramDocument, int paramInt, DocUtils.OfferFilter paramOfferFilter, byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(FinskyApp.get(), LightPurchaseFlowActivity.class);
    localIntent.putExtra("LightPurchaseFlowActivity.account", paramAccount);
    localIntent.putExtra("LightPurchaseFlowActivity.doc", paramDocument);
    localIntent.putExtra("LightPurchaseFlowActivity.offerType", paramInt);
    if (paramOfferFilter != null) {
      localIntent.putExtra("LightPurchaseFlowActivity.offerFilter", paramOfferFilter.name());
    }
    localIntent.putExtra("LightPurchaseFlowActivity.appsContinueUrl", paramString1);
    localIntent.putExtra("LightPurchaseFlowActivity.serverLogsCookie", paramArrayOfByte);
    localIntent.putExtra("LightPurchaseFlowActivity.indirectProvisioningType", 0);
    localIntent.putExtra("LightPurchaseFlowActivity.voucherId", paramString2);
    return localIntent;
  }
  
  private void fail()
  {
    this.mFailed = true;
    setResult(0);
    logFinish(false);
    finish();
  }
  
  private PlayStore.PlayStoreBackgroundActionEvent getBackgroundEvent(int paramInt)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt).setDocument(this.mDocidStr).setCallingPackage(getCallingPackage());
    if (this.mOfferType != 0)
    {
      localBackgroundEventBuilder.setOfferType(this.mOfferType);
      boolean bool = this.mOfferRequiresCheckout;
      localBackgroundEventBuilder.event.offerCheckoutFlowRequired = bool;
      localBackgroundEventBuilder.event.hasOfferCheckoutFlowRequired = true;
    }
    return localBackgroundEventBuilder.event;
  }
  
  private String getCallerName()
  {
    String str1 = getCallingPackage();
    if (str1 == null) {
      return null;
    }
    PackageManager localPackageManager = getPackageManager();
    try
    {
      String str2 = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(str1, 0)).toString();
      return str2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  private boolean isExternalPurchaseIntent()
  {
    return "com.android.vending.billing.PURCHASE".equals(getIntent().getAction());
  }
  
  private void launchPostSuccessConsumptionFlow()
  {
    if (!ConsumptionUtils.openItem(this, this.mAccount, this.mDoc, getSupportFragmentManager(), null, 5, null))
    {
      this.mPostSuccessItemOpened = true;
      success();
    }
  }
  
  private void logConfirmFreeDownload()
  {
    FinskyApp.get().getAnalytics(this.mAccount.name).logAdMobPageView("confirmFreeDownload?doc=" + this.mDocidStr);
  }
  
  private void logFinish(boolean paramBoolean)
  {
    PlayStore.PlayStoreBackgroundActionEvent localPlayStoreBackgroundActionEvent = getBackgroundEvent(601);
    localPlayStoreBackgroundActionEvent.operationSuccess = paramBoolean;
    localPlayStoreBackgroundActionEvent.hasOperationSuccess = true;
    FinskyApp.get().getEventLogger(this.mAccount).sendBackgroundEventToSinks(localPlayStoreBackgroundActionEvent);
  }
  
  private boolean setupFromExternalPurchaseIntent(Intent paramIntent)
  {
    if (SignatureUtils.getCallingFirstPartyPackage(this) != null) {}
    for (boolean bool = true;; bool = false)
    {
      this.mCalledByFirstPartyPackage = bool;
      if ((((Boolean)G.enableThirdPartyDirectPurchases.get()).booleanValue()) || (this.mCalledByFirstPartyPackage)) {
        break;
      }
      FinskyLog.w("Called from untrusted package.", new Object[0]);
      return false;
    }
    if ((!paramIntent.hasExtra("backend")) || (!paramIntent.hasExtra("document_type")) || (!paramIntent.hasExtra("backend_docid")) || (!paramIntent.hasExtra("full_docid")))
    {
      FinskyLog.e("Missing argument.", new Object[0]);
      return false;
    }
    if (paramIntent.hasExtra("authAccount"))
    {
      String str3 = paramIntent.getStringExtra("authAccount");
      this.mAccount = AccountHandler.findAccount(str3, this);
      if (this.mAccount == null)
      {
        FinskyLog.d("Invalid account passed: %s", new Object[] { str3 });
        return false;
      }
    }
    else
    {
      this.mAccount = FinskyApp.get().getCurrentAccount();
    }
    this.mDocid = DocUtils.createDocid(paramIntent.getIntExtra("backend", 0), paramIntent.getIntExtra("document_type", 0), paramIntent.getStringExtra("backend_docid"));
    this.mDocidStr = paramIntent.getStringExtra("full_docid");
    this.mDoc = null;
    this.mOfferType = paramIntent.getIntExtra("offer_type", 0);
    this.mOfferId = paramIntent.getStringExtra("offer_id");
    this.mOfferRequiresCheckout = true;
    String str1 = paramIntent.getStringExtra("offer_filter");
    if (str1 != null) {}
    try
    {
      this.mOfferFilter = DocUtils.OfferFilter.valueOf(str1);
      String str2 = paramIntent.getStringExtra("family_consistency_token");
      FamilyUtils.saveConsistencyToken(this.mAccount.name, str2);
      this.mReferralUrl = paramIntent.getStringExtra("referral_url");
      this.mIndirectProvisioningType = paramIntent.getIntExtra("indirect_provisioning_type", 0);
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      FinskyLog.e("Invalid offer types passed: %s", new Object[] { str1 });
    }
    return false;
  }
  
  private void showDownloadSizeWarning(Bundle paramBundle)
  {
    DownloadSizeDialogFragment.newInstance(null, paramBundle).show(getSupportFragmentManager(), "LightPurchaseFlowActivity.appDownloadSizeWarningDialog");
  }
  
  private void showNetworkDownloadDialog()
  {
    DownloadNetworkDialogFragment.newInstance$41b8249f(this.mDoc.getInstallationSize()).show(getSupportFragmentManager(), "LightPurchaseFlowActivity.appDownloadNetworkDialog");
  }
  
  private void startAcquisitionFlow()
  {
    if (UiUtils.isAndroidTv())
    {
      PurchaseParams.Builder localBuilder = new PurchaseParams.Builder();
      localBuilder.docid = this.mDocid;
      localBuilder.docidStr = this.mDocidStr;
      localBuilder.offerType = this.mOfferType;
      localBuilder.offerId = this.mOfferId;
      PurchaseParams localPurchaseParams = localBuilder.setAppData(this.mAppVersionCode, this.mAppTitle, this.mAppsContinueUrl).build();
      Account localAccount = this.mAccount;
      DocUtils.OfferFilter localOfferFilter = this.mOfferFilter;
      String str = getCallerName();
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("TvIntentUtils.account", localAccount);
      localBundle.putParcelable("TvIntentUtils.purchaseParams", localPurchaseParams);
      if (localOfferFilter != null) {
        localBundle.putString("TvIntentUtils.offerFilter", localOfferFilter.name());
      }
      localBundle.putBoolean("TvIntentUtils.showOfferResolution", true);
      localBundle.putString("TvIntentUtils.breadCrumb", str);
      Intent localIntent = new Intent("com.google.android.finsky.tv.PURCHASE");
      localIntent.putExtras(localBundle);
      startActivityForResult(localIntent, 9);
      return;
    }
    if (this.mDocid.type == 1)
    {
      startActivityForResult(AppsPermissionsActivity.createIntent(this.mAccount.name, this.mDocidStr, this.mDoc, false), 2);
      return;
    }
    if ((TextUtils.isEmpty(this.mOfferId)) && (this.mOfferType == 0))
    {
      startActivityForResult(OfferResolutionActivity.createIntent(FinskyApp.get().mToc, this.mAccount, this.mDocidStr, this.mDoc, this.mOfferFilter), 3);
      return;
    }
    acquire(null, false);
  }
  
  private void startFopRequiredOrAcquisitionFlow()
  {
    boolean bool1 = isExternalPurchaseIntent();
    boolean bool2 = false;
    if (bool1) {}
    while (bool2)
    {
      this.mHeavyDialogShown = true;
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      byte[] arrayOfByte = getIntent().getByteArrayExtra("LightPurchaseFlowActivity.serverLogsCookie");
      localBuilder.setTitleId(2131362733).setMessageId(2131362665).setPositiveId(2131362062).setCallback(null, 6, null).setEventLog(1000, arrayOfByte, -1, -1, this.mAccount);
      SimpleAlertDialog localSimpleAlertDialog = localBuilder.build();
      String str = this.mAccount.name;
      PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.promptForFopNumDialogImpressions.get(str);
      localSharedPreference.put(Integer.valueOf(1 + ((Integer)localSharedPreference.get()).intValue()));
      localSimpleAlertDialog.show(getSupportFragmentManager(), "LightPurchaseFlowActivity.fopRequiredDialog");
      return;
      Document localDocument = this.mDoc;
      bool2 = false;
      if (localDocument != null)
      {
        int i = this.mDoc.mDocument.docType;
        bool2 = false;
        if (i == 1)
        {
          boolean bool3 = this.mOfferRequiresCheckout;
          bool2 = false;
          if (!bool3)
          {
            boolean bool4 = FinskyApp.get().mLibraries.getAppOwners(this.mDocidStr).isEmpty();
            bool2 = false;
            if (bool4)
            {
              boolean bool5 = UiUtils.isAndroidTv();
              bool2 = false;
              if (!bool5) {
                bool2 = PromptForFopHelper.shouldPromptForFop(this.mAccount.name);
              }
            }
          }
        }
      }
    }
    startAcquisitionFlow();
  }
  
  private void success()
  {
    if (this.mCalledByFirstPartyPackage)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("authAccount", this.mAccount.name);
      localIntent.putExtra("backend", this.mDocid.backend);
      localIntent.putExtra("document_type", this.mDocid.type);
      localIntent.putExtra("backend_docid", this.mDocid.backendDocid);
      localIntent.putExtra("offer_type", this.mOfferType);
      localIntent.putExtra("offer_id", this.mOfferId);
      localIntent.putExtra("involved_heavy_dialogs", this.mHeavyDialogShown);
      localIntent.putExtra("post_success_item_opened", this.mPostSuccessItemOpened);
      Document localDocument = this.mDoc;
      if (localDocument != null)
      {
        AppDetails localAppDetails = localDocument.getAppDetails();
        if (localAppDetails != null)
        {
          String str = localAppDetails.appType;
          if ((str != null) && (str.equalsIgnoreCase("game")))
          {
            localIntent.putExtra("is_game", true);
            if (localDocument.hasBadgeContainer()) {
              localIntent.putExtra("has_game_features", true);
            }
          }
        }
      }
      setResult(-1, localIntent);
    }
    for (;;)
    {
      logFinish(true);
      finish();
      return;
      setResult(-1);
    }
  }
  
  public String getCallingPackage()
  {
    return AndroidUtils.getCallingPackage(this);
  }
  
  protected void onActivityResult(int paramInt1, final int paramInt2, final Intent paramIntent)
  {
    switch (paramInt1)
    {
    default: 
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    case 8: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$600(LightPurchaseFlowActivity.this, paramInt2);
        }
      });
      return;
    case 7: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$700(LightPurchaseFlowActivity.this, paramInt2);
        }
      });
      return;
    case 2: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$800(LightPurchaseFlowActivity.this, paramInt2, paramIntent);
        }
      });
      return;
    case 3: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$900(LightPurchaseFlowActivity.this, paramInt2, paramIntent);
        }
      });
      return;
    case 1: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$1000(LightPurchaseFlowActivity.this, paramInt2, paramIntent);
        }
      });
      return;
    case 9: 
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          LightPurchaseFlowActivity.access$1100(LightPurchaseFlowActivity.this, paramInt2, paramIntent);
        }
      });
      return;
    }
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        LightPurchaseFlowActivity.access$1200(LightPurchaseFlowActivity.this, paramInt2);
      }
    });
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    Intent localIntent = getIntent();
    if (isExternalPurchaseIntent()) {
      if (!setupFromExternalPurchaseIntent(localIntent)) {
        fail();
      }
    }
    label471:
    for (;;)
    {
      if (paramBundle != null)
      {
        this.mDocid = ((Common.Docid)ParcelableProto.getProtoFromBundle(paramBundle, "LightPurchaseFlowActivity.docid"));
        this.mDocidStr = paramBundle.getString("LightPurchaseFlowActivity.docidStr");
        this.mDoc = ((Document)paramBundle.getParcelable("LightPurchaseFlowActivity.doc"));
        this.mOfferType = paramBundle.getInt("LightPurchaseFlowActivity.offerType", 0);
        this.mOfferId = paramBundle.getString("LightPurchaseFlowActivity.offerId");
        this.mOfferRequiresCheckout = paramBundle.getBoolean("LightPurchaseFlowActivity.offerRequiresCheckout");
        this.mAppTitle = paramBundle.getString("LightPurchaseFlowActivity.appTitle");
        this.mAppVersionCode = paramBundle.getInt("LightPurchaseFlowActivity.appVersionCode");
        this.mFailed = paramBundle.getBoolean("LightPurchaseFlowActivity.failed");
        this.mHeavyDialogShown = paramBundle.getBoolean("LightPurchaseFlowActivity.purchasePerformed");
        this.mPostSuccessItemOpened = paramBundle.getBoolean("LightPurchaseFlowActivity.postSuccessItemOpened");
        this.mTosLaunched = paramBundle.getBoolean("LightPurchaseFlowActivity.tosLaunched");
        this.mAppPermissionsLaunched = paramBundle.getBoolean("LightPurchaseFlowActivity.appPermissionsLaunched");
        this.mIndirectProvisioningType = paramBundle.getInt("LightPurchaseFlowActivity.indirectProvisioningType");
      }
      this.mSavedInstanceState = paramBundle;
      super.onCreate(paramBundle);
      return;
      String str = getCallingPackage();
      int i;
      if ((str == null) || (!str.equals(getPackageName())))
      {
        FinskyLog.w("Blocked request from external package: %s", new Object[] { str });
        i = 0;
      }
      for (;;)
      {
        if (i != 0) {
          break label471;
        }
        fail();
        break;
        this.mCalledByFirstPartyPackage = true;
        this.mAccount = ((Account)localIntent.getParcelableExtra("LightPurchaseFlowActivity.account"));
        this.mDocid = ((Common.Docid)ParcelableProto.getProtoFromIntent(localIntent, "LightPurchaseFlowActivity.docid"));
        this.mDoc = ((Document)localIntent.getParcelableExtra("LightPurchaseFlowActivity.doc"));
        this.mDocid = this.mDoc.getFullDocid();
        this.mDocidStr = this.mDoc.mDocument.docid;
        this.mOfferType = localIntent.getIntExtra("LightPurchaseFlowActivity.offerType", 0);
        this.mOfferId = localIntent.getStringExtra("LightPurchaseFlowActivity.offerId");
        if (localIntent.hasExtra("LightPurchaseFlowActivity.offerFilter")) {
          this.mOfferFilter = DocUtils.OfferFilter.valueOf(localIntent.getStringExtra("LightPurchaseFlowActivity.offerFilter"));
        }
        if (this.mOfferType != 0)
        {
          Common.Offer localOffer = this.mDoc.getOffer(this.mOfferType);
          if (localOffer == null)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(this.mOfferType);
            FinskyLog.e("Offer type not available: %d", arrayOfObject);
            i = 0;
          }
          else
          {
            this.mOfferRequiresCheckout = localOffer.checkoutFlowRequired;
          }
        }
        else
        {
          this.mAppsContinueUrl = localIntent.getStringExtra("LightPurchaseFlowActivity.appsContinueUrl");
          this.mIndirectProvisioningType = localIntent.getIntExtra("LightPurchaseFlowActivity.indirectProvisioningType", 0);
          this.mVoucherId = localIntent.getStringExtra("LightPurchaseFlowActivity.voucherId");
          i = 1;
        }
      }
    }
  }
  
  public final void onDoAcquisition()
  {
    acquire(null, false);
  }
  
  public final void onDownloadCancel()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mDoc.mDocument.backendDocid;
    FinskyLog.d("Download pre-acquisition warning dismissed for app = %s", arrayOfObject);
    fail();
  }
  
  public final void onDownloadOk(boolean paramBoolean1, boolean paramBoolean2)
  {
    String str = this.mDocid.backendDocid;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = str;
    arrayOfObject[1] = Boolean.valueOf(paramBoolean1);
    FinskyLog.d("Will queue %s to be downloaded on wifi only = %b", arrayOfObject);
    if (paramBoolean1) {
      FinskyApp.get().mInstaller.setMobileDataProhibited(str);
    }
    while ((paramBoolean2) && (!((Boolean)FinskyPreferences.downloadNetworkSettingsMessageShown.get()).booleanValue()))
    {
      DownloadNetworkSettingsDialogFragment.newInstance$1b0e3152().show(getSupportFragmentManager(), "LightPurchaseFlowActivity.appDownloadNetworkDialog");
      FinskyPreferences.downloadNetworkSettingsMessageShown.put(Boolean.valueOf(true));
      return;
      FinskyApp.get().mInstaller.setMobileDataAllowed(str);
    }
    acquire(null, false);
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    fail();
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unknown dialog callback: %d", arrayOfObject);
      return;
    case 4: 
      fail();
      return;
    case 5: 
      startActivity(IntentUtils.createViewDocumentUrlIntent(this, paramBundle.getString("dialog_details_url")));
      fail();
      return;
    }
    byte[] arrayOfByte = getIntent().getByteArrayExtra("LightPurchaseFlowActivity.serverLogsCookie");
    startActivityForResult(PromptForFopActivity.createIntent(FinskyApp.get().getCurrentAccount(), arrayOfByte), 7);
  }
  
  protected final void onReady(boolean paramBoolean)
  {
    super.onReady(paramBoolean);
    if (this.mFailed) {}
    while (this.mSavedInstanceState != null) {
      return;
    }
    PlayStore.PlayStoreBackgroundActionEvent localPlayStoreBackgroundActionEvent = getBackgroundEvent(600);
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    localFinskyEventLog.sendBackgroundEventToSinks(localPlayStoreBackgroundActionEvent);
    if (!TextUtils.isEmpty(this.mReferralUrl)) {
      localFinskyEventLog.logDeepLinkEventAndFlush(9, this.mReferralUrl, null, null, null, null);
    }
    boolean bool;
    if (!FinskyApp.get().getClientMutationCache(this.mAccount.name).isAgeVerificationRequired()) {
      bool = false;
    }
    while (bool)
    {
      String str1 = this.mAccount.name;
      int i = this.mDocid.backend;
      Document localDocument = this.mDoc;
      String str2 = null;
      if (localDocument == null) {
        str2 = this.mDocidStr;
      }
      startActivityForResult(AgeVerificationActivity.createIntent(str1, i, str2), 8);
      return;
      Libraries localLibraries = FinskyApp.get().mLibraries;
      if (this.mDocid.type == 1)
      {
        if (!localLibraries.getAppOwners(this.mDocidStr).isEmpty()) {
          bool = false;
        }
      }
      else if (LibraryUtils.isOwned(this.mDocid, localLibraries.getAccountLibrary(this.mAccount)))
      {
        bool = false;
        continue;
      }
      if (this.mDoc == null) {
        bool = true;
      } else {
        bool = this.mDoc.mDocument.mature;
      }
    }
    startFopRequiredOrAcquisitionFlow();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("LightPurchaseFlowActivity.docid", ParcelableProto.forProto(this.mDocid));
    paramBundle.putString("LightPurchaseFlowActivity.docidStr", this.mDocidStr);
    paramBundle.putParcelable("LightPurchaseFlowActivity.doc", this.mDoc);
    paramBundle.putInt("LightPurchaseFlowActivity.offerType", this.mOfferType);
    paramBundle.putString("LightPurchaseFlowActivity.offerId", this.mOfferId);
    paramBundle.putBoolean("LightPurchaseFlowActivity.offerRequiresCheckout", this.mOfferRequiresCheckout);
    paramBundle.putBoolean("LightPurchaseFlowActivity.purchasePerformed", this.mHeavyDialogShown);
    paramBundle.putBoolean("LightPurchaseFlowActivity.postSuccessItemOpened", this.mPostSuccessItemOpened);
    paramBundle.putString("LightPurchaseFlowActivity.appTitle", this.mAppTitle);
    paramBundle.putInt("LightPurchaseFlowActivity.appVersionCode", this.mAppVersionCode);
    paramBundle.putBoolean("LightPurchaseFlowActivity.failed", this.mFailed);
    paramBundle.putBoolean("LightPurchaseFlowActivity.tosLaunched", this.mTosLaunched);
    paramBundle.putBoolean("LightPurchaseFlowActivity.appPermissionsLaunched", this.mAppPermissionsLaunched);
    paramBundle.putInt("LightPurchaseFlowActivity.indirectProvisioningType", this.mIndirectProvisioningType);
  }
  
  public final void onSetupWifi()
  {
    startActivity(new Intent("android.settings.WIFI_SETTINGS"));
    fail();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity
 * JD-Core Version:    0.7.0.1
 */