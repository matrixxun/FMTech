package com.google.android.finsky.billing.giftcard;

import android.accounts.Account;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.billing.SuccessStep;
import com.google.android.finsky.billing.giftcard.steps.AddressChallengeStep;
import com.google.android.finsky.billing.giftcard.steps.ConfirmationStep;
import com.google.android.finsky.billing.giftcard.steps.InstrumentManagerRedeemStep;
import com.google.android.finsky.billing.giftcard.steps.RedeemScreenStep;
import com.google.android.finsky.billing.giftcard.steps.RedeemSuccessStep;
import com.google.android.finsky.billing.iab.InAppBillingUtils;
import com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity;
import com.google.android.finsky.billing.lightpurchase.PurchaseActivity;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams.Builder;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.Acquisition.InstallAppAction;
import com.google.android.finsky.protos.Acquisition.OpenContainerDocumentAction;
import com.google.android.finsky.protos.Acquisition.OpenDocAction;
import com.google.android.finsky.protos.Acquisition.PostAcquisitionPrompt;
import com.google.android.finsky.protos.Acquisition.PostSuccessAction;
import com.google.android.finsky.protos.Acquisition.PurchaseFlowAction;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.protos.PromoCode.RedeemCodeRequest;
import com.google.android.finsky.protos.PromoCode.RedeemCodeResponse;
import com.google.android.finsky.protos.PromoCode.UserConfirmationChallenge;
import com.google.android.finsky.protos.SingleFopPaymentsIntegratorContext;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.protobuf.nano.MessageNano;

public final class RedeemCodeFragment
  extends MultiStepFragment
  implements SimpleAlertDialog.Listener, SidecarFragment.Listener
{
  private String mAccountName;
  public int mBillingUiMode;
  private boolean mCodeScreenSkipped;
  private Common.Docid mDocid;
  private int mLastStateInstance;
  public RedeemCodeResult mRedeemCodeResult;
  private int mRedemptionContext;
  public RedeemCodeSidecar mSidecar;
  
  public static RedeemCodeFragment newInstance(String paramString1, int paramInt1, int paramInt2, int paramInt3, Common.Docid paramDocid, int paramInt4, String paramString2, int paramInt5, String paramString3)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putInt("RedeemCodeFragment.redemption_context", paramInt1);
    localBundle.putInt("RedeemCodeFragment.backend", paramInt2);
    localBundle.putInt("ui_mode", paramInt3);
    localBundle.putParcelable("RedeemCodeFragment.docid", ParcelableProto.forProto(paramDocid));
    localBundle.putInt("RedeemCodeFragment.offer_type", paramInt4);
    localBundle.putString("RedeemCodeFragment.prefill_code", paramString2);
    localBundle.putInt("RedeemCodeSidecar.im_theme_res_id", paramInt5);
    localBundle.putString("RedeemCodeFragment.partner_payload", paramString3);
    RedeemCodeFragment localRedeemCodeFragment = new RedeemCodeFragment();
    localRedeemCodeFragment.setArguments(localBundle);
    return localRedeemCodeFragment;
  }
  
  private boolean purchaseApp(Account paramAccount, Document paramDocument, String paramString)
  {
    if ((this.mRedemptionContext == 1) && (this.mDocid.type == 1))
    {
      this.mRedeemCodeResult = new RedeemCodeResult(this.mRedeemCodeResult.mStoredValueInstrumentId, true, this.mRedeemCodeResult.mExtraPurchaseData, this.mRedeemCodeResult.mLink, paramString);
      return false;
    }
    startActivityForResult(LightPurchaseFlowActivity.createIntent$202310a9(paramAccount, paramDocument, 1, null, null, null, paramString), 2);
    return true;
  }
  
  public final void finish()
  {
    Listener localListener;
    if ((this.mTarget instanceof Listener)) {
      localListener = (Listener)this.mTarget;
    }
    while (localListener == null)
    {
      FinskyLog.wtf("No listener.", new Object[0]);
      return;
      if ((getActivity() instanceof Listener)) {
        localListener = (Listener)getActivity();
      } else {
        localListener = null;
      }
    }
    localListener.onFinished();
  }
  
  protected final int getBackendId()
  {
    return this.mArguments.getInt("RedeemCodeFragment.backend");
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default: 
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    }
    if (paramInt2 == -1)
    {
      Document localDocument = new Document(this.mSidecar.getPostAcquisitionPrompt().postSuccessAction.installApp.doc);
      startActivity(IntentUtils.createViewDocumentIntent(getActivity(), localDocument));
    }
    finish();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccountName = this.mArguments.getString("authAccount");
    this.mBillingUiMode = this.mArguments.getInt("ui_mode");
    this.mRedemptionContext = this.mArguments.getInt("RedeemCodeFragment.redemption_context");
    this.mDocid = ((Common.Docid)ParcelableProto.getProtoFromBundle(this.mArguments, "RedeemCodeFragment.docid"));
    if ((this.mRedemptionContext == 1) && (this.mDocid == null)) {
      throw new IllegalStateException("Null docid in purchase context.");
    }
    if (paramBundle != null)
    {
      this.mLastStateInstance = paramBundle.getInt("RedeemCodeFragment.last_state_instance");
      this.mRedeemCodeResult = ((RedeemCodeResult)paramBundle.getParcelable("RedeemCodeFragment.redeem_code_result"));
      this.mCodeScreenSkipped = paramBundle.getBoolean("RedeemCodeFragment.code_screen_skipped");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (this.mBillingUiMode == 0) {}
    for (int i = 2130968804;; i = 2130969110) {
      return paramLayoutInflater.inflate(i, paramViewGroup, false);
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1) {
      finish();
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1)
    {
      String str = paramBundle.getString("dialog_details_url");
      startActivity(IntentUtils.createViewDocumentUrlIntent(getActivity(), str));
      finish();
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("RedeemCodeFragment.last_state_instance", this.mLastStateInstance);
    paramBundle.putParcelable("RedeemCodeFragment.redeem_code_result", this.mRedeemCodeResult);
    paramBundle.putBoolean("RedeemCodeFragment.code_screen_skipped", this.mCodeScreenSkipped);
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mSidecar = ((RedeemCodeSidecar)this.mFragmentManager.findFragmentByTag("RedeemCodeFragment.sidecar"));
    if (this.mSidecar == null)
    {
      Bundle localBundle1 = this.mArguments;
      String str1 = this.mAccountName;
      int i = this.mRedemptionContext;
      Common.Docid localDocid = this.mDocid;
      int j = localBundle1.getInt("RedeemCodeFragment.offer_type");
      int k = localBundle1.getInt("RedeemCodeSidecar.im_theme_res_id");
      String str2 = localBundle1.getString("RedeemCodeFragment.partner_payload");
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("authAccount", str1);
      localBundle2.putInt("RedeemCodeSidecar.redemption_context", i);
      localBundle2.putParcelable("RedeemCodeSidecar.docid", ParcelableProto.forProto(localDocid));
      localBundle2.putInt("RedeemCodeSidecar.offer_type", j);
      localBundle2.putInt("RedeemCodeSidecar.im_theme_res_id", k);
      localBundle2.putString("RedeemCodeSidecar.partner_payload", str2);
      RedeemCodeSidecar localRedeemCodeSidecar = new RedeemCodeSidecar();
      localRedeemCodeSidecar.setArguments(localBundle2);
      this.mSidecar = localRedeemCodeSidecar;
      this.mFragmentManager.beginTransaction().add(this.mSidecar, "RedeemCodeFragment.sidecar").commit();
    }
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    Object localObject = null;
    if (paramSidecarFragment != this.mSidecar)
    {
      FinskyLog.wtf("Received state change for unknown fragment: %s", new Object[] { paramSidecarFragment });
      return;
    }
    if (this.mSidecar.mStateInstance <= this.mLastStateInstance)
    {
      Object[] arrayOfObject8 = new Object[1];
      arrayOfObject8[0] = Integer.valueOf(this.mLastStateInstance);
      FinskyLog.d("Already received state instance %d, ignore.", arrayOfObject8);
      return;
    }
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject7 = new Object[1];
      arrayOfObject7[0] = Integer.valueOf(this.mSidecar.mState);
      FinskyLog.d("State changed: %d", arrayOfObject7);
    }
    this.mLastStateInstance = this.mSidecar.mStateInstance;
    RedeemCodeSidecar localRedeemCodeSidecar2;
    LibraryUpdateProto.LibraryUpdate[] arrayOfLibraryUpdate;
    RedeemCodeSidecar localRedeemCodeSidecar3;
    String str4;
    switch (this.mSidecar.mState)
    {
    default: 
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = Integer.valueOf(this.mSidecar.mState);
      FinskyLog.wtf("Unknown sidecar state: %d", arrayOfObject6);
      return;
    case 0: 
      String str8 = this.mArguments.getString("RedeemCodeFragment.prefill_code");
      if (!TextUtils.isEmpty(str8))
      {
        this.mCodeScreenSkipped = true;
        redeem(str8);
        return;
      }
      showStep(RedeemScreenStep.newInstance(this.mAccountName, str8, null, this.mBillingUiMode));
      return;
    case 1: 
      showLoading();
      return;
    case 5: 
      RedeemCodeSidecar localRedeemCodeSidecar7 = this.mSidecar;
      if (localRedeemCodeSidecar7.mState != 5)
      {
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(localRedeemCodeSidecar7.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject5);
      }
      for (;;)
      {
        int i1 = this.mBillingUiMode;
        boolean bool2 = this.mCodeScreenSkipped;
        Bundle localBundle4 = new Bundle();
        localBundle4.putParcelable("ConfirmationStep.challenge", ParcelableProto.forProto((MessageNano)localObject));
        localBundle4.putInt("ui_mode", i1);
        localBundle4.putBoolean("ConfirmationStep.code_screen_skipped", bool2);
        ConfirmationStep localConfirmationStep = new ConfirmationStep();
        localConfirmationStep.mChallenge = ((PromoCode.UserConfirmationChallenge)localObject);
        localConfirmationStep.setArguments(localBundle4);
        showStep(localConfirmationStep);
        return;
        localObject = localRedeemCodeSidecar7.mLastRedeemCodeResponse.userConfirmationChallenge;
      }
    case 4: 
      String str7 = this.mAccountName;
      RedeemCodeSidecar localRedeemCodeSidecar6 = this.mSidecar;
      if (localRedeemCodeSidecar6.mState != 4)
      {
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(localRedeemCodeSidecar6.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject4);
      }
      for (;;)
      {
        int n = this.mBillingUiMode;
        Bundle localBundle3 = new Bundle();
        localBundle3.putString("authAccount", str7);
        localBundle3.putParcelable("ConfirmationStep.challenge", ParcelableProto.forProto((MessageNano)localObject));
        localBundle3.putInt("ui_mode", n);
        AddressChallengeStep localAddressChallengeStep = new AddressChallengeStep();
        localAddressChallengeStep.setArguments(localBundle3);
        showStep(localAddressChallengeStep);
        return;
        localObject = localRedeemCodeSidecar6.mLastRedeemCodeResponse.addressChallenge;
      }
    case 6: 
      String str6 = this.mAccountName;
      RedeemCodeSidecar localRedeemCodeSidecar5 = this.mSidecar;
      if (localRedeemCodeSidecar5.mState != 6)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(localRedeemCodeSidecar5.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject3);
      }
      for (;;)
      {
        int m = this.mArguments.getInt("RedeemCodeSidecar.im_theme_res_id");
        InstrumentManagerRedeemStep localInstrumentManagerRedeemStep = new InstrumentManagerRedeemStep();
        localInstrumentManagerRedeemStep.setArguments(InstrumentManagerRedeemStep.createArgs(str6, (SingleFopPaymentsIntegratorContext)localObject, m));
        showStep(localInstrumentManagerRedeemStep);
        return;
        localObject = localRedeemCodeSidecar5.mLastRedeemCodeResponse.paymentsIntegratorContext;
      }
    case 2: 
      localRedeemCodeSidecar2 = this.mSidecar;
      if (localRedeemCodeSidecar2.mState != 2)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(localRedeemCodeSidecar2.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject2);
        arrayOfLibraryUpdate = null;
        localRedeemCodeSidecar3 = this.mSidecar;
        if (localRedeemCodeSidecar3.mState == 2) {
          break label897;
        }
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(localRedeemCodeSidecar3.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject1);
        str4 = null;
        label693:
        if (this.mRedemptionContext != 1) {
          break label1189;
        }
      }
      break;
    }
    label768:
    label897:
    label910:
    label923:
    label929:
    label1189:
    for (Bundle localBundle1 = InAppBillingUtils.extractExtraPurchaseData(arrayOfLibraryUpdate, this.mDocid);; localBundle1 = null)
    {
      this.mRedeemCodeResult = new RedeemCodeResult(str4, false, localBundle1, null, null);
      RedeemCodeSidecar localRedeemCodeSidecar4;
      Common.Docid localDocid;
      String str5;
      if (this.mRedemptionContext == 3)
      {
        Document localDocument = this.mSidecar.getRedeemedItemDoc();
        localRedeemCodeSidecar4 = this.mSidecar;
        PromoCode.RedeemCodeResponse localRedeemCodeResponse = localRedeemCodeSidecar4.mLastRedeemCodeResponse;
        localDocid = null;
        if (localRedeemCodeResponse != null) {
          break label910;
        }
        if ((localDocument != null) && (DocUtils.isInAppDocid(localDocument.getFullDocid())) && (localDocid != null) && (localDocid.backend == 3))
        {
          str5 = localDocid.backendDocid;
          if (FinskyApp.get().mPackageStateRepository.get(str5) == null) {
            break label923;
          }
        }
      }
      Acquisition.PostAcquisitionPrompt localPostAcquisitionPrompt;
      int i;
      for (int k = 1;; k = 0)
      {
        if (k != 0)
        {
          Intent localIntent = new Intent("com.android.vending.billing.PURCHASES_UPDATED");
          localIntent.setPackage(str5);
          getActivity().sendBroadcast(localIntent);
        }
        localPostAcquisitionPrompt = this.mSidecar.getPostAcquisitionPrompt();
        i = SuccessStep.getLayoutResId(localPostAcquisitionPrompt);
        if (i != 0) {
          break label929;
        }
        performSuccessActionAndFinish();
        return;
        arrayOfLibraryUpdate = localRedeemCodeSidecar2.mLastRedeemCodeResponse.libraryUpdate;
        break;
        str4 = localRedeemCodeSidecar3.mLastRedeemCodeResponse.storedValueInstrumentId;
        break label693;
        localDocid = localRedeemCodeSidecar4.mLastRedeemCodeResponse.consumptionAppDocid;
        break label768;
      }
      int j = this.mBillingUiMode;
      boolean bool1 = false;
      if (j == 1) {
        bool1 = true;
      }
      if ((bool1) && (localPostAcquisitionPrompt.setupWizardTemplate == null)) {
        throw new IllegalArgumentException("Setup Wizard template is required for the Setup Wizard success step.");
      }
      Bundle localBundle2 = SuccessStep.createArgs(localPostAcquisitionPrompt, i);
      localBundle2.putBoolean("RedeemSuccessStep.in_setup_wizard", bool1);
      RedeemSuccessStep localRedeemSuccessStep = new RedeemSuccessStep();
      localRedeemSuccessStep.setArguments(localBundle2);
      showStep(localRedeemSuccessStep);
      return;
      if ((this.mSidecar.mSubstate == 1) && (this.mSidecar.mVolleyError != null)) {}
      for (String str1 = ErrorStrings.get(getActivity(), this.mSidecar.mVolleyError);; str1 = this.mSidecar.mErrorHtml)
      {
        FinskyLog.d("Redemption error: %s", new Object[] { str1 });
        if (!(this.mCurrentFragment instanceof RedeemScreenStep)) {
          break;
        }
        hideLoading();
        RedeemScreenStep localRedeemScreenStep = (RedeemScreenStep)this.mCurrentFragment;
        localRedeemScreenStep.mErrorMessageHtml = str1;
        UiUtils.showKeyboard(localRedeemScreenStep.getActivity(), localRedeemScreenStep.mCodeEntry);
        localRedeemScreenStep.syncErrorTextView();
        localRedeemScreenStep.fireErrorEvents();
        return;
      }
      this.mCodeScreenSkipped = false;
      String str2 = this.mAccountName;
      RedeemCodeSidecar localRedeemCodeSidecar1 = this.mSidecar;
      PromoCode.RedeemCodeRequest localRedeemCodeRequest = localRedeemCodeSidecar1.mRequest;
      String str3 = null;
      if (localRedeemCodeRequest == null) {}
      for (;;)
      {
        showStep(RedeemScreenStep.newInstance(str2, str3, str1, this.mBillingUiMode));
        return;
        str3 = localRedeemCodeSidecar1.mRequest.code;
      }
    }
  }
  
  public final void onStop()
  {
    this.mSidecar.setListener(null);
    super.onStop();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Button localButton = (Button)this.mMainView.findViewById(2131756119);
    if (localButton != null)
    {
      localButton.setText(2131361915);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          RedeemCodeFragment.this.finish();
        }
      });
    }
  }
  
  public boolean performSuccessAction(Acquisition.PostSuccessAction paramPostSuccessAction)
  {
    Account localAccount = AccountHandler.findAccount(this.mAccountName, getActivity());
    if (paramPostSuccessAction.installApp != null)
    {
      Document localDocument1 = new Document(paramPostSuccessAction.installApp.doc);
      if ((this.mRedemptionContext == 1) && (DocUtils.isInAppDocid(this.mDocid))) {
        return false;
      }
      String str = localDocument1.getAppDetails().packageName;
      int i = localDocument1.getAppDetails().versionCode;
      PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(str);
      if ((localPackageState != null) && (localPackageState.installedVersion >= i)) {}
      for (int j = 1; j != 0; j = 0)
      {
        Intent localIntent = getActivity().getPackageManager().getLaunchIntentForPackage(str);
        if (localIntent == null) {
          localIntent = IntentUtils.createViewDocumentIntent(getActivity(), localDocument1);
        }
        startActivity(localIntent);
        return false;
      }
      return purchaseApp(localAccount, localDocument1, null);
    }
    if (paramPostSuccessAction.openDoc != null)
    {
      Document localDocument2 = new Document(paramPostSuccessAction.openDoc.doc);
      return ConsumptionUtils.openItem(getActivity(), localAccount, localDocument2, this.mFragmentManager, this, 1, null);
    }
    if (paramPostSuccessAction.openHome != null) {
      startActivity(IntentUtils.createAggregatedHomeIntent(getActivity()));
    }
    for (;;)
    {
      return false;
      if (paramPostSuccessAction.purchaseFlow != null)
      {
        Document localDocument3 = this.mSidecar.getRedeemedItemDoc();
        Document localDocument4 = new Document(paramPostSuccessAction.purchaseFlow.purchaseDoc);
        if (localDocument4.mDocument.backendId == 3) {
          return purchaseApp(localAccount, localDocument4, localDocument3.mDocument.docid);
        }
        PurchaseParams.Builder localBuilder = PurchaseParams.builder().setDocument(localDocument4);
        localBuilder.offerType = paramPostSuccessAction.purchaseFlow.purchaseOfferType;
        localBuilder.voucherId = localDocument3.mDocument.docid;
        PurchaseParams localPurchaseParams = localBuilder.build();
        startActivity(PurchaseActivity.createIntent(this.mAccount, localPurchaseParams, null, null));
      }
      else if (paramPostSuccessAction.openContainer != null)
      {
        Link localLink = paramPostSuccessAction.openContainer.link;
        if (localLink != null) {
          this.mRedeemCodeResult = new RedeemCodeResult(this.mRedeemCodeResult.mStoredValueInstrumentId, this.mRedeemCodeResult.mIsInstallAppDeferred, this.mRedeemCodeResult.mExtraPurchaseData, localLink, this.mRedeemCodeResult.getVoucherId());
        } else {
          FinskyLog.wtf("Unexpected missing link", new Object[0]);
        }
      }
      else
      {
        FinskyLog.w("Unsupported PostSuccessAction.", new Object[0]);
      }
    }
  }
  
  public final void performSuccessActionAndFinish()
  {
    Acquisition.PostAcquisitionPrompt localPostAcquisitionPrompt;
    if (this.mRedeemCodeResult != null)
    {
      localPostAcquisitionPrompt = this.mSidecar.getPostAcquisitionPrompt();
      if (localPostAcquisitionPrompt.postSuccessAction != null)
      {
        if (this.mBillingUiMode != 1) {
          break label45;
        }
        FinskyLog.wtf("Cannot perform success action during Setup Wizard", new Object[0]);
      }
    }
    label45:
    while (!performSuccessAction(localPostAcquisitionPrompt.postSuccessAction))
    {
      finish();
      return;
    }
    FinskyLog.d("Dialog shown, waiting for user input.", new Object[0]);
  }
  
  public final void redeem(String paramString)
  {
    RedeemCodeSidecar localRedeemCodeSidecar = this.mSidecar;
    localRedeemCodeSidecar.mRequest.code = paramString;
    localRedeemCodeSidecar.mRequest.hasCode = true;
    localRedeemCodeSidecar.sendRedemptionRequest();
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.RedeemCodeFragment
 * JD-Core Version:    0.7.0.1
 */