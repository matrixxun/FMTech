package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class zza<E>
  extends AbstractSet<E>
{
  private final ArrayMap<E, E> zzawH;
  
  public zza()
  {
    this.zzawH = new ArrayMap();
  }
  
  private zza(int paramInt)
  {
    this.zzawH = new ArrayMap(paramInt);
  }
  
  public zza(Collection<E> paramCollection)
  {
    this(paramCollection.size());
    addAll(paramCollection);
  }
  
  private boolean zza(zza<? extends E> paramzza)
  {
    int i = size();
    ArrayMap localArrayMap1 = this.zzawH;
    ArrayMap localArrayMap2 = paramzza.zzawH;
    int j = localArrayMap2.mSize;
    localArrayMap1.ensureCapacity(j + localArrayMap1.mSize);
    if (localArrayMap1.mSize == 0) {
      if (j > 0)
      {
        System.arraycopy(localArrayMap2.mHashes, 0, localArrayMap1.mHashes, 0, j);
        System.arraycopy(localArrayMap2.mArray, 0, localArrayMap1.mArray, 0, j << 1);
        localArrayMap1.mSize = j;
      }
    }
    for (;;)
    {
      int m = size();
      boolean bool = false;
      if (m > i) {
        bool = true;
      }
      return bool;
      for (int k = 0; k < j; k++) {
        localArrayMap1.put(localArrayMap2.keyAt(k), localArrayMap2.valueAt(k));
      }
    }
  }
  
  public final boolean add(E paramE)
  {
    if (this.zzawH.containsKey(paramE)) {
      return false;
    }
    this.zzawH.put(paramE, paramE);
    return true;
  }
  
  public final boolean addAll(Collection<? extends E> paramCollection)
  {
    if ((paramCollection instanceof zza)) {
      return zza((zza)paramCollection);
    }
    return super.addAll(paramCollection);
  }
  
  public final void clear()
  {
    this.zzawH.clear();
  }
  
  public final boolean contains(Object paramObject)
  {
    return this.zzawH.containsKey(paramObject);
  }
  
  public final Iterator<E> iterator()
  {
    return this.zzawH.keySet().iterator();
  }
  
  public final boolean remove(Object paramObject)
  {
    if (!this.zzawH.containsKey(paramObject)) {
      return false;
    }
    this.zzawH.remove(paramObject);
    return true;
  }
  
  public final int size()
  {
    return this.zzawH.size();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.util.zza
 * JD-Core Version:    0.7.0.1
 */