package com.google.android.finsky.utils;

import com.google.android.finsky.FinskyApp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public final class DateUtils
{
  private static final java.text.DateFormat DEVICE_DISPLAY_FORMAT;
  private static final java.text.DateFormat DEVICE_DISPLAY_FORMAT_SHORT;
  private static final java.text.DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final java.text.DateFormat UTC_DISPLAY_FORMAT_SHORT;
  
  static
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    DEVICE_DISPLAY_FORMAT = android.text.format.DateFormat.getLongDateFormat(localFinskyApp);
    DEVICE_DISPLAY_FORMAT_SHORT = android.text.format.DateFormat.getDateFormat(localFinskyApp);
    java.text.DateFormat localDateFormat = android.text.format.DateFormat.getDateFormat(localFinskyApp);
    UTC_DISPLAY_FORMAT_SHORT = localDateFormat;
    localDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }
  
  public static String formatDate(Date paramDate)
  {
    try
    {
      String str = DEVICE_DISPLAY_FORMAT.format(paramDate);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static String formatDate(Date paramDate, String paramString)
  {
    return new SimpleDateFormat(paramString).format(paramDate);
  }
  
  public static String formatIso8601Date(String paramString)
    throws ParseException
  {
    try
    {
      Date localDate = ISO8601_DATE_FORMAT.parse(paramString);
      String str = DEVICE_DISPLAY_FORMAT.format(localDate);
      paramString = str;
    }
    catch (ParseException localParseException)
    {
      while (Pattern.matches("^\\d\\d\\d\\d$", paramString)) {}
      throw localParseException;
    }
    finally {}
    return paramString;
  }
  
  public static String formatShortDisplayDate(long paramLong)
  {
    try
    {
      String str = DEVICE_DISPLAY_FORMAT_SHORT.format(new Date(paramLong));
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static String formatShortDisplayDateUtc(long paramLong)
  {
    try
    {
      String str = UTC_DISPLAY_FORMAT_SHORT.format(new Date(paramLong));
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static Date parseDate(String paramString1, String paramString2)
  {
    try
    {
      Date localDate = new SimpleDateFormat(paramString2).parse(paramString1);
      return localDate;
    }
    catch (ParseException localParseException)
    {
      FinskyLog.wtf("Cannot parse date %s", new Object[] { paramString1 });
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DateUtils
 * JD-Core Version:    0.7.0.1
 */