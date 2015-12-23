package com.google.android.finsky.appstate;

import android.os.Handler;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class WriteThroughInstallerDataStore
  implements InstallerDataStore
{
  private final InstallerDataStore mInMemoryStore;
  private boolean mIsLoaded;
  private Collection<Runnable> mLoadedCallbacks = new ArrayList();
  private final Handler mNotificationHandler;
  final InstallerDataStore mPersistentStore;
  private final Handler mWriteThroughHandler;
  
  public WriteThroughInstallerDataStore(InstallerDataStore paramInstallerDataStore1, InstallerDataStore paramInstallerDataStore2, Handler paramHandler1, Handler paramHandler2)
  {
    this.mInMemoryStore = paramInstallerDataStore1;
    this.mPersistentStore = paramInstallerDataStore2;
    this.mWriteThroughHandler = paramHandler1;
    this.mNotificationHandler = paramHandler2;
  }
  
  public final InstallerDataStore.InstallerData get(String paramString)
  {
    try
    {
      InstallerDataStore.InstallerData localInstallerData = this.mInMemoryStore.get(paramString);
      return localInstallerData;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Collection<InstallerDataStore.InstallerData> getAll()
  {
    try
    {
      Collection localCollection = this.mInMemoryStore.getAll();
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final boolean isLoaded()
  {
    try
    {
      boolean bool = this.mIsLoaded;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void load()
  {
    try
    {
      if (!this.mIsLoaded)
      {
        Iterator localIterator2 = this.mPersistentStore.getAll().iterator();
        while (localIterator2.hasNext())
        {
          InstallerDataStore.InstallerData localInstallerData = (InstallerDataStore.InstallerData)localIterator2.next();
          this.mInMemoryStore.put(localInstallerData);
        }
        this.mIsLoaded = true;
      }
    }
    finally {}
    Iterator localIterator1 = this.mLoadedCallbacks.iterator();
    while (localIterator1.hasNext())
    {
      Runnable localRunnable = (Runnable)localIterator1.next();
      if (localRunnable != null) {
        this.mNotificationHandler.post(localRunnable);
      }
    }
    this.mLoadedCallbacks.clear();
  }
  
  /* Error */
  public final boolean load(Runnable paramRunnable)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 47	com/google/android/finsky/appstate/WriteThroughInstallerDataStore:mIsLoaded	Z
    //   6: ifeq +24 -> 30
    //   9: aload_1
    //   10: ifnull +12 -> 22
    //   13: aload_0
    //   14: getfield 35	com/google/android/finsky/appstate/WriteThroughInstallerDataStore:mNotificationHandler	Landroid/os/Handler;
    //   17: aload_1
    //   18: invokevirtual 77	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   21: pop
    //   22: iconst_1
    //   23: istore 4
    //   25: aload_0
    //   26: monitorexit
    //   27: iload 4
    //   29: ireturn
    //   30: aload_0
    //   31: getfield 27	com/google/android/finsky/appstate/WriteThroughInstallerDataStore:mLoadedCallbacks	Ljava/util/Collection;
    //   34: aload_1
    //   35: invokeinterface 84 2 0
    //   40: pop
    //   41: aload_0
    //   42: getfield 27	com/google/android/finsky/appstate/WriteThroughInstallerDataStore:mLoadedCallbacks	Ljava/util/Collection;
    //   45: invokeinterface 88 1 0
    //   50: iconst_2
    //   51: if_icmpge +19 -> 70
    //   54: aload_0
    //   55: getfield 33	com/google/android/finsky/appstate/WriteThroughInstallerDataStore:mWriteThroughHandler	Landroid/os/Handler;
    //   58: new 90	com/google/android/finsky/appstate/WriteThroughInstallerDataStore$1
    //   61: dup
    //   62: aload_0
    //   63: invokespecial 93	com/google/android/finsky/appstate/WriteThroughInstallerDataStore$1:<init>	(Lcom/google/android/finsky/appstate/WriteThroughInstallerDataStore;)V
    //   66: invokevirtual 77	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   69: pop
    //   70: iconst_0
    //   71: istore 4
    //   73: goto -48 -> 25
    //   76: astore_2
    //   77: aload_0
    //   78: monitorexit
    //   79: aload_2
    //   80: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	81	0	this	WriteThroughInstallerDataStore
    //   0	81	1	paramRunnable	Runnable
    //   76	4	2	localObject	Object
    //   23	49	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	9	76	finally
    //   13	22	76	finally
    //   30	70	76	finally
  }
  
  public final void put(final InstallerDataStore.InstallerData paramInstallerData)
  {
    try
    {
      this.mInMemoryStore.put(paramInstallerData);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.put(paramInstallerData);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAccountForUpdate(final String paramString1, final String paramString2)
  {
    try
    {
      this.mInMemoryStore.setAccountForUpdate(paramString1, paramString2);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setAccountForUpdate(paramString1, paramString2);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setActiveSplitId(final String paramString1, final String paramString2)
  {
    try
    {
      this.mInMemoryStore.setActiveSplitId(paramString1, paramString2);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setActiveSplitId(paramString1, paramString2);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoAcquireTags(final String paramString, final String[] paramArrayOfString)
  {
    try
    {
      this.mInMemoryStore.setAutoAcquireTags(paramString, paramArrayOfString);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setAutoAcquireTags(paramString, paramArrayOfString);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoUpdate(final String paramString, final int paramInt)
  {
    try
    {
      this.mInMemoryStore.setAutoUpdate(paramString, paramInt);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setAutoUpdate(paramString, paramInt);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setCompletedSplitIds(final String paramString, final String[] paramArrayOfString)
  {
    try
    {
      this.mInMemoryStore.setCompletedSplitIds(paramString, paramArrayOfString);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setCompletedSplitIds(paramString, paramArrayOfString);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setContinueUrl(final String paramString1, final String paramString2)
  {
    try
    {
      this.mInMemoryStore.setContinueUrl(paramString1, paramString2);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setContinueUrl(paramString1, paramString2);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setDeliveryData(final String paramString, final AndroidAppDeliveryData paramAndroidAppDeliveryData, final long paramLong)
  {
    try
    {
      this.mInMemoryStore.setDeliveryData(paramString, paramAndroidAppDeliveryData, paramLong);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setDeliveryData(paramString, paramAndroidAppDeliveryData, paramLong);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setDeliveryToken(final String paramString1, final String paramString2)
  {
    this.mInMemoryStore.setDeliveryToken(paramString1, paramString2);
    this.mWriteThroughHandler.post(new Runnable()
    {
      public final void run()
      {
        WriteThroughInstallerDataStore.this.mPersistentStore.setDeliveryToken(paramString1, paramString2);
      }
    });
  }
  
  public final void setDesiredVersion(final String paramString, final int paramInt)
  {
    try
    {
      this.mInMemoryStore.setDesiredVersion(paramString, paramInt);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setDesiredVersion(paramString, paramInt);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrer(final String paramString1, final String paramString2)
  {
    try
    {
      this.mInMemoryStore.setExternalReferrer(paramString1, paramString2);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setExternalReferrer(paramString1, paramString2);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrerTimestampMs(final String paramString, final long paramLong)
  {
    this.mInMemoryStore.setExternalReferrerTimestampMs(paramString, paramLong);
    this.mWriteThroughHandler.post(new Runnable()
    {
      public final void run()
      {
        WriteThroughInstallerDataStore.this.mPersistentStore.setExternalReferrerTimestampMs(paramString, paramLong);
      }
    });
  }
  
  public final void setFirstDownloadMs(final String paramString, final long paramLong)
  {
    try
    {
      this.mInMemoryStore.setFirstDownloadMs(paramString, paramLong);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setFirstDownloadMs(paramString, paramLong);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setFlags(final String paramString, final int paramInt)
  {
    try
    {
      this.mInMemoryStore.setFlags(paramString, paramInt);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setFlags(paramString, paramInt);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setInstallerState(final String paramString1, final int paramInt, final String paramString2)
  {
    try
    {
      this.mInMemoryStore.setInstallerState(paramString1, paramInt, paramString2);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setInstallerState(paramString1, paramInt, paramString2);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastNotifiedVersion(final String paramString, final int paramInt)
  {
    try
    {
      this.mInMemoryStore.setLastNotifiedVersion(paramString, paramInt);
      this.mWriteThroughHandler.post(new Runnable()
      {
        public final void run()
        {
          WriteThroughInstallerDataStore.this.mPersistentStore.setLastNotifiedVersion(paramString, paramInt);
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastUpdateTimestampMs(final String paramString, final long paramLong)
  {
    this.mInMemoryStore.setLastUpdateTimestampMs(paramString, paramLong);
    this.mWriteThroughHandler.post(new Runnable()
    {
      public final void run()
      {
        WriteThroughInstallerDataStore.this.mPersistentStore.setLastUpdateTimestampMs(paramString, paramLong);
      }
    });
  }
  
  public final void setPermissionsVersion(final String paramString, final int paramInt)
  {
    this.mInMemoryStore.setPermissionsVersion(paramString, paramInt);
    this.mWriteThroughHandler.post(new Runnable()
    {
      public final void run()
      {
        WriteThroughInstallerDataStore.this.mPersistentStore.setPermissionsVersion(paramString, paramInt);
      }
    });
  }
  
  public final void setPersistentFlags(final String paramString, final int paramInt)
  {
    this.mInMemoryStore.setPersistentFlags(paramString, paramInt);
    this.mWriteThroughHandler.post(new Runnable()
    {
      public final void run()
      {
        WriteThroughInstallerDataStore.this.mPersistentStore.setPersistentFlags(paramString, paramInt);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.WriteThroughInstallerDataStore
 * JD-Core Version:    0.7.0.1
 */