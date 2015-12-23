package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.accounts.Account;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.auth.AuthApi;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.billing.lightpurchase.AuthChallengeSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.config.PurchaseAuthUtils;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.AuthChallengeDialogDocumentInfoLayout;
import com.google.android.finsky.protos.ChallengeProto.AuthenticationChallenge;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.UrlSpanUtils;
import com.google.android.play.utils.UrlSpanUtils.Listener;
import com.google.android.play.utils.config.GservicesValue;

public final class AuthChallengeStep
  extends PurchaseAuthenticationChallengeBaseStep
  implements CompoundButton.OnCheckedChangeListener
{
  private Button mAccessibilitySettingsButton;
  private ChallengeProto.AuthenticationChallenge mChallenge;
  private FinskyEventLog mEventLog;
  private int mFailedCount;
  private FingerprintUiHelper mFingerprintHelper;
  private CheckBox mFingerprintOptInCheckbox;
  private boolean mIsFingerprintOptInChecked;
  private boolean mIsOptOutChecked;
  private CheckBox mOptOutCheckbox;
  private TextView mOptOutInfo;
  private TextView mPasswordRecoveryView;
  public int mPurchaseAuthBeforeManageSettings = -1;
  
  public AuthChallengeStep()
  {
    super(750);
  }
  
  private void fallbackAuthMethod()
  {
    this.mAuthState.mUseFingerprintAuth = false;
    ((PurchaseFragment)this.mParentFragment).showAuthChallengeStep(this.mChallenge, this.mAuthState);
  }
  
  private CharSequence getPurchaseChallengeText(int paramInt)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = "SETTINGS_ACTIVITY_SENTINEL";
    arrayOfObject[1] = G.gaiaOptOutLearnMoreLink.get();
    Spanned localSpanned = Html.fromHtml(getString(paramInt, arrayOfObject));
    UrlSpanUtils.selfishifyUrlSpans(localSpanned, "SETTINGS_ACTIVITY_SENTINEL", new UrlSpanUtils.Listener()
    {
      public final void onClick(View paramAnonymousView, String paramAnonymousString)
      {
        AuthChallengeStep.this.logClick(754, false);
        AuthChallengeStep.access$102(AuthChallengeStep.this, PurchaseAuthUtils.getPurchaseAuthType(AuthChallengeStep.this.mAccountName));
        ((PurchaseFragment)AuthChallengeStep.access$200(AuthChallengeStep.this)).launchSettingsForAuthChallenge();
      }
    });
    return localSpanned;
  }
  
  public static AuthChallengeStep newInstance(String paramString, ChallengeProto.AuthenticationChallenge paramAuthenticationChallenge, AuthState paramAuthState)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("AuthChallengeStep.challenge", ParcelableProto.forProto(paramAuthenticationChallenge));
    localBundle.putParcelable("PurchaseAuthenticationChallengeBaseStep.authState", paramAuthState);
    AuthChallengeStep localAuthChallengeStep = new AuthChallengeStep();
    localAuthChallengeStep.mAuthState = paramAuthState;
    localAuthChallengeStep.setArguments(localBundle);
    return localAuthChallengeStep;
  }
  
  private void updateAccessibilitySettingsButton()
  {
    int i;
    Button localButton;
    int j;
    if (this.mAccessibilitySettingsButton != null)
    {
      if ((!this.mIsOptOutChecked) && (!this.mIsPasswordHelpExpanded)) {
        break label40;
      }
      i = 1;
      localButton = this.mAccessibilitySettingsButton;
      j = 0;
      if (i == 0) {
        break label45;
      }
    }
    for (;;)
    {
      localButton.setVisibility(j);
      return;
      label40:
      i = 0;
      break;
      label45:
      j = 8;
    }
  }
  
  protected final void changePasswordHelpAndPurchaseDisclaimer(boolean paramBoolean)
  {
    this.mIsPasswordHelpExpanded = paramBoolean;
    updatePasswordHelpAndPurchaseDisclaimer();
  }
  
  protected final void createPasswordPinView(int paramInt1, int paramInt2)
  {
    super.createPasswordPinView(paramInt1, paramInt2);
    int j;
    int k;
    if (AuthApi.isFingerprintAvailable(this.mAccountName))
    {
      this.mFingerprintOptInCheckbox = ((CheckBox)this.mMainView.findViewById(2131755687));
      this.mIsFingerprintOptInChecked = AuthApi.isFingerprintEnabled(this.mAccountName);
      String str = this.mAccountName;
      if ((((Boolean)FinskyPreferences.useFingerprintForPurchase.get(str).get()).booleanValue()) && (!AuthApi.isFingerprintKeyValid()))
      {
        j = 1;
        if (j != 0)
        {
          TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755681);
          if (this.mAuthState.getAuthMethod() != 2) {
            break label311;
          }
          k = 2131362153;
          label112:
          localTextView2.setText(k);
          localTextView2.setVisibility(0);
          this.mIsFingerprintOptInChecked = true;
        }
        if (!this.mIsFingerprintOptInChecked)
        {
          this.mFingerprintOptInCheckbox.setVisibility(0);
          this.mFingerprintOptInCheckbox.setOnCheckedChangeListener(this);
        }
      }
    }
    for (;;)
    {
      this.mPasswordRecoveryView = ((TextView)this.mMainView.findViewById(2131755685));
      this.mPasswordRecoveryView.setMovementMethod(LinkMovementMethod.getInstance());
      TextView localTextView1 = this.mPasswordRecoveryView;
      int i = this.mAuthState.getRecoveryMessageResourceId();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mAuthState.getRecoveryUrl(this.mAccountName);
      localTextView1.setText(Html.fromHtml(getString(i, arrayOfObject)));
      this.mOptOutInfo = ((TextView)this.mMainView.findViewById(2131755575));
      this.mOptOutInfo.setText(getPurchaseChallengeText(2131362606));
      this.mOptOutInfo.setMovementMethod(LinkMovementMethod.getInstance());
      this.mOptOutCheckbox = ((CheckBox)this.mMainView.findViewById(2131755688));
      if (this.mChallenge.gaiaOptOutCheckbox != null) {
        break label336;
      }
      this.mOptOutCheckbox.setVisibility(8);
      return;
      j = 0;
      break;
      label311:
      k = 2131362152;
      break label112;
      this.mMainView.findViewById(2131755688).setVisibility(0);
    }
    label336:
    ChallengeProto.FormCheckbox localFormCheckbox = this.mChallenge.gaiaOptOutCheckbox;
    this.mOptOutCheckbox.setOnCheckedChangeListener(this);
    this.mOptOutCheckbox.setText(getString(2131361868).toUpperCase());
    this.mIsOptOutChecked = localFormCheckbox.checked;
    this.mOptOutCheckbox.setChecked(this.mIsOptOutChecked);
  }
  
  protected final void handleErrorState()
  {
    int i = this.mSidecar.mSubstate;
    if (i == 1)
    {
      this.mSidecar.confirmCredentials(this.mAccountName, this.mPasswordView.getText().toString());
      return;
    }
    if (i == 4)
    {
      failWithMaxAttemptsExceeded(this.mAccountName);
      return;
    }
    fail();
  }
  
  public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    if (paramCompoundButton == this.mOptOutCheckbox)
    {
      logClick(753, false);
      this.mIsOptOutChecked = paramBoolean;
      if (paramBoolean)
      {
        changePasswordHelpAndPurchaseDisclaimer(false);
        this.mOptOutInfo.setVisibility(0);
        updateAccessibilitySettingsButton();
      }
    }
    while (paramCompoundButton != this.mFingerprintOptInCheckbox) {
      for (;;)
      {
        return;
        this.mOptOutInfo.setVisibility(8);
      }
    }
    logClick(755, false);
    this.mIsFingerprintOptInChecked = paramBoolean;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mHelpToggleView)
    {
      logClick(752, false);
      bool1 = this.mIsPasswordHelpExpanded;
      bool2 = false;
      if (!bool1) {
        bool2 = true;
      }
      changePasswordHelpAndPurchaseDisclaimer(bool2);
    }
    while (paramView != this.mAccessibilitySettingsButton)
    {
      boolean bool1;
      boolean bool2;
      return;
    }
    this.mPurchaseAuthBeforeManageSettings = PurchaseAuthUtils.getPurchaseAuthType(this.mAccountName);
    ((PurchaseFragment)this.mParentFragment).launchSettingsForAuthChallenge();
  }
  
  public final void onContinueButtonClicked()
  {
    if (this.mAuthState.getAuthMethod() == 3)
    {
      fallbackAuthMethod();
      return;
    }
    logClickAndSubmitResponse(false);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((ChallengeProto.AuthenticationChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "AuthChallengeStep.challenge"));
    this.mIsOptOutChecked = false;
    this.mIsFingerprintOptInChecked = false;
    this.mEventLog = FinskyApp.get().getEventLogger(((PurchaseFragment)this.mParentFragment).mAccount);
    if (paramBundle != null)
    {
      this.mFailedCount = paramBundle.getInt("AuthChallengeStep.retryCount");
      this.mIsOptOutChecked = paramBundle.getBoolean("AuthChallengeStep.optOutSelected");
      this.mIsFingerprintOptInChecked = paramBundle.getBoolean("AuthChallengeStep.fingerprintOptInSelected");
      this.mPurchaseAuthBeforeManageSettings = paramBundle.getInt("AuthChallengeStep.PurchaseAuthBeforeManageSettings");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968821, paramViewGroup, false);
    AuthChallengeDialogDocumentInfoLayout localAuthChallengeDialogDocumentInfoLayout = (AuthChallengeDialogDocumentInfoLayout)this.mMainView.findViewById(2131755662);
    String str1 = this.mChallenge.documentTitle;
    String str2 = this.mChallenge.formattedPrice;
    int i;
    if (!TextUtils.isEmpty(str1))
    {
      localAuthChallengeDialogDocumentInfoLayout.mTitleView.setText(str1);
      localAuthChallengeDialogDocumentInfoLayout.mTitleView.setVisibility(0);
      i = 1;
      label76:
      if (TextUtils.isEmpty(str2)) {
        break label406;
      }
      localAuthChallengeDialogDocumentInfoLayout.mPriceView.setText(str2);
      localAuthChallengeDialogDocumentInfoLayout.mPriceView.setVisibility(0);
      i = 1;
      label106:
      if (i == 0) {
        break label419;
      }
    }
    label406:
    label419:
    for (int j = 0;; j = 8)
    {
      localAuthChallengeDialogDocumentInfoLayout.setVisibility(j);
      if ((!TextUtils.isEmpty(this.mChallenge.documentTitle)) || (!TextUtils.isEmpty(this.mChallenge.formattedPrice)) || (!TextUtils.isEmpty(this.mChallenge.instrumentDisplayTitle))) {
        this.mMainView.findViewById(2131755660).setVisibility(0);
      }
      createChallengeHeader(((PurchaseFragment)this.mParentFragment).mAccount.name, this.mChallenge.instrumentDisplayTitle, this.mAuthState.getTitleResourceId());
      this.mOptOutInfo = ((TextView)this.mMainView.findViewById(2131755575));
      this.mOptOutCheckbox = ((CheckBox)this.mMainView.findViewById(2131755688));
      if (!TextUtils.isEmpty(this.mChallenge.challengeDescriptionTextHtml))
      {
        TextView localTextView = (TextView)this.mMainView.findViewById(2131755686);
        localTextView.setText(Html.fromHtml(this.mChallenge.challengeDescriptionTextHtml));
        localTextView.setVisibility(0);
      }
      if (UiUtils.isAccessibilityEnabled(this.mMainView.getContext()))
      {
        this.mAccessibilitySettingsButton = ((Button)this.mMainView.findViewById(2131755689));
        this.mAccessibilitySettingsButton.setOnClickListener(this);
      }
      int k = this.mAuthState.getAuthMethod();
      switch (k)
      {
      default: 
        throw new IllegalStateException("Unexpected auth method " + k);
        localAuthChallengeDialogDocumentInfoLayout.mTitleView.setVisibility(8);
        i = 0;
        break label76;
        localAuthChallengeDialogDocumentInfoLayout.mPriceView.setVisibility(8);
        break label106;
      }
    }
    this.mMainView.findViewById(2131755682).setVisibility(0);
    this.mIsFingerprintOptInChecked = true;
    this.mFingerprintHelper = new FingerprintUiHelper(getContext(), (ImageView)this.mMainView.findViewById(2131755683), (TextView)this.mMainView.findViewById(2131755684), this.mAccountName, new FingerprintUiHelper.Callback()
    {
      public final void onAuthenticated()
      {
        AuthChallengeStep.this.succeed();
      }
      
      public final void onUnrecoverableError()
      {
        AuthChallengeStep.this.fallbackAuthMethod();
      }
    });
    for (;;)
    {
      return this.mMainView;
      createPasswordPinView(2131755101, 2131362001);
      continue;
      createPasswordPinView(2131755057, 2131361999);
    }
  }
  
  public final void onPause()
  {
    super.onPause();
    if (this.mFingerprintHelper != null) {
      this.mFingerprintHelper.stopListening();
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    FingerprintUiHelper localFingerprintUiHelper;
    if (this.mFingerprintHelper != null)
    {
      localFingerprintUiHelper = this.mFingerprintHelper;
      if (!AuthApi.isFingerprintAvailable(localFingerprintUiHelper.mAccountName)) {
        localFingerprintUiHelper.mCallback.onUnrecoverableError();
      }
    }
    else
    {
      return;
    }
    localFingerprintUiHelper.mCancellationSignal = new CancellationSignal();
    localFingerprintUiHelper.mSelfCancelled = false;
    localFingerprintUiHelper.mFingerprintManager.authenticate(null, localFingerprintUiHelper.mCancellationSignal, 0, localFingerprintUiHelper, null);
    localFingerprintUiHelper.mStatusTextView.setTextColor(localFingerprintUiHelper.mStatusTextView.getResources().getColor(2131689542, null));
    localFingerprintUiHelper.mStatusTextView.setText(localFingerprintUiHelper.mStatusTextView.getResources().getString(2131362157));
    localFingerprintUiHelper.mIcon.setImageResource(2130837766);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("AuthChallengeStep.retryCount", this.mFailedCount);
    paramBundle.putBoolean("AuthChallengeStep.optOutSelected", this.mIsOptOutChecked);
    paramBundle.putBoolean("AuthChallengeStep.fingerprintOptInSelected", this.mIsFingerprintOptInChecked);
    paramBundle.putInt("AuthChallengeStep.PurchaseAuthBeforeManageSettings", this.mPurchaseAuthBeforeManageSettings);
  }
  
  protected final void performAdditionalSuccessActions(Bundle paramBundle)
  {
    paramBundle.putString(this.mChallenge.responseRetryCountParam, String.valueOf(this.mFailedCount));
    if (this.mIsOptOutChecked)
    {
      int i = PurchaseAuthUtils.getPurchaseAuthType(this.mAccountName);
      if (i == 0)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        FinskyLog.wtf("Got through auth while opted out? Previous=%d", arrayOfObject);
      }
      PurchaseAuthUtils.setAndLogPurchaseAuth(this.mAccountName, 0, Integer.valueOf(i), this.mEventLog, "purchase-auth-screen");
    }
    PurchaseAuthUtils.setAndLogFingerprintAuth(this.mAccountName, this.mIsFingerprintOptInChecked, this.mEventLog, "purchase-auth-screen");
    FinskyPreferences.lastGaiaAuthTimestamp.get(this.mAccountName).put(Long.valueOf(System.currentTimeMillis()));
  }
  
  protected final void submitResponse()
  {
    this.mSidecar.submitResponse(this.mAccountName, this.mPasswordView.getText().toString(), null);
  }
  
  public void updatePasswordHelpAndPurchaseDisclaimer()
  {
    int i = PurchaseAuthUtils.getPurchaseAuthType(this.mAccountName);
    int j = 1;
    int k;
    label35:
    TextView localTextView2;
    int m;
    if (i == 0)
    {
      j = 0;
      TextView localTextView1 = this.mPurchaseDisclaimerView;
      if ((j == 0) || (!this.mIsPasswordHelpExpanded)) {
        break label159;
      }
      k = 0;
      localTextView1.setVisibility(k);
      localTextView2 = this.mPasswordRecoveryView;
      boolean bool = this.mIsPasswordHelpExpanded;
      m = 0;
      if (!bool) {
        break label166;
      }
    }
    for (;;)
    {
      localTextView2.setVisibility(m);
      updateAccessibilitySettingsButton();
      return;
      if (!this.mChallenge.displayClientAuthMessage)
      {
        j = 0;
        break;
      }
      int n;
      if (i == 1) {
        n = 2131362607;
      }
      for (;;)
      {
        this.mPurchaseDisclaimerView.setText(getPurchaseChallengeText(n));
        break;
        if (i == 2)
        {
          n = 2131362605;
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(i);
          FinskyLog.wtf("Unexpected value for PurchaseAuth message %d", arrayOfObject);
          n = 2131362605;
          j = 0;
        }
      }
      label159:
      k = 8;
      break label35;
      label166:
      m = 8;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.AuthChallengeStep
 * JD-Core Version:    0.7.0.1
 */