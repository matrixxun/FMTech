package com.google.android.libraries.bind.async;

import android.os.Process;
import com.google.android.libraries.bind.logging.Logd;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class Queue
  implements Executor
{
  private static final Logd LOGD = Logd.get(Queues.class);
  protected static final List<Queue> queues = new ArrayList();
  protected Executor executor;
  public final Executor fallbackIfMain = new Executor()
  {
    public final void execute(Runnable paramAnonymousRunnable)
    {
      if (!AsyncUtil.isMainThread())
      {
        paramAnonymousRunnable.run();
        return;
      }
      Queue.this.execute(paramAnonymousRunnable);
    }
  };
  private final String name;
  public final int poolSize;
  protected final ThreadGroup threadGroup;
  
  public Queue(String paramString)
  {
    this.name = paramString;
    queues.add(this);
    this.poolSize = 2;
    this.threadGroup = new ThreadGroup(paramString);
    ThreadFactory local3 = new ThreadFactory()
    {
      private final AtomicInteger createdCount = new AtomicInteger(1);
      
      public final Thread newThread(final Runnable paramAnonymousRunnable)
      {
        Runnable local1 = new Runnable()
        {
          public final void run()
          {
            Process.setThreadPriority(10);
            paramAnonymousRunnable.run();
          }
        };
        String str = Queue.this + " #" + this.createdCount.getAndIncrement();
        Thread localThread = new Thread(Queue.this.threadGroup, local1, str);
        localThread.setPriority(1);
        return localThread;
      }
    };
    this.executor = new ThreadPoolExecutor(this.poolSize, this.poolSize, TimeUnit.SECONDS, new LinkedBlockingQueue(), local3, true)
    {
      protected final void beforeExecute(Thread paramAnonymousThread, Runnable paramAnonymousRunnable)
      {
        super.beforeExecute(paramAnonymousThread, paramAnonymousRunnable);
        JankLock localJankLock;
        if (this.val$jankLocked)
        {
          localJankLock = JankLock.global;
          if ((!JankLock.DISABLED) && (!AsyncUtil.isMainThread())) {}
        }
        else
        {
          return;
        }
        localJankLock.pauseLock.lock();
        try
        {
          if (localJankLock.isPaused) {
            localJankLock.unpaused.awaitUninterruptibly();
          }
          return;
        }
        finally
        {
          localJankLock.pauseLock.unlock();
        }
      }
    };
  }
  
  public Queue(String paramString, Executor paramExecutor)
  {
    this.name = paramString;
    queues.add(this);
    this.poolSize = 1;
    this.threadGroup = new ThreadGroup(paramString);
    this.executor = paramExecutor;
  }
  
  public final void execute(Runnable paramRunnable)
  {
    this.executor.execute(paramRunnable);
  }
  
  public final String toString()
  {
    return this.name;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.async.Queue
 * JD-Core Version:    0.7.0.1
 */