package com.google.android.gms.wearable.internal;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.LargeAssetApi.QueueEntryBuffer;

public final class zzbt
  extends AbstractDataBuffer<Object>
  implements LargeAssetApi.QueueEntryBuffer
{
  private static final long[] zzchq = new long[0];
  private static final int[] zzchr = new int[0];
  private final Status zzUc;
  private final LongSparseArray<zza> zzchs;
  
  public zzbt(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
    this.zzUc = zzbz.zzhO(paramDataHolder.zzakr);
    Bundle localBundle = paramDataHolder.zzarz;
    if (localBundle == null) {}
    long[] arrayOfLong;
    int[] arrayOfInt1;
    LongSparseArray localLongSparseArray1;
    for (Object localObject = new LongSparseArray(0);; localObject = localLongSparseArray1)
    {
      this.zzchs = ((LongSparseArray)localObject);
      return;
      arrayOfLong = localBundle.getLongArray("notPausedTransferIds");
      if (arrayOfLong != null) {}
      for (;;)
      {
        arrayOfInt1 = localBundle.getIntArray("notPausedStates");
        if (arrayOfInt1 != null) {
          break label142;
        }
        zza localzza1 = new zza(2, 0);
        zzx.zzC(localzza1);
        localLongSparseArray1 = new LongSparseArray(arrayOfLong.length);
        int j = arrayOfLong.length;
        while (i < j)
        {
          localLongSparseArray1.put(arrayOfLong[i], localzza1);
          i++;
        }
        arrayOfLong = zzchq;
      }
    }
    label142:
    int[] arrayOfInt2 = localBundle.getIntArray("refuseCodes");
    label155:
    boolean bool1;
    label167:
    LongSparseArray localLongSparseArray2;
    int k;
    label198:
    boolean bool2;
    label226:
    int n;
    if (arrayOfInt2 != null)
    {
      if (arrayOfLong.length != arrayOfInt1.length) {
        break label285;
      }
      bool1 = true;
      zzx.zzb(bool1, "transferIds and states differ in length.");
      zzx.zzC(arrayOfInt2);
      localLongSparseArray2 = new LongSparseArray(arrayOfLong.length);
      k = 0;
      int m = 0;
      if (k >= arrayOfLong.length) {
        break label297;
      }
      if (arrayOfInt1[k] != 4) {
        break label304;
      }
      if (m >= arrayOfInt2.length) {
        break label291;
      }
      bool2 = true;
      zzx.zzb(bool2, "More entries in STATE_REFUSED than refuseCodes");
      n = arrayOfInt2[m];
      m++;
    }
    for (;;)
    {
      zza localzza2 = new zza(arrayOfInt1[k], n);
      localLongSparseArray2.put(arrayOfLong[k], localzza2);
      k++;
      break label198;
      arrayOfInt2 = zzchr;
      break label155;
      label285:
      bool1 = false;
      break label167;
      label291:
      bool2 = false;
      break label226;
      label297:
      localObject = localLongSparseArray2;
      break;
      label304:
      n = 0;
    }
  }
  
  public final Status getStatus()
  {
    return this.zzUc;
  }
  
  public final String toString()
  {
    return "QueueEntryBufferImpl{status=" + this.zzUc + ", entryMetadataByTransferId=" + this.zzchs + "}";
  }
  
  private static final class zza
  {
    public final int state;
    public final int zzcht;
    
    public zza(int paramInt1, int paramInt2)
    {
      this.state = zzbt.zzjW(paramInt1);
      this.zzcht = paramInt2;
    }
    
    public final String toString()
    {
      return "EntryMetadata{state=" + this.state + ", refuseCode=" + this.zzcht + "}";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbt
 * JD-Core Version:    0.7.0.1
 */