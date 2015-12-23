package com.google.android.gms.internal;

import java.io.IOException;

public final class zzaib
{
  final byte[] buffer;
  int zzciQ;
  private int zzciR;
  private int zzciS;
  private int zzciT;
  private int zzciU;
  private int zzciV = 2147483647;
  private int zzciW;
  private int zzciX = 64;
  private int zzciY = 67108864;
  
  private zzaib(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer = paramArrayOfByte;
    this.zzciQ = paramInt1;
    this.zzciR = (paramInt1 + paramInt2);
    this.zzciT = paramInt1;
  }
  
  private void zzOR()
  {
    this.zzciR += this.zzciS;
    int i = this.zzciR;
    if (i > this.zzciV)
    {
      this.zzciS = (i - this.zzciV);
      this.zzciR -= this.zzciS;
      return;
    }
    this.zzciS = 0;
  }
  
  private byte zzOU()
    throws IOException
  {
    if (this.zzciT == this.zzciR) {
      throw zzaij.zzPa();
    }
    byte[] arrayOfByte = this.buffer;
    int i = this.zzciT;
    this.zzciT = (i + 1);
    return arrayOfByte[i];
  }
  
  public static zzaib zza(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zzaib(paramArrayOfByte, 0, paramInt2);
  }
  
  private byte[] zzso(int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw zzaij.zzPb();
    }
    if (paramInt + this.zzciT > this.zzciV)
    {
      zzsp(this.zzciV - this.zzciT);
      throw zzaij.zzPa();
    }
    if (paramInt <= this.zzciR - this.zzciT)
    {
      byte[] arrayOfByte = new byte[paramInt];
      System.arraycopy(this.buffer, this.zzciT, arrayOfByte, 0, paramInt);
      this.zzciT = (paramInt + this.zzciT);
      return arrayOfByte;
    }
    throw zzaij.zzPa();
  }
  
  private void zzsp(int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw zzaij.zzPb();
    }
    if (paramInt + this.zzciT > this.zzciV)
    {
      zzsp(this.zzciV - this.zzciT);
      throw zzaij.zzPa();
    }
    if (paramInt <= this.zzciR - this.zzciT)
    {
      this.zzciT = (paramInt + this.zzciT);
      return;
    }
    throw zzaij.zzPa();
  }
  
  public final int getPosition()
  {
    return this.zzciT - this.zzciQ;
  }
  
  public final byte[] readBytes()
    throws IOException
  {
    int i = zzON();
    if ((i <= this.zzciR - this.zzciT) && (i > 0))
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.buffer, this.zzciT, arrayOfByte, 0, i);
      this.zzciT = (i + this.zzciT);
      return arrayOfByte;
    }
    if (i == 0) {
      return zzain.zzcjr;
    }
    return zzso(i);
  }
  
  public final String readString()
    throws IOException
  {
    int i = zzON();
    if ((i <= this.zzciR - this.zzciT) && (i > 0))
    {
      String str = new String(this.buffer, this.zzciT, i, "UTF-8");
      this.zzciT = (i + this.zzciT);
      return str;
    }
    return new String(zzso(i), "UTF-8");
  }
  
  public final int zzOF()
    throws IOException
  {
    if (this.zzciT == this.zzciR) {}
    for (int i = 1; i != 0; i = 0)
    {
      this.zzciU = 0;
      return 0;
    }
    this.zzciU = zzON();
    if (this.zzciU == 0) {
      throw zzaij.zzPd();
    }
    return this.zzciU;
  }
  
  public final boolean zzOK()
    throws IOException
  {
    return zzON() != 0;
  }
  
  public final int zzON()
    throws IOException
  {
    int i = zzOU();
    if (i >= 0) {}
    int i4;
    do
    {
      return i;
      int j = i & 0x7F;
      int k = zzOU();
      if (k >= 0) {
        return j | k << 7;
      }
      int m = j | (k & 0x7F) << 7;
      int n = zzOU();
      if (n >= 0) {
        return m | n << 14;
      }
      int i1 = m | (n & 0x7F) << 14;
      int i2 = zzOU();
      if (i2 >= 0) {
        return i1 | i2 << 21;
      }
      int i3 = i1 | (i2 & 0x7F) << 21;
      i4 = zzOU();
      i = i3 | i4 << 28;
    } while (i4 >= 0);
    for (int i5 = 0;; i5++)
    {
      if (i5 >= 5) {
        break label151;
      }
      if (zzOU() >= 0) {
        break;
      }
    }
    label151:
    throw zzaij.zzPc();
  }
  
  public final long zzOO()
    throws IOException
  {
    int i = 0;
    long l = 0L;
    while (i < 64)
    {
      int j = zzOU();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0) {
        return l;
      }
      i += 7;
    }
    throw zzaij.zzPc();
  }
  
  public final int zzOP()
    throws IOException
  {
    int i = zzOU();
    int j = zzOU();
    int k = zzOU();
    int m = zzOU();
    return i & 0xFF | (j & 0xFF) << 8 | (k & 0xFF) << 16 | (m & 0xFF) << 24;
  }
  
  public final long zzOQ()
    throws IOException
  {
    int i = zzOU();
    int j = zzOU();
    int k = zzOU();
    int m = zzOU();
    int n = zzOU();
    int i1 = zzOU();
    int i2 = zzOU();
    int i3 = zzOU();
    return 0xFF & i | (0xFF & j) << 8 | (0xFF & k) << 16 | (0xFF & m) << 24 | (0xFF & n) << 32 | (0xFF & i1) << 40 | (0xFF & i2) << 48 | (0xFF & i3) << 56;
  }
  
  public final int zzOS()
  {
    if (this.zzciV == 2147483647) {
      return -1;
    }
    int i = this.zzciT;
    return this.zzciV - i;
  }
  
  public final void zza(zzaik paramzzaik)
    throws IOException
  {
    int i = zzON();
    if (this.zzciW >= this.zzciX) {
      throw zzaij.zzPg();
    }
    int j = zzsl(i);
    this.zzciW = (1 + this.zzciW);
    paramzzaik.mergeFrom(this);
    zzsi(0);
    this.zzciW = (-1 + this.zzciW);
    zzsm(j);
  }
  
  public final void zzsi(int paramInt)
    throws zzaij
  {
    if (this.zzciU != paramInt) {
      throw zzaij.zzPe();
    }
  }
  
  public final boolean zzsj(int paramInt)
    throws IOException
  {
    switch (zzain.zzsE(paramInt))
    {
    default: 
      throw zzaij.zzPf();
    case 0: 
      zzON();
      return true;
    case 1: 
      zzOQ();
      return true;
    case 2: 
      zzsp(zzON());
      return true;
    case 3: 
      int i;
      do
      {
        i = zzOF();
      } while ((i != 0) && (zzsj(i)));
      zzsi(zzain.zzab(zzain.zzsF(paramInt), 4));
      return true;
    case 4: 
      return false;
    }
    zzOP();
    return true;
  }
  
  public final int zzsl(int paramInt)
    throws zzaij
  {
    if (paramInt < 0) {
      throw zzaij.zzPb();
    }
    int i = paramInt + this.zzciT;
    int j = this.zzciV;
    if (i > j) {
      throw zzaij.zzPa();
    }
    this.zzciV = i;
    zzOR();
    return j;
  }
  
  public final void zzsm(int paramInt)
  {
    this.zzciV = paramInt;
    zzOR();
  }
  
  public final void zzsn(int paramInt)
  {
    if (paramInt > this.zzciT - this.zzciQ) {
      throw new IllegalArgumentException("Position " + paramInt + " is beyond current " + (this.zzciT - this.zzciQ));
    }
    if (paramInt < 0) {
      throw new IllegalArgumentException("Bad position " + paramInt);
    }
    this.zzciT = (paramInt + this.zzciQ);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaib
 * JD-Core Version:    0.7.0.1
 */