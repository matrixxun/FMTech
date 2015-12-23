package com.google.android.finsky.activities.myaccount;

import android.support.v4.util.ArrayMap;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.protos.DocV2;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MyAccountLibrarySubscriptionEntries
{
  private final AccountLibrary mAccountLibrary;
  boolean mHasNewsstandSubscriptions;
  private Map<String, LibrarySubscriptionEntry> mLibraryEntries;
  
  public MyAccountLibrarySubscriptionEntries(AccountLibrary paramAccountLibrary)
  {
    this.mAccountLibrary = paramAccountLibrary;
    this.mLibraryEntries = new ArrayMap();
    Iterator localIterator1 = paramAccountLibrary.getInAppSubscriptionsList().iterator();
    while (localIterator1.hasNext())
    {
      LibrarySubscriptionEntry localLibrarySubscriptionEntry2 = (LibrarySubscriptionEntry)localIterator1.next();
      this.mLibraryEntries.put(localLibrarySubscriptionEntry2.mDocId, localLibrarySubscriptionEntry2);
    }
    Iterator localIterator2 = paramAccountLibrary.getMusicSubscriptionsList().iterator();
    while (localIterator2.hasNext())
    {
      LibrarySubscriptionEntry localLibrarySubscriptionEntry1 = (LibrarySubscriptionEntry)localIterator2.next();
      this.mLibraryEntries.put(localLibrarySubscriptionEntry1.mDocId, localLibrarySubscriptionEntry1);
    }
    if (paramAccountLibrary.getMagazinesSubscriptionsList().size() > 0) {}
    for (boolean bool = true;; bool = false)
    {
      this.mHasNewsstandSubscriptions = bool;
      return;
    }
  }
  
  public final LibrarySubscriptionEntry getLibrarySubscriptionEntry(Document paramDocument)
  {
    if (this.mLibraryEntries.containsKey(paramDocument.mDocument.backendDocid))
    {
      LibrarySubscriptionEntry localLibrarySubscriptionEntry = (LibrarySubscriptionEntry)this.mLibraryEntries.get(paramDocument.mDocument.backendDocid);
      return (LibrarySubscriptionEntry)this.mAccountLibrary.get(localLibrarySubscriptionEntry);
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountLibrarySubscriptionEntries
 * JD-Core Version:    0.7.0.1
 */