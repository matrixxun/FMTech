package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class zzb<T>
  implements Iterator<T>
{
  protected final DataBuffer<T> zzarp;
  protected int zzarq;
  
  public zzb(DataBuffer<T> paramDataBuffer)
  {
    this.zzarp = ((DataBuffer)zzx.zzC(paramDataBuffer));
    this.zzarq = -1;
  }
  
  public final boolean hasNext()
  {
    return this.zzarq < -1 + this.zzarp.getCount();
  }
  
  public final T next()
  {
    if (!hasNext()) {
      throw new NoSuchElementException("Cannot advance the iterator beyond " + this.zzarq);
    }
    DataBuffer localDataBuffer = this.zzarp;
    int i = 1 + this.zzarq;
    this.zzarq = i;
    return localDataBuffer.get(i);
  }
  
  public final void remove()
  {
    throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzb
 * JD-Core Version:    0.7.0.1
 */