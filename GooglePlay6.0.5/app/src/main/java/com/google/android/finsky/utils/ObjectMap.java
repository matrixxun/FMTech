package com.google.android.finsky.utils;

import android.os.Bundle;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectMap
{
  public static final ObjectMap EMPTY = new ObjectMap(Collections.emptyMap(), Bundle.EMPTY);
  public Bundle mBundle;
  public Map<String, Object> mHashmap;
  
  public ObjectMap()
  {
    this(new HashMap(), new Bundle());
  }
  
  private ObjectMap(Map paramMap, Bundle paramBundle)
  {
    this.mHashmap = paramMap;
    this.mBundle = paramBundle;
  }
  
  public final boolean containsKey(String paramString)
  {
    return (this.mHashmap.containsKey(paramString)) || (this.mBundle.containsKey(paramString));
  }
  
  public final Object get(String paramString)
  {
    if (this.mHashmap.containsKey(paramString)) {
      return this.mHashmap.get(paramString);
    }
    return this.mBundle.get(paramString);
  }
  
  public final int getInt(String paramString)
  {
    if (this.mHashmap.containsKey(paramString)) {
      return ((Integer)this.mHashmap.get(paramString)).intValue();
    }
    return this.mBundle.getInt(paramString);
  }
  
  public final <T> List<T> getList(String paramString)
  {
    if (this.mHashmap.containsKey(paramString)) {
      return (List)this.mHashmap.get(paramString);
    }
    return (List)this.mBundle.getParcelable(paramString);
  }
  
  public final void put(String paramString, Object paramObject)
  {
    this.mHashmap.put(paramString, paramObject);
  }
  
  public final void putInt(String paramString, int paramInt)
  {
    this.mHashmap.put(paramString, Integer.valueOf(paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ObjectMap
 * JD-Core Version:    0.7.0.1
 */