package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCanceledListener;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.ContainerHelpers;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

final class LoaderManagerImpl
  extends LoaderManager
{
  static boolean DEBUG = false;
  boolean mCreatingLoader;
  FragmentHostCallback mHost;
  final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat();
  final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
  boolean mRetaining;
  boolean mStarted;
  final String mWho;
  
  LoaderManagerImpl(String paramString, FragmentHostCallback paramFragmentHostCallback, boolean paramBoolean)
  {
    this.mWho = paramString;
    this.mHost = paramFragmentHostCallback;
    this.mStarted = paramBoolean;
  }
  
  private LoaderInfo createAndInstallLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    try
    {
      this.mCreatingLoader = true;
      LoaderInfo localLoaderInfo = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
      installLoader(localLoaderInfo);
      return localLoaderInfo;
    }
    finally
    {
      this.mCreatingLoader = false;
    }
  }
  
  private LoaderInfo createLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    LoaderInfo localLoaderInfo = new LoaderInfo(paramInt, paramBundle, paramLoaderCallbacks);
    localLoaderInfo.mLoader = paramLoaderCallbacks.onCreateLoader(paramInt, paramBundle);
    return localLoaderInfo;
  }
  
  public final void destroyLoader$13462e()
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    if (DEBUG) {
      Log.v("LoaderManager", "destroyLoader in " + this + " of 2");
    }
    int i = this.mLoaders.indexOfKey(2);
    if (i >= 0)
    {
      LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mLoaders.valueAt(i);
      this.mLoaders.removeAt(i);
      localLoaderInfo2.destroy();
    }
    int j = this.mInactiveLoaders.indexOfKey(2);
    if (j >= 0)
    {
      LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mInactiveLoaders.valueAt(j);
      this.mInactiveLoaders.removeAt(j);
      localLoaderInfo1.destroy();
    }
    if ((this.mHost != null) && (!hasRunningLoaders())) {
      this.mHost.mFragmentManager.startPendingDeferredFragments();
    }
  }
  
  final void doDestroy()
  {
    if (!this.mRetaining)
    {
      if (DEBUG) {
        Log.v("LoaderManager", "Destroying Active in " + this);
      }
      for (int j = -1 + this.mLoaders.size(); j >= 0; j--) {
        ((LoaderInfo)this.mLoaders.valueAt(j)).destroy();
      }
      this.mLoaders.clear();
    }
    if (DEBUG) {
      Log.v("LoaderManager", "Destroying Inactive in " + this);
    }
    for (int i = -1 + this.mInactiveLoaders.size(); i >= 0; i--) {
      ((LoaderInfo)this.mInactiveLoaders.valueAt(i)).destroy();
    }
    this.mInactiveLoaders.clear();
  }
  
  final void doReportNextStart()
  {
    for (int i = -1 + this.mLoaders.size(); i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).mReportNextStart = true;
    }
  }
  
  final void doReportStart()
  {
    for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
      if ((localLoaderInfo.mStarted) && (localLoaderInfo.mReportNextStart))
      {
        localLoaderInfo.mReportNextStart = false;
        if (localLoaderInfo.mHaveData) {
          localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
        }
      }
    }
  }
  
  final void doRetain()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Retaining in " + this);
    }
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doRetain when not started: " + this, localRuntimeException);
    }
    for (;;)
    {
      return;
      this.mRetaining = true;
      this.mStarted = false;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--)
      {
        LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
        if (DEBUG) {
          Log.v("LoaderManager", "  Retaining: " + localLoaderInfo);
        }
        localLoaderInfo.mRetaining = true;
        localLoaderInfo.mRetainingStarted = localLoaderInfo.mStarted;
        localLoaderInfo.mStarted = false;
        localLoaderInfo.mCallbacks = null;
      }
    }
  }
  
  final void doStart()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Starting in " + this);
    }
    if (this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStart when already started: " + this, localRuntimeException);
    }
    for (;;)
    {
      return;
      this.mStarted = true;
      for (int i = -1 + this.mLoaders.size(); i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).start();
      }
    }
  }
  
  final void doStop()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Stopping in " + this);
    }
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStop when not started: " + this, localRuntimeException);
      return;
    }
    for (int i = -1 + this.mLoaders.size(); i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).stop();
    }
    this.mStarted = false;
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    if (this.mLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Active Loaders:");
      String str2 = paramString + "    ";
      for (int j = 0; j < this.mLoaders.size(); j++)
      {
        LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mLoaders.valueAt(j);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mLoaders.keyAt(j));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(localLoaderInfo2.toString());
        localLoaderInfo2.dump(str2, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    if (this.mInactiveLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Inactive Loaders:");
      String str1 = paramString + "    ";
      for (int i = 0; i < this.mInactiveLoaders.size(); i++)
      {
        LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mInactiveLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mInactiveLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(localLoaderInfo1.toString());
        localLoaderInfo1.dump(str1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
  }
  
  public final <D> Loader<D> getLoader(int paramInt)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
    if (localLoaderInfo != null)
    {
      if (localLoaderInfo.mPendingLoader != null) {
        return localLoaderInfo.mPendingLoader.mLoader;
      }
      return localLoaderInfo.mLoader;
    }
    return null;
  }
  
  public final boolean hasRunningLoaders()
  {
    boolean bool1 = false;
    int i = this.mLoaders.size();
    int j = 0;
    if (j < i)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(j);
      if ((localLoaderInfo.mStarted) && (!localLoaderInfo.mDeliveredData)) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        bool1 |= bool2;
        j++;
        break;
      }
    }
    return bool1;
  }
  
  public final <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
    if (DEBUG) {
      Log.v("LoaderManager", "initLoader in " + this + ": args=" + paramBundle);
    }
    if (localLoaderInfo == null)
    {
      localLoaderInfo = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks);
      if (DEBUG) {
        Log.v("LoaderManager", "  Created new loader " + localLoaderInfo);
      }
    }
    for (;;)
    {
      if ((localLoaderInfo.mHaveData) && (this.mStarted)) {
        localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
      }
      return localLoaderInfo.mLoader;
      if (DEBUG) {
        Log.v("LoaderManager", "  Re-using existing loader " + localLoaderInfo);
      }
      localLoaderInfo.mCallbacks = paramLoaderCallbacks;
    }
  }
  
  final void installLoader(LoaderInfo paramLoaderInfo)
  {
    this.mLoaders.put(paramLoaderInfo.mId, paramLoaderInfo);
    if (this.mStarted) {
      paramLoaderInfo.start();
    }
  }
  
  public final <D> Loader<D> restartLoader$71be8de6(int paramInt, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mLoaders.get(paramInt);
    if (DEBUG) {
      Log.v("LoaderManager", "restartLoader in " + this + ": args=" + null);
    }
    if (localLoaderInfo1 != null)
    {
      LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mInactiveLoaders.get(paramInt);
      if (localLoaderInfo2 == null) {
        break label366;
      }
      if (!localLoaderInfo1.mHaveData) {
        break label164;
      }
      if (DEBUG) {
        Log.v("LoaderManager", "  Removing last inactive loader: " + localLoaderInfo1);
      }
      localLoaderInfo2.mDeliveredData = false;
      localLoaderInfo2.destroy();
    }
    for (;;)
    {
      localLoaderInfo1.mLoader.mAbandoned = true;
      this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
      for (;;)
      {
        return createAndInstallLoader(paramInt, null, paramLoaderCallbacks).mLoader;
        label164:
        if (localLoaderInfo1.mStarted) {
          break;
        }
        if (DEBUG) {
          Log.v("LoaderManager", "  Current loader is stopped; replacing");
        }
        this.mLoaders.put(paramInt, null);
        localLoaderInfo1.destroy();
      }
      if (DEBUG) {
        Log.v("LoaderManager", "  Current loader is running; attempting to cancel");
      }
      if (DEBUG) {
        Log.v("LoaderManager", "  Canceling: " + localLoaderInfo1);
      }
      if ((localLoaderInfo1.mStarted) && (localLoaderInfo1.mLoader != null) && (localLoaderInfo1.mListenerRegistered) && (!localLoaderInfo1.mLoader.cancelLoad())) {
        localLoaderInfo1.onLoadCanceled$5dda1f52();
      }
      if (localLoaderInfo1.mPendingLoader != null)
      {
        if (DEBUG) {
          Log.v("LoaderManager", "  Removing pending loader: " + localLoaderInfo1.mPendingLoader);
        }
        localLoaderInfo1.mPendingLoader.destroy();
        localLoaderInfo1.mPendingLoader = null;
      }
      if (DEBUG) {
        Log.v("LoaderManager", "  Enqueuing as new pending loader");
      }
      localLoaderInfo1.mPendingLoader = createLoader(paramInt, null, paramLoaderCallbacks);
      return localLoaderInfo1.mPendingLoader.mLoader;
      label366:
      if (DEBUG) {
        Log.v("LoaderManager", "  Making last loader inactive: " + localLoaderInfo1);
      }
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("LoaderManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    DebugUtils.buildShortClassTag(this.mHost, localStringBuilder);
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  final class LoaderInfo
    implements Loader.OnLoadCanceledListener<Object>, Loader.OnLoadCompleteListener<Object>
  {
    final Bundle mArgs;
    LoaderManager.LoaderCallbacks<Object> mCallbacks;
    Object mData;
    boolean mDeliveredData;
    boolean mDestroyed;
    boolean mHaveData;
    final int mId;
    boolean mListenerRegistered;
    Loader<Object> mLoader;
    LoaderInfo mPendingLoader;
    boolean mReportNextStart;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    
    public LoaderInfo(Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
    {
      this.mId = paramBundle;
      this.mArgs = paramLoaderCallbacks;
      Object localObject;
      this.mCallbacks = localObject;
    }
    
    final void callOnLoadFinished(Loader<Object> paramLoader, Object paramObject)
    {
      String str;
      if (this.mCallbacks != null)
      {
        FragmentHostCallback localFragmentHostCallback = LoaderManagerImpl.this.mHost;
        str = null;
        if (localFragmentHostCallback != null)
        {
          str = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
          LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
        }
      }
      try
      {
        if (LoaderManagerImpl.DEBUG)
        {
          StringBuilder localStringBuilder1 = new StringBuilder("  onLoadFinished in ").append(paramLoader).append(": ");
          StringBuilder localStringBuilder2 = new StringBuilder(64);
          DebugUtils.buildShortClassTag(paramObject, localStringBuilder2);
          localStringBuilder2.append("}");
          Log.v("LoaderManager", localStringBuilder2.toString());
        }
        this.mCallbacks.onLoadFinished(paramLoader, paramObject);
        if (LoaderManagerImpl.this.mHost != null) {
          LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
        }
        this.mDeliveredData = true;
        return;
      }
      finally
      {
        if (LoaderManagerImpl.this.mHost != null) {
          LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
        }
      }
    }
    
    final void destroy()
    {
      for (;;)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Destroying: " + this);
        }
        this.mDestroyed = true;
        boolean bool = this.mDeliveredData;
        this.mDeliveredData = false;
        String str;
        if ((this.mCallbacks != null) && (this.mLoader != null) && (this.mHaveData) && (bool))
        {
          if (LoaderManagerImpl.DEBUG) {
            Log.v("LoaderManager", "  Reseting: " + this);
          }
          FragmentHostCallback localFragmentHostCallback = LoaderManagerImpl.this.mHost;
          str = null;
          if (localFragmentHostCallback != null)
          {
            str = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
            LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
          }
        }
        try
        {
          this.mCallbacks.onLoaderReset$5dda1f52();
          if (LoaderManagerImpl.this.mHost != null) {
            LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
          }
          this.mCallbacks = null;
          this.mData = null;
          this.mHaveData = false;
          if (this.mLoader != null)
          {
            if (this.mListenerRegistered)
            {
              this.mListenerRegistered = false;
              this.mLoader.unregisterListener(this);
              this.mLoader.unregisterOnLoadCanceledListener(this);
            }
            Loader localLoader = this.mLoader;
            localLoader.onReset();
            localLoader.mReset = true;
            localLoader.mStarted = false;
            localLoader.mAbandoned = false;
            localLoader.mContentChanged = false;
            localLoader.mProcessingChange = false;
          }
          if (this.mPendingLoader != null) {
            this = this.mPendingLoader;
          }
        }
        finally
        {
          if (LoaderManagerImpl.this.mHost != null) {
            LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
          }
        }
      }
    }
    
    public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      for (;;)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mId=");
        paramPrintWriter.print(this.mId);
        paramPrintWriter.print(" mArgs=");
        paramPrintWriter.println(this.mArgs);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mCallbacks=");
        paramPrintWriter.println(this.mCallbacks);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mLoader=");
        paramPrintWriter.println(this.mLoader);
        if (this.mLoader != null) {
          this.mLoader.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
        }
        if ((this.mHaveData) || (this.mDeliveredData))
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("mHaveData=");
          paramPrintWriter.print(this.mHaveData);
          paramPrintWriter.print("  mDeliveredData=");
          paramPrintWriter.println(this.mDeliveredData);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("mData=");
          paramPrintWriter.println(this.mData);
        }
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mStarted=");
        paramPrintWriter.print(this.mStarted);
        paramPrintWriter.print(" mReportNextStart=");
        paramPrintWriter.print(this.mReportNextStart);
        paramPrintWriter.print(" mDestroyed=");
        paramPrintWriter.println(this.mDestroyed);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mRetaining=");
        paramPrintWriter.print(this.mRetaining);
        paramPrintWriter.print(" mRetainingStarted=");
        paramPrintWriter.print(this.mRetainingStarted);
        paramPrintWriter.print(" mListenerRegistered=");
        paramPrintWriter.println(this.mListenerRegistered);
        if (this.mPendingLoader == null) {
          break;
        }
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Loader ");
        paramPrintWriter.print(this.mPendingLoader);
        paramPrintWriter.println(":");
        this = this.mPendingLoader;
        paramString = paramString + "  ";
      }
    }
    
    public final void onLoadCanceled$5dda1f52()
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "onLoadCanceled: " + this);
      }
      if (this.mDestroyed) {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load canceled -- destroyed");
        }
      }
      LoaderInfo localLoaderInfo;
      do
      {
        do
        {
          return;
          if (LoaderManagerImpl.this.mLoaders.get(this.mId) == this) {
            break;
          }
        } while (!LoaderManagerImpl.DEBUG);
        Log.v("LoaderManager", "  Ignoring load canceled -- not active");
        return;
        localLoaderInfo = this.mPendingLoader;
      } while (localLoaderInfo == null);
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "  Switching to pending loader: " + localLoaderInfo);
      }
      this.mPendingLoader = null;
      LoaderManagerImpl.this.mLoaders.put(this.mId, null);
      destroy();
      LoaderManagerImpl.this.installLoader(localLoaderInfo);
    }
    
    public final void onLoadComplete(Loader<Object> paramLoader, Object paramObject)
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "onLoadComplete: " + this);
      }
      if (this.mDestroyed) {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load complete -- destroyed");
        }
      }
      do
      {
        do
        {
          return;
          if (LoaderManagerImpl.this.mLoaders.get(this.mId) == this) {
            break;
          }
        } while (!LoaderManagerImpl.DEBUG);
        Log.v("LoaderManager", "  Ignoring load complete -- not active");
        return;
        LoaderInfo localLoaderInfo1 = this.mPendingLoader;
        if (localLoaderInfo1 != null)
        {
          if (LoaderManagerImpl.DEBUG) {
            Log.v("LoaderManager", "  Switching to pending loader: " + localLoaderInfo1);
          }
          this.mPendingLoader = null;
          LoaderManagerImpl.this.mLoaders.put(this.mId, null);
          destroy();
          LoaderManagerImpl.this.installLoader(localLoaderInfo1);
          return;
        }
        if ((this.mData != paramObject) || (!this.mHaveData))
        {
          this.mData = paramObject;
          this.mHaveData = true;
          if (this.mStarted) {
            callOnLoadFinished(paramLoader, paramObject);
          }
        }
        LoaderInfo localLoaderInfo2 = (LoaderInfo)LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
        if ((localLoaderInfo2 != null) && (localLoaderInfo2 != this))
        {
          localLoaderInfo2.mDeliveredData = false;
          localLoaderInfo2.destroy();
          SparseArrayCompat localSparseArrayCompat = LoaderManagerImpl.this.mInactiveLoaders;
          int i = this.mId;
          int j = ContainerHelpers.binarySearch(localSparseArrayCompat.mKeys, localSparseArrayCompat.mSize, i);
          if ((j >= 0) && (localSparseArrayCompat.mValues[j] != SparseArrayCompat.DELETED))
          {
            localSparseArrayCompat.mValues[j] = SparseArrayCompat.DELETED;
            localSparseArrayCompat.mGarbage = true;
          }
        }
      } while ((LoaderManagerImpl.this.mHost == null) || (LoaderManagerImpl.this.hasRunningLoaders()));
      LoaderManagerImpl.this.mHost.mFragmentManager.startPendingDeferredFragments();
    }
    
    final void start()
    {
      if ((this.mRetaining) && (this.mRetainingStarted)) {
        this.mStarted = true;
      }
      do
      {
        do
        {
          return;
        } while (this.mStarted);
        this.mStarted = true;
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Starting: " + this);
        }
        if ((this.mLoader == null) && (this.mCallbacks != null)) {
          this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
        }
      } while (this.mLoader == null);
      if ((this.mLoader.getClass().isMemberClass()) && (!Modifier.isStatic(this.mLoader.getClass().getModifiers()))) {
        throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
      }
      if (!this.mListenerRegistered)
      {
        Loader localLoader2 = this.mLoader;
        int i = this.mId;
        if (localLoader2.mListener != null) {
          throw new IllegalStateException("There is already a listener registered");
        }
        localLoader2.mListener = this;
        localLoader2.mId = i;
        Loader localLoader3 = this.mLoader;
        if (localLoader3.mOnLoadCanceledListener != null) {
          throw new IllegalStateException("There is already a listener registered");
        }
        localLoader3.mOnLoadCanceledListener = this;
        this.mListenerRegistered = true;
      }
      Loader localLoader1 = this.mLoader;
      localLoader1.mStarted = true;
      localLoader1.mReset = false;
      localLoader1.mAbandoned = false;
      localLoader1.onStartLoading();
    }
    
    final void stop()
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "  Stopping: " + this);
      }
      this.mStarted = false;
      if ((!this.mRetaining) && (this.mLoader != null) && (this.mListenerRegistered))
      {
        this.mListenerRegistered = false;
        this.mLoader.unregisterListener(this);
        this.mLoader.unregisterOnLoadCanceledListener(this);
        Loader localLoader = this.mLoader;
        localLoader.mStarted = false;
        localLoader.onStopLoading();
      }
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(64);
      localStringBuilder.append("LoaderInfo{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mId);
      localStringBuilder.append(" : ");
      DebugUtils.buildShortClassTag(this.mLoader, localStringBuilder);
      localStringBuilder.append("}}");
      return localStringBuilder.toString();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.LoaderManagerImpl
 * JD-Core Version:    0.7.0.1
 */