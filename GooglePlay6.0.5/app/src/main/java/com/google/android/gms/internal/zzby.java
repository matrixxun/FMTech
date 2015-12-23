package com.google.android.gms.internal;

import java.security.MessageDigest;

@zzhb
public final class zzby
  extends zzbv
{
  private MessageDigest zztQ;
  
  public final byte[] zzz(String paramString)
  {
    int i = 0;
    String[] arrayOfString = paramString.split(" ");
    byte[] arrayOfByte1 = new byte[arrayOfString.length];
    while (i < arrayOfString.length)
    {
      int k = zzbx.zzC(arrayOfString[i]);
      arrayOfByte1[i] = ((byte)(k & 0xFF ^ (0xFF00 & k) >> 8 ^ (0xFF0000 & k) >> 16 ^ (k & 0xFF000000) >> 24));
      i++;
    }
    this.zztQ = zzcQ();
    for (;;)
    {
      byte[] arrayOfByte3;
      synchronized (this.zzqp)
      {
        if (this.zztQ == null)
        {
          byte[] arrayOfByte2 = new byte[0];
          return arrayOfByte2;
        }
        this.zztQ.reset();
        this.zztQ.update(arrayOfByte1);
        arrayOfByte3 = this.zztQ.digest();
        if (arrayOfByte3.length > 4)
        {
          j = 4;
          byte[] arrayOfByte4 = new byte[j];
          System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, arrayOfByte4.length);
          return arrayOfByte4;
        }
      }
      int j = arrayOfByte3.length;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzby
 * JD-Core Version:    0.7.0.1
 */