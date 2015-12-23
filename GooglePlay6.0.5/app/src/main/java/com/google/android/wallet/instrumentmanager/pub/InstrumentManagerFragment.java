package com.google.android.wallet.instrumentmanager.pub;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment.ResultListener;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar.OrchestrationErrorListener;
import com.google.android.wallet.common.sidecar.SidecarFragment;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.common.util.ErrorUtils;
import com.google.android.wallet.common.util.Objects;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.PermissionDelegate;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.android.wallet.common.util.SmsSender;
import com.google.android.wallet.common.util.SmsSender.1;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.api.http.SecureRefreshPageRequest;
import com.google.android.wallet.instrumentmanager.common.util.ImUtils;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar.InstrumentManagerSmsSendListener;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar.RefreshPageResponseListener;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar.SavePageResponseListener;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar.SendSmsAndSavePageErrorListener;
import com.google.android.wallet.instrumentmanager.sidecar.InstrumentManagerSidecar.SendSmsAndSavePageResponseListener;
import com.google.android.wallet.instrumentmanager.ui.common.TopBarView;
import com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardUpdateFragment;
import com.google.android.wallet.instrumentmanager.ui.customer.CustomerFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.DummyFormFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.PopupRedirectActivity;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ExpDateEditText;
import com.google.android.wallet.ui.common.FocusedViewToTopScrollView;
import com.google.android.wallet.ui.common.Form;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.RegionCodeView;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.tax.TaxInfoEntryFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass.SecureDataHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.UiError;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass.CustomerForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass.CustomerFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardUpdateForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardUpdateFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass.TaxInfoForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass.TaxInfoFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.DependencyGraph;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.InitializeResponse;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.InstrumentManagerParameters;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.Page;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.PageValue;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.RefreshPageRequest;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.RefreshPageResponse;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageRequest;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.Api.SavePageResponse;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.DataTokens.ActionToken;
import com.google.commerce.payments.orchestration.proto.ui.instrumentmanager.DataTokens.CommonToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class InstrumentManagerFragment
  extends BaseOrchestrationFragment<InstrumentManagerSidecar>
{
  private DataTokens.CommonToken mCommonToken;
  protected boolean mHasAutoSubmittedForPage;
  private Api.InstrumentManagerParameters mInstrumentManagerParameters;
  private Api.Page mPage;
  
  private Api.PageValue getPageValue(Bundle paramBundle, byte[] paramArrayOfByte)
  {
    Api.PageValue localPageValue = new Api.PageValue();
    if ((this.mSubFormFragment instanceof CreditCardUpdateFragment))
    {
      CreditCardUpdateFragment localCreditCardUpdateFragment = (CreditCardUpdateFragment)this.mSubFormFragment;
      String str = PaymentUtils.removeNonNumericDigits(localCreditCardUpdateFragment.mCvcText.getText().toString());
      CreditCard.CreditCardUpdateFormValue localCreditCardUpdateFormValue = new CreditCard.CreditCardUpdateFormValue();
      localCreditCardUpdateFormValue.newMonth = localCreditCardUpdateFragment.mExpDateText.getExpMonth();
      localCreditCardUpdateFormValue.newYear = localCreditCardUpdateFragment.mExpDateText.getExpYear();
      localCreditCardUpdateFormValue.cvc = str;
      localPageValue.creditCardUpdateFormValue = localCreditCardUpdateFormValue;
    }
    for (;;)
    {
      if (paramArrayOfByte != null) {
        localPageValue.dependencyGraphRequestToken = paramArrayOfByte;
      }
      return localPageValue;
      if ((this.mSubFormFragment instanceof CustomerFragment))
      {
        CustomerFragment localCustomerFragment = (CustomerFragment)this.mSubFormFragment;
        CustomerFormOuterClass.CustomerFormValue localCustomerFormValue = new CustomerFormOuterClass.CustomerFormValue();
        if (localCustomerFragment.mRegionCodeView != null) {
          localCustomerFormValue.legalCountryCode = RegionCode.toCountryCode(localCustomerFragment.mRegionCodeView.getSelectedRegionCode());
        }
        if (localCustomerFragment.mLegalMessage != null) {
          localCustomerFormValue.legalDocData = localCustomerFragment.mLegalMessage.opaqueData;
        }
        int i = localCustomerFragment.mFieldData.size();
        int j = 0;
        if (j < i)
        {
          Form localForm = (Form)((FormFragment.FieldData)localCustomerFragment.mFieldData.get(j)).field;
          if ((localForm instanceof TaxInfoEntryFragment))
          {
            TaxInfoEntryFragment localTaxInfoEntryFragment = (TaxInfoEntryFragment)localForm;
            TaxInfoFormOuterClass.TaxInfoForm localTaxInfoForm = (TaxInfoFormOuterClass.TaxInfoForm)localTaxInfoEntryFragment.mFormProtos.get(localTaxInfoEntryFragment.mCurrentFormIndex);
            int k = localTaxInfoForm.taxInfoField.length;
            TaxInfoFormOuterClass.TaxInfoFormValue localTaxInfoFormValue = new TaxInfoFormOuterClass.TaxInfoFormValue();
            localTaxInfoFormValue.taxInfoFormId = localTaxInfoForm.id;
            localTaxInfoFormValue.taxInfoValue = new UiFieldOuterClass.UiFieldValue[k];
            for (int m = 0; m < k; m++) {
              localTaxInfoFormValue.taxInfoValue[m] = WalletUiUtils.createUiFieldValue(localTaxInfoEntryFragment.getUiFieldViewAtIndex(m), localTaxInfoForm.taxInfoField[m]);
            }
            localCustomerFormValue.taxInfo = localTaxInfoFormValue;
          }
          for (;;)
          {
            j++;
            break;
            if ((localForm instanceof AddressEntryFragment)) {
              localCustomerFormValue.legalAddress = ((AddressEntryFragment)localForm).getAddressFormValue$64352f99();
            } else {
              localCustomerFormValue.instrument = ImUtils.getInstrumentFormValueFromFragment(localForm, paramBundle);
            }
          }
        }
        localPageValue.newCustomer = localCustomerFormValue;
      }
      else
      {
        localPageValue.newInstrument = ImUtils.getInstrumentFormValueFromFragment(this.mSubFormFragment, paramBundle);
      }
    }
  }
  
  public static InstrumentManagerFragment newInstance(Account paramAccount, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt, Bundle paramBundle)
  {
    if (paramAccount == null) {
      throw new IllegalArgumentException("Account is a required parameter");
    }
    if (paramArrayOfByte1.length == 0) {
      throw new IllegalArgumentException("Common token is a required parameter");
    }
    if (paramArrayOfByte2.length == 0) {
      throw new IllegalArgumentException("Action token is a required parameter");
    }
    InstrumentManagerFragment localInstrumentManagerFragment = new InstrumentManagerFragment();
    Bundle localBundle = createArgsForOrchestrationFragment(paramInt, paramAccount, paramBundle);
    localBundle.putByteArray("commonToken", paramArrayOfByte1);
    localBundle.putByteArray("actionToken", paramArrayOfByte2);
    localInstrumentManagerFragment.setArguments(localBundle);
    return localInstrumentManagerFragment;
  }
  
  private void refreshPage(Api.RefreshPageRequest paramRefreshPageRequest)
  {
    this.mLastRequest = paramRefreshPageRequest;
    Map localMap = PaymentUtils.headerArrayToMap(this.mSecureDataHeader.refreshActionHeader);
    InstrumentManagerSidecar localInstrumentManagerSidecar = (InstrumentManagerSidecar)this.mSidecar;
    ResponseContextOuterClass.ResponseContext localResponseContext = this.mResponseContext;
    if (paramRefreshPageRequest.context != null) {
      throw new IllegalArgumentException("RefreshPageRequest's RequestContext should not be set by the caller");
    }
    BaseOrchestrationSidecar.OrchestrationErrorListener localOrchestrationErrorListener = new BaseOrchestrationSidecar.OrchestrationErrorListener(localInstrumentManagerSidecar, localResponseContext.logToken);
    SecureRefreshPageRequest localSecureRefreshPageRequest = new SecureRefreshPageRequest(localInstrumentManagerSidecar.mApiContext, paramRefreshPageRequest, localMap, localResponseContext.sessionData, new InstrumentManagerSidecar.RefreshPageResponseListener(localInstrumentManagerSidecar), localOrchestrationErrorListener);
    localOrchestrationErrorListener.mRequest = localSecureRefreshPageRequest;
    localInstrumentManagerSidecar.sendRequest(localSecureRefreshPageRequest, true);
    AnalyticsUtil.createAndSendRequestSentBackgroundEvent(722, 1, localResponseContext.logToken);
    localInstrumentManagerSidecar.mSendingRequestAfterSms = false;
  }
  
  private void savePage(Api.SavePageRequest paramSavePageRequest)
  {
    this.mLastRequest = paramSavePageRequest;
    Map localMap = PaymentUtils.headerArrayToMap(this.mSecureDataHeader.submitActionHeader);
    String str1;
    String str2;
    PendingIntent localPendingIntent;
    if ((this.mPage.instrumentForm != null) && (this.mPage.instrumentForm.dcbVerifyAssociation != null))
    {
      Dcb.DcbVerifyAssociationForm localDcbVerifyAssociationForm = this.mPage.instrumentForm.dcbVerifyAssociation;
      InstrumentManagerSidecar localInstrumentManagerSidecar2 = (InstrumentManagerSidecar)this.mSidecar;
      str1 = localDcbVerifyAssociationForm.smsPhoneNumber;
      str2 = localDcbVerifyAssociationForm.smsMessage;
      ResponseContextOuterClass.ResponseContext localResponseContext2 = this.mResponseContext;
      SmsSender localSmsSender = new SmsSender(localInstrumentManagerSidecar2.getActivity().getApplicationContext(), new InstrumentManagerSidecar.InstrumentManagerSmsSendListener(localInstrumentManagerSidecar2, localResponseContext2.logToken));
      if (TextUtils.isEmpty(str1)) {
        throw new IllegalArgumentException("SMS destination address must be provided");
      }
      if (!PermissionDelegate.selfHasPermission(localSmsSender.mAppContext, "android.permission.SEND_SMS"))
      {
        Log.d("SmsSender", "Sending an SMS text requires the SEND_SMS permission.");
        localSmsSender.dispatch(5);
        localInstrumentManagerSidecar2.savePage(paramSavePageRequest, localResponseContext2, localMap, new InstrumentManagerSidecar.SendSmsAndSavePageResponseListener(localInstrumentManagerSidecar2, localSmsSender), new InstrumentManagerSidecar.SendSmsAndSavePageErrorListener(localInstrumentManagerSidecar2, localResponseContext2.logToken, localSmsSender), InstrumentManagerSidecar.REQUEST_DEPENDENT_ON_SMS_TAG);
        localInstrumentManagerSidecar2.mSendingRequestAfterSms = true;
        return;
      }
      if (localSmsSender.mListener == null) {
        break label326;
      }
      localPendingIntent = PendingIntent.getBroadcast(localSmsSender.mAppContext, 0, new Intent("com.google.android.wallet.SMS_SENT_ACTION"), 0);
      localSmsSender.mAppContext.registerReceiver(new SmsSender.1(localSmsSender), new IntentFilter("com.google.android.wallet.SMS_SENT_ACTION"));
    }
    for (;;)
    {
      SmsManager.getDefault().sendTextMessage(str1, null, str2, localPendingIntent, null);
      break;
      InstrumentManagerSidecar localInstrumentManagerSidecar1 = (InstrumentManagerSidecar)this.mSidecar;
      ResponseContextOuterClass.ResponseContext localResponseContext1 = this.mResponseContext;
      localInstrumentManagerSidecar1.savePage(paramSavePageRequest, localResponseContext1, localMap, new InstrumentManagerSidecar.SavePageResponseListener(localInstrumentManagerSidecar1), new BaseOrchestrationSidecar.OrchestrationErrorListener(localInstrumentManagerSidecar1, localResponseContext1.logToken), null);
      return;
      label326:
      localPendingIntent = null;
    }
  }
  
  protected final void autoSubmitIfReadyAndAllowed()
  {
    if ((this.mSideCarInitialized) && (isAutoSubmitPending()) && (this.mSubFormFragment.isReadyToSubmit()))
    {
      this.mProgressTitle = this.mPage.progressDialogTitle;
      this.mProgressMessage = this.mPage.progressDialogText;
      this.mHasAutoSubmittedForPage = true;
      submitPage(null, Bundle.EMPTY, null);
    }
  }
  
  protected final FormFragment createAndDisplaySubFormFragment()
  {
    if ((ImUtils.isRedirectForm(this.mPage.instrumentForm)) && (Build.VERSION.SDK_INT < 14)) {
      throw new IllegalStateException("Redirect form is not supported on API < 14.");
    }
    Object localObject;
    if ((ImUtils.isRedirectForm(this.mPage.instrumentForm)) && (this.mPage.instrumentForm.redirect.displayType == 2))
    {
      if (!this.mPopupRedirectActivitySupported) {
        throw new IllegalStateException("Server requested popup redirect form, but popup UI is not supported.");
      }
      startActivityForResultFromTopLevelFragment(PopupRedirectActivity.createIntent(getActivity(), this.mPage.instrumentForm.redirect, this.mPage.title, this.mPopupRedirectActivityTheme, this.mThemeResourceId, this.mResponseContext.logToken), 501);
      localObject = DummyFormFragment.newInstance(this.mPage.instrumentForm.redirect, this.mThemeResourceId);
    }
    for (;;)
    {
      getChildFragmentManager().beginTransaction().replace(R.id.sub_form_holder, (Fragment)localObject).commit();
      return localObject;
      if (this.mPage.instrumentForm != null)
      {
        localObject = ImUtils.createFragmentForInstrumentForm(this.mPage.instrumentForm, this.mThemeResourceId);
      }
      else if (this.mPage.creditCardUpdateForm != null)
      {
        CreditCard.CreditCardUpdateForm localCreditCardUpdateForm = this.mPage.creditCardUpdateForm;
        int j = this.mThemeResourceId;
        localObject = new CreditCardUpdateFragment();
        ((CreditCardUpdateFragment)localObject).setArguments(CreditCardUpdateFragment.createArgsForFormFragment$179723a0(j, localCreditCardUpdateForm));
      }
      else
      {
        if (this.mPage.customerForm == null) {
          break;
        }
        CustomerFormOuterClass.CustomerForm localCustomerForm = this.mPage.customerForm;
        int i = this.mThemeResourceId;
        if ((localCustomerForm.legalCountrySelector != null) && (localCustomerForm.legalAddressForm != null) && (!ArrayUtils.contains(localCustomerForm.legalAddressForm.readOnlyField, 1))) {
          throw new IllegalArgumentException("Customer form with both a legal country selector and a legal address form containing a country selector is not supported");
        }
        localObject = new CustomerFragment();
        ((CustomerFragment)localObject).setArguments(CustomerFragment.createArgsForFormFragment$179723a0(i, localCustomerForm));
      }
    }
    throw new IllegalStateException("No top level form specified");
  }
  
  public final List<UiNode> getChildren()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.mPage.topInfoMessage != null) {
      localArrayList.add(this.mTopInfoText);
    }
    localArrayList.add(this.mSubFormFragment);
    return localArrayList;
  }
  
  protected final DependencyGraphOuterClass.DependencyGraph getDependencyGraph()
  {
    return this.mPage.dependencyGraph;
  }
  
  protected final String getProgressDialogMessage()
  {
    return this.mPage.progressDialogText;
  }
  
  protected final String getProgressDialogTitle()
  {
    return this.mPage.progressDialogTitle;
  }
  
  protected final ButtonContainerOuterClass.Button getSubmitButton()
  {
    return this.mPage.submitButton;
  }
  
  protected final String getTitle()
  {
    return this.mPage.title;
  }
  
  protected final ImageWithCaptionOuterClass.ImageWithCaption getTitleImage()
  {
    return this.mPage.titleImage;
  }
  
  protected final InfoMessageOuterClass.InfoMessage getTopInfoMessage()
  {
    return this.mPage.topInfoMessage;
  }
  
  public final WalletUiElement getUiElement()
  {
    return new WalletUiElement(1620, this.mResponseContext.logToken);
  }
  
  protected final boolean handleErrorResponseSubstate()
  {
    Api.SavePageResponse localSavePageResponse = ((InstrumentManagerSidecar)this.mSidecar).mSavePageResponse;
    switch (((InstrumentManagerSidecar)this.mSidecar).mSubstate)
    {
    default: 
      return false;
    case 5: 
      if (localSavePageResponse != null) {}
      for (UiErrorOuterClass.UiError localUiError = localSavePageResponse.error;; localUiError = ((InstrumentManagerSidecar)this.mSidecar).mRefreshPageResponse.error)
      {
        String str = getString(17039370);
        if ((this.mPage.autoSubmit) && (localUiError.action == 1))
        {
          str = getString(R.string.wallet_uic_retry);
          this.mHasAutoSubmittedForPage = false;
        }
        showErrorMessage(ErrorUtils.addErrorDetailsToBundle(new Bundle(), localUiError.action, getString(R.string.wallet_im_error_title), localUiError.message, localUiError.errorCode, str));
        return true;
      }
    case 4: 
      applyFormFieldMessages(localSavePageResponse.error.formFieldMessage);
      return true;
    }
    showErrorMessage(ErrorUtils.addErrorDetailsToBundle(new Bundle(), 500, getString(R.string.wallet_im_error_title), getString(R.string.wallet_im_send_sms_for_dcb_error), null, getString(R.string.wallet_uic_retry)));
    return true;
  }
  
  protected final boolean isAutoSubmitPending()
  {
    return (this.mPage.autoSubmit) && (!this.mHasAutoSubmittedForPage);
  }
  
  public final void notifyFormEvent(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      super.notifyFormEvent(paramInt, paramBundle);
    }
    for (;;)
    {
      return;
      if (((InstrumentManagerSidecar)this.mSidecar).mState != 1)
      {
        String str = paramBundle.getString("FormEventListener.EXTRA_FORM_ID");
        int i = paramBundle.getInt("FormEventListener.EXTRA_FIELD_ID");
        for (FormFieldReferenceOuterClass.FormFieldReference localFormFieldReference : this.mPage.refreshTriggerField) {
          if ((i == localFormFieldReference.fieldId) && (Objects.equals(str, localFormFieldReference.formId)))
          {
            refreshPage(paramBundle, null, localFormFieldReference);
            return;
          }
        }
      }
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mCommonToken = ((DataTokens.CommonToken)ProtoUtils.parseFrom(this.mArguments.getByteArray("commonToken"), DataTokens.CommonToken.class));
    if (paramBundle == null)
    {
      DataTokens.ActionToken localActionToken = (DataTokens.ActionToken)ProtoUtils.parseFrom(this.mArguments.getByteArray("actionToken"), DataTokens.ActionToken.class);
      ProtoUtils.log(localActionToken, "actionToken=");
      this.mResponseContext = localActionToken.initializeResponse.context;
      this.mPage = localActionToken.initializeResponse.initialPage;
      this.mSecureDataHeader = localActionToken.initializeResponse.secureHeader;
      this.mInstrumentManagerParameters = localActionToken.parameters;
      return;
    }
    this.mPage = ((Api.Page)ParcelableProto.getProtoFromBundle(paramBundle, "page"));
    this.mInstrumentManagerParameters = ((Api.InstrumentManagerParameters)ParcelableProto.getProtoFromBundle(paramBundle, "instrumentManagerParameters"));
    this.mHasAutoSubmittedForPage = paramBundle.getBoolean("hasAutoSubmittedForPage");
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("page", ParcelableProto.forProto(this.mPage));
    paramBundle.putParcelable("instrumentManagerParameters", ParcelableProto.forProto(this.mInstrumentManagerParameters));
    paramBundle.putBoolean("hasAutoSubmittedForPage", this.mHasAutoSubmittedForPage);
  }
  
  protected final void onSuccessfulResponse()
  {
    Api.SavePageResponse localSavePageResponse = ((InstrumentManagerSidecar)this.mSidecar).mSavePageResponse;
    Api.RefreshPageResponse localRefreshPageResponse = ((InstrumentManagerSidecar)this.mSidecar).mRefreshPageResponse;
    if (localSavePageResponse != null)
    {
      if (localSavePageResponse.stayOnCurrentPage)
      {
        notifyFormEvent(4, Bundle.EMPTY);
        return;
      }
      if (localSavePageResponse.flowComplete)
      {
        Bundle localBundle = new Bundle();
        if (!TextUtils.isEmpty(localSavePageResponse.instrumentId)) {
          localBundle.putString("com.google.android.wallet.instrumentmanager.INSTRUMENT_ID", localSavePageResponse.instrumentId);
        }
        this.mResultListener.onOrchestrationResult(50, localBundle);
        return;
      }
      if (localSavePageResponse.nextPage != null)
      {
        this.mHasAutoSubmittedForPage = false;
        this.mPage = localSavePageResponse.nextPage;
        updateNonSubformPageUi();
        updateSubFormFragment();
        return;
      }
      throw new IllegalStateException("SavePageResponse flowComplete flag was not set, but no error or next page was found.");
    }
    if (localRefreshPageResponse != null)
    {
      this.mHasAutoSubmittedForPage = false;
      this.mPage = localRefreshPageResponse.nextPage;
      updateNonSubformPageUi();
      updateSubFormFragment();
      return;
    }
    throw new IllegalStateException("Sidecar successful but no response was found");
  }
  
  protected final void refreshPage(Bundle paramBundle, byte[] paramArrayOfByte, FormFieldReferenceOuterClass.FormFieldReference paramFormFieldReference)
  {
    this.mLastFormEventDetailsForPageValue = paramBundle;
    this.mLastDependencyGraphRequestTokenForPageValue = paramArrayOfByte;
    Api.RefreshPageRequest localRefreshPageRequest = new Api.RefreshPageRequest();
    localRefreshPageRequest.pageValue = getPageValue(paramBundle, paramArrayOfByte);
    if (paramFormFieldReference != null) {
      localRefreshPageRequest.refreshTriggerField = ((FormFieldReferenceOuterClass.FormFieldReference)ProtoUtils.copyFrom(paramFormFieldReference));
    }
    refreshPage(localRefreshPageRequest);
  }
  
  protected final void retryLastRequest()
  {
    if ((this.mLastRequest instanceof Api.SavePageRequest))
    {
      Api.SavePageRequest localSavePageRequest = (Api.SavePageRequest)this.mLastRequest;
      localSavePageRequest.pageValue = getPageValue(this.mLastFormEventDetailsForPageValue, this.mLastDependencyGraphRequestTokenForPageValue);
      savePage(localSavePageRequest);
      return;
    }
    if ((this.mLastRequest instanceof Api.RefreshPageRequest))
    {
      Api.RefreshPageRequest localRefreshPageRequest = (Api.RefreshPageRequest)this.mLastRequest;
      localRefreshPageRequest.pageValue = getPageValue(this.mLastFormEventDetailsForPageValue, this.mLastDependencyGraphRequestTokenForPageValue);
      refreshPage(localRefreshPageRequest);
      return;
    }
    if (this.mLastRequest != null) {}
    for (String str = this.mLastRequest.getClass().getName();; str = null) {
      throw new IllegalStateException("retryLastRequest() called with invalid last request. Unexpected request class: " + str);
    }
  }
  
  protected final void submitPage(int[] paramArrayOfInt, Bundle paramBundle, byte[] paramArrayOfByte)
  {
    if (!this.mSubFormFragment.validate(paramArrayOfInt))
    {
      AnalyticsUtil.createAndSendImpressionEvent(this.mSubFormFragment, 1623);
      this.mSubFormFragment.focusOnFirstErroneousFormField();
      return;
    }
    this.mLastFormEventDetailsForPageValue = paramBundle;
    this.mLastDependencyGraphRequestTokenForPageValue = paramArrayOfByte;
    Api.SavePageRequest localSavePageRequest = new Api.SavePageRequest();
    localSavePageRequest.parameters = this.mInstrumentManagerParameters;
    localSavePageRequest.pageValue = getPageValue(paramBundle, paramArrayOfByte);
    savePage(localSavePageRequest);
  }
  
  protected final void updateProgressBarStateImpl(boolean paramBoolean)
  {
    if (this.mRootLayout != R.layout.fragment_instrument_manager_full_screen)
    {
      super.updateProgressBarStateImpl(paramBoolean);
      return;
    }
    int i;
    if ((this.mTitleSeparator.getVisibility() != 0) && (this.mProgressBar.getVisibility() != 0))
    {
      i = 1;
      if (!paramBoolean) {
        break label178;
      }
      if (i == 0) {
        break label151;
      }
      this.mProgressBar.setVisibility(0);
      label54:
      this.mProgressBarVisible = true;
      if ((!paramBoolean) || (TextUtils.isEmpty(this.mProgressTitle))) {
        break label252;
      }
      this.mProgressTopBarView.setTitle(this.mProgressTitle, null, null);
      if (i == 0) {
        break label225;
      }
      this.mProgressTopBarView.setVisibility(0);
      label98:
      this.mProgressTitleVisible = true;
      if ((!paramBoolean) || (TextUtils.isEmpty(this.mProgressMessage))) {
        break label331;
      }
      this.mProgressText.setText(this.mProgressMessage);
      if (i == 0) {
        break label304;
      }
      this.mProgressText.setVisibility(0);
    }
    for (;;)
    {
      this.mProgressMessageVisible = true;
      return;
      i = 0;
      break;
      label151:
      if (this.mProgressBarVisible) {
        break label54;
      }
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mTitleSeparator, 0);
      WalletUiUtils.animateViewAppearing(this.mProgressBar, 0, 0);
      break label54;
      label178:
      if (i != 0) {
        this.mTitleSeparator.setVisibility(0);
      }
      for (;;)
      {
        this.mProgressBarVisible = false;
        break;
        if (this.mProgressBarVisible)
        {
          WalletUiUtils.animateViewAppearing(this.mTitleSeparator, 0, 0);
          WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mProgressBar, 0);
        }
      }
      label225:
      if (this.mProgressTitleVisible) {
        break label98;
      }
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mTopBarView, 0);
      WalletUiUtils.animateViewAppearing(this.mProgressTopBarView, 0, 0);
      break label98;
      label252:
      this.mProgressTitle = null;
      if (i != 0) {
        this.mTopBarView.setVisibility(0);
      }
      for (;;)
      {
        this.mProgressTitleVisible = false;
        break;
        if (this.mProgressTitleVisible)
        {
          WalletUiUtils.animateViewAppearing(this.mTopBarView, 0, 0);
          WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mProgressTopBarView, 0);
        }
      }
      label304:
      if (!this.mProgressMessageVisible)
      {
        WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mMainContent, 0);
        WalletUiUtils.animateViewAppearing(this.mProgressText, 0, 0);
      }
    }
    label331:
    this.mProgressMessage = null;
    if (i != 0) {
      this.mMainContent.setVisibility(0);
    }
    for (;;)
    {
      this.mProgressMessageVisible = false;
      return;
      if (this.mProgressMessageVisible)
      {
        WalletUiUtils.animateViewAppearing(this.mMainContent, 0, 0);
        WalletUiUtils.animateViewDisappearingToGone(this.mProgressText, 0, 0);
      }
    }
  }
  
  protected final void updateResponseContextAndSecureDataHeader()
  {
    Api.SavePageResponse localSavePageResponse = ((InstrumentManagerSidecar)this.mSidecar).mSavePageResponse;
    Api.RefreshPageResponse localRefreshPageResponse = ((InstrumentManagerSidecar)this.mSidecar).mRefreshPageResponse;
    if (localSavePageResponse != null)
    {
      this.mResponseContext = localSavePageResponse.context;
      this.mSecureDataHeader = localSavePageResponse.secureHeader;
    }
    while (localRefreshPageResponse == null) {
      return;
    }
    this.mResponseContext = localRefreshPageResponse.context;
    this.mSecureDataHeader = localRefreshPageResponse.secureHeader;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.pub.InstrumentManagerFragment
 * JD-Core Version:    0.7.0.1
 */