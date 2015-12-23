package com.google.android.finsky.utils.persistence;

import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class WriteThroughKeyValueStore
  implements KeyValueStore
{
  private static final ExecutorService sWriteThread = Executors.newSingleThreadExecutor(new BackgroundThreadFactory());
  public final KeyValueStore mBackingStore;
  public Map<String, Map<String, String>> mDataMap = null;
  private final Handler mHandler;
  public List<Runnable> mOnCompleteListeners = new ArrayList();
  
  public WriteThroughKeyValueStore(KeyValueStore paramKeyValueStore)
  {
    this.mBackingStore = paramKeyValueStore;
    this.mHandler = new Handler(Looper.getMainLooper());
  }
  
  public static void ensureOnMainThread()
  {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      throw new IllegalStateException("Tried to access data off of the main thread.");
    }
  }
  
  private void ensureReadyAndOnMainThread()
  {
    if (this.mDataMap == null) {
      throw new IllegalStateException("Tried to access data before initializing.");
    }
    ensureOnMainThread();
  }
  
  public final void delete(final String paramString)
  {
    ensureReadyAndOnMainThread();
    this.mDataMap.remove(paramString);
    Runnable local1 = new Runnable()
    {
      public final void run()
      {
        WriteThroughKeyValueStore.this.mBackingStore.delete(paramString);
      }
    };
    sWriteThread.submit(local1);
  }
  
  public final Map<String, Map<String, String>> fetchAll()
  {
    ensureReadyAndOnMainThread();
    if (this.mDataMap.isEmpty()) {
      return Collections.emptyMap();
    }
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.mDataMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, Collections.unmodifiableMap((Map)this.mDataMap.get(str)));
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public final Map<String, String> get(String paramString)
  {
    ensureReadyAndOnMainThread();
    Map localMap = (Map)this.mDataMap.get(paramString);
    if (localMap != null) {
      return Collections.unmodifiableMap(localMap);
    }
    return null;
  }
  
  public final void load(Runnable paramRunnable)
  {
    
    if (this.mDataMap != null) {
      this.mHandler.post(paramRunnable);
    }
    do
    {
      return;
      this.mOnCompleteListeners.add(paramRunnable);
    } while (this.mOnCompleteListeners.size() != 1);
    sWriteThread.submit(new Runnable()
    {
      public final void run()
      {
        final Map localMap = WriteThroughKeyValueStore.this.mBackingStore.fetchAll();
        WriteThroughKeyValueStore.this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            WriteThroughKeyValueStore.access$100(WriteThroughKeyValueStore.this, localMap);
          }
        });
      }
    });
  }
  
  public final void put(final String paramString, Map<String, String> paramMap)
  {
    ensureReadyAndOnMainThread();
    this.mDataMap.put(paramString, paramMap);
    Runnable local2 = new Runnable()
    {
      public final void run()
      {
        WriteThroughKeyValueStore.this.mBackingStore.put(paramString, this.val$mapCopy);
      }
    };
    sWriteThread.submit(local2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore
 * JD-Core Version:    0.7.0.1
 */