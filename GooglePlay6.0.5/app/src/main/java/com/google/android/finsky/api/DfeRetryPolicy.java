package com.google.android.finsky.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.play.utils.config.GservicesValue;

public final class DfeRetryPolicy
  extends DefaultRetryPolicy
{
  private final DfeApiContext mDfeApiContext;
  private boolean mHadAuthException;
  
  public DfeRetryPolicy(int paramInt1, int paramInt2, float paramFloat, DfeApiContext paramDfeApiContext)
  {
    super(paramInt1, paramInt2, paramFloat);
    this.mDfeApiContext = paramDfeApiContext;
  }
  
  public DfeRetryPolicy(int paramInt, DfeApiContext paramDfeApiContext)
  {
    super(paramInt, ((Integer)DfeApiConfig.dfeMaxRetries.get()).intValue(), ((Float)DfeApiConfig.dfeBackoffMultipler.get()).floatValue());
    this.mDfeApiContext = paramDfeApiContext;
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
      this.mDfeApiContext.invalidateAuthToken();
    }
    super.retry(paramVolleyError);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeRetryPolicy
 * JD-Core Version:    0.7.0.1
 */