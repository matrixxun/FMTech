package com.google.android.play.dfe.api;

import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;

public final class PlayDfeApiImpl
  implements PlayDfeApi
{
  private static final float PLUS_PROFILE_BG_BACKOFF_MULT = ((Float)PlayG.plusProfileBgBackoffMult.get()).floatValue();
  private static final int PLUS_PROFILE_BG_MAX_RETRIES;
  private static final int PLUS_PROFILE_BG_TIMEOUT_MS = ((Integer)PlayG.plusProfileBgTimeoutMs.get()).intValue();
  private final PlayDfeApiContext mApiContext;
  private final RequestQueue mQueue;
  
  static
  {
    PLUS_PROFILE_BG_MAX_RETRIES = ((Integer)PlayG.plusProfileBgMaxRetries.get()).intValue();
  }
  
  public PlayDfeApiImpl(RequestQueue paramRequestQueue, PlayDfeApiContext paramPlayDfeApiContext)
  {
    this.mQueue = paramRequestQueue;
    this.mApiContext = paramPlayDfeApiContext;
  }
  
  public final Request<?> getPlusProfile(Response.Listener<PlusProfileResponse> paramListener, Response.ErrorListener paramErrorListener, boolean paramBoolean)
  {
    DfeRequest localDfeRequest = new DfeRequest(PLUS_PROFILE_URI.toString(), this.mApiContext, PlusProfileResponse.class, paramListener, paramErrorListener);
    if (!paramBoolean) {
      localDfeRequest.mRetryPolicy = new DfeRetryPolicy(PLUS_PROFILE_BG_TIMEOUT_MS, PLUS_PROFILE_BG_MAX_RETRIES, PLUS_PROFILE_BG_BACKOFF_MULT, this.mApiContext);
    }
    return this.mQueue.add(localDfeRequest);
  }
  
  public final void invalidatePlusProfile$1385ff()
  {
    DfeRequest localDfeRequest = new DfeRequest(PLUS_PROFILE_URI.toString(), this.mApiContext, PlusProfileResponse.class, null, null);
    this.mQueue.add(new DfeClearCacheRequest(this.mApiContext.mCache, localDfeRequest.getCacheKey(), true));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.PlayDfeApiImpl
 * JD-Core Version:    0.7.0.1
 */