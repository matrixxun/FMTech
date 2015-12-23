package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzah
  implements Parcelable.Creator<EnqueueLargeAssetResponse>
{
  static void zza(EnqueueLargeAssetResponse paramEnqueueLargeAssetResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramEnqueueLargeAssetResponse.mVersionCode);
    zzb.zzc(paramParcel, 2, paramEnqueueLargeAssetResponse.statusCode);
    zzb.zza$377a007(paramParcel, 3, paramEnqueueLargeAssetResponse.zzcgi, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzah
 * JD-Core Version:    0.7.0.1
 */