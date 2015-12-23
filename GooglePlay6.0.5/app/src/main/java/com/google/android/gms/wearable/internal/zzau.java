package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzau
  implements Parcelable.Creator<GetLargeAssetQueueStateResponse>
{
  static void zza(GetLargeAssetQueueStateResponse paramGetLargeAssetQueueStateResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGetLargeAssetQueueStateResponse.mVersionCode);
    zzb.zza$377a007(paramParcel, 2, paramGetLargeAssetQueueStateResponse.status, paramInt);
    zzb.zza$377a007(paramParcel, 3, paramGetLargeAssetQueueStateResponse.zzcgu, paramInt);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzau
 * JD-Core Version:    0.7.0.1
 */