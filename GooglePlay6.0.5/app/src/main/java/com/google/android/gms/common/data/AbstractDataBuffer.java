package com.google.android.gms.common.data;

import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
  implements DataBuffer<T>
{
  public final DataHolder zzapd;
  
  public AbstractDataBuffer(DataHolder paramDataHolder)
  {
    this.zzapd = paramDataHolder;
    if (this.zzapd != null) {
      this.zzapd.zzarC = this;
    }
  }
  
  public int getCount()
  {
    if (this.zzapd == null) {
      return 0;
    }
    return this.zzapd.zzarB;
  }
  
  public Iterator<T> iterator()
  {
    return new zzb(this);
  }
  
  public final void release()
  {
    if (this.zzapd != null) {
      this.zzapd.close();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.AbstractDataBuffer
 * JD-Core Version:    0.7.0.1
 */