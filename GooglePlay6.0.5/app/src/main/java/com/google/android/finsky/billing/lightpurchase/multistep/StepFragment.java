package com.google.android.finsky.billing.lightpurchase.multistep;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;

public abstract class StepFragment<T extends MultiStepFragment>
  extends Fragment
  implements PlayStoreUiElementNode
{
  public boolean allowButtonBar()
  {
    return true;
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public abstract String getContinueButtonLabel(Resources paramResources);
  
  public PlayStoreUiElementNode getParentNode()
  {
    return (PlayStoreUiElementNode)getActivity();
  }
  
  public String getSecondaryButtonLabel$469752d4()
  {
    return null;
  }
  
  public final void logClick(int paramInt)
  {
    logClick(paramInt, null);
  }
  
  public final void logClick(int paramInt, PlayStore.PlayStoreUiElementInfo paramPlayStoreUiElementInfo)
  {
    ((MultiStepFragment)this.mParentFragment).logClick(paramInt, paramPlayStoreUiElementInfo, this);
  }
  
  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof PlayStoreUiElementNode)) {
      throw new IllegalStateException("Activity must implement PlayStoreUiElementNode");
    }
  }
  
  public abstract void onContinueButtonClicked();
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      ((MultiStepFragment)this.mParentFragment).logImpression(this);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("StepFragment.exists", true);
  }
  
  public void onSecondaryButtonClicked() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.multistep.StepFragment
 * JD-Core Version:    0.7.0.1
 */