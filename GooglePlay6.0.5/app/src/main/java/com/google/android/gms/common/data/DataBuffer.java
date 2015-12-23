package com.google.android.gms.common.data;

import com.google.android.gms.common.api.Releasable;

public abstract interface DataBuffer<T>
  extends Releasable, Iterable<T>
{
  public abstract T get(int paramInt);
  
  public abstract int getCount();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.DataBuffer
 * JD-Core Version:    0.7.0.1
 */