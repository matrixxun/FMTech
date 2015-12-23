package com.google.android.finsky.download.inlineappinstaller.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerFragment;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.utils.UiUtils;

public final class InlineConsumptionAppInstallerReadyToReadStep
  extends StepFragment<InlineConsumptionAppInstallerFragment>
{
  private View mMainView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5107);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362633);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).logClick(5108, this);
    ((InlineConsumptionAppInstallerFragment)this.mParentFragment).finish(true);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Document localDocument = (Document)this.mArguments.getParcelable("InlineConsumptionAppInstallerReadyToReadStep.appDoc");
    this.mMainView = paramLayoutInflater.inflate(2130968795, paramViewGroup, false);
    ConsumptionUtils.maybeBindAppIcon(localDocument, this.mMainView);
    return this.mMainView;
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(getContext(), getString(2131361846), this.mMainView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.steps.InlineConsumptionAppInstallerReadyToReadStep
 * JD-Core Version:    0.7.0.1
 */