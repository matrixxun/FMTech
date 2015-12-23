package com.google.android.finsky.billing;

import android.os.Bundle;

public abstract class BillingFlow
{
  public final BillingFlowContext mBillingFlowContext;
  private boolean mFinished;
  private final BillingFlowListener mListener;
  public final Bundle mParameters;
  
  public BillingFlow(BillingFlowContext paramBillingFlowContext, BillingFlowListener paramBillingFlowListener, Bundle paramBundle)
  {
    this.mParameters = paramBundle;
    this.mBillingFlowContext = paramBillingFlowContext;
    this.mListener = paramBillingFlowListener;
  }
  
  private void notifyFinished(boolean paramBoolean, Bundle paramBundle)
  {
    this.mListener.onFinished$713ba888(paramBoolean, paramBundle);
  }
  
  public void back()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean canGoBack()
  {
    return false;
  }
  
  public void cancel()
  {
    this.mFinished = true;
    notifyFinished(true, null);
  }
  
  public final void fail(String paramString)
  {
    this.mFinished = true;
    this.mListener.onError$5b46052e(paramString);
  }
  
  public final void finish(Bundle paramBundle)
  {
    this.mFinished = true;
    notifyFinished(false, paramBundle);
  }
  
  public void onActivityResume() {}
  
  public abstract void resumeFromSavedState(Bundle paramBundle);
  
  public abstract void saveState(Bundle paramBundle);
  
  public abstract void start();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingFlow
 * JD-Core Version:    0.7.0.1
 */