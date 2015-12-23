package com.google.android.wallet.common.pub;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.CreditCardEntryAction;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;
import com.google.android.wallet.analytics.events.WalletBackgroundEvent;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.analytics.util.PageImpressionTracker;
import com.google.android.wallet.common.sidecar.SidecarFragment;
import com.google.android.wallet.common.sidecar.SidecarFragment.Listener;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.common.util.ErrorUtils;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.dependencygraph.DependencyGraphManager;
import com.google.android.wallet.dependencygraph.ResultingActionComponent;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.android.wallet.instrumentmanager.R.bool;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.common.util.ImUtils;
import com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher;
import com.google.android.wallet.instrumentmanager.ui.common.ButtonBar;
import com.google.android.wallet.instrumentmanager.ui.common.ImInfoMessageTextView;
import com.google.android.wallet.instrumentmanager.ui.common.TopBarView;
import com.google.android.wallet.instrumentmanager.ui.dcb.VerifyAssociationFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.DummyFormFragment;
import com.google.android.wallet.nfc.NfcIntentForwarder;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.FifeNetworkImageView;
import com.google.android.wallet.ui.common.FifeNetworkImageView.OnLoadedListener;
import com.google.android.wallet.ui.common.FocusedViewToTopScrollView;
import com.google.android.wallet.ui.common.FormEventListener;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.WalletDialogFragment;
import com.google.android.wallet.ui.common.WalletDialogFragment.Builder;
import com.google.android.wallet.ui.common.WalletDialogFragment.OnWalletDialogDismissedListener;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.SecureDataHeaderOuterClass.SecureDataHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.DependencyGraph;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference.SendRequestAction;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.protobuf.nano.MessageNano;

public abstract class BaseOrchestrationFragment<T extends SidecarFragment>
  extends BaseWalletUiComponentFragment
  implements View.OnClickListener, View.OnFocusChangeListener, UiNode, SidecarFragment.Listener, ResultingActionComponent, NfcIntentForwarder, ClickSpan.OnClickListener, FormEventListener, WalletDialogFragment.OnWalletDialogDismissedListener
{
  public Account mAccount;
  public ButtonBar mButtonBar;
  protected DependencyGraphManager mDependencyGraphManager;
  public TextView mErrorCode;
  public TextView mErrorText;
  private Bundle mErrorToShowOnResume;
  protected boolean mInitialFocusRequired;
  protected boolean mInitialFocusSet;
  public Bundle mInlineErrorMessageDetails;
  public byte[] mLastDependencyGraphRequestTokenForPageValue;
  public Bundle mLastFormEventDetailsForPageValue;
  public MessageNano mLastRequest;
  public FocusedViewToTopScrollView mMainContent;
  protected PageImpressionTracker mPageImpressionTracker;
  public boolean mPopupRedirectActivitySupported;
  public int mPopupRedirectActivityTheme;
  public ProgressBar mProgressBar;
  public boolean mProgressBarVisible;
  public String mProgressMessage;
  public boolean mProgressMessageVisible;
  public TextView mProgressText;
  public String mProgressTitle;
  public boolean mProgressTitleVisible;
  public TopBarView mProgressTopBarView;
  public ResponseContextOuterClass.ResponseContext mResponseContext;
  public ResultListener mResultListener;
  public int mRootLayout;
  public SecureDataHeaderOuterClass.SecureDataHeader mSecureDataHeader;
  private boolean mSendResultOnResume;
  private int mSendResultOnResumeResultCode;
  private Bundle mSendResultOnResumeResultData;
  private boolean mShowErrorMessagesInDialog;
  public boolean mSideCarInitialized;
  public T mSidecar;
  public FormFragment mSubFormFragment;
  private boolean mThemeFinishOnTouchOutside;
  public FifeNetworkImageView mTitleImageView;
  public View mTitleSeparator;
  public TopBarView mTopBarView;
  public ImInfoMessageTextView mTopInfoText;
  
  private void configureAutoScrollingForPage()
  {
    boolean bool1 = ArrayUtils.contains(this.mResponseContext.globalClientFeatures, 2);
    boolean bool2 = ArrayUtils.contains(this.mResponseContext.globalClientFeatures, 3);
    boolean bool3 = ArrayUtils.contains(this.mResponseContext.globalClientFeatures, 5);
    Window localWindow = getActivity().getWindow();
    if ((localWindow != null) && (localWindow.getAttributes() != null)) {}
    for (int i = localWindow.getAttributes().softInputMode;; i = 0)
    {
      this.mMainContent.setScrollToTop(bool1, bool3, i);
      this.mMainContent.setAnimateScroll(bool2);
      return;
    }
  }
  
  private void constructAndConfigureDependencyGraphManagerForPage()
  {
    this.mDependencyGraphManager = new DependencyGraphManager(getDependencyGraph());
    FormFragment localFormFragment = this.mSubFormFragment;
    DependencyGraphManager localDependencyGraphManager1 = this.mDependencyGraphManager;
    DependencyGraphManager localDependencyGraphManager2 = this.mDependencyGraphManager;
    localFormFragment.mDependencyGraphManager = localDependencyGraphManager1;
    localFormFragment.mTriggerListener = localDependencyGraphManager2;
    WalletUiUtils.addComponentToDependencyGraph(this, 1, this.mDependencyGraphManager, this.mDependencyGraphManager);
  }
  
  public static Bundle createArgsForOrchestrationFragment(int paramInt, Account paramAccount, Bundle paramBundle)
  {
    Bundle localBundle = createArgs(paramInt);
    localBundle.putParcelable("account", paramAccount);
    if (paramBundle != null) {}
    for (;;)
    {
      localBundle.putParcelable("additionalArgs", paramBundle);
      return localBundle;
      paramBundle = Bundle.EMPTY;
    }
  }
  
  private void sendResultToListener(int paramInt, Bundle paramBundle)
  {
    if (this.mResumed)
    {
      this.mResultListener.onOrchestrationResult(paramInt, paramBundle);
      return;
    }
    this.mSendResultOnResume = true;
    this.mSendResultOnResumeResultCode = paramInt;
    this.mSendResultOnResumeResultData = paramBundle;
  }
  
  private void updateExpandButton()
  {
    if ((this.mInlineErrorMessageDetails != null) || (this.mSubFormFragment == null) || (!this.mSubFormFragment.shouldShowButtonBarExpandButton()))
    {
      this.mButtonBar.showExpandButton(false);
      return;
    }
    this.mButtonBar.showExpandButton(true);
    this.mButtonBar.setExpandButtonText(this.mSubFormFragment.getButtonBarExpandButtonText());
  }
  
  private void updateInlineErrorMessageStateAndVisibility()
  {
    if (this.mInlineErrorMessageDetails == null)
    {
      this.mTopInfoText.setVisibility(0);
      getChildFragmentManager().beginTransaction().show(this.mSubFormFragment).commit();
      this.mErrorText.setVisibility(8);
      this.mErrorCode.setVisibility(8);
      this.mButtonBar.setPositiveButtonEnabled(this.mSubFormFragment.isReadyToSubmit());
      return;
    }
    SpannableString localSpannableString = WalletUiUtils.linkifyHtml$24df1acc(this.mInlineErrorMessageDetails.getString("ErrorUtils.KEY_ERROR_MESSAGE"));
    this.mErrorText.setText(localSpannableString);
    this.mErrorText.setMovementMethod(LinkMovementMethod.getInstance());
    String str = this.mInlineErrorMessageDetails.getString("ErrorUtils.KEY_ERROR_CODE");
    if (!TextUtils.isEmpty(str)) {
      this.mErrorCode.setText(str);
    }
    this.mTopInfoText.setVisibility(8);
    getChildFragmentManager().beginTransaction().hide(this.mSubFormFragment).commit();
    this.mErrorText.setVisibility(0);
    this.mErrorCode.setVisibility(0);
    this.mButtonBar.setPositiveButtonEnabled(true);
  }
  
  @TargetApi(11)
  private void updateProgressBarState(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool1 = true;
    FormFragment localFormFragment = this.mSubFormFragment;
    boolean bool2;
    boolean bool3;
    label45:
    boolean bool4;
    if (!paramBoolean1)
    {
      bool2 = bool1;
      localFormFragment.enableUi(bool2);
      ButtonBar localButtonBar1 = this.mButtonBar;
      if ((paramBoolean1) || (!this.mSubFormFragment.isReadyToSubmit())) {
        break label164;
      }
      bool3 = bool1;
      localButtonBar1.setPositiveButtonEnabled(bool3);
      ImInfoMessageTextView localImInfoMessageTextView = this.mTopInfoText;
      if (paramBoolean1) {
        break label170;
      }
      bool4 = bool1;
      label65:
      localImInfoMessageTextView.setEnabled(bool4);
      ButtonBar localButtonBar2 = this.mButtonBar;
      if (paramBoolean1) {
        break label176;
      }
      label82:
      localButtonBar2.setExpandButtonEnabled(bool1);
      if (paramBoolean1) {
        WalletUiUtils.hideSoftKeyboard(getActivity(), this.mMainContent);
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        if ((!paramBoolean1) || (!(this.mSubFormFragment instanceof VerifyAssociationFragment))) {
          break label181;
        }
        getActivity().setFinishOnTouchOutside(false);
      }
    }
    for (;;)
    {
      if ((!paramBoolean2) && (paramBoolean1)) {
        AnalyticsUtil.createAndSendImpressionEvent(this.mSubFormFragment, 1625);
      }
      updateProgressBarStateImpl(paramBoolean1);
      return;
      bool2 = false;
      break;
      label164:
      bool3 = false;
      break label45;
      label170:
      bool4 = false;
      break label65;
      label176:
      bool1 = false;
      break label82;
      label181:
      getActivity().setFinishOnTouchOutside(this.mThemeFinishOnTouchOutside);
    }
  }
  
  public final void applyFormFieldMessages(UiErrorOuterClass.FormFieldMessage[] paramArrayOfFormFieldMessage)
  {
    int i = paramArrayOfFormFieldMessage.length;
    for (int j = 0; j < i; j++)
    {
      UiErrorOuterClass.FormFieldMessage localFormFieldMessage = paramArrayOfFormFieldMessage[j];
      if (!this.mSubFormFragment.applyFormFieldMessage(localFormFieldMessage)) {
        throw new IllegalArgumentException("FormFieldMessage form not found: " + localFormFieldMessage.formFieldReference.formId);
      }
    }
    AnalyticsUtil.createAndSendImpressionEvent(this.mSubFormFragment, 1623);
    this.mSubFormFragment.focusOnFirstErroneousFormField();
  }
  
  public final void applyResultingAction(DependencyGraphOuterClass.ResultingActionReference paramResultingActionReference, DependencyGraphOuterClass.TriggerValueReference[] paramArrayOfTriggerValueReference)
  {
    switch (paramResultingActionReference.actionType)
    {
    default: 
      throw new IllegalArgumentException("Unsupported resulting action type: " + paramResultingActionReference.actionType);
    }
    byte[] arrayOfByte = paramResultingActionReference.sendRequestAction.requestToken;
    Bundle localBundle = new Bundle();
    localBundle.putParcelableArrayList("FormEventListener.EXTRA_TRIGGER_VALUE_REFERENCES", ParcelableProto.forProtoArray(paramArrayOfTriggerValueReference));
    if (paramResultingActionReference.actionType == 3)
    {
      if (paramResultingActionReference.sendRequestAction.validateAllElements) {}
      for (int[] arrayOfInt = null;; arrayOfInt = paramResultingActionReference.sendRequestAction.componentIdToValidate)
      {
        submitPage(arrayOfInt, localBundle, arrayOfByte);
        return;
      }
    }
    refreshPage(localBundle, arrayOfByte, null);
  }
  
  public abstract void autoSubmitIfReadyAndAllowed();
  
  public abstract FormFragment createAndDisplaySubFormFragment();
  
  public abstract T createSidecar();
  
  public final void forwardNfcIntent(Intent paramIntent)
  {
    if ((this.mSubFormFragment instanceof NfcIntentForwarder)) {
      ((NfcIntentForwarder)this.mSubFormFragment).forwardNfcIntent(paramIntent);
    }
  }
  
  public abstract DependencyGraphOuterClass.DependencyGraph getDependencyGraph();
  
  public UiNode getParentUiNode()
  {
    return null;
  }
  
  public abstract String getProgressDialogMessage();
  
  public abstract String getProgressDialogTitle();
  
  public abstract ButtonContainerOuterClass.Button getSubmitButton();
  
  public abstract String getTitle();
  
  public abstract ImageWithCaptionOuterClass.ImageWithCaption getTitleImage();
  
  public abstract InfoMessageOuterClass.InfoMessage getTopInfoMessage();
  
  public abstract boolean handleErrorResponseSubstate();
  
  public abstract boolean isAutoSubmitPending();
  
  public void notifyFormEvent(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    case 2: 
    case 3: 
    case 9: 
    default: 
      throw new IllegalArgumentException("Unknown formEvent: " + paramInt);
    case 1: 
      boolean bool = this.mSubFormFragment.isReadyToSubmit();
      this.mButtonBar.setPositiveButtonEnabled(bool);
      autoSubmitIfReadyAndAllowed();
    case 8: 
    case 6: 
    case 4: 
    case 5: 
      do
      {
        do
        {
          do
          {
            do
            {
              return;
            } while (!this.mSubFormFragment.isReadyToSubmit());
            submitPage(null, Bundle.EMPTY, null);
            return;
            updateExpandButton();
            return;
            this.mPageImpressionTracker.trackImpressionIfNecessary(this);
          } while ((this.mSidecar.mState == 1) || (isAutoSubmitPending()));
          updateProgressBarState(false, false);
        } while ((!this.mInitialFocusRequired) || (this.mInitialFocusSet));
        this.mInitialFocusSet = WalletUiUtils.showSoftKeyboardOnFirstEditText(this.mMainContent);
        return;
      } while (this.mSidecar.mState == 1);
      showErrorMessage(paramBundle);
      return;
    case 7: 
      int j = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE");
      switch (j)
      {
      case 771: 
      case 774: 
      case 775: 
      default: 
        throw new IllegalArgumentException("Unknown analytics background event type: " + j);
      case 770: 
        CreditCardEntryAction localCreditCardEntryAction = (CreditCardEntryAction)paramBundle.getParcelable("FormEventListener.EXTRA_BACKGROUND_EVENT_DATA");
        if (localCreditCardEntryAction == null) {
          throw new IllegalArgumentException("CreditCardEntryAction background events must include a CreditCardEntryAction");
        }
        InstrumentManagerAnalyticsEventDispatcher.sendBackgroundEvent(new WalletBackgroundEvent(localCreditCardEntryAction, this.mResponseContext.logToken));
        return;
      case 773: 
        int n = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", -1);
        if (n == -1) {
          throw new IllegalArgumentException("OTP autofill background event must include a resultCode");
        }
        AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(j, n, this.mResponseContext.logToken);
        return;
      case 772: 
        WebViewPageLoadEvent localWebViewPageLoadEvent = (WebViewPageLoadEvent)paramBundle.getParcelable("FormEventListener.EXTRA_BACKGROUND_EVENT_DATA");
        if (localWebViewPageLoadEvent == null) {
          throw new IllegalArgumentException("WebViewPageLoad background events must include a WebViewPageLoadEvent.");
        }
        int m = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", -1);
        if (m == -1) {
          throw new IllegalArgumentException("WebViewPageLoad background events must include a resultCode");
        }
        AnalyticsUtil.createAndSendWebViewPageLoadBackgroundEvent(m, localWebViewPageLoadEvent, this.mResponseContext.logToken);
        return;
      }
      int k = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", -1);
      if (k == -1) {
        throw new IllegalArgumentException("ProviderInstall background events must include a resultCode");
      }
      AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(776, k, this.mResponseContext.logToken);
      return;
    }
    int i = paramBundle.getInt("FormEventListener.EXTRA_RESULT_CODE", -1);
    if (i == -1) {
      throw new IllegalArgumentException("NotifyResultListener event must contain a result code.");
    }
    Bundle localBundle = paramBundle.getBundle("FormEventListener.EXTRA_RESULT_DATA");
    if (localBundle == null) {
      localBundle = Bundle.EMPTY;
    }
    sendResultToListener(i, localBundle);
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mSidecar = ((SidecarFragment)getActivity().getSupportFragmentManager().findFragmentByTag("BaseOrchestrationFragmesidecar"));
    if ((this.mSidecar == null) || (paramBundle == null))
    {
      FragmentTransaction localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
      if (this.mSidecar != null) {
        localFragmentTransaction.remove(this.mSidecar);
      }
      this.mSidecar = createSidecar();
      localFragmentTransaction.add(this.mSidecar, "BaseOrchestrationFragmesidecar").commit();
    }
    if (paramBundle != null) {
      if (this.mSidecar.mState != 1) {
        break label112;
      }
    }
    label112:
    for (boolean bool = true;; bool = false)
    {
      updateProgressBarState(bool, true);
      return;
    }
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 501) {
      switch (paramInt2)
      {
      default: 
        throw new RuntimeException("Unknown result code from popup redirect: " + paramInt2);
      case 2: 
        sendResultToListener(paramIntent.getIntExtra("resultCode", 51), paramIntent.getBundleExtra("resultData"));
      }
    }
    Fragment localFragment;
    do
    {
      return;
      if ((this.mSubFormFragment instanceof DummyFormFragment))
      {
        ((DummyFormFragment)this.mSubFormFragment).mRedirectFormValue = ((RedirectFormOuterClass.RedirectFormValue)ParcelableProto.getProtoFromIntent(paramIntent, "formValue"));
        notifyFormEvent(8, Bundle.EMPTY);
        return;
      }
      throw new IllegalStateException("Unexpected subform type: " + this.mSubFormFragment.getClass().getSimpleName());
      sendResultToListener(51, Bundle.EMPTY);
      return;
      Bundle localBundle = paramIntent.getBundleExtra("errorDetails");
      if (localBundle == null) {
        throw new IllegalArgumentException("Error result must provide error details.");
      }
      if (this.mResumed)
      {
        updateProgressBarState(false, false);
        notifyFormEvent(5, localBundle);
        return;
      }
      this.mErrorToShowOnResume = localBundle;
      return;
      localFragment = getChildFragmentManager().findFragmentById(R.id.sub_form_holder);
    } while (localFragment == null);
    localFragment.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.positive_btn) {
      if (this.mInlineErrorMessageDetails == null)
      {
        this.mProgressTitle = getProgressDialogTitle();
        this.mProgressMessage = getProgressDialogMessage();
        AnalyticsUtil.createAndSendClickEvent(this.mSubFormFragment, -1, 1621);
        submitPage(null, Bundle.EMPTY, null);
      }
    }
    do
    {
      String str;
      int i;
      do
      {
        return;
        str = this.mInlineErrorMessageDetails.getString("FormEventListener.EXTRA_FORM_ID");
        i = this.mInlineErrorMessageDetails.getInt("ErrorUtils.KEY_TYPE");
        this.mInlineErrorMessageDetails = null;
        updateInlineErrorMessageStateAndVisibility();
        updateNonSubformPageUi();
        if (str == null) {
          break;
        }
      } while (this.mSubFormFragment.handleErrorMessageDismissed(str, i));
      throw new IllegalArgumentException("Form to handle error message not found: " + str);
      onWalletDialogDismissed$71cf5c62(-1, i);
      return;
      if (paramView.getId() == R.id.expand_btn)
      {
        this.mButtonBar.showExpandButton(false);
        this.mSubFormFragment.onButtonBarExpandButtonClicked();
        return;
      }
    } while (paramView.getId() != R.id.negative_btn);
    AnalyticsUtil.createAndSendClickEvent(this.mSubFormFragment, 1622);
    this.mResultListener.onOrchestrationResult(51, Bundle.EMPTY);
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if (this.mFragmentManager.findFragmentByTag("BaseOrchestrationFragmewebViewDialog") != null) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "BaseOrchestrationFragmewebViewDialog");
  }
  
  public void onCreate(Bundle paramBundle)
  {
    Fragment localFragment = this.mParentFragment;
    if ((localFragment instanceof ResultListener))
    {
      this.mResultListener = ((ResultListener)localFragment);
      GservicesValue.init(getActivity().getApplicationContext());
      super.onCreate(paramBundle);
      ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
      int[] arrayOfInt = new int[5];
      arrayOfInt[0] = 16843611;
      arrayOfInt[1] = R.attr.imShowErrorMessagesInDialog;
      arrayOfInt[2] = R.attr.imRootLayout;
      arrayOfInt[3] = R.attr.imPopupRedirectActivitySupported;
      arrayOfInt[4] = R.attr.imPopupRedirectActivityTheme;
      TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
      this.mThemeFinishOnTouchOutside = localTypedArray.getBoolean(0, false);
      this.mShowErrorMessagesInDialog = localTypedArray.getBoolean(1, false);
      this.mRootLayout = localTypedArray.getResourceId(2, 0);
      this.mPopupRedirectActivitySupported = localTypedArray.getBoolean(3, false);
      this.mPopupRedirectActivityTheme = localTypedArray.getResourceId(4, 0);
      localTypedArray.recycle();
      this.mAccount = ((Account)this.mArguments.getParcelable("account"));
      if (paramBundle == null) {
        break label314;
      }
      this.mResponseContext = ((ResponseContextOuterClass.ResponseContext)ParcelableProto.getProtoFromBundle(paramBundle, "responseContext"));
      this.mSecureDataHeader = ((SecureDataHeaderOuterClass.SecureDataHeader)ParcelableProto.getProtoFromBundle(paramBundle, "secureHeader"));
      this.mLastFormEventDetailsForPageValue = paramBundle.getBundle("lastFormEventDetailsForPageValue");
      this.mLastDependencyGraphRequestTokenForPageValue = paramBundle.getByteArray("lastDependencyGraphRequestTokenForPageValue");
      this.mPageImpressionTracker = new PageImpressionTracker(paramBundle.getBoolean("impressionForPageTracked"));
      this.mProgressTitle = paramBundle.getString("progressTitle");
      this.mProgressMessage = paramBundle.getString("progressMessage");
      this.mInlineErrorMessageDetails = paramBundle.getBundle("inlineErrorMessageDetails");
      this.mLastRequest = ParcelableProto.getProtoFromBundle(paramBundle, "lastRequest");
      this.mSideCarInitialized = paramBundle.getBoolean("sidecarInitialized");
    }
    for (;;)
    {
      ImUtils.setScreenshotsEnabled(getActivity(), false);
      return;
      this.mResultListener = ((ResultListener)getActivity());
      break;
      label314:
      this.mPageImpressionTracker = new PageImpressionTracker(false);
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(this.mRootLayout, paramViewGroup, false);
    this.mMainContent = ((FocusedViewToTopScrollView)localView.findViewById(R.id.main_content));
    this.mTitleImageView = ((FifeNetworkImageView)localView.findViewById(R.id.title_image));
    this.mTopBarView = ((TopBarView)localView.findViewById(R.id.top_bar));
    this.mTitleSeparator = localView.findViewById(R.id.title_separator);
    this.mButtonBar = ((ButtonBar)localView.findViewById(R.id.button_bar));
    this.mButtonBar.setPositiveButtonOnClickListener(this);
    this.mButtonBar.setNegativeButtonOnClickListener(this);
    this.mButtonBar.setExpandButtonOnClickListener(this);
    String str = this.mArguments.getBundle("additionalArgs").getString("com.google.android.wallet.orchestration.TITLE_IMAGE_FIFE_URL");
    if (!TextUtils.isEmpty(str))
    {
      this.mTitleImageView.setOnLoadedListener(new FifeNetworkImageView.OnLoadedListener()
      {
        public final void onLoaded$70e9aa75(Bitmap paramAnonymousBitmap)
        {
          int i;
          int j;
          int k;
          int m;
          float f;
          int n;
          if (paramAnonymousBitmap != null)
          {
            BaseOrchestrationFragment.this.mTitleImageView.setScaleType(ImageView.ScaleType.MATRIX);
            i = BaseOrchestrationFragment.this.mTitleImageView.getWidth();
            j = BaseOrchestrationFragment.this.mTitleImageView.getHeight();
            k = paramAnonymousBitmap.getWidth();
            m = paramAnonymousBitmap.getHeight();
            f = k / m;
            if (!BaseOrchestrationFragment.this.getResources().getBoolean(R.bool.wallet_uic_is_tablet)) {
              break label156;
            }
            n = (int)(f * j);
          }
          for (int i1 = j;; i1 = Math.max(j, (int)(i / f)))
          {
            RectF localRectF1 = new RectF(0.0F, 0.0F, k, m);
            RectF localRectF2 = new RectF(0.0F, 0.0F, n, i1);
            Matrix localMatrix = new Matrix();
            localMatrix.setRectToRect(localRectF1, localRectF2, Matrix.ScaleToFit.FILL);
            BaseOrchestrationFragment.this.mTitleImageView.setImageMatrix(localMatrix);
            return;
            label156:
            n = Math.max(i, (int)(f * j));
          }
        }
      });
      this.mTitleImageView.setVisibility(0);
      this.mTitleImageView.setFadeIn(false);
      this.mTitleImageView.setFifeImageUrl(str, PaymentUtils.getImageLoader(getActivity().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
    }
    this.mTopInfoText = ((ImInfoMessageTextView)localView.findViewById(R.id.top_info_text));
    this.mTopInfoText.setParentUiNode(this);
    this.mTopInfoText.setUrlClickListener(this);
    this.mProgressTopBarView = ((TopBarView)localView.findViewById(R.id.progress_top_bar));
    this.mProgressBar = ((ProgressBar)localView.findViewById(R.id.progress_bar));
    this.mProgressText = ((TextView)localView.findViewById(R.id.progress_text));
    this.mErrorText = ((TextView)localView.findViewById(R.id.message));
    this.mErrorCode = ((TextView)localView.findViewById(R.id.details));
    updateNonSubformPageUi();
    this.mSubFormFragment = ((FormFragment)getChildFragmentManager().findFragmentById(R.id.sub_form_holder));
    if (this.mSubFormFragment == null) {
      updateSubFormFragment();
    }
    for (;;)
    {
      if ((paramBundle != null) && (this.mInlineErrorMessageDetails != null)) {
        updateInlineErrorMessageStateAndVisibility();
      }
      localView.findViewById(R.id.steal_focus_and_hide_keyboard).setOnFocusChangeListener(this);
      return localView;
      configureAutoScrollingForPage();
      constructAndConfigureDependencyGraphManagerForPage();
    }
  }
  
  public final void onDetach()
  {
    if (this.mRemoving)
    {
      getActivity().getSupportFragmentManager().beginTransaction().remove(this.mSidecar).commit();
      this.mSidecar = null;
      ImUtils.setScreenshotsEnabled(getActivity(), true);
    }
    super.onDetach();
    this.mResultListener = null;
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if ((paramView.getId() == R.id.steal_focus_and_hide_keyboard) && (paramBoolean)) {
      WalletUiUtils.hideSoftKeyboard(getActivity(), paramView);
    }
  }
  
  @TargetApi(11)
  public final void onPause()
  {
    super.onPause();
    if (Build.VERSION.SDK_INT >= 11) {
      getActivity().setFinishOnTouchOutside(this.mThemeFinishOnTouchOutside);
    }
    this.mSidecar.setListener(null);
  }
  
  public void onResume()
  {
    super.onResume();
    this.mSidecar.setListener(this);
    if (this.mSendResultOnResume)
    {
      this.mResultListener.onOrchestrationResult(this.mSendResultOnResumeResultCode, this.mSendResultOnResumeResultData);
      this.mSendResultOnResume = false;
    }
    if (this.mErrorToShowOnResume != null)
    {
      updateProgressBarState(false, false);
      notifyFormEvent(5, this.mErrorToShowOnResume);
      this.mErrorToShowOnResume = null;
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("responseContext", ParcelableProto.forProto(this.mResponseContext));
    paramBundle.putParcelable("secureHeader", ParcelableProto.forProto(this.mSecureDataHeader));
    paramBundle.putBoolean("impressionForPageTracked", this.mPageImpressionTracker.mImpressionForPageTracked);
    paramBundle.putString("progressTitle", this.mProgressTitle);
    paramBundle.putString("progressMessage", this.mProgressMessage);
    paramBundle.putBundle("inlineErrorMessageDetails", this.mInlineErrorMessageDetails);
    if (this.mLastRequest != null) {
      paramBundle.putParcelable("lastRequest", ParcelableProto.forProto(this.mLastRequest));
    }
    if (this.mLastFormEventDetailsForPageValue != null) {
      paramBundle.putBundle("lastFormEventDetailsForPageValue", this.mLastFormEventDetailsForPageValue);
    }
    if (this.mLastDependencyGraphRequestTokenForPageValue != null) {
      paramBundle.putByteArray("lastDependencyGraphRequestTokenForPageValue", this.mLastDependencyGraphRequestTokenForPageValue);
    }
    paramBundle.putBoolean("sidecarInitialized", this.mSideCarInitialized);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    if (paramSidecarFragment != this.mSidecar) {
      throw new IllegalArgumentException("Unexpected sidecar");
    }
    switch (this.mSidecar.mState)
    {
    }
    do
    {
      return;
      this.mSideCarInitialized = true;
      autoSubmitIfReadyAndAllowed();
      return;
      updateProgressBarState(true, false);
      return;
      updateResponseContextAndSecureDataHeader();
      onSuccessfulResponse();
      return;
      updateProgressBarState(false, false);
      updateResponseContextAndSecureDataHeader();
    } while (handleErrorResponseSubstate());
    switch (this.mSidecar.mSubstate)
    {
    default: 
      throw new IllegalArgumentException("Unknown sidecar substate: " + this.mSidecar.mSubstate);
    case 1: 
    case 3: 
      showErrorMessage(ErrorUtils.addErrorDetailsToBundle(new Bundle(), 2, getString(R.string.wallet_im_error_title), getString(R.string.wallet_im_unknown_error), null, getString(17039370)));
      return;
    }
    showErrorMessage(ErrorUtils.addErrorDetailsToBundle(new Bundle(), 500, getString(R.string.wallet_uic_network_error_title), getString(R.string.wallet_uic_network_error_message), null, getString(R.string.wallet_uic_retry)));
  }
  
  public abstract void onSuccessfulResponse();
  
  public final void onWalletDialogDismissed$71cf5c62(int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException("Unknown errorType: " + paramInt2);
    case 2: 
      this.mResultListener.onOrchestrationResult(52, Bundle.EMPTY);
    case 1: 
      do
      {
        return;
        if (paramInt1 == -1)
        {
          autoSubmitIfReadyAndAllowed();
          return;
        }
      } while (!isAutoSubmitPending());
      this.mResultListener.onOrchestrationResult(51, Bundle.EMPTY);
      return;
    case 500: 
      if (paramInt1 == -2)
      {
        this.mResultListener.onOrchestrationResult(51, Bundle.EMPTY);
        return;
      }
      retryLastRequest();
      return;
    }
    if (paramInt1 == -1)
    {
      updateSubFormFragment();
      return;
    }
    this.mResultListener.onOrchestrationResult(51, Bundle.EMPTY);
  }
  
  public abstract void refreshPage(Bundle paramBundle, byte[] paramArrayOfByte, FormFieldReferenceOuterClass.FormFieldReference paramFormFieldReference);
  
  public abstract void retryLastRequest();
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    throw new UnsupportedOperationException("Top level UiNode doesn't support custom parents.");
  }
  
  public final void showErrorMessage(Bundle paramBundle)
  {
    if (this.mShowErrorMessagesInDialog)
    {
      DialogFragment localDialogFragment = (DialogFragment)this.mFragmentManager.findFragmentByTag("BaseOrchestrationFragmeserverErrorDialog");
      if (localDialogFragment != null) {
        localDialogFragment.dismissInternal(false);
      }
      WalletDialogFragment.Builder localBuilder = new WalletDialogFragment.Builder();
      localBuilder.mRequestCode = paramBundle.getInt("ErrorUtils.KEY_TYPE");
      localBuilder.mTitle = paramBundle.getString("ErrorUtils.KEY_TITLE");
      localBuilder.mMessage = paramBundle.getString("ErrorUtils.KEY_ERROR_MESSAGE");
      localBuilder.mDetails = paramBundle.getString("ErrorUtils.KEY_ERROR_CODE");
      localBuilder.mPositiveButtonText = paramBundle.getString("ErrorUtils.KEY_ERROR_BUTTON_TEXT");
      localBuilder.mThemeResourceId = this.mThemeResourceId;
      WalletDialogFragment localWalletDialogFragment = localBuilder.build();
      localWalletDialogFragment.setTargetFragment(this, 0);
      localWalletDialogFragment.show(this.mFragmentManager, "BaseOrchestrationFragmeserverErrorDialog");
    }
    for (;;)
    {
      AnalyticsUtil.createAndSendImpressionEvent(this.mSubFormFragment, 1626);
      return;
      this.mInlineErrorMessageDetails = paramBundle;
      updateNonSubformPageUi();
      updateInlineErrorMessageStateAndVisibility();
      WalletUiUtils.hideSoftKeyboard(getActivity(), this.mMainContent);
      WalletUiUtils.announceForAccessibility(this.mErrorText, this.mErrorText.getText());
    }
  }
  
  public abstract void submitPage(int[] paramArrayOfInt, Bundle paramBundle, byte[] paramArrayOfByte);
  
  public final void updateNonSubformPageUi()
  {
    String str1;
    String str3;
    String str4;
    int i;
    String str2;
    Button localButton;
    int j;
    if (this.mInlineErrorMessageDetails == null)
    {
      str1 = getTitle();
      ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = getTitleImage();
      str3 = null;
      str4 = null;
      if (localImageWithCaption != null)
      {
        str4 = localImageWithCaption.imageUri;
        str3 = localImageWithCaption.altText;
      }
      ButtonContainerOuterClass.Button localButton1 = getSubmitButton();
      if (localButton1 != null) {}
      for (i = 1;; i = 0)
      {
        str2 = null;
        if (i == 0) {
          break;
        }
        str2 = localButton1.buttonTextBeforeClick;
        if (!TextUtils.isEmpty(str2)) {
          break;
        }
        throw new IllegalStateException("SubmitButton is missing text.");
      }
      this.mTopInfoText.setInfoMessage(getTopInfoMessage());
      ButtonBar localButtonBar = this.mButtonBar;
      localButtonBar.showNegativeButton(localButtonBar.mDefaultShowNegativeButton);
      this.mTopBarView.setTitle(str1, str4, str3);
      getActivity().setTitle(str1);
      localButton = this.mButtonBar.mPositiveButton;
      j = 0;
      if (i == 0) {
        break label237;
      }
    }
    for (;;)
    {
      localButton.setVisibility(j);
      if (i != 0) {
        this.mButtonBar.setPositiveButtonText(str2);
      }
      updateExpandButton();
      return;
      str1 = this.mInlineErrorMessageDetails.getString("ErrorUtils.KEY_TITLE");
      i = 1;
      str2 = this.mInlineErrorMessageDetails.getString("ErrorUtils.KEY_ERROR_BUTTON_TEXT");
      if (TextUtils.isEmpty(str2)) {
        throw new IllegalStateException("InlineErrorMessageDetails is missing button text.");
      }
      this.mButtonBar.showNegativeButton(false);
      str3 = null;
      str4 = null;
      break;
      label237:
      j = 8;
    }
  }
  
  public void updateProgressBarStateImpl(boolean paramBoolean)
  {
    int i;
    if ((this.mMainContent.getVisibility() != 0) && (this.mProgressBar.getVisibility() != 0))
    {
      i = 1;
      if (!paramBoolean) {
        break label274;
      }
      if (!TextUtils.isEmpty(this.mProgressMessage)) {
        break label184;
      }
      RelativeLayout.LayoutParams localLayoutParams2 = (RelativeLayout.LayoutParams)this.mProgressBar.getLayoutParams();
      WalletUiUtils.removeRule(localLayoutParams2, WalletUiUtils.sanitizeRelativeLayoutVerb(20), ViewCompat.getLayoutDirection(this.mProgressBar));
      localLayoutParams2.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(14));
      label75:
      if (i == 0) {
        break label223;
      }
      this.mProgressBar.setVisibility(0);
      label87:
      this.mProgressBarVisible = true;
      if ((!paramBoolean) || (TextUtils.isEmpty(this.mProgressTitle))) {
        break label392;
      }
      this.mProgressTopBarView.setTitle(this.mProgressTitle, null, null);
      if (i == 0) {
        break label373;
      }
      this.mProgressTopBarView.setVisibility(0);
      label131:
      this.mProgressTitleVisible = true;
      label136:
      if ((!paramBoolean) || (TextUtils.isEmpty(this.mProgressMessage))) {
        break label443;
      }
      this.mProgressText.setText(this.mProgressMessage);
      if (i == 0) {
        break label424;
      }
      this.mProgressText.setVisibility(0);
    }
    for (;;)
    {
      this.mProgressMessageVisible = true;
      return;
      i = 0;
      break;
      label184:
      RelativeLayout.LayoutParams localLayoutParams1 = (RelativeLayout.LayoutParams)this.mProgressBar.getLayoutParams();
      WalletUiUtils.removeRule(localLayoutParams1, WalletUiUtils.sanitizeRelativeLayoutVerb(14), ViewCompat.getLayoutDirection(this.mProgressBar));
      localLayoutParams1.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(20));
      break label75;
      label223:
      if (this.mProgressBarVisible) {
        break label87;
      }
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mTopBarView, 0);
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mTitleSeparator, 0);
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mMainContent, 0);
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mButtonBar, 0);
      WalletUiUtils.animateViewAppearing(this.mProgressBar, 0, 0);
      break label87;
      label274:
      if (i != 0)
      {
        this.mTopBarView.setVisibility(0);
        this.mTitleSeparator.setVisibility(0);
        this.mMainContent.setVisibility(0);
        this.mButtonBar.setVisibility(0);
      }
      for (;;)
      {
        this.mProgressBarVisible = false;
        break;
        if (this.mProgressBarVisible)
        {
          WalletUiUtils.animateViewAppearing(this.mTopBarView, 0, 0);
          WalletUiUtils.animateViewAppearing(this.mTitleSeparator, 0, 0);
          WalletUiUtils.animateViewAppearing(this.mMainContent, 0, 0);
          WalletUiUtils.animateViewAppearing(this.mButtonBar, 0, 0);
          WalletUiUtils.animateViewDisappearingToGone(this.mProgressBar, 0, 0);
        }
      }
      label373:
      if (this.mProgressTitleVisible) {
        break label131;
      }
      WalletUiUtils.animateViewAppearing(this.mProgressTopBarView, 0, 0);
      break label131;
      label392:
      this.mProgressTitle = null;
      if ((i == 0) && (this.mProgressTitleVisible)) {
        WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mProgressTopBarView, 0);
      }
      this.mProgressTitleVisible = false;
      break label136;
      label424:
      if (!this.mProgressMessageVisible) {
        WalletUiUtils.animateViewAppearing(this.mProgressText, 0, 0);
      }
    }
    label443:
    this.mProgressMessage = null;
    if ((i == 0) && (this.mProgressMessageVisible)) {
      WalletUiUtils.animateViewDisappearingToGone(this.mProgressText, 0, 0);
    }
    this.mProgressMessageVisible = false;
  }
  
  public abstract void updateResponseContextAndSecureDataHeader();
  
  public final void updateSubFormFragment()
  {
    this.mTopInfoText.expand(false);
    this.mInitialFocusRequired = true;
    this.mInitialFocusSet = false;
    this.mPageImpressionTracker.mImpressionForPageTracked = false;
    configureAutoScrollingForPage();
    this.mSubFormFragment = createAndDisplaySubFormFragment();
    this.mTopBarView.post(new Runnable()
    {
      public final void run()
      {
        WalletUiUtils.announceForAccessibility(BaseOrchestrationFragment.this.mTopBarView, BaseOrchestrationFragment.this.mTopBarView.getTitle());
      }
    });
    constructAndConfigureDependencyGraphManagerForPage();
  }
  
  public static abstract interface ResultListener
  {
    public abstract void onOrchestrationResult(int paramInt, Bundle paramBundle);
    
    public abstract void onQueuedOrchestrationResult(int paramInt, Bundle paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.pub.BaseOrchestrationFragment
 * JD-Core Version:    0.7.0.1
 */