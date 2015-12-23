package com.google.android.gms.internal;

public final class zzaig
  implements Cloneable
{
  static final zzaih zzcjd = new zzaih();
  int mSize;
  boolean zzcje = false;
  int[] zzcjf;
  zzaih[] zzcjg;
  
  zzaig()
  {
    this(10);
  }
  
  private zzaig(int paramInt)
  {
    int i = idealIntArraySize(paramInt);
    this.zzcjf = new int[i];
    this.zzcjg = new zzaih[i];
    this.mSize = 0;
  }
  
  static int idealIntArraySize(int paramInt)
  {
    int i = paramInt * 4;
    for (int j = 4;; j++) {
      if (j < 32)
      {
        if (i <= -12 + (1 << j)) {
          i = -12 + (1 << j);
        }
      }
      else {
        return i / 4;
      }
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    label147:
    label153:
    label157:
    for (;;)
    {
      return true;
      if (!(paramObject instanceof zzaig)) {
        return false;
      }
      zzaig localzzaig = (zzaig)paramObject;
      if (size() != localzzaig.size()) {
        return false;
      }
      int[] arrayOfInt1 = this.zzcjf;
      int[] arrayOfInt2 = localzzaig.zzcjf;
      int i = this.mSize;
      int j = 0;
      int k;
      label76:
      int n;
      if (j < i) {
        if (arrayOfInt1[j] != arrayOfInt2[j])
        {
          k = 0;
          if (k != 0)
          {
            zzaih[] arrayOfzzaih1 = this.zzcjg;
            zzaih[] arrayOfzzaih2 = localzzaig.zzcjg;
            int m = this.mSize;
            n = 0;
            label102:
            if (n >= m) {
              break label153;
            }
            if (arrayOfzzaih1[n].equals(arrayOfzzaih2[n])) {
              break label147;
            }
          }
        }
      }
      for (int i1 = 0;; i1 = 1)
      {
        if (i1 != 0) {
          break label157;
        }
        return false;
        j++;
        break;
        k = 1;
        break label76;
        n++;
        break label102;
      }
    }
  }
  
  final void gc()
  {
    int i = this.mSize;
    int[] arrayOfInt = this.zzcjf;
    zzaih[] arrayOfzzaih = this.zzcjg;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      zzaih localzzaih = arrayOfzzaih[j];
      if (localzzaih != zzcjd)
      {
        if (j != k)
        {
          arrayOfInt[k] = arrayOfInt[j];
          arrayOfzzaih[k] = localzzaih;
          arrayOfzzaih[j] = null;
        }
        k++;
      }
      j++;
    }
    this.zzcje = false;
    this.mSize = k;
  }
  
  public final int hashCode()
  {
    if (this.zzcje) {
      gc();
    }
    int i = 17;
    for (int j = 0; j < this.mSize; j++) {
      i = 31 * (i * 31 + this.zzcjf[j]) + this.zzcjg[j].hashCode();
    }
    return i;
  }
  
  public final boolean isEmpty()
  {
    return size() == 0;
  }
  
  final int size()
  {
    if (this.zzcje) {
      gc();
    }
    return this.mSize;
  }
  
  public final zzaig zzOY()
  {
    int i = 0;
    int j = size();
    zzaig localzzaig = new zzaig(j);
    System.arraycopy(this.zzcjf, 0, localzzaig.zzcjf, 0, j);
    while (i < j)
    {
      if (this.zzcjg[i] != null) {
        localzzaig.zzcjg[i] = this.zzcjg[i].zzOZ();
      }
      i++;
    }
    localzzaig.mSize = j;
    return localzzaig;
  }
  
  final zzaih zzsB(int paramInt)
  {
    if (this.zzcje) {
      gc();
    }
    return this.zzcjg[paramInt];
  }
  
  final int zzsC(int paramInt)
  {
    int i = -1 + this.mSize;
    int j = 0;
    int k = i;
    while (j <= k)
    {
      m = j + k >>> 1;
      int n = this.zzcjf[m];
      if (n < paramInt)
      {
        j = m + 1;
      }
      else
      {
        if (n <= paramInt) {
          break label69;
        }
        k = m - 1;
      }
    }
    int m = j ^ 0xFFFFFFFF;
    label69:
    return m;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaig
 * JD-Core Version:    0.7.0.1
 */