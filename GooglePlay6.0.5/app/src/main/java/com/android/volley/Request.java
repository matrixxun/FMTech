package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class Request<T>
  implements Comparable<Request<T>>
{
  public Cache.Entry mCacheEntry;
  private boolean mCanceled;
  final int mDefaultTrafficStatsTag;
  private final Response.ErrorListener mErrorListener;
  private final VolleyLog.MarkerLog mEventLog;
  public final int mMethod;
  RequestQueue mRequestQueue;
  boolean mResponseDelivered;
  public RetryPolicy mRetryPolicy;
  Integer mSequence;
  public boolean mShouldCache;
  public Object mTag;
  private final String mUrl;
  
  public Request(int paramInt, String paramString, Response.ErrorListener paramErrorListener)
  {
    VolleyLog.MarkerLog localMarkerLog;
    String str;
    if (VolleyLog.MarkerLog.ENABLED)
    {
      localMarkerLog = new VolleyLog.MarkerLog();
      this.mEventLog = localMarkerLog;
      this.mShouldCache = true;
      this.mCanceled = false;
      this.mResponseDelivered = false;
      this.mCacheEntry = null;
      this.mMethod = paramInt;
      this.mUrl = paramString;
      this.mErrorListener = paramErrorListener;
      this.mRetryPolicy = new DefaultRetryPolicy();
      if (TextUtils.isEmpty(paramString)) {
        break label121;
      }
      Uri localUri = Uri.parse(paramString);
      if (localUri == null) {
        break label121;
      }
      str = localUri.getHost();
      if (str == null) {
        break label121;
      }
    }
    label121:
    for (int i = str.hashCode();; i = 0)
    {
      this.mDefaultTrafficStatsTag = i;
      return;
      localMarkerLog = null;
      break;
    }
  }
  
  private static byte[] encodeParameters(Map<String, String> paramMap, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localStringBuilder.append(URLEncoder.encode((String)localEntry.getKey(), paramString));
        localStringBuilder.append('=');
        localStringBuilder.append(URLEncoder.encode((String)localEntry.getValue(), paramString));
        localStringBuilder.append('&');
      }
      arrayOfByte = localStringBuilder.toString().getBytes(paramString);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("Encoding not supported: " + paramString, localUnsupportedEncodingException);
    }
    byte[] arrayOfByte;
    return arrayOfByte;
  }
  
  public final void addMarker(String paramString)
  {
    if (VolleyLog.MarkerLog.ENABLED) {
      this.mEventLog.add(paramString, Thread.currentThread().getId());
    }
  }
  
  public void cancel()
  {
    this.mCanceled = true;
  }
  
  public void deliverError(VolleyError paramVolleyError)
  {
    if (this.mErrorListener != null) {
      this.mErrorListener.onErrorResponse(paramVolleyError);
    }
  }
  
  public abstract void deliverResponse(T paramT);
  
  final void finish(final String paramString)
  {
    RequestQueue localRequestQueue;
    if (this.mRequestQueue != null)
    {
      localRequestQueue = this.mRequestQueue;
      synchronized (localRequestQueue.mCurrentRequests)
      {
        localRequestQueue.mCurrentRequests.remove(this);
        synchronized (localRequestQueue.mFinishedListeners)
        {
          Iterator localIterator = localRequestQueue.mFinishedListeners.iterator();
          if (localIterator.hasNext()) {
            localIterator.next();
          }
        }
      }
      if (!this.mShouldCache) {}
    }
    final long l;
    synchronized (localRequestQueue.mWaitingRequests)
    {
      String str = getCacheKey();
      Queue localQueue = (Queue)localRequestQueue.mWaitingRequests.remove(str);
      if (localQueue != null)
      {
        if (VolleyLog.DEBUG)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(localQueue.size());
          arrayOfObject[1] = str;
          VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", arrayOfObject);
        }
        localRequestQueue.mCacheQueue.addAll(localQueue);
      }
      if (VolleyLog.MarkerLog.ENABLED)
      {
        l = Thread.currentThread().getId();
        if (Looper.myLooper() != Looper.getMainLooper()) {
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public final void run()
            {
              Request.this.mEventLog.add(paramString, l);
              Request.this.mEventLog.finish(toString());
            }
          });
        }
      }
      else
      {
        return;
      }
    }
    this.mEventLog.add(paramString, l);
    this.mEventLog.finish(toString());
  }
  
  public byte[] getBody()
    throws AuthFailureError
  {
    Map localMap = getParams();
    if ((localMap != null) && (localMap.size() > 0)) {
      return encodeParameters(localMap, "UTF-8");
    }
    return null;
  }
  
  public String getBodyContentType()
  {
    return "application/x-www-form-urlencoded; charset=UTF-8";
  }
  
  public String getCacheKey()
  {
    return getUrl();
  }
  
  public Map<String, String> getHeaders()
    throws AuthFailureError
  {
    return Collections.emptyMap();
  }
  
  public Map<String, String> getParams()
    throws AuthFailureError
  {
    return null;
  }
  
  @Deprecated
  public byte[] getPostBody()
    throws AuthFailureError
  {
    Map localMap = getParams();
    if ((localMap != null) && (localMap.size() > 0)) {
      return encodeParameters(localMap, "UTF-8");
    }
    return null;
  }
  
  @Deprecated
  public String getPostBodyContentType()
  {
    return getBodyContentType();
  }
  
  public Priority getPriority()
  {
    return Priority.NORMAL;
  }
  
  public final int getSequence()
  {
    if (this.mSequence == null) {
      throw new IllegalStateException("getSequence called before setSequence");
    }
    return this.mSequence.intValue();
  }
  
  public final int getTimeoutMs()
  {
    return this.mRetryPolicy.getCurrentTimeout();
  }
  
  public String getUrl()
  {
    return this.mUrl;
  }
  
  public boolean isCanceled()
  {
    return this.mCanceled;
  }
  
  public VolleyError parseNetworkError(VolleyError paramVolleyError)
  {
    return paramVolleyError;
  }
  
  public abstract Response<T> parseNetworkResponse(NetworkResponse paramNetworkResponse);
  
  public String toString()
  {
    String str1 = "0x" + Integer.toHexString(this.mDefaultTrafficStatsTag);
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.mCanceled) {}
    for (String str2 = "[X] ";; str2 = "[ ] ") {
      return str2 + getUrl() + " " + str1 + " " + getPriority() + " " + this.mSequence;
    }
  }
  
  public static enum Priority
  {
    static
    {
      HIGH = new Priority("HIGH", 2);
      IMMEDIATE = new Priority("IMMEDIATE", 3);
      Priority[] arrayOfPriority = new Priority[4];
      arrayOfPriority[0] = LOW;
      arrayOfPriority[1] = NORMAL;
      arrayOfPriority[2] = HIGH;
      arrayOfPriority[3] = IMMEDIATE;
      $VALUES = arrayOfPriority;
    }
    
    private Priority() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.Request
 * JD-Core Version:    0.7.0.1
 */