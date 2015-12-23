package com.google.android.finsky.analytics;

import com.google.android.finsky.utils.FinskyLog;
import java.lang.reflect.Array;

public final class EventProtoCache
{
  private static EventProtoCache INSTANCE = null;
  final ElementCache<PlayStore.PlayStoreBackgroundActionEvent> mCachePlayStoreBackgroundAction = new ElementCache(PlayStore.PlayStoreBackgroundActionEvent.class, 10);
  final ElementCache<PlayStore.PlayStoreClickEvent> mCachePlayStoreClick = new ElementCache(PlayStore.PlayStoreClickEvent.class, 10);
  final ElementCache<PlayStore.PlayStoreImpressionEvent> mCachePlayStoreImpression = new ElementCache(PlayStore.PlayStoreImpressionEvent.class, 10);
  final ElementCache<PlayStore.PlayStoreLogEvent> mCachePlayStoreLogEvent = new ElementCache(PlayStore.PlayStoreLogEvent.class, 40);
  final ElementCache<PlayStore.PlayStoreSearchEvent> mCachePlayStoreSearch = new ElementCache(PlayStore.PlayStoreSearchEvent.class, 10);
  final ElementCache<PlayStore.SearchSuggestionReport> mCachePlayStoreSearchSuggestionReport = new ElementCache(PlayStore.SearchSuggestionReport.class, 10);
  private final ElementCache<PlayStore.PlayStoreUiElement> mCachePlayStoreUIElement = new ElementCache(PlayStore.PlayStoreUiElement.class, 50);
  
  public static EventProtoCache getInstance()
  {
    try
    {
      if (INSTANCE == null) {
        INSTANCE = new EventProtoCache();
      }
      EventProtoCache localEventProtoCache = INSTANCE;
      return localEventProtoCache;
    }
    finally {}
  }
  
  public final PlayStore.PlayStoreClickEvent obtainPlayStoreClickEvent()
  {
    return (PlayStore.PlayStoreClickEvent)this.mCachePlayStoreClick.obtain();
  }
  
  public final PlayStore.PlayStoreImpressionEvent obtainPlayStoreImpressionEvent()
  {
    return (PlayStore.PlayStoreImpressionEvent)this.mCachePlayStoreImpression.obtain();
  }
  
  public final PlayStore.PlayStoreLogEvent obtainPlayStoreLogEvent()
  {
    return (PlayStore.PlayStoreLogEvent)this.mCachePlayStoreLogEvent.obtain();
  }
  
  public final PlayStore.PlayStoreUiElement obtainPlayStoreUiElement()
  {
    return (PlayStore.PlayStoreUiElement)this.mCachePlayStoreUIElement.obtain();
  }
  
  public final void recycle(PlayStore.PlayStoreUiElement paramPlayStoreUiElement)
  {
    PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement = paramPlayStoreUiElement.child;
    int i = arrayOfPlayStoreUiElement.length;
    for (int j = 0; j < i; j++) {
      recycle(arrayOfPlayStoreUiElement[j]);
    }
    paramPlayStoreUiElement.clear();
    this.mCachePlayStoreUIElement.recycle(paramPlayStoreUiElement);
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
          FinskyLog.wtf("Exception from mClazz.newInstance ", new Object[] { localException });
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
 * Qualified Name:     com.google.android.finsky.analytics.EventProtoCache
 * JD-Core Version:    0.7.0.1
 */