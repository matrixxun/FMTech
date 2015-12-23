package com.google.android.finsky.billing;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public abstract interface BillingFlowContext
{
  public abstract void hideFragment$4b1b4969(Fragment paramFragment);
  
  public abstract void hideProgress();
  
  public abstract void persistFragment(Bundle paramBundle, String paramString, Fragment paramFragment);
  
  public abstract Fragment restoreFragment(Bundle paramBundle, String paramString);
  
  public abstract void showDialogFragment(DialogFragment paramDialogFragment, String paramString);
  
  public abstract void showFragment$41b27b4d(Fragment paramFragment, String paramString);
  
  public abstract void showProgress(int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingFlowContext
 * JD-Core Version:    0.7.0.1
 */