package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzaik
{
  public volatile int zzcjk = -1;
  
  public static final <T extends zzaik> T mergeFrom$44fe8ab2(T paramT, byte[] paramArrayOfByte, int paramInt)
    throws zzaij
  {
    try
    {
      zzaib localzzaib = zzaib.zza(paramArrayOfByte, 0, paramInt);
      paramT.mergeFrom(localzzaib);
      localzzaib.zzsi(0);
      return paramT;
    }
    catch (zzaij localzzaij)
    {
      throw localzzaij;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
    }
  }
  
  public static final byte[] toByteArray(zzaik paramzzaik)
  {
    byte[] arrayOfByte = new byte[paramzzaik.getSerializedSize()];
    int i = arrayOfByte.length;
    try
    {
      zzaic localzzaic = zzaic.zzb(arrayOfByte, 0, i);
      paramzzaik.writeTo(localzzaic);
      localzzaic.zzOW();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", localIOException);
    }
  }
  
  public zzaik clone()
    throws CloneNotSupportedException
  {
    return (zzaik)super.clone();
  }
  
  public int computeSerializedSize()
  {
    return 0;
  }
  
  public final int getSerializedSize()
  {
    int i = computeSerializedSize();
    this.zzcjk = i;
    return i;
  }
  
  public abstract zzaik mergeFrom(zzaib paramzzaib)
    throws IOException;
  
  public String toString()
  {
    return zzail.zzf(this);
  }
  
  public void writeTo(zzaic paramzzaic)
    throws IOException
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaik
 * JD-Core Version:    0.7.0.1
 */