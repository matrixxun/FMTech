package com.google.android.finsky.api;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.NetworkInfo;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.DebugImageRequest;
import com.google.android.play.image.TentativeGcRunner;

public final class InstrumentedBitmapLoader
  extends BitmapLoader
{
  private final FinskyEventLog mEventLogger;
  private NetworkStateProvider mNetworkStateProvider;
  
  public InstrumentedBitmapLoader(FinskyEventLog paramFinskyEventLog, NetworkStateProvider paramNetworkStateProvider, RequestQueue paramRequestQueue, int paramInt1, int paramInt2, TentativeGcRunner paramTentativeGcRunner)
  {
    super(paramRequestQueue, paramInt1, paramInt2, paramTentativeGcRunner);
    this.mEventLogger = paramFinskyEventLog;
    this.mNetworkStateProvider = paramNetworkStateProvider;
  }
  
  protected final BitmapLoader.DebugImageRequest createImageRequest(String paramString, int paramInt1, int paramInt2, Bitmap.Config paramConfig, Response.Listener<Bitmap> paramListener, Response.ErrorListener paramErrorListener)
  {
    return new InstrumentedDebugImageRequest(this.mEventLogger, this.mNetworkStateProvider, paramString, paramInt1, paramInt2, paramConfig, paramListener, paramErrorListener);
  }
  
  public final class InstrumentedDebugImageRequest
    extends BitmapLoader.DebugImageRequest
  {
    private FinskyEventLog mEventLogger;
    private NetworkStateProvider mNetworkStateProvider;
    private long mNetworkTimeMs;
    private int mResponseBodySizeBytes;
    private NetworkInfo mStartNetworkInfo;
    
    public InstrumentedDebugImageRequest(NetworkStateProvider paramNetworkStateProvider, String paramString, int paramInt1, int paramInt2, Bitmap.Config paramConfig, Response.Listener<Bitmap> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramInt2, paramConfig, paramListener, paramErrorListener, localErrorListener);
      this.mEventLogger = paramNetworkStateProvider;
      this.mNetworkStateProvider = paramString;
      this.mStartNetworkInfo = paramString.getCurrentNetworkInfo();
    }
    
    private void logNetworkEvent(boolean paramBoolean1, VolleyError paramVolleyError, boolean paramBoolean2)
    {
      if ((this.mRetryPolicy instanceof DefaultRetryPolicy)) {}
      for (float f = ((DefaultRetryPolicy)this.mRetryPolicy).mBackoffMultiplier;; f = 0.0F)
      {
        this.mEventLogger.logRpcReport(getUrl(), this.mNetworkTimeMs, 0L, 1 + this.mRetryPolicy.getCurrentRetryCount(), this.mRetryPolicy.getCurrentTimeout(), f, paramBoolean1, paramVolleyError, this.mStartNetworkInfo, this.mNetworkStateProvider.getCurrentNetworkInfo(), this.mResponseBodySizeBytes, paramBoolean2);
        return;
      }
    }
    
    public final void deliverError(VolleyError paramVolleyError)
    {
      super.deliverError(paramVolleyError);
      this.mNetworkTimeMs = paramVolleyError.networkTimeMs;
      logNetworkEvent(false, paramVolleyError, false);
    }
    
    protected final void deliverResponse(Bitmap paramBitmap)
    {
      super.deliverResponse(paramBitmap);
      if (this.mNetworkTimeMs <= 0L) {}
      for (boolean bool = true;; bool = false)
      {
        logNetworkEvent(true, null, bool);
        return;
      }
    }
    
    protected final Response<Bitmap> parseNetworkResponse(NetworkResponse paramNetworkResponse)
    {
      this.mNetworkTimeMs = paramNetworkResponse.networkTimeMs;
      this.mResponseBodySizeBytes = paramNetworkResponse.data.length;
      return super.parseNetworkResponse(paramNetworkResponse);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.InstrumentedBitmapLoader
 * JD-Core Version:    0.7.0.1
 */