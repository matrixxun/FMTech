package com.google.android.finsky.library;

public final class SumHasher
  implements LibraryHasher
{
  private long mHash;
  
  public final void add(long paramLong)
  {
    this.mHash = (paramLong + this.mHash);
  }
  
  public final long compute()
  {
    return this.mHash;
  }
  
  public final void remove(long paramLong)
  {
    this.mHash -= paramLong;
  }
  
  public final void reset()
  {
    this.mHash = 0L;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.SumHasher
 * JD-Core Version:    0.7.0.1
 */