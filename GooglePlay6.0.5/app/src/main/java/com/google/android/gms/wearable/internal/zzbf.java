package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbf
  implements Parcelable.Creator<LargeAssetQueueEntryParcelable>
{
  static void zza(LargeAssetQueueEntryParcelable paramLargeAssetQueueEntryParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetQueueEntryParcelable.mVersionCode);
    zzb.zza(paramParcel, 2, paramLargeAssetQueueEntryParcelable.zzcgK);
    zzb.zzc(paramParcel, 3, paramLargeAssetQueueEntryParcelable.mState);
    zzb.zza$2cfb68bf(paramParcel, 4, paramLargeAssetQueueEntryParcelable.mPath);
    zzb.zza$2cfb68bf(paramParcel, 5, paramLargeAssetQueueEntryParcelable.zzcev);
    zzb.zza$377a007(paramParcel, 6, paramLargeAssetQueueEntryParcelable.zzcex, paramInt);
    zzb.zzc(paramParcel, 8, paramLargeAssetQueueEntryParcelable.zzcgL);
    zzb.zza(paramParcel, 9, paramLargeAssetQueueEntryParcelable.zzcey);
    zzb.zza(paramParcel, 10, paramLargeAssetQueueEntryParcelable.zzcez);
    zzb.zza(paramParcel, 11, paramLargeAssetQueueEntryParcelable.zzceA);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbf
 * JD-Core Version:    0.7.0.1
 */