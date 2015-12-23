package com.google.android.libraries.bind.async;

import android.os.Handler;
import android.os.SystemClock;

public final class DelayedRunnable
{
  final Runnable baseRunnable;
  private final Handler handler;
  final Object lock;
  long scheduledTime;
  private final Runnable wrapperRunnable;
  
  public DelayedRunnable(Handler paramHandler, Runnable paramRunnable)
  {
    this.baseRunnable = paramRunnable;
    this.handler = paramHandler;
    this.wrapperRunnable = new Runnable()
    {
      public final void run()
      {
        synchronized (DelayedRunnable.this.lock)
        {
          DelayedRunnable.this.scheduledTime = -1L;
          DelayedRunnable.this.baseRunnable.run();
          return;
        }
      }
    };
    this.lock = this.wrapperRunnable;
    this.scheduledTime = -1L;
  }
  
  private void rescheduleAtTime(long paramLong)
  {
    this.scheduledTime = paramLong;
    this.handler.removeCallbacks(this.wrapperRunnable);
    if (!this.handler.postAtTime(this.wrapperRunnable, this.scheduledTime)) {
      this.scheduledTime = -1L;
    }
  }
  
  public final boolean postDelayed$25666f8(long paramLong)
  {
    long l = 500L + SystemClock.uptimeMillis();
    for (;;)
    {
      synchronized (this.lock)
      {
        if (this.scheduledTime > 0L)
        {
          i = 1;
          if (i == 0)
          {
            rescheduleAtTime(l);
            return false;
          }
          if (l > this.scheduledTime) {
            rescheduleAtTime(l);
          }
          return true;
        }
      }
      int i = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.async.DelayedRunnable
 * JD-Core Version:    0.7.0.1
 */