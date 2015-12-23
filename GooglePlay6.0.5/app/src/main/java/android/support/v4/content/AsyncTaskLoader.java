package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.os.OperationCanceledException;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

public abstract class AsyncTaskLoader<D>
  extends Loader<D>
{
  volatile AsyncTaskLoader<D>.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.LoadTask mCancellingTask;
  private final Executor mExecutor;
  Handler mHandler;
  long mLastLoadCompleteTime = -10000L;
  volatile AsyncTaskLoader<D>.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.LoadTask mTask;
  long mUpdateThrottle;
  
  public AsyncTaskLoader(Context paramContext)
  {
    this(paramContext, ModernAsyncTask.THREAD_POOL_EXECUTOR);
  }
  
  private AsyncTaskLoader(Context paramContext, Executor paramExecutor)
  {
    super(paramContext);
    this.mExecutor = paramExecutor;
  }
  
  public void cancelLoadInBackground() {}
  
  final void dispatchOnCancelled(AsyncTaskLoader<D>.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader.LoadTask paramAsyncTaskLoader, D paramD)
  {
    onCanceled(paramD);
    if (this.mCancellingTask == paramAsyncTaskLoader)
    {
      if (this.mProcessingChange) {
        this.mContentChanged = true;
      }
      this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
      this.mCancellingTask = null;
      if (this.mOnLoadCanceledListener != null) {
        this.mOnLoadCanceledListener.onLoadCanceled$5dda1f52();
      }
      executePendingTask();
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    if (this.mTask != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTask=");
      paramPrintWriter.print(this.mTask);
      paramPrintWriter.print(" waiting=");
      paramPrintWriter.println(this.mTask.waiting);
    }
    if (this.mCancellingTask != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mCancellingTask=");
      paramPrintWriter.print(this.mCancellingTask);
      paramPrintWriter.print(" waiting=");
      paramPrintWriter.println(this.mCancellingTask.waiting);
    }
    if (this.mUpdateThrottle != 0L)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mUpdateThrottle=");
      TimeUtils.formatDuration(this.mUpdateThrottle, paramPrintWriter);
      paramPrintWriter.print(" mLastLoadCompleteTime=");
      TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), paramPrintWriter);
      paramPrintWriter.println();
    }
  }
  
  final void executePendingTask()
  {
    if ((this.mCancellingTask == null) && (this.mTask != null))
    {
      if (this.mTask.waiting)
      {
        this.mTask.waiting = false;
        this.mHandler.removeCallbacks(this.mTask);
      }
      if ((this.mUpdateThrottle > 0L) && (SystemClock.uptimeMillis() < this.mLastLoadCompleteTime + this.mUpdateThrottle))
      {
        this.mTask.waiting = true;
        this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
      }
    }
    else
    {
      return;
    }
    LoadTask localLoadTask = this.mTask;
    Executor localExecutor = this.mExecutor;
    if (localLoadTask.mStatus != ModernAsyncTask.Status.PENDING) {}
    switch (ModernAsyncTask.4.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[localLoadTask.mStatus.ordinal()])
    {
    default: 
      localLoadTask.mStatus = ModernAsyncTask.Status.RUNNING;
      localLoadTask.mWorker.mParams = null;
      localExecutor.execute(localLoadTask.mFuture);
      return;
    case 1: 
      throw new IllegalStateException("Cannot execute task: the task is already running.");
    }
    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
  }
  
  public abstract D loadInBackground();
  
  protected final boolean onCancelLoad()
  {
    if (this.mTask != null)
    {
      if (this.mCancellingTask != null)
      {
        if (this.mTask.waiting)
        {
          this.mTask.waiting = false;
          this.mHandler.removeCallbacks(this.mTask);
        }
        this.mTask = null;
      }
    }
    else {
      return false;
    }
    if (this.mTask.waiting)
    {
      this.mTask.waiting = false;
      this.mHandler.removeCallbacks(this.mTask);
      this.mTask = null;
      return false;
    }
    boolean bool = this.mTask.mFuture.cancel(false);
    if (bool)
    {
      this.mCancellingTask = this.mTask;
      cancelLoadInBackground();
    }
    this.mTask = null;
    return bool;
  }
  
  public void onCanceled(D paramD) {}
  
  protected final void onForceLoad()
  {
    super.onForceLoad();
    cancelLoad();
    this.mTask = new LoadTask();
    executePendingTask();
  }
  
  final class LoadTask
    extends ModernAsyncTask<Void, Void, D>
    implements Runnable
  {
    private final CountDownLatch mDone = new CountDownLatch(1);
    boolean waiting;
    
    LoadTask() {}
    
    private D doInBackground$532ebdd5()
    {
      try
      {
        Object localObject = AsyncTaskLoader.this.loadInBackground();
        return localObject;
      }
      catch (OperationCanceledException localOperationCanceledException)
      {
        if (!this.mFuture.isCancelled()) {
          throw localOperationCanceledException;
        }
      }
      return null;
    }
    
    protected final void onCancelled(D paramD)
    {
      try
      {
        AsyncTaskLoader.this.dispatchOnCancelled(this, paramD);
        return;
      }
      finally
      {
        this.mDone.countDown();
      }
    }
    
    protected final void onPostExecute(D paramD)
    {
      for (;;)
      {
        AsyncTaskLoader localAsyncTaskLoader;
        try
        {
          localAsyncTaskLoader = AsyncTaskLoader.this;
          if (localAsyncTaskLoader.mTask != this)
          {
            localAsyncTaskLoader.dispatchOnCancelled(this, paramD);
            return;
          }
          if (localAsyncTaskLoader.mAbandoned)
          {
            localAsyncTaskLoader.onCanceled(paramD);
            continue;
          }
          localAsyncTaskLoader.mProcessingChange = false;
        }
        finally
        {
          this.mDone.countDown();
        }
        localAsyncTaskLoader.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        localAsyncTaskLoader.mTask = null;
        localAsyncTaskLoader.deliverResult(paramD);
      }
    }
    
    public final void run()
    {
      this.waiting = false;
      AsyncTaskLoader.this.executePendingTask();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.content.AsyncTaskLoader
 * JD-Core Version:    0.7.0.1
 */