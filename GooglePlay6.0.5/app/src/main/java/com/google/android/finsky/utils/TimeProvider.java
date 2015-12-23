package com.google.android.finsky.utils;

public abstract interface TimeProvider
{
  public abstract long currentTimeMillis();
  
  public static final class SystemTimeProvider
    implements TimeProvider
  {
    public final long currentTimeMillis()
    {
      return System.currentTimeMillis();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.TimeProvider
 * JD-Core Version:    0.7.0.1
 */