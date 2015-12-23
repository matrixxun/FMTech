package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseError;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.protos.Purchase.PreparePurchaseResponse;
import com.google.android.finsky.utils.UiUtils;

public final class ErrorStep
  extends StepFragment<PurchaseFragment>
{
  private CheckoutPurchaseError mCheckoutPurchaseError;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(770);
  
  public static ErrorStep newInstance(CheckoutPurchaseError paramCheckoutPurchaseError, boolean paramBoolean)
  {
    ErrorStep localErrorStep = new ErrorStep();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("ErrorStep.checkoutPurchaseError", paramCheckoutPurchaseError);
    localBundle.putBoolean("ErrorStep.purchaseFailed", paramBoolean);
    localErrorStep.setArguments(localBundle);
    localErrorStep.mCheckoutPurchaseError = paramCheckoutPurchaseError;
    return localErrorStep;
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    if (TextUtils.isEmpty(this.mCheckoutPurchaseError.continueButtonLabel)) {
      return paramResources.getString(2131362418);
    }
    return this.mCheckoutPurchaseError.continueButtonLabel;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(771, null);
    if (this.mArguments.getBoolean("ErrorStep.purchaseFailed"))
    {
      ((PurchaseFragment)this.mParentFragment).finish();
      return;
    }
    if (this.mCheckoutPurchaseError.preparePurchaseResponse == null)
    {
      ((PurchaseFragment)this.mParentFragment).preparePurchase(null, null);
      return;
    }
    PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
    Purchase.PreparePurchaseResponse localPreparePurchaseResponse = this.mCheckoutPurchaseError.preparePurchaseResponse;
    localPurchaseFragment.mSidecar.handlePrepareResponse(localPreparePurchaseResponse);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mCheckoutPurchaseError = ((CheckoutPurchaseError)this.mArguments.getParcelable("ErrorStep.checkoutPurchaseError"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(2130968817, paramViewGroup, false);
    String str1;
    if (TextUtils.isEmpty(this.mCheckoutPurchaseError.errorTitle))
    {
      str1 = getString(2131362123);
      if (!TextUtils.isEmpty(this.mCheckoutPurchaseError.errorMessageHtml)) {
        break label115;
      }
    }
    label115:
    for (String str2 = getString(2131362187);; str2 = this.mCheckoutPurchaseError.errorMessageHtml)
    {
      ((TextView)localViewGroup.findViewById(2131755173)).setText(str1);
      TextView localTextView = (TextView)localViewGroup.findViewById(2131755233);
      localTextView.setText(Html.fromHtml(str2));
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      return localViewGroup;
      str1 = this.mCheckoutPurchaseError.errorTitle;
      break;
    }
  }
  
  public final void onStart()
  {
    super.onStart();
    View localView = this.mView;
    TextView localTextView = (TextView)localView.findViewById(2131755233);
    UiUtils.sendAccessibilityEventWithText(getActivity(), localTextView.getText().toString(), localView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.ErrorStep
 * JD-Core Version:    0.7.0.1
 */