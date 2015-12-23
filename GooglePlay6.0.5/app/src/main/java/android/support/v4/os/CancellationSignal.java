package android.support.v4.os;

import android.os.Build.VERSION;

public final class CancellationSignal
{
  public boolean mCancelInProgress;
  public Object mCancellationSignalObj;
  public boolean mIsCanceled;
  
  public final Object getCancellationSignalObject()
  {
    if (Build.VERSION.SDK_INT < 16) {
      return null;
    }
    try
    {
      if (this.mCancellationSignalObj == null)
      {
        this.mCancellationSignalObj = new android.os.CancellationSignal();
        if (this.mIsCanceled) {
          ((android.os.CancellationSignal)this.mCancellationSignalObj).cancel();
        }
      }
      Object localObject2 = this.mCancellationSignalObj;
      return localObject2;
    }
    finally {}
  }
  
  public final boolean isCanceled()
  {
    try
    {
      boolean bool = this.mIsCanceled;
      return bool;
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.os.CancellationSignal
 * JD-Core Version:    0.7.0.1
 */