package com.google.android.gms.internal;

import java.io.IOException;

public final class zzain
{
  public static final long[] zzchq = new long[0];
  public static final int[] zzchr = new int[0];
  public static final float[] zzcjm = new float[0];
  public static final double[] zzcjn = new double[0];
  public static final boolean[] zzcjo = new boolean[0];
  public static final String[] zzcjp = new String[0];
  public static final byte[][] zzcjq = new byte[0][];
  public static final byte[] zzcjr = new byte[0];
  
  static int zzab(int paramInt1, int paramInt2)
  {
    return paramInt2 | paramInt1 << 3;
  }
  
  public static boolean zzb(zzaib paramzzaib, int paramInt)
    throws IOException
  {
    return paramzzaib.zzsj(paramInt);
  }
  
  public static final int zzc(zzaib paramzzaib, int paramInt)
    throws IOException
  {
    int i = 1;
    int j = paramzzaib.getPosition();
    paramzzaib.zzsj(paramInt);
    while (paramzzaib.zzOF() == paramInt)
    {
      paramzzaib.zzsj(paramInt);
      i++;
    }
    paramzzaib.zzsn(j);
    return i;
  }
  
  static int zzsE(int paramInt)
  {
    return paramInt & 0x7;
  }
  
  public static int zzsF(int paramInt)
  {
    return paramInt >>> 3;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzain
 * JD-Core Version:    0.7.0.1
 */