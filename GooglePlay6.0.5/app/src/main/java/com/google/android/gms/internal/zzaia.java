package com.google.android.gms.internal;

public final class zzaia
{
  final byte[] zzciN = new byte[256];
  int zzciO;
  int zzciP;
  
  public zzaia(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < 256; i++) {
      this.zzciN[i] = ((byte)i);
    }
    int j = 0;
    for (int k = 0; k < 256; k++)
    {
      j = 0xFF & j + this.zzciN[k] + paramArrayOfByte[(k % paramArrayOfByte.length)];
      int m = this.zzciN[k];
      this.zzciN[k] = this.zzciN[j];
      this.zzciN[j] = m;
    }
    this.zzciO = 0;
    this.zzciP = 0;
  }
  
  public final void zzS(byte[] paramArrayOfByte)
  {
    int i = this.zzciO;
    int j = this.zzciP;
    for (int k = 0; k < 256; k++)
    {
      i = 0xFF & i + 1;
      j = 0xFF & j + this.zzciN[i];
      int m = this.zzciN[i];
      this.zzciN[i] = this.zzciN[j];
      this.zzciN[j] = m;
      paramArrayOfByte[k] = ((byte)(paramArrayOfByte[k] ^ this.zzciN[(0xFF & this.zzciN[i] + this.zzciN[j])]));
    }
    this.zzciO = i;
    this.zzciP = j;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaia
 * JD-Core Version:    0.7.0.1
 */