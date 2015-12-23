package com.google.android.finsky.library;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public final class AppLibrary
  extends HashMapLibrary
{
  Set<String> mInAppDocIdSet = new HashSet();
  Set<String> mSubscriptionsDocIdSet = new HashSet();
  
  public AppLibrary(LibraryHasher paramLibraryHasher)
  {
    super(3, paramLibraryHasher);
  }
  
  public final void add(LibraryEntry paramLibraryEntry)
  {
    int i = paramLibraryEntry.mDocType;
    String str = paramLibraryEntry.mDocId;
    if (i == 15) {
      this.mSubscriptionsDocIdSet.add(str);
    }
    for (;;)
    {
      super.add(paramLibraryEntry);
      return;
      if (i == 11) {
        this.mInAppDocIdSet.add(str);
      }
    }
  }
  
  public final void dumpState(String paramString1, String paramString2)
  {
    Log.d("FinskyLibrary", paramString2 + "AppLibrary (" + paramString1 + ") {");
    Log.d("FinskyLibrary", paramString2 + "  totalCount=" + size());
    Log.d("FinskyLibrary", paramString2 + "  subscriptionsCount=" + this.mSubscriptionsDocIdSet.size());
    Log.d("FinskyLibrary", paramString2 + "}");
  }
  
  public final LibraryInAppEntry getInAppEntry(String paramString)
  {
    return (LibraryInAppEntry)get(new LibraryEntry(null, AccountLibrary.LIBRARY_ID_APPS, 3, paramString, 11, 1));
  }
  
  public final LibraryInAppSubscriptionEntry getSubscriptionEntry(String paramString)
  {
    return (LibraryInAppSubscriptionEntry)get(new LibraryEntry(null, AccountLibrary.LIBRARY_ID_APPS, 3, paramString, 15, 1));
  }
  
  public final void remove(LibraryEntry paramLibraryEntry)
  {
    int i = paramLibraryEntry.mDocType;
    String str = paramLibraryEntry.mDocId;
    if (i == 15) {
      this.mSubscriptionsDocIdSet.remove(str);
    }
    for (;;)
    {
      super.remove(paramLibraryEntry);
      return;
      if (i == 11) {
        this.mInAppDocIdSet.remove(str);
      }
    }
  }
  
  public final void reset()
  {
    try
    {
      this.mSubscriptionsDocIdSet.clear();
      this.mInAppDocIdSet.clear();
      super.reset();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final String toString()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(size());
    return String.format("{num apps=%d}", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.AppLibrary
 * JD-Core Version:    0.7.0.1
 */