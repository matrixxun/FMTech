package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Purchase.ChangeSubscription;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;

public final class ChangeSubscriptionStep
  extends StepFragment<PurchaseFragment>
  implements PlayStoreUiElementNode
{
  private int mBackend;
  private Purchase.ChangeSubscription mChangeSubscription;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1280);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return getString(2131362062);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(1281, null);
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = ((PurchaseFragment)this.mParentFragment).mSidecar;
    if (localCheckoutPurchaseSidecar.mState != 4)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localCheckoutPurchaseSidecar.mState);
      FinskyLog.wtf("switchFromChangeSubscriptionToCart() called in state %d", arrayOfObject);
    }
    localCheckoutPurchaseSidecar.setState(5, 0);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mBackend = localBundle.getInt("ChangeSubscriptionStep.backend");
    this.mChangeSubscription = ((Purchase.ChangeSubscription)ParcelableProto.getProtoFromBundle(localBundle, "ChangeSubscriptionStep.changeSubscription"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130968815, paramViewGroup, false);
    ((TextView)localView.findViewById(2131755173)).setText(this.mChangeSubscription.title);
    ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(getActivity(), this.mBackend);
    TextView localTextView = (TextView)localView.findViewById(2131755233);
    localTextView.setText(BillingUtils.parseHtmlAndColorizeEm(this.mChangeSubscription.descriptionHtml, localColorStateList.getDefaultColor()));
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    localTextView.setLinkTextColor(localTextView.getTextColors());
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.ChangeSubscriptionStep
 * JD-Core Version:    0.7.0.1
 */