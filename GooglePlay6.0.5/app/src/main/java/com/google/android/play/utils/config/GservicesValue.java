package com.google.android.play.utils.config;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.gsf.GoogleSettingsContract.Partner;
import com.google.android.gsf.Gservices;

public abstract class GservicesValue<T>
{
  static GservicesReader sGservicesReader = null;
  public final String mKey;
  
  private GservicesValue(String paramString)
  {
    this.mKey = paramString;
  }
  
  public static void init(Context paramContext, String[] paramArrayOfString)
  {
    sGservicesReader = new GservicesReaderImpl(paramContext.getContentResolver(), paramArrayOfString);
  }
  
  public static GservicesValue<String> partnerSetting(final String paramString1, final String paramString2)
  {
    new GservicesValue(paramString1, paramString1) {};
  }
  
  public static GservicesValue<Boolean> value(final String paramString, final Boolean paramBoolean)
  {
    new GservicesValue(paramString, paramString) {};
  }
  
  public static GservicesValue<Float> value(final String paramString, final Float paramFloat)
  {
    new GservicesValue(paramString, paramString) {};
  }
  
  public static GservicesValue<Integer> value(final String paramString, final Integer paramInteger)
  {
    new GservicesValue(paramString, paramString) {};
  }
  
  public static GservicesValue<Long> value(final String paramString, final Long paramLong)
  {
    new GservicesValue(paramString, paramString) {};
  }
  
  public static GservicesValue<String> value(final String paramString1, final String paramString2)
  {
    new GservicesValue(paramString1, paramString1) {};
  }
  
  public final T get()
  {
    return retrieve();
  }
  
  protected abstract T retrieve();
  
  private static abstract interface GservicesReader
  {
    public abstract Boolean getBoolean(String paramString, Boolean paramBoolean);
    
    public abstract Float getFloat(String paramString, Float paramFloat);
    
    public abstract Integer getInt(String paramString, Integer paramInteger);
    
    public abstract Long getLong(String paramString, Long paramLong);
    
    public abstract String getPartnerString(String paramString1, String paramString2);
    
    public abstract String getString(String paramString1, String paramString2);
  }
  
  private static final class GservicesReaderImpl
    implements GservicesValue.GservicesReader
  {
    private final ContentResolver mContentResolver;
    
    public GservicesReaderImpl(ContentResolver paramContentResolver, String[] paramArrayOfString)
    {
      this.mContentResolver = paramContentResolver;
      Gservices.bulkCacheByPrefix(this.mContentResolver, paramArrayOfString);
    }
    
    public final Boolean getBoolean(String paramString, Boolean paramBoolean)
    {
      return Boolean.valueOf(Gservices.getBoolean(this.mContentResolver, paramString, paramBoolean.booleanValue()));
    }
    
    public final Float getFloat(String paramString, Float paramFloat)
    {
      String str = Gservices.getString(this.mContentResolver, paramString, null);
      if (str != null) {}
      try
      {
        Float localFloat = Float.valueOf(Float.parseFloat(str));
        paramFloat = localFloat;
        return paramFloat;
      }
      catch (NumberFormatException localNumberFormatException) {}
      return paramFloat;
    }
    
    public final Integer getInt(String paramString, Integer paramInteger)
    {
      return Integer.valueOf(Gservices.getInt(this.mContentResolver, paramString, paramInteger.intValue()));
    }
    
    public final Long getLong(String paramString, Long paramLong)
    {
      return Long.valueOf(Gservices.getLong(this.mContentResolver, paramString, paramLong.longValue()));
    }
    
    public final String getPartnerString(String paramString1, String paramString2)
    {
      return GoogleSettingsContract.Partner.getString(this.mContentResolver, paramString1, paramString2);
    }
    
    public final String getString(String paramString1, String paramString2)
    {
      return Gservices.getString(this.mContentResolver, paramString1, paramString2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.config.GservicesValue
 * JD-Core Version:    0.7.0.1
 */