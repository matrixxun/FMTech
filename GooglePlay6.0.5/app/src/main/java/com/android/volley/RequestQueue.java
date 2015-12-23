package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class RequestQueue
{
  private final Cache mCache;
  private CacheDispatcher mCacheDispatcher;
  final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue();
  final Set<Request<?>> mCurrentRequests = new HashSet();
  private final ResponseDelivery mDelivery;
  private NetworkDispatcher[] mDispatchers;
  List<Object> mFinishedListeners = new ArrayList();
  private final Network mNetwork;
  private final PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue();
  public AtomicInteger mSequenceGenerator = new AtomicInteger();
  final Map<String, Queue<Request<?>>> mWaitingRequests = new HashMap();
  
  public RequestQueue(Cache paramCache, Network paramNetwork)
  {
    this(paramCache, paramNetwork, 4);
  }
  
  public RequestQueue(Cache paramCache, Network paramNetwork, int paramInt)
  {
    this(paramCache, paramNetwork, paramInt, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
  }
  
  private RequestQueue(Cache paramCache, Network paramNetwork, int paramInt, ResponseDelivery paramResponseDelivery)
  {
    this.mCache = paramCache;
    this.mNetwork = paramNetwork;
    this.mDispatchers = new NetworkDispatcher[paramInt];
    this.mDelivery = paramResponseDelivery;
  }
  
  public final <T> Request<T> add(Request<T> paramRequest)
  {
    paramRequest.mRequestQueue = this;
    synchronized (this.mCurrentRequests)
    {
      this.mCurrentRequests.add(paramRequest);
      paramRequest.mSequence = Integer.valueOf(this.mSequenceGenerator.incrementAndGet());
      paramRequest.addMarker("add-to-queue");
      if (!paramRequest.mShouldCache)
      {
        this.mNetworkQueue.add(paramRequest);
        return paramRequest;
      }
    }
    for (;;)
    {
      String str;
      synchronized (this.mWaitingRequests)
      {
        str = paramRequest.getCacheKey();
        if (this.mWaitingRequests.containsKey(str))
        {
          Object localObject3 = (Queue)this.mWaitingRequests.get(str);
          if (localObject3 == null) {
            localObject3 = new LinkedList();
          }
          ((Queue)localObject3).add(paramRequest);
          this.mWaitingRequests.put(str, localObject3);
          if (VolleyLog.DEBUG) {
            VolleyLog.v("Request for cacheKey=%s is in flight, putting on hold.", new Object[] { str });
          }
          return paramRequest;
        }
      }
      this.mWaitingRequests.put(str, null);
      this.mCacheQueue.add(paramRequest);
    }
  }
  
  public final void cancelAll(RequestFilter paramRequestFilter)
  {
    synchronized (this.mCurrentRequests)
    {
      Iterator localIterator = this.mCurrentRequests.iterator();
      while (localIterator.hasNext())
      {
        Request localRequest = (Request)localIterator.next();
        if (paramRequestFilter.apply(localRequest)) {
          localRequest.cancel();
        }
      }
    }
  }
  
  public final void start()
  {
    if (this.mCacheDispatcher != null)
    {
      CacheDispatcher localCacheDispatcher = this.mCacheDispatcher;
      localCacheDispatcher.mQuit = true;
      localCacheDispatcher.interrupt();
    }
    for (int i = 0; i < this.mDispatchers.length; i++) {
      if (this.mDispatchers[i] != null)
      {
        NetworkDispatcher localNetworkDispatcher2 = this.mDispatchers[i];
        localNetworkDispatcher2.mQuit = true;
        localNetworkDispatcher2.interrupt();
      }
    }
    this.mCacheDispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mCache, this.mDelivery);
    this.mCacheDispatcher.start();
    for (int j = 0; j < this.mDispatchers.length; j++)
    {
      NetworkDispatcher localNetworkDispatcher1 = new NetworkDispatcher(this.mNetworkQueue, this.mNetwork, this.mCache, this.mDelivery);
      this.mDispatchers[j] = localNetworkDispatcher1;
      localNetworkDispatcher1.start();
    }
  }
  
  public static abstract interface RequestFilter
  {
    public abstract boolean apply(Request<?> paramRequest);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.RequestQueue
 * JD-Core Version:    0.7.0.1
 */