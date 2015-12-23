package com.google.android.gms.internal;

import java.io.IOException;
import java.util.List;

public abstract class zzaid<M extends zzaid<M>>
  extends zzaik
{
  protected zzaig zzcja;
  
  private M zzOX()
    throws CloneNotSupportedException
  {
    zzaid localzzaid = (zzaid)super.clone();
    zzaii.zza(this, localzzaid);
    return localzzaid;
  }
  
  protected int computeSerializedSize()
  {
    int i = 0;
    if (this.zzcja != null)
    {
      j = 0;
      while (i < this.zzcja.size())
      {
        j += this.zzcja.zzsB(i).computeSerializedSize();
        i++;
      }
    }
    int j = 0;
    return j;
  }
  
  public void writeTo(zzaic paramzzaic)
    throws IOException
  {
    if (this.zzcja == null) {}
    for (;;)
    {
      return;
      for (int i = 0; i < this.zzcja.size(); i++) {
        this.zzcja.zzsB(i).writeTo(paramzzaic);
      }
    }
  }
  
  protected final boolean zza(zzaib paramzzaib, int paramInt)
    throws IOException
  {
    int i = paramzzaib.getPosition();
    if (!paramzzaib.zzsj(paramInt)) {
      return false;
    }
    int j = zzain.zzsF(paramInt);
    int k = paramzzaib.getPosition() - i;
    byte[] arrayOfByte;
    zzaim localzzaim;
    Object localObject;
    label72:
    zzaih localzzaih;
    zzaig localzzaig2;
    int i1;
    if (k == 0)
    {
      arrayOfByte = zzain.zzcjr;
      localzzaim = new zzaim(paramInt, arrayOfByte);
      if (this.zzcja != null) {
        break label166;
      }
      this.zzcja = new zzaig();
      localObject = null;
      if (localObject == null)
      {
        localzzaih = new zzaih();
        localzzaig2 = this.zzcja;
        i1 = localzzaig2.zzsC(j);
        if (i1 < 0) {
          break label219;
        }
        localzzaig2.zzcjg[i1] = localzzaih;
        localObject = localzzaih;
      }
    }
    for (;;)
    {
      ((zzaih)localObject).zzcji.add(localzzaim);
      return true;
      arrayOfByte = new byte[k];
      int m = i + paramzzaib.zzciQ;
      System.arraycopy(paramzzaib.buffer, m, arrayOfByte, 0, k);
      break;
      label166:
      zzaig localzzaig1 = this.zzcja;
      int n = localzzaig1.zzsC(j);
      if ((n < 0) || (localzzaig1.zzcjg[n] == zzaig.zzcjd))
      {
        localObject = null;
        break label72;
      }
      localObject = localzzaig1.zzcjg[n];
      break label72;
      label219:
      int i2 = i1 ^ 0xFFFFFFFF;
      if ((i2 < localzzaig2.mSize) && (localzzaig2.zzcjg[i2] == zzaig.zzcjd))
      {
        localzzaig2.zzcjf[i2] = j;
        localzzaig2.zzcjg[i2] = localzzaih;
        localObject = localzzaih;
      }
      else
      {
        if ((localzzaig2.zzcje) && (localzzaig2.mSize >= localzzaig2.zzcjf.length))
        {
          localzzaig2.gc();
          i2 = 0xFFFFFFFF ^ localzzaig2.zzsC(j);
        }
        if (localzzaig2.mSize >= localzzaig2.zzcjf.length)
        {
          int i3 = zzaig.idealIntArraySize(1 + localzzaig2.mSize);
          int[] arrayOfInt = new int[i3];
          zzaih[] arrayOfzzaih = new zzaih[i3];
          System.arraycopy(localzzaig2.zzcjf, 0, arrayOfInt, 0, localzzaig2.zzcjf.length);
          System.arraycopy(localzzaig2.zzcjg, 0, arrayOfzzaih, 0, localzzaig2.zzcjg.length);
          localzzaig2.zzcjf = arrayOfInt;
          localzzaig2.zzcjg = arrayOfzzaih;
        }
        if (localzzaig2.mSize - i2 != 0)
        {
          System.arraycopy(localzzaig2.zzcjf, i2, localzzaig2.zzcjf, i2 + 1, localzzaig2.mSize - i2);
          System.arraycopy(localzzaig2.zzcjg, i2, localzzaig2.zzcjg, i2 + 1, localzzaig2.mSize - i2);
        }
        localzzaig2.zzcjf[i2] = j;
        localzzaig2.zzcjg[i2] = localzzaih;
        localzzaig2.mSize = (1 + localzzaig2.mSize);
        localObject = localzzaih;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaid
 * JD-Core Version:    0.7.0.1
 */