package com.android.volley;

import android.os.Process;
import java.util.concurrent.BlockingQueue;

public final class CacheDispatcher
  extends Thread
{
  private static final boolean DEBUG = VolleyLog.DEBUG;
  private final Cache mCache;
  private final BlockingQueue<Request<?>> mCacheQueue;
  private final ResponseDelivery mDelivery;
  private final BlockingQueue<Request<?>> mNetworkQueue;
  volatile boolean mQuit = false;
  
  public CacheDispatcher(BlockingQueue<Request<?>> paramBlockingQueue1, BlockingQueue<Request<?>> paramBlockingQueue2, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mCacheQueue = paramBlockingQueue1;
    this.mNetworkQueue = paramBlockingQueue2;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
  }
  
  public final void run()
  {
    if (DEBUG) {
      VolleyLog.v("start new dispatcher", new Object[0]);
    }
    Process.setThreadPriority(10);
    this.mCache.initialize();
    final Request localRequest;
    label73:
    Cache.Entry localEntry;
    for (;;)
    {
      try
      {
        localRequest = (Request)this.mCacheQueue.take();
        localRequest.addMarker("cache-queue-take");
        if (!localRequest.isCanceled()) {
          break label73;
        }
        localRequest.finish("cache-discard-canceled");
        continue;
        if (!this.mQuit) {
          continue;
        }
      }
      catch (InterruptedException localInterruptedException) {}
      return;
      localEntry = this.mCache.get(localRequest.getCacheKey());
      if (localEntry == null)
      {
        localRequest.addMarker("cache-miss");
        this.mNetworkQueue.put(localRequest);
      }
      else
      {
        if (!localEntry.isExpired()) {
          break;
        }
        localRequest.addMarker("cache-hit-expired");
        localRequest.mCacheEntry = localEntry;
        this.mNetworkQueue.put(localRequest);
      }
    }
    localRequest.addMarker("cache-hit");
    Response localResponse = localRequest.parseNetworkResponse(new NetworkResponse(localEntry.data, localEntry.responseHeaders));
    localRequest.addMarker("cache-hit-parsed");
    if (localEntry.softTtl < System.currentTimeMillis()) {}
    for (int i = 1;; i = 0)
    {
      if (i == 0)
      {
        this.mDelivery.postResponse(localRequest, localResponse);
        break;
      }
      localRequest.addMarker("cache-hit-refresh-needed");
      localRequest.mCacheEntry = localEntry;
      localResponse.intermediate = true;
      this.mDelivery.postResponse(localRequest, localResponse, new Runnable()
      {
        public final void run()
        {
          try
          {
            CacheDispatcher.this.mNetworkQueue.put(localRequest);
            return;
          }
          catch (InterruptedException localInterruptedException) {}
        }
      });
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.CacheDispatcher
 * JD-Core Version:    0.7.0.1
 */