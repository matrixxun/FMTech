package com.google.android.finsky.utils.persistence;

import java.util.Map;

public abstract interface KeyValueStore
{
  public abstract void delete(String paramString);
  
  public abstract Map<String, Map<String, String>> fetchAll();
  
  public abstract void put(String paramString, Map<String, String> paramMap);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.persistence.KeyValueStore
 * JD-Core Version:    0.7.0.1
 */