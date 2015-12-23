package org.apache.log4j;

import com.google.android.finsky.utils.FinskyLog;

public final class Logger
{
  private final String mTag;
  
  private Logger(Class<?> paramClass)
  {
    this.mTag = paramClass.getSimpleName();
  }
  
  public static Logger getLogger(Class<?> paramClass)
  {
    return new Logger(paramClass);
  }
  
  public final void debug(Object paramObject)
  {
    if (FinskyLog.DEBUG) {
      FinskyLog.v(this.mTag + ": " + paramObject, new Object[0]);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.apache.log4j.Logger
 * JD-Core Version:    0.7.0.1
 */