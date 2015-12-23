package com.google.android.wallet.common.api.http;

import android.os.SystemClock;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public abstract class BackgroundEventRequest<T>
  extends Request<T>
{
  private long mEndTimeMs;
  private final Response.Listener<T> mResponseListener;
  private final long mStartTimeMs;
  
  public BackgroundEventRequest(Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(1, null, paramErrorListener);
    this.mResponseListener = paramListener;
    this.mStartTimeMs = SystemClock.elapsedRealtime();
  }
  
  public final void deliverError(VolleyError paramVolleyError)
  {
    this.mEndTimeMs = SystemClock.elapsedRealtime();
    super.deliverError(paramVolleyError);
  }
  
  public final void deliverResponse(T paramT)
  {
    this.mEndTimeMs = SystemClock.elapsedRealtime();
    this.mResponseListener.onResponse(paramT);
  }
  
  public abstract int getBackgroundEventReceivedType();
  
  public abstract int getBackgroundEventSentType();
  
  public final long getClientLatencyMs()
  {
    return this.mEndTimeMs - this.mStartTimeMs;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.http.BackgroundEventRequest
 * JD-Core Version:    0.7.0.1
 */