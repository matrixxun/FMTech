package com.google.android.finsky.library;

public abstract interface LibraryHasher
{
  public abstract void add(long paramLong);
  
  public abstract long compute();
  
  public abstract void remove(long paramLong);
  
  public abstract void reset();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryHasher
 * JD-Core Version:    0.7.0.1
 */