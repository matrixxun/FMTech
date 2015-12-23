package com.google.android.gms.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class zzaa
  extends ByteArrayOutputStream
{
  private final zzu zzaq;
  
  public zzaa(zzu paramzzu, int paramInt)
  {
    this.zzaq = paramzzu;
    this.buf = this.zzaq.zzb(Math.max(paramInt, 256));
  }
  
  private void zzd(int paramInt)
  {
    if (paramInt + this.count <= this.buf.length) {
      return;
    }
    byte[] arrayOfByte = this.zzaq.zzb(2 * (paramInt + this.count));
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
    this.zzaq.zza(this.buf);
    this.buf = arrayOfByte;
  }
  
  public final void close()
    throws IOException
  {
    this.zzaq.zza(this.buf);
    this.buf = null;
    super.close();
  }
  
  public final void finalize()
  {
    this.zzaq.zza(this.buf);
  }
  
  public final void write(int paramInt)
  {
    try
    {
      zzd(1);
      super.write(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      zzd(paramInt2);
      super.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaa
 * JD-Core Version:    0.7.0.1
 */