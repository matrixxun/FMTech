package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbk
  implements Parcelable.Creator<LargeAssetSyncRequestPayload>
{
  static void zza$2799b5f3(LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, Parcel paramParcel)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLargeAssetSyncRequestPayload.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramLargeAssetSyncRequestPayload.path);
    zzb.zza(paramParcel, 3, paramLargeAssetSyncRequestPayload.zzchc);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbk
 * JD-Core Version:    0.7.0.1
 */