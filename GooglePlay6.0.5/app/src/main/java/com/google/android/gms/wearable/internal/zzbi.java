package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbi
  implements Parcelable.Creator<LargeAssetRemoveRequest>
{
  static void zza$61d0d736(LargeAssetRemoveRequest paramLargeAssetRemoveRequest, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetRemoveRequest.mVersionCode);
    long[] arrayOfLong = paramLargeAssetRemoveRequest.zzcgV;
    if (arrayOfLong != null)
    {
      int j = zzb.zzH(paramParcel, 2);
      paramParcel.writeLongArray(arrayOfLong);
      zzb.zzI(paramParcel, j);
    }
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbi
 * JD-Core Version:    0.7.0.1
 */