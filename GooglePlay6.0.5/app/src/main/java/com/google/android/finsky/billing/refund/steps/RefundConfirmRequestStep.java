package com.google.android.finsky.billing.refund.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.billing.refund.RequestRefundFragment;
import com.google.android.finsky.billing.refund.RequestRefundSidecar;
import com.google.android.play.layout.PlayTextView;

public final class RefundConfirmRequestStep
  extends RefundBaseStep
{
  private final PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1151);
  
  protected final String getAnnouncementString()
  {
    return this.mArguments.getString("RefundConfirmRequestStep.userMessage");
  }
  
  protected final int getLayoutRes()
  {
    return 2130969064;
  }
  
  protected final String getNegativeButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131361915);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  protected final String getPositiveButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362769);
  }
  
  public final void onPositiveButtonClick()
  {
    logClick(1155, null);
    ((RequestRefundFragment)this.mParentFragment).mSidecar.setState(5, 0);
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((PlayTextView)paramView.findViewById(2131755173)).setText(2131362660);
    ((TextView)paramView.findViewById(2131755621)).setText(this.mArguments.getString("RefundConfirmRequestStep.accountName"));
    ((TextView)paramView.findViewById(2131756031)).setText(Html.fromHtml(this.mArguments.getString("RefundConfirmRequestStep.userMessage")));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.steps.RefundConfirmRequestStep
 * JD-Core Version:    0.7.0.1
 */