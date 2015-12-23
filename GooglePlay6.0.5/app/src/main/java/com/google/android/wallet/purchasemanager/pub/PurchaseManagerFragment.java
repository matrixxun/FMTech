package com.google.android.wallet.purchasemanager.pub;

import android.accounts.Account;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment.ResultListener;
import com.google.android.wallet.common.pub.SecurePaymentsPayload;
import com.google.android.wallet.common.pub.SecurePaymentsPayload.SecurePaymentsData;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar;
import com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar.OrchestrationErrorListener;
import com.google.android.wallet.common.sidecar.SidecarFragment;
import com.google.android.wallet.common.ui.DummyErrorMessageFragment;
import com.google.android.wallet.common.util.ErrorUtils;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.common.util.ImUtils;
import com.google.android.wallet.instrumentmanager.ui.redirect.DummyFormFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.PopupRedirectActivity;
import com.google.android.wallet.purchasemanager.api.http.SecureSubmitRequest;
import com.google.android.wallet.purchasemanager.sidecar.PurchaseManagerSidecar;
import com.google.android.wallet.purchasemanager.sidecar.PurchaseManagerSidecar.SubmitResponseListener;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass.SecureDataHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.UiError;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.FieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleFormValue.FormFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.DependencyGraph;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;
import com.google.moneta.orchestration.ui.common.SecureDataMappingOuterClass.SecureDataMapping;
import com.google.moneta.orchestration.ui.purchasemanager.Api.InitializeResponse;
import com.google.moneta.orchestration.ui.purchasemanager.Api.Page;
import com.google.moneta.orchestration.ui.purchasemanager.Api.PageValue;
import com.google.moneta.orchestration.ui.purchasemanager.Api.PurchaseManagerParameters;
import com.google.moneta.orchestration.ui.purchasemanager.Api.SubmitRequest;
import com.google.moneta.orchestration.ui.purchasemanager.Api.SubmitResponse;
import com.google.moneta.orchestration.ui.purchasemanager.DataTokens.ActionToken;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PurchaseManagerFragment
  extends BaseOrchestrationFragment<PurchaseManagerSidecar>
{
  private ClientEnvironmentConfig.AndroidEnvironmentConfig mAndroidEnvironmentConfig;
  private SecureDataMappingOuterClass.SecureDataMapping[] mForwardSecureDataMappings;
  private UiErrorOuterClass.UiError mInitializeError;
  private Api.Page mPage;
  private Api.PurchaseManagerParameters mPurchaseManagerParameters;
  private String mQueuedCheckoutOrderId;
  private boolean mQueuedIsFlowComplete;
  private boolean mReturnSecurePayloadToIntegrator;
  
  private Api.PageValue getPageValue(Bundle paramBundle, byte[] paramArrayOfByte)
  {
    Api.PageValue localPageValue = new Api.PageValue();
    localPageValue.formValue = ImUtils.getInstrumentFormValueFromFragment(this.mSubFormFragment, paramBundle);
    if (paramArrayOfByte != null) {
      localPageValue.dependencyGraphRequestToken = paramArrayOfByte;
    }
    return localPageValue;
  }
  
  public static PurchaseManagerFragment newInstance(Account paramAccount, SecurePaymentsPayload paramSecurePaymentsPayload, int paramInt, Bundle paramBundle)
  {
    if (paramAccount == null) {
      throw new IllegalArgumentException("Account is a required parameter");
    }
    if (paramSecurePaymentsPayload == null) {
      throw new IllegalArgumentException("SecurePaymentsPayload is a required parameter");
    }
    PurchaseManagerFragment localPurchaseManagerFragment = new PurchaseManagerFragment();
    Bundle localBundle = createArgsForOrchestrationFragment(paramInt, paramAccount, paramBundle);
    localBundle.putParcelable("securePaymentsPayload", paramSecurePaymentsPayload);
    localPurchaseManagerFragment.setArguments(localBundle);
    return localPurchaseManagerFragment;
  }
  
  private void notifyResultListener$615fbea4(String paramString, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    if (!TextUtils.isEmpty(paramString)) {
      localBundle.putString("com.google.android.wallet.purchasemanager.CHECKOUT_ORDER_ID", paramString);
    }
    if (paramBoolean)
    {
      this.mResultListener.onQueuedOrchestrationResult(50, localBundle);
      return;
    }
    this.mResultListener.onOrchestrationResult(50, localBundle);
  }
  
  private void submit(Api.SubmitRequest paramSubmitRequest)
  {
    this.mLastRequest = paramSubmitRequest;
    Map localMap = PaymentUtils.headerArrayToMap(this.mSecureDataHeader.submitActionHeader);
    PurchaseManagerSidecar localPurchaseManagerSidecar = (PurchaseManagerSidecar)this.mSidecar;
    ResponseContextOuterClass.ResponseContext localResponseContext = this.mResponseContext;
    if (paramSubmitRequest.context != null) {
      throw new IllegalArgumentException("SubmitRequest's RequestContext should not be set by the caller");
    }
    BaseOrchestrationSidecar.OrchestrationErrorListener localOrchestrationErrorListener = new BaseOrchestrationSidecar.OrchestrationErrorListener(localPurchaseManagerSidecar, localResponseContext.logToken);
    SecureSubmitRequest localSecureSubmitRequest = new SecureSubmitRequest(localPurchaseManagerSidecar.mApiContext, paramSubmitRequest, localMap, localResponseContext.sessionData, new PurchaseManagerSidecar.SubmitResponseListener(localPurchaseManagerSidecar), localOrchestrationErrorListener);
    localOrchestrationErrorListener.mRequest = localSecureSubmitRequest;
    localPurchaseManagerSidecar.sendRequest(localSecureSubmitRequest, true);
    AnalyticsUtil.createAndSendRequestSentBackgroundEvent(724, 1, localResponseContext.logToken);
  }
  
  protected final void autoSubmitIfReadyAndAllowed() {}
  
  protected final FormFragment createAndDisplaySubFormFragment()
  {
    Object localObject;
    if (this.mInitializeError != null)
    {
      FragmentActivity localFragmentActivity = getActivity();
      int i = this.mThemeResourceId;
      UiErrorOuterClass.UiError localUiError = this.mInitializeError;
      localObject = new DummyErrorMessageFragment();
      Bundle localBundle1 = DummyErrorMessageFragment.createArgsForFormFragment$179723a0(i, null);
      Bundle localBundle2 = new Bundle();
      ErrorUtils.addErrorDetailsToBundle(localBundle2, localUiError.action, localFragmentActivity.getString(R.string.wallet_im_error_title), localUiError.message, localUiError.errorCode, localFragmentActivity.getString(17039370));
      localBundle1.putBundle("errorDetails", localBundle2);
      ((DummyErrorMessageFragment)localObject).setArguments(localBundle1);
    }
    for (;;)
    {
      getChildFragmentManager().beginTransaction().replace(R.id.sub_form_holder, (Fragment)localObject).commit();
      return localObject;
      if ((ImUtils.isRedirectForm(this.mPage.form)) && (Build.VERSION.SDK_INT < 14)) {
        throw new IllegalStateException("Redirect form is not supported on API < 14.");
      }
      if ((ImUtils.isRedirectForm(this.mPage.form)) && (this.mPage.form.redirect.displayType == 2))
      {
        if (!this.mPopupRedirectActivitySupported) {
          throw new IllegalStateException("Server requested popup redirect form, but popup UI is not supported.");
        }
        startActivityForResultFromTopLevelFragment(PopupRedirectActivity.createIntent(getActivity(), this.mPage.form.redirect, this.mPage.title, this.mPopupRedirectActivityTheme, this.mThemeResourceId, this.mResponseContext.logToken), 501);
        localObject = DummyFormFragment.newInstance(this.mPage.form.redirect, this.mThemeResourceId);
      }
      else
      {
        if (this.mPage.form == null) {
          break;
        }
        localObject = ImUtils.createFragmentForInstrumentForm(this.mPage.form, this.mThemeResourceId);
      }
    }
    throw new IllegalStateException("No form specified");
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
    return new WalletUiElement(1619, this.mResponseContext.logToken);
  }
  
  protected final boolean handleErrorResponseSubstate()
  {
    UiErrorOuterClass.UiError localUiError = ((PurchaseManagerSidecar)this.mSidecar).mSubmitResponse.error;
    switch (((PurchaseManagerSidecar)this.mSidecar).mSubstate)
    {
    default: 
      return false;
    case 5: 
      showErrorMessage(ErrorUtils.addErrorDetailsToBundle(new Bundle(), localUiError.action, getString(R.string.wallet_im_error_title), localUiError.message, localUiError.errorCode, getString(17039370)));
      return true;
    }
    applyFormFieldMessages(localUiError.formFieldMessage);
    return true;
  }
  
  protected final boolean isAutoSubmitPending()
  {
    return false;
  }
  
  public final void onClick(View paramView)
  {
    if ((this.mQueuedIsFlowComplete) && (paramView.getId() == R.id.positive_btn) && (this.mInlineErrorMessageDetails == null))
    {
      AnalyticsUtil.createAndSendClickEvent(this.mSubFormFragment, -1, 1621);
      notifyResultListener$615fbea4(this.mQueuedCheckoutOrderId, false);
      return;
    }
    super.onClick(paramView);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      SecurePaymentsPayload localSecurePaymentsPayload = (SecurePaymentsPayload)this.mArguments.getParcelable("securePaymentsPayload");
      DataTokens.ActionToken localActionToken = (DataTokens.ActionToken)ProtoUtils.parseFrom(localSecurePaymentsPayload.opaqueToken, DataTokens.ActionToken.class);
      ProtoUtils.log(localActionToken, "actionToken=");
      Api.InitializeResponse localInitializeResponse = localActionToken.initializeResponse;
      this.mResponseContext = localInitializeResponse.context;
      this.mSecureDataHeader = localInitializeResponse.secureHeader;
      this.mPurchaseManagerParameters = localActionToken.parameters;
      this.mAndroidEnvironmentConfig = localActionToken.androidEnvironmentConfig;
      this.mReturnSecurePayloadToIntegrator = localActionToken.returnSecurePayloadToIntegrator;
      this.mForwardSecureDataMappings = localActionToken.forwardSecureDataMapping;
      switch (localInitializeResponse.flowInstruction)
      {
      case 2: 
      case 3: 
      default: 
        throw new IllegalArgumentException("Unsupported flow instruction: " + localInitializeResponse.flowInstruction);
      case 1: 
        this.mPage = localInitializeResponse.initialPage;
      }
      do
      {
        for (;;)
        {
          if (this.mPage.form != null) {
            ImUtils.applySecureData(this.mPage.form, localSecurePaymentsPayload.secureData, localActionToken.reverseSecureDataMapping);
          }
          return;
          this.mPage = localInitializeResponse.initialPage;
          this.mQueuedIsFlowComplete = true;
          this.mQueuedCheckoutOrderId = localInitializeResponse.checkoutOrderId;
          notifyResultListener$615fbea4(localInitializeResponse.checkoutOrderId, true);
        }
        this.mPage = new Api.Page();
        this.mInitializeError = localInitializeResponse.error;
      } while (this.mInitializeError.action == 2);
      throw new IllegalArgumentException("Invalid initialize error action: " + this.mInitializeError.action);
    }
    this.mPage = ((Api.Page)ParcelableProto.getProtoFromBundle(paramBundle, "page"));
    this.mPurchaseManagerParameters = ((Api.PurchaseManagerParameters)ParcelableProto.getProtoFromBundle(paramBundle, "purchaseManagerParameters"));
    this.mAndroidEnvironmentConfig = ((ClientEnvironmentConfig.AndroidEnvironmentConfig)ParcelableProto.getProtoFromBundle(paramBundle, "androidEnvironmentConfig"));
    this.mQueuedIsFlowComplete = paramBundle.getBoolean("queuedIsFlowComplete");
    this.mQueuedCheckoutOrderId = paramBundle.getString("queuedCheckoutOrderId");
    this.mReturnSecurePayloadToIntegrator = paramBundle.getBoolean("returnSecurePayloadToIntegrator");
    ArrayList localArrayList = ParcelableProto.getProtoArrayListFromBundle(paramBundle, "forwardSecureDataMappings");
    this.mForwardSecureDataMappings = ((SecureDataMappingOuterClass.SecureDataMapping[])localArrayList.toArray(new SecureDataMappingOuterClass.SecureDataMapping[localArrayList.size()]));
  }
  
  public final void onResume()
  {
    super.onResume();
    if (this.mQueuedIsFlowComplete) {
      notifyResultListener$615fbea4(this.mQueuedCheckoutOrderId, true);
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("page", ParcelableProto.forProto(this.mPage));
    paramBundle.putParcelable("purchaseManagerParameters", ParcelableProto.forProto(this.mPurchaseManagerParameters));
    paramBundle.putParcelable("androidEnvironmentConfig", ParcelableProto.forProto(this.mAndroidEnvironmentConfig));
    paramBundle.putBoolean("queuedIsFlowComplete", this.mQueuedIsFlowComplete);
    paramBundle.putString("queuedCheckoutOrderId", this.mQueuedCheckoutOrderId);
    paramBundle.putBoolean("returnSecurePayloadToIntegrator", this.mReturnSecurePayloadToIntegrator);
    paramBundle.putParcelableArrayList("forwardSecureDataMappings", ParcelableProto.forProtoArray(this.mForwardSecureDataMappings));
  }
  
  protected final void onSuccessfulResponse()
  {
    Api.SubmitResponse localSubmitResponse = ((PurchaseManagerSidecar)this.mSidecar).mSubmitResponse;
    switch (localSubmitResponse.flowInstruction)
    {
    default: 
      throw new IllegalArgumentException("Unknown flow instruction: " + localSubmitResponse.flowInstruction);
    case 1: 
      this.mPage = localSubmitResponse.nextPage;
      this.mReturnSecurePayloadToIntegrator = false;
      this.mForwardSecureDataMappings = null;
      updateNonSubformPageUi();
      updateSubFormFragment();
      return;
    case 4: 
      this.mPage = localSubmitResponse.nextPage;
      this.mReturnSecurePayloadToIntegrator = false;
      this.mForwardSecureDataMappings = null;
      updateNonSubformPageUi();
      updateSubFormFragment();
      this.mQueuedIsFlowComplete = true;
      this.mQueuedCheckoutOrderId = localSubmitResponse.checkoutOrderId;
      notifyResultListener$615fbea4(localSubmitResponse.checkoutOrderId, true);
      return;
    case 2: 
      notifyFormEvent(4, Bundle.EMPTY);
      return;
    }
    notifyResultListener$615fbea4(localSubmitResponse.checkoutOrderId, false);
  }
  
  protected final void refreshPage(Bundle paramBundle, byte[] paramArrayOfByte, FormFieldReferenceOuterClass.FormFieldReference paramFormFieldReference)
  {
    throw new UnsupportedOperationException("PurchaseManager API doesn't support refresh.");
  }
  
  protected final void retryLastRequest()
  {
    Api.SubmitRequest localSubmitRequest = (Api.SubmitRequest)this.mLastRequest;
    localSubmitRequest.pageValue = getPageValue(this.mLastFormEventDetailsForPageValue, this.mLastDependencyGraphRequestTokenForPageValue);
    submit(localSubmitRequest);
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
    Api.SubmitRequest localSubmitRequest = new Api.SubmitRequest();
    localSubmitRequest.pageValue = getPageValue(paramBundle, paramArrayOfByte);
    if (this.mReturnSecurePayloadToIntegrator)
    {
      InstrumentFormOuterClass.InstrumentFormValue localInstrumentFormValue = localSubmitRequest.pageValue.formValue;
      SecureDataMappingOuterClass.SecureDataMapping[] arrayOfSecureDataMapping = this.mForwardSecureDataMappings;
      int i = arrayOfSecureDataMapping.length;
      SecurePaymentsPayload.SecurePaymentsData[] arrayOfSecurePaymentsData = new SecurePaymentsPayload.SecurePaymentsData[i];
      int j = 0;
      if (j < i)
      {
        SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping = arrayOfSecureDataMapping[j];
        if ((localInstrumentFormValue.simpleForm != null) && (localSecureDataMapping.secureDataField.formId.equals(localInstrumentFormValue.simpleForm.id)) && (localSecureDataMapping.secureDataField.fieldId == 1))
        {
          UiFieldOuterClass.UiFieldValue localUiFieldValue = localInstrumentFormValue.simpleForm.formFieldValue[localSecureDataMapping.secureDataField.repeatedFieldIndex].fieldValue.uiFieldValue;
          String str;
          if (!TextUtils.isEmpty(localUiFieldValue.secureStringValue))
          {
            str = localUiFieldValue.secureStringValue;
            localUiFieldValue.secureStringValue = "";
          }
          for (;;)
          {
            arrayOfSecurePaymentsData[j] = new SecurePaymentsPayload.SecurePaymentsData(localSecureDataMapping.secureDataKey, str);
            j++;
            break;
            str = localUiFieldValue.stringValue;
            localUiFieldValue.stringValue = "";
          }
        }
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = localSecureDataMapping.secureDataField.formId;
        arrayOfObject[1] = Integer.valueOf(localSecureDataMapping.secureDataField.fieldId);
        arrayOfObject[2] = Integer.valueOf(localSecureDataMapping.secureDataField.repeatedFieldIndex);
        throw new IllegalArgumentException(String.format("Unsupported SecureDataMapping FormFieldReference formId=%s fieldId=%d repeatedFieldIndex=%d", arrayOfObject));
      }
      SecurePaymentsPayload localSecurePaymentsPayload = new SecurePaymentsPayload(MessageNano.toByteArray(localSubmitRequest), arrayOfSecurePaymentsData);
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("com.google.android.wallet.purchasemanager.EXTRA_SECURE_PAYMENTS_PAYLOAD", localSecurePaymentsPayload);
      this.mResultListener.onOrchestrationResult(50, localBundle);
      return;
    }
    submit(localSubmitRequest);
  }
  
  protected final void updateResponseContextAndSecureDataHeader()
  {
    Api.SubmitResponse localSubmitResponse = ((PurchaseManagerSidecar)this.mSidecar).mSubmitResponse;
    this.mResponseContext = localSubmitResponse.context;
    this.mSecureDataHeader = localSubmitResponse.secureHeader;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.purchasemanager.pub.PurchaseManagerFragment
 * JD-Core Version:    0.7.0.1
 */