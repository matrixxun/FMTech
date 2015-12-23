package com.google.android.play.utils;

public final class Assertions
{
  public static void checkArgument(boolean paramBoolean, String paramString)
    throws IllegalArgumentException
  {
    if (!paramBoolean) {
      throw new IllegalArgumentException(paramString);
    }
  }
  
  public static void checkState(boolean paramBoolean, String paramString)
    throws IllegalStateException
  {
    if (!paramBoolean) {
      throw new IllegalStateException(paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.Assertions
 * JD-Core Version:    0.7.0.1
 */