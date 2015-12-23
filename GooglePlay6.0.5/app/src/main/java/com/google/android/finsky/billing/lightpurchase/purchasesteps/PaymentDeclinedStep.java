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
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.utils.UiUtils;

public final class PaymentDeclinedStep
  extends StepFragment<PurchaseFragment>
{
  private View mMainView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1300);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    String str = this.mArguments.getString("PaymentDeclinedStep.continueButtonLabel");
    if (TextUtils.isEmpty(str)) {
      str = paramResources.getString(2131362418);
    }
    return str;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(1301, null);
    ((PurchaseFragment)this.mParentFragment).launchBillingProfile();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968817, paramViewGroup, false);
    ((TextView)this.mMainView.findViewById(2131755173)).setText(this.mArguments.getString("PaymentDeclinedStep.title"));
    TextView localTextView = (TextView)this.mMainView.findViewById(2131755233);
    localTextView.setText(Html.fromHtml(this.mArguments.getString("PaymentDeclinedStep.messageHtml")));
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    return this.mMainView;
  }
  
  public final void onResume()
  {
    super.onResume();
    String str1 = this.mArguments.getString("PaymentDeclinedStep.messageHtml");
    String str2 = this.mArguments.getString("PaymentDeclinedStep.title");
    String str3;
    if (!TextUtils.isEmpty(str1)) {
      str3 = Html.fromHtml(str1).toString();
    }
    for (;;)
    {
      if (str3 != null) {
        UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), str3, this.mMainView);
      }
      return;
      boolean bool = TextUtils.isEmpty(str2);
      str3 = null;
      if (!bool) {
        str3 = str2;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.PaymentDeclinedStep
 * JD-Core Version:    0.7.0.1
 */