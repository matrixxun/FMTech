package com.google.android.finsky.library;

public abstract class HashingLibrary
  implements Library
{
  final LibraryHasher mHasher;
  
  public HashingLibrary(LibraryHasher paramLibraryHasher)
  {
    this.mHasher = paramLibraryHasher;
  }
  
  public void add(LibraryEntry paramLibraryEntry)
  {
    if (!contains(paramLibraryEntry)) {
      this.mHasher.add(paramLibraryEntry.mDocumentHash);
    }
  }
  
  public abstract void dumpState(String paramString1, String paramString2);
  
  public void remove(LibraryEntry paramLibraryEntry)
  {
    LibraryEntry localLibraryEntry = get(paramLibraryEntry);
    if (localLibraryEntry != null) {
      this.mHasher.remove(localLibraryEntry.mDocumentHash);
    }
  }
  
  public void reset()
  {
    this.mHasher.reset();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.HashingLibrary
 * JD-Core Version:    0.7.0.1
 */