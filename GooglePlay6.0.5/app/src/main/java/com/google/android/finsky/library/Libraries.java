package com.google.android.finsky.library;

import android.accounts.Account;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public final class Libraries
  implements Library
{
  private final AccountsProvider mAccounts;
  private final Handler mBackgroundHandler;
  private List<Account> mCurrentAccounts = null;
  public final Map<Account, AccountLibrary> mLibraries = new HashMap();
  private List<AccountLibrary> mLibraryList = Collections.unmodifiableList(new ArrayList());
  private final List<Listener> mListeners = new ArrayList();
  private boolean mLoadHasBeenCalled = false;
  private int mLoadedAccountHash;
  private final List<Runnable> mLoadingCallbacks = new ArrayList();
  private final Handler mNotificationHandler;
  private final SQLiteLibrary mSQLiteLibrary;
  
  public Libraries(AccountsProvider paramAccountsProvider, SQLiteLibrary paramSQLiteLibrary, Handler paramHandler1, Handler paramHandler2)
  {
    this.mAccounts = paramAccountsProvider;
    this.mSQLiteLibrary = paramSQLiteLibrary;
    this.mBackgroundHandler = paramHandler2;
    this.mNotificationHandler = paramHandler1;
  }
  
  private static int computeAccountHash(List<Account> paramList)
  {
    int i = 0;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      i += ((Account)localIterator.next()).hashCode();
    }
    return i;
  }
  
  private void fireAllLibrariesLoaded()
  {
    this.mNotificationHandler.post(new Runnable()
    {
      public final void run()
      {
        synchronized (Libraries.this.mListeners)
        {
          Iterator localIterator = Libraries.this.mListeners.iterator();
          if (localIterator.hasNext()) {
            ((Libraries.Listener)localIterator.next()).onAllLibrariesLoaded();
          }
        }
      }
    });
  }
  
  private void runAndClearLoadingCallbacks()
  {
    try
    {
      Iterator localIterator = this.mLoadingCallbacks.iterator();
      while (localIterator.hasNext())
      {
        Runnable localRunnable = (Runnable)localIterator.next();
        if (localRunnable != null) {
          this.mNotificationHandler.post(localRunnable);
        }
      }
      this.mLoadingCallbacks.clear();
    }
    finally {}
  }
  
  public final void addListener(Listener paramListener)
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
  
  public final void blockingLoad()
  {
    if ((Looper.myLooper() == this.mNotificationHandler.getLooper()) || (Looper.myLooper() == this.mBackgroundHandler.getLooper())) {
      throw new IllegalStateException();
    }
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    load(new Runnable()
    {
      public final void run()
      {
        localCountDownLatch.countDown();
      }
    });
    try
    {
      localCountDownLatch.await();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new RuntimeException(localInterruptedException);
    }
  }
  
  public final void cleanup()
  {
    try
    {
      this.mBackgroundHandler.post(new Runnable()
      {
        public final void run()
        {
          Libraries.this.mSQLiteLibrary.reopen();
          SQLiteLibrary localSQLiteLibrary = Libraries.this.mSQLiteLibrary;
          List localList = Libraries.this.mAccounts.getAccounts();
          StringBuilder localStringBuilder = new StringBuilder();
          String[] arrayOfString = new String[localList.size()];
          for (int i = 0; i < localList.size(); i++)
          {
            if (i > 0) {
              localStringBuilder.append(',');
            }
            localStringBuilder.append('?');
            arrayOfString[i] = ((Account)localList.get(i)).name;
          }
          int j = localSQLiteLibrary.mDb.delete("ownership", "account NOT IN (" + localStringBuilder.toString() + ")", arrayOfString);
          if (j > 0)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(j);
            FinskyLog.d("Removed %d obsolete library rows.", arrayOfObject);
          }
          Libraries.this.mSQLiteLibrary.close();
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
  
  /* Error */
  public final boolean contains(LibraryEntry paramLibraryEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   6: invokeinterface 182 1 0
    //   11: istore_3
    //   12: iconst_0
    //   13: istore 4
    //   15: iload 4
    //   17: iload_3
    //   18: if_icmpge +42 -> 60
    //   21: aload_0
    //   22: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   25: iload 4
    //   27: invokeinterface 186 2 0
    //   32: checkcast 188	com/google/android/finsky/library/AccountLibrary
    //   35: aload_1
    //   36: invokevirtual 190	com/google/android/finsky/library/AccountLibrary:contains	(Lcom/google/android/finsky/library/LibraryEntry;)Z
    //   39: istore 5
    //   41: iload 5
    //   43: ifeq +11 -> 54
    //   46: iconst_1
    //   47: istore 6
    //   49: aload_0
    //   50: monitorexit
    //   51: iload 6
    //   53: ireturn
    //   54: iinc 4 1
    //   57: goto -42 -> 15
    //   60: iconst_0
    //   61: istore 6
    //   63: goto -14 -> 49
    //   66: astore_2
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_2
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	71	0	this	Libraries
    //   0	71	1	paramLibraryEntry	LibraryEntry
    //   66	4	2	localObject	Object
    //   11	8	3	i	int
    //   13	42	4	j	int
    //   39	3	5	bool1	boolean
    //   47	15	6	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	66	finally
    //   21	41	66	finally
  }
  
  /* Error */
  public final LibraryEntry get(LibraryEntry paramLibraryEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   6: invokeinterface 182 1 0
    //   11: istore_3
    //   12: iconst_0
    //   13: istore 4
    //   15: iload 4
    //   17: iload_3
    //   18: if_icmpge +43 -> 61
    //   21: aload_0
    //   22: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   25: iload 4
    //   27: invokeinterface 186 2 0
    //   32: checkcast 188	com/google/android/finsky/library/AccountLibrary
    //   35: aload_1
    //   36: invokevirtual 193	com/google/android/finsky/library/AccountLibrary:get	(Lcom/google/android/finsky/library/LibraryEntry;)Lcom/google/android/finsky/library/LibraryEntry;
    //   39: astore 5
    //   41: aload 5
    //   43: astore 6
    //   45: aload 6
    //   47: ifnull +8 -> 55
    //   50: aload_0
    //   51: monitorexit
    //   52: aload 6
    //   54: areturn
    //   55: iinc 4 1
    //   58: goto -43 -> 15
    //   61: aconst_null
    //   62: astore 6
    //   64: goto -14 -> 50
    //   67: astore_2
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_2
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	Libraries
    //   0	72	1	paramLibraryEntry	LibraryEntry
    //   67	4	2	localObject	Object
    //   11	8	3	i	int
    //   13	43	4	j	int
    //   39	3	5	localLibraryEntry1	LibraryEntry
    //   43	20	6	localLibraryEntry2	LibraryEntry
    // Exception table:
    //   from	to	target	type
    //   2	12	67	finally
    //   21	41	67	finally
  }
  
  public final List<AccountLibrary> getAccountLibraries()
  {
    try
    {
      List localList = this.mLibraryList;
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final AccountLibrary getAccountLibrary(Account paramAccount)
  {
    try
    {
      AccountLibrary localAccountLibrary = (AccountLibrary)this.mLibraries.get(paramAccount);
      return localAccountLibrary;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<LibraryAppEntry> getAppEntries(String paramString, String[] paramArrayOfString)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      int i = this.mLibraryList.size();
      for (int j = 0; j < i; j++)
      {
        LibraryAppEntry localLibraryAppEntry = ((AccountLibrary)this.mLibraryList.get(j)).getAppEntry(paramString);
        if ((localLibraryAppEntry != null) && (localLibraryAppEntry.hasMatchingCertificateHash(paramArrayOfString))) {
          localArrayList.add(localLibraryAppEntry);
        }
      }
      return localArrayList;
    }
    finally {}
  }
  
  public final List<Account> getAppOwners(String paramString)
  {
    try
    {
      List localList = getAppOwners(paramString, LibraryAppEntry.ANY_CERTIFICATE_HASHES);
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<Account> getAppOwners(String paramString, String[] paramArrayOfString)
  {
    for (;;)
    {
      int j;
      Account localAccount;
      LibraryAppEntry localLibraryAppEntry;
      try
      {
        int i = this.mCurrentAccounts.size();
        j = 0;
        localObject3 = null;
        if (j >= i) {
          break;
        }
      }
      finally {}
      try
      {
        localAccount = (Account)this.mCurrentAccounts.get(j);
        localLibraryAppEntry = ((AccountLibrary)this.mLibraries.get(localAccount)).getAppEntry(paramString);
        if ((localLibraryAppEntry == null) || (!localLibraryAppEntry.hasMatchingCertificateHash(paramArrayOfString))) {
          break label151;
        }
        if (localObject3 != null) {
          break label144;
        }
        localObject4 = new ArrayList();
      }
      finally
      {
        continue;
        localObject4 = localObject3;
        continue;
        localObject4 = localObject3;
        continue;
      }
      ((List)localObject4).add(localAccount);
      j++;
      localObject3 = localObject4;
    }
    if (localObject3 != null) {}
    for (;;)
    {
      return localObject3;
      List localList = Collections.emptyList();
      localObject3 = localList;
    }
  }
  
  public final int getLoadedAccountHash()
  {
    try
    {
      int i = this.mLoadedAccountHash;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final boolean hasSubscriptions()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   6: invokeinterface 182 1 0
    //   11: istore_2
    //   12: iconst_0
    //   13: istore_3
    //   14: iload_3
    //   15: iload_2
    //   16: if_icmpge +45 -> 61
    //   19: aload_0
    //   20: getfield 52	com/google/android/finsky/library/Libraries:mLibraryList	Ljava/util/List;
    //   23: iload_3
    //   24: invokeinterface 186 2 0
    //   29: checkcast 188	com/google/android/finsky/library/AccountLibrary
    //   32: invokevirtual 230	com/google/android/finsky/library/AccountLibrary:getInAppSubscriptionsList	()Ljava/util/List;
    //   35: invokeinterface 233 1 0
    //   40: istore 4
    //   42: iload 4
    //   44: ifne +11 -> 55
    //   47: iconst_1
    //   48: istore 5
    //   50: aload_0
    //   51: monitorexit
    //   52: iload 5
    //   54: ireturn
    //   55: iinc 3 1
    //   58: goto -44 -> 14
    //   61: iconst_0
    //   62: istore 5
    //   64: goto -14 -> 50
    //   67: astore_1
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	Libraries
    //   67	4	1	localObject	Object
    //   11	6	2	i	int
    //   13	43	3	j	int
    //   40	3	4	bool1	boolean
    //   48	15	5	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	67	finally
    //   19	42	67	finally
  }
  
  public final boolean isLoaded()
  {
    try
    {
      boolean bool = this.mLoadHasBeenCalled;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Iterator<LibraryEntry> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  public final void load(Runnable paramRunnable)
  {
    for (;;)
    {
      final int i;
      ArrayList localArrayList1;
      try
      {
        if ((this.mLoadHasBeenCalled) && (this.mLoadedAccountHash == computeAccountHash(this.mAccounts.getAccounts())))
        {
          if (paramRunnable != null) {
            this.mNotificationHandler.post(paramRunnable);
          }
          return;
        }
        this.mLoadingCallbacks.add(paramRunnable);
        if (this.mLoadingCallbacks.size() > 1) {
          continue;
        }
        this.mCurrentAccounts = this.mAccounts.getAccounts();
        i = computeAccountHash(this.mCurrentAccounts);
        localArrayList1 = null;
        Iterator localIterator1 = this.mLibraries.keySet().iterator();
        if (localIterator1.hasNext())
        {
          Account localAccount4 = (Account)localIterator1.next();
          if (localArrayList1 == null) {
            localArrayList1 = new ArrayList();
          }
          if (this.mCurrentAccounts.contains(localAccount4)) {
            continue;
          }
          localArrayList1.add(localAccount4);
          continue;
        }
        if (localArrayList1 == null) {
          break label254;
        }
      }
      finally {}
      Iterator localIterator4 = localArrayList1.iterator();
      while (localIterator4.hasNext())
      {
        Account localAccount3 = (Account)localIterator4.next();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = FinskyLog.scrubPii(localAccount3.name);
        FinskyLog.d("Unloading AccountLibrary for account: %s", arrayOfObject);
        this.mLibraries.remove(localAccount3);
      }
      label254:
      final ArrayList localArrayList2 = new ArrayList();
      Iterator localIterator2 = this.mCurrentAccounts.iterator();
      while (localIterator2.hasNext())
      {
        Account localAccount2 = (Account)localIterator2.next();
        if (!this.mLibraries.containsKey(localAccount2))
        {
          localArrayList2.add(localAccount2);
          final AccountLibrary localAccountLibrary2 = new AccountLibrary(localAccount2, this.mNotificationHandler);
          localAccountLibrary2.addListener(new AccountLibrary.Listener()
          {
            public final void onLibraryChange()
            {
              Libraries.access$500(Libraries.this, localAccountLibrary2);
            }
          });
          this.mLibraries.put(localAccount2, localAccountLibrary2);
        }
      }
      this.mLibraryList = Collections.unmodifiableList(Lists.newArrayList(this.mLibraries.values()));
      if (localArrayList2.size() == 0)
      {
        fireAllLibrariesLoaded();
        runAndClearLoadingCallbacks();
        this.mLoadedAccountHash = i;
        this.mLoadHasBeenCalled = true;
      }
      else
      {
        final int[] arrayOfInt = { 0 };
        Iterator localIterator3 = localArrayList2.iterator();
        while (localIterator3.hasNext())
        {
          final Account localAccount1 = (Account)localIterator3.next();
          AccountLibrary localAccountLibrary1 = (AccountLibrary)this.mLibraries.get(localAccount1);
          new LibraryLoader(this.mSQLiteLibrary, localAccountLibrary1, this.mNotificationHandler, this.mBackgroundHandler).load(new Runnable()
          {
            public final void run()
            {
              Object[] arrayOfObject1 = new Object[1];
              arrayOfObject1[0] = FinskyLog.scrubPii(localAccount1.name);
              FinskyLog.d("Loaded library for account: %s", arrayOfObject1);
              int[] arrayOfInt = arrayOfInt;
              arrayOfInt[0] = (1 + arrayOfInt[0]);
              if (arrayOfInt[0] == localArrayList2.size())
              {
                Object[] arrayOfObject2 = new Object[1];
                arrayOfObject2[0] = Integer.valueOf(localArrayList2.size());
                FinskyLog.d("Finished loading %d libraries.", arrayOfObject2);
                Libraries.this.fireAllLibrariesLoaded();
                Libraries.this.runAndClearLoadingCallbacks();
                Libraries.access$202(Libraries.this, i);
              }
            }
          });
        }
        this.mLoadHasBeenCalled = true;
      }
    }
  }
  
  public final void remove(LibraryEntry paramLibraryEntry)
  {
    throw new UnsupportedOperationException();
  }
  
  public final void removeListener(Listener paramListener)
  {
    try
    {
      this.mListeners.remove(paramListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final int size()
  {
    int i = 0;
    try
    {
      int j = this.mLibraryList.size();
      for (int k = 0; k < j; k++)
      {
        int m = ((AccountLibrary)this.mLibraryList.get(k)).size();
        i += m;
      }
      return i;
    }
    finally {}
  }
  
  public static abstract interface Listener
  {
    public abstract void onAllLibrariesLoaded();
    
    public abstract void onLibraryContentsChanged$40bdb4b1();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.Libraries
 * JD-Core Version:    0.7.0.1
 */