package com.google.android.finsky.download.inlineappinstaller.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.utils.UiUtils;

public final class InlineConsumptionAppInstallerErrorStep
  extends StepFragment<InlineConsumptionAppInstallerFragment>
{
  private String mErrorMsg;
  private View mMainView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5109);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131361931);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).logClick(5110, this);
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).finish(false);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968795, paramViewGroup, false);
    Bundle localBundle = this.mArguments;
    this.mErrorMsg = localBundle.getString("errorMsg");
    ((TextView)this.mMainView.findViewById(2131755173)).setText(this.mErrorMsg);
    ConsumptionUtils.maybeBindAppIcon((Document)localBundle.getParcelable("appDoc"), this.mMainView);
    return this.mMainView;
  }
  
  public final void onResume()
  {
    super.onResume();
    if (TextUtils.isEmpty(this.mErrorMsg)) {
      UiUtils.sendAccessibilityEventWithText(getContext(), this.mErrorMsg, this.mMainView);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerErrorStep
 * JD-Core Version:    0.7.0.1
 */