package com.google.android.finsky.billing.refund.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.billing.refund.RequestRefundFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.utils.UiUtils;

public abstract class RefundBaseStep
  extends StepFragment<RequestRefundFragment>
  implements ButtonBar.ClickListener
{
  ButtonBar mButtonBar;
  private View mMainView;
  
  public final boolean allowButtonBar()
  {
    return false;
  }
  
  protected abstract String getAnnouncementString();
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return null;
  }
  
  protected abstract int getLayoutRes();
  
  protected abstract String getNegativeButtonLabel(Resources paramResources);
  
  protected abstract String getPositiveButtonLabel(Resources paramResources);
  
  public final void onContinueButtonClicked() {}
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mMainView = paramLayoutInflater.inflate(getLayoutRes(), paramViewGroup, false);
    this.mButtonBar = ((ButtonBar)this.mMainView.findViewById(2131755300));
    if (this.mButtonBar != null)
    {
      String str1 = getPositiveButtonLabel(getResources());
      if (str1 == null) {
        break label106;
      }
      this.mButtonBar.setPositiveButtonTitle(str1);
      String str2 = getNegativeButtonLabel(getResources());
      if (str2 == null) {
        break label117;
      }
      this.mButtonBar.setNegativeButtonTitle(str2);
    }
    for (;;)
    {
      this.mButtonBar.setClickListener(this);
      return this.mMainView;
      label106:
      this.mButtonBar.setPositiveButtonVisible(false);
      break;
      label117:
      this.mButtonBar.setNegativeButtonVisible(false);
    }
  }
  
  public final void onNegativeButtonClick()
  {
    logClick(1156, null);
    ((RequestRefundFragment)this.mParentFragment).finish();
  }
  
  public final void onResume()
  {
    super.onResume();
    String str = getAnnouncementString();
    if (!TextUtils.isEmpty(str)) {
      UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), str, this.mMainView);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.steps.RefundBaseStep
 * JD-Core Version:    0.7.0.1
 */