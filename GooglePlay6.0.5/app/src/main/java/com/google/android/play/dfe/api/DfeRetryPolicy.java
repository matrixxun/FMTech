package com.google.android.play.dfe.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;

public final class DfeRetryPolicy
  extends DefaultRetryPolicy
{
  private boolean mHadAuthException;
  private final PlayDfeApiContext mPlayDfeApiContext;
  
  public DfeRetryPolicy(int paramInt1, int paramInt2, float paramFloat, PlayDfeApiContext paramPlayDfeApiContext)
  {
    super(paramInt1, paramInt2, paramFloat);
    this.mPlayDfeApiContext = paramPlayDfeApiContext;
  }
  
  public DfeRetryPolicy(PlayDfeApiContext paramPlayDfeApiContext)
  {
    super(((Integer)PlayG.dfeRequestTimeoutMs.get()).intValue(), ((Integer)PlayG.dfeMaxRetries.get()).intValue(), ((Float)PlayG.dfeBackoffMultipler.get()).floatValue());
    this.mPlayDfeApiContext = paramPlayDfeApiContext;
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
      this.mPlayDfeApiContext.invalidateAuthToken();
    }
    super.retry(paramVolleyError);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.DfeRetryPolicy
 * JD-Core Version:    0.7.0.1
 */