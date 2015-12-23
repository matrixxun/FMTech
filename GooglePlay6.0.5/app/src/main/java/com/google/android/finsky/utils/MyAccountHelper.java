package com.google.android.finsky.utils;

public final class MyAccountHelper
{
  static long sLastMutationTimeMs;
  
  public static boolean hasMutationOccurredSince(long paramLong)
  {
    return paramLong < sLastMutationTimeMs;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.MyAccountHelper
 * JD-Core Version:    0.7.0.1
 */