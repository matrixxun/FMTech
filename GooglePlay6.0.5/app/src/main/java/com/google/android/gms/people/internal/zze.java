package com.google.android.gms.people.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class zze<T>
  implements Iterable<T>, Iterator<T>
{
  public final boolean hasNext()
  {
    return false;
  }
  
  public final Iterator<T> iterator()
  {
    return this;
  }
  
  public final T next()
  {
    throw new NoSuchElementException();
  }
  
  public final void remove()
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zze
 * JD-Core Version:    0.7.0.1
 */