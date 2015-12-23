package com.google.android.wallet.common.api.http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;

public final class AuthHandlingRetryPolicy
  extends DefaultRetryPolicy
{
  private final ApiContext mApiContext;
  private boolean mHadAuthException;
  
  public AuthHandlingRetryPolicy(int paramInt, ApiContext paramApiContext)
  {
    super(paramInt, 1, 1.0F);
    this.mApiContext = paramApiContext;
  }
  
  public final void retry(VolleyError paramVolleyError)
    throws VolleyError
  {
    if ((paramVolleyError instanceof AuthFailureError))
    {
      if ((((AuthFailureError)paramVolleyError).mResolutionIntent != null) || (this.mHadAuthException)) {
        throw paramVolleyError;
      }
      this.mHadAuthException = true;
      this.mApiContext.invalidateAuthToken();
      super.retry(paramVolleyError);
      return;
    }
    throw paramVolleyError;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.http.AuthHandlingRetryPolicy
 * JD-Core Version:    0.7.0.1
 */