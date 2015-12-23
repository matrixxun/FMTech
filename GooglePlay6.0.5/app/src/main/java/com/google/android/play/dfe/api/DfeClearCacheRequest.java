package com.google.android.play.dfe.api;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;

public final class DfeClearCacheRequest
  extends Request<Object>
{
  private final Cache mCache;
  private final String mCacheKey;
  private final Runnable mCallback;
  private final boolean mFullExpire;
  
  public DfeClearCacheRequest(Cache paramCache, String paramString, boolean paramBoolean)
  {
    super(0, null, null);
    this.mCache = paramCache;
    this.mCacheKey = paramString;
    this.mFullExpire = true;
    this.mCallback = null;
  }
  
  protected final void deliverResponse(Object paramObject) {}
  
  public final Request.Priority getPriority()
  {
    return Request.Priority.IMMEDIATE;
  }
  
  public final boolean isCanceled()
  {
    this.mCache.invalidate(this.mCacheKey, this.mFullExpire);
    if (this.mCallback != null) {
      new Handler(Looper.getMainLooper()).postAtFrontOfQueue(this.mCallback);
    }
    return true;
  }
  
  protected final Response<Object> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.DfeClearCacheRequest
 * JD-Core Version:    0.7.0.1
 */