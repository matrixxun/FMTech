package com.google.android.vending.remoting.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public final class VendingRetryPolicy
  extends DefaultRetryPolicy
{
  private static final int VENDING_TIMEOUT_MS = ((Integer)G.vendingRequestTimeoutMs.get()).intValue();
  private boolean mHadAuthException;
  private boolean mUseSecureToken;
  private final VendingApiContext mVendingApiContext;
  
  public VendingRetryPolicy(VendingApiContext paramVendingApiContext, boolean paramBoolean)
  {
    super(VENDING_TIMEOUT_MS, 1, 0.0F);
    this.mVendingApiContext = paramVendingApiContext;
    this.mUseSecureToken = paramBoolean;
  }
  
  public final void retry(VolleyError paramVolleyError)
    throws VolleyError
  {
    if ((paramVolleyError instanceof AuthFailureError))
    {
      if (this.mHadAuthException) {
        throw paramVolleyError;
      }
      this.mHadAuthException = true;
      this.mVendingApiContext.invalidateAuthToken(this.mUseSecureToken);
    }
    super.retry(paramVolleyError);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.VendingRetryPolicy
 * JD-Core Version:    0.7.0.1
 */