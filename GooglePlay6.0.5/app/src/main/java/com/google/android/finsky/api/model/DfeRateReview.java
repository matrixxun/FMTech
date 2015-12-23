package com.google.android.finsky.api.model;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.ReviewResponse;

public final class DfeRateReview
  extends DfeModel
  implements Response.Listener<ReviewResponse>
{
  private boolean mResponseRecieved;
  
  public DfeRateReview(DfeApi paramDfeApi, String paramString1, String paramString2, int paramInt)
  {
    paramDfeApi.rateReview(paramString1, paramString2, paramInt, this, this);
  }
  
  public final boolean isReady()
  {
    return this.mResponseRecieved;
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mResponseRecieved = true;
    super.onErrorResponse(paramVolleyError);
    unregisterAll();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeRateReview
 * JD-Core Version:    0.7.0.1
 */