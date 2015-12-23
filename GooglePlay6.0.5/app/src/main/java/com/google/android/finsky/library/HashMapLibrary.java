package com.google.android.finsky.library;

import android.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapLibrary
  extends HashingLibrary
{
  private final int mBackendId;
  private final Map<LibraryEntry, LibraryEntry> mEntries = new HashMap();
  
  public HashMapLibrary(int paramInt, LibraryHasher paramLibraryHasher)
  {
    super(paramLibraryHasher);
    this.mBackendId = paramInt;
  }
  
  public void add(LibraryEntry paramLibraryEntry)
  {
    try
    {
      super.add(paramLibraryEntry);
      this.mEntries.put(paramLibraryEntry, paramLibraryEntry);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final boolean contains(LibraryEntry paramLibraryEntry)
  {
    try
    {
      boolean bool = this.mEntries.containsKey(paramLibraryEntry);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void dumpState(String paramString1, String paramString2)
  {
    Log.d("FinskyLibrary", paramString2 + "Library (" + paramString1 + ") {");
    Log.d("FinskyLibrary", paramString2 + "  backend=" + this.mBackendId);
    Log.d("FinskyLibrary", paramString2 + "  entryCount=" + this.mEntries.size());
    Log.d("FinskyLibrary", paramString2 + "}");
  }
  
  public final LibraryEntry get(LibraryEntry paramLibraryEntry)
  {
    return (LibraryEntry)this.mEntries.get(paramLibraryEntry);
  }
  
  public Iterator<LibraryEntry> iterator()
  {
    try
    {
      Iterator localIterator = this.mEntries.values().iterator();
      return localIterator;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void remove(LibraryEntry paramLibraryEntry)
  {
    try
    {
      super.remove(paramLibraryEntry);
      this.mEntries.remove(paramLibraryEntry);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void reset()
  {
    try
    {
      super.reset();
      this.mEntries.clear();
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
    try
    {
      int i = this.mEntries.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.mBackendId);
    arrayOfObject[1] = Integer.valueOf(size());
    return String.format("{backend=%d, num entries=%d}", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.HashMapLibrary
 * JD-Core Version:    0.7.0.1
 */