package com.google.android.libraries.bind.logging;

import android.util.Log;

public final class SystemLogHandler
  implements LogHandler
{
  public final void log(int paramInt, String paramString1, String paramString2)
  {
    Log.println(paramInt, paramString1, paramString2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.logging.SystemLogHandler
 * JD-Core Version:    0.7.0.1
 */