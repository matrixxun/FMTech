package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;

public final class NoCache
  implements Cache
{
  public final void clear() {}
  
  public final Cache.Entry get(String paramString)
  {
    return null;
  }
  
  public final void initialize() {}
  
  public final void invalidate(String paramString, boolean paramBoolean) {}
  
  public final void put(String paramString, Cache.Entry paramEntry) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.NoCache
 * JD-Core Version:    0.7.0.1
 */