package com.google.android.libraries.bind.logging;

import java.util.HashMap;
import java.util.Map;

public final class Logd
{
  private static boolean enableAll;
  protected static Map<String, Logd> existingClassLoggers = new HashMap();
  protected static LogHandler sysLogHandler = new SystemLogHandler();
  protected boolean enabled;
  protected String tag;
  
  private Logd(String paramString)
  {
    this.tag = paramString;
  }
  
  public static Logd get(Class<?> paramClass)
  {
    String str = paramClass.getSimpleName();
    if (existingClassLoggers.containsKey(str)) {
      return (Logd)existingClassLoggers.get(str);
    }
    Logd localLogd = new Logd(str);
    existingClassLoggers.put(str, localLogd);
    return localLogd;
  }
  
  private static String safeFormat(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    if ((paramString == null) || (paramVarArgs == null) || (paramVarArgs.length == 0)) {}
    for (String str = paramString;; str = String.format(paramString, paramVarArgs))
    {
      if (str == null) {
        str = "";
      }
      return str;
    }
  }
  
  public final void d(String paramString, Object... paramVarArgs)
  {
    if (isEnabled()) {
      sysLogHandler.log(3, this.tag, safeFormat(null, paramString, paramVarArgs));
    }
  }
  
  public final void e(String paramString, Object... paramVarArgs)
  {
    sysLogHandler.log(6, this.tag, safeFormat(null, paramString, paramVarArgs));
  }
  
  public final void i(String paramString, Object... paramVarArgs)
  {
    if (isEnabled()) {
      sysLogHandler.log(4, this.tag, safeFormat(null, paramString, paramVarArgs));
    }
  }
  
  public final boolean isEnabled()
  {
    return (this.enabled) || (enableAll);
  }
  
  public final void v(String paramString, Object... paramVarArgs)
  {
    if (isEnabled()) {
      sysLogHandler.log(2, this.tag, safeFormat(null, paramString, paramVarArgs));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.logging.Logd
 * JD-Core Version:    0.7.0.1
 */