package com.squareup.okhttp.internal.spdy;

public final class Settings
{
  int persistValue;
  int persisted;
  int set;
  final int[] values = new int[10];
  
  final int flags(int paramInt)
  {
    int i = 1;
    int j;
    int k;
    if ((i << paramInt & this.persisted) != 0)
    {
      j = i;
      k = 0;
      if (j != 0) {
        k = 2;
      }
      if ((i << paramInt & this.persistValue) == 0) {
        break label54;
      }
    }
    for (;;)
    {
      if (i != 0) {
        k |= 0x1;
      }
      return k;
      j = 0;
      break;
      label54:
      i = 0;
    }
  }
  
  final int getHeaderTableSize()
  {
    if ((0x2 & this.set) != 0) {
      return this.values[1];
    }
    return -1;
  }
  
  public final int getInitialWindowSize$134621()
  {
    if ((0x80 & this.set) != 0) {
      return this.values[7];
    }
    return 65536;
  }
  
  final boolean isSet(int paramInt)
  {
    return (1 << paramInt & this.set) != 0;
  }
  
  final Settings set(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= this.values.length) {
      return this;
    }
    int i = 1 << paramInt1;
    this.set = (i | this.set);
    if ((paramInt2 & 0x1) != 0)
    {
      this.persistValue = (i | this.persistValue);
      if ((paramInt2 & 0x2) == 0) {
        break label86;
      }
    }
    label86:
    for (this.persisted = (i | this.persisted);; this.persisted &= (i ^ 0xFFFFFFFF))
    {
      this.values[paramInt1] = paramInt3;
      return this;
      this.persistValue &= (i ^ 0xFFFFFFFF);
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Settings
 * JD-Core Version:    0.7.0.1
 */