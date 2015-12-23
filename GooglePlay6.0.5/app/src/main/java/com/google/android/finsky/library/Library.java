package com.google.android.finsky.library;

public abstract interface Library
  extends Iterable<LibraryEntry>
{
  public abstract boolean contains(LibraryEntry paramLibraryEntry);
  
  public abstract LibraryEntry get(LibraryEntry paramLibraryEntry);
  
  public abstract void remove(LibraryEntry paramLibraryEntry);
  
  public abstract int size();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.Library
 * JD-Core Version:    0.7.0.1
 */