package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbu
  implements Parcelable.Creator<RemoveLargeAssetQueueEntriesResponse>
{
  static void zza(RemoveLargeAssetQueueEntriesResponse paramRemoveLargeAssetQueueEntriesResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramRemoveLargeAssetQueueEntriesResponse.versionCode);
    zzb.zza$377a007(paramParcel, 2, paramRemoveLargeAssetQueueEntriesResponse.status, paramInt);
    zzb.zzc(paramParcel, 3, paramRemoveLargeAssetQueueEntriesResponse.zzcgh);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbu
 * JD-Core Version:    0.7.0.1
 */