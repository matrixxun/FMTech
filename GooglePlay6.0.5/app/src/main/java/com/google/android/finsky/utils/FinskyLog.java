package com.google.android.finsky.utils;

import android.util.Log;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import java.util.IllegalFormatException;
import java.util.Locale;

public class FinskyLog
{
  public static final boolean DEBUG = Log.isLoggable("Finsky", 2);
  private static String TAG = "Finsky";
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
    label268:
    for (int i = 2;; i++)
    {
      for (;;)
      {
        if (i < arrayOfStackTraceElement.length)
        {
          String str2 = arrayOfStackTraceElement[i].getClassName();
          if (str2.equals(FinskyLog.class.getName())) {
            break label268;
          }
          String str3 = str2.substring(1 + str2.lastIndexOf('.'));
          int j = 1 + str3.lastIndexOf('$');
          if (j > 0)
          {
            int k = str3.charAt(j);
            if ((k < 48) || (k > 57)) {
              str3 = str3.substring(j);
            }
          }
          str1 = str3 + "." + arrayOfStackTraceElement[i].getMethodName();
        }
        Locale localLocale = Locale.US;
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = Long.valueOf(Thread.currentThread().getId());
        arrayOfObject2[1] = str1;
        arrayOfObject2[2] = localObject;
        return String.format(localLocale, "[%d] %s: %s", arrayOfObject2);
        try
        {
          String str4 = String.format(Locale.US, paramString, paramVarArgs);
          localObject = str4;
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
  
  public static void e(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs), paramThrowable);
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
  
  public static String scrubPii(String paramString)
  {
    if ((paramString == null) || (((Boolean)G.debugOptionsEnabled.get()).booleanValue())) {
      return paramString;
    }
    return "[" + Sha1Util.secureHash(paramString.getBytes()) + "]";
  }
  
  public static void startTiming()
  {
    sStartTime = System.currentTimeMillis();
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
    Log.e(TAG, buildMessage(paramString, paramVarArgs), new Exception());
    Log.wtf(TAG, buildMessage(paramString, paramVarArgs));
  }
  
  public static void wtf(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    Log.e(TAG, buildMessage(paramString, paramVarArgs), paramThrowable);
    Log.wtf(TAG, buildMessage(paramString, paramVarArgs), paramThrowable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.FinskyLog
 * JD-Core Version:    0.7.0.1
 */