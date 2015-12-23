package com.google.android.finsky.utils;

import android.os.HandlerThread;
import android.os.Process;
import java.util.concurrent.ThreadFactory;

public final class BackgroundThreadFactory
  implements ThreadFactory
{
  public static HandlerThread createHandlerThread(String paramString)
  {
    return new HandlerThread(paramString, 10);
  }
  
  public static Thread createThread(Runnable paramRunnable)
  {
    return new Thread(wrap(paramRunnable));
  }
  
  public static Thread createThread(String paramString, Runnable paramRunnable)
  {
    return new Thread(wrap(paramRunnable), paramString);
  }
  
  private static Runnable wrap(Runnable paramRunnable)
  {
    new Runnable()
    {
      public final void run()
      {
        Process.setThreadPriority(10);
        this.val$r.run();
      }
    };
  }
  
  public final Thread newThread(Runnable paramRunnable)
  {
    return createThread(paramRunnable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.BackgroundThreadFactory
 * JD-Core Version:    0.7.0.1
 */