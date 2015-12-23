package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.ByteBuffer;

final class zzat
  implements zzar
{
  private zzaic zzom;
  private byte[] zzon;
  private final int zzoo;
  
  public zzat(int paramInt)
  {
    this.zzoo = paramInt;
    reset();
  }
  
  public final void reset()
  {
    this.zzon = new byte[this.zzoo];
    byte[] arrayOfByte = this.zzon;
    this.zzom = zzaic.zzb(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public final void zza(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    this.zzom.zza(paramInt, paramArrayOfByte);
  }
  
  public final byte[] zzad()
    throws IOException
  {
    int i = this.zzom.zzciZ.remaining();
    if (i < 0) {
      throw new IOException();
    }
    if (i == 0) {
      return this.zzon;
    }
    byte[] arrayOfByte = new byte[this.zzon.length - i];
    System.arraycopy(this.zzon, 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public final void zzb(int paramInt, long paramLong)
    throws IOException
  {
    this.zzom.zzb(paramInt, paramLong);
  }
  
  public final void zzb(int paramInt, String paramString)
    throws IOException
  {
    this.zzom.zzb(paramInt, paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzat
 * JD-Core Version:    0.7.0.1
 */