package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AddressChallengeActivity;
import com.google.android.finsky.activities.SettingsActivity;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.activities.WebViewChallengeActivity;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.auth.AuthApi;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.auth.AuthStateFetchListener;
import com.google.android.finsky.billing.BillingFlowFragment;
import com.google.android.finsky.billing.BillingFlowFragment.BillingFlowHost;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.DownloadNetworkDialogFragment;
import com.google.android.finsky.billing.DownloadNetworkSettingsDialogFragment;
import com.google.android.finsky.billing.DownloadSizeDialogFragment;
import com.google.android.finsky.billing.InstantPurchaseUtils;
import com.google.android.finsky.billing.InstantPurchaseUtils.InstantPurchaseParams;
import com.google.android.finsky.billing.PreAppDownloadWarnings;
import com.google.android.finsky.billing.PreAppDownloadWarnings.Listener;
import com.google.android.finsky.billing.SuccessStep;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileActivity;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.AcknowledgementChallengeStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.AuthChallengeStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.CartDetailsStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.ChangeSubscriptionStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.CvcChallengeStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.ErrorStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.FamilyAskToBuyAuthChallengeStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.FamilyAskToBuyDescriptionStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.GiftEmailStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.InstrumentManagerPurchaseStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.LightPurchaseSuccessStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.PaymentDeclinedStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.PurchaseAuthenticationChallengeBaseStep;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.SuccessStepWithAuthChoices;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.config.PurchaseAuthUtils;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.HashingLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.Acquisition.OpenDocAction;
import com.google.android.finsky.protos.Acquisition.PostAcquisitionPrompt;
import com.google.android.finsky.protos.Acquisition.PostSuccessAction;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.ChallengeProto.AcknowledgementChallenge;
import com.google.android.finsky.protos.ChallengeProto.AuthenticationChallenge;
import com.google.android.finsky.protos.ChallengeProto.Challenge;
import com.google.android.finsky.protos.ChallengeProto.CvnChallenge;
import com.google.android.finsky.protos.ChallengeProto.FamilyWalletAuthChallenge;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.protos.ChallengeProto.PaymentsUpdateChallenge;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Gift.GiftDeliveryInfo;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.Money;
import com.google.android.finsky.protos.PlayAccountProto.BrokerRequiredDocuments;
import com.google.android.finsky.protos.PlayAccountProto.CachedPaymentsLegalDocument;
import com.google.android.finsky.protos.PlayAccountProto.CachedPlayAccountInstrument;
import com.google.android.finsky.protos.PlayAccountProto.PlayAccountGlobalPurchaseCache;
import com.google.android.finsky.protos.PlayAccountProto.PlayAccountUserPurchaseCache;
import com.google.android.finsky.protos.Purchase.AppPurchaseInfo;
import com.google.android.finsky.protos.Purchase.AuthenticationInfo;
import com.google.android.finsky.protos.Purchase.ChangeSubscription;
import com.google.android.finsky.protos.Purchase.ClientCart;
import com.google.android.finsky.protos.Purchase.DcbPurchaseInfo;
import com.google.android.finsky.protos.Purchase.InstantPurchaseRequest;
import com.google.android.finsky.protos.PurchaseDetails;
import com.google.android.finsky.protos.SingleFopPaymentsIntegratorContext;
import com.google.android.finsky.protos.StoredValueInstrument;
import com.google.android.finsky.protos.UserSettings;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.VoucherUtils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.wallet.common.pub.OrchestrationUtil;
import com.google.android.wallet.common.pub.SecurePaymentsPayload;
import com.google.android.wallet.common.pub.SecurePaymentsPayload.SecurePaymentsData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PurchaseFragment
  extends MultiStepFragment
  implements SimpleAlertDialog.Listener, BillingFlowFragment.BillingFlowHost, PreAppDownloadWarnings.Listener, SidecarFragment.Listener
{
  private Bundle mAppDownloadSizeWarningParameters;
  private Bundle mCommitChallengeResponses = new Bundle();
  private BillingFlowFragment mCompleteFlowFragment;
  private Bundle mCompleteFlowResult;
  byte[] mDocServerLogsCookie;
  PurchaseError mError;
  public String mEscrowHandleParameterKey;
  Bundle mExtraPurchaseData;
  private int mHandledPurchaseStateInstance = -1;
  public PurchaseParams mParams;
  boolean mPostSuccessItemOpened;
  private Bundle mPrepareChallengeResponses = new Bundle();
  private int mPreviousState;
  private int mPreviousSubstate;
  private String mSelectedInstrumentId;
  public CheckoutPurchaseSidecar mSidecar;
  private boolean mSkipCheckout;
  boolean mSucceeded;
  private boolean mUseDelegatedAuth;
  private boolean mUsePinBasedAuth;
  private VoucherParams mVoucherParams;
  
  private void completeCheckoutPurchase()
  {
    Instrument localInstrument = this.mSidecar.mCart.instrument;
    int i = localInstrument.instrumentFamily;
    Purchase.DcbPurchaseInfo localDcbPurchaseInfo = null;
    Bundle localBundle2;
    if (i == 2)
    {
      int m = BillingUtils.getFopVersion(localInstrument);
      localDcbPurchaseInfo = null;
      if (m == 3)
      {
        localBundle2 = this.mCompleteFlowResult;
        localDcbPurchaseInfo = new Purchase.DcbPurchaseInfo();
        String str2 = BillingLocator.getSimIdentifier();
        if (!TextUtils.isEmpty(str2))
        {
          localDcbPurchaseInfo.simIdentifierHash = str2;
          localDcbPurchaseInfo.hasSimIdentifierHash = true;
        }
        if (localBundle2 == null) {
          break label792;
        }
      }
    }
    label792:
    for (String str3 = localBundle2.getString("encrypted_passphrase");; str3 = null)
    {
      if (!TextUtils.isEmpty(str3))
      {
        localDcbPurchaseInfo.passphrase = str3;
        localDcbPurchaseInfo.hasPassphrase = true;
      }
      CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = this.mSidecar;
      Bundle localBundle1 = this.mCommitChallengeResponses;
      if ((localCheckoutPurchaseSidecar.mCart.completePurchaseChallenge != null) && (localBundle1.keySet().isEmpty()))
      {
        localCheckoutPurchaseSidecar.mCompleteChallenge = localCheckoutPurchaseSidecar.mCart.completePurchaseChallenge;
        localCheckoutPurchaseSidecar.setState(7, 0);
        return;
      }
      localCheckoutPurchaseSidecar.log(304, null);
      String str1 = BillingUtils.getRiskHeader();
      localCheckoutPurchaseSidecar.mCompletePurchaseStartedMs = System.currentTimeMillis();
      SecurePaymentsPayload localSecurePaymentsPayload = (SecurePaymentsPayload)localBundle1.getParcelable("PurchaseManagerActivity.securePaymentsPayload");
      if (localSecurePaymentsPayload != null) {
        localBundle1.remove("PurchaseManagerActivity.securePaymentsPayload");
      }
      if (localCheckoutPurchaseSidecar.mIsInstantPurchase)
      {
        Purchase.InstantPurchaseRequest localInstantPurchaseRequest = new Purchase.InstantPurchaseRequest();
        localInstantPurchaseRequest.clientCart = localCheckoutPurchaseSidecar.mCart;
        localInstantPurchaseRequest.docid = localCheckoutPurchaseSidecar.mPurchaseParams.docid;
        localInstantPurchaseRequest.offerType = localCheckoutPurchaseSidecar.mPurchaseParams.offerType;
        localInstantPurchaseRequest.hasOfferType = true;
        if (localCheckoutPurchaseSidecar.mPurchaseParams.appVersionCode > 0)
        {
          localInstantPurchaseRequest.appPurchaseInfo = new Purchase.AppPurchaseInfo();
          localInstantPurchaseRequest.appPurchaseInfo.appVersionCode = localCheckoutPurchaseSidecar.mPurchaseParams.appVersionCode;
          localInstantPurchaseRequest.appPurchaseInfo.hasAppVersionCode = true;
        }
        if (!TextUtils.isEmpty(str1))
        {
          localInstantPurchaseRequest.checkoutRiskHashedDeviceInfo = str1;
          localInstantPurchaseRequest.hasCheckoutRiskHashedDeviceInfo = true;
        }
        localInstantPurchaseRequest.authenticationInfo = localCheckoutPurchaseSidecar.mAuthenticationInfo;
        localInstantPurchaseRequest.dcbPurchaseInfo = localDcbPurchaseInfo;
        localInstantPurchaseRequest.paymentsIntegratorClientContextToken = OrchestrationUtil.createClientToken(localCheckoutPurchaseSidecar.getActivity(), BillingUtils.getInstrumentManagerThemeResourceId());
        localInstantPurchaseRequest.hasPaymentsIntegratorClientContextToken = true;
        if (localCheckoutPurchaseSidecar.mLegalDocuments != null) {
          localInstantPurchaseRequest.acceptedLegalDocument = localCheckoutPurchaseSidecar.mLegalDocuments;
        }
        localInstantPurchaseRequest.inAppPurchaseInfo = localCheckoutPurchaseSidecar.mPurchaseParams.inAppPurchaseInfo;
        localCheckoutPurchaseSidecar.mCompletePurchaseRequest = localCheckoutPurchaseSidecar.mDfeApi.instantPurchase(localInstantPurchaseRequest, new CheckoutPurchaseSidecar.InstantPurchaseListener(localCheckoutPurchaseSidecar, (byte)0), new CheckoutPurchaseSidecar.CompletePurchaseErrorListener(localCheckoutPurchaseSidecar, (byte)0));
        localCheckoutPurchaseSidecar.setState(1, 2);
        return;
      }
      Map localMap = CheckoutPurchaseSidecar.bundleToMap(localBundle1);
      if (FinskyApp.get().getExperiments().isEnabled(12603788L)) {
        localMap.put("bppcc", DfeUtils.base64Encode(OrchestrationUtil.createClientToken(localCheckoutPurchaseSidecar.getActivity(), BillingUtils.getInstrumentManagerThemeResourceId())));
      }
      if (localSecurePaymentsPayload != null)
      {
        localMap.put("pmcot", DfeUtils.base64Encode(localSecurePaymentsPayload.opaqueToken));
        SecurePaymentsPayload.SecurePaymentsData[] arrayOfSecurePaymentsData = localSecurePaymentsPayload.secureData;
        if (arrayOfSecurePaymentsData.length > 0)
        {
          int j = arrayOfSecurePaymentsData.length;
          for (int k = 0; k < j; k++)
          {
            SecurePaymentsPayload.SecurePaymentsData localSecurePaymentsData = arrayOfSecurePaymentsData[k];
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(localSecurePaymentsData.key);
            localMap.put(String.format("s7e_21_%s", arrayOfObject), localSecurePaymentsData.value);
          }
        }
      }
      for (boolean bool = true;; bool = false)
      {
        if (localCheckoutPurchaseSidecar.mGiftEmailParams != null)
        {
          Gift.GiftDeliveryInfo localGiftDeliveryInfo = new Gift.GiftDeliveryInfo();
          localGiftDeliveryInfo.recipientEmailAddress = localCheckoutPurchaseSidecar.mGiftEmailParams.recipientEmailAddress;
          localGiftDeliveryInfo.hasRecipientEmailAddress = true;
          localGiftDeliveryInfo.senderName = localCheckoutPurchaseSidecar.mGiftEmailParams.senderName;
          localGiftDeliveryInfo.hasSenderName = true;
          if (!TextUtils.isEmpty(localCheckoutPurchaseSidecar.mGiftEmailParams.giftMessage))
          {
            localGiftDeliveryInfo.giftMessage = localCheckoutPurchaseSidecar.mGiftEmailParams.giftMessage;
            localGiftDeliveryInfo.hasGiftMessage = true;
          }
          localMap.put("gdi", DfeUtils.base64Encode(Gift.GiftDeliveryInfo.toByteArray(localGiftDeliveryInfo)));
        }
        localCheckoutPurchaseSidecar.mCompletePurchaseRequest = localCheckoutPurchaseSidecar.mDfeApi.commitPurchase(localCheckoutPurchaseSidecar.mCart.purchaseContextToken, localMap, localCheckoutPurchaseSidecar.mAuthenticationInfo, localDcbPurchaseInfo, str1, bool, UserSettingsCache.getConsistencyTokens(localCheckoutPurchaseSidecar.mDfeApi.getAccountName()), new CheckoutPurchaseSidecar.CommitListener(localCheckoutPurchaseSidecar, (byte)0), new CheckoutPurchaseSidecar.CompletePurchaseErrorListener(localCheckoutPurchaseSidecar, (byte)0));
        break;
      }
    }
  }
  
  private void handleInstrumentManager()
  {
    SingleFopPaymentsIntegratorContext localSingleFopPaymentsIntegratorContext = this.mSidecar.mPrepareChallenge.paymentsUpdateChallenge.paymentsIntegratorContext;
    String str = this.mAccount.name;
    int i = BillingUtils.getInstrumentManagerThemeResourceId();
    InstrumentManagerPurchaseStep localInstrumentManagerPurchaseStep = new InstrumentManagerPurchaseStep();
    localInstrumentManagerPurchaseStep.setArguments(InstrumentManagerPurchaseStep.createArgs(str, localSingleFopPaymentsIntegratorContext, i));
    showStep(localInstrumentManagerPurchaseStep);
  }
  
  private boolean handleInstrumentSelected(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    this.mSelectedInstrumentId = paramString;
    preparePurchase(null, null);
    return true;
  }
  
  private void handlePreparedForCartDetails(boolean paramBoolean)
  {
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = this.mSidecar;
    boolean bool = localCheckoutPurchaseSidecar.mCartExpandedAtStart;
    localCheckoutPurchaseSidecar.mCartExpandedAtStart = false;
    showStep(CartDetailsStep.newInstance(this.mParams.docid.backend, this.mSidecar.mCart, paramBoolean, bool, this.mSidecar.mGiftEmailParams));
  }
  
  private boolean handlePromoRedeemed(RedeemCodeResult paramRedeemCodeResult)
  {
    if (paramRedeemCodeResult == null) {
      return false;
    }
    if (paramRedeemCodeResult.mIsInstallAppDeferred)
    {
      String str = paramRedeemCodeResult.getVoucherId();
      if (!TextUtils.isEmpty(str))
      {
        this.mVoucherParams = new VoucherParams(str, true, true);
        preparePurchase(null, null);
        return true;
      }
      this.mSkipCheckout = true;
      showAppDownloadWarningAndContinue();
      return true;
    }
    this.mSucceeded = true;
    this.mExtraPurchaseData = paramRedeemCodeResult.mExtraPurchaseData;
    finish();
    return true;
  }
  
  public static PurchaseFragment newInstance(Account paramAccount, PurchaseParams paramPurchaseParams, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MultiStepFragment.account", paramAccount);
    localBundle.putParcelable("PurchaseFragment.params", paramPurchaseParams);
    localBundle.putByteArray("PurchaseFragment.docServerLogsCookie", paramArrayOfByte);
    localBundle.putBundle("PurchaseFragment.appDownloadSizeWarningArgs", paramBundle);
    PurchaseFragment localPurchaseFragment = new PurchaseFragment();
    localPurchaseFragment.setArguments(localBundle);
    return localPurchaseFragment;
  }
  
  private void removeCompleteFlowFragment()
  {
    if (this.mCompleteFlowFragment != null)
    {
      getChildFragmentManager().beginTransaction().remove(this.mCompleteFlowFragment).commit();
      this.mCompleteFlowFragment = null;
    }
  }
  
  private void runCompleteFlowAndContinue()
  {
    PreAppDownloadWarnings.prohibitMobileDownloadIfWifiOnly(getContext(), this.mParams.docid.backendDocid);
    if (this.mSkipCheckout)
    {
      this.mSidecar.installApp();
      finish();
      return;
    }
    Instrument localInstrument = this.mSidecar.mCart.instrument;
    if (localInstrument.instrumentFamily == 2)
    {
      int i = BillingUtils.getFopVersion(localInstrument);
      if (this.mCompleteFlowFragment != null)
      {
        FinskyLog.wtf("No complete flow fragment expected.", new Object[0]);
        return;
      }
      if (i == 3)
      {
        FinskyLog.d("Start complete flow for DCB3 instrument.", new Object[0]);
        Account localAccount = this.mAccount;
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("CompleteDcb3Flow.account", localAccount);
        localBundle.putParcelable("CompleteDcb3Flow.instrument", ParcelableProto.forProto(localInstrument));
        CompleteDcb3FlowFragment localCompleteDcb3FlowFragment = new CompleteDcb3FlowFragment();
        localCompleteDcb3FlowFragment.setArguments(localBundle);
        this.mCompleteFlowFragment = localCompleteDcb3FlowFragment;
      }
      if (this.mCompleteFlowFragment != null)
      {
        showLoading();
        getChildFragmentManager().beginTransaction().add(this.mCompleteFlowFragment, "PurchaseFragment.completeFlowFragment").commit();
        return;
      }
    }
    completeCheckoutPurchase();
  }
  
  private void showError(CheckoutPurchaseError paramCheckoutPurchaseError, boolean paramBoolean)
  {
    showStep(ErrorStep.newInstance(paramCheckoutPurchaseError, paramBoolean));
  }
  
  public final boolean answerChallenge(Bundle paramBundle)
  {
    int i = this.mSidecar.mState;
    switch (i)
    {
    case 9: 
    case 10: 
    case 11: 
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      FinskyLog.wtf("Cannot answer challenge in state %d", arrayOfObject);
      return false;
    case 6: 
      this.mPrepareChallengeResponses.putAll(paramBundle);
      preparePurchase(null, null);
      return true;
    }
    this.mCommitChallengeResponses.putAll(paramBundle);
    completeCheckoutPurchase();
    return true;
  }
  
  public final void finish()
  {
    ((Listener)getActivity()).onFinished();
  }
  
  protected final int getBackendId()
  {
    return this.mParams.docid.backend;
  }
  
  public final void launchBillingProfile()
  {
    Account localAccount = this.mAccount;
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = this.mSidecar;
    CheckoutPurchaseError localCheckoutPurchaseError = localCheckoutPurchaseSidecar.mCheckoutPurchaseError;
    String str1 = null;
    if (localCheckoutPurchaseError != null) {
      str1 = localCheckoutPurchaseSidecar.mCheckoutPurchaseError.purchaseContextToken;
    }
    if ((TextUtils.isEmpty(str1)) && (localCheckoutPurchaseSidecar.mCart != null))
    {
      str1 = localCheckoutPurchaseSidecar.mCart.purchaseContextToken;
      startActivityForResult(BillingProfileActivity.createIntent(localAccount, str1, this.mParams.docid, this.mParams.offerType), 1);
      return;
    }
    Object[] arrayOfObject = new Object[1];
    if (localCheckoutPurchaseSidecar.mCart == null) {}
    for (String str2 = "cart";; str2 = "purchaseContextToken")
    {
      arrayOfObject[0] = str2;
      FinskyLog.wtf("Unexpected null %s.", arrayOfObject);
      break;
    }
  }
  
  public final void launchSettingsForAuthChallenge()
  {
    startActivityForResult(new Intent(FinskyApp.get(), SettingsActivity.class), 4);
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    boolean bool = false;
    switch (paramInt1)
    {
    }
    for (;;)
    {
      if (!bool) {
        logImpression(this.mCurrentFragment);
      }
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      bool = false;
      if (paramInt2 == -1)
      {
        bool = handleInstrumentSelected(paramIntent.getStringExtra("BillingProfileActivity.selectedInstrumentId"));
        if (!bool)
        {
          bool = handlePromoRedeemed((RedeemCodeResult)paramIntent.getParcelableExtra("BillingProfileActivity.redeemPromoCodeResult"));
          continue;
          if (paramInt2 == -1)
          {
            bool = answerChallenge(paramIntent.getBundleExtra("challenge_response"));
          }
          else
          {
            finish();
            bool = true;
            continue;
            bool = false;
            if (paramInt2 == -1)
            {
              RedeemCodeResult localRedeemCodeResult = (RedeemCodeResult)paramIntent.getParcelableExtra("RedeemCodeBaseActivity.redeem_code_result");
              bool = false;
              if (localRedeemCodeResult != null)
              {
                bool = handleInstrumentSelected(localRedeemCodeResult.mStoredValueInstrumentId);
                if (!bool)
                {
                  bool = handlePromoRedeemed(localRedeemCodeResult);
                  continue;
                  if ((this.mCurrentFragment instanceof AuthChallengeStep))
                  {
                    AuthChallengeStep localAuthChallengeStep = (AuthChallengeStep)this.mCurrentFragment;
                    int i = PurchaseAuthUtils.getPurchaseAuthType(localAuthChallengeStep.mAccountName);
                    if (i != localAuthChallengeStep.mPurchaseAuthBeforeManageSettings)
                    {
                      Object[] arrayOfObject2 = new Object[2];
                      arrayOfObject2[0] = Integer.valueOf(localAuthChallengeStep.mPurchaseAuthBeforeManageSettings);
                      arrayOfObject2[1] = Integer.valueOf(i);
                      FinskyLog.d("PurchaseAuth changed from %d to %d.", arrayOfObject2);
                      if (i == 0) {
                        ((PurchaseFragment)localAuthChallengeStep.mParentFragment).preparePurchase(null, null);
                      }
                    }
                    for (bool = true;; bool = false)
                    {
                      break;
                      localAuthChallengeStep.updatePasswordHelpAndPurchaseDisclaimer();
                    }
                  }
                  Object[] arrayOfObject1 = new Object[1];
                  arrayOfObject1[0] = this.mCurrentFragment;
                  FinskyLog.wtf("Unexpected fragment: %s", arrayOfObject1);
                  bool = false;
                }
              }
            }
          }
        }
      }
    }
  }
  
  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof PlayStoreUiElementNode)) {
      throw new IllegalStateException("Activity must implement PlayStoreUiElementNode");
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mSidecar = ((CheckoutPurchaseSidecar)this.mFragmentManager.findFragmentByTag("PurchaseFragment.sidecar"));
      this.mHandledPurchaseStateInstance = paramBundle.getInt("PurchaseFragment.handledStateInstance");
      this.mPreviousState = paramBundle.getInt("PurchaseFragment.previousState");
      this.mPreviousSubstate = paramBundle.getInt("PurchaseFragment.previousSubstate");
    }
    Bundle localBundle = this.mArguments;
    this.mParams = ((PurchaseParams)localBundle.getParcelable("PurchaseFragment.params"));
    this.mDocServerLogsCookie = localBundle.getByteArray("PurchaseFragment.docServerLogsCookie");
    this.mAppDownloadSizeWarningParameters = localBundle.getBundle("PurchaseFragment.appDownloadSizeWarningArgs");
    if (paramBundle != null)
    {
      this.mSelectedInstrumentId = paramBundle.getString("PurchaseFragment.selectedInstrumentId");
      this.mVoucherParams = ((VoucherParams)paramBundle.getParcelable("PurchaseFragment.voucherParams"));
      this.mPrepareChallengeResponses = paramBundle.getBundle("PurchaseFragment.prepareChallengeResponses");
      this.mCommitChallengeResponses = paramBundle.getBundle("PurchaseFragment.commitChallengeResponses");
      this.mError = ((PurchaseError)paramBundle.getParcelable("PurchaseFragment.error"));
      this.mSucceeded = paramBundle.getBoolean("PurchaseFragment.succeeded");
      this.mSkipCheckout = paramBundle.getBoolean("PurchaseFragment.skipCheckout");
      this.mExtraPurchaseData = paramBundle.getBundle("PurchaseFragment.extraPurchaseData");
      this.mEscrowHandleParameterKey = paramBundle.getString("PurchaseFragment.escrowHandleParameterKey");
      this.mUsePinBasedAuth = paramBundle.getBoolean("PurchaseFragment.usePinBasedAuth");
      this.mUseDelegatedAuth = paramBundle.getBoolean("PurchaseFragment.useDelegatedAuth");
      this.mPostSuccessItemOpened = paramBundle.getBoolean("PurchaseFragment.postSuccessItemOpened");
      return;
    }
    if (!TextUtils.isEmpty(this.mParams.voucherId))
    {
      this.mVoucherParams = new VoucherParams(this.mParams.voucherId, true, true);
      return;
    }
    this.mVoucherParams = new VoucherParams(null, true, VoucherUtils.hasVouchers(FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount)));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130968804, paramViewGroup, false);
  }
  
  public final void onDoAcquisition()
  {
    runCompleteFlowAndContinue();
  }
  
  public final void onDownloadCancel()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mParams.docid.backendDocid;
    FinskyLog.d("Download size warning dismissed for app = %s", arrayOfObject);
  }
  
  public final void onDownloadOk(boolean paramBoolean1, boolean paramBoolean2)
  {
    String str = this.mParams.docid.backendDocid;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = str;
    arrayOfObject[1] = Boolean.valueOf(paramBoolean1);
    FinskyLog.d("Will queue %s to be downloaded on wifi only = %b", arrayOfObject);
    if (paramBoolean1) {
      FinskyApp.get().mInstaller.setMobileDataProhibited(str);
    }
    for (;;)
    {
      if ((paramBoolean2) && (!((Boolean)FinskyPreferences.downloadNetworkSettingsMessageShown.get()).booleanValue()))
      {
        DownloadNetworkSettingsDialogFragment.newInstance$1b0e3152().show(this.mFragmentManager, "PurchaseFragment.downloadNetworkDialog");
        FinskyPreferences.downloadNetworkSettingsMessageShown.put(Boolean.valueOf(true));
      }
      runCompleteFlowAndContinue();
      return;
      FinskyApp.get().mInstaller.setMobileDataAllowed(str);
    }
  }
  
  public final void onFlowCanceled$70802698()
  {
    if (!this.mResumed)
    {
      FinskyLog.d("Not resumed, ignoring onFlowCanceled.", new Object[0]);
      return;
    }
    removeCompleteFlowFragment();
    hideLoading();
  }
  
  public final void onFlowError$3af1da62(String paramString)
  {
    if (!this.mResumed)
    {
      FinskyLog.d("Not resumed, ignoring onFlowError.", new Object[0]);
      return;
    }
    showError(new CheckoutPurchaseError(paramString), true);
    removeCompleteFlowFragment();
    hideLoading();
  }
  
  public final void onFlowFinished$127338c4(Bundle paramBundle)
  {
    if (!this.mResumed)
    {
      FinskyLog.d("Not resumed, ignoring onFlowFinished.", new Object[0]);
      return;
    }
    removeCompleteFlowFragment();
    this.mCompleteFlowResult = paramBundle;
    completeCheckoutPurchase();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 101) {
      finish();
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 101)
    {
      String str = paramBundle.getString("dialog_details_url");
      startActivity(IntentUtils.createViewDocumentUrlIntent(getActivity(), str));
      finish();
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("PurchaseFragment.handledStateInstance", this.mHandledPurchaseStateInstance);
    paramBundle.putInt("PurchaseFragment.previousState", this.mPreviousState);
    paramBundle.putInt("PurchaseFragment.previousSubstate", this.mPreviousSubstate);
    paramBundle.putBundle("PurchaseFragment.prepareChallengeResponses", this.mPrepareChallengeResponses);
    paramBundle.putBundle("PurchaseFragment.commitChallengeResponses", this.mCommitChallengeResponses);
    paramBundle.putString("PurchaseFragment.selectedInstrumentId", this.mSelectedInstrumentId);
    paramBundle.putParcelable("PurchaseFragment.voucherParams", this.mVoucherParams);
    paramBundle.putBoolean("PurchaseFragment.succeeded", this.mSucceeded);
    paramBundle.putParcelable("PurchaseFragment.error", this.mError);
    paramBundle.putBoolean("PurchaseFragment.skipCheckout", this.mSkipCheckout);
    paramBundle.putBundle("PurchaseFragment.extraPurchaseData", this.mExtraPurchaseData);
    paramBundle.putString("PurchaseFragment.escrowHandleParameterKey", this.mEscrowHandleParameterKey);
    paramBundle.putBoolean("PurchaseFragment.usePinBasedAuth", this.mUsePinBasedAuth);
    paramBundle.putBoolean("PurchaseFragment.useDelegatedAuth", this.mUseDelegatedAuth);
    paramBundle.putBoolean("PurchaseFragment.postSuccessItemOpened", this.mPostSuccessItemOpened);
  }
  
  public final void onSetupWifi()
  {
    startActivity(new Intent("android.settings.WIFI_SETTINGS"));
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mCompleteFlowFragment = ((BillingFlowFragment)getChildFragmentManager().findFragmentByTag("PurchaseFragment.completeFlowFragment"));
    if (this.mSidecar == null)
    {
      String str = this.mAccount.name;
      PurchaseParams localPurchaseParams = this.mParams;
      Bundle localBundle = new Bundle();
      localBundle.putString("authAccount", str);
      localBundle.putParcelable("CheckoutPurchaseSidecar.purchaseParams", localPurchaseParams);
      CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = new CheckoutPurchaseSidecar();
      localCheckoutPurchaseSidecar.setArguments(localBundle);
      this.mSidecar = localCheckoutPurchaseSidecar;
      this.mFragmentManager.beginTransaction().add(this.mSidecar, "PurchaseFragment.sidecar").commit();
    }
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    int i = paramSidecarFragment.mStateInstance;
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject6 = new Object[2];
      arrayOfObject6[0] = Integer.valueOf(paramSidecarFragment.mState);
      arrayOfObject6[1] = Integer.valueOf(i);
      FinskyLog.v("Received state change: state=%d, stateInstance=%d", arrayOfObject6);
    }
    if (i == this.mHandledPurchaseStateInstance)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(this.mHandledPurchaseStateInstance);
        FinskyLog.d("Already handled state %d", arrayOfObject5);
      }
      return;
    }
    this.mHandledPurchaseStateInstance = i;
    label172:
    Object localObject3;
    boolean bool6;
    InstantPurchaseUtils.InstantPurchaseParams localInstantPurchaseParams;
    Document localDocument;
    label522:
    Purchase.AuthenticationInfo localAuthenticationInfo;
    int i2;
    label751:
    Common.Offer localOffer1;
    label772:
    label795:
    PlayAccountProto.PlayAccountUserPurchaseCache localPlayAccountUserPurchaseCache;
    PlayAccountProto.PlayAccountGlobalPurchaseCache localPlayAccountGlobalPurchaseCache;
    label861:
    PurchaseDetails localPurchaseDetails;
    switch (this.mSidecar.mState)
    {
    default: 
    case 0: 
    case 14: 
      for (;;)
      {
        this.mPreviousState = paramSidecarFragment.mState;
        this.mPreviousSubstate = paramSidecarFragment.mSubstate;
        return;
        CheckoutPurchaseSidecar localCheckoutPurchaseSidecar2 = this.mSidecar;
        if (localCheckoutPurchaseSidecar2.mPurchaseParams.indirectProvisioningType == 1) {}
        for (int i3 = 14;; i3 = 13)
        {
          localCheckoutPurchaseSidecar2.setState(i3, 0);
          break;
        }
        showStep(GiftEmailStep.newInstance(this.mAccount.name, this.mParams.docid.backend, this.mParams.docidStr, this.mParams.offerId, this.mSidecar.mGiftEmailParams));
      }
    case 13: 
      boolean bool3 = FinskyApp.get().getExperiments(this.mAccount.name).isEnabled(12603718L);
      localObject3 = null;
      if (bool3)
      {
        PurchaseParams localPurchaseParams = this.mParams;
        Account localAccount = this.mAccount;
        AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(localAccount);
        UserSettings localUserSettings = UserSettingsCache.getCachedUserSettings(localAccount.name);
        Cache localCache = FinskyApp.get().getDfeApi(localAccount.name).getApiContext().mCache;
        boolean bool4 = FinskyApp.get().getExperiments(localAccount.name).isEnabled(12603886L);
        boolean bool5 = FinskyApp.get().getExperiments(localAccount.name).isEnabled(12603991L);
        bool6 = FinskyApp.get().getExperiments(localAccount.name).isEnabled(12604268L);
        localInstantPurchaseParams = new InstantPurchaseUtils.InstantPurchaseParams();
        ConnectivityManager localConnectivityManager;
        if (bool5)
        {
          localConnectivityManager = (ConnectivityManager)FinskyApp.get().getSystemService("connectivity");
          if (localConnectivityManager.getActiveNetworkInfo() != null) {
            break label751;
          }
          localInstantPurchaseParams.addDisabledReason(12);
        }
        for (;;)
        {
          if (VoucherUtils.hasVouchers(localAccountLibrary)) {
            localInstantPurchaseParams.addDisabledReason(1);
          }
          localDocument = InstantPurchaseUtils.getDocument(localPurchaseParams, localCache);
          if (localDocument == null) {
            localInstantPurchaseParams.addDisabledReason(2);
          }
          if (localUserSettings == null) {
            localInstantPurchaseParams.addDisabledReason(3);
          }
          if (localInstantPurchaseParams.mClientDisabledReasons.isEmpty()) {
            break label772;
          }
          localObject3 = localInstantPurchaseParams;
          if (!localObject3.mClientDisabledReasons.isEmpty()) {
            break label1726;
          }
          Purchase.ClientCart localClientCart2 = localObject3.mClientCart;
          localAuthenticationInfo = PurchaseAuthUtils.createAuthenticationInfo(this.mAccount.name);
          if (!localObject3.mInstrumentSuppressesAuth) {
            break label1593;
          }
          i2 = 0;
          if (i2 != 0)
          {
            Purchase.ClientCart localClientCart3 = localObject3.mClientCart;
            ChallengeProto.AuthenticationChallenge localAuthenticationChallenge2 = new ChallengeProto.AuthenticationChallenge();
            localAuthenticationChallenge2.documentTitle = localClientCart3.title;
            localAuthenticationChallenge2.hasDocumentTitle = true;
            localAuthenticationChallenge2.formattedPrice = localClientCart3.formattedPrice;
            localAuthenticationChallenge2.hasFormattedPrice = true;
            localAuthenticationChallenge2.instrumentDisplayTitle = localClientCart3.instrument.displayTitle;
            localAuthenticationChallenge2.hasInstrumentDisplayTitle = true;
            localAuthenticationChallenge2.gaiaOptOutCheckbox = new ChallengeProto.FormCheckbox();
            localClientCart3.completePurchaseChallenge = new ChallengeProto.Challenge();
            localClientCart3.completePurchaseChallenge.authenticationChallenge = localAuthenticationChallenge2;
          }
          this.mSelectedInstrumentId = localClientCart2.instrument.externalInstrumentId;
          CheckoutPurchaseSidecar localCheckoutPurchaseSidecar1 = this.mSidecar;
          localCheckoutPurchaseSidecar1.mIsInstantPurchase = true;
          localCheckoutPurchaseSidecar1.mCart = localObject3.mClientCart;
          localCheckoutPurchaseSidecar1.mAuthenticationInfo = localAuthenticationInfo;
          localCheckoutPurchaseSidecar1.mLegalDocuments = localObject3.mLegalDocuments;
          localCheckoutPurchaseSidecar1.log(302, localObject3);
          localCheckoutPurchaseSidecar1.log(303, 0, 0L, 0L);
          localCheckoutPurchaseSidecar1.setState(5, 0);
          break;
          if (!localConnectivityManager.getActiveNetworkInfo().isConnected()) {
            localInstantPurchaseParams.addDisabledReason(13);
          }
        }
        localOffer1 = localDocument.getOffer(localPurchaseParams.offerType);
        int n;
        if (localOffer1 == null)
        {
          localInstantPurchaseParams.addDisabledReason(4);
          localPlayAccountUserPurchaseCache = localUserSettings.playAccountUserPurchaseCache;
          if (localPlayAccountUserPurchaseCache == null) {
            localInstantPurchaseParams.addDisabledReason(6);
          }
          localPlayAccountGlobalPurchaseCache = localUserSettings.playAccountGlobalPurchaseCache;
          if (localPlayAccountGlobalPurchaseCache == null) {
            localInstantPurchaseParams.addDisabledReason(10);
          }
          if ((localDocument.mDocument.annotations == null) || (localDocument.mDocument.annotations.purchaseDetails == null)) {
            break label926;
          }
          n = 1;
          if (n == 0) {
            break label932;
          }
        }
        label926:
        label932:
        for (localPurchaseDetails = localDocument.mDocument.annotations.purchaseDetails;; localPurchaseDetails = null)
        {
          if (localPurchaseDetails == null) {
            localInstantPurchaseParams.addDisabledReason(11);
          }
          if (localInstantPurchaseParams.mClientDisabledReasons.isEmpty()) {
            break label938;
          }
          localObject3 = localInstantPurchaseParams;
          break;
          if (localOffer1.instantPurchaseEnabled) {
            break label795;
          }
          localInstantPurchaseParams.addDisabledReason(5);
          break label795;
          n = 0;
          break label861;
        }
        label938:
        localInstantPurchaseParams.mServerLogsCookie = localPlayAccountUserPurchaseCache.serverLogsCookie;
        if (!localPlayAccountUserPurchaseCache.instantPurchaseEnabled) {
          localInstantPurchaseParams.addDisabledReason(7);
        }
        if ((!bool4) || (localOffer1.convertedPrice.length <= 0)) {
          break label3337;
        }
      }
      break;
    }
    label1026:
    label3337:
    for (Common.Offer localOffer2 = localOffer1.convertedPrice[0];; localOffer2 = localOffer1)
    {
      PlayAccountProto.CachedPlayAccountInstrument localCachedPlayAccountInstrument1 = localPlayAccountUserPurchaseCache.playStoredValueInstrument;
      PlayAccountProto.CachedPlayAccountInstrument localCachedPlayAccountInstrument2;
      PlayAccountProto.CachedPlayAccountInstrument localCachedPlayAccountInstrument4;
      PlayAccountProto.CachedPlayAccountInstrument localCachedPlayAccountInstrument3;
      if (localCachedPlayAccountInstrument1 != null) {
        if (InstantPurchaseUtils.hasCurrencyCodeMismatch(localCachedPlayAccountInstrument1, localOffer2))
        {
          if (localPlayAccountUserPurchaseCache.lastUsedInstrument == null) {
            localInstantPurchaseParams.addDisabledReason(8);
          }
          localCachedPlayAccountInstrument2 = null;
          if (localCachedPlayAccountInstrument2 != null) {
            break label3330;
          }
          localCachedPlayAccountInstrument4 = localPlayAccountUserPurchaseCache.lastUsedInstrument;
          if (localCachedPlayAccountInstrument4 != null) {
            break label1163;
          }
          localInstantPurchaseParams.addDisabledReason(9);
          localCachedPlayAccountInstrument3 = localCachedPlayAccountInstrument2;
        }
      }
      for (;;)
      {
        if (!localInstantPurchaseParams.mClientDisabledReasons.isEmpty())
        {
          localObject3 = localInstantPurchaseParams;
          break label522;
          long l3 = localOffer2.micros;
          if (localPlayAccountUserPurchaseCache.estimatedMaxTaxRateMicros > 0)
          {
            double d = localPlayAccountUserPurchaseCache.estimatedMaxTaxRateMicros / 1000000.0D;
            l3 = (l3 * (d + 1.0D));
          }
          if (l3 <= localCachedPlayAccountInstrument1.instrument.storedValue.balance.micros)
          {
            localCachedPlayAccountInstrument2 = localCachedPlayAccountInstrument1;
            break label1026;
          }
          if ((bool6) && (localPlayAccountUserPurchaseCache.lastUsedInstrument != null)) {
            localInstantPurchaseParams.addDisabledReason(15);
          }
          localCachedPlayAccountInstrument2 = null;
          break label1026;
          label1163:
          if (InstantPurchaseUtils.hasCurrencyCodeMismatch(localCachedPlayAccountInstrument4, localOffer2))
          {
            localInstantPurchaseParams.addDisabledReason(8);
            localCachedPlayAccountInstrument3 = localCachedPlayAccountInstrument2;
            continue;
          }
          localCachedPlayAccountInstrument3 = localCachedPlayAccountInstrument4;
          continue;
        }
        localInstantPurchaseParams.mInstrumentSuppressesAuth = localCachedPlayAccountInstrument3.suppressAuthentication;
        Purchase.ClientCart localClientCart1 = new Purchase.ClientCart();
        localClientCart1.instrument = localCachedPlayAccountInstrument3.instrument;
        localClientCart1.title = localDocument.mDocument.title;
        localClientCart1.hasTitle = true;
        localClientCart1.formattedPrice = localOffer1.formattedAmount;
        localClientCart1.hasFormattedPrice = true;
        if (!TextUtils.isEmpty(localPlayAccountUserPurchaseCache.instantPurchasePriceByline))
        {
          localClientCart1.priceByline = localPlayAccountUserPurchaseCache.instantPurchasePriceByline;
          localClientCart1.hasPriceByline = true;
        }
        localClientCart1.buttonText = localOffer1.buyButtonLabel;
        localClientCart1.hasButtonText = true;
        String str9 = localOffer1.buyButtonLabel;
        StringBuilder localStringBuilder = new StringBuilder();
        PlayAccountProto.BrokerRequiredDocuments localBrokerRequiredDocuments = InstantPurchaseUtils.getBrokerRequiredDocuments(localPurchaseDetails, localPlayAccountGlobalPurchaseCache);
        PlayAccountProto.CachedPaymentsLegalDocument[] arrayOfCachedPaymentsLegalDocument = InstantPurchaseUtils.getPaymentsLegalDocumentsToAccept(localPlayAccountUserPurchaseCache.acceptedPaymentsLegalDocuments, localBrokerRequiredDocuments);
        if (arrayOfCachedPaymentsLegalDocument.length > 0) {
          localStringBuilder.append(String.format(localBrokerRequiredDocuments.promptHtmlFormat, new Object[] { str9 }));
        }
        if (localPurchaseDetails.showAgeConfirmationPrompt)
        {
          if (localStringBuilder.length() == 0) {
            localStringBuilder.append(String.format(localPlayAccountGlobalPurchaseCache.ageConfirmationPromptFormat, new Object[] { str9 }));
          }
        }
        else
        {
          label1406:
          if ((localStringBuilder.length() != 0) || (TextUtils.isEmpty(localPlayAccountUserPurchaseCache.purchaseFlowEcRefundPolicyNoticeHtml))) {
            break label1549;
          }
          localStringBuilder.append(localPlayAccountUserPurchaseCache.purchaseFlowEcRefundPolicyNoticeHtml);
        }
        for (;;)
        {
          Pair localPair = new Pair(localStringBuilder.toString(), arrayOfCachedPaymentsLegalDocument);
          if (!TextUtils.isEmpty((CharSequence)localPair.first))
          {
            localClientCart1.footerHtml = ((String)localPair.first);
            localClientCart1.hasFooterHtml = true;
            localInstantPurchaseParams.mLegalDocuments = ((PlayAccountProto.CachedPaymentsLegalDocument[])localPair.second);
          }
          localInstantPurchaseParams.mClientCart = localClientCart1;
          localObject3 = localInstantPurchaseParams;
          break;
          localStringBuilder.append(localPlayAccountGlobalPurchaseCache.messageSeparator);
          localStringBuilder.append(String.format(localPlayAccountGlobalPurchaseCache.ageConfirmationWithLegalDocumentPromptFormat, new Object[] { str9 }));
          break label1406;
          label1549:
          if ((localStringBuilder.length() > 0) && (!TextUtils.isEmpty(localPlayAccountUserPurchaseCache.purchaseFlowEcRefundPolicyNoticeWithTosHtml)))
          {
            localStringBuilder.append(localPlayAccountGlobalPurchaseCache.messageSeparator);
            localStringBuilder.append(localPlayAccountUserPurchaseCache.purchaseFlowEcRefundPolicyNoticeWithTosHtml);
          }
        }
        label1593:
        int i1 = localAuthenticationInfo.authenticationPreference;
        switch (i1)
        {
        default: 
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = Integer.valueOf(i1);
          FinskyLog.wtf("Unexpected AuthenticationPreference %d", arrayOfObject4);
          i2 = 1;
          break;
        case 2: 
          i2 = 1;
          break;
        case 3: 
          i2 = 0;
          break;
        case 4: 
          if (localAuthenticationInfo.hasLastAuthTimestampMillis)
          {
            long l2 = localAuthenticationInfo.lastAuthTimestampMillis + ((Long)G.authChallengeSessionDurationMs.get()).longValue();
            if (localAuthenticationInfo.flowStartedTimestampMillis > l2)
            {
              i2 = 1;
              break;
            }
            i2 = 0;
            break;
          }
          i2 = 1;
          break;
          label1726:
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = localObject3.getClientDisabledReasonsString();
          FinskyLog.w("Instant purchase disabled: %s", arrayOfObject3);
          preparePurchase(localObject3, null);
          break label172;
          showLoading();
          break label172;
          ChallengeProto.Challenge localChallenge2 = this.mSidecar.mPrepareChallenge;
          if (localChallenge2.acknowledgementChallenge != null)
          {
            ChallengeProto.AcknowledgementChallenge localAcknowledgementChallenge = localChallenge2.acknowledgementChallenge;
            AcknowledgementChallengeStep localAcknowledgementChallengeStep = new AcknowledgementChallengeStep();
            Bundle localBundle8 = new Bundle();
            localBundle8.putParcelable("AcknowledgementChallengeStep.challenge", ParcelableProto.forProto(localAcknowledgementChallenge));
            localAcknowledgementChallengeStep.setArguments(localBundle8);
            localAcknowledgementChallengeStep.mChallenge = localAcknowledgementChallenge;
            showStep(localAcknowledgementChallengeStep);
            break label172;
          }
          if (localChallenge2.paymentsUpdateChallenge != null)
          {
            if (localChallenge2.paymentsUpdateChallenge.useClientCart)
            {
              handlePreparedForCartDetails(true);
              break label172;
            }
            handleInstrumentManager();
            break label172;
          }
          if (localChallenge2.addressChallenge != null)
          {
            Bundle localBundle7 = new Bundle();
            localBundle7.putString("authAccount", this.mAccount.name);
            startActivityForResult(AddressChallengeActivity.getIntent(this.mParams.docid.backend, localChallenge2.addressChallenge, localBundle7), 2);
            break label172;
          }
          if (localChallenge2.webViewChallenge != null)
          {
            startActivityForResult(WebViewChallengeActivity.createIntent$14ebf4f1(this.mAccount.name, localChallenge2.webViewChallenge), 2);
            break label172;
          }
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.mParams.docid;
          FinskyLog.wtf("Don't know how to handle prepare challenge for doc: %s", arrayOfObject2);
          showError(new CheckoutPurchaseError(), true);
          break label172;
          handleInstrumentManager();
          break label172;
          int m = this.mParams.docid.backend;
          Purchase.ChangeSubscription localChangeSubscription = this.mSidecar.mChangeSubscription;
          Bundle localBundle6 = new Bundle();
          ChangeSubscriptionStep localChangeSubscriptionStep = new ChangeSubscriptionStep();
          localBundle6.putInt("ChangeSubscriptionStep.backend", m);
          localBundle6.putParcelable("ChangeSubscriptionStep.changeSubscription", ParcelableProto.forProto(localChangeSubscription));
          localChangeSubscriptionStep.setArguments(localBundle6);
          showStep(localChangeSubscriptionStep);
          break label172;
          handlePreparedForCartDetails(false);
          break label172;
          ChallengeProto.Challenge localChallenge1 = this.mSidecar.mCompleteChallenge;
          if (localChallenge1.familyWalletAuthChallenge != null)
          {
            ChallengeProto.FamilyWalletAuthChallenge localFamilyWalletAuthChallenge = localChallenge1.familyWalletAuthChallenge;
            Bundle localBundle5 = new Bundle();
            localBundle5.putParcelable("FamilyAskToBuyDescriptionStep.challenge", ParcelableProto.forProto(localFamilyWalletAuthChallenge));
            FamilyAskToBuyDescriptionStep localFamilyAskToBuyDescriptionStep = new FamilyAskToBuyDescriptionStep();
            localFamilyAskToBuyDescriptionStep.setArguments(localBundle5);
            showStep(localFamilyAskToBuyDescriptionStep);
            break label172;
          }
          if (localChallenge1.authenticationChallenge != null)
          {
            final ChallengeProto.AuthenticationChallenge localAuthenticationChallenge1 = localChallenge1.authenticationChallenge;
            final AuthApi localAuthApi = new AuthApi(this.mAccount);
            localAuthApi.fetchAuthState(new AuthStateFetchListener()
            {
              public final void onComplete(AuthState paramAnonymousAuthState)
              {
                if (!PurchaseFragment.this.isAdded()) {
                  return;
                }
                if (PurchaseFragment.this.mIsLoading) {
                  PurchaseFragment.this.hideLoading();
                }
                PurchaseFragment.this.showAuthChallengeStep(localAuthenticationChallenge1, paramAnonymousAuthState);
              }
              
              public final void onStart()
              {
                if (!PurchaseFragment.this.isAdded())
                {
                  localAuthApi.cancelAuthFetchRequest();
                  return;
                }
                PurchaseFragment.this.showLoading();
              }
            });
            break label172;
          }
          if (localChallenge1.cvnChallenge != null)
          {
            String str8 = this.mAccount.name;
            ChallengeProto.CvnChallenge localCvnChallenge = localChallenge1.cvnChallenge;
            CvcChallengeStep localCvcChallengeStep = new CvcChallengeStep();
            Bundle localBundle4 = new Bundle();
            localBundle4.putString("authAccount", str8);
            localBundle4.putParcelable("CvcChallengeStep.challenge", ParcelableProto.forProto(localCvnChallenge));
            localCvcChallengeStep.setArguments(localBundle4);
            showStep(localCvcChallengeStep);
            break label172;
          }
          if (localChallenge1.purchaseManagerChallenge != null)
          {
            startActivityForResult(PurchaseManagerActivity.createIntent(this.mAccount, localChallenge1.purchaseManagerChallenge), 5);
            break label172;
          }
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.mParams.docid;
          FinskyLog.wtf("Don't know how to handle complete challenge for doc: %s", arrayOfObject1);
          showError(new CheckoutPurchaseError(), true);
          break label172;
          Bundle localBundle3 = new Bundle();
          String str7 = this.mSidecar.mCvcEscrowHandle;
          localBundle3.putString(this.mEscrowHandleParameterKey, str7);
          answerChallenge(localBundle3);
          break label172;
          CheckoutPurchaseError localCheckoutPurchaseError2 = this.mSidecar.mCheckoutPurchaseError;
          String str4 = localCheckoutPurchaseError2.errorTitle;
          String str5 = localCheckoutPurchaseError2.errorMessageHtml;
          String str6 = localCheckoutPurchaseError2.continueButtonLabel;
          PaymentDeclinedStep localPaymentDeclinedStep = new PaymentDeclinedStep();
          Bundle localBundle2 = new Bundle();
          localBundle2.putString("PaymentDeclinedStep.title", str4);
          localBundle2.putString("PaymentDeclinedStep.messageHtml", str5);
          localBundle2.putString("PaymentDeclinedStep.continueButtonLabel", str6);
          localPaymentDeclinedStep.setArguments(localBundle2);
          showStep(localPaymentDeclinedStep);
          break label172;
          finish();
          break label172;
          this.mUseDelegatedAuth = true;
          showStep(FamilyAskToBuyAuthChallengeStep.newInstance(this.mAccount.name, this.mSidecar.mCompleteChallenge.familyWalletAuthChallenge, new AuthState(false, null, false, true, null)));
          break label172;
          switch (this.mSidecar.mSubstate)
          {
          default: 
            throw new IllegalStateException("handleSuccess() was called from substate " + this.mSidecar.mSubstate);
          case 7: 
            FinskyLog.d("Purchase succeeded", new Object[0]);
            this.mSucceeded = true;
            if ((!this.mUseDelegatedAuth) && (((Integer)FinskyPreferences.purchaseAuthType.get(this.mAccount.name).get()).intValue() == -1) && (((Integer)G.defaultPurchaseAuthentication.get()).intValue() != 0) && (!((Boolean)FinskyPreferences.hasSeenPurchaseSessionMessage.get(this.mAccount.name).get()).booleanValue())) {}
            for (int k = 1;; k = 0)
            {
              if (k == 0) {
                break label2768;
              }
              showStep(SuccessStepWithAuthChoices.newInstance(this.mAccount.name, this.mParams.docid.backend, this.mUsePinBasedAuth));
              FinskyPreferences.hasSeenPurchaseSessionMessage.get(this.mAccount.name).put(Boolean.valueOf(true));
              break;
            }
            this.mSidecar.confirmAuthChoiceSelected();
            break;
          case 8: 
            label2768:
            Acquisition.PostAcquisitionPrompt localPostAcquisitionPrompt = this.mSidecar.mPostAcquisitionPrompt;
            int j = SuccessStep.getLayoutResId(localPostAcquisitionPrompt);
            if (j == 0)
            {
              performSuccessActionAndFinish();
              break;
            }
            Bundle localBundle1 = SuccessStep.createArgs(localPostAcquisitionPrompt, j);
            LightPurchaseSuccessStep localLightPurchaseSuccessStep = new LightPurchaseSuccessStep();
            localLightPurchaseSuccessStep.setArguments(localBundle1);
            showStep(localLightPurchaseSuccessStep);
            break;
            Object localObject1 = new PurchaseError(0, 0);
            Object localObject2 = new CheckoutPurchaseError();
            boolean bool1;
            if ((this.mPreviousState == 1) && (this.mPreviousSubstate == 1))
            {
              bool1 = true;
              label2878:
              switch (this.mSidecar.mSubstate)
              {
              }
            }
            for (;;)
            {
              FinskyLog.d("Error: %s", new Object[] { localObject1 });
              if (bool1)
              {
                FinskyLog.d("Purchase failed: %s", new Object[] { localObject1 });
                this.mError = ((PurchaseError)localObject1);
              }
              showError((CheckoutPurchaseError)localObject2, bool1);
              break;
              bool1 = false;
              break label2878;
              PurchaseError localPurchaseError = new PurchaseError(2, 0);
              FragmentActivity localFragmentActivity = getActivity();
              if ((this.mSidecar.mVolleyError instanceof NetworkError)) {}
              CheckoutPurchaseError localCheckoutPurchaseError1;
              final VolleyError localVolleyError;
              for (String str1 = localFragmentActivity.getString(2131362363);; str1 = localFragmentActivity.getString(2131362123))
              {
                localCheckoutPurchaseError1 = new CheckoutPurchaseError(str1, ErrorStrings.get(getActivity(), this.mSidecar.mVolleyError));
                localVolleyError = this.mSidecar.mVolleyError;
                if ((FinskyApp.get().getExperiments(this.mAccount.name).isEnabled(12604300L)) && (this.mPreviousState == 1) && (this.mPreviousSubstate == 2)) {
                  break label3102;
                }
                localObject2 = localCheckoutPurchaseError1;
                localObject1 = localPurchaseError;
                break;
              }
              label3102:
              String str2 = AccountLibrary.getLibraryIdFromBackend(getBackendId());
              final HashingLibrary localHashingLibrary = FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount).getLibrary(str2);
              boolean bool2 = LibraryUtils.isOwned(this.mParams.docid, localHashingLibrary);
              if (this.mSidecar.mIsInstantPurchase) {}
              for (final String str3 = "instant";; str3 = "commit")
              {
                this.mEventLog.sendBackgroundEventToSinks(new BackgroundEventBuilder(630).setReason(str3).setExceptionType(localVolleyError).setServerLogsCookie(this.mSidecar.mServerLogsCookie).event);
                final long l1 = SystemClock.elapsedRealtime();
                FinskyApp.get().mLibraryReplicators.replicateAccount(this.mAccount, new String[] { str2 }, new Runnable()
                {
                  public final void run()
                  {
                    long l = SystemClock.elapsedRealtime();
                    boolean bool1 = LibraryUtils.isOwned(PurchaseFragment.this.mParams.docid, localHashingLibrary);
                    FinskyEventLog localFinskyEventLog = PurchaseFragment.this.mEventLog;
                    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(631).setReason(str3).setExceptionType(localVolleyError).setServerLogsCookie(PurchaseFragment.this.mSidecar.mServerLogsCookie).setClientLatencyMs(l - l1);
                    if (bool1 != this.val$wasOwned) {}
                    for (boolean bool2 = true;; bool2 = false)
                    {
                      localFinskyEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.setOperationSuccess(bool2).event);
                      return;
                    }
                  }
                }, "purchase_error_library_replication");
                localObject2 = localCheckoutPurchaseError1;
                localObject1 = localPurchaseError;
                break;
              }
              localObject1 = new PurchaseError(2, 0);
              localObject2 = new CheckoutPurchaseError(getString(2131361869));
              continue;
              this.mCommitChallengeResponses.clear();
              localObject1 = new PurchaseError(3, ((CheckoutPurchaseError)localObject2).permissionError);
              localObject2 = this.mSidecar.mCheckoutPurchaseError;
            }
            localCachedPlayAccountInstrument3 = localCachedPlayAccountInstrument2;
          }
          break;
        }
      }
    }
  }
  
  public final void onStop()
  {
    if (this.mSidecar != null) {
      this.mSidecar.setListener(null);
    }
    super.onStop();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
  }
  
  public final void performSuccessActionAndFinish()
  {
    if (this.mSucceeded)
    {
      Acquisition.PostAcquisitionPrompt localPostAcquisitionPrompt = this.mSidecar.mPostAcquisitionPrompt;
      if (localPostAcquisitionPrompt.postSuccessAction != null)
      {
        Acquisition.PostSuccessAction localPostSuccessAction = localPostAcquisitionPrompt.postSuccessAction;
        if (localPostSuccessAction.installApp != null) {
          FinskyLog.wtf("Invalid PostSuccessAction. Should not install app after purchase flow.", new Object[0]);
        }
        for (;;)
        {
          int i = 0;
          if (i == 0) {
            break;
          }
          FinskyLog.d("Dialog shown, waiting for user input.", new Object[0]);
          return;
          if (localPostSuccessAction.openDoc != null)
          {
            Document localDocument = new Document(localPostSuccessAction.openDoc.doc);
            boolean bool1 = ConsumptionUtils.openItem(getActivity(), this.mAccount, localDocument, this.mFragmentManager, this, 101, this.mParams);
            if (!bool1) {}
            for (boolean bool2 = true;; bool2 = false)
            {
              this.mPostSuccessItemOpened = bool2;
              i = bool1;
              break;
            }
          }
          if (localPostSuccessAction.openHome != null) {
            FinskyLog.wtf("Invalid PostSuccessAction. Should not go to home from purchase flow.", new Object[0]);
          } else if (localPostSuccessAction.purchaseFlow != null) {
            FinskyLog.wtf("Invalid PostSuccessAction. Cannot enter purchase flow from purchase flow.", new Object[0]);
          } else if (localPostSuccessAction.openContainer != null) {
            FinskyLog.wtf("Invalid PostSuccessAction. Cannot open container from purchase flow.", new Object[0]);
          } else {
            FinskyLog.w("Unsupported PostSuccessAction.", new Object[0]);
          }
        }
      }
    }
    finish();
  }
  
  public final void preparePurchase(InstantPurchaseUtils.InstantPurchaseParams paramInstantPurchaseParams, Boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    Purchase.AuthenticationInfo localAuthenticationInfo = PurchaseAuthUtils.createAuthenticationInfo(this.mAccount.name);
    CarrierBillingUtils.addPrepareOrBillingProfileParams(false, localHashMap);
    localHashMap.put("bppcc", Base64.encodeToString(OrchestrationUtil.createClientToken(getActivity(), BillingUtils.getInstrumentManagerThemeResourceId()), 8));
    this.mSidecar.prepare(this.mSelectedInstrumentId, this.mVoucherParams, this.mPrepareChallengeResponses, localAuthenticationInfo, paramInstantPurchaseParams, paramBoolean, localHashMap);
  }
  
  public void showAppDownloadWarningAndContinue()
  {
    int i = this.mParams.docid.type;
    if (PreAppDownloadWarnings.showDownloadNetworkDialog(getContext(), i))
    {
      DownloadNetworkDialogFragment.newInstance(this, null).show(this.mFragmentManager, "PurchaseFragment.downloadNetworkDialog");
      return;
    }
    if ((i == 1) && (this.mAppDownloadSizeWarningParameters != null))
    {
      DownloadSizeDialogFragment.newInstance(this, this.mAppDownloadSizeWarningParameters).show(this.mFragmentManager, "PurchaseFragment.appDownloadSizeWarningDialog");
      return;
    }
    runCompleteFlowAndContinue();
  }
  
  public final void showAuthChallengeStep(ChallengeProto.AuthenticationChallenge paramAuthenticationChallenge, AuthState paramAuthState)
  {
    if (paramAuthState.getAuthMethod() == 2) {}
    for (boolean bool = true;; bool = false)
    {
      this.mUsePinBasedAuth = bool;
      showStep(AuthChallengeStep.newInstance(this.mAccount.name, paramAuthenticationChallenge, paramAuthState));
      return;
    }
  }
  
  public final void switchVoucher(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {}
    for (this.mVoucherParams = new VoucherParams(paramString, true, true);; this.mVoucherParams = new VoucherParams(null, false, true))
    {
      preparePurchase(null, null);
      return;
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished();
  }
  
  public static class PurchaseError
    implements Parcelable
  {
    public static final Parcelable.Creator<PurchaseError> CREATOR = new Parcelable.Creator() {};
    public final int errorSubtype;
    public final int errorType;
    
    public PurchaseError(int paramInt1, int paramInt2)
    {
      this.errorType = paramInt1;
      this.errorSubtype = paramInt2;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public String toString()
    {
      return "PurchaseError{type=" + this.errorType + " subtype=" + this.errorSubtype + "}";
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.errorType);
      paramParcel.writeInt(this.errorSubtype);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.PurchaseFragment
 * JD-Core Version:    0.7.0.1
 */