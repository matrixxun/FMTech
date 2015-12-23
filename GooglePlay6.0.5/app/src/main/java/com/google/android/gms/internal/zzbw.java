package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

@zzhb
public final class zzbw
{
  private final int zztJ;
  private final int zztK;
  final int zztL;
  private final zzbv zztM = new zzby();
  
  public zzbw(int paramInt)
  {
    this.zztK = paramInt;
    this.zztJ = 6;
    this.zztL = 0;
  }
  
  final String zzA(String paramString)
  {
    String[] arrayOfString = paramString.split("\n");
    if (arrayOfString.length == 0) {
      return "";
    }
    zza localzza = new zza();
    Arrays.sort(arrayOfString, new Comparator() {});
    int i = 0;
    for (;;)
    {
      if ((i < arrayOfString.length) && (i < this.zztK))
      {
        if (arrayOfString[i].trim().length() != 0) {}
        try
        {
          localzza.write(this.zztM.zzz(arrayOfString[i]));
          i++;
        }
        catch (IOException localIOException)
        {
          zzb.e("Error while writing hash to byteStream", localIOException);
        }
      }
    }
    return localzza.toString();
  }
  
  final String zzB(String paramString)
  {
    String[] arrayOfString1 = paramString.split("\n");
    if (arrayOfString1.length == 0) {
      return "";
    }
    zza localzza = new zza();
    PriorityQueue localPriorityQueue = new PriorityQueue(this.zztK, new Comparator() {});
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      String[] arrayOfString2 = zzbx.zzD(arrayOfString1[i]);
      if (arrayOfString2.length >= this.zztJ) {
        zzbz.zza(arrayOfString2, this.zztK, this.zztJ, localPriorityQueue);
      }
    }
    Iterator localIterator = localPriorityQueue.iterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        zzbz.zza localzza1 = (zzbz.zza)localIterator.next();
        try
        {
          localzza.write(this.zztM.zzz(localzza1.zztR));
        }
        catch (IOException localIOException)
        {
          zzb.e("Error while writing hash to byteStream", localIOException);
        }
      }
    }
    return localzza.toString();
  }
  
  static final class zza
  {
    ByteArrayOutputStream zztO = new ByteArrayOutputStream(4096);
    Base64OutputStream zztP = new Base64OutputStream(this.zztO, 10);
    
    public final String toString()
    {
      try
      {
        this.zztP.close();
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          try
          {
            this.zztO.close();
            String str = this.zztO.toString();
            return str;
          }
          catch (IOException localIOException2)
          {
            zzb.e("HashManager: Unable to convert to Base64.", localIOException2);
            return "";
          }
          finally
          {
            this.zztO = null;
            this.zztP = null;
          }
          localIOException1 = localIOException1;
          zzb.e("HashManager: Unable to convert to Base64.", localIOException1);
        }
      }
    }
    
    public final void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.zztP.write(paramArrayOfByte);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbw
 * JD-Core Version:    0.7.0.1
 */