package com.google.android.finsky.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class Sets
{
  public static <T> HashSet<T> newHashSet(Collection<T> paramCollection)
  {
    return new HashSet(paramCollection);
  }
  
  public static <T> Set<T> newHashSet(T... paramVarArgs)
  {
    HashSet localHashSet = new HashSet(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      localHashSet.add(paramVarArgs[j]);
    }
    return localHashSet;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Sets
 * JD-Core Version:    0.7.0.1
 */