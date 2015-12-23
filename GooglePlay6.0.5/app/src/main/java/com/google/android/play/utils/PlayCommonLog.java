package com.google.android.play.utils;

import android.util.Log;
import java.util.IllegalFormatException;
import java.util.Locale;

public class PlayCommonLog
{
  public static final boolean DEBUG = Log.isLoggable("PlayCommon", 2);
  private static String TAG = "PlayCommon";
  private static long sStartTime = System.currentTimeMillis();
  
  private static String buildMessage(String paramString, Object... paramVarArgs)
  {
    Object localObject;
    StackTraceElement[] arrayOfStackTraceElement;
    String str1;
    if (paramVarArgs == null)
    {
      localObject = paramString;
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      str1 = "<unknown>";
    }
    label236:
    for (int i = 2;; i++)
    {
      for (;;)
      {
        if (i < arrayOfStackTraceElement.length)
        {
          String str2 = arrayOfStackTraceElement[i].getClassName();
          if (str2.equals(PlayCommonLog.class.getName())) {
            break label236;
          }
          String str3 = str2.substring(1 + str2.lastIndexOf('.'));
          String str4 = str3.substring(1 + str3.lastIndexOf('$'));
          str1 = str4 + "." + arrayOfStackTraceElement[i].getMethodName();
        }
        Locale localLocale = Locale.US;
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = Long.valueOf(Thread.currentThread().getId());
        arrayOfObject2[1] = str1;
        arrayOfObject2[2] = localObject;
        return String.format(localLocale, "[%d] %s: %s", arrayOfObject2);
        try
        {
          String str5 = String.format(Locale.US, paramString, paramVarArgs);
          localObject = str5;
        }
        catch (IllegalFormatException localIllegalFormatException)
        {
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = paramString;
          arrayOfObject1[1] = Integer.valueOf(paramVarArgs.length);
          wtf("IllegalFormatException: formatString='%s' numArgs=%d", arrayOfObject1);
          localObject = paramString + " (An error occurred while formatting the message.)";
        }
      }
      break;
    }
  }
  
  public static void d(String paramString, Object... paramVarArgs)
  {
    Log.d(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void e(String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void logTiming(String paramString, Object... paramVarArgs)
  {
    if (!Log.isLoggable(TAG, 2)) {
      return;
    }
    String str = String.format(Locale.US, paramString, paramVarArgs);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Long.valueOf(System.currentTimeMillis() - sStartTime);
    arrayOfObject[1] = str;
    v("%4dms: %s", arrayOfObject);
  }
  
  public static void v(String paramString, Object... paramVarArgs)
  {
    Log.v(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void w(String paramString, Object... paramVarArgs)
  {
    Log.w(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void wtf(String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs));
    Log.wtf(TAG, buildMessage(paramString, paramVarArgs));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.PlayCommonLog
 * JD-Core Version:    0.7.0.1
 */