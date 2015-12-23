package com.google.android.finsky.library;

import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class LibraryWithSubscriptions
  extends HashMapLibrary
{
  protected LibraryWithSubscriptions(int paramInt, LibraryHasher paramLibraryHasher)
  {
    super(paramInt, paramLibraryHasher);
  }
  
  public final List<LibrarySubscriptionEntry> getSubscriptionsList()
  {
    ArrayList localArrayList = Lists.newArrayList(size());
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      LibraryEntry localLibraryEntry = (LibraryEntry)localIterator.next();
      if (!shouldExcludeFromSubscriptionsList(localLibraryEntry)) {
        localArrayList.add((LibrarySubscriptionEntry)localLibraryEntry);
      }
    }
    return localArrayList;
  }
  
  protected boolean shouldExcludeFromSubscriptionsList(LibraryEntry paramLibraryEntry)
  {
    return !(paramLibraryEntry instanceof LibrarySubscriptionEntry);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryWithSubscriptions
 * JD-Core Version:    0.7.0.1
 */