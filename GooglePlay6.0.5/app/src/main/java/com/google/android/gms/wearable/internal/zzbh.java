package com.google.android.gms.wearable.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzbh
  implements Parcelable.Creator<LargeAssetQueueStateParcelable>
{
  static void zza$2673ddc(LargeAssetQueueStateParcelable paramLargeAssetQueueStateParcelable, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetQueueStateParcelable.mVersionCode);
    zzb.zzc(paramParcel, 2, paramLargeAssetQueueStateParcelable.zzcgP);
    zzb.zza$2cfb68bf(paramParcel, 3, paramLargeAssetQueueStateParcelable.zzcgQ);
    Bundle localBundle = new Bundle();
    Iterator localIterator = paramLargeAssetQueueStateParcelable.zzcgR.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localBundle.putInt((String)localEntry.getKey(), ((Integer)localEntry.getValue()).intValue());
    }
    zzb.zza$f7bef55(paramParcel, 4, localBundle);
    zzb.zzc(paramParcel, 5, paramLargeAssetQueueStateParcelable.zzcgS);
    zzb.zzc(paramParcel, 6, paramLargeAssetQueueStateParcelable.zzcgT);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbh
 * JD-Core Version:    0.7.0.1
 */