package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

final class zzaih
  implements Cloneable
{
  private Object zzbBb;
  private zzaie<?, ?> zzcjh;
  List<zzaim> zzcji = new ArrayList();
  
  private byte[] toByteArray()
    throws IOException
  {
    byte[] arrayOfByte = new byte[computeSerializedSize()];
    writeTo(zzaic.zzb(arrayOfByte, 0, arrayOfByte.length));
    return arrayOfByte;
  }
  
  final int computeSerializedSize()
  {
    int i;
    if (this.zzbBb != null) {
      i = this.zzcjh.zzay(this.zzbBb);
    }
    for (;;)
    {
      return i;
      Iterator localIterator = this.zzcji.iterator();
      i = 0;
      while (localIterator.hasNext())
      {
        zzaim localzzaim = (zzaim)localIterator.next();
        i += 0 + zzaic.zzsx(localzzaim.tag) + localzzaim.zzcjl.length;
      }
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    boolean bool2;
    if (paramObject == this) {
      bool2 = true;
    }
    zzaih localzzaih;
    zzaie localzzaie1;
    zzaie localzzaie2;
    do
    {
      boolean bool1;
      do
      {
        return bool2;
        bool1 = paramObject instanceof zzaih;
        bool2 = false;
      } while (!bool1);
      localzzaih = (zzaih)paramObject;
      if ((this.zzbBb == null) || (localzzaih.zzbBb == null)) {
        break;
      }
      localzzaie1 = this.zzcjh;
      localzzaie2 = localzzaih.zzcjh;
      bool2 = false;
    } while (localzzaie1 != localzzaie2);
    if (!this.zzcjh.zzcjb.isArray()) {
      return this.zzbBb.equals(localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof byte[])) {
      return Arrays.equals((byte[])this.zzbBb, (byte[])localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof int[])) {
      return Arrays.equals((int[])this.zzbBb, (int[])localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof long[])) {
      return Arrays.equals((long[])this.zzbBb, (long[])localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof float[])) {
      return Arrays.equals((float[])this.zzbBb, (float[])localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof double[])) {
      return Arrays.equals((double[])this.zzbBb, (double[])localzzaih.zzbBb);
    }
    if ((this.zzbBb instanceof boolean[])) {
      return Arrays.equals((boolean[])this.zzbBb, (boolean[])localzzaih.zzbBb);
    }
    return Arrays.deepEquals((Object[])this.zzbBb, (Object[])localzzaih.zzbBb);
    if ((this.zzcji != null) && (localzzaih.zzcji != null)) {
      return this.zzcji.equals(localzzaih.zzcji);
    }
    try
    {
      boolean bool3 = Arrays.equals(toByteArray(), localzzaih.toByteArray());
      return bool3;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }
  
  public final int hashCode()
  {
    try
    {
      int i = Arrays.hashCode(toByteArray());
      return i + 527;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }
  
  final void writeTo(zzaic paramzzaic)
    throws IOException
  {
    if (this.zzbBb != null) {
      this.zzcjh.zza(this.zzbBb, paramzzaic);
    }
    for (;;)
    {
      return;
      Iterator localIterator = this.zzcji.iterator();
      while (localIterator.hasNext())
      {
        zzaim localzzaim = (zzaim)localIterator.next();
        paramzzaic.zzsw(localzzaim.tag);
        paramzzaic.zzX(localzzaim.zzcjl);
      }
    }
  }
  
  public final zzaih zzOZ()
  {
    int i = 0;
    zzaih localzzaih = new zzaih();
    try
    {
      localzzaih.zzcjh = this.zzcjh;
      if (this.zzcji == null) {
        localzzaih.zzcji = null;
      }
      for (;;)
      {
        if (this.zzbBb == null) {
          return localzzaih;
        }
        if (!(this.zzbBb instanceof zzaik)) {
          break;
        }
        localzzaih.zzbBb = ((zzaik)this.zzbBb).clone();
        return localzzaih;
        localzzaih.zzcji.addAll(this.zzcji);
      }
      if (!(this.zzbBb instanceof byte[])) {
        break label119;
      }
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
    localzzaih.zzbBb = ((byte[])this.zzbBb).clone();
    return localzzaih;
    label119:
    if ((this.zzbBb instanceof byte[][]))
    {
      byte[][] arrayOfByte = (byte[][])this.zzbBb;
      byte[][] arrayOfByte1 = new byte[arrayOfByte.length][];
      localzzaih.zzbBb = arrayOfByte1;
      for (int j = 0; j < arrayOfByte.length; j++) {
        arrayOfByte1[j] = ((byte[])arrayOfByte[j].clone());
      }
    }
    if ((this.zzbBb instanceof boolean[]))
    {
      localzzaih.zzbBb = ((boolean[])this.zzbBb).clone();
      return localzzaih;
    }
    if ((this.zzbBb instanceof int[]))
    {
      localzzaih.zzbBb = ((int[])this.zzbBb).clone();
      return localzzaih;
    }
    if ((this.zzbBb instanceof long[]))
    {
      localzzaih.zzbBb = ((long[])this.zzbBb).clone();
      return localzzaih;
    }
    if ((this.zzbBb instanceof float[]))
    {
      localzzaih.zzbBb = ((float[])this.zzbBb).clone();
      return localzzaih;
    }
    if ((this.zzbBb instanceof double[]))
    {
      localzzaih.zzbBb = ((double[])this.zzbBb).clone();
      return localzzaih;
    }
    if ((this.zzbBb instanceof zzaik[]))
    {
      zzaik[] arrayOfzzaik1 = (zzaik[])this.zzbBb;
      zzaik[] arrayOfzzaik2 = new zzaik[arrayOfzzaik1.length];
      localzzaih.zzbBb = arrayOfzzaik2;
      while (i < arrayOfzzaik1.length)
      {
        arrayOfzzaik2[i] = arrayOfzzaik1[i].clone();
        i++;
      }
    }
    return localzzaih;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaih
 * JD-Core Version:    0.7.0.1
 */