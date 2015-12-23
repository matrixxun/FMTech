package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbd
  implements Parcelable.Creator<LargeAssetEnqueueRequest>
{
  static void zza(LargeAssetEnqueueRequest paramLargeAssetEnqueueRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetEnqueueRequest.mVersionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramLargeAssetEnqueueRequest.zzcgE);
    zzb.zza$2cfb68bf(paramParcel, 3, paramLargeAssetEnqueueRequest.path);
    zzb.zza$377a007(paramParcel, 4, paramLargeAssetEnqueueRequest.zzbru, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 5, paramLargeAssetEnqueueRequest.zzcgF);
    zzb.zza(paramParcel, 6, paramLargeAssetEnqueueRequest.zzcgG);
    zzb.zza(paramParcel, 7, paramLargeAssetEnqueueRequest.zzcgH);
    zzb.zza(paramParcel, 8, paramLargeAssetEnqueueRequest.zzcgI);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbd
 * JD-Core Version:    0.7.0.1
 */