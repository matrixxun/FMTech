package com.google.android.finsky.utils;

public final class TvIntentUtils
{
  public static Class<?> getClass(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      FinskyLog.e("Could not find " + paramString, new Object[0]);
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.TvIntentUtils
 * JD-Core Version:    0.7.0.1
 */