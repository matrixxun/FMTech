package com.google.android.finsky.library;

import android.accounts.Account;
import android.os.Handler;
import android.util.Log;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class LibraryReplicatorsImpl
  implements LibraryReplicators
{
  final Handler mBackgroundHandler;
  private final DfeApiProvider mDfeApiProvider;
  private final boolean mEnableDebugging;
  private final Libraries mLibraries;
  private final List<LibraryReplicators.Listener> mListeners = new ArrayList();
  final Handler mNotificationHandler;
  final Map<Account, LibraryReplicator> mReplicators = new HashMap();
  private final SQLiteLibrary mSQLiteLibrary;
  
  public LibraryReplicatorsImpl(DfeApiProvider paramDfeApiProvider, SQLiteLibrary paramSQLiteLibrary, Libraries paramLibraries, Handler paramHandler1, Handler paramHandler2, boolean paramBoolean)
  {
    this.mDfeApiProvider = paramDfeApiProvider;
    this.mSQLiteLibrary = paramSQLiteLibrary;
    this.mLibraries = paramLibraries;
    this.mNotificationHandler = paramHandler1;
    this.mBackgroundHandler = paramHandler2;
    this.mEnableDebugging = paramBoolean;
    reinitialize();
  }
  
  public final void addListener(LibraryReplicators.Listener paramListener)
  {
    try
    {
      this.mListeners.add(paramListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void applyLibraryUpdates(final Account paramAccount, final String paramString, final Runnable paramRunnable, final LibraryUpdateProto.LibraryUpdate... paramVarArgs)
  {
    this.mLibraries.load(new Runnable()
    {
      public final void run()
      {
        for (;;)
        {
          synchronized (LibraryReplicatorsImpl.this)
          {
            if (LibraryReplicatorsImpl.this.mReplicators.containsKey(paramAccount))
            {
              Object[] arrayOfObject2 = new Object[1];
              arrayOfObject2[0] = FinskyLog.scrubPii(paramAccount.name);
              FinskyLog.d("Applying library update: account=%s", arrayOfObject2);
              LibraryReplicator localLibraryReplicator = (LibraryReplicator)LibraryReplicatorsImpl.this.mReplicators.get(paramAccount);
              String str = paramString;
              LibraryUpdateProto.LibraryUpdate[] arrayOfLibraryUpdate = paramVarArgs;
              if ((arrayOfLibraryUpdate == null) || (arrayOfLibraryUpdate.length == 0))
              {
                if (paramRunnable != null) {
                  LibraryReplicatorsImpl.this.mBackgroundHandler.post(new Runnable()
                  {
                    public final void run()
                    {
                      LibraryReplicatorsImpl.this.mNotificationHandler.post(LibraryReplicatorsImpl.4.this.val$postLibraryUpdateCallback);
                    }
                  });
                }
                return;
              }
              localLibraryReplicator.mBackgroundHandler.post(new LibraryReplicator.2(localLibraryReplicator, arrayOfLibraryUpdate, str));
            }
          }
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = FinskyLog.scrubPii(paramAccount.name);
          FinskyLog.e("LibraryUpdate for unknown account %s could not be applied", arrayOfObject1);
        }
      }
    });
  }
  
  public final void dumpState()
  {
    Log.d("FinskyLibrary", "| LibraryReplicators {");
    Iterator localIterator1 = this.mReplicators.values().iterator();
    while (localIterator1.hasNext())
    {
      LibraryReplicator localLibraryReplicator = (LibraryReplicator)localIterator1.next();
      String str = FinskyLog.scrubPii(localLibraryReplicator.mAccountLibrary.mAccount.name);
      Log.d("FinskyLibrary", "|   " + "LibraryReplicator (account=" + str + ") {");
      if (localLibraryReplicator.mDebugEvents != null)
      {
        Log.d("FinskyLibrary", "|   " + "  eventsCount=" + localLibraryReplicator.mDebugEvents.size());
        Iterator localIterator2 = localLibraryReplicator.mDebugEvents.iterator();
        while (localIterator2.hasNext()) {
          ((LibraryReplicator.DebugEvent)localIterator2.next()).dumpState("|   ");
        }
      }
      Log.d("FinskyLibrary", "|   " + "  eventsCount=0");
      Log.d("FinskyLibrary", "|   " + "} (account=" + str + ")");
    }
    Log.d("FinskyLibrary", "| }");
  }
  
  final void notifyListeners$12348bc5(String paramString)
  {
    try
    {
      Iterator localIterator = this.mListeners.iterator();
      while (localIterator.hasNext()) {
        ((LibraryReplicators.Listener)localIterator.next()).onMutationsApplied$12348bc5(paramString);
      }
    }
    finally {}
  }
  
  public final void reinitialize()
  {
    try
    {
      this.mReplicators.clear();
      Iterator localIterator = this.mLibraries.getAccountLibraries().iterator();
      while (localIterator.hasNext())
      {
        final AccountLibrary localAccountLibrary = (AccountLibrary)localIterator.next();
        Account localAccount = localAccountLibrary.mAccount;
        LibraryReplicator localLibraryReplicator = new LibraryReplicator(this.mDfeApiProvider.getDfeApi(localAccount.name), this.mSQLiteLibrary, localAccountLibrary, this.mNotificationHandler, this.mBackgroundHandler, this.mEnableDebugging);
        localLibraryReplicator.addListener(new LibraryReplicator.Listener()
        {
          public final void onMutationsApplied(String paramAnonymousString)
          {
            LibraryReplicatorsImpl.this.notifyListeners$12348bc5(paramAnonymousString);
          }
        });
        this.mReplicators.put(localAccount, localLibraryReplicator);
      }
    }
    finally {}
  }
  
  public final void replicateAccount(final Account paramAccount, final String[] paramArrayOfString, final Runnable paramRunnable, final String paramString)
  {
    this.mLibraries.load(new Runnable()
    {
      public final void run()
      {
        synchronized (LibraryReplicatorsImpl.this)
        {
          ((LibraryReplicator)LibraryReplicatorsImpl.this.mReplicators.get(paramAccount)).replicate(paramArrayOfString, paramRunnable, paramString);
          return;
        }
      }
    });
  }
  
  public final void replicateAllAccounts(final Runnable paramRunnable, final String paramString)
  {
    this.mLibraries.load(new Runnable()
    {
      public final void run()
      {
        synchronized (LibraryReplicatorsImpl.this)
        {
          final Collection localCollection = LibraryReplicatorsImpl.this.mReplicators.values();
          if (localCollection.isEmpty())
          {
            if (paramRunnable != null) {
              LibraryReplicatorsImpl.this.mNotificationHandler.post(paramRunnable);
            }
            return;
          }
          Runnable local1 = new Runnable()
          {
            private int mFinished;
            
            public final void run()
            {
              this.mFinished = (1 + this.mFinished);
              if ((this.mFinished == localCollection.size()) && (LibraryReplicatorsImpl.2.this.val$successRunnable != null)) {
                LibraryReplicatorsImpl.this.mNotificationHandler.post(LibraryReplicatorsImpl.2.this.val$successRunnable);
              }
            }
          };
          Iterator localIterator = localCollection.iterator();
          if (localIterator.hasNext()) {
            ((LibraryReplicator)localIterator.next()).replicate(AccountLibrary.LIBRARY_IDS, local1, paramString);
          }
        }
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryReplicatorsImpl
 * JD-Core Version:    0.7.0.1
 */