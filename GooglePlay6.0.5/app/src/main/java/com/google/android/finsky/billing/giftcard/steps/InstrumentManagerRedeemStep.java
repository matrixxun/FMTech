package com.google.android.finsky.billing.giftcard.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.giftcard.RedeemCodeSidecar;
import com.google.android.finsky.billing.instrumentmanager.InstrumentManagerStep;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.utils.FinskyLog;

public final class InstrumentManagerRedeemStep
  extends InstrumentManagerStep<RedeemCodeFragment>
{
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1106);
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    RedeemCodeFragment localRedeemCodeFragment = (RedeemCodeFragment)this.mParentFragment;
    if (isSuccess(paramInt))
    {
      RedeemCodeSidecar localRedeemCodeSidecar = localRedeemCodeFragment.mSidecar;
      if (localRedeemCodeSidecar.mState != 6)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(localRedeemCodeSidecar.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject);
        return;
      }
      localRedeemCodeSidecar.sendRedemptionRequest();
      return;
    }
    localRedeemCodeFragment.finish();
  }
  
  public final void onQueuedOrchestrationResult(int paramInt, Bundle paramBundle) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.steps.InstrumentManagerRedeemStep
 * JD-Core Version:    0.7.0.1
 */