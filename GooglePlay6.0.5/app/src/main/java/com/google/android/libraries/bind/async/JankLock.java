package com.google.android.libraries.bind.async;

import android.os.Build.VERSION;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class JankLock
  implements Executor
{
  public static final boolean DISABLED;
  public static final JankLock global;
  public boolean isPaused;
  public final ReentrantLock pauseLock = new ReentrantLock();
  public final DelayedRunnable resumeRunnable = new DelayedRunnable(AsyncUtil.mainThreadHandler(), new Runnable()
  {
    public final void run()
    {
      JankLock localJankLock = JankLock.this;
      if (!JankLock.DISABLED)
      {
        AsyncUtil.checkMainThread();
        localJankLock.pauseLock.lock();
      }
      try
      {
        localJankLock.isPaused = false;
        localJankLock.unpaused.signalAll();
        return;
      }
      finally
      {
        localJankLock.pauseLock.unlock();
      }
    }
  });
  final Condition unpaused = this.pauseLock.newCondition();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      DISABLED = bool;
      global = new JankLock();
      return;
    }
  }
  
  public final void execute(final Runnable paramRunnable)
  {
    final Executor localExecutor = AsyncUtil.mainThreadExecutor();
    final Queue localQueue = Queues.BIND_CPU;
    Runnable local2 = new Runnable()
    {
      final Runnable deliveringRunnable = new Runnable()
      {
        public final void run()
        {
          if (JankLock.this.isPaused())
          {
            JankLock.2.this.val$blockingQueue.execute(JankLock.2.this.outerRunnable);
            return;
          }
          JankLock.2.this.val$runnable.run();
        }
      };
      final Runnable outerRunnable = this;
      
      public final void run()
      {
        localExecutor.execute(this.deliveringRunnable);
      }
    };
    if (!isPaused())
    {
      local2.run();
      return;
    }
    localQueue.execute(local2);
  }
  
  public final boolean isPaused()
  {
    if (DISABLED) {
      return false;
    }
    this.pauseLock.lock();
    try
    {
      boolean bool = this.isPaused;
      return bool;
    }
    finally
    {
      this.pauseLock.unlock();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.async.JankLock
 * JD-Core Version:    0.7.0.1
 */