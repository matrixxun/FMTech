package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.protos.ChallengeProto.AcknowledgementChallenge;
import com.google.android.finsky.utils.ParcelableProto;

public final class AcknowledgementChallengeStep
  extends StepFragment<PurchaseFragment>
{
  public ChallengeProto.AcknowledgementChallenge mChallenge;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1290);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    if (this.mChallenge.hasSubmitButtonLabel) {
      return this.mChallenge.submitButtonLabel;
    }
    return paramResources.getString(2131362062);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    ((PurchaseFragment)this.mParentFragment).logClick(1291, this);
    Bundle localBundle = new Bundle();
    localBundle.putString(this.mChallenge.responseAcknowledgementParam, "true");
    ((PurchaseFragment)this.mParentFragment).answerChallenge(localBundle);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.mChallenge = ((ChallengeProto.AcknowledgementChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "AcknowledgementChallengeStep.challenge"));
    FinskyEventLog.setServerLogCookie(this.mUiElement, this.mChallenge.serverLogsCookie);
    super.onCreate(paramBundle);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130968601, paramViewGroup, false);
    ((TextView)localView.findViewById(2131755173)).setText(this.mChallenge.title);
    TextView localTextView = (TextView)localView.findViewById(2131755211);
    localTextView.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.AcknowledgementChallengeStep
 * JD-Core Version:    0.7.0.1
 */