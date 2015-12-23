package com.google.android.finsky.api;

import com.android.volley.AuthFailureError;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.play.utils.config.GservicesValue;

public final class PreparePurchaseRetryPolicy
  implements RetryPolicy
{
  private final DfeApiContext mDfeApiContext;
  private final int mInitialTimeoutMs;
  private boolean mWasRetryAttempted;
  
  PreparePurchaseRetryPolicy(int paramInt, DfeApiContext paramDfeApiContext)
  {
    this.mInitialTimeoutMs = paramInt;
    this.mDfeApiContext = paramDfeApiContext;
  }
  
  public final int getCurrentRetryCount()
  {
    if (this.mWasRetryAttempted) {
      return 1;
    }
    return 0;
  }
  
  public final int getCurrentTimeout()
  {
    if (this.mWasRetryAttempted) {
      return ((Integer)DfeApiConfig.purchaseStatusTimeoutMs.get()).intValue();
    }
    return this.mInitialTimeoutMs;
  }
  
  public final void retry(VolleyError paramVolleyError)
    throws VolleyError
  {
    if (this.mWasRetryAttempted) {
      throw paramVolleyError;
    }
    if ((paramVolleyError instanceof AuthFailureError)) {
      this.mDfeApiContext.invalidateAuthToken();
    }
    this.mWasRetryAttempted = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.PreparePurchaseRetryPolicy
 * JD-Core Version:    0.7.0.1
 */