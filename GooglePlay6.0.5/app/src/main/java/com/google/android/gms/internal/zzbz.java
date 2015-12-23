package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.PriorityQueue;

@zzhb
public final class zzbz
{
  private static String zza(String[] paramArrayOfString, int paramInt1, int paramInt2)
  {
    if (paramArrayOfString.length < paramInt1 + paramInt2)
    {
      zzb.e("Unable to construct shingle");
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = paramInt1; i < -1 + (paramInt1 + paramInt2); i++)
    {
      localStringBuffer.append(paramArrayOfString[i]);
      localStringBuffer.append(' ');
    }
    localStringBuffer.append(paramArrayOfString[(-1 + (paramInt1 + paramInt2))]);
    return localStringBuffer.toString();
  }
  
  private static void zza(int paramInt, long paramLong, String paramString, PriorityQueue<zza> paramPriorityQueue)
  {
    zza localzza = new zza(paramLong, paramString);
    if ((paramPriorityQueue.size() == paramInt) && (((zza)paramPriorityQueue.peek()).value > localzza.value)) {}
    do
    {
      do
      {
        return;
      } while (paramPriorityQueue.contains(localzza));
      paramPriorityQueue.add(localzza);
    } while (paramPriorityQueue.size() <= paramInt);
    paramPriorityQueue.poll();
  }
  
  public static void zza(String[] paramArrayOfString, int paramInt1, int paramInt2, PriorityQueue<zza> paramPriorityQueue)
  {
    long l1 = (2147483647L + zzbx.zzC(paramArrayOfString[0])) % 1073807359L;
    for (int i = 1; i < paramInt2 + 0; i++) {
      l1 = (l1 * 16785407L % 1073807359L + (2147483647L + zzbx.zzC(paramArrayOfString[i])) % 1073807359L) % 1073807359L;
    }
    zza(paramInt1, l1, zza(paramArrayOfString, 0, paramInt2), paramPriorityQueue);
    long l2 = zzb(16785407L, paramInt2 - 1);
    for (int j = 1; j < 1 + (paramArrayOfString.length - paramInt2); j++)
    {
      int k = zzbx.zzC(paramArrayOfString[(j - 1)]);
      int m = zzbx.zzC(paramArrayOfString[(-1 + (j + paramInt2))]);
      long l3 = l2 * ((2147483647L + k) % 1073807359L) % 1073807359L;
      l1 = (16785407L * ((l1 + 1073807359L - l3) % 1073807359L) % 1073807359L + (2147483647L + m) % 1073807359L) % 1073807359L;
      zza(paramInt1, l1, zza(paramArrayOfString, j, paramInt2), paramPriorityQueue);
    }
  }
  
  private static long zzb(long paramLong, int paramInt)
  {
    if (paramInt == 0) {
      paramLong = 1L;
    }
    while (paramInt == 1) {
      return paramLong;
    }
    if (paramInt % 2 == 0) {
      return zzb(paramLong * paramLong % 1073807359L, paramInt / 2) % 1073807359L;
    }
    return paramLong * (zzb(paramLong * paramLong % 1073807359L, paramInt / 2) % 1073807359L) % 1073807359L;
  }
  
  public static final class zza
  {
    final long value;
    final String zztR;
    
    zza(long paramLong, String paramString)
    {
      this.value = paramLong;
      this.zztR = paramString;
    }
    
    public final boolean equals(Object paramObject)
    {
      if ((paramObject == null) || (!(paramObject instanceof zza))) {}
      while (((zza)paramObject).value != this.value) {
        return false;
      }
      return true;
    }
    
    public final int hashCode()
    {
      return (int)this.value;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbz
 * JD-Core Version:    0.7.0.1
 */