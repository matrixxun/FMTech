package com.android.volley;

public class DefaultRetryPolicy
  implements RetryPolicy
{
  public final float mBackoffMultiplier;
  private int mCurrentRetryCount;
  private int mCurrentTimeoutMs;
  private final int mMaxNumRetries;
  
  public DefaultRetryPolicy()
  {
    this(2500, 1, 1.0F);
  }
  
  public DefaultRetryPolicy(int paramInt1, int paramInt2, float paramFloat)
  {
    this.mCurrentTimeoutMs = paramInt1;
    this.mMaxNumRetries = paramInt2;
    this.mBackoffMultiplier = paramFloat;
  }
  
  public final int getCurrentRetryCount()
  {
    return this.mCurrentRetryCount;
  }
  
  public final int getCurrentTimeout()
  {
    return this.mCurrentTimeoutMs;
  }
  
  public void retry(VolleyError paramVolleyError)
    throws VolleyError
  {
    this.mCurrentRetryCount = (1 + this.mCurrentRetryCount);
    this.mCurrentTimeoutMs = ((int)(this.mCurrentTimeoutMs + this.mCurrentTimeoutMs * this.mBackoffMultiplier));
    if (this.mCurrentRetryCount <= this.mMaxNumRetries) {}
    for (int i = 1; i == 0; i = 0) {
      throw paramVolleyError;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.DefaultRetryPolicy
 * JD-Core Version:    0.7.0.1
 */