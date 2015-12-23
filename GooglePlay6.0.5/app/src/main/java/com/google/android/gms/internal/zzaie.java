package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;

public final class zzaie<M extends zzaid<M>, T>
{
  public final int tag;
  protected final int type;
  protected final Class<T> zzcjb;
  protected final boolean zzcjc;
  
  private int zzaA(Object paramObject)
  {
    int i = zzain.zzsF(this.tag);
    switch (this.type)
    {
    default: 
      throw new IllegalArgumentException("Unknown type " + this.type);
    case 10: 
      zzaik localzzaik = (zzaik)paramObject;
      return 2 * zzaic.zzsv(i) + localzzaik.getSerializedSize();
    }
    return zzaic.zzc(i, (zzaik)paramObject);
  }
  
  private void zzb(Object paramObject, zzaic paramzzaic)
  {
    try
    {
      paramzzaic.zzsw(this.tag);
      switch (this.type)
      {
      default: 
        throw new IllegalArgumentException("Unknown type " + this.type);
      }
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
    zzaik localzzaik = (zzaik)paramObject;
    int i = zzain.zzsF(this.tag);
    localzzaik.writeTo(paramzzaic);
    paramzzaic.zzaa(i, 4);
    return;
    paramzzaic.zzc((zzaik)paramObject);
  }
  
  final void zza(Object paramObject, zzaic paramzzaic)
    throws IOException
  {
    if (this.zzcjc)
    {
      int i = Array.getLength(paramObject);
      for (int j = 0; j < i; j++)
      {
        Object localObject = Array.get(paramObject, j);
        if (localObject != null) {
          zzb(localObject, paramzzaic);
        }
      }
    }
    zzb(paramObject, paramzzaic);
  }
  
  final int zzay(Object paramObject)
  {
    int i = 0;
    if (this.zzcjc)
    {
      int j = Array.getLength(paramObject);
      for (int k = 0; k < j; k++) {
        if (Array.get(paramObject, k) != null) {
          i += zzaA(Array.get(paramObject, k));
        }
      }
    }
    i = zzaA(paramObject);
    return i;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaie
 * JD-Core Version:    0.7.0.1
 */