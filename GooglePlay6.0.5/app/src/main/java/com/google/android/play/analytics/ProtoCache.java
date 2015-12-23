package com.google.android.play.analytics;

import android.util.Log;
import java.lang.reflect.Array;

public class ProtoCache
{
  private static ProtoCache INSTANCE = null;
  private static final String TAG = ProtoCache.class.getSimpleName();
  public final ElementCache<ClientAnalytics.LogEvent> mCacheLogEvent = new ElementCache(ClientAnalytics.LogEvent.class, 60);
  public final ElementCache<ClientAnalytics.LogEventKeyValues> mCacheLogEventKeyValues = new ElementCache(ClientAnalytics.LogEventKeyValues.class, 50);
  
  public static ProtoCache getInstance()
  {
    try
    {
      if (INSTANCE == null) {
        INSTANCE = new ProtoCache();
      }
      ProtoCache localProtoCache = INSTANCE;
      return localProtoCache;
    }
    finally {}
  }
  
  public final void recycle(ClientAnalytics.LogEvent paramLogEvent)
  {
    ClientAnalytics.LogEventKeyValues[] arrayOfLogEventKeyValues = paramLogEvent.value;
    for (int i = 0; i < arrayOfLogEventKeyValues.length; i++)
    {
      ClientAnalytics.LogEventKeyValues localLogEventKeyValues = arrayOfLogEventKeyValues[i];
      localLogEventKeyValues.clear();
      this.mCacheLogEventKeyValues.recycle(localLogEventKeyValues);
    }
    paramLogEvent.clear();
    this.mCacheLogEvent.recycle(paramLogEvent);
  }
  
  private static final class ElementCache<T>
  {
    private T[] mCache;
    Class<?> mClazz;
    private int mCount;
    private int mHighWater;
    private final int mLimit;
    
    public ElementCache(Class<?> paramClass, int paramInt)
    {
      this.mLimit = paramInt;
      this.mCount = 0;
      this.mHighWater = 0;
      this.mCache = ((Object[])Array.newInstance(paramClass, paramInt));
      this.mClazz = paramClass;
    }
    
    public final T obtain()
    {
      try
      {
        if (this.mCount > 0)
        {
          this.mCount = (-1 + this.mCount);
          Object localObject3 = this.mCache[this.mCount];
          this.mCache[this.mCount] = null;
          return localObject3;
        }
        Object localObject2;
        return null;
      }
      finally
      {
        try
        {
          localObject2 = this.mClazz.newInstance();
          return localObject2;
        }
        catch (Exception localException)
        {
          Log.wtf(ProtoCache.TAG, "Exception from mClazz.newInstance ", localException);
        }
        localObject1 = finally;
      }
    }
    
    public final void recycle(T paramT)
    {
      try
      {
        if (this.mCount < this.mLimit)
        {
          this.mCache[this.mCount] = paramT;
          this.mCount = (1 + this.mCount);
          if (this.mCount > this.mHighWater) {
            this.mHighWater = this.mCount;
          }
        }
        return;
      }
      finally {}
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.analytics.ProtoCache
 * JD-Core Version:    0.7.0.1
 */