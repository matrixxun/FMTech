package com.google.android.gms.internal;

import java.util.Arrays;

final class zzaim
{
  final int tag;
  final byte[] zzcjl;
  
  zzaim(int paramInt, byte[] paramArrayOfByte)
  {
    this.tag = paramInt;
    this.zzcjl = paramArrayOfByte;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    zzaim localzzaim;
    do
    {
      return true;
      if (!(paramObject instanceof zzaim)) {
        return false;
      }
      localzzaim = (zzaim)paramObject;
    } while ((this.tag == localzzaim.tag) && (Arrays.equals(this.zzcjl, localzzaim.zzcjl)));
    return false;
  }
  
  public final int hashCode()
  {
    return 31 * (527 + this.tag) + Arrays.hashCode(this.zzcjl);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaim
 * JD-Core Version:    0.7.0.1
 */