package com.google.android.finsky.billing.lightpurchase;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowFragment;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.ProgressDialogFragment;
import com.google.android.finsky.utils.FinskyLog;

public abstract class LegacyFlowWrapperFragment
  extends BillingFlowFragment
  implements BillingFlowContext, BillingFlowListener
{
  protected BillingFlow mLegacyFlow;
  
  public final void back()
  {
    if (this.mLegacyFlow != null) {
      this.mLegacyFlow.back();
    }
  }
  
  public final boolean canGoBack()
  {
    return (this.mLegacyFlow != null) && (this.mLegacyFlow.canGoBack());
  }
  
  protected abstract BillingFlow getLegacyPurchaseFlow();
  
  public final void hideFragment$4b1b4969(Fragment paramFragment)
  {
    FinskyLog.wtf("Not implemented.", new Object[0]);
  }
  
  public final void hideProgress()
  {
    ProgressDialogFragment localProgressDialogFragment = (ProgressDialogFragment)this.mFragmentManager.findFragmentByTag("CompleteDcb3Flow.progressDialog");
    if (localProgressDialogFragment == null)
    {
      FinskyLog.wtf("Progress dialog not shown.", new Object[0]);
      return;
    }
    localProgressDialogFragment.dismissInternal(false);
  }
  
  public final void onError$5b46052e(String paramString)
  {
    fail(paramString);
  }
  
  public final void onFinished$713ba888(boolean paramBoolean, Bundle paramBundle)
  {
    if (paramBoolean)
    {
      cancel();
      return;
    }
    finish(paramBundle);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Bundle localBundle = new Bundle();
    if (this.mLegacyFlow != null) {
      this.mLegacyFlow.saveState(localBundle);
    }
    paramBundle.putBundle("CompleteDcb3Flow.flowState", localBundle);
  }
  
  public final void persistFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    this.mFragmentManager.putFragment(paramBundle, paramString, paramFragment);
  }
  
  public final Fragment restoreFragment(Bundle paramBundle, String paramString)
  {
    return this.mFragmentManager.getFragment(paramBundle, paramString);
  }
  
  public final void showDialogFragment(DialogFragment paramDialogFragment, String paramString)
  {
    paramDialogFragment.show(this.mFragmentManager, paramString);
  }
  
  public final void showFragment$41b27b4d(Fragment paramFragment, String paramString)
  {
    FinskyLog.wtf("Not implemented.", new Object[0]);
  }
  
  public final void showProgress(int paramInt)
  {
    if (this.mFragmentManager.findFragmentByTag("CompleteDcb3Flow.progressDialog") != null)
    {
      FinskyLog.wtf("Duplicate progress dialog.", new Object[0]);
      return;
    }
    ProgressDialogFragment.newInstance(paramInt).show(this.mFragmentManager, "CompleteDcb3Flow.progressDialog");
  }
  
  protected final void startOrResumeLegacyFlow(Bundle paramBundle)
  {
    this.mLegacyFlow = getLegacyPurchaseFlow();
    if ((paramBundle != null) && (paramBundle.containsKey("CompleteDcb3Flow.flowState")))
    {
      Bundle localBundle = paramBundle.getBundle("CompleteDcb3Flow.flowState");
      this.mLegacyFlow.resumeFromSavedState(localBundle);
      return;
    }
    this.mLegacyFlow.start();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.LegacyFlowWrapperFragment
 * JD-Core Version:    0.7.0.1
 */