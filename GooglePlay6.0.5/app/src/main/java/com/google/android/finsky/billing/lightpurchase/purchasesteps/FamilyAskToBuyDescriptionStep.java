package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.ChallengeProto.FamilyWalletAuthChallenge;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;

public final class FamilyAskToBuyDescriptionStep
  extends StepFragment<PurchaseFragment>
  implements PlayStoreUiElementNode
{
  private ChallengeProto.FamilyWalletAuthChallenge mChallenge;
  private View mMainView;
  private final PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(5210);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362062);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(5211, null);
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = ((PurchaseFragment)this.mParentFragment).mSidecar;
    if (localCheckoutPurchaseSidecar.mState != 7)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localCheckoutPurchaseSidecar.mState);
      FinskyLog.wtf("switchToFamilyManagerChallenge() called in state %d", arrayOfObject);
    }
    localCheckoutPurchaseSidecar.setState(12, 0);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.mChallenge = ((ChallengeProto.FamilyWalletAuthChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "FamilyAskToBuyDescriptionStep.challenge"));
    super.onCreate(paramBundle);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968807, paramViewGroup, false);
    ((TextView)this.mMainView.findViewById(2131755626)).setText(this.mChallenge.challengeDescriptionTitle);
    ((TextView)this.mMainView.findViewById(2131755627)).setText(this.mChallenge.challengeDescriptionContent);
    return this.mMainView;
  }
  
  public final void onResume()
  {
    super.onResume();
    Context localContext = this.mMainView.getContext();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mChallenge.challengeDescriptionTitle;
    arrayOfObject[1] = this.mChallenge.challengeDescriptionContent;
    UiUtils.sendAccessibilityEventWithText(localContext, getString(2131362613, arrayOfObject), this.mMainView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.FamilyAskToBuyDescriptionStep
 * JD-Core Version:    0.7.0.1
 */