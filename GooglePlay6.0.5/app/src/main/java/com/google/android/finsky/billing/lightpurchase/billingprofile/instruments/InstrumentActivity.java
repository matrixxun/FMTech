package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowFragment;
import com.google.android.finsky.billing.BillingFlowFragment.BillingFlowHost;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.ProgressDialogFragment;
import com.google.android.finsky.billing.PromptForFopHelper;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SetupWizardUtils;

public abstract class InstrumentActivity
  extends LoggingFragmentActivity
  implements BillingFlowContext, BillingFlowFragment.BillingFlowHost, BillingFlowListener
{
  protected ViewGroup mFragmentContainer;
  protected View mMainView;
  private boolean mNeedsHideProgress;
  private ProgressDialogFragment mProgressDialog;
  private BillingFlow mRunningFlow;
  private BillingFlowFragment mRunningFlowFragment;
  private boolean mSaveInstanceStateCalled;
  protected Bundle mSavedFlowState;
  public SetupWizardNavBar mSetupWizardNavBar;
  protected TextView mTitleView;
  
  protected int getActivityLayout()
  {
    return 2130968803;
  }
  
  protected abstract BillingFlow getBillingFlow();
  
  protected int getBillingUiMode()
  {
    return 0;
  }
  
  public final void hideFragment$4b1b4969(Fragment paramFragment)
  {
    if (this.mSaveInstanceStateCalled) {
      return;
    }
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.remove(paramFragment);
    localFragmentTransaction.commit();
  }
  
  public void hideProgress()
  {
    this.mNeedsHideProgress = false;
    if (this.mProgressDialog != null)
    {
      if (this.mSaveInstanceStateCalled) {
        this.mNeedsHideProgress = true;
      }
    }
    else {
      return;
    }
    this.mProgressDialog.dismissInternal(false);
    this.mProgressDialog = null;
  }
  
  public void onBackPressed()
  {
    if (this.mRunningFlow != null)
    {
      this.mEventLog.logClickEvent(600, null, this);
      if (this.mRunningFlow.canGoBack())
      {
        this.mRunningFlow.back();
        return;
      }
      FinskyLog.d("Cannot interrupt the current flow.", new Object[0]);
      return;
    }
    if (this.mRunningFlowFragment != null)
    {
      this.mEventLog.logClickEvent(600, null, this);
      if (this.mRunningFlowFragment.canGoBack())
      {
        this.mRunningFlowFragment.back();
        return;
      }
      FinskyLog.d("Cannot interrupt the current flow.", new Object[0]);
      return;
    }
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mMainView = View.inflate(this, getActivityLayout(), null);
    this.mTitleView = ((TextView)this.mMainView.findViewById(2131755173));
    setContentView(this.mMainView);
    this.mFragmentContainer = ((ViewGroup)findViewById(2131755234));
    if (getBillingUiMode() == 1) {}
    for (SetupWizardNavBar localSetupWizardNavBar = SetupWizardUtils.getNavBarIfPossible(this);; localSetupWizardNavBar = null)
    {
      this.mSetupWizardNavBar = localSetupWizardNavBar;
      if (paramBundle != null)
      {
        this.mSavedFlowState = paramBundle.getBundle("InstrumentActivity.saved_flow_state");
        this.mProgressDialog = ((ProgressDialogFragment)restoreFragment(paramBundle, "InstrumentActivity.progress_dialog"));
        if (this.mProgressDialog != null)
        {
          this.mProgressDialog.dismissInternal(false);
          this.mProgressDialog = null;
        }
        if (paramBundle.containsKey("InstrumentActivity.title")) {
          this.mTitleView.setText(paramBundle.getString("InstrumentActivity.title"));
        }
      }
      return;
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mFragmentContainer.setVisibility(8);
    this.mRunningFlow = null;
  }
  
  public final void onError$5b46052e(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("billing_flow_error", true);
    localIntent.putExtra("billing_flow_error_message", paramString);
    setResult(0, localIntent);
    finish();
  }
  
  public final void onFinished$713ba888(boolean paramBoolean, Bundle paramBundle)
  {
    if (paramBoolean) {}
    for (int i = 0;; i = -1)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("billing_flow_error", false);
      localIntent.putExtra("billing_flow_canceled", paramBoolean);
      if ((!paramBoolean) && (paramBundle != null) && (paramBundle.containsKey("instrument_id"))) {
        localIntent.putExtra("instrument_id", paramBundle.getString("instrument_id"));
      }
      if (!paramBoolean) {
        PromptForFopHelper.expireHasNoFop(this.mAccountName);
      }
      setResult(i, localIntent);
      finish();
      return;
    }
  }
  
  public final void onFlowCanceled$70802698()
  {
    onFinished$713ba888(true, null);
  }
  
  public final void onFlowError$3af1da62(String paramString)
  {
    onError$5b46052e(paramString);
  }
  
  public final void onFlowFinished$127338c4(Bundle paramBundle)
  {
    onFinished$713ba888(false, paramBundle);
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.mRunningFlow != null) {
      this.mRunningFlow.onActivityResume();
    }
    this.mSaveInstanceStateCalled = false;
    if (this.mNeedsHideProgress) {
      hideProgress();
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    this.mSaveInstanceStateCalled = true;
    super.onSaveInstanceState(paramBundle);
    if (this.mRunningFlow != null)
    {
      Bundle localBundle = new Bundle();
      this.mRunningFlow.saveState(localBundle);
      paramBundle.putBundle("InstrumentActivity.saved_flow_state", localBundle);
    }
    if (this.mProgressDialog != null) {
      persistFragment(paramBundle, "InstrumentActivity.progress_dialog", this.mProgressDialog);
    }
    if (!TextUtils.isEmpty(this.mTitleView.getText())) {
      paramBundle.putString("InstrumentActivity.title", this.mTitleView.getText().toString());
    }
  }
  
  public final void persistFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    getSupportFragmentManager().putFragment(paramBundle, paramString, paramFragment);
  }
  
  public final Fragment restoreFragment(Bundle paramBundle, String paramString)
  {
    return getSupportFragmentManager().getFragment(paramBundle, paramString);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mTitleView.setText(paramCharSequence);
  }
  
  public final void showDialogFragment(DialogFragment paramDialogFragment, String paramString)
  {
    if (this.mSaveInstanceStateCalled) {
      return;
    }
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    Fragment localFragment = getSupportFragmentManager().findFragmentByTag(paramString);
    if (localFragment != null) {
      localFragmentTransaction.remove(localFragment);
    }
    localFragmentTransaction.addToBackStack(null);
    paramDialogFragment.show(getSupportFragmentManager(), paramString);
  }
  
  public final void showFragment$41b27b4d(Fragment paramFragment, String paramString)
  {
    if (this.mSaveInstanceStateCalled) {}
    do
    {
      return;
      setTitle(paramString);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755234, paramFragment);
      localFragmentTransaction.commitAllowingStateLoss();
    } while (this.mSetupWizardNavBar == null);
    this.mSetupWizardNavBar.resetButtonsState();
  }
  
  public void showProgress(int paramInt)
  {
    if (this.mSaveInstanceStateCalled) {
      return;
    }
    this.mProgressDialog = ProgressDialogFragment.newInstance(paramInt);
    this.mProgressDialog.show(getSupportFragmentManager(), "progress_dialog");
  }
  
  protected final void startOrResumeFlow(Bundle paramBundle)
  {
    this.mRunningFlowFragment = ((BillingFlowFragment)getSupportFragmentManager().findFragmentByTag("InstrumentActivity.billing_flow_fragment"));
    if (this.mRunningFlowFragment != null)
    {
      if (FinskyLog.DEBUG) {
        FinskyLog.v("Re-attached to billing flow fragment.", new Object[0]);
      }
      return;
    }
    this.mRunningFlowFragment = null;
    if (this.mRunningFlowFragment != null) {
      getSupportFragmentManager().beginTransaction().add(this.mFragmentContainer.getId(), this.mRunningFlowFragment, "InstrumentActivity.billing_flow_fragment").commit();
    }
    while ((this.mRunningFlow == null) && (this.mRunningFlowFragment == null))
    {
      FinskyLog.wtf("Couldn't instantiate BillingFlow for FOP.", new Object[0]);
      finish();
      return;
      this.mRunningFlow = getBillingFlow();
    }
    if (this.mRunningFlow != null)
    {
      if (paramBundle != null) {
        break label148;
      }
      this.mRunningFlow.start();
    }
    for (;;)
    {
      this.mFragmentContainer.setVisibility(0);
      return;
      label148:
      this.mRunningFlow.resumeFromSavedState(paramBundle);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.InstrumentActivity
 * JD-Core Version:    0.7.0.1
 */