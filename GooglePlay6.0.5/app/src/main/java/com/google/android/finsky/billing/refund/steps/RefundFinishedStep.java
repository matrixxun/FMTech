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

public final class RefundFinishedStep
  extends RefundBaseStep
{
  private final PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1154);
  
  public static RefundFinishedStep newInstance(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("RefundFinishedStep.userMessage", paramString);
    RefundFinishedStep localRefundFinishedStep = new RefundFinishedStep();
    localRefundFinishedStep.setArguments(localBundle);
    return localRefundFinishedStep;
  }
  
  protected final String getAnnouncementString()
  {
    return this.mArguments.getString("RefundFinishedStep.userMessage");
  }
  
  protected final int getLayoutRes()
  {
    return 2130969065;
  }
  
  protected final String getNegativeButtonLabel(Resources paramResources)
  {
    return null;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  protected final String getPositiveButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362418);
  }
  
  public final void onPositiveButtonClick()
  {
    logClick(1155, null);
    ((RequestRefundFragment)this.mParentFragment).finish();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((TextView)paramView.findViewById(2131756031)).setText(Html.fromHtml(this.mArguments.getString("RefundFinishedStep.userMessage")));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.steps.RefundFinishedStep
 * JD-Core Version:    0.7.0.1
 */