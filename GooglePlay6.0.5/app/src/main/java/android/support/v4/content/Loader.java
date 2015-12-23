package android.support.v4.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.util.DebugUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class Loader<D>
{
  public boolean mAbandoned = false;
  public boolean mContentChanged = false;
  public Context mContext;
  public int mId;
  public OnLoadCompleteListener<D> mListener;
  public OnLoadCanceledListener<D> mOnLoadCanceledListener;
  public boolean mProcessingChange = false;
  public boolean mReset = true;
  public boolean mStarted = false;
  
  public Loader(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }
  
  public final boolean cancelLoad()
  {
    return onCancelLoad();
  }
  
  public void deliverResult(D paramD)
  {
    if (this.mListener != null) {
      this.mListener.onLoadComplete(this, paramD);
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mId=");
    paramPrintWriter.print(this.mId);
    paramPrintWriter.print(" mListener=");
    paramPrintWriter.println(this.mListener);
    if ((this.mStarted) || (this.mContentChanged) || (this.mProcessingChange))
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStarted=");
      paramPrintWriter.print(this.mStarted);
      paramPrintWriter.print(" mContentChanged=");
      paramPrintWriter.print(this.mContentChanged);
      paramPrintWriter.print(" mProcessingChange=");
      paramPrintWriter.println(this.mProcessingChange);
    }
    if ((this.mAbandoned) || (this.mReset))
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mAbandoned=");
      paramPrintWriter.print(this.mAbandoned);
      paramPrintWriter.print(" mReset=");
      paramPrintWriter.println(this.mReset);
    }
  }
  
  public final void forceLoad()
  {
    onForceLoad();
  }
  
  protected boolean onCancelLoad()
  {
    return false;
  }
  
  public final void onContentChanged()
  {
    if (this.mStarted)
    {
      onForceLoad();
      return;
    }
    this.mContentChanged = true;
  }
  
  protected void onForceLoad() {}
  
  public void onReset() {}
  
  public void onStartLoading() {}
  
  public void onStopLoading() {}
  
  public final boolean takeContentChanged()
  {
    boolean bool = this.mContentChanged;
    this.mContentChanged = false;
    this.mProcessingChange = (bool | this.mProcessingChange);
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(64);
    DebugUtils.buildShortClassTag(this, localStringBuilder);
    localStringBuilder.append(" id=");
    localStringBuilder.append(this.mId);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public final void unregisterListener(OnLoadCompleteListener<D> paramOnLoadCompleteListener)
  {
    if (this.mListener == null) {
      throw new IllegalStateException("No listener register");
    }
    if (this.mListener != paramOnLoadCompleteListener) {
      throw new IllegalArgumentException("Attempting to unregister the wrong listener");
    }
    this.mListener = null;
  }
  
  public final void unregisterOnLoadCanceledListener(OnLoadCanceledListener<D> paramOnLoadCanceledListener)
  {
    if (this.mOnLoadCanceledListener == null) {
      throw new IllegalStateException("No listener register");
    }
    if (this.mOnLoadCanceledListener != paramOnLoadCanceledListener) {
      throw new IllegalArgumentException("Attempting to unregister the wrong listener");
    }
    this.mOnLoadCanceledListener = null;
  }
  
  public final class ForceLoadContentObserver
    extends ContentObserver
  {
    public ForceLoadContentObserver()
    {
      super();
    }
    
    public final boolean deliverSelfNotifications()
    {
      return true;
    }
    
    public final void onChange(boolean paramBoolean)
    {
      Loader.this.onContentChanged();
    }
  }
  
  public static abstract interface OnLoadCanceledListener<D>
  {
    public abstract void onLoadCanceled$5dda1f52();
  }
  
  public static abstract interface OnLoadCompleteListener<D>
  {
    public abstract void onLoadComplete(Loader<D> paramLoader, D paramD);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.content.Loader
 * JD-Core Version:    0.7.0.1
 */