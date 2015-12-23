package com.google.android.play.image;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;

public final class TentativeGcRunner
{
  int mAllocatedSinceLastRun;
  boolean mEnabled;
  Runnable mGcRunnable = new Runnable()
  {
    public final void run() {}
  };
  Handler mHandler;
  private HandlerThread mHandlerThread;
  
  public TentativeGcRunner()
  {
    int i = Runtime.getRuntime().availableProcessors();
    if ((Build.VERSION.SDK_INT >= 11) && (i > 1) && (((Boolean)PlayG.tentativeGcRunnerEnabled.get()).booleanValue())) {}
    for (boolean bool = true;; bool = false)
    {
      this.mEnabled = bool;
      if (this.mEnabled)
      {
        this.mHandlerThread = new HandlerThread("tentative-gc-runner", 10);
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
      }
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.TentativeGcRunner
 * JD-Core Version:    0.7.0.1
 */