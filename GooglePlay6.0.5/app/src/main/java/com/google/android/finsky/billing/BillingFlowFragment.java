package com.google.android.finsky.billing;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BillingFlowFragment
  extends Fragment
{
  protected BillingFlowHost mHost;
  
  public abstract void back();
  
  public abstract boolean canGoBack();
  
  public final void cancel()
  {
    this.mHost.onFlowCanceled$70802698();
  }
  
  public final void fail(String paramString)
  {
    this.mHost.onFlowError$3af1da62(paramString);
  }
  
  public final void finish(Bundle paramBundle)
  {
    this.mHost.onFlowFinished$127338c4(paramBundle);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if ((this.mTarget instanceof BillingFlowHost))
    {
      this.mHost = ((BillingFlowHost)this.mTarget);
      return;
    }
    if ((this.mParentFragment instanceof BillingFlowHost))
    {
      this.mHost = ((BillingFlowHost)this.mParentFragment);
      return;
    }
    this.mHost = ((BillingFlowHost)getActivity());
  }
  
  public static abstract interface BillingFlowHost
  {
    public abstract void onFlowCanceled$70802698();
    
    public abstract void onFlowError$3af1da62(String paramString);
    
    public abstract void onFlowFinished$127338c4(Bundle paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingFlowFragment
 * JD-Core Version:    0.7.0.1
 */