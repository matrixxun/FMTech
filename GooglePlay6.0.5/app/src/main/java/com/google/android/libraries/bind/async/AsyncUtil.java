package com.google.android.libraries.bind.async;

import android.os.Handler;
import android.os.Looper;
import com.google.android.libraries.bind.util.Util;
import java.util.concurrent.Executor;

public final class AsyncUtil
{
  private static Executor immediateExecutor = new Executor()
  {
    public final void execute(Runnable paramAnonymousRunnable)
    {
      paramAnonymousRunnable.run();
    }
  };
  private static Executor mainThreadExecutor;
  private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
  
  static
  {
    mainThreadExecutor = new Executor()
    {
      public final void execute(Runnable paramAnonymousRunnable)
      {
        AsyncUtil.mainThreadHandler.post(paramAnonymousRunnable);
      }
    };
  }
  
  public static void checkMainThread()
  {
    Util.checkPrecondition(isMainThread(), "Not on the main thread");
  }
  
  public static Executor immediateExecutor()
  {
    return immediateExecutor;
  }
  
  public static boolean isMainThread()
  {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }
  
  public static Executor mainThreadExecutor()
  {
    return mainThreadExecutor;
  }
  
  public static Handler mainThreadHandler()
  {
    return mainThreadHandler;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.async.AsyncUtil
 * JD-Core Version:    0.7.0.1
 */