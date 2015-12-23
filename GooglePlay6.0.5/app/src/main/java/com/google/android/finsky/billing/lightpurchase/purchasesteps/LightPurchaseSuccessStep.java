package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.support.v4.app.Fragment;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.SuccessStep;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;

public final class LightPurchaseSuccessStep
  extends SuccessStep<PurchaseFragment>
{
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(775);
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(778, null);
    performSuccessActionAndFinish();
  }
  
  protected final void performSuccessActionAndFinish()
  {
    ((PurchaseFragment)this.mParentFragment).performSuccessActionAndFinish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.LightPurchaseSuccessStep
 * JD-Core Version:    0.7.0.1
 */