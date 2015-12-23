package com.google.android.gms.internal;

import java.util.Map;

public final class zzi
{
  public final byte[] data;
  public final Map<String, String> headers;
  public final int statusCode;
  public final boolean zzA;
  public final long zzB;
  
  public zzi(int paramInt, byte[] paramArrayOfByte, Map<String, String> paramMap, boolean paramBoolean, long paramLong)
  {
    this.statusCode = paramInt;
    this.data = paramArrayOfByte;
    this.headers = paramMap;
    this.zzA = paramBoolean;
    this.zzB = paramLong;
  }
  
  public zzi(byte[] paramArrayOfByte, Map<String, String> paramMap)
  {
    this(200, paramArrayOfByte, paramMap, false, 0L);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzi
 * JD-Core Version:    0.7.0.1
 */