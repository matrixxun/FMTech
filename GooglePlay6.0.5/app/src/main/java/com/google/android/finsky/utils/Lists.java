package com.google.android.finsky.utils;

import java.util.ArrayList;
import java.util.Collection;

public final class Lists
{
  public static <T> ArrayList<T> newArrayList(int paramInt)
  {
    return new ArrayList(paramInt);
  }
  
  public static <T> ArrayList<T> newArrayList(Collection<T> paramCollection)
  {
    return new ArrayList(paramCollection);
  }
  
  public static <T> ArrayList<T> newArrayList(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(paramVarArgs[j]);
    }
    return localArrayList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Lists
 * JD-Core Version:    0.7.0.1
 */