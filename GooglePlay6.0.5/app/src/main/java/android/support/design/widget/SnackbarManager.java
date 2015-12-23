package android.support.design.widget;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

final class SnackbarManager
{
  private static SnackbarManager sSnackbarManager;
  SnackbarRecord mCurrentSnackbar;
  final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback()
  {
    public final boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return false;
      }
      SnackbarManager localSnackbarManager = SnackbarManager.this;
      SnackbarManager.SnackbarRecord localSnackbarRecord = (SnackbarManager.SnackbarRecord)paramAnonymousMessage.obj;
      synchronized (localSnackbarManager.mLock)
      {
        if ((localSnackbarManager.mCurrentSnackbar == localSnackbarRecord) || (localSnackbarManager.mNextSnackbar == localSnackbarRecord)) {
          SnackbarManager.cancelSnackbarLocked(localSnackbarRecord, 2);
        }
        return true;
      }
    }
  });
  final Object mLock = new Object();
  SnackbarRecord mNextSnackbar;
  
  static boolean cancelSnackbarLocked(SnackbarRecord paramSnackbarRecord, int paramInt)
  {
    Callback localCallback = (Callback)paramSnackbarRecord.callback.get();
    if (localCallback != null)
    {
      localCallback.dismiss(paramInt);
      return true;
    }
    return false;
  }
  
  static SnackbarManager getInstance()
  {
    if (sSnackbarManager == null) {
      sSnackbarManager = new SnackbarManager();
    }
    return sSnackbarManager;
  }
  
  public final void cancelTimeout(Callback paramCallback)
  {
    synchronized (this.mLock)
    {
      if (isCurrentSnackbarLocked(paramCallback)) {
        this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
      }
      return;
    }
  }
  
  public final boolean isCurrentOrNext(Callback paramCallback)
  {
    for (;;)
    {
      synchronized (this.mLock)
      {
        if (!isCurrentSnackbarLocked(paramCallback))
        {
          if (!isNextSnackbarLocked(paramCallback)) {
            break label42;
          }
          break label36;
          return bool;
        }
      }
      label36:
      boolean bool = true;
      continue;
      label42:
      bool = false;
    }
  }
  
  final boolean isCurrentSnackbarLocked(Callback paramCallback)
  {
    return (this.mCurrentSnackbar != null) && (this.mCurrentSnackbar.isSnackbar(paramCallback));
  }
  
  final boolean isNextSnackbarLocked(Callback paramCallback)
  {
    return (this.mNextSnackbar != null) && (this.mNextSnackbar.isSnackbar(paramCallback));
  }
  
  public final void onShown(Callback paramCallback)
  {
    synchronized (this.mLock)
    {
      if (isCurrentSnackbarLocked(paramCallback)) {
        scheduleTimeoutLocked(this.mCurrentSnackbar);
      }
      return;
    }
  }
  
  public final void restoreTimeout(Callback paramCallback)
  {
    synchronized (this.mLock)
    {
      if (isCurrentSnackbarLocked(paramCallback)) {
        scheduleTimeoutLocked(this.mCurrentSnackbar);
      }
      return;
    }
  }
  
  final void scheduleTimeoutLocked(SnackbarRecord paramSnackbarRecord)
  {
    if (paramSnackbarRecord.duration == -2) {
      return;
    }
    int i = 2750;
    if (paramSnackbarRecord.duration > 0) {
      i = paramSnackbarRecord.duration;
    }
    for (;;)
    {
      this.mHandler.removeCallbacksAndMessages(paramSnackbarRecord);
      this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 0, paramSnackbarRecord), i);
      return;
      if (paramSnackbarRecord.duration == -1) {
        i = 1500;
      }
    }
  }
  
  final void showNextSnackbarLocked()
  {
    if (this.mNextSnackbar != null)
    {
      this.mCurrentSnackbar = this.mNextSnackbar;
      this.mNextSnackbar = null;
      Callback localCallback = (Callback)this.mCurrentSnackbar.callback.get();
      if (localCallback != null) {
        localCallback.show();
      }
    }
    else
    {
      return;
    }
    this.mCurrentSnackbar = null;
  }
  
  static abstract interface Callback
  {
    public abstract void dismiss(int paramInt);
    
    public abstract void show();
  }
  
  private static final class SnackbarRecord
  {
    final WeakReference<SnackbarManager.Callback> callback;
    int duration;
    
    SnackbarRecord(int paramInt, SnackbarManager.Callback paramCallback)
    {
      this.callback = new WeakReference(paramCallback);
      this.duration = paramInt;
    }
    
    final boolean isSnackbar(SnackbarManager.Callback paramCallback)
    {
      return (paramCallback != null) && (this.callback.get() == paramCallback);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.SnackbarManager
 * JD-Core Version:    0.7.0.1
 */