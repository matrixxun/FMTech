package com.google.android.finsky.library;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AccountLibrary
  implements Library
{
  private static final String[] EMPTY_AUTO_ACQUIRE_TAGS = new String[0];
  public static final String[] LIBRARY_IDS;
  public static final String LIBRARY_ID_APPS = "3";
  public static final String LIBRARY_ID_COMMERCE;
  public static final String LIBRARY_ID_MAGAZINE;
  public static final String LIBRARY_ID_MUSIC;
  public static final String LIBRARY_ID_OCEAN = "1";
  public static final String LIBRARY_ID_YOUTUBE = "4";
  public final Account mAccount;
  private String[] mAutoAcquireTags = EMPTY_AUTO_ACQUIRE_TAGS;
  public final Map<String, HashingLibrary> mLibraries = new HashMap();
  private final List<Listener> mListeners = new ArrayList();
  private boolean mListenersEnabled = true;
  private final Handler mNotificationHandler;
  private final Map<String, byte[]> mTokens = new HashMap();
  
  static
  {
    LIBRARY_ID_MUSIC = "2";
    LIBRARY_ID_MAGAZINE = "6";
    LIBRARY_ID_COMMERCE = "10";
    String[] arrayOfString = new String[8];
    arrayOfString[0] = LIBRARY_ID_APPS;
    arrayOfString[1] = LIBRARY_ID_OCEAN;
    arrayOfString[2] = LIBRARY_ID_YOUTUBE;
    arrayOfString[3] = LIBRARY_ID_MUSIC;
    arrayOfString[4] = LIBRARY_ID_MAGAZINE;
    arrayOfString[5] = LIBRARY_ID_COMMERCE;
    arrayOfString[6] = "u-wl";
    arrayOfString[7] = "u-pl";
    LIBRARY_IDS = arrayOfString;
  }
  
  public AccountLibrary(Account paramAccount, Handler paramHandler)
  {
    this.mAccount = paramAccount;
    this.mNotificationHandler = paramHandler;
    this.mLibraries.put(LIBRARY_ID_APPS, new AppLibrary(new SumHasher()));
    this.mLibraries.put(LIBRARY_ID_MUSIC, new MusicLibrary(new SumHasher()));
    this.mLibraries.put(LIBRARY_ID_OCEAN, new HashMapLibrary(1, new SumHasher()));
    this.mLibraries.put(LIBRARY_ID_YOUTUBE, new HashMapLibrary(4, new SumHasher()));
    this.mLibraries.put(LIBRARY_ID_MAGAZINE, new MagazinesLibrary(new SumHasher()));
    this.mLibraries.put(LIBRARY_ID_COMMERCE, new HashMapLibrary(10, new SumHasher()));
    this.mLibraries.put("u-wl", new HashMapLibrary(0, new SumHasher()));
    this.mLibraries.put("u-pl", new HashMapLibrary(0, new SumHasher()));
  }
  
  public static int getBackendFromLibraryId(String paramString)
  {
    if (LIBRARY_ID_APPS.equals(paramString)) {
      return 3;
    }
    if (LIBRARY_ID_OCEAN.equals(paramString)) {
      return 1;
    }
    if (LIBRARY_ID_YOUTUBE.equals(paramString)) {
      return 4;
    }
    if (LIBRARY_ID_MUSIC.equals(paramString)) {
      return 2;
    }
    if (LIBRARY_ID_MAGAZINE.equals(paramString)) {
      return 6;
    }
    if (LIBRARY_ID_COMMERCE.equals(paramString)) {
      return 10;
    }
    if (isLibraryMultiContainer(paramString)) {
      return 0;
    }
    throw new IllegalArgumentException("Invalid libraryId: " + paramString);
  }
  
  public static String getLibraryIdFromBackend(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    default: 
      return null;
    case 3: 
      return LIBRARY_ID_APPS;
    case 1: 
      return LIBRARY_ID_OCEAN;
    case 4: 
      return LIBRARY_ID_YOUTUBE;
    case 2: 
      return LIBRARY_ID_MUSIC;
    case 6: 
      return LIBRARY_ID_MAGAZINE;
    }
    return LIBRARY_ID_COMMERCE;
  }
  
  public static boolean isLibraryMultiContainer(String paramString)
  {
    return ("u-wl".equals(paramString)) || ("u-pl".equals(paramString));
  }
  
  private void notifyListeners()
  {
    if (!this.mListenersEnabled) {
      return;
    }
    final ArrayList localArrayList = new ArrayList(this.mListeners);
    this.mNotificationHandler.post(new Runnable()
    {
      public final void run()
      {
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          ((AccountLibrary.Listener)localIterator.next()).onLibraryChange();
        }
      }
    });
  }
  
  public final void add(LibraryEntry paramLibraryEntry)
  {
    try
    {
      if (!this.mAccount.name.equals(paramLibraryEntry.mAccountName)) {
        throw new IllegalArgumentException("Invalid account.");
      }
    }
    finally {}
    HashingLibrary localHashingLibrary = (HashingLibrary)this.mLibraries.get(paramLibraryEntry.mLibraryId);
    if (localHashingLibrary != null)
    {
      localHashingLibrary.add(paramLibraryEntry);
      notifyListeners();
    }
  }
  
  public final void addAll(Collection<? extends LibraryEntry> paramCollection)
  {
    try
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        add((LibraryEntry)localIterator.next());
      }
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
  
  /* Error */
  public final boolean contains(LibraryEntry paramLibraryEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 76	com/google/android/finsky/library/AccountLibrary:mLibraries	Ljava/util/Map;
    //   6: aload_1
    //   7: getfield 178	com/google/android/finsky/library/LibraryEntry:mLibraryId	Ljava/lang/String;
    //   10: invokeinterface 182 2 0
    //   15: checkcast 6	com/google/android/finsky/library/Library
    //   18: astore_3
    //   19: aload_3
    //   20: ifnull +21 -> 41
    //   23: aload_3
    //   24: aload_1
    //   25: invokeinterface 216 2 0
    //   30: istore 4
    //   32: iload 4
    //   34: istore 5
    //   36: aload_0
    //   37: monitorexit
    //   38: iload 5
    //   40: ireturn
    //   41: iconst_0
    //   42: istore 5
    //   44: goto -8 -> 36
    //   47: astore_2
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_2
    //   51: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	this	AccountLibrary
    //   0	52	1	paramLibraryEntry	LibraryEntry
    //   47	4	2	localObject	Object
    //   18	6	3	localLibrary	Library
    //   30	3	4	bool1	boolean
    //   34	9	5	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	19	47	finally
    //   23	32	47	finally
  }
  
  public final void disableListeners()
  {
    try
    {
      this.mListenersEnabled = false;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void enableListeners()
  {
    try
    {
      this.mListenersEnabled = true;
      notifyListeners();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final LibraryEntry get(LibraryEntry paramLibraryEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 76	com/google/android/finsky/library/AccountLibrary:mLibraries	Ljava/util/Map;
    //   6: aload_1
    //   7: getfield 178	com/google/android/finsky/library/LibraryEntry:mLibraryId	Ljava/lang/String;
    //   10: invokeinterface 182 2 0
    //   15: checkcast 6	com/google/android/finsky/library/Library
    //   18: astore_3
    //   19: aload_3
    //   20: ifnull +21 -> 41
    //   23: aload_3
    //   24: aload_1
    //   25: invokeinterface 221 2 0
    //   30: astore 4
    //   32: aload 4
    //   34: astore 5
    //   36: aload_0
    //   37: monitorexit
    //   38: aload 5
    //   40: areturn
    //   41: aconst_null
    //   42: astore 5
    //   44: goto -8 -> 36
    //   47: astore_2
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_2
    //   51: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	this	AccountLibrary
    //   0	52	1	paramLibraryEntry	LibraryEntry
    //   47	4	2	localObject	Object
    //   18	6	3	localLibrary	Library
    //   30	3	4	localLibraryEntry1	LibraryEntry
    //   34	9	5	localLibraryEntry2	LibraryEntry
    // Exception table:
    //   from	to	target	type
    //   2	19	47	finally
    //   23	32	47	finally
  }
  
  public final LibraryAppEntry getAppEntry(String paramString)
  {
    try
    {
      LibraryAppEntry localLibraryAppEntry = (LibraryAppEntry)((AppLibrary)this.mLibraries.get(LIBRARY_ID_APPS)).get(new LibraryEntry(null, LIBRARY_ID_APPS, 3, paramString, 1, 1));
      return localLibraryAppEntry;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final String[] getAutoAcquireTags()
  {
    try
    {
      String[] arrayOfString = this.mAutoAcquireTags;
      return arrayOfString;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final LibraryInAppEntry getInAppEntry(String paramString)
  {
    try
    {
      LibraryInAppEntry localLibraryInAppEntry = ((AppLibrary)this.mLibraries.get(LIBRARY_ID_APPS)).getInAppEntry(paramString);
      return localLibraryInAppEntry;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<LibraryInAppEntry> getInAppPurchasesForPackage(String paramString)
  {
    ArrayList localArrayList;
    try
    {
      AppLibrary localAppLibrary = (AppLibrary)this.mLibraries.get(LIBRARY_ID_APPS);
      localArrayList = new ArrayList();
      Iterator localIterator = localAppLibrary.mInAppDocIdSet.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (TextUtils.equals(DocUtils.getPackageNameForInApp(str), paramString)) {
          localArrayList.add(localAppLibrary.getInAppEntry(str));
        }
      }
    }
    finally {}
    return localArrayList;
  }
  
  public final List<LibraryInAppSubscriptionEntry> getInAppSubscriptionsList()
  {
    ArrayList localArrayList;
    try
    {
      AppLibrary localAppLibrary = (AppLibrary)this.mLibraries.get(LIBRARY_ID_APPS);
      localArrayList = new ArrayList();
      Iterator localIterator = localAppLibrary.mSubscriptionsDocIdSet.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(localAppLibrary.getSubscriptionEntry((String)localIterator.next()));
      }
    }
    finally {}
    return localArrayList;
  }
  
  public final HashingLibrary getLibrary(String paramString)
  {
    return (HashingLibrary)this.mLibraries.get(paramString);
  }
  
  public final LibrarySubscriptionEntry getMagazinesSubscriptionEntry(String paramString)
  {
    try
    {
      LibrarySubscriptionEntry localLibrarySubscriptionEntry = (LibrarySubscriptionEntry)((MagazinesLibrary)this.mLibraries.get(LIBRARY_ID_MAGAZINE)).get(new LibraryEntry(null, LIBRARY_ID_MAGAZINE, 6, paramString, 15, 1));
      return localLibrarySubscriptionEntry;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<LibrarySubscriptionEntry> getMagazinesSubscriptionsList()
  {
    try
    {
      List localList = ((MagazinesLibrary)this.mLibraries.get(LIBRARY_ID_MAGAZINE)).getSubscriptionsList();
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<LibrarySubscriptionEntry> getMusicSubscriptionsList()
  {
    try
    {
      List localList = ((MusicLibrary)this.mLibraries.get(LIBRARY_ID_MUSIC)).getSubscriptionsList();
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final byte[] getServerToken(String paramString)
  {
    try
    {
      byte[] arrayOfByte = (byte[])this.mTokens.get(paramString);
      return arrayOfByte;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<LibraryInAppSubscriptionEntry> getSubscriptionPurchasesForPackage(String paramString)
  {
    ArrayList localArrayList;
    try
    {
      AppLibrary localAppLibrary = (AppLibrary)this.mLibraries.get(LIBRARY_ID_APPS);
      localArrayList = new ArrayList();
      Iterator localIterator = localAppLibrary.mSubscriptionsDocIdSet.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (TextUtils.equals(DocUtils.getPackageNameForSubscription(str), paramString)) {
          localArrayList.add(localAppLibrary.getSubscriptionEntry(str));
        }
      }
    }
    finally {}
    return localArrayList;
  }
  
  public final Iterator<LibraryEntry> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  public final void remove(LibraryEntry paramLibraryEntry)
  {
    try
    {
      if (!this.mAccount.name.equals(paramLibraryEntry.mAccountName)) {
        throw new IllegalArgumentException();
      }
    }
    finally {}
    Library localLibrary = (Library)this.mLibraries.get(paramLibraryEntry.mLibraryId);
    if (localLibrary != null)
    {
      localLibrary.remove(paramLibraryEntry);
      notifyListeners();
    }
  }
  
  /* Error */
  public final void resetLibrary(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 76	com/google/android/finsky/library/AccountLibrary:mLibraries	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 182 2 0
    //   12: checkcast 184	com/google/android/finsky/library/HashingLibrary
    //   15: astore_3
    //   16: aload_3
    //   17: ifnonnull +24 -> 41
    //   20: ldc_w 295
    //   23: iconst_1
    //   24: anewarray 4	java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_1
    //   30: aastore
    //   31: invokestatic 301	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   34: aload_0
    //   35: invokespecial 188	com/google/android/finsky/library/AccountLibrary:notifyListeners	()V
    //   38: aload_0
    //   39: monitorexit
    //   40: return
    //   41: aload_3
    //   42: invokevirtual 304	com/google/android/finsky/library/HashingLibrary:reset	()V
    //   45: goto -11 -> 34
    //   48: astore_2
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_2
    //   52: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	AccountLibrary
    //   0	53	1	paramString	String
    //   48	4	2	localObject	Object
    //   15	27	3	localHashingLibrary	HashingLibrary
    // Exception table:
    //   from	to	target	type
    //   2	16	48	finally
    //   20	34	48	finally
    //   34	38	48	finally
    //   41	45	48	finally
  }
  
  public final void setAutoAcquireTags(String[] paramArrayOfString)
  {
    try
    {
      this.mAutoAcquireTags = paramArrayOfString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setServerToken(String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      this.mTokens.put(paramString, paramArrayOfByte);
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
      Iterator localIterator = this.mLibraries.values().iterator();
      while (localIterator.hasNext())
      {
        int j = ((HashingLibrary)localIterator.next()).size();
        i += j;
      }
      return i;
    }
    finally {}
  }
  
  public final boolean supportsLibrary(String paramString)
  {
    return this.mLibraries.containsKey(paramString);
  }
  
  @SuppressLint({"DefaultLocale"})
  public final String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = FinskyLog.scrubPii(this.mAccount.name);
    arrayOfObject[1] = Integer.valueOf(((HashingLibrary)this.mLibraries.get(LIBRARY_ID_APPS)).size());
    return String.format("{account=%s numapps=%d}", arrayOfObject);
  }
  
  public static abstract interface Listener
  {
    public abstract void onLibraryChange();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.AccountLibrary
 * JD-Core Version:    0.7.0.1
 */