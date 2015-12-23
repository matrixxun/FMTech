package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.Context;

public abstract class GservicesValue<T>
{
  static ContentResolver sContentResolver = null;
  protected final T mDefaultValue;
  protected final String mKey;
  
  protected GservicesValue(String paramString, T paramT)
  {
    this.mKey = paramString;
    this.mDefaultValue = paramT;
  }
  
  public static void init(Context paramContext)
  {
    sContentResolver = paramContext.getContentResolver();
  }
  
  public static GservicesValue<String> partnerSetting(String paramString1, String paramString2)
  {
    new GservicesValue(paramString1, paramString2) {};
  }
  
  public static GservicesValue<Integer> value(String paramString, Integer paramInteger)
  {
    new GservicesValue(paramString, paramInteger) {};
  }
  
  public static GservicesValue<Long> value(String paramString, Long paramLong)
  {
    new GservicesValue(paramString, paramLong) {};
  }
  
  public static GservicesValue<String> value(String paramString1, String paramString2)
  {
    new GservicesValue(paramString1, paramString2) {};
  }
  
  public static GservicesValue<Boolean> value(String paramString, boolean paramBoolean)
  {
    new GservicesValue(paramString, Boolean.valueOf(paramBoolean)) {};
  }
  
  public final T get()
  {
    return retrieve$9543ced();
  }
  
  protected abstract T retrieve$9543ced();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gsf.GservicesValue
 * JD-Core Version:    0.7.0.1
 */