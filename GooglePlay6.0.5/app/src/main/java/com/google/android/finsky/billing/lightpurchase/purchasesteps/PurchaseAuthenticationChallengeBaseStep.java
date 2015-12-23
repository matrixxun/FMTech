package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.billing.lightpurchase.AuthChallengeSidecar;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseError;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;

public abstract class PurchaseAuthenticationChallengeBaseStep
  extends StepFragment<PurchaseFragment>
  implements View.OnClickListener, SidecarFragment.Listener, PlayStoreUiElementNode
{
  public String mAccountName;
  AuthState mAuthState;
  private String mErrorMessage;
  private TextView mErrorView;
  private int mHandledStateInstance;
  ImageView mHelpToggleView;
  boolean mIsPasswordHelpExpanded;
  View mMainView;
  EditText mPasswordView;
  TextView mPurchaseDisclaimerView;
  AuthChallengeSidecar mSidecar;
  private final PlayStore.PlayStoreUiElement mUiElement;
  
  protected PurchaseAuthenticationChallengeBaseStep(int paramInt)
  {
    this.mUiElement = FinskyEventLog.obtainPlayStoreUiElement(paramInt);
  }
  
  private void showErrorMessage(String paramString)
  {
    this.mErrorMessage = paramString;
    this.mErrorView.setText(paramString);
    this.mErrorView.setVisibility(0);
    UiUtils.sendAccessibilityEventWithText(getActivity(), paramString, this.mErrorView);
  }
  
  protected abstract void changePasswordHelpAndPurchaseDisclaimer(boolean paramBoolean);
  
  protected final void createChallengeHeader(String paramString1, String paramString2, int paramInt)
  {
    this.mPurchaseDisclaimerView = ((TextView)this.mMainView.findViewById(2131755628));
    this.mPurchaseDisclaimerView.setMovementMethod(LinkMovementMethod.getInstance());
    ((TextView)this.mMainView.findViewById(2131755173)).setText(paramInt);
    ((TextView)this.mMainView.findViewById(2131755621)).setText(paramString1);
    if (!TextUtils.isEmpty(paramString2))
    {
      TextView localTextView = (TextView)this.mMainView.findViewById(2131755664);
      localTextView.setText(paramString2);
      localTextView.setVisibility(0);
    }
  }
  
  protected void createPasswordPinView(int paramInt1, int paramInt2)
  {
    this.mMainView.findViewById(2131755690).setVisibility(0);
    this.mPasswordView = ((EditText)this.mMainView.findViewById(paramInt1));
    this.mPasswordView.setVisibility(0);
    this.mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if (paramAnonymousInt == 6)
        {
          PurchaseAuthenticationChallengeBaseStep.this.logClickAndSubmitResponse(true);
          return true;
        }
        return false;
      }
    });
    this.mPasswordView.setHintTextColor(ContextCompat.getColor(((PurchaseFragment)this.mParentFragment).getContext(), 2131689798));
    this.mHelpToggleView = ((ImageView)this.mMainView.findViewById(2131755691));
    this.mHelpToggleView.setOnClickListener(this);
    this.mHelpToggleView.setContentDescription(getString(paramInt2));
    this.mErrorView = ((TextView)this.mMainView.findViewById(2131755481));
    if (!TextUtils.isEmpty(this.mErrorMessage)) {
      showErrorMessage(this.mErrorMessage);
    }
  }
  
  protected final void fail()
  {
    this.mPasswordView.setText("");
    showErrorMessage(this.mSidecar.mErrorMessage);
    ((PurchaseFragment)this.mParentFragment).hideLoading();
    UiUtils.showKeyboard(getActivity(), this.mPasswordView);
  }
  
  protected final void failWithMaxAttemptsExceeded(String paramString)
  {
    int i = this.mAuthState.getMaxAttemptExceededResourceId();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mAuthState.getRecoveryUrl(paramString);
    ErrorStep localErrorStep = ErrorStep.newInstance(new CheckoutPurchaseError(getString(i, arrayOfObject)), true);
    ((PurchaseFragment)this.mParentFragment).showStep(localErrorStep);
  }
  
  public String getContinueButtonLabel(Resources paramResources)
  {
    AuthState localAuthState = this.mAuthState;
    int i;
    if (!localAuthState.mUseFingerprintAuth) {
      i = 2131361964;
    }
    for (;;)
    {
      return paramResources.getString(i);
      if (localAuthState.mUsePinBasedAuth) {
        i = 2131362823;
      } else {
        i = 2131362822;
      }
    }
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  protected abstract void handleErrorState();
  
  protected final void logClick(int paramInt, boolean paramBoolean)
  {
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
    if (paramBoolean)
    {
      localPlayStoreUiElementInfo.isImeAction = true;
      localPlayStoreUiElementInfo.hasIsImeAction = true;
    }
    localPlayStoreUiElementInfo.authContext = this.mAuthState.getAuthContextForLogging();
    logClick(paramInt, localPlayStoreUiElementInfo);
  }
  
  protected final void logClickAndSubmitResponse(boolean paramBoolean)
  {
    logClick(751, paramBoolean);
    UiUtils.hideKeyboard(getActivity(), this.mPasswordView);
    ((PurchaseFragment)this.mParentFragment).showLoading();
    submitResponse();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mAuthState = ((AuthState)this.mArguments.getParcelable("PurchaseAuthenticationChallengeBaseStep.authState"));
    if (this.mUiElement.clientLogsCookie == null) {
      this.mUiElement.clientLogsCookie = new PlayStore.PlayStoreUiElementInfo();
    }
    this.mUiElement.clientLogsCookie.authContext = this.mAuthState.getAuthContextForLogging();
    super.onCreate(paramBundle);
    this.mAccountName = this.mArguments.getString("authAccount");
    if (paramBundle != null)
    {
      this.mHandledStateInstance = paramBundle.getInt("PurchaseAuthenticationChallengeBaseStep.handledStateInstance");
      this.mIsPasswordHelpExpanded = paramBundle.getBoolean("PurchaseAuthenticationChallengeBaseStep.passwordHelpExpanded");
      this.mErrorMessage = paramBundle.getString("PurchaseAuthenticationChallengeBaseStep.errorMessage");
    }
  }
  
  public void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), getString(this.mAuthState.getTitleResourceId()), this.mMainView);
    if ((this.mPasswordView != null) && (!((PurchaseFragment)this.mParentFragment).mIsLoading) && (this.mPasswordView.getVisibility() == 0)) {
      UiUtils.showKeyboard(getActivity(), this.mPasswordView);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("PurchaseAuthenticationChallengeBaseStep.handledStateInstance", this.mHandledStateInstance);
    paramBundle.putBoolean("PurchaseAuthenticationChallengeBaseStep.passwordHelpExpanded", this.mIsPasswordHelpExpanded);
    paramBundle.putString("PurchaseAuthenticationChallengeBaseStep.errorMessage", this.mErrorMessage);
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mSidecar = ((AuthChallengeSidecar)getActivity().getSupportFragmentManager().findFragmentByTag("PurchaseAuthenticationChallengeBaseStep.sidecar"));
    if (this.mSidecar == null)
    {
      this.mSidecar = AuthChallengeSidecar.newInstance(this.mAccountName, this.mAuthState);
      getActivity().getSupportFragmentManager().beginTransaction().add(this.mSidecar, "PurchaseAuthenticationChallengeBaseStep.sidecar").commit();
    }
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    int i = paramSidecarFragment.mStateInstance;
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(paramSidecarFragment.mState);
      arrayOfObject2[1] = Integer.valueOf(i);
      FinskyLog.v("Received state change: state=%d, stateInstance=%d", arrayOfObject2);
    }
    if (i == this.mHandledStateInstance)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(this.mHandledStateInstance);
        FinskyLog.d("Already handled state %d", arrayOfObject1);
      }
      return;
    }
    this.mHandledStateInstance = i;
    switch (this.mSidecar.mState)
    {
    default: 
      return;
    case 2: 
      succeed();
      return;
    }
    handleErrorState();
  }
  
  public final void onStop()
  {
    if (this.mSidecar != null) {
      this.mSidecar.setListener(null);
    }
    if (this.mPasswordView != null) {
      UiUtils.hideKeyboard(getActivity(), this.mPasswordView);
    }
    super.onStop();
  }
  
  protected abstract void performAdditionalSuccessActions(Bundle paramBundle);
  
  protected abstract void submitResponse();
  
  protected final void succeed()
  {
    PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
    Bundle localBundle = new Bundle();
    int i = this.mAuthState.getAuthMethod();
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = localPurchaseFragment.mSidecar;
    localCheckoutPurchaseSidecar.mAuthenticationInfo.authenticationMethod = i;
    localCheckoutPurchaseSidecar.mAuthenticationInfo.hasAuthenticationMethod = true;
    localBundle.putString("pcam", String.valueOf(i));
    performAdditionalSuccessActions(localBundle);
    localPurchaseFragment.answerChallenge(localBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.PurchaseAuthenticationChallengeBaseStep
 * JD-Core Version:    0.7.0.1
 */