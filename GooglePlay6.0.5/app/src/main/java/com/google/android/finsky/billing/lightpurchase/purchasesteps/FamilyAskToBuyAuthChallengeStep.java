package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.billing.lightpurchase.AuthChallengeSidecar;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.ChallengeProto.FamilyWalletAuthChallenge;
import com.google.android.finsky.protos.ChallengeProto.FamilyWalletAuthChallenge.Approver;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.play.image.FifeImageView;

public final class FamilyAskToBuyAuthChallengeStep
  extends PurchaseAuthenticationChallengeBaseStep
{
  private ChallengeProto.FamilyWalletAuthChallenge mChallenge;
  
  public FamilyAskToBuyAuthChallengeStep()
  {
    super(5212);
  }
  
  public static FamilyAskToBuyAuthChallengeStep newInstance(String paramString, ChallengeProto.FamilyWalletAuthChallenge paramFamilyWalletAuthChallenge, AuthState paramAuthState)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("FamilyAskToBuyAuthChallengeStep.challenge", ParcelableProto.forProto(paramFamilyWalletAuthChallenge));
    localBundle.putParcelable("PurchaseAuthenticationChallengeBaseStep.authState", paramAuthState);
    FamilyAskToBuyAuthChallengeStep localFamilyAskToBuyAuthChallengeStep = new FamilyAskToBuyAuthChallengeStep();
    localFamilyAskToBuyAuthChallengeStep.setArguments(localBundle);
    return localFamilyAskToBuyAuthChallengeStep;
  }
  
  protected final void changePasswordHelpAndPurchaseDisclaimer(boolean paramBoolean)
  {
    this.mIsPasswordHelpExpanded = paramBoolean;
    this.mPurchaseDisclaimerView.setText(Html.fromHtml(this.mChallenge.helpTextHtml));
    TextView localTextView = this.mPurchaseDisclaimerView;
    if (this.mIsPasswordHelpExpanded) {}
    for (int i = 0;; i = 8)
    {
      localTextView.setVisibility(i);
      return;
    }
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131361854);
  }
  
  protected final void handleErrorState()
  {
    if (this.mSidecar.mSubstate == 4)
    {
      failWithMaxAttemptsExceeded(this.mChallenge.approver.emailAddress);
      return;
    }
    fail();
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mHelpToggleView)
    {
      logClick(752, false);
      boolean bool1 = this.mIsPasswordHelpExpanded;
      boolean bool2 = false;
      if (!bool1) {
        bool2 = true;
      }
      changePasswordHelpAndPurchaseDisclaimer(bool2);
    }
  }
  
  public final void onContinueButtonClicked()
  {
    logClickAndSubmitResponse(false);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((ChallengeProto.FamilyWalletAuthChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "FamilyAskToBuyAuthChallengeStep.challenge"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968808, paramViewGroup, false);
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755663);
    localTextView1.setText(this.mChallenge.documentTitle);
    localTextView1.setVisibility(0);
    TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755659);
    localTextView2.setText(this.mChallenge.formattedPrice);
    localTextView2.setVisibility(0);
    FifeImageView localFifeImageView = (FifeImageView)this.mMainView.findViewById(2131755610);
    if (this.mChallenge.documentThumbnail != null)
    {
      localFifeImageView.setImage(this.mChallenge.documentThumbnail.imageUrl, this.mChallenge.documentThumbnail.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      localFifeImageView.setVisibility(0);
    }
    this.mMainView.findViewById(2131755660).setVisibility(0);
    createChallengeHeader(this.mChallenge.approver.emailAddress, this.mChallenge.instrumentDisplayTitle, 2131361861);
    createPasswordPinView(2131755057, 2131361999);
    return this.mMainView;
  }
  
  protected final void performAdditionalSuccessActions(Bundle paramBundle) {}
  
  protected final void submitResponse()
  {
    this.mSidecar.submitResponse(this.mAccountName, this.mPasswordView.getText().toString(), this.mChallenge.approver);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.FamilyAskToBuyAuthChallengeStep
 * JD-Core Version:    0.7.0.1
 */