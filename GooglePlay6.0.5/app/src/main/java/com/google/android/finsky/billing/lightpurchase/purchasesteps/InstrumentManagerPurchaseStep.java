package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.instrumentmanager.InstrumentManagerStep;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;

public final class InstrumentManagerPurchaseStep
  extends InstrumentManagerStep<PurchaseFragment>
{
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(740);
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    if (isSuccess(paramInt))
    {
      ((PurchaseFragment)this.mParentFragment).preparePurchase(null, null);
      return;
    }
    ((PurchaseFragment)this.mParentFragment).finish();
  }
  
  public final void onQueuedOrchestrationResult(int paramInt, Bundle paramBundle) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.InstrumentManagerPurchaseStep
 * JD-Core Version:    0.7.0.1
 */